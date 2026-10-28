package com.lineage.data.cmd;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public abstract class EnchantExecutor_Acc
{
    public abstract void failureEnchant(L1PcInstance p0, L1ItemInstance p1);
    
    public abstract void successEnchant(L1PcInstance p0, L1ItemInstance p1, int p2);
    
    public int randomELevel(L1ItemInstance item, int bless) {
        int level = 0;
        switch (bless) {
            case 0:
            case 128: {
                level = 1;
                break;
            }
            case 1:
            case 129: {
                level = 1;
                break;
            }
            case 2:
            case 130: {
                if (item.getBless() < 3) {
                    level = -1;
                    break;
                }
                else {
                    break;
                }
            }
            case 3:
            case 131: {
                if (item.getBless() == 3) {
                    level = 1;
                    break;
                }
                else {
                    break;
                }
            }
        }
        return level;
    }
}
