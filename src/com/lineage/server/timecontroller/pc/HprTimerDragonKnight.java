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

public class HprTimerDragonKnight extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(HprTimerDragonKnight.class);
    }
    
    public void start() {
        int timeMillis = 3000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 3000L, 3000L);
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
                    HprExecutor hpr = HprExecutor.get();
                    if (hpr.check(tgpc)) {
                        hpr.checkRegenHp(tgpc);
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            HprTimerDragonKnight._log.error("Pc(龍騎) HP自然回復時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            HprTimerDragonKnight hprDK = new HprTimerDragonKnight();
            hprDK.start();
        }
    }
}
