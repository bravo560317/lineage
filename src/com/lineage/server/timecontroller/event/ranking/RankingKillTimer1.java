package com.lineage.server.timecontroller.event.ranking;

import com.lineage.server.templates.L1Account;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class RankingKillTimer1 extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static boolean _load;
    private static String[] _killName;
    
    static {
        _log = LogFactory.getLog(RankingKillTimer1.class);
        RankingKillTimer1._load = false;
        RankingKillTimer1._killName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }
    
    public void start() {
        int timeMillis = 5000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 5000L, 5000L);
    }
    
    public static String[] killName() {
        if (!RankingKillTimer1._load) {
            load();
        }
        return RankingKillTimer1._killName;
    }
    
    @Override
    public void run() {
        try {
            load();
        }
        catch (Exception e) {
            RankingKillTimer1._log.error("贊助排行榜時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            RankingKillTimer1 killTimer = new RankingKillTimer1();
            killTimer.start();
        }
    }
    
    private static void load() {
        try {
            Collection<L1PcInstance> allPc = World.get().getAllPlayers();
            if (allPc.isEmpty()) {
                return;
            }
            else {
                RankingKillTimer1._load = true;
                restart();
for (L1PcInstance tgpc : allPc) {
    if (tgpc == null) {
        continue;
    }
    if (tgpc.isGm()) {
        continue;
    }
    
    // --- 修改開始：增加連線與帳號的非空判斷 ---
    if (tgpc.getNetConnection() == null || tgpc.getNetConnection().getAccount() == null) {
        continue; 
    }
    
    L1Account account = tgpc.getNetConnection().getAccount();
    // --- 修改結束 ---

    int killCount = account.get_first_pay();
    if (killCount > 0) {
        RankingKillTimer1._killName = intTree(killCount, tgpc.getName(), RankingKillTimer1._killName);
    }
    Thread.sleep(1L);
	    }
		    }
			    }
		
        catch (Exception e) {
            RankingKillTimer1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void restart() {
        RankingKillTimer1._killName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }
    
    private static String[] intTree(int killCount, String name, String[] userName) {
        if (userName[0].equals(" ")) {
            userName[0] = name + "," + killCount;
            return userName;
        }
        else {
            String[] set = userName[0].split(",");
            int srcCount = Integer.parseInt(set[1]);
            if (srcCount < killCount && !set[0].equals(name)) {
                userName[9] = userName[8];
                userName[8] = userName[7];
                userName[7] = userName[6];
                userName[6] = userName[5];
                userName[5] = userName[4];
                userName[4] = userName[3];
                userName[3] = userName[2];
                userName[2] = userName[1];
                userName[1] = userName[0];
                userName[0] = name + "," + killCount;
                return userName;
            }
            else if (userName[1].equals(" ")) {
                userName[1] = name + "," + killCount;
                return userName;
            }
            else {
                set = userName[1].split(",");
                srcCount = Integer.parseInt(set[1]);
                if (srcCount < killCount && !set[0].equals(name)) {
                    userName[9] = userName[8];
                    userName[8] = userName[7];
                    userName[7] = userName[6];
                    userName[6] = userName[5];
                    userName[5] = userName[4];
                    userName[4] = userName[3];
                    userName[3] = userName[2];
                    userName[2] = userName[1];
                    userName[1] = name + "," + killCount;
                    return userName;
                }
                else if (userName[2].equals(" ")) {
                    userName[2] = name + "," + killCount;
                    return userName;
                }
                else {
                    set = userName[2].split(",");
                    srcCount = Integer.parseInt(set[1]);
                    if (srcCount < killCount && !set[0].equals(name)) {
                        userName[9] = userName[8];
                        userName[8] = userName[7];
                        userName[7] = userName[6];
                        userName[6] = userName[5];
                        userName[5] = userName[4];
                        userName[4] = userName[3];
                        userName[3] = userName[2];
                        userName[2] = name + "," + killCount;
                        return userName;
                    }
                    else if (userName[3].equals(" ")) {
                        userName[3] = name + "," + killCount;
                        return userName;
                    }
                    else {
                        set = userName[3].split(",");
                        srcCount = Integer.parseInt(set[1]);
                        if (srcCount < killCount && !set[0].equals(name)) {
                            userName[9] = userName[8];
                            userName[8] = userName[7];
                            userName[7] = userName[6];
                            userName[6] = userName[5];
                            userName[5] = userName[4];
                            userName[4] = userName[3];
                            userName[3] = name + "," + killCount;
                            return userName;
                        }
                        else if (userName[4].equals(" ")) {
                            userName[4] = name + "," + killCount;
                            return userName;
                        }
                        else {
                            set = userName[4].split(",");
                            srcCount = Integer.parseInt(set[1]);
                            if (srcCount < killCount && !set[0].equals(name)) {
                                userName[9] = userName[8];
                                userName[8] = userName[7];
                                userName[7] = userName[6];
                                userName[6] = userName[5];
                                userName[5] = userName[4];
                                userName[4] = name + "," + killCount;
                                return userName;
                            }
                            else if (userName[5].equals(" ")) {
                                userName[5] = name + "," + killCount;
                                return userName;
                            }
                            else {
                                set = userName[5].split(",");
                                srcCount = Integer.parseInt(set[1]);
                                if (srcCount < killCount && !set[0].equals(name)) {
                                    userName[9] = userName[8];
                                    userName[8] = userName[7];
                                    userName[7] = userName[6];
                                    userName[6] = userName[5];
                                    userName[5] = name + "," + killCount;
                                    return userName;
                                }
                                else if (userName[6].equals(" ")) {
                                    userName[6] = name + "," + killCount;
                                    return userName;
                                }
                                else {
                                    set = userName[6].split(",");
                                    srcCount = Integer.parseInt(set[1]);
                                    if (srcCount < killCount && !set[0].equals(name)) {
                                        userName[9] = userName[8];
                                        userName[8] = userName[7];
                                        userName[7] = userName[6];
                                        userName[6] = name + "," + killCount;
                                        return userName;
                                    }
                                    else if (userName[7].equals(" ")) {
                                        userName[7] = name + "," + killCount;
                                        return userName;
                                    }
                                    else {
                                        set = userName[7].split(",");
                                        srcCount = Integer.parseInt(set[1]);
                                        if (srcCount < killCount && !set[0].equals(name)) {
                                            userName[9] = userName[8];
                                            userName[8] = userName[7];
                                            userName[7] = name + "," + killCount;
                                            return userName;
                                        }
                                        else if (userName[8].equals(" ")) {
                                            userName[8] = name + "," + killCount;
                                            return userName;
                                        }
                                        else {
                                            set = userName[8].split(",");
                                            srcCount = Integer.parseInt(set[1]);
                                            if (srcCount < killCount && !set[0].equals(name)) {
                                                userName[9] = userName[8];
                                                userName[8] = name + "," + killCount;
                                                return userName;
                                            }
                                            else if (userName[9].equals(" ")) {
                                                userName[9] = name + "," + killCount;
                                                return userName;
                                            }
                                            else {
                                                set = userName[9].split(",");
                                                srcCount = Integer.parseInt(set[1]);
                                                if (srcCount < killCount && !set[0].equals(name)) {
                                                    userName[9] = name + "," + killCount;
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
