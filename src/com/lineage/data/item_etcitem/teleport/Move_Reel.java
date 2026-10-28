package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.model.L1Location;
import com.lineage.server.templates.L1BookMark;
import com.lineage.server.utils.Teleportation;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Move_Reel extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Move_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else if (pc.getAutoX() != 0 && pc.getAutoY() != 0 && pc.isActived()) {
            pc.sendPackets(new S_ServerMessage("定點內掛中無法瞬移"));
            return;
        }
        else {
            int btele = data[0];
            int mapid = pc.getMapId();
            boolean isTeleport = pc.getMap().isTeleportable();
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
            if (!isTeleport && !pc.isGm()) {
                pc.sendPackets(new S_ServerMessage(647));
                pc.sendPackets(new S_Paralysis(7, false));
            }
            else {
                L1BookMark bookm = CharBookReading.get().getBookMark(pc, btele);
                if (bookm != null) {
                    pc.getInventory().removeItem(item, 1L);
                    pc.setTeleport(false);
                    pc.setTeleportX(bookm.getLocX());
                    pc.setTeleportY(bookm.getLocY());
                    pc.setTeleportMapId(bookm.getMapId());
                    pc.setTeleportHeading(5);
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 169));
                    Teleportation.teleportation(pc);
                }
                else {
                    pc.getInventory().removeItem(item, 1L);
                    pc.setTeleport(false);
                    L1Location newLocation = pc.getLocation().randomLocation(200, true);
                    int newX = newLocation.getX();
                    int newY = newLocation.getY();
                    pc.setTeleportX(newX);
                    pc.setTeleportY(newY);
                    pc.setTeleportMapId((short)mapid);
                    pc.setTeleportHeading(5);
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 169));
                    Teleportation.teleportation(pc);
                }
                if (pc.hasSkillEffect(78)) {
                    pc.killSkillEffectTimer(78);
                    pc.startHpRegeneration();
                    pc.startMpRegeneration();
                }
            }
            return;
        }
    }
}
