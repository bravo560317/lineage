package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Lavor_Pouch extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Lavor_Pouch();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 1;
        int k = (int)(Math.random() * 5.0);
        switch (k) {
            case 0: {
                item_id = 40090;
                break;
            }
            case 1: {
                item_id = 40091;
                break;
            }
            case 2: {
                item_id = 40092;
                break;
            }
            case 3: {
                item_id = 40093;
                break;
            }
            default: {
                item_id = 40094;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, 1L);
    }
}
