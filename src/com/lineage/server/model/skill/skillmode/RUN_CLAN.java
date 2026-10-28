package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class RUN_CLAN extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        L1PcInstance pc = (L1PcInstance)cha;
        L1PcInstance clanPc = (L1PcInstance)World.get().findObject(integer);
        if (clanPc != null) {
            if (pc.getMap().isEscapable() || pc.isGm()) {
                boolean castle_area = L1CastleLocation.checkInAllWarArea(clanPc.getX(), clanPc.getY(), clanPc.getMapId());
                if (!castle_area && (clanPc.getMapId() == 0 || clanPc.getMapId() == 4 || clanPc.getMapId() == 304)) {
                    L1Teleport.teleport(pc, clanPc.getX(), clanPc.getY(), clanPc.getMapId(), 5, true);
                    return 0;
                }
                else {
                    pc.sendPackets(new S_ServerMessage(1192));
                    pc.sendPackets(new S_Paralysis(7, false));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(647));
                pc.sendPackets(new S_Paralysis(7, false));
            }
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
    }
}
