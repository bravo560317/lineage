package com.lineage.server.timecontroller.npc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.WorldMob;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcMprTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(NpcMprTimer.class);
    }
    
    public void start() {
        int timeMillis = 5000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 5000L, 5000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1MonsterInstance> allMob = WorldMob.get().all();
            if (allMob.isEmpty()) {
                return;
            }
            else {
                for (L1MonsterInstance mob : allMob) {
                    if (mob.isMpR()) {
                        mpUpdate(mob);
                        Thread.sleep(50L);
                    }
                }
            }
        }
        catch (Exception e) {
            NpcMprTimer._log.error("Npc MP自然回復時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcMprTimer npcMprTimer = new NpcMprTimer();
            npcMprTimer.start();
        }
    }
    
    private static void mpUpdate(L1MonsterInstance mob) {
        int mprInterval = mob.getNpcTemplate().get_mprinterval();
        if (mprInterval <= 0) {
            mprInterval = 10;
        }
        long nowtime = System.currentTimeMillis() / 1000L;
        long LastMprTime = mob.getLastMprTime();
        if (nowtime - LastMprTime >= (long)mprInterval) {
            int mpr = mob.getNpcTemplate().get_mpr();
            if (mpr <= 0) {
                mpr = 2;
            }
            mprInterval(mob, mpr);
            mob.setLastMprTime(nowtime);
        }
    }
    
    private static void mprInterval(L1MonsterInstance mob, int mpr) {
        try {
            if (mob.isMpRegenerationX()) {
                mob.setCurrentMp(mob.getCurrentMp() + mpr);
            }
        }
        catch (Exception e) {
            NpcMprTimer._log.error("Npc 執行回復MP發生異常", e);
            mob.deleteMe();
        }
    }
}
