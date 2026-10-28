package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DRAGON_BLOOD3 extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(6799)) {
            srcpc.addSp(1);
            srcpc.addMr(1);
            srcpc.addFire(50);
            srcpc.sendPackets(new S_SPMR(srcpc));
            srcpc.sendPackets(new S_OwnCharAttrDef(srcpc));
            srcpc.setSkillEffect(6799, integer * 1000);
            srcpc.sendPackets(new S_PacketBox(100, 88, integer / 60));
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
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addSp(-1);
            pc.addMr(-1);
            pc.addFire(-50);
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharAttrDef(pc));
        }
    }
}
