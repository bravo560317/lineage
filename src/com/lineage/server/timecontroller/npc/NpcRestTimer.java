package com.lineage.server.timecontroller.npc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcRestTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(NpcRestTimer.class);
    }
    
    public void start() {
        int timeMillis = 5000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 5000L, 5000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1NpcInstance> allMob = WorldNpc.get().all();
            if (allMob.isEmpty()) {
                return;
            }
            else {
                for (L1NpcInstance npc : allMob) {
                    if (npc == null) {
                        continue;
                    }
                    else if (npc.get_stop_time() < 0) {
                        continue;
                    }
                    else {
                        int time = npc.get_stop_time();
                        npc.set_stop_time(time - 5);
                        if (npc.get_stop_time() <= 0) {
                            npc.set_stop_time(-1);
                            npc.setRest(false);
                        }
                        Thread.sleep(50L);
                    }
                }
            }
        }
        catch (Exception e) {
            NpcRestTimer._log.error("NPC動作暫停時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcRestTimer restTimer = new NpcRestTimer();
            restTimer.start();
        }
    }
}
