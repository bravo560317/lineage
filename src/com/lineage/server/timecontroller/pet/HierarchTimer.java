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

public class HierarchTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(HierarchTimer.class);
    }
    
    public void start() {
        int timeMillis = 10000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1HierarchInstance> allHierarch = WorldHierarch.get().all();
            if (allHierarch.isEmpty()) {
                return;
            }
            else {
                for (L1HierarchInstance l1HierarchInstance : allHierarch) {}
            }
        }
        catch (Exception e) {
            HierarchTimer._log.error("祭司處理時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            HierarchTimer hierarch_Timer = new HierarchTimer();
            hierarch_Timer.start();
        }
    }
    
    private static void outHierarch(L1HierarchInstance hierarch) {
        try {
            if (hierarch != null) {
                if (hierarch._destroyed) {
                    return;
                }
                else {
                    hierarch.deleteHierarch();
                }
            }
        }
        catch (Exception e) {
            HierarchTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
