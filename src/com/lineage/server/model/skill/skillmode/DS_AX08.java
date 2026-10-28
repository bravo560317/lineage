package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DS_AX08 extends SkillMode
{
    private static final int _addhp = 40;
    private static final int _addmp = 30;
    private static final int _addhitbow = 2;
    private static final int _addhpr = 2;
    private static final int _addmpr = 2;
    private static final int _adddmgbow = 1;
    
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4518)) {
            srcpc.addMaxHp(40);
            srcpc.addMaxMp(30);
            srcpc.addBowHitup(2);
            srcpc.addHpr(2);
            srcpc.addMpr(2);
            srcpc.addBowDmgup(1);
            srcpc.setSkillEffect(4518, integer * 1000);
            srcpc.sendPackets(new S_HPUpdate(srcpc.getCurrentHp(), srcpc.getMaxHp()));
            srcpc.sendPackets(new S_MPUpdate(srcpc));
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
        cha.addMaxHp(-40);
        cha.addMaxMp(-30);
        cha.addBowHitup(-2);
        cha.addBowDmgup(-1);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addHpr(-2);
            pc.addMpr(-2);
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_MPUpdate(pc));
        }
    }
}
