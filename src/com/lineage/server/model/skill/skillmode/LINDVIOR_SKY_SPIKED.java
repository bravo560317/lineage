package com.lineage.server.model.skill.skillmode;

import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class LINDVIOR_SKY_SPIKED extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        return 0;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = magic.calcMagicDamage(11061);
        int dir = npc.targetDirection(cha.getX(), cha.getY());
        if (dir == 3 || dir == 4) {
            npc.broadcastPacketAll(new S_EffectLocation(cha.getX(), cha.getY(), 7987));
        }
        else if (dir == 5) {
            npc.broadcastPacketAll(new S_EffectLocation(cha.getX(), cha.getY(), 8050));
        }
        else if (dir == 6 || dir == 7) {
            npc.broadcastPacketAll(new S_EffectLocation(cha.getX(), cha.getY(), 8051));
        }
        return dmg;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
    }
}
