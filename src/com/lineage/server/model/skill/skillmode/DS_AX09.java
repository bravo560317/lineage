package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DS_AX09 extends SkillMode
{
    private static final int _addhp = 60;
    private static final int _addmp = 43;
    private static final int _addhitbow = 3;
    private static final int _addhpr = 3;
    private static final int _addmpr = 3;
    private static final int _adddmgbow = 3;
    private static final int _adddex = 1;
    
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4519)) {
            srcpc.addMaxHp(60);
            srcpc.addMaxMp(43);
            srcpc.addBowHitup(3);
            srcpc.addHpr(3);
            srcpc.addMpr(3);
            srcpc.addBowDmgup(3);
            srcpc.addDex(1);
            srcpc.setSkillEffect(4519, integer * 1000);
            srcpc.sendPackets(new S_HPUpdate(srcpc.getCurrentHp(), srcpc.getMaxHp()));
            srcpc.sendPackets(new S_MPUpdate(srcpc));
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
        cha.addMaxHp(-60);
        cha.addMaxMp(-43);
        cha.addBowHitup(-3);
        cha.addBowDmgup(-3);
        cha.addDex(-1);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addHpr(-3);
            pc.addMpr(-3);
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_MPUpdate(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));
        }
    }
}
