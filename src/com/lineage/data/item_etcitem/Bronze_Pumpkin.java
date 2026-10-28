package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Bronze_Pumpkin extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Bronze_Pumpkin();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = 0;
        int count = 1;
        short k = (short)(int)(Math.random() * 100.0);
        switch (k) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4: {
                item_id = 40028;
                count = 60;
                break;
            }
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: {
                item_id = 40058;
                count = 3;
                break;
            }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14: {
                item_id = 40071;
                break;
            }
            case 15:
            case 16:
            case 17:
            case 18:
            case 19: {
                item_id = 40018;
                break;
            }
            case 20:
            case 21:
            case 22:
            case 23:
            case 24: {
                item_id = 40088;
                count = 3;
                break;
            }
            case 25:
            case 26:
            case 27:
            case 28:
            case 29: {
                item_id = 40015;
                count = 5;
                break;
            }
            case 30:
            case 31:
            case 32:
            case 33:
            case 34: {
                item_id = 40318;
                count = 20;
                break;
            }
            case 35:
            case 36:
            case 37:
            case 38:
            case 39: {
                item_id = 40014;
                count = 10;
                break;
            }
            case 40:
            case 41:
            case 42:
            case 43:
            case 44: {
                item_id = 40068;
                break;
            }
            case 45:
            case 46:
            case 47:
            case 48:
            case 49: {
                item_id = 40089;
                count = 10;
                break;
            }
            case 50:
            case 51:
            case 52:
            case 53:
            case 54: {
                item_id = 40043;
                break;
            }
            case 55:
            case 56:
            case 57:
            case 58:
            case 59: {
                item_id = 20345;
                break;
            }
            case 60:
            case 61:
            case 62:
            case 63:
            case 64: {
                item_id = 20346;
                break;
            }
            case 65:
            case 66:
            case 67:
            case 68:
            case 69: {
                item_id = 20361;
                break;
            }
            case 70:
            case 71:
            case 72:
            case 73:
            case 74: {
                item_id = 140061;
                break;
            }
            case 75:
            case 76:
            case 77:
            case 78:
            case 79: {
                item_id = 140062;
                break;
            }
            case 80:
            case 81:
            case 82:
            case 83:
            case 84: {
                item_id = 140065;
                break;
            }
            case 85:
            case 86:
            case 87:
            case 88:
            case 89: {
                item_id = 140069;
                break;
            }
            case 90:
            case 91:
            case 92:
            case 93:
            case 94: {
                item_id = 140072;
                break;
            }
            default: {
                item_id = 140506;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, (long)count);
    }
}
