package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_PacketBoxDk;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class FOE_SLAYER extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        srcpc.setFoeSlayer(true);
        for (int i = 0; i < 3; ++i) {
            cha.onAction(srcpc);
            if (i == 2) {
                srcpc.setFoeSlayer(false);
                srcpc.set_weaknss(0, 0L);
                srcpc.sendPackets(new S_PacketBoxDk(0));
            }
        }
        srcpc.sendPacketsAll(new S_SkillSound(srcpc.getId(), 7020));
        srcpc.sendPacketsAll(new S_SkillSound(cha.getId(), 12119));
        return dmg;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        for (int i = 0; i < 3; ++i) {
            npc.attackTarget(cha);
        }
        npc.broadcastPacketAll(new S_SkillSound(cha.getId(), 7020));
        npc.broadcastPacketAll(new S_SkillSound(cha.getId(), 12119));
        return dmg;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
    }
}
