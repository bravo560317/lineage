package com.lineage.server.model.skill;

import com.lineage.server.templates.L1MobSkill;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.datatables.MobSkillTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SkillDelayforMob
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1SkillDelayforMob.class);
    }
    
    public static void onSkillUse(L1NpcInstance npc, int time, int idx) {
        try {
            L1MobSkill mobSkillTemplate = MobSkillTable.getInstance().getTemplate(npc.getNpcTemplate().get_npcId());
            mobSkillTemplate.setSkillDelayIdx(idx, true);
            GeneralThreadPool.get().schedule(new L1SkillDelayforMob.SkillDelayTimer(npc, idx), (long)time);
        }
        catch (Exception e) {
            L1SkillDelayforMob._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    static class SkillDelayTimer implements Runnable
    {
        private L1NpcInstance _npc;
        private int _idx;
        
        public SkillDelayTimer(L1NpcInstance npc, int idx) {
            this._npc = npc;
            this._idx = idx;
        }
        
        @Override
        public void run() {
            this.stopDelayTimer(this._idx);
        }
        
        public void stopDelayTimer(int idx) {
            L1MobSkill mobSkillTemplate = MobSkillTable.getInstance().getTemplate(this._npc.getNpcTemplate().get_npcId());
            mobSkillTemplate.setSkillDelayIdx(idx, false);
        }
    }
}
