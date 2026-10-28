package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class BS_ASX05 extends SkillMode
{
    private static final int _addac = -1;
    private static final int _addmr = 10;
    
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4435)) {
            srcpc.addAc(-1);
            srcpc.addMr(10);
            srcpc.setSkillEffect(4435, integer * 1000);
            srcpc.sendPackets(new S_OwnCharAttrDef(srcpc));
            srcpc.sendPackets(new S_SPMR(srcpc));
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
        cha.addAc(1);
        cha.addMr(-10);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.sendPackets(new S_OwnCharAttrDef(pc));
            pc.sendPackets(new S_SPMR(pc));
        }
    }
}
