package com.lineage.server;

import com.lineage.server.templates.L1Account;
import java.util.Collection;
import com.lineage.server.datatables.lock.ServerReading;
import com.lineage.server.datatables.lock.CharMapTimeReading;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.echo.QuitGame;
import com.lineage.config.Config;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.EchoServerTimer; // 你原本有用到
import com.lineage.server.model.Instance.L1PcInstance;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * 支援「重啟時先啟新 exe，再退出自己」；可反覆重啟。
 */
public class Shutdown extends Thread {

    // === 視你的環境調整這兩個常數 ===
    /** 直接指向你的啟動 EXE 或 BAT（兩者皆可） */
    //private static final String EXE_PATH = "D:\\秘密天堂381\\381server.exe";
	private static final String EXE_PATH = Config.RESTART_EXE_PATH;

    /** 工作目錄（讓相對路徑/資源正確）。請改成你的伺服器根資料夾 */
    //private static final java.io.File WORK_DIR = new java.io.File("D:\\秘密天堂381");
	private static final java.io.File WORK_DIR = Config.RESTART_WORK_DIR;


    // 防止同一次關閉重複啟動多個新進程
    private static final AtomicBoolean RESTART_SPAWNED = new AtomicBoolean(false);

    protected static final Random _random = new Random();
    private static final Log _log = LogFactory.getLog(Shutdown.class);

    private static Shutdown _instance;
    private static Shutdown _counterInstance;

    private int _secondsShut = -1;
    private int _shutdownMode = 0;
    private int _overTime = 5;

    public static final int SIGTERM = 0;
    public static final int GM_SHUTDOWN = 1;
    public static final int GM_RESTART = 2;
    public static final int ABORT = 3;

    public static boolean SHUTDOWN = false;
    private static boolean _isMsg = false;
    public static boolean isSHUTDOWN = false;

    public Shutdown() {}

    public Shutdown(int seconds, boolean restart) {
        if (seconds < 0) seconds = 0;
        this._secondsShut = seconds;
        this._shutdownMode = restart ? GM_RESTART : GM_SHUTDOWN;
    }

