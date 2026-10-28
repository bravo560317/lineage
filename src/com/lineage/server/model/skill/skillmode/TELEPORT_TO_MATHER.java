package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.utils.Teleportation;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1Trade;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class TELEPORT_TO_MATHER extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        L1PcInstance pc = (L1PcInstance)cha;
        if (pc.getMap().isEscapable() || pc.isGm()) {
            if (pc.getTradeID() != 0) {
                L1Trade trade = new L1Trade();
                trade.tradeCancel(pc);
            }
            pc.setTeleportX(33047);
            pc.setTeleportY(32338);
            pc.setTeleportMapId((short)4);
            pc.setTeleportHeading(5);
            pc.sendPacketsAll(new S_SkillSound(pc.getId(), 169));
            Teleportation.teleportation(pc);
        }
        else {
            pc.sendPackets(new S_ServerMessage(276));
            pc.sendPackets(new S_Paralysis(7, false));
        }
        return dmg;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        return dmg;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
    }
}
