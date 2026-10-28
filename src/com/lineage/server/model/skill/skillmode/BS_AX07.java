package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class BS_AX07 extends SkillMode
{
    private static final int _addhp = 30;
    private static final int _addmp = 20;
    private static final int _addhpr = 1;
    private static final int _addmpr = 1;
    
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4417)) {
            srcpc.addMaxHp(30);
            srcpc.addMaxMp(20);
            srcpc.addHpr(1);
            srcpc.addMpr(1);
            srcpc.setSkillEffect(4417, integer * 1000);
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
        cha.addMaxHp(-30);
        cha.addMaxMp(-20);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addHpr(-1);
            pc.addMpr(-1);
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_MPUpdate(pc));
        }
    }
}
