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

public class PcGhostTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(PcGhostTimer.class);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : all) {
                    if (tgpc.isActived() && tgpc.getAutoNow_AttackSkillDelay() > 0) {
                        tgpc.setAutoNow_AttackSkillDelay(tgpc.getAutoNow_AttackSkillDelay() - 1);
                    }
                }
            }
        }
        catch (Exception e) {
            PcGhostTimer._log.error("PC 鬼魂模式處理時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            PcGhostTimer pcHprTimer = new PcGhostTimer();
            pcHprTimer.start();
        }
    }
}
