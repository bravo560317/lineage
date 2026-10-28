package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Caliph_Pouch_Sapphire extends ItemExecutor
{
    private Caliph_Pouch_Sapphire() {
    }
    
    public static ItemExecutor get() {
        return new Caliph_Pouch_Sapphire();
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
            case 10: {
                item_id = 40010;
                count = 5;
                break;
            }
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20: {
                item_id = 40013;
                count = 2;
                break;
            }
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30: {
                item_id = 40408;
                count = 2;
                break;
            }
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40: {
                item_id = 40015;
                count = 2;
                break;
            }
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50: {
                item_id = 40318;
                break;
            }
            case 51:
            case 52:
            case 53:
            case 54:
            case 55: {
                item_id = 40779;
                break;
            }
            case 56:
            case 57:
            case 58:
            case 59:
            case 60: {
                item_id = 40016;
                break;
            }
            case 61:
            case 62:
            case 63:
            case 64:
            case 65: {
                item_id = 40494;
                count = 5;
                break;
            }
            case 66:
            case 67:
            case 68:
            case 69:
            case 70: {
                item_id = 40068;
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
                item_id = 40398;
                break;
            }
            case 76:
            case 77:
            case 78:
            case 79:
            case 80: {
                item_id = 40521;
                break;
            }
            case 81:
            case 82:
            case 83:
            case 84:
            case 85: {
                item_id = 40444;
                break;
            }
            case 86:
            case 87:
            case 88:
            case 89:
            case 90: {
                item_id = 40489;
                break;
            }
            case 91:
            case 92:
            case 93:
            case 94:
            case 95: {
                item_id = 40441;
                break;
            }
            default: {
                item_id = 40307;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, (long)count);
    }
}
