package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class ADLV80_3 extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4018)) {
            srcpc.addDex(5);
            srcpc.addBowHitup(7);
            srcpc.addBowDmgup(5);
            srcpc.addHpr(10);
            srcpc.addMpr(3);
            srcpc.addMaxHp(100);
            srcpc.addMaxMp(40);
            srcpc.addWind(30);
            srcpc.addMr(15);
            srcpc.setSkillEffect(4018, integer * 1000);
            srcpc.sendPackets(new S_SPMR(srcpc));
            srcpc.sendPackets(new S_OwnCharStatus2(srcpc));
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
        cha.addDex(-5);
        cha.addMaxHp(-100);
        cha.addMaxMp(-40);
        cha.addBowHitup(-7);
        cha.addBowDmgup(-5);
        cha.addWind(-30);
        cha.addMr(-15);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addHpr(-10);
            pc.addMpr(-3);
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_MPUpdate(pc));
            pc.sendPackets(new S_OwnCharAttrDef(pc));
        }
    }
}
