package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Box_Doll extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Box_Doll();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.getInventory().removeItem(item, 1L);
        int k = (int)(Math.random() * 10.0);
        int item_id = 0;
        switch (k) {
            case 0: {
                item_id = 55000;
                break;
            }
            case 1: {
                item_id = 55001;
                break;
            }
            case 2: {
                item_id = 55002;
                break;
            }
            case 3: {
                item_id = 55010;
                break;
            }
            case 4: {
                item_id = 55011;
                break;
            }
            case 5: {
                item_id = 55012;
                break;
            }
            case 6: {
                item_id = 55006;
                break;
            }
            case 7: {
                item_id = 55007;
                break;
            }
            case 8: {
                item_id = 55009;
                break;
            }
            case 9: {
                item_id = 55013;
                break;
            }
        }
        if (item_id != 0) {
            CreateNewItem.createNewItem(pc, item_id, 1L);
        }
    }
}
