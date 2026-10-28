package com.lineage.server.timecontroller.skill;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1EffectType;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.world.WorldEffect;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class EffectCubeShockTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(EffectCubeShockTimer.class);
    }
    
    public void start() {
        int timeMillis = 500;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 500L, 500L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1EffectInstance> allNpc = WorldEffect.get().all();
            if (allNpc.isEmpty()) {
                return;
            }
            else {
                for (L1EffectInstance effect : allNpc) {
                    if (effect.effectType() == L1EffectType.isCubeShock) {
                        EffectCubeExecutor.get().cubeBurn(effect);
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            EffectCubeShockTimer._log.error("Npc L1Effect幻術師技能(立方：衝擊)狀態送出時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            EffectCubeShockTimer cubeShockTimer = new EffectCubeShockTimer();
            cubeShockTimer.start();
        }
    }
}
