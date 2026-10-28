package com.lineage.server.timecontroller.server;

import com.lineage.server.Shutdown;
import com.lineage.server.world.World;
import com.lineage.server.datatables.QuizSetTable;
import com.lineage.config.ConfigAlt;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.config.Config;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.TimerTask;

/**
 * 自動重啟時間軸
 * - 讀取 Config.AUTORESTART（多個 "HH:mm"），挑選最近一次未來時間排程
 * - 到點後觸發 Shutdown 倒數（重啟模式）
 * - 記錄日誌時會附帶 Config.ISWORKCD 狀態（方便判讀將採用的重啟方式）
 */
public class ServerRestartTimer extends TimerTask {

    private static final Log _log = LogFactory.getLog(ServerRestartTimer.class);

    private ScheduledFuture<?> _timer;

    private static final ArrayList<Calendar> _restartList = new ArrayList<>();
    private static Calendar _restart = null;

    private static final String _fmt = "yyyy/MM/dd HH:mm:ss";
    private static String _startTime = null;
    private static String _restartTime = null;

    public static String get_restartTime() {
        return _restartTime;
    }

    public static String get_startTime() {
        return _startTime;
    }

    /** 倒數 10 分鐘內回傳 true（給外部查詢用） */
    public static boolean isRtartTime() {
        return _restart != null && (_restart.getTimeInMillis() - System.currentTimeMillis()) <= 600000L;
    }

    private static Calendar nowWithTz() {
        TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        return Calendar.getInstance(tz);
    }

    public void start() {
        if (Config.AUTORESTART == null || Config.AUTORESTART.length == 0) {
            _log.info("[AutoRestart] 未設定 AUTORESTART，功能停用");
            return;
        }

        // 啟動時間記錄
        Calendar cals = nowWithTz();
        if (_startTime == null) {
            _startTime = new SimpleDateFormat(_fmt).format(cals.getTime());
        }

        // 重新計算下一次重啟時間
        computeNextRestart(cals);

        if (_restart == null) {
            _log.warn("[AutoRestart] 無法計算下一次重啟時間，請檢查 AUTORESTART 格式（HH:mm）");
            return;
        }

        _restartTime = new SimpleDateFormat(_fmt).format(_restart.getTime());
        _log.info(String.format("[AutoRestart] 啟動時間：%s；下一次重啟：%s；模式：%s",
                _startTime, _restartTime, Config.ISWORKCD ? "內建拉起 EXE" : "外部監護重啟"));

        // 每 60 秒檢查一次是否到點
        _timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 60_000L, 60_000L);
    }

    @Override
    public void run() {
        try {
            startCommand();
        } catch (Exception e) {
            _log.error("自動重啟時間軸異常，嘗試重新啟動排程", e);
            GeneralThreadPool.get().cancel(_timer, false);
            ServerRestartTimer restartTimer = new ServerRestartTimer();
            restartTimer.start();
        }
    }

    private void startCommand() {
        if (Config.AUTORESTART == null || Config.AUTORESTART.length == 0) return;

        Calendar now = Calendar.getInstance(); // 這裡用系統預設時區即可

        // 每分鐘 tick：若已到點（或已過），觸發重啟並重新排下一次
        if (_restart != null && _restart.before(now)) {
            _log.info(String.format("[AutoRestart] 觸發重啟：%s；模式：%s（ISWORKCD=%s）",
                    new SimpleDateFormat(_fmt).format(_restart.getTime()),
                    Config.ISWORKCD ? "內建啟 EXE 後退出" : "直接退出交由外部重啟",
                    Config.ISWORKCD));

            // 這裡發出重啟倒數（300 秒），最後由 Shutdown 依 ISWORKCD 決定如何啟新實例
            Shutdown.getInstance().startShutdown(null, 300, true);

            // 重算下一次
            computeNextRestart(nowWithTz());
            if (_restart != null) {
                _restartTime = new SimpleDateFormat(_fmt).format(_restart.getTime());
                _log.info("[AutoRestart] 已重排下一次時間：" + _restartTime);
            }
        }

        // 額外：每日一題的重置邏輯（保留原本）
        if (ConfigAlt.QUIZ_SET_RESET_TIME != null && ConfigAlt.QUIZ_SET_RESET_TIME.before(now)) {
            if (ConfigAlt.QUIZ_SET_SWITCH) {
                QuizSetTable.getInstance().updateQuizToNext();
                QuizSetTable.getInstance().updateQuizInfo();
                QuizSetTable.getInstance().updateAllPcQuizSet();
				 // 關閉公告
                 // World.get().broadcastServerMessage("\\fX【每日一題】今日題目已自動更新完成，快來賺獎品喔！");
            }
            ConfigAlt.QUIZ_SET_RESET_TIME = null;
        }
    }

    /**
     * 根據 Config.AUTORESTART（多個 HH:mm）計算「最近的未來」時間。
     * 若今日皆已過，則取明日對應時間。
     */
    private void computeNextRestart(Calendar baseNow) {
        _restartList.clear();
        _restart = null;

        try {
            String HH = new SimpleDateFormat("HH").format(baseNow.getTime());
            int curH = Integer.parseInt(HH);
            String mm = new SimpleDateFormat("mm").format(baseNow.getTime());
            int curM = Integer.parseInt(mm);

            for (String hm : Config.AUTORESTART) {
                if (hm == null || hm.isEmpty() || !hm.contains(":")) {
                    _log.warn("[AutoRestart] 時間格式錯誤，略過：" + hm);
                    continue;
                }
                String[] parts = hm.split(":");
                if (parts.length != 2) {
                    _log.warn("[AutoRestart] 時間格式錯誤，略過：" + hm);
                    continue;
                }

                int tgtH, tgtM;
                try {
                    tgtH = Integer.parseInt(parts[0].trim());
                    tgtM = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    _log.warn("[AutoRestart] 時間數字解析失敗，略過：" + hm);
                    continue;
                }

                Calendar cal = (Calendar) baseNow.clone();
                int diffH = tgtH - curH;
                int diffM = tgtM - curM;

                int addH, addM;
                if (diffH > 0) { // 今天稍後
                    addH = diffH; addM = diffM;
                } else if (diffH == 0) { // 同一小時
                    if (diffM > 0) { // 今天稍後
                        addH = 0; addM = diffM;
                    } else { // 已過 → 明天
                        addH = 24; addM = diffM;
                    }
                } else { // 已過 → 明天
                    addH = 24 + diffH; addM = diffM;
                }

                cal.add(Calendar.HOUR_OF_DAY, addH);
                cal.add(Calendar.MINUTE, addM);
                _restartList.add(cal);
            }

            for (Calendar c : _restartList) {
                if (_restart == null || c.before(_restart)) {
                    _restart = c;
                }
            }
        } catch (Exception e) {
            _log.error("[AutoRestart] 計算下一次時間失敗", e);
            _restart = null;
        }
    }
}
