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

public class NpcSpawnBossTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    public static final Map<L1NpcInstance, Long> MAP;
    
    static {
        _log = LogFactory.getLog(NpcSpawnBossTimer.class);
        MAP = new ConcurrentHashMap();
    }
    
    public void start() {
        int timeMillis = 10000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 10000L, 10000L);
    }
    
    @Override
    public void run() {
        try {
            if (NpcSpawnBossTimer.MAP.isEmpty()) {
                return;
            }
            else {
                for (L1NpcInstance npc : NpcSpawnBossTimer.MAP.keySet()) {
                    Long time = NpcSpawnBossTimer.MAP.get(npc);
                    long t = time - 60L;
                    if (time > 0L) {
                        NpcSpawnBossTimer.MAP.put(npc, t);
                    }
                    else {
                        spawn(npc);
                        NpcSpawnBossTimer.MAP.remove(npc);
                    }
                    Thread.sleep(50L);
                }
            }
        }
        catch (Exception e) {
            NpcSpawnBossTimer._log.error("NPC(BOSS)召喚時間時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcSpawnBossTimer bossTimer = new NpcSpawnBossTimer();
            bossTimer.start();
        }
    }
    
    private static void spawn(L1NpcInstance npc) {
        try {
            npc.getSpawn().executeSpawnTask(npc.getSpawnNumber(), npc.getId());
        }
        catch (Exception e) {
            NpcSpawnBossTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
