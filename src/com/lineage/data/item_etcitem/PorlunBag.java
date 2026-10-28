package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class PorlunBag extends ItemExecutor
{
    public static ItemExecutor get() {
        return new PorlunBag();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 1;
        int k = (int)(Math.random() * 100.0);
        switch (k) {
            case 1: {
                item_id = 140074;
                break;
            }
            case 2: {
                item_id = 140087;
                break;
            }
            case 3:
            case 93:
            case 94:
            case 95:
            case 98: {
                item_id = 40074;
                break;
            }
            case 4:
            case 88:
            case 89:
            case 96:
            case 97: {
                item_id = 40087;
                break;
            }
            case 5:
            case 17: {
                item_id = 40441;
                break;
            }
            case 6:
            case 9:
            case 90: {
                item_id = 40458;
                break;
            }
            case 7:
            case 91: {
                item_id = 40444;
                break;
            }
            case 8:
            case 92: {
                item_id = 40508;
                break;
            }
            case 10: {
                item_id = 40412;
                break;
            }
            case 11:
            case 14: {
                item_id = 40304;
                break;
            }
            case 12:
            case 15: {
                item_id = 40305;
                break;
            }
            case 13:
            case 16: {
                item_id = 40306;
                break;
            }
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                item_id = 40410;
                break;
            }
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67: {
                item_id = 40024;
                break;
            }
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77: {
                item_id = 40023;
                break;
            }
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87: {
                item_id = 40022;
                break;
            }
            default: {
                item_id = 40307;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, 1L);
    }
}
