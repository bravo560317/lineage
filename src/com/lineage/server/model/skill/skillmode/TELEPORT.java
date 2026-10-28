package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Location;
import com.lineage.server.templates.L1BookMark;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.utils.Teleportation;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1Trade;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class TELEPORT extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        L1PcInstance pc = (L1PcInstance)cha;
        L1BookMark bookm = CharBookReading.get().getBookMark(pc, integer);
        boolean isTeleport = pc.getMap().isTeleportable();
        int mapid = pc.getMapId();
        if (pc.getInventory().checkItem(84041, 1L) && mapid == 3301) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84042, 1L) && mapid == 3302) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84043, 1L) && mapid == 3303) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84044, 1L) && mapid == 3304) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84045, 1L) && mapid == 3305) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84046, 1L) && mapid == 3306) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84047, 1L) && mapid == 3307) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84048, 1L) && mapid == 3308) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84049, 1L) && mapid == 3309) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84050, 1L) && mapid == 3310) {
            isTeleport = true;
        }
        else if (pc.getInventory().checkItem(84071, 1L) && mapid >= 3301 && mapid <= 3310) {
            isTeleport = true;
        }
        if (bookm != null) {
            if (isTeleport) {
                int newX = bookm.getLocX();
                int newY = bookm.getLocY();
                short mapId = bookm.getMapId();
                if (pc.getTradeID() != 0) {
                    L1Trade trade = new L1Trade();
                    trade.tradeCancel(pc);
                }
                pc.setTeleportX(newX);
                pc.setTeleportY(newY);
                pc.setTeleportMapId(mapId);
                pc.setTeleportHeading(5);
                pc.sendPacketsAll(new S_SkillSound(pc.getId(), 169));
                Teleportation.teleportation(pc);
            }
            else {
                pc.sendPackets(new S_ServerMessage(647));
                pc.sendPackets(new S_Paralysis(7, false));
            }
        }
        else if (isTeleport) {
            L1Location newLocation = pc.getLocation().randomLocation(200, true);
            int newX2 = newLocation.getX();
            int newY2 = newLocation.getY();
            short mapId2 = (short)newLocation.getMapId();
            if (pc.getTradeID() != 0) {
                L1Trade trade2 = new L1Trade();
                trade2.tradeCancel(pc);
            }
            pc.setTeleportX(newX2);
            pc.setTeleportY(newY2);
            pc.setTeleportMapId(mapId2);
            pc.setTeleportHeading(5);
            pc.sendPacketsAll(new S_SkillSound(pc.getId(), 169));
            Teleportation.teleportation(pc);
        }
        else {
            pc.sendPackets(new S_ServerMessage(647));
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
