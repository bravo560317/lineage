package com.lineage.data.npc;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_InHaiSaBelt extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_InHaiSaBelt();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_2014belt1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        int[] oldbelts = { 300034, 300035, 300036, 300037 };
        int newbelt = 0;
        boolean success = false;
        if (cmd.equalsIgnoreCase("b")) {
            newbelt = 401008;
            int i = 0;
            while (i < oldbelts.length) {
                if (pc.getInventory().checkItemNotEquipped(oldbelts[i], 1L) && pc.getInventory().checkItem(80329, 1L)) {
                    pc.getInventory().consumeItem(oldbelts[i], 1L);
                    pc.getInventory().consumeItem(80329, 1L);
                    L1ItemInstance item = ItemTable.get().createItem(newbelt);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("c")) {
            newbelt = 401009;
            int i = 0;
            while (i < oldbelts.length) {
                if (pc.getInventory().checkItemNotEquipped(oldbelts[i], 1L) && pc.getInventory().checkItem(80329, 1L)) {
                    pc.getInventory().consumeItem(oldbelts[i], 1L);
                    pc.getInventory().consumeItem(80329, 1L);
                    L1ItemInstance item = ItemTable.get().createItem(newbelt);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("d")) {
            newbelt = 401010;
            int i = 0;
            while (i < oldbelts.length) {
                if (pc.getInventory().checkItemNotEquipped(oldbelts[i], 1L) && pc.getInventory().checkItem(80329, 1L)) {
                    pc.getInventory().consumeItem(oldbelts[i], 1L);
                    pc.getInventory().consumeItem(80329, 1L);
                    L1ItemInstance item = ItemTable.get().createItem(newbelt);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        else if (cmd.equalsIgnoreCase("e")) {
            newbelt = 401011;
            int i = 0;
            while (i < oldbelts.length) {
                if (pc.getInventory().checkItemNotEquipped(oldbelts[i], 1L) && pc.getInventory().checkItem(80329, 1L)) {
                    pc.getInventory().consumeItem(oldbelts[i], 1L);
                    pc.getInventory().consumeItem(80329, 1L);
                    L1ItemInstance item = ItemTable.get().createItem(newbelt);
                    item.setIdentified(true);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        if (success) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_2014belt2"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_2014belt3"));
        }
    }
}
