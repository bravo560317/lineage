package com.lineage.server.model.skill;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SkillDelay
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1SkillDelay.class);
    }
    
    private L1SkillDelay() {
    }
    
    public static void onSkillUse(L1Character cha, int time) {
        try {
            cha.setSkillDelay(true);
            GeneralThreadPool.get().schedule(new SkillDelayTimer(cha), (long)time);
        }
        catch (Exception e) {
            L1SkillDelay._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    static class SkillDelayTimer implements Runnable
    {
        private L1Character _cha;
        
        public SkillDelayTimer(L1Character cha) {
            this._cha = cha;
        }
        
        @Override
        public void run() {
            this.stopDelayTimer();
        }
        
        public void stopDelayTimer() {
            this._cha.setSkillDelay(false);
        }
    }
}
