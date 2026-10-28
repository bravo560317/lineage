package com.lineage.data.item_etcitem.quest;

import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class OrcEmissaryPoly extends ItemExecutor
{
    public static ItemExecutor get() {
        return new OrcEmissaryPoly();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.getInventory().removeItem(item);
        L1PolyMorph.doPoly(pc, 6984, 1800, 1);
    }
}
