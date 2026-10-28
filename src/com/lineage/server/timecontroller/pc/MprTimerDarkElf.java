package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.WorldDarkelf;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class MprTimerDarkElf extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(MprTimerDarkElf.class);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> allPc = WorldDarkelf.get().all();
            if (allPc.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : allPc) {
                    MprExecutor mpr = MprExecutor.get();
                    if (mpr.check(tgpc)) {
                        mpr.checkRegenMp(tgpc);
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            MprTimerDarkElf._log.error("Pc(黑妖) MP自然回復時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            MprTimerDarkElf mprDarkElf = new MprTimerDarkElf();
            mprDarkElf.start();
        }
    }
}
