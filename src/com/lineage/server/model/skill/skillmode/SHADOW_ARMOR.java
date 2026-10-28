package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_SkillIconShield;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class SHADOW_ARMOR extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!cha.hasSkillEffect(99) && cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.setSkillEffect(99, integer * 1000);
            pc.addMr(5);
            pc.sendPackets(new S_SkillIconShield(3, integer));
            pc.sendPackets(new S_SPMR(pc));
        }
        return 0;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        return 0;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.sendPackets(new S_SkillIconShield(3, 0));
            pc.addMr(-5);
            pc.sendPackets(new S_SPMR(pc));
        }
    }
}
