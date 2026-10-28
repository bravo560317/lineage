package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DS_WX07 extends SkillMode
{
    private static final int _addmp = 55;
    private static final int _addmpr = 2;
    private static final int _addsp = 1;
    
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(4527)) {
            srcpc.addMaxMp(55);
            srcpc.addMpr(2);
            srcpc.addSp(1);
            srcpc.setSkillEffect(4527, integer * 1000);
            srcpc.sendPackets(new S_MPUpdate(srcpc));
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
        cha.addMaxMp(-55);
        cha.addSp(-1);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addMpr(-2);
            pc.sendPackets(new S_MPUpdate(pc));
            pc.sendPackets(new S_SPMR(pc));
        }
    }
}
