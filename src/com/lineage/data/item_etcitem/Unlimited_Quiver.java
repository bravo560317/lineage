package com.lineage.data.item_etcitem;

import java.sql.Timestamp;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Unlimited_Quiver extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Unlimited_Quiver();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 1000;
        int k = (int)(Math.random() * 100.0);
        switch (k) {
            case 0:
            case 96:
            case 97:
            case 98:
            case 99: {
                item_id = 40748;
                break;
            }
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89: {
                item_id = 40746;
                break;
            }
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95: {
                item_id = 40747;
                break;
            }
            default: {
                item_id = 40744;
                count = 4000;
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
