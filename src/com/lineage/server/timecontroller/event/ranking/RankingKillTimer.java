package com.lineage.server.timecontroller.event.ranking;

import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class RankingKillTimer extends TimerTask {

    private static final Log _log = LogFactory.getLog(RankingKillTimer.class);
    private ScheduledFuture<?> _timer;

    private static boolean _load = false;

    private static String[] _killName = {
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " "
    };

    private static String[] _deathName = {
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " "
    };

    // ===== 啟動（原本 10 分鐘不動）=====
    public void start() {
        _timer = GeneralThreadPool.get().scheduleAtFixedRate(
            this, 600000L, 600000L
        );
    }

    // ===== 對外取值 =====
    public static String[] killName() {
        if (!_load) {
            load();
        }
        return _killName;
    }

    public static String[] deathName() {
        if (!_load) {
            load();
        }
        return _deathName;
    }

    // ===== 新增：即時刷新（給 PVP 用）=====
    public static void refreshNow() {
        try {
            load();
        } catch (Exception e) {
            _log.error("殺人排行榜即時刷新失敗", e);
        }
    }

    // ===== Timer =====
    @Override
    public void run() {
        try {
            load();
        } catch (Exception e) {
            _log.error("殺手風雲榜時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(_timer, false);
            new RankingKillTimer().start();
        }
    }

    // ===== 核心邏輯（你原本的）=====
    private static synchronized void load() {
        try {
            Collection<L1PcInstance> allPc = World.get().getAllPlayers();
            if (allPc.isEmpty()) {
                return;
            }

            _load = true;
            restart();

            for (L1PcInstance pc : allPc) {
                if (pc == null) {
                    continue;
                }
                if (pc.getOnlineStatus() == 0) {
                    continue;
                }
                if (pc.getNetConnection() == null) {
                    continue;
                }

                int killCount = pc.get_other().get_killCount();
                if (killCount > 0) {
                    _killName = intTree(killCount, pc.getName(), _killName);
                }

                int deathCount = pc.get_other().get_deathCount();
                if (deathCount > 0) {
                    _deathName = intTree(deathCount, pc.getName(), _deathName);
                }
            }

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    private static void restart() {
        _killName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
        _deathName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }

    // ===== 排名邏輯（完全照你原本）=====
    private static String[] intTree(int count, String name, String[] userName) {

        for (int i = 0; i < userName.length; i++) {

            if (userName[i].equals(" ")) {
                userName[i] = name + "," + count;
                return userName;
            }

            String[] set = userName[i].split(",");
            int srcCount = Integer.parseInt(set[1]);

            if (count > srcCount && !set[0].equals(name)) {
                for (int j = userName.length - 1; j > i; j--) {
                    userName[j] = userName[j - 1];
                }
                userName[i] = name + "," + count;
                return userName;
            }
        }
        return userName;
    }
}
