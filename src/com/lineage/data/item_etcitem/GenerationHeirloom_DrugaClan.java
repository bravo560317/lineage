package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class GenerationHeirloom_DrugaClan extends ItemExecutor
{
    public static ItemExecutor get() {
        return new GenerationHeirloom_DrugaClan();
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
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 26:
            case 27:
            case 31:
            case 36:
            case 37:
            case 53:
            case 54:
            case 62:
            case 63:
            case 80:
            case 81:
            case 89: {
                item_id = 40308;
                count = 1000000;
                break;
            }
            case 16:
            case 22:
            case 33:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 73: {
                item_id = 40076;
                break;
            }
            case 17:
            case 18:
            case 19:
            case 20:
            case 52:
            case 55:
            case 56:
            case 57: {
                item_id = 20153;
                break;
            }
            case 21:
            case 23:
            case 24:
            case 25:
            case 60:
            case 61:
            case 64:
            case 65: {
                item_id = 20130;
                break;
            }
            case 28:
            case 29:
            case 30:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72: {
                item_id = 20119;
                break;
            }
            case 32:
            case 34:
            case 35:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88: {
                item_id = 20108;
                break;
            }
            case 38:
            case 39:
            case 76:
            case 77:
            case 78:
            case 79: {
                item_id = 66;
                break;
            }
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97: {
                item_id = 40666;
                break;
            }
            case 58:
            case 59: {
                item_id = 40033;
                break;
            }
            case 66:
            case 67: {
                item_id = 40035;
                break;
            }
            case 74:
            case 75: {
                item_id = 40034;
                break;
            }
            case 82:
            case 83: {
                item_id = 40036;
                break;
            }
            case 90:
            case 91: {
                item_id = 40037;
                break;
            }
            case 98:
            case 99: {
                item_id = 40038;
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem(pc, item_id, (long)count);
    }
}
