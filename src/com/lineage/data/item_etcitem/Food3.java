package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1Cooking;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Food3 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Food3();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        L1Cooking.useCookingItem(pc, item);
    }
}
