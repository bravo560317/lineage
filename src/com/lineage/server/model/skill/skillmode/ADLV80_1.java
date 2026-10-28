package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class ADLV80_1 extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4009)) {
            srcpc.addHpr(3);
            srcpc.addMpr(3);
            srcpc.addMaxHp(100);
            srcpc.addMaxMp(50);
            srcpc.addEarth(30);
            srcpc.addDmgup(1);
            srcpc.addBowDmgup(1);
            srcpc.addHitup(5);
            srcpc.addBowHitup(5);
            srcpc.addWeightReduction(4);
            srcpc.setSkillEffect(4009, integer * 1000);
            srcpc.sendPackets(new S_HPUpdate(srcpc.getCurrentHp(), srcpc.getMaxHp()));
            srcpc.sendPackets(new S_MPUpdate(srcpc));
            srcpc.sendPackets(new S_OwnCharAttrDef(srcpc));
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
        cha.addMaxHp(-100);
        cha.addMaxMp(-50);
        cha.addEarth(-30);
        cha.addDmgup(-1);
        cha.addBowDmgup(-1);
        cha.addHitup(-5);
        cha.addBowHitup(-5);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addHpr(-3);
            pc.addMpr(-3);
            pc.addWeightReduction(-4);
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_MPUpdate(pc));
            pc.sendPackets(new S_OwnCharAttrDef(pc));
        }
    }
}
