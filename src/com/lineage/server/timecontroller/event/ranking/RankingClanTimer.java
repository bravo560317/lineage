package com.lineage.server.timecontroller.event.ranking;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class RankingClanTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static boolean _load;
    private static String[] _clanName;
    
    static {
        _log = LogFactory.getLog(RankingClanTimer.class);
        RankingClanTimer._load = false;
        RankingClanTimer._clanName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }
    
    public void start() {
        int timeMillis = 600000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 600000L, 600000L);
    }
    
    public static String[] userName() {
        if (!RankingClanTimer._load) {
            load();
        }
        return RankingClanTimer._clanName;
    }
    
    @Override
    public void run() {
        try {
            load();
        }
        catch (Exception e) {
            RankingClanTimer._log.error("血盟風雲榜時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            RankingClanTimer clanRTimer = new RankingClanTimer();
            clanRTimer.start();
        }
    }
    
    private static void load() {
        try {
            Collection<L1Clan> allClan = WorldClan.get().getAllClans();
            if (allClan.isEmpty()) {
                return;
            }
            else {
                RankingClanTimer._load = true;
                restart();
                for (L1Clan clan : allClan) {
                    String clanName = clan.getClanName();
                    if (clanName.equalsIgnoreCase("管理團隊")) {
                        continue;
                    }
                    else {
                        int count = clan.getOnlineClanMemberSize();
                        RankingClanTimer._clanName = intTree(count, clanName, RankingClanTimer._clanName);
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            RankingClanTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void restart() {
        RankingClanTimer._clanName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }
    
    private static String[] intTree(int count, String clanName, String[] userName) {
        if (userName[0].equals(" ")) {
            userName[0] = clanName + "," + count;
            return userName;
        }
        else {
            String[] set = userName[0].split(",");
            int srcCount = Integer.parseInt(set[1]);
            if (srcCount < count && !set[0].equals(clanName)) {
                userName[9] = userName[8];
                userName[8] = userName[7];
                userName[7] = userName[6];
                userName[6] = userName[5];
                userName[5] = userName[4];
                userName[4] = userName[3];
                userName[3] = userName[2];
                userName[2] = userName[1];
                userName[1] = userName[0];
                userName[0] = clanName + "," + count;
                return userName;
            }
            else if (userName[1].equals(" ")) {
                userName[1] = clanName + "," + count;
                return userName;
            }
            else {
                set = userName[1].split(",");
                srcCount = Integer.parseInt(set[1]);
                if (srcCount < count && !set[0].equals(clanName)) {
                    userName[9] = userName[8];
                    userName[8] = userName[7];
                    userName[7] = userName[6];
                    userName[6] = userName[5];
                    userName[5] = userName[4];
                    userName[4] = userName[3];
                    userName[3] = userName[2];
                    userName[2] = userName[1];
                    userName[1] = clanName + "," + count;
                    return userName;
                }
                else if (userName[2].equals(" ")) {
                    userName[2] = clanName + "," + count;
                    return userName;
                }
                else {
                    set = userName[2].split(",");
                    srcCount = Integer.parseInt(set[1]);
                    if (srcCount < count && !set[0].equals(clanName)) {
                        userName[9] = userName[8];
                        userName[8] = userName[7];
                        userName[7] = userName[6];
                        userName[6] = userName[5];
                        userName[5] = userName[4];
                        userName[4] = userName[3];
                        userName[3] = userName[2];
                        userName[2] = clanName + "," + count;
                        return userName;
                    }
                    else if (userName[3].equals(" ")) {
                        userName[3] = clanName + "," + count;
                        return userName;
                    }
                    else {
                        set = userName[3].split(",");
                        srcCount = Integer.parseInt(set[1]);
                        if (srcCount < count && !set[0].equals(clanName)) {
                            userName[9] = userName[8];
                            userName[8] = userName[7];
                            userName[7] = userName[6];
                            userName[6] = userName[5];
                            userName[5] = userName[4];
                            userName[4] = userName[3];
                            userName[3] = clanName + "," + count;
                            return userName;
                        }
                        else if (userName[4].equals(" ")) {
                            userName[4] = clanName + "," + count;
                            return userName;
                        }
                        else {
                            set = userName[4].split(",");
                            srcCount = Integer.parseInt(set[1]);
                            if (srcCount < count && !set[0].equals(clanName)) {
                                userName[9] = userName[8];
                                userName[8] = userName[7];
                                userName[7] = userName[6];
                                userName[6] = userName[5];
                                userName[5] = userName[4];
                                userName[4] = clanName + "," + count;
                                return userName;
                            }
                            else if (userName[5].equals(" ")) {
                                userName[5] = clanName + "," + count;
                                return userName;
                            }
                            else {
                                set = userName[5].split(",");
                                srcCount = Integer.parseInt(set[1]);
                                if (srcCount < count && !set[0].equals(clanName)) {
                                    userName[9] = userName[8];
                                    userName[8] = userName[7];
                                    userName[7] = userName[6];
                                    userName[6] = userName[5];
                                    userName[5] = clanName + "," + count;
                                    return userName;
                                }
                                else if (userName[6].equals(" ")) {
                                    userName[6] = clanName + "," + count;
                                    return userName;
                                }
                                else {
                                    set = userName[6].split(",");
                                    srcCount = Integer.parseInt(set[1]);
                                    if (srcCount < count && !set[0].equals(clanName)) {
                                        userName[9] = userName[8];
                                        userName[8] = userName[7];
                                        userName[7] = userName[6];
                                        userName[6] = clanName + "," + count;
                                        return userName;
                                    }
                                    else if (userName[7].equals(" ")) {
                                        userName[7] = clanName + "," + count;
                                        return userName;
                                    }
                                    else {
                                        set = userName[7].split(",");
                                        srcCount = Integer.parseInt(set[1]);
                                        if (srcCount < count && !set[0].equals(clanName)) {
                                            userName[9] = userName[8];
                                            userName[8] = userName[7];
                                            userName[7] = clanName + "," + count;
                                            return userName;
                                        }
                                        else if (userName[8].equals(" ")) {
                                            userName[8] = clanName + "," + count;
                                            return userName;
                                        }
                                        else {
                                            set = userName[8].split(",");
                                            srcCount = Integer.parseInt(set[1]);
                                            if (srcCount < count && !set[0].equals(clanName)) {
                                                userName[9] = userName[8];
                                                userName[8] = clanName + "," + count;
                                                return userName;
                                            }
                                            else if (userName[9].equals(" ")) {
                                                userName[9] = clanName + "," + count;
                                                return userName;
                                            }
                                            else {
                                                set = userName[9].split(",");
                                                srcCount = Integer.parseInt(set[1]);
                                                if (srcCount < count && !set[0].equals(clanName)) {
                                                    userName[9] = clanName + "," + count;
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
