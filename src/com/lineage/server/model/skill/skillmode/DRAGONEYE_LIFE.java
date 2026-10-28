package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class DRAGONEYE_LIFE extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(6687)) {
            srcpc.addDmgup(2);
            srcpc.setSkillEffect(6687, integer * 1000);
            srcpc.add_dodge(1);
            srcpc.addOriginalMagicCritical(2);
            srcpc.sendPackets(new S_PacketBoxIcon1(true, srcpc.get_dodge()));
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
        cha.addDmgup(-2);
        cha.add_dodge(-1);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.addOriginalMagicCritical(-2);
            pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
        }
    }
}
