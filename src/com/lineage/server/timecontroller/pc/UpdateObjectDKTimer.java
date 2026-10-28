package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.WorldDragonKnight;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class UpdateObjectDKTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(UpdateObjectDKTimer.class);
    }
    
    public void start() {
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 350L, 350L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> allPc = WorldDragonKnight.get().all();
            if (allPc.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : allPc) {
                    if (UpdateObjectCheck.check(tgpc)) {
                        tgpc.updateObject();
                    }
                }
            }
        }
        catch (Exception e) {
            UpdateObjectDKTimer._log.error("Pc 可見物更新處理時間軸(龍騎)異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            UpdateObjectDKTimer objectDKTimer = new UpdateObjectDKTimer();
            objectDKTimer.start();
        }
    }
}
