package com.lineage.server.timecontroller.npc;

import java.util.Iterator;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcDeadTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    public static final Map<L1NpcInstance, Integer> MAP;
    
    static {
        _log = LogFactory.getLog(NpcDeadTimer.class);
        MAP = new ConcurrentHashMap(100);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    public static void put(L1NpcInstance npc, int time) {
        NpcDeadTimer.MAP.put(npc, new Integer(time));
    }
    
    @Override
    public void run() {
        try {
            if (!NpcDeadTimer.MAP.isEmpty()) {
                for (L1NpcInstance key : NpcDeadTimer.MAP.keySet()) {
                    if (!check(key)) {
                        NpcDeadTimer.MAP.remove(key);
                    }
                    else {
                        Integer value = NpcDeadTimer.MAP.get(key);
                        value = (Integer)(value - 1);
                        NpcDeadTimer.MAP.put(key, value);
                        if (value < 1) {
                            delMob(key);
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            NpcDeadTimer._log.error("NPC死亡移除时间轴异常重启", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcDeadTimer npcDeadTimer = new NpcDeadTimer();
            npcDeadTimer.start();
        }
    }
    
    private static boolean check(L1NpcInstance npc) {
        try {
            if (npc == null) {
                return false;
            }
            else if (npc.getMaxHp() <= 0) {
                return false;
            }
            else if (!npc.isDead()) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static void delMob(L1NpcInstance npc) {
        NpcDeadTimer.MAP.remove(npc);
        npc.deleteMe();
    }
}
