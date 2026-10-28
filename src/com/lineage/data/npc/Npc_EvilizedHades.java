package com.lineage.data.npc;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_EvilizedHades extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_EvilizedHades();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun21"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        int olditem = 0;
        int newcape = 0;
        boolean success = false;
        if (cmd.equalsIgnoreCase("A")) {
            olditem = 82209;
            newcape = 401004;
            if (pc.getInventory().checkItemNotEquipped(olditem, 2L)) {
                pc.getInventory().consumeItem(olditem, 2L);
                L1ItemInstance item = ItemTable.get().createItem(newcape);
                item.setEnchantLevel(6);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun22"));
            }
        }
        else if (cmd.equalsIgnoreCase("B")) {
            olditem = 82209;
            newcape = 401005;
            if (pc.getInventory().checkItemNotEquipped(olditem, 2L)) {
                pc.getInventory().consumeItem(olditem, 2L);
                L1ItemInstance item = ItemTable.get().createItem(newcape);
                item.setEnchantLevel(6);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun22"));
            }
        }
        else if (cmd.equalsIgnoreCase("C")) {
            olditem = 82209;
            newcape = 401006;
            if (pc.getInventory().checkItemNotEquipped(olditem, 2L)) {
                pc.getInventory().consumeItem(olditem, 2L);
                L1ItemInstance item = ItemTable.get().createItem(newcape);
                item.setEnchantLevel(6);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun22"));
            }
        }
        else if (cmd.equalsIgnoreCase("D")) {
            olditem = 82209;
            newcape = 401007;
            if (pc.getInventory().checkItemNotEquipped(olditem, 2L)) {
                pc.getInventory().consumeItem(olditem, 2L);
                L1ItemInstance item = ItemTable.get().createItem(newcape);
                item.setEnchantLevel(6);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun22"));
            }
        }
        else if (cmd.equalsIgnoreCase("E")) {
            olditem = 82210;
            newcape = 401004;
            if (pc.getInventory().checkItemNotEquipped(olditem, 3L)) {
                pc.getInventory().consumeItem(olditem, 3L);
                L1ItemInstance item = ItemTable.get().createItem(newcape);
                item.setEnchantLevel(9);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun22"));
            }
        }
        else if (cmd.equalsIgnoreCase("F")) {
            olditem = 82210;
            newcape = 401005;
            if (pc.getInventory().checkItemNotEquipped(olditem, 3L)) {
                pc.getInventory().consumeItem(olditem, 3L);
                L1ItemInstance item = ItemTable.get().createItem(newcape);
                item.setEnchantLevel(9);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun22"));
            }
        }
        else if (cmd.equalsIgnoreCase("G")) {
            olditem = 82210;
            newcape = 401006;
            if (pc.getInventory().checkItemNotEquipped(olditem, 3L)) {
                pc.getInventory().consumeItem(olditem, 3L);
                L1ItemInstance item = ItemTable.get().createItem(newcape);
                item.setEnchantLevel(9);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun22"));
            }
        }
        else if (cmd.equalsIgnoreCase("H")) {
            olditem = 82210;
            newcape = 401007;
            if (pc.getInventory().checkItemNotEquipped(olditem, 3L)) {
                pc.getInventory().consumeItem(olditem, 3L);
                L1ItemInstance item = ItemTable.get().createItem(newcape);
                item.setEnchantLevel(9);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun22"));
            }
        }
        if (!success) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun23"));
        }
    }
}
