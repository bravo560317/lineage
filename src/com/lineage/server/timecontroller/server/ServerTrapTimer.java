package com.lineage.server.timecontroller.server;

import java.util.HashMap;
import com.lineage.server.model.Instance.L1TrapInstance;
import com.lineage.server.world.WorldTrap;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerTrapTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ServerTrapTimer.class);
    }
    
    public void start() {
        int timeMillis = 5000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 5000L, 5000L);
    }
    
    @Override
    public void run() {
        try {
            HashMap allTrap = WorldTrap.get().map();
            if (allTrap.isEmpty()) {
                return;
            }
            else {
                Object[] array;
                for (int length = (array = allTrap.values().toArray()).length, i = 0; i < length; ++i) {
                    Object iter = array[i];
                    L1TrapInstance trap = (L1TrapInstance)iter;
                    if (!trap.isEnable()) {
                        trap.set_stop(trap.get_stop() + 1);
                        if (trap.get_stop() >= trap.getSpan()) {
                            trap.resetLocation();
                            Thread.sleep(50L);
                        }
                    }
                }
            }
        }
        catch (Exception io) {
            ServerTrapTimer._log.error("陷阱召喚處理時間軸異常重啟", io);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerTrapTimer trapTimer = new ServerTrapTimer();
            trapTimer.start();
        }
    }
}
