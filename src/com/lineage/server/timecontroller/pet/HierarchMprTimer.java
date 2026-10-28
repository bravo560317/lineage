package com.lineage.server.timecontroller.pet;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1HierarchInstance;
import com.lineage.server.world.WorldHierarch;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class HierarchMprTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static int _time;
    
    static {
        _log = LogFactory.getLog(HierarchMprTimer.class);
        HierarchMprTimer._time = 0;
    }
    
    public void start() {
        HierarchMprTimer._time = 0;
        int timeMillis = 30000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            ++HierarchMprTimer._time;
            Collection<L1HierarchInstance> allHierarch = WorldHierarch.get().all();
            if (allHierarch.isEmpty()) {
                return;
            }
            else {
                for (L1HierarchInstance hierarch : allHierarch) {
                    if (HMprPet.mpUpdate(hierarch, HierarchMprTimer._time)) {
                        Thread.sleep(5L);
                    }
                }
            }
        }
        catch (Exception e) {
            HierarchMprTimer._log.error("祭司自然回復時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            HierarchMprTimer hierarchMprTimer = new HierarchMprTimer();
            hierarchMprTimer.start();
        }
    }
}
