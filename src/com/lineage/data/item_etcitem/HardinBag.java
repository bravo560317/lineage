package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.utils.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class HardinBag extends ItemExecutor
{
    public static ItemExecutor get() {
        return new HardinBag();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int[] items_1 = { 49301, 49302, 49303, 49304, 49305, 49306, 49307, 49308, 49309, 49310 };
        int item2 = items_1[Random.getInt(items_1.length)];
        int[] items_2 = { 49317, 49318, 49319, 49320, 49321, 49322, 49323, 49324, 49325, 49326, 49327, 49328, 49329, 49330, 49331, 49332, 49333, 49334 };
        int item3 = items_2[Random.getInt(items_2.length)];
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item2, 1L);
        CreateNewItem.createNewItem(pc, item3, 1L);
    }
}
