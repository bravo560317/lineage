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

public class ExpTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ExpTimer.class);
    }
    
    public void start() {
        int timeMillis = 500;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 500L, 500L);
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
                    if (check(tgpc)) {
                        tgpc.onChangeExp();
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            ExpTimer._log.error("EXP更新處理時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            ExpTimer expTimer = new ExpTimer();
            expTimer.start();
        }
    }
    
    private static boolean check(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
            else if (tgpc.isTeleport()) {
                return false;
            }
            else if (tgpc.getExp() == tgpc.getExpo()) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
