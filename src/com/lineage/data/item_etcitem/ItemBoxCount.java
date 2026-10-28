package com.lineage.data.item_etcitem;

import com.lineage.william.ItemBox;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ItemBoxCount extends ItemExecutor
{
    private ItemBoxCount() {
    }
    
    public static ItemExecutor get() {
        return new ItemBoxCount();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            ItemBox.trueOutburst(pc, item);
            return;
        }
    }
}
