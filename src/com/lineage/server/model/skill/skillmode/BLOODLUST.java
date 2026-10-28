package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class BLOODLUST extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        L1PcInstance pc = (L1PcInstance)cha;
        L1BuffUtil.braveStart(pc);
        pc.setSkillEffect(186, integer * 1000);
        pc.setBraveSpeed(1);
        pc.sendPackets(new S_SkillBrave(pc.getId(), 1, integer));
        pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 1, 0));
        return 0;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        return 0;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
        cha.setBraveSpeed(0);
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
        }
    }
}
