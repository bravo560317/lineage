package com.lineage.data.cmd;

import com.lineage.config.ConfigRate;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public abstract class EnchantExecutor
{
    public abstract void failureEnchant(L1PcInstance p0, L1ItemInstance p1);
    
    public abstract void successEnchant(L1PcInstance p0, L1ItemInstance p1, int p2);
    
    public int randomELevel(L1ItemInstance item, int bless) {
        int level = 0;
        switch (bless) {
            case 0:
            case 128: {
                if (item.getBless() < 3) {
                    Random random = new Random();
                    int i = random.nextInt(100);
                    if (item.getItem().getType2() == 1) {
                        if (item.getEnchantLevel() >= -1 && item.getEnchantLevel() <= 2) {
                            if (i <= ConfigRate.ran3) {
                                level = 1;
                                break;
                            }
                            else if (i <= ConfigRate.ran2) {
                                level = 2;
                                break;
                            }
                            else if (i <= ConfigRate.ran1) {
                                level = 3;
                                break;
                            }
                            else {
                                level = 1;
                                break;
                            }
                        }
                        else if (item.getEnchantLevel() >= 3 && item.getEnchantLevel() <= 5) {
                            if (i <= ConfigRate.ran3) {
                                level = 1;
                                break;
                            }
                            else if (i <= ConfigRate.ran2) {
                                level = 2;
                                break;
                            }
                            else if (i <= ConfigRate.ran1) {
                                level = 2;
                                break;
                            }
                            else {
                                level = 1;
                                break;
                            }
                        }
                        else if (item.getEnchantLevel() >= 6) {
                            level = 1;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else if (item.getItem().getType2() == 2) {
                        if (item.getEnchantLevel() >= -1 && item.getEnchantLevel() <= 2) {
                            if (i <= ConfigRate.ran8) {
                                level = 1;
                                break;
                            }
                            else if (i <= ConfigRate.ran7) {
                                level = 2;
                                break;
                            }
                            else if (i <= ConfigRate.ran6) {
                                level = 3;
                                break;
                            }
                            else {
                                level = 1;
                                break;
                            }
                        }
                        else if (item.getEnchantLevel() >= 3 && item.getEnchantLevel() <= 5) {
                            if (i <= ConfigRate.ran8) {
                                level = 1;
                                break;
                            }
                            else if (i <= ConfigRate.ran7) {
                                level = 2;
                                break;
                            }
                            else if (i <= ConfigRate.ran6) {
                                level = 2;
                                break;
                            }
                            else {
                                level = 1;
                                break;
                            }
                        }
                        else if (item.getEnchantLevel() >= 6) {
                            level = 1;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
            case 1:
            case 129: {
                if (item.getBless() < 3) {
                    level = 1;
                    break;
                }
                else {
                    break;
                }
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
