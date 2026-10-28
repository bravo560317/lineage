package com.lineage.server.timecontroller.pet;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.world.WorldPet;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class PetMprTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static int _time;
    
    static {
        _log = LogFactory.getLog(PetMprTimer.class);
        PetMprTimer._time = 0;
    }
    
    public void start() {
        PetMprTimer._time = 0;
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            ++PetMprTimer._time;
            Collection<L1PetInstance> allPet = WorldPet.get().all();
            if (allPet.isEmpty()) {
                return;
            }
            else {
                for (L1PetInstance pet : allPet) {
                    if (MprPet.mpUpdate(pet, PetMprTimer._time)) {
                        Thread.sleep(5L);
                    }
                }
            }
        }
        catch (Exception e) {
            PetMprTimer._log.error("Pet MP自然回復時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            PetMprTimer petMprTimer = new PetMprTimer();
            petMprTimer.start();
        }
    }
}
