package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class AWAKEN_VALAKAS extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (!srcpc.hasSkillEffect(195)) {
            srcpc.addHitup(5);
            srcpc.addRegistStun(10);
            srcpc.setSkillEffect(195, integer * 1000);
            srcpc.sendPackets(new S_OwnCharStatus(srcpc));
        }
        srcpc.sendPacketsX8(new S_SkillSound(cha.getId(), 6922));
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
            pc.addHitup(-5);
            pc.addRegistStun(-10);
            pc.sendPackets(new S_OwnCharStatus(pc));
        }
    }
}
