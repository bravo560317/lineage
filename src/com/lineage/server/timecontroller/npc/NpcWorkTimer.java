package com.lineage.server.timecontroller.npc;

import java.util.Iterator;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcWorkTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static final Map<L1NpcInstance, Integer> _map;
    
    static {
        _log = LogFactory.getLog(NpcWorkTimer.class);
        _map = new HashMap();
    }
    
    public static void put(L1NpcInstance npc, Integer time) {
        NpcWorkTimer._map.put(npc, time);
    }
    
    public void start() {
        int timeMillis = 2000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 2000L, 2000L);
    }
    
    @Override
    public void run() {
        try {
            if (NpcWorkTimer._map.isEmpty()) {
                return;
            }
            else {
                for (L1NpcInstance npc : NpcWorkTimer._map.keySet()) {
                    Integer time = NpcWorkTimer._map.get(npc);
                    time = (Integer)(time - 2);
                    if (time > 0) {
                        NpcWorkTimer._map.put(npc, time);
                    }
                    else {
                        startWork(npc);
                    }
                    Thread.sleep(50L);
                }
            }
        }
        catch (Exception e) {
            NpcWorkTimer._log.error("NPC工作時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcWorkTimer workTimer = new NpcWorkTimer();
            workTimer.start();
        }
    }
    
    private static void startWork(L1NpcInstance npc) {
        try {
            if (npc != null) {
                int time = npc.WORK.workTime();
                if (time != 0) {
                    npc.WORK.work(npc);
                    NpcWorkTimer._map.put(npc, time);
                }
            }
        }
        catch (Exception e) {
            NpcWorkTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
