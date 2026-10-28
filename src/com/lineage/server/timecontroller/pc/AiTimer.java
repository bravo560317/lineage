package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class AiTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(AiTimer.class);
    }
    
    public void start() {
        int timeMillis = 20000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 20000L, 20000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance l1PcInstance : all) {}
            }
        }
        catch (Exception localException) {
            AiTimer._log.error("ai驗證處理時間軸異常重啟", localException);
            PcOtherThreadPool.get().cancel(this._timer, false);
            Object localObject = new AiTimer();
            ((AiTimer)localObject).start();
        }
    }
}
