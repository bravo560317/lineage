package com.lineage.server.timecontroller.npc;

import java.util.HashMap;
import com.lineage.server.model.Instance.L1BowInstance;
import com.lineage.server.world.WorldBow;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcBowTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(NpcBowTimer.class);
    }
    
    public void start() {
        int timeMillis = 1500;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1500L, 1500L);
    }
    
    @Override
    public void run() {
        try {
            HashMap<Integer, L1BowInstance> allBow = WorldBow.get().map();
            if (allBow.isEmpty()) {
                return;
            }
            else {
                Object[] array;
                for (int length = (array = allBow.values().toArray()).length, i = 0; i < length; ++i) {
                    Object iter = array[i];
                    L1BowInstance bowNpc = (L1BowInstance)iter;
                    if (bowNpc != null) {
                        if (!bowNpc.isDead()) {
                            if (bowNpc.get_start() && bowNpc.checkPc()) {
                                bowNpc.atkTrag();
                            }
                            Thread.sleep(50L);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            NpcBowTimer._log.error("固定攻擊器時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcBowTimer bow = new NpcBowTimer();
            bow.start();
        }
    }
}
