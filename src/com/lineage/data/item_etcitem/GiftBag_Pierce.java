package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class GiftBag_Pierce extends ItemExecutor
{
    public static ItemExecutor get() {
        return new GiftBag_Pierce();
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
            case 5: {
                item_id = 152;
                break;
            }
            case 6:
            case 7:
            case 8:
            case 9:
            case 10: {
                item_id = 69;
                break;
            }
            case 11:
            case 12:
            case 13:
            case 14:
            case 15: {
                item_id = 153;
                break;
            }
            case 16:
            case 17:
            case 18:
            case 19: {
                item_id = 71;
                break;
            }
            case 21:
            case 22:
            case 23:
            case 24: {
                item_id = 154;
                break;
            }
            case 26:
            case 27:
            case 28:
            case 29: {
                item_id = 72;
                break;
            }
            case 31:
            case 32:
            case 33:
            case 34: {
                item_id = 159;
                break;
            }
            case 36:
            case 37:
            case 38:
            case 39: {
                item_id = 77;
                break;
            }
            case 40:
            case 45:
            case 50:
            case 55: {
                item_id = 20132;
                break;
            }
            case 41:
            case 42:
            case 43:
            case 44: {
                item_id = 161;
                break;
            }
            case 46:
            case 47:
            case 48:
            case 49: {
                item_id = 80;
                break;
            }
            case 51:
            case 52:
            case 53:
            case 54: {
                item_id = 158;
                break;
            }
            case 56:
            case 57:
            case 58:
            case 59: {
                item_id = 75;
                break;
            }
            case 60:
            case 65:
            case 70:
            case 90: {
                item_id = 20180;
                break;
            }
            case 61:
            case 62:
            case 63:
            case 64: {
                item_id = 168;
                break;
            }
            case 66:
            case 67:
            case 68:
            case 69: {
                item_id = 81;
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74: {
                item_id = 40739;
                count = 1000;
                break;
            }
            case 75:
            case 80:
            case 85:
            case 95: {
                item_id = 20070;
                break;
            }
            case 76:
            case 77:
            case 78:
            case 79: {
                item_id = 40738;
                count = 1000;
                break;
            }
            case 81:
            case 82:
            case 83:
            case 84: {
                item_id = 40740;
                count = 500;
                break;
            }
            case 86:
            case 87:
            case 88:
            case 89: {
                item_id = 162;
                break;
            }
            case 91:
            case 92:
            case 93:
            case 94: {
                item_id = 177;
                break;
            }
            case 96:
            case 97:
            case 98:
            case 99: {
                item_id = 20032;
                break;
            }
            default: {
                item_id = 20210;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, (long)count);
    }
}
