package com.lineage.data.item_etcitem.add;

import com.lineage.server.utils.BinaryOutputStream;
import com.lineage.server.datatables.ItemUseEXTable;
import com.lineage.server.templates.L1ItemUseEX;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class item_buffTime extends ItemExecutor
{
    public static ItemExecutor get() {
        return new item_buffTime();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = item.getItemId();
        L1ItemUseEX value = ItemUseEXTable._list.get((Integer)item_id);
        if (value == null) {
            return;
        }
        else {
            if (ItemUseEXTable.get().add(pc, item.getItemId(), 0) && value.get_removeitem() == 1) {
                pc.getInventory().removeItem(item, 1L);
            }
            return;
        }
    }
    
    @Override
    public BinaryOutputStream itemStatus(L1ItemInstance item) {
        return ItemUseEXTable.get().getOS(item.getItemId());
    }
}
