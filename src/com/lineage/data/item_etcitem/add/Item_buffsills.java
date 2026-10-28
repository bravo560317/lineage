package com.lineage.data.item_etcitem.add;

import com.lineage.william.buffskills;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Item_buffsills extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Item_buffsills();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        buffskills.forItemUSe(pc, item);
    }
}
