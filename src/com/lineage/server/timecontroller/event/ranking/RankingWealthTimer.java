package com.lineage.server.timecontroller.event.ranking;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class RankingWealthTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static boolean _load;
    private static String[] _userName;
    
    static {
        _log = LogFactory.getLog(RankingWealthTimer.class);
        RankingWealthTimer._load = false;
        RankingWealthTimer._userName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }
    
    public void start() {
        int timeMillis = 600000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 600000L, 600000L);
    }
    
    public static String[] userName() {
        if (!RankingWealthTimer._load) {
            load();
        }
        String[] newUserName = new String[10];
        for (int i = 0; i < 10; ++i) {
            String[] set = RankingWealthTimer._userName[i].split(",");
            newUserName[i] = set[0];
        }
        return newUserName;
    }
    
    @Override
    public void run() {
        try {
            load();
        }
        catch (Exception e) {
            RankingWealthTimer._log.error("財富風雲榜時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            RankingWealthTimer wealthTimer = new RankingWealthTimer();
            wealthTimer.start();
        }
    }
    
    private static void load() {
        try {
            Collection<L1PcInstance> allPc = World.get().getAllPlayers();
            if (allPc.isEmpty()) {
                return;
            }
            else {
                RankingWealthTimer._load = true;
                restart();
                for (L1PcInstance tgpc : allPc) {
                    if (tgpc == null) {
                        continue;
                    }
                    else if (tgpc.getOnlineStatus() == 0) {
                        continue;
                    }
                    else if (tgpc.getNetConnection() == null) {
                        continue;
                    }
                    else if (tgpc.getAccessLevel() > 0) {
                        continue;
                    }
                    else {
                        long count = tgpc.getInventory().countItems(40308);
                        if (count > 0L) {
                            RankingWealthTimer._userName = intTree(count, tgpc.getName(), RankingWealthTimer._userName);
                        }
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            RankingWealthTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void restart() {
        RankingWealthTimer._userName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }
    
    private static String[] intTree(long count, String name, String[] userName) {
        if (userName[0].equals(" ")) {
            userName[0] = name + "," + count;
            return userName;
        }
        else {
            String[] set = userName[0].split(",");
            long srcCount = Long.parseLong(set[1]);
            if (srcCount < count && !set[0].equals(name)) {
                userName[9] = userName[8];
                userName[8] = userName[7];
                userName[7] = userName[6];
                userName[6] = userName[5];
                userName[5] = userName[4];
                userName[4] = userName[3];
                userName[3] = userName[2];
                userName[2] = userName[1];
                userName[1] = userName[0];
                userName[0] = name + "," + count;
                return userName;
            }
            else if (userName[1].equals(" ")) {
                userName[1] = name + "," + count;
                return userName;
            }
            else {
                set = userName[1].split(",");
                srcCount = Long.parseLong(set[1]);
                if (srcCount < count && !set[0].equals(name)) {
                    userName[9] = userName[8];
                    userName[8] = userName[7];
                    userName[7] = userName[6];
                    userName[6] = userName[5];
                    userName[5] = userName[4];
                    userName[4] = userName[3];
                    userName[3] = userName[2];
                    userName[2] = userName[1];
                    userName[1] = name + "," + count;
                    return userName;
                }
                else if (userName[2].equals(" ")) {
                    userName[2] = name + "," + count;
                    return userName;
                }
                else {
                    set = userName[2].split(",");
                    srcCount = Long.parseLong(set[1]);
                    if (srcCount < count && !set[0].equals(name)) {
                        userName[9] = userName[8];
                        userName[8] = userName[7];
                        userName[7] = userName[6];
                        userName[6] = userName[5];
                        userName[5] = userName[4];
                        userName[4] = userName[3];
                        userName[3] = userName[2];
                        userName[2] = name + "," + count;
                        return userName;
                    }
                    else if (userName[3].equals(" ")) {
                        userName[3] = name + "," + count;
                        return userName;
                    }
                    else {
                        set = userName[3].split(",");
                        srcCount = Long.parseLong(set[1]);
                        if (srcCount < count && !set[0].equals(name)) {
                            userName[9] = userName[8];
                            userName[8] = userName[7];
                            userName[7] = userName[6];
                            userName[6] = userName[5];
                            userName[5] = userName[4];
                            userName[4] = userName[3];
                            userName[3] = name + "," + count;
                            return userName;
                        }
                        else if (userName[4].equals(" ")) {
                            userName[4] = name + "," + count;
                            return userName;
                        }
                        else {
                            set = userName[4].split(",");
                            srcCount = Long.parseLong(set[1]);
                            if (srcCount < count && !set[0].equals(name)) {
                                userName[9] = userName[8];
                                userName[8] = userName[7];
                                userName[7] = userName[6];
                                userName[6] = userName[5];
                                userName[5] = userName[4];
                                userName[4] = name + "," + count;
                                return userName;
                            }
                            else if (userName[5].equals(" ")) {
                                userName[5] = name + "," + count;
                                return userName;
                            }
                            else {
                                set = userName[5].split(",");
                                srcCount = Long.parseLong(set[1]);
                                if (srcCount < count && !set[0].equals(name)) {
                                    userName[9] = userName[8];
                                    userName[8] = userName[7];
                                    userName[7] = userName[6];
                                    userName[6] = userName[5];
                                    userName[5] = name + "," + count;
                                    return userName;
                                }
                                else if (userName[6].equals(" ")) {
                                    userName[6] = name + "," + count;
                                    return userName;
                                }
                                else {
                                    set = userName[6].split(",");
                                    srcCount = Long.parseLong(set[1]);
                                    if (srcCount < count && !set[0].equals(name)) {
                                        userName[9] = userName[8];
                                        userName[8] = userName[7];
                                        userName[7] = userName[6];
                                        userName[6] = name + "," + count;
                                        return userName;
                                    }
                                    else if (userName[7].equals(" ")) {
                                        userName[7] = name + "," + count;
                                        return userName;
                                    }
                                    else {
                                        set = userName[7].split(",");
                                        srcCount = Long.parseLong(set[1]);
                                        if (srcCount < count && !set[0].equals(name)) {
                                            userName[9] = userName[8];
                                            userName[8] = userName[7];
                                            userName[7] = name + "," + count;
                                            return userName;
                                        }
                                        else if (userName[8].equals(" ")) {
                                            userName[8] = name + "," + count;
                                            return userName;
                                        }
                                        else {
                                            set = userName[8].split(",");
                                            srcCount = Long.parseLong(set[1]);
                                            if (srcCount < count && !set[0].equals(name)) {
                                                userName[9] = userName[8];
                                                userName[8] = name + "," + count;
                                                return userName;
                                            }
                                            else if (userName[9].equals(" ")) {
                                                userName[9] = name + "," + count;
                                                return userName;
                                            }
                                            else {
                                                set = userName[9].split(",");
                                                srcCount = Long.parseLong(set[1]);
                                                if (srcCount < count && !set[0].equals(name)) {
                                                    userName[9] = name + "," + count;
                                                    return userName;
                                                }
                                                else {
                                                    return userName;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
