package com.lineage.server.timecontroller.skill;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.WorldDragonKnight;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class Skill_Awake_Timer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(Skill_Awake_Timer.class);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> allPc = WorldDragonKnight.get().all();
            if (allPc.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : allPc) {
                    if (tgpc.isDead()) {
                        continue;
                    }
                    else if (!tgpc.isMpReductionActiveByAwake()) {
                        continue;
                    }
                    else {
                        int time = tgpc.get_awakeMprTime();
                        if (--time <= 0) {
                            decreaseMp(tgpc);
                            tgpc.set_awakeMprTime(4);
                        }
                        else {
                            tgpc.set_awakeMprTime(time);
                        }
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            Skill_Awake_Timer._log.error("龍騎士覺醒技能MP自然減少處理異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            Skill_Awake_Timer awake_Timer = new Skill_Awake_Timer();
            awake_Timer.start();
        }
    }
    
    private static void decreaseMp(L1PcInstance tgpc) {
        try {
            int newMp = Math.max(tgpc.getCurrentMp() - 8, 0);
            tgpc.setCurrentMp(newMp);
        }
        catch (Throwable e) {
            Skill_Awake_Timer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
