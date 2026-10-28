package com.lineage.data.npc;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ShopSellList;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_MoonGirl extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_MoonGirl();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_15moon1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        int[] oldweapon = { 410199, 410200, 410201, 410202, 410203, 410204, 410205, 410206 };
        int newItem = 86007;
        boolean success = false;
        if (cmd.equalsIgnoreCase("a")) {
            for (int i = 0; i < oldweapon.length; ++i) {
                if (pc.getInventory().checkEnchantItem(oldweapon[i], 8, 1L)) {
                    pc.getInventory().consumeEnchantItem(oldweapon[i], 8, 1L);
                    L1ItemInstance item = ItemTable.get().createItem(newItem);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                    success = true;
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
            }
            if (!success) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_15moon2"));
            }
        }
        else if (cmd.equalsIgnoreCase("buy")) {
            pc.sendPackets(new S_ShopSellList(npc.getId()));
        }
    }
}
