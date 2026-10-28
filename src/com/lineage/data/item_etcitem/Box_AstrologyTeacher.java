package com.lineage.data.item_etcitem;

import java.sql.Timestamp;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Box_AstrologyTeacher extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Box_AstrologyTeacher();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        CreateNewItem.createNewItem(pc, 41313, 1L);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        item.setLastUsed(ts);
        pc.getInventory().updateItem(item, 32);
        pc.getInventory().saveItem(item, 32);
    }
}
