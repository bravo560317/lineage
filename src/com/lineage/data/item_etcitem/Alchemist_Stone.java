package com.lineage.data.item_etcitem;

import java.sql.Timestamp;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Alchemist_Stone extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Alchemist_Stone();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 2;
        int k = (int)(Math.random() * 7.0);
        switch (k) {
            case 1: {
                item_id = 40024;
                break;
            }
            case 2: {
                item_id = 40023;
                break;
            }
            case 3: {
                item_id = 40022;
                count = 3;
                break;
            }
            case 4: {
                item_id = 40015;
                break;
            }
            case 5: {
                item_id = 40016;
                break;
            }
            case 6: {
                item_id = 40042;
                count = 1;
                break;
            }
            default: {
                item_id = 40068;
                break;
            }
        }
        CreateNewItem.createNewItem(pc, item_id, (long)count);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        item.setLastUsed(ts);
        pc.getInventory().updateItem(item, 32);
        pc.getInventory().saveItem(item, 32);
    }
}
