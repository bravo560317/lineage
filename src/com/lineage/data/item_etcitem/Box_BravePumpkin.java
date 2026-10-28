package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Box_BravePumpkin extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Box_BravePumpkin();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.getInventory().removeItem(item, 1L);
        int k = (int)(Math.random() * 14.0);
        int item_id = 0;
        int count = 1;
        switch (k) {
            case 0: {
                item_id = 41251;
                break;
            }
            case 1: {
                item_id = 40318;
                count = 5;
                break;
            }
            case 2: {
                item_id = 40013;
                count = 8;
                break;
            }
            case 3: {
                item_id = 40010;
                count = 15;
                break;
            }
            case 4: {
                item_id = 40010;
                count = 30;
                break;
            }
            case 5: {
                item_id = 40011;
                count = 8;
                break;
            }
            case 6: {
                item_id = 40011;
                count = 16;
                break;
            }
            case 7: {
                item_id = 40318;
                count = 10;
                break;
            }
            case 8: {
                item_id = 40319;
                count = 10;
                break;
            }
            case 9: {
                item_id = 40087;
                count = 1;
                break;
            }
            case 10: {
                item_id = 40088;
                count = 2;
                break;
            }
            case 11: {
                item_id = 40012;
                count = 4;
                break;
            }
            case 12: {
                item_id = 40074;
                count = 1;
                break;
            }
            case 13: {
                item_id = 49143;
                count = 1;
                break;
            }
        }
        if (item_id != 0) {
            CreateNewItem.createNewItem(pc, item_id, (long)count);
        }
    }
}
