package com.lineage.data.item_etcitem.add;

import com.lineage.william.ItemUse;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Itemuse extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Itemuse();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        ItemUse.forItemUSe(pc, item);
    }
}
