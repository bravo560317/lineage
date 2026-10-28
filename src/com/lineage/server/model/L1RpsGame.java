package com.lineage.server.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.ConfigOther;
import com.lineage.server.Shutdown;
import com.lineage.server.WriteLogTxt;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.timecontroller.server.ServerRestartTimer;
import com.lineage.server.world.World;

public class L1RpsGame {
    private static L1RpsGame _instance;
    private static final Log _log = LogFactory.getLog(L1RpsGame.class);
    private static final Random _random = new Random();
    private static final int MIN_BET_COUNT = 10000;

    private WaitingPlayer _waitingPlayer = null;
    private final HashMap<Integer, Integer> _onlemembers = new HashMap<>();
    
    private long _queueStartTime = 0;

    public static L1RpsGame getInstance() {
        if (_instance == null) {
            _instance = new L1RpsGame();
        }
        return _instance;
    }

    public static class WaitingPlayer {
        public L1PcInstance pc;
        public int betCount;
        public int choice;

        public WaitingPlayer(L1PcInstance pc, int betCount, int choice) {
            this.pc = pc;
            this.betCount = betCount;
            this.choice = choice;
        }
    }

    public WaitingPlayer getWaitingPlayer() {
        return _waitingPlayer;
    }

    public String[] getRpsData(L1PcInstance pc) {
        String hostName = "無人";
        String betStr = "0";
        String statusStr = "目前無人排隊，請點擊出拳發起挑戰";

        if (_waitingPlayer != null) {
            hostName = _waitingPlayer.pc.getName();
            betStr = insertComma(_waitingPlayer.betCount);
            if (_waitingPlayer.pc.getId() == pc.getId()) {
                statusStr = "您正在排隊等待對手中...";
            } else {
                statusStr = String.format("有挑戰者！點擊出拳即刻應戰（需 %s 籌碼）！", betStr);
            }
        }

        return new String[] { hostName, betStr, statusStr };
    }

    private String insertComma(int s) {
        final NumberFormat formater = new DecimalFormat("###,###,###,###");
        return formater.format(s);
    }

    public synchronized void playRps(L1PcInstance pc, int betCount, int choice) {
        if (ServerRestartTimer.isRtartTime() || Shutdown.SHUTDOWN) {
            pc.sendPackets(new S_SystemMessage("伺服器即將重啟或關機，暫時無法進行猜拳對決！"));
            return;
        }

        if (betCount < MIN_BET_COUNT) {
            pc.sendPackets(new S_SystemMessage(String.format("最低下注籌碼為 %d！", MIN_BET_COUNT)));
            return;
        }
        final L1ItemInstance deaItem = pc.getInventory().findItemId(ConfigOther.Dubo);
        if (deaItem == null || deaItem.getCount() < betCount) {
            pc.sendPackets(new S_SystemMessage("你的籌碼不足！"));
            return;
        }

        if (_waitingPlayer != null && _waitingPlayer.pc.getId() == pc.getId()) {
            pc.sendPackets(new S_SystemMessage("你已經在排隊等待對手中了！"));
            return;
        }

        if (pc.getInventory().removeItem(deaItem, betCount) != betCount) {
            pc.sendPackets(new S_SystemMessage("扣除籌碼失敗！"));
            return;
        }

        if (_waitingPlayer == null) {
            _waitingPlayer = new WaitingPlayer(pc, betCount, choice);
            _queueStartTime = System.currentTimeMillis();
            
            pc.sendPackets(new S_SystemMessage(String.format("成功發起挑戰（下注：%d 籌碼）。等待對手應戰（10分鐘內未應戰將自動退還）...", betCount)));
            World.get().broadcastServerMessage(String.format("【對決】玩家 [%s] 發起了 %d 籌碼的猜拳對決！快去對決NPC應戰！", pc.getName(), betCount));
            
            GeneralThreadPool.get().execute(new RpsTimeoutTask(_waitingPlayer, _queueStartTime));
            return;
        }

        WaitingPlayer p1 = _waitingPlayer;
        _waitingPlayer = null;

        L1PcInstance p2 = pc;
        int p1Choice = p1.choice;
        int p2Choice = choice;

        GeneralThreadPool.get().execute(new RpsBattleTask(p1.pc, p1Choice, p2, p2Choice, p1.betCount));
    }

    public synchronized void cancelQueue(L1PcInstance pc) {
        if (_waitingPlayer != null && _waitingPlayer.pc.getId() == pc.getId()) {
            final L1ItemInstance deaItem = pc.getInventory().findItemId(ConfigOther.Dubo);
            if (deaItem != null) {
                pc.getInventory().storeItem(ConfigOther.Dubo, _waitingPlayer.betCount);
            }
            _waitingPlayer = null;
            pc.sendPackets(new S_SystemMessage("已成功取消排隊，籌碼已退還。"));
            World.get().broadcastServerMessage(String.format("【對決】玩家 [%s] 取消了猜拳挑戰。", pc.getName()));
        } else {
            pc.sendPackets(new S_SystemMessage("你目前沒有在排隊中。"));
        }
    }

    private class RpsTimeoutTask implements Runnable {
        private final WaitingPlayer targetPlayer;
        private final long savedTime;