    public static Shutdown getInstance() {
        if (_instance == null) {
            _instance = new Shutdown();
            // ★ JVM 即將退出時的保險：若是「重啟模式」，先啟新 exe 再退出
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (_instance._shutdownMode == GM_RESTART && Config.ISWORKCD) {
                        _instance.spawnNewProcess();
                    }
                } catch (Throwable t) {
                    _log.error("[RestartHook] 啟動新進程失敗", t);
                }
            }, "RestartHook"));
        }
        return _instance;
    }

    @Override
    public void run() {
        if (this == _instance) {
            // 主關閉執行緒：結尾延遲倒數
            if (this._shutdownMode != ABORT) {
                try {
                    this.saveData();
                    _log.warn("正在關閉玩家連線中...");
                    Thread.sleep(3000L);
                    while (this._overTime > 0) {
                        _log.warn("距離核心完全關閉 : " + this._overTime + "秒!");
                        --this._overTime;
                        Thread.sleep(1000L);
                    }
                    int list = com.lineage.list.OnlineUser.get().size();
                    _log.warn("核心關閉殘餘連線帳號數量: " + list);
                    Thread.sleep(1000L);
                    if (list >= 0) {
                        for (String account : com.lineage.list.OnlineUser.get().map().keySet()) {
                            _log.warn("核心關閉殘留帳號: " + account);
                        }
                    }
                } catch (InterruptedException e) {
                    _log.error(e.getLocalizedMessage(), e);
                }
            }
        } else {
            // 倒數執行緒
            this.countdown();
            if (this._shutdownMode != ABORT) {
                switch (this._shutdownMode) {
                    case GM_SHUTDOWN: {
                        _instance.setMode(GM_SHUTDOWN);
                        System.exit(0);
                        break;
                    }
                    case GM_RESTART: {
                        _instance.setMode(GM_RESTART);

                        if (Config.ISWORKCD) {
                            // 新方法：由程式內先啟 EXE，再正常退出
                            _log.warn("[Restart] ISWORKCD=true → 以程式內啟 EXE 後退出");
                            spawnNewProcess();
                            System.exit(0);       // 若外部還有監護，建議用 0 以免雙重拉起
                        } else {
                            // 舊方法：不啟 EXE，純退出交給外部監護(服務/NSSM/bat)自動重啟
                            _log.warn("[Restart] ISWORKCD=false → 僅退出，由外部監護重啟");
                            System.exit(1);       // 依你的外部監護設定，維持原本退出碼
                        }
                        break;
                    }

                }
            }
        }
    }

    /** 在舊進程退出前，啟動一個全新的伺服器進程（exe 或 bat） */
    private void spawnNewProcess() {
        if (RESTART_SPAWNED.getAndSet(true)) {
            _log.warn("[Restart] 已啟動過新進程，略過重複呼叫");
            return;
        }
        try {
            // 用 cmd /c start 開新視窗背景執行，避免卡住本進程
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "", EXE_PATH);
            pb.directory(WORK_DIR);
            pb.inheritIO(); // 讓輸出可見（可視需要拿掉）
            Process p = pb.start();
            _log.warn("[Restart] 已啟動新進程：" + EXE_PATH + "（pid 不一定可得）");
        } catch (Exception e) {
            _log.error("[Restart] 啟動新進程失敗：" + EXE_PATH, e);
        }
    }

    public void startShutdown(L1PcInstance activeChar, int seconds, boolean restart) {
        if (_counterInstance != null) return;

        if (activeChar != null) {
            _log.warn(activeChar.getName() + " 啟動關機計時: " + seconds + " 秒!");
        }
        if (_counterInstance != null) {
            _counterInstance._abort();
        }
        _counterInstance = new Shutdown(seconds, restart);
        // 每次啟動新的倒數，重置防重入旗標，允許再次重啟
        RESTART_SPAWNED.set(false);

        GeneralThreadPool.get().execute(_counterInstance);
    }

    public void abort(L1PcInstance activeChar) {
        if (_counterInstance != null) {
            _counterInstance._abort();
        }
    }

    private void setMode(int mode) {
        this._shutdownMode = mode;
    }

    private void _abort() {
        this._shutdownMode = ABORT;
    }

    private void countdown() {
        try {
            SHUTDOWN = true;
            World.get().broadcastPacketToAll(new S_ServerMessage(72, String.valueOf(this._secondsShut)));
            while (this._secondsShut > 0) {
                switch (this._secondsShut) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 10:
                    case 30:
                    case 60:
                    case 120:
                    case 150:
                    case 180:
                    case 240: {
                        if (this._secondsShut == 60) {
                            _log.warn("第二次儲存玩家資料..");
                            this.onlineusersave();
                        }
                        if (this._secondsShut != 1) {
                            _log.warn("關機倒數: " + this._secondsShut + " 秒!");
                        }
                        World.get().broadcastPacketToAll(new S_ServerMessage(72, String.valueOf(this._secondsShut)));
                        break;
                    }
                    case 300: {
                        _log.warn("第一次儲存玩家資料..");
                        this.onlineusersave();
                        Thread.sleep(2000L);
                        isSHUTDOWN = true;
                        for (L1PcInstance tgpc : World.get().getAllPlayers()) {
                            tgpc.setSkillEffect(1755, 10000 + _random.nextInt(50000));
                        }
                        break;
                    }
                }
                --this._secondsShut;
                Thread.sleep(1000L);

                // ★ 修正：去掉你原本重複判斷
                if (this._shutdownMode == ABORT && this._secondsShut > 5) {
                    SHUTDOWN = false;
                    isSHUTDOWN = false;
                    EchoServerTimer.get().reStart();
                    World.get().broadcastPacketToAll(new S_ServerMessage(166, "取消關機倒數!!遊戲將會正常執行!!"));
                    return;
                }
            }
        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        } finally {
            if (this._shutdownMode != ABORT) {
                this.saveData();
            } else {
                this._secondsShut = -1;
                this._shutdownMode = 0;
                _counterInstance = null;
            }
        }
    }

    private synchronized void saveData() {
        try {
            World.get().broadcastPacketToAll(new S_Disconnect());
            Collection<ClientExecutor> list = com.lineage.list.OnlineUser.get().all();
            if (!_isMsg) {
                _isMsg = true;
                _log.info("人物/物品 資料的存檔 - 關閉核心前連線帳號數量: " + list.size());
            }
            for (ClientExecutor client : list) {
                L1PcInstance tgpc = client.getActiveChar();
                if (tgpc != null) {
                    QuitGame.quitGame(tgpc);
                    client.setActiveChar(null);
                    client.kick();
                    client.close();
                }
                com.lineage.server.templates.L1Account value = client.getAccount();
                if (value != null) {
                    if (value.is_isLoad()) {
                        com.lineage.list.OnlineUser.get().remove(client.getAccountName());
                    }
                    value.set_isLoad(false);
                    AccountReading.get().updateLan(client.getAccountName(), false);
                }
            }
        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        } finally {
            AccountReading.get().updateLan();
            CharMapTimeReading.get().saveAllTime();
            ServerReading.get().isStop();
        }
    }

    private void onlineusersave() {
        Collection<ClientExecutor> list = com.lineage.list.OnlineUser.get().all();
        for (ClientExecutor client : list) {
            L1PcInstance tgpc = client.getActiveChar();
            if (tgpc != null) {
                try {
                    tgpc.save();
                    tgpc.saveInventory();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
