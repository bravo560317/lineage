package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class BanditPouch extends ItemExecutor
{
    public static ItemExecutor get() {
        return new BanditPouch();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.getInventory().removeItem(item, 1L);
        int k = (int)(Math.random() * 300.0);
        int count = 300 + k;
        CreateNewItem.createNewItem(pc, 40308, (long)count);
    }
}
