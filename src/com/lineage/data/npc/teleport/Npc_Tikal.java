package com.lineage.data.npc.teleport;

import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.ServerCrockTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Tikal extends NpcExecutor
{
    private Npc_Tikal() {
    }
    
    public static NpcExecutor get() {
        return new Npc_Tikal();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tikalgate1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equals("e")) {
            if (ServerCrockTable.get().checkBoss()) {
                int itemid = 49273;
                L1ItemInstance item = pc.getInventory().checkItemX(49273, 1L);
                if (item != null) {
                    pc.getInventory().removeItem(item, 1L);
                    L1Teleport.teleport(pc, 32732, 32862, (short)784, 5, true);
                }
                else {
                    L1Item itemtmp = ItemTable.get().getTemplate(49273);
                    pc.sendPackets(new S_ServerMessage(337, itemtmp.getNameId()));
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tikalgate2"));
            }
        }
    }
}
