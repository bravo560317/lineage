package com.lineage.server.timecontroller.npc;

import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.Iterator;
import java.util.Map;
import com.lineage.server.model.L1Spawn;
import com.lineage.server.datatables.lock.SpawnBossReading;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcExistTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(NpcExistTimer.class);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Map<Integer, L1Spawn> BossSpawn = SpawnBossReading.get().get_bossSpawnTable();
            for (L1Spawn spawn : BossSpawn.values()) {
                if (spawn.get_existTime() > 0) {
                    long existTime = (long)(spawn.get_existTime() * 60 * 1000);
                    long spawnTime = spawn.get_nextSpawnTime().getTimeInMillis();
                    long nowTime = System.currentTimeMillis();
                    L1NpcInstance npc = spawn.getNpcTemp();
                    if (existTime + spawnTime < nowTime) {
                        npc.deleteMe();
                    }
                }
                Thread.sleep(50L);
            }
        }
        catch (Exception e) {
            NpcExistTimer._log.error("BOSS存在時間限制時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcExistTimer npcexistTimer = new NpcExistTimer();
            npcexistTimer.start();
        }
    }
}
