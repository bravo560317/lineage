package com.lineage.server.timecontroller.npc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.WorldMob;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.text.SimpleDateFormat;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcHprTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static SimpleDateFormat format;
    
    static {
        _log = LogFactory.getLog(NpcHprTimer.class);
        NpcHprTimer.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    public void start() {
        int timeMillis = 1200;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1200L, 1200L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1MonsterInstance> allMob = WorldMob.get().all();
            if (allMob.isEmpty()) {
                NpcHprTimer._log.error("Npc HP自然回復時間軸異常重啟 allMob.isEmpty()");
                return;
            }
            else {
                for (L1MonsterInstance mob : allMob) {
                    if (mob.isHpR()) {
                        hpUpdate(mob);
                    }
                }
            }
        }
        catch (Exception e) {
            NpcHprTimer._log.error("Npc HP自然回復時間軸異常重啟", e);
            if (this._timer != null) {
                GeneralThreadPool.get().cancel(this._timer, false);
            }
            NpcHprTimer npcHprTimer = new NpcHprTimer();
            npcHprTimer.start();
        }
    }
    
    private static void hpUpdate(L1MonsterInstance mob) {
        int hprInterval = mob.getNpcTemplate().get_hprinterval();
        if (hprInterval <= 0) {
            hprInterval = 10;
        }
        long nowtime = System.currentTimeMillis() / 1000L;
        long LastHprTime = mob.getLastHprTime();
        if (nowtime - LastHprTime >= (long)hprInterval) {
            int hpr = mob.getNpcTemplate().get_hpr();
            if (hpr <= 0) {
                hpr = 2;
            }
            hprInterval(mob, hpr);
            mob.setLastHprTime(nowtime);
        }
    }
    
    private static void hprInterval(L1MonsterInstance mob, int hpr) {
        try {
            if (mob.isHpRegenerationX()) {
                mob.setCurrentHp(mob.getCurrentHp() + hpr);
            }
        }
        catch (Exception e) {
            NpcHprTimer._log.error("Npc 執行回復HP發生異常", e);
            mob.deleteMe();
        }
    }
}
