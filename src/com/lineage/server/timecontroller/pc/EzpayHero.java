package com.lineage.server.timecontroller.pc;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Collection;
import com.lineage.data.event.RankingSet;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class EzpayHero extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(EzpayHero.class);
    }
    
    public void start() {
        int timeMillis = 3600000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 3600000L, 3600000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) {
                return;
            }
            else {
                RankingSet.getRankingKill();
            }
        }
        catch (Exception e) {
            EzpayHero._log.error("贊助排行處理時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            EzpayHero lawfulTimer = new EzpayHero();
            lawfulTimer.start();
        }
    }
}
