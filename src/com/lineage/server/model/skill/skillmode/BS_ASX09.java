package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class BS_ASX09 extends SkillMode
{
    private static final int _addac = -5;
    private static final int _addmr = 20;
    private static final int _adddmgdown = 3;
    private static final int _addcon = 1;
    
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4439)) {
            srcpc.addAc(-5);
            srcpc.addMr(20);
            srcpc.addDamageReductionByArmor(3);
            srcpc.addCon(1);
            srcpc.setSkillEffect(4439, integer * 1000);
            srcpc.sendPackets(new S_OwnCharAttrDef(srcpc));
            srcpc.sendPackets(new S_SPMR(srcpc));
            srcpc.sendPackets(new S_OwnCharStatus2(srcpc));
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
        cha.addAc(5);
        cha.addMr(-20);
        cha.addCon(-1);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addDamageReductionByArmor(-3);
            pc.sendPackets(new S_OwnCharAttrDef(pc));
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));
        }
    }
}