        public RpsTimeoutTask(WaitingPlayer player, long time) {
            this.targetPlayer = player;
            this.savedTime = time;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) {
                    Thread.sleep(30000);
                    synchronized (L1RpsGame.this) {
                        if (_waitingPlayer == null || _queueStartTime != savedTime) {
                            return;
                        }
                    }
                }

                synchronized (L1RpsGame.this) {
                    if (_waitingPlayer == null || _queueStartTime != savedTime || _waitingPlayer.pc.getId() != targetPlayer.pc.getId()) {
                        return;
                    }

                    L1PcInstance host = _waitingPlayer.pc;
                    int bet = _waitingPlayer.betCount;
                    _waitingPlayer = null;

                    if (host.getOnlineStatus() == 1) {
                        host.getInventory().storeItem(ConfigOther.Dubo, bet);
                        host.sendPackets(new S_SystemMessage("【猜拳逾時】10分鐘內無人應戰，您的挑戰已自動取消，下注籌碼已全數退還。"));
                    } else {
                        _onlemembers.put(host.getId(), _onlemembers.getOrDefault(host.getId(), 0) + bet);
                    }
                    World.get().broadcastServerMessage(String.format("【對決取消】玩家 [%s] 的猜拳挑戰因 10 分鐘逾時未應戰，已自動撤銷。", host.getName()));
                    WriteLogTxt.Recording("對決記錄", String.format("玩家:%s 猜拳逾時退還籌碼(%d)", host.getName(), bet));
                }
            } catch (Exception e) {
                _log.error("RpsTimeoutTask error", e);
            }
        }
    }

    public int getOnlineCount(final L1PcInstance pc) {
        int count = 0;
        if (_onlemembers.containsKey(pc.getId())) {
            count = _onlemembers.get(pc.getId());
            _onlemembers.remove(pc.getId());
        }
        return count;
    }

    public static String getChoiceName(int choice) {
        switch (choice) {
            case 1: return "剪刀";
            case 2: return "石頭";
            case 3: return "布";
            default: return "未知";
        }
    }

    private static class RpsBattleTask implements Runnable {
        private final L1PcInstance p1;
        private final int p1Choice;
        private final L1PcInstance p2;
        private final int p2Choice;
        private final int betCount;

        public RpsBattleTask(L1PcInstance p1, int p1Choice, L1PcInstance p2, int p2Choice, int bet) {
            this.p1 = p1;
            this.p1Choice = p1Choice;
            this.p2 = p2;
            this.p2Choice = p2Choice;
            this.betCount = bet;
        }

        @Override
        public void run() {
            try {
                World.get().broadcastServerMessage(String.format("【對決】[%s] v.s [%s] 猜拳對決一局定勝負！", p1.getName(), p2.getName()));
                Thread.sleep(1000);

                World.get().broadcastServerMessage(String.format("【出拳結果】 %s 出【%s】 v.s %s 出【%s】", 
                        p1.getName(), getChoiceName(p1Choice), p2.getName(), getChoiceName(p2Choice)));
                Thread.sleep(1500);

                if (p1Choice == p2Choice) {
                    if (p1.getOnlineStatus() == 1) {
                        p1.getInventory().storeItem(ConfigOther.Dubo, betCount);
                        p1.sendPackets(new S_SystemMessage("猜拳平手，已退還您的下注籌碼。"));
                    } else {
                        _instance._onlemembers.put(p1.getId(), betCount);
                    }
                    if (p2.getOnlineStatus() == 1) {
                        p2.getInventory().storeItem(ConfigOther.Dubo, betCount);
                        p2.sendPackets(new S_SystemMessage("猜拳平手，已退還您的下注籌碼。"));
                    } else {
                        _instance._onlemembers.put(p2.getId(), betCount);
                    }
                    World.get().broadcastServerMessage(String.format("【對決平手】[%s] 與 [%s] 平手，雙方籌碼已全數退還！", p1.getName(), p2.getName()));
                    return;
                }

                L1PcInstance winner = ((p1Choice == 1 && p2Choice == 3) || (p1Choice == 2 && p2Choice == 1) || (p1Choice == 3 && p2Choice == 2)) ? p1 : p2;
                L1PcInstance loser = (winner == p1) ? p2 : p1;

                long totalPot = (long)betCount * 2;
                long tax = (long)(totalPot * 0.02);
                long reward = totalPot - tax;

                if (winner.getOnlineStatus() == 1) {
                    winner.getInventory().storeItem(ConfigOther.Dubo, reward);
                    winner.sendPackets(new S_ServerMessage(143, "博弈大師", "籌碼(" + reward + ")"));
                    WriteLogTxt.Recording("對決記錄", String.format("玩家:%s 贏得籌碼(%d)在線", winner.getName(), reward));
                } else {
                    _instance._onlemembers.put(winner.getId(), (int) reward);
                    WriteLogTxt.Recording("對決記錄", String.format("玩家:%s 贏得籌碼(%d)離線", winner.getName(), reward));
                }

                World.get().broadcastServerMessage(String.format("【對決結束】恭喜 [%s] 擊敗 [%s]，獨得獎金 %d 籌碼！ (系統抽取水錢 %d)", winner.getName(), loser.getName(), reward, tax));

            } catch (Exception e) {
                _log.error("RpsBattleTask error", e);
            }
        }
    }
}