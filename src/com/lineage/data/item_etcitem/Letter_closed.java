package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_Letter;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Letter_closed extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Letter_closed();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        pc.sendPackets(new S_Letter(item));
        item.setItemId(itemId + 1);
        pc.getInventory().updateItem(item, 64);
        pc.getInventory().saveItem(item, 64);
    }
}
