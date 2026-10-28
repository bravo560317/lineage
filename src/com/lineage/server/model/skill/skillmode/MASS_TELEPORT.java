package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Location;
import java.util.Iterator;
import java.util.List;
import com.lineage.server.templates.L1BookMark;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.world.World;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class MASS_TELEPORT extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        L1PcInstance pc = (L1PcInstance)cha;
        L1BookMark bookm = CharBookReading.get().getBookMark(pc, integer);
        if (bookm != null) {
            if (pc.getMap().isEscapable() || pc.isGm()) {
                int newX = bookm.getLocX();
                int newY = bookm.getLocY();
                short mapId = bookm.getMapId();
                List<L1PcInstance> clanMember = (List)World.get().getVisiblePlayer(pc);
                for (L1PcInstance member : clanMember) {
                    if (pc.getLocation().getTileLineDistance(member.getLocation()) <= 3 && member.getClanid() == pc.getClanid() && pc.getClanid() != 0 && member.getId() != pc.getId() && !member.isPrivateShop()) {
                        member.setTeleportX(newX);
                        member.setTeleportY(newY);
                        member.setTeleportMapId(mapId);
                        if (member.hasSkillEffect(1692)) {
                            member.sendPackets(new S_Message_YN(748));
                        }
                        else if (member.isParalyzed_guaji()) {
                            member.sendPackets(new S_Paralysis(7, false));
                            return 0;
                        }
                        else {
                            L1Teleport.teleport(member, newX, newY, mapId, 5, true);
                        }
                    }
                }
                L1Teleport.teleport(pc, newX, newY, mapId, 5, true);
            }
            else {
                pc.sendPackets(new S_ServerMessage(276));
                pc.sendPackets(new S_Paralysis(7, false));
            }
        }
        else if (pc.getMap().isTeleportable() || pc.isGm()) {
            L1Location newLocation = pc.getLocation().randomLocation(200, true);
            int newX2 = newLocation.getX();
            int newY2 = newLocation.getY();
            short mapId2 = (short)newLocation.getMapId();
            List<L1PcInstance> clanMember2 = (List)World.get().getVisiblePlayer(pc);
            for (L1PcInstance member2 : clanMember2) {
                if (pc.getLocation().getTileLineDistance(member2.getLocation()) <= 3 && member2.getClanid() == pc.getClanid() && pc.getClanid() != 0 && member2.getId() != pc.getId() && !member2.isPrivateShop()) {
                    member2.setTeleportX(newX2);
                    member2.setTeleportY(newY2);
                    member2.setTeleportMapId(mapId2);
                    if (member2.hasSkillEffect(1692)) {
                        member2.sendPackets(new S_Message_YN(748));
                    }
                    else if (member2.isParalyzed_guaji()) {
                        member2.sendPackets(new S_Paralysis(7, false));
                        return 0;
                    }
                    else {
                        L1Teleport.teleport(member2, newX2, newY2, mapId2, 5, true);
                    }
                }
            }
            L1Teleport.teleport(pc, newX2, newY2, mapId2, 5, true);
        }
        else {
            pc.sendPackets(new S_ServerMessage(276));
            pc.sendPackets(new S_Paralysis(7, false));
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
