package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class ADLV80_2 extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4010)) {
            srcpc.addHpr(8);
            srcpc.addMpr(1);
            srcpc.addMaxHp(80);
            srcpc.addMaxMp(10);
            srcpc.addBowHitup(6);
            srcpc.addBowDmgup(3);
            srcpc.addWater(30);
            srcpc.addAc(-8);
            srcpc.setSkillEffect(4010, integer * 1000);
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
        cha.addMaxHp(-80);
        cha.addMaxMp(-10);
        cha.addBowHitup(-6);
        cha.addBowDmgup(-3);
        cha.addWater(-30);
        cha.addAc(8);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addHpr(-8);
            pc.addMpr(-1);
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_MPUpdate(pc));
            pc.sendPackets(new S_OwnCharAttrDef(pc));
        }
    }
}
