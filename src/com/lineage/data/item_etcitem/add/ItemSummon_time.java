package com.lineage.data.item_etcitem.add;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ItemSummon_time extends ItemExecutor
{
    private ItemSummon_time() {
    }
    
    public static ItemExecutor get() {
        return new ItemSummon_time();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        com.lineage.william.ItemSummon_time.forresolvent(pc, item);
    }
}
