package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DRAGON_BLOOD2 extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(6798)) {
            srcpc.addHpr(3);
            srcpc.addMpr(1);
            srcpc.addWind(50);
            srcpc.sendPackets(new S_OwnCharAttrDef(srcpc));
            srcpc.setSkillEffect(6798, integer * 1000);
            srcpc.sendPackets(new S_PacketBox(100, 85, integer / 60));
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
            pc.addHpr(-3);
            pc.addMpr(-1);
            pc.addWind(-50);
            pc.sendPackets(new S_OwnCharAttrDef(pc));
        }
    }
}
