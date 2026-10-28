package com.lineage.data.item_etcitem.quest;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class I50_Teleport extends ItemExecutor
{
    public static ItemExecutor get() {
        return new I50_Teleport();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.getInventory().removeItem(item, 1L);
        L1Teleport.teleport(pc, 33436, 32814, (short)4, 5, true);
    }
}
