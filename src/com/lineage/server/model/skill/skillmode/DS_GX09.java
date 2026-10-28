package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DS_GX09 extends SkillMode
{
    private static final int _addhp = 120;
    private static final int _addhit = 3;
    private static final int _addhpr = 6;
    private static final int _adddmg = 3;
    private static final int _addstr = 1;
    
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4509)) {
            srcpc.addMaxHp(120);
            srcpc.addHitup(3);
            srcpc.addHpr(6);
            srcpc.addDmgup(3);
            srcpc.addStr(1);
            srcpc.setSkillEffect(4509, integer * 1000);
            srcpc.sendPackets(new S_HPUpdate(srcpc.getCurrentHp(), srcpc.getMaxHp()));
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
        cha.addMaxHp(-120);
        cha.addHitup(-3);
        cha.addDmgup(-3);
        cha.addStr(-1);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addHpr(-6);
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_OwnCharStatus2(pc));
        }
    }
}
