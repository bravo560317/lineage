package com.lineage.data.item_etcitem.wand;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1TownLocation;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Wand_Blink extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Wand_Blink();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1BuffUtil.cancelAbsoluteBarrier(pc);
        if (pc.isInvisble()) {
            pc.delInvis();
        }
        pc.sendPacketsX8(new S_DoActionGFX(pc.getId(), 17));
        int newchargecount = item.getChargeCount() - 1;
        item.setChargeCount(newchargecount);
        pc.getInventory().updateItem(item, 128);
        if (newchargecount <= 0) {
            pc.getInventory().deleteItem(item);
        }
        else {
            L1Object target = World.get().findObject(targObjId);
            if (target != null) {
                int rnd = (int)(Math.random() * 100.0);
                if (target != null && rnd >= 50) {
                    this.wandAction(pc, target);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(280));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
        }
    }
    
    private void wandAction(L1PcInstance pc, L1Object ta) {
        int target_x = 0;
        int target_y = 0;
        int newX = 0;
        int newY = 0;
        short xy = (short)(int)(Math.random() * 3.0 + 1.0);
        if (ta instanceof L1PcInstance) {
            L1PcInstance targetPc = (L1PcInstance)ta;
            target_x = pc.getX() - targetPc.getX();
            target_y = pc.getY() - targetPc.getY();
            if (pc.getId() == targetPc.getId()) {
                pc.sendPackets(new S_ServerMessage(280));
            }
            else if (target_x > 4 || target_x < -4 || target_y > 4 || target_y < -4 || targetPc.isGm()) {
                pc.sendPackets(new S_ServerMessage(280));
            }
            else {
                switch (target_x) {
                    case -4:
                    case -3:
                    case -2:
                    case -1: {
                        newX = targetPc.getX() + xy;
                        break;
                    }
                    case 1:
                    case 2:
                    case 3:
                    case 4: {
                        newX = targetPc.getX() - xy;
                        break;
                    }
                    default: {
                        newX = targetPc.getX();
                        break;
                    }
                }
                switch (target_y) {
                    case -4:
                    case -3:
                    case -2:
                    case -1: {
                        newY = targetPc.getY() + xy;
                        break;
                    }
                    case 1:
                    case 2:
                    case 3:
                    case 4: {
                        newY = targetPc.getY() - xy;
                        break;
                    }
                    default: {
                        newY = targetPc.getY();
                        break;
                    }
                }
                L1Map map = L1WorldMap.get().getMap(targetPc.getMapId());
                short mapId = targetPc.getMapId();
                int head = targetPc.getHeading();
                if (L1TownLocation.isGambling(newX, newY, mapId)) {
                    pc.sendPackets(new S_ServerMessage(280));
                    return;
                }
                else if (map.isInMap(newX, newY) && map.isPassable(newX, newY, null)) {
                    L1Teleport.teleport(targetPc, newX, newY, mapId, head, true);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(280));
                }
            }
        }
        else if (ta instanceof L1MonsterInstance) {
            L1MonsterInstance targetNpc = (L1MonsterInstance)ta;
            target_x = pc.getX() - targetNpc.getX();
            target_y = pc.getY() - targetNpc.getY();
            if (target_x > 4 || target_x < -4 || target_y > 4 || target_y < -4 || targetNpc.getLevel() >= 40) {
                pc.sendPackets(new S_ServerMessage(280));
            }
            else {
                switch (target_x) {
                    case -4:
                    case -3:
                    case -2:
                    case -1: {
                        newX = targetNpc.getX() + xy;
                        break;
                    }
                    case 1:
                    case 2:
                    case 3:
                    case 4: {
                        newX = targetNpc.getX() - xy;
                        break;
                    }
                    default: {
                        newX = targetNpc.getX();
                        break;
                    }
                }
                switch (target_y) {
                    case -4:
                    case -3:
                    case -2:
                    case -1: {
                        newY = targetNpc.getY() + xy;
                        break;
                    }
                    case 1:
                    case 2:
                    case 3:
                    case 4: {
                        newY = targetNpc.getY() - xy;
                        break;
                    }
                    default: {
                        newY = targetNpc.getY();
                        break;
                    }
                }
                L1Map map = L1WorldMap.get().getMap(targetNpc.getMapId());
                short mapId = targetNpc.getMapId();
                int head = targetNpc.getHeading();
                if (map.isInMap(newX, newY) && map.isPassable(newX, newY, targetNpc)) {
                    this.teleport(targetNpc, newX, newY, mapId, head);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(280));
                }
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage(280));
        }
    }
    
    private void teleport(L1MonsterInstance targetNpc, int x, int y, short map, int head) {
        World.get().moveVisibleObject(targetNpc, (int)map);
        targetNpc.getMap().setPassable(targetNpc.getX(), targetNpc.getY(), true, 2);
        targetNpc.setX(x);
        targetNpc.setY(y);
        targetNpc.setMap(map);
        targetNpc.setHeading(head);
        targetNpc.getMap().setPassable(x, y, false, 2);
        ArrayList<L1PcInstance> tgPcs = World.get().getVisiblePlayer(targetNpc, 15);
        for (L1PcInstance tgPc : tgPcs) {
            if (tgPc != null) {
                tgPc.sendPackets(new S_SkillSound(targetNpc.getId(), 169));
                tgPc.sendPackets(new S_RemoveObject(targetNpc));
                tgPc.removeKnownObject(targetNpc);
                tgPc.updateObject();
            }
        }
    }
}
