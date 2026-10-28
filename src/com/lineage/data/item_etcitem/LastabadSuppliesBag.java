package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class LastabadSuppliesBag extends ItemExecutor
{
    public static ItemExecutor get() {
        return new LastabadSuppliesBag();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 1;
        int k = (int)(Math.random() * 100.0);
        switch (k) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86: {
                item_id = 40308;
                count = 300;
                break;
            }
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 87: {
                item_id = 40308;
                count = 1000;
                break;
            }
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35: {
                item_id = 40308;
                count = 10000;
                break;
            }
            case 36: {
                item_id = 40006;
                break;
            }
            case 37:
            case 38:
            case 56:
            case 57:
            case 58: {
                item_id = 40046;
                break;
            }
            case 39:
            case 40:
            case 68: {
                item_id = 40050;
                break;
            }
            case 41: {
                item_id = 40008;
                break;
            }
            case 42:
            case 43:
            case 59:
            case 60:
            case 61: {
                item_id = 40047;
                break;
            }
            case 44:
            case 45:
            case 69: {
                item_id = 40051;
                break;
            }
            case 46:
            case 47:
            case 48:
            case 49:
            case 50: {
                item_id = 40044;
                break;
            }
            case 51:
            case 52:
            case 53:
            case 54:
            case 55: {
                item_id = 40045;
                break;
            }
            case 62:
            case 63:
            case 64: {
                item_id = 40048;
                break;
            }
            case 65:
            case 66:
            case 67: {
                item_id = 40049;
                break;
            }
            case 70: {
                item_id = 40052;
                break;
            }
            case 71: {
                item_id = 40053;
                break;
            }
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81: {
                item_id = 40429;
                break;
            }
            case 90: {
                item_id = 40055;
                break;
            }
            case 91: {
                item_id = 40054;
                break;
            }
            case 92: {
                item_id = 40441;
                break;
            }
            case 93: {
                item_id = 40444;
                break;
            }
            case 94: {
                item_id = 40468;
                break;
            }
            case 95: {
                item_id = 40489;
                break;
            }
            case 96: {
                item_id = 40508;
                break;
            }
            case 97: {
                item_id = 40524;
                break;
            }
            case 98: {
                item_id = 140074;
                break;
            }
            case 99: {
                item_id = 140087;
                break;
            }
            default: {
                item_id = 140100;
                count = 3;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, (long)count);
    }
}
