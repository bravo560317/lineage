package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class RelicExpedition_Member extends ItemExecutor
{
    public static ItemExecutor get() {
        return new RelicExpedition_Member();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 1;
        int k = (int)(Math.random() * 6.0);
        switch (k) {
            case 0: {
                item_id = 40682;
                break;
            }
            case 1: {
                item_id = 40681;
                break;
            }
            case 2: {
                item_id = 40680;
                break;
            }
            case 3: {
                item_id = 40684;
                break;
            }
            case 4: {
                item_id = 40679;
                break;
            }
            case 5: {
                item_id = 40683;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, 1L);
    }
}
