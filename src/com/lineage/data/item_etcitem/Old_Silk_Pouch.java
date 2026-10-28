package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Old_Silk_Pouch extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Old_Silk_Pouch();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 1;
        int k = (int)(Math.random() * 12.0);
        switch (k) {
            case 0: {
                item_id = 40058;
                count = 3;
                break;
            }
            case 1: {
                item_id = 40071;
                count = 2;
                break;
            }
            case 2: {
                item_id = 40039;
                count = 3;
                break;
            }
            case 3: {
                item_id = 40040;
                count = 2;
                break;
            }
            case 4: {
                item_id = 40335;
                break;
            }
            case 5: {
                item_id = 40332;
                break;
            }
            case 6: {
                item_id = 40331;
                break;
            }
            case 7: {
                item_id = 40336;
                break;
            }
            case 8: {
                item_id = 40338;
                break;
            }
            case 9: {
                item_id = 40334;
                break;
            }
            case 10: {
                item_id = 40339;
                break;
            }
            default: {
                item_id = 40340;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, (long)count);
    }
}
