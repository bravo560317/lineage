package com.lineage.server.timecontroller.pet;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.world.WorldSummons;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class SummonMprTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static int _time;
    
    static {
        _log = LogFactory.getLog(SummonMprTimer.class);
        SummonMprTimer._time = 0;
    }
    
    public void start() {
        SummonMprTimer._time = 0;
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            ++SummonMprTimer._time;
            Collection<L1SummonInstance> allPet = WorldSummons.get().all();
            if (allPet.isEmpty()) {
                return;
            }
            else {
                for (L1SummonInstance summon : allPet) {
                    if (MprPet.mpUpdate(summon, SummonMprTimer._time)) {
                        Thread.sleep(5L);
                    }
                }
            }
        }
        catch (Exception e) {
            SummonMprTimer._log.error("Summon MP自然回復時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            SummonMprTimer summonMprTimer = new SummonMprTimer();
            summonMprTimer.start();
        }
    }
}
