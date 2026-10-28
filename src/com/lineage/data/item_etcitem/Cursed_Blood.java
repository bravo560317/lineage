package com.lineage.data.item_etcitem;

import java.sql.Timestamp;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Cursed_Blood extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Cursed_Blood();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 1;
        short k = (short)(int)(Math.random() * 6.0);
        switch (k) {
            case 1: {
                item_id = 40031;
                break;
            }
            case 2: {
                item_id = 40006;
                break;
            }
            case 3: {
                item_id = 40008;
                break;
            }
            case 4: {
                item_id = 40009;
                break;
            }
            case 5: {
                item_id = 40524;
                break;
            }
            default: {
                item_id = 40007;
                break;
            }
        }
        CreateNewItem.createNewItem(pc, item_id, 1L);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        item.setLastUsed(ts);
        pc.getInventory().updateItem(item, 32);
        pc.getInventory().saveItem(item, 32);
    }
}
