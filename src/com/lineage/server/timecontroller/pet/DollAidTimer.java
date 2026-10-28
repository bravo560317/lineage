package com.lineage.server.timecontroller.pet;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.world.WorldDoll;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class DollAidTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(DollAidTimer.class);
    }
    
    public void start() {
        int timeMillis = 10000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1DollInstance> allDoll = WorldDoll.get().all();
            if (allDoll.isEmpty()) {
                return;
            }
            else {
                for (L1DollInstance doll : allDoll) {
                    if (doll.is_power_doll()) {
                        doll.startDollSkill();
                    }
                    Thread.sleep(50L);
                }
            }
        }
        catch (Exception e) {
            DollAidTimer._log.error("魔法娃娃處理時間軸(輔助技能)異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            DollAidTimer dollTimer = new DollAidTimer();
            dollTimer.start();
        }
    }
}
