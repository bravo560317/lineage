package com.lineage.server.model.skill.skillmode;

import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class KIRTAS_BARRIER1 extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        return 0;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        npc.startKIRTAS_Timer();
        int dmg = magic.calcMagicDamage(11060);
        npc.setHiddenStatus(4);
        npc.setStatus(20);
        npc.setSkillEffect(11060, integer * 1000);
        npc.broadcastPacketAll(new S_NPCPack(npc));
        return dmg;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
    }
}
