package com.lineage.data.npc;

import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_NewUser extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_NewUser();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.hasSkillEffect(79)) {
            return;
        }
        else {
            int[] allBuffSkill = { 79, 48, 42, 26 };
            for (int i = 0; i < allBuffSkill.length; ++i) {
                int skillid = allBuffSkill[i];
                this.startSkill(pc, npc, skillid);
            }
            return;
        }
    }
    
    private void startSkill(L1PcInstance pc, L1NpcInstance npc, int skillid) {
        int objid = pc.getId();
        int x = pc.getX();
        int y = pc.getY();
        L1SkillUse skillUse = new L1SkillUse();
        skillUse.handleCommands(pc, skillid, objid, x, y, 0, 3, npc);
    }
}
