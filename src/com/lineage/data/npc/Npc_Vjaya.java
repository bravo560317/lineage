package com.lineage.data.npc;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Vjaya extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Vjaya();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "vjaya01"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        int[] oldweapon = { 270, 271 };
        int newweapon = 410173;
        boolean success = false;
        if (cmd.equalsIgnoreCase("A")) {
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 8, 1L);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newweapon);
                    item.setEnchantLevel(7);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    success = true;
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "vjaya05"));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("B")) {
            int i = 0;
            while (i < oldweapon.length) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 9, 1L);
                    pc.getInventory().consumeItem(40308, 10000000L);
                    L1ItemInstance item = ItemTable.get().createItem(newweapon);
                    item.setEnchantLevel(8);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    success = true;
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "vjaya05"));
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        if (!success) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "vjaya04"));
        }
    }
}
