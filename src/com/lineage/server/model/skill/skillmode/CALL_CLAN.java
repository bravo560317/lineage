package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class CALL_CLAN extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        L1PcInstance pc = (L1PcInstance)cha;
        L1PcInstance clanPc = (L1PcInstance)World.get().findObject(integer);
        boolean castle_area = L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId());
        if (castle_area) {
            return 0;
        }
        else {
            if (clanPc != null) {
                clanPc.setTempID(pc.getId());
                clanPc.sendPackets(new S_Message_YN(729));
            }
            return 0;
        }
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
    }
}
