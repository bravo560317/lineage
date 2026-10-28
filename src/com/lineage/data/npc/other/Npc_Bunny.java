package com.lineage.data.npc.other;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Bunny extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Bunny();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "nt_14chu"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        int[] olditem = { 41413, 41414 };
        int newWeapon = 0;
        boolean success = false;
        if (cmd.equalsIgnoreCase("a")) {
            newWeapon = 410176;
            for (int i = 0; i < olditem.length; ++i) {
                if (pc.getInventory().checkItem(olditem[i], 1000L)) {
                    pc.getInventory().consumeItem(olditem[i], 1000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getNameId()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
            }
        }
        else if (cmd.equalsIgnoreCase("b")) {
            newWeapon = 410177;
            for (int i = 0; i < olditem.length; ++i) {
                if (pc.getInventory().checkItem(olditem[i], 1000L)) {
                    pc.getInventory().consumeItem(olditem[i], 1000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getNameId()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
            }
        }
        else if (cmd.equalsIgnoreCase("c")) {
            newWeapon = 410175;
            for (int i = 0; i < olditem.length; ++i) {
                if (pc.getInventory().checkItem(olditem[i], 1000L)) {
                    pc.getInventory().consumeItem(olditem[i], 1000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getNameId()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
            }
        }
        else if (cmd.equalsIgnoreCase("d")) {
            newWeapon = 410178;
            for (int i = 0; i < olditem.length; ++i) {
                if (pc.getInventory().checkItem(olditem[i], 1000L)) {
                    pc.getInventory().consumeItem(olditem[i], 1000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getNameId()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
            }
        }
        else if (cmd.equalsIgnoreCase("e")) {
            newWeapon = 410179;
            for (int i = 0; i < olditem.length; ++i) {
                if (pc.getInventory().checkItem(olditem[i], 1000L)) {
                    pc.getInventory().consumeItem(olditem[i], 1000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getNameId()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
            }
        }
        else if (cmd.equalsIgnoreCase("f")) {
            newWeapon = 410180;
            for (int i = 0; i < olditem.length; ++i) {
                if (pc.getInventory().checkItem(olditem[i], 1000L)) {
                    pc.getInventory().consumeItem(olditem[i], 1000L);
                    L1ItemInstance item = ItemTable.get().createItem(newWeapon);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getNameId()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
            }
        }
        if (!success) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "nt_14chu2"));
        }
    }
}
