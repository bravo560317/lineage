package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DS_WX09 extends SkillMode
{
    private static final int _addmp = 70;
    private static final int _addmpr = 5;
    private static final int _addsp = 3;
    private static final int _addint = 1;
    
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4529)) {
            srcpc.addMaxMp(70);
            srcpc.addMpr(5);
            srcpc.addSp(3);
            srcpc.addInt(1);
            srcpc.setSkillEffect(4529, integer * 1000);
            srcpc.sendPackets(new S_MPUpdate(srcpc));
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
        cha.addMaxMp(-70);
        cha.addSp(-3);
        cha.addInt(-1);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addMpr(-5);
            pc.sendPackets(new S_MPUpdate(pc));
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));
        }
    }
}
