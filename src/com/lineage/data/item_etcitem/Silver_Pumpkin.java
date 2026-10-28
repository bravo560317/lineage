package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Silver_Pumpkin extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Silver_Pumpkin();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 1;
        int k = (int)(Math.random() * 100.0);
        switch (k) {
            case 0:
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
            case 11: {
                item_id = 40021;
                count = 9;
                break;
            }
            case 12:
            case 13:
            case 14:
            case 15:
            case 16: {
                item_id = 40024;
                count = 20;
                break;
            }
            case 17:
            case 18:
            case 19:
            case 20:
            case 21: {
                item_id = 40015;
                count = 20;
                break;
            }
            case 22:
            case 23:
            case 24:
            case 25:
            case 26: {
                item_id = 40068;
                count = 5;
                break;
            }
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36: {
                item_id = 40093;
                count = 8;
                break;
            }
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46: {
                item_id = 40094;
                count = 8;
                break;
            }
            case 47:
            case 48:
            case 49:
            case 50:
            case 51: {
                item_id = 40524;
                break;
            }
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59: {
                item_id = 40074;
                break;
            }
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66: {
                item_id = 40087;
                break;
            }
            case 67:
            case 68:
            case 69:
            case 70:
            case 71: {
                item_id = 40212;
                break;
            }
            case 72:
            case 73:
            case 74:
            case 75:
            case 76: {
                item_id = 40218;
                break;
            }
            case 77:
            case 78:
            case 79:
            case 80:
            case 81: {
                item_id = 140068;
                break;
            }
            case 82:
            case 83: {
                item_id = 100004;
                break;
            }
            case 84:
            case 85: {
                item_id = 100025;
                break;
            }
            case 86:
            case 87: {
                item_id = 100062;
                break;
            }
            case 88:
            case 89: {
                item_id = 100099;
                break;
            }
            case 90:
            case 91: {
                item_id = 100132;
                break;
            }
            case 92:
            case 93: {
                item_id = 100169;
                break;
            }
            case 94:
            case 95: {
                item_id = 120011;
                break;
            }
            case 96:
            case 97: {
                item_id = 120085;
                break;
            }
            default: {
                item_id = 120137;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, (long)count);
    }
}
