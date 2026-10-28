package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Evolution_Fruit extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Evolution_Fruit();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.sendPackets(new S_ServerMessage(76, item.getLogName()));
        pc.getInventory().removeItem(item, 1L);
    }
}
