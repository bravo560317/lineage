package com.lineage.data.npc;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Rushi extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Rushi();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rushi05"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        int[] oldweapon = { 41, 37, 42, 52, 180, 181, 131, 5, 6, 145, 148, 64, 125, 129, 99, 104, 32 };
        int newWeapon = 0;
        boolean success = false;
        if (cmd.equalsIgnoreCase("A")) {
            newWeapon = 259;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 8, 1L);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(7);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("B")) {
            newWeapon = 410132;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 8, 1L);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(7);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("C")) {
            newWeapon = 410157;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 8, 1L);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(7);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("D")) {
            newWeapon = 410131;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 8, 1L);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(7);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("E")) {
            newWeapon = 410133;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 8, 1L);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(7);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("F")) {
            newWeapon = 410134;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 8, 1L);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(7);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("G")) {
            newWeapon = 410135;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 8, 1L);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(7);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("H")) {
            newWeapon = 259;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 9, 1L);
                    pc.getInventory().consumeItem(40308, 10000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(8);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("I")) {
            newWeapon = 410132;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 9, 1L);
                    pc.getInventory().consumeItem(40308, 10000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(8);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("J")) {
            newWeapon = 410157;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 9, 1L);
                    pc.getInventory().consumeItem(40308, 10000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(8);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("K")) {
            newWeapon = 410131;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 9, 1L);
                    pc.getInventory().consumeItem(40308, 10000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(8);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("L")) {
            newWeapon = 410133;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 9, 1L);
                    pc.getInventory().consumeItem(40308, 10000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(8);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("M")) {
            newWeapon = 410134;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 9, 1L);
                    pc.getInventory().consumeItem(40308, 10000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(8);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("N")) {
            newWeapon = 410135;
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 9, 1L);
                    pc.getInventory().consumeItem(40308, 10000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setEnchantLevel(8);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        if (!success) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rushi04"));
        }
    }
}
