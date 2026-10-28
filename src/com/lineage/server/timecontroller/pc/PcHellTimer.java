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

public class PcHellTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(PcHellTimer.class);
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
                    int time = tgpc.getHellTime();
                    if (time <= 0) {
                        continue;
                    }
                    else {
                        --time;
                        if (!tgpc.isDead()) {
                            check(tgpc, time);
                        }
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            PcHellTimer._log.error("PC 地獄模式處理時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            PcHellTimer hellTimer = new PcHellTimer();
            hellTimer.start();
        }
    }
    
    private static void check(L1PcInstance tgpc, Integer time) {
        if (time > 0) {
            tgpc.setHellTime(time);
        }
        else {
            tgpc.setHellTime(0);
            if (tgpc.getNetConnection() != null) {
                outPc(tgpc);
            }
        }
    }
    
    private static void outPc(L1PcInstance tgpc) {
        try {
            if (tgpc != null) {
                tgpc.endHell();
            }
        }
        catch (Exception e) {
            PcHellTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
