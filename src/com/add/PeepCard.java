package com.add;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_MapID;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;

public class PeepCard
{
    private static Random _random;
    
    static {
        PeepCard._random = new Random();
    }
    
    public static void TeleportPc(L1PcInstance pc, L1Object target) {
        if (target instanceof L1PcInstance) {
            L1PcInstance target_pc = (L1PcInstance)target;
            if (pc.getLocation().getTileLineDistance(target_pc.getLocation()) > 4) {
                L1Location newLoc = pc.getLocation().randomLocation(1, 1, false);
                if (pc.glanceCheck(newLoc.getX(), newLoc.getY())) {
                    L1Teleport.teleport(target_pc, newLoc.getX(), newLoc.getY(), pc.getMapId(), 5, true);
                    pc.sendPackets(new S_SkillSound(pc.getId(), 3932));
                    pc.broadcastPacketAll(new S_SkillSound(pc.getId(), 3932));
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("對方距離不夠遠，無法把他抓過來。"));
            }
        }
        else if (target instanceof L1MonsterInstance) {
            L1MonsterInstance target_npc = (L1MonsterInstance)target;
            if (pc.getLocation().getTileLineDistance(target_npc.getLocation()) > 4) {
                L1Location newLoc = pc.getLocation().randomLocation(1, 1, false);
                if (pc.glanceCheck(newLoc.getX(), newLoc.getY())) {
                    target_npc.broadcastPacketAll(new S_SkillSound(target_npc.getId(), 3932));
                    target_npc.setX(newLoc.getX());
                    target_npc.setY(newLoc.getY());
                    target_npc.setHeading(target_npc.getHeading());
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("怪物距離不夠遠，無法把怪物抓過來。"));
            }
        }
    }
    
    public static void BackMagic(L1PcInstance pc, L1Object target) {
        int _x = pc.getX() - target.getX();
        int _y = pc.getY() - target.getY();
        if (Math.abs(_x) < 5 && Math.abs(_y) < 5) {
            int a;
            if (_x > 0) {
                a = target.getX() - 1;
            }
            else if (_x < 0) {
                a = target.getX() + 1;
            }
            else {
                a = target.getX();
            }
            int f;
            if (_y > 0) {
                f = target.getY() - 1;
            }
            else if (_y < 0) {
                f = target.getY() + 1;
            }
            else {
                f = target.getY();
            }
            if (target != null) {
                L1Map map = target.getMap();
                if (map.isPassable(a, f, null)) {
                    target.setX(a);
                    target.setY(f);
                    if (target instanceof L1PcInstance) {
                        L1PcInstance targetPc = (L1PcInstance)target;
                        targetPc.sendPackets(new S_MapID((int)targetPc.getMapId(), targetPc.getMap().isUnderwater()));
                        targetPc.broadcastPacketAll(new S_OtherCharPacks(targetPc));
                        targetPc.sendPackets(new S_OwnCharPack(targetPc));
                        targetPc.sendPackets(new S_CharVisualUpdate(targetPc));
                        targetPc.removeAllKnownObjects();
                        targetPc.updateObject();
                    }
                }
                pc.sendPacketsAll(new S_SkillSound(target.getId(), 2181));
            }
        }
    }
    
    public static void TakePc(L1PcInstance pc, L1Object target) {
        if (target instanceof L1PcInstance) {
            L1PcInstance target_pc = (L1PcInstance)target;
            int rnd = PeepCard._random.nextInt(100) + 1;
            if (rnd < 0) {
                rnd = 0;
            }
            L1ItemInstance item = target_pc.getInventory().caoPenalty();
            if (item != null && PeepCard._random.nextInt(100) + 1 < rnd) {
                target_pc.getInventory().tradeItem(item, item.isStackable() ? item.getCount() : 1L, pc.getInventory());
                target_pc.sendPackets(new S_ServerMessage(638, item.getLogName()));
                pc.sendPackets(new S_SystemMessage("成功偷到( " + item.getLogName() + " )。"));
            }
        }
    }
}
