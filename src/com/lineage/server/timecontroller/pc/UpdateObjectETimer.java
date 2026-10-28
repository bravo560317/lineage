package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.WorldElf;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class UpdateObjectETimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(UpdateObjectETimer.class);
    }
    
    public void start() {
        int timeMillis = 350;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 350L, 350L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> allPc = WorldElf.get().all();
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
            UpdateObjectETimer._log.error("Pc 可見物更新處理時間軸(精靈)異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            UpdateObjectETimer objectETimer = new UpdateObjectETimer();
            objectETimer.start();
        }
    }
}
