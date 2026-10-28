package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Fruit_BlackEnt extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Fruit_BlackEnt();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        String itenName = item.getLogName();
        if (pc.castleWarResult()) {
            pc.sendPackets(new S_ServerMessage(403, itenName));
        }
        else if (pc.getMapId() == 303) {
            pc.sendPackets(new S_ServerMessage(403, itenName));
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            pc.death(null);
            int newItemId = 20299;
            CreateNewItem.createNewItem(pc, 20299, 1L);
        }
    }
}
