package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Box_Happy1 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Box_Happy1();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.getInventory().removeItem(item, 1L);
        int k = (int)(Math.random() * 17.0);
        int item_id = 0;
        int count = 0;
        switch (k) {
            case 0: {
                item_id = 40068;
                count = 10;
                break;
            }
            case 1: {
                item_id = 40031;
                count = 10;
                break;
            }
            case 2: {
                item_id = 40890;
                count = 10;
                break;
            }
            case 3: {
                item_id = 40882;
                count = 10;
                break;
            }
            case 4: {
                item_id = 40866;
                count = 10;
                break;
            }
            case 5: {
                item_id = 82205;
                count = 10;
                break;
            }
            case 6: {
                item_id = 82206;
                count = 10;
                break;
            }
            case 7: {
                item_id = 82207;
                count = 10;
                break;
            }
            case 8: {
                item_id = 82208;
                count = 10;
                break;
            }
            case 9: {
                item_id = 40884;
                count = 10;
            }
            case 10: {
                item_id = 40867;
                count = 10;
            }
            case 11: {
                item_id = 40861;
                count = 10;
            }
            case 12: {
                item_id = 40014;
                count = 10;
            }
            case 13: {
                item_id = 41254;
                count = 1;
            }
            case 14: {
                item_id = 41251;
                count = 1;
            }
            case 15: {
                item_id = 41253;
                count = 1;
            }
            case 16: {
                item_id = 41252;
                count = 1;
                break;
            }
        }
        if (item_id != 0) {
            CreateNewItem.createNewItem(pc, item_id, (long)count);
        }
    }
}
