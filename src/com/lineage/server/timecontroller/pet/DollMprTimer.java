package com.lineage.server.timecontroller.pet;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class DollMprTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(DollMprTimer.class);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : all) {
                    if (tgpc.get_doll_mpr_time_src() > 0 && checkErr(tgpc)) {
                        int newMp = tgpc.getCurrentMp() + tgpc.get_doll_mpr();
                        tgpc.setCurrentMp(newMp);
                        if (!tgpc.getDolls().isEmpty()) {
                            for (L1DollInstance doll : tgpc.getDolls().values()) {
                                doll.show_action(3);
                            }
                        }
                        Thread.sleep(50L);
                    }
                }
            }
        }
        catch (Exception e) {
            DollMprTimer._log.error("魔法娃娃處理時間軸(MPR)異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            DollMprTimer dollTimer = new DollMprTimer();
            dollTimer.start();
        }
    }
    
    private static boolean checkErr(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
            else if (tgpc.isTeleport()) {
                return false;
            }
            else if (!tgpc.getMpRegeneration()) {
                return false;
            }
            else if (tgpc.getCurrentMp() >= tgpc.getMaxMp()) {
                return false;
            }
            else {
                int newtime = tgpc.get_doll_mpr_time() - 1;
                tgpc.set_doll_mpr_time(newtime);
                if (newtime <= 0) {
                    tgpc.set_doll_mpr_time(tgpc.get_doll_mpr_time_src());
                    return true;
                }
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }
}
