package com.lineage.server.model.skill;

import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Character;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;

public class L1SkillTimerTimerImpl implements L1SkillTimer, Runnable
{
    private static final Log _log;
    private ScheduledFuture<?> _future;
    private final L1Character _cha;
    private final int _skillId;
    private int _remainingTime;
    
    static {
        _log = LogFactory.getLog(L1SkillTimerTimerImpl.class);
    }
    
    public L1SkillTimerTimerImpl(L1Character cha, int skillId, int timeMillis) {
        this._cha = cha;
        this._skillId = skillId;
        this._remainingTime = timeMillis / 1000;
    }
    
    @Override
    public void run() {
        int remainingTime = this._remainingTime - 1;
        this._remainingTime = remainingTime;
        if (remainingTime <= 0) {
            this._cha.removeSkillEffect(this._skillId);
        }
    }
    
    @Override
    public void begin() {
        this._future = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void end() {
        this.kill();
        try {
            L1SkillStop.stopSkill(this._cha, this._skillId);
        }
        catch (Throwable e) {
            L1SkillTimerTimerImpl._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void kill() {
        try {
            if (this._future != null) {
                this._future.cancel(false);
                this._future = null;
            }
        }
        catch (Throwable e) {
            L1SkillTimerTimerImpl._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public int getRemainingTime() {
        return this._remainingTime;
    }
    
    @Override
    public void setRemainingTime(int remainingTime) {
        this._remainingTime = remainingTime;
    }
}
