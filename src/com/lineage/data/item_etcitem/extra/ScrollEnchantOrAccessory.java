package com.lineage.data.item_etcitem.extra;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.data.cmd.EnchantExecutor_Acc;
import com.lineage.server.serverpackets.S_ItemStatus;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.config.ConfigOrAccessory;
import java.util.Random;
import com.lineage.data.cmd.EnchantAccessory_Acc;
import com.lineage.server.datatables.ItemNoENTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ScrollEnchantOrAccessory extends ItemExecutor
{
    private ScrollEnchantOrAccessory() {
    }
    
    public static ItemExecutor get() {
        return new ScrollEnchantOrAccessory();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.isEquipped()) {
            pc.sendPackets(new S_ServerMessage("(預防誤點機制啟動)裝備中無法強化"));
            return;
        }
        else if (ItemNoENTable.RESTRICTIONS.contains((Integer)tgItem.getItemId())) {
            pc.sendPackets(new S_ServerMessage("該物品禁止強化。"));
            return;
        }
        else {
            int safe_enchant = tgItem.getItem().get_safeenchant();
            boolean isErr = false;
            int use_type = tgItem.getItem().getUseType();
            switch (use_type) {
                case 23:
                case 24:
                case 37:
                case 40: {
                    if (tgItem.getItem().get_greater() == 3) {
                        isErr = true;
                    }
                    if (safe_enchant < 0) {
                        isErr = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                default: {
                    isErr = true;
                    break;
                }
            }
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                int enchant_level = tgItem.getEnchantLevel();
                if (enchant_level == 9) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else {
                    EnchantExecutor_Acc enchantExecutor = new EnchantAccessory_Acc();
                    int randomELevel = enchantExecutor.randomELevel(tgItem, item.getBless());
                    pc.getInventory().removeItem(item, 1L);
                    Random random = new Random();
                    int lv = 0;
                    if (tgItem.getEnchantLevel() == 0) {
                        lv = ConfigOrAccessory.ran1;
                    }
                    else if (tgItem.getEnchantLevel() == 1) {
                        lv = ConfigOrAccessory.ran2;
                    }
                    else if (tgItem.getEnchantLevel() == 2) {
                        lv = ConfigOrAccessory.ran3;
                    }
                    else if (tgItem.getEnchantLevel() == 3) {
                        lv = ConfigOrAccessory.ran4;
                    }
                    else if (tgItem.getEnchantLevel() == 4) {
                        lv = ConfigOrAccessory.ran5;
                    }
                    else if (tgItem.getEnchantLevel() == 5) {
                        lv = ConfigOrAccessory.ran6;
                    }
                    else if (tgItem.getEnchantLevel() == 6) {
                        lv = ConfigOrAccessory.ran7;
                    }
                    else if (tgItem.getEnchantLevel() == 7) {
                        lv = ConfigOrAccessory.ran8;
                    }
                    else if (tgItem.getEnchantLevel() == 8) {
                        lv = ConfigOrAccessory.ran9;
                    }
                    if (random.nextInt(100) <= lv) {
                        enchantExecutor.successEnchant(pc, tgItem, randomELevel);
                        pc.sendPackets(new S_SystemMessage(String.format(ConfigOrAccessory.msg1, tgItem.getName())));
                        飾品強化成功記錄表("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "的" + "【  " + tgItem.getEnchantLevel() + " " + tgItem.getName() + "】- (強化成功)" + "時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
                    }
                    else {
                        if (tgItem.getEnchantLevel() == 0 && ConfigOrAccessory.removeitem1 == 1) {
                            enchantExecutor.failureEnchant(pc, tgItem);
                            pc.sendPackets(new S_SystemMessage(String.format(ConfigOrAccessory.msg2, tgItem.getName())));
                        }
                        else if (tgItem.getEnchantLevel() == 0 && ConfigOrAccessory.removeitem1 == 2) {
                            if (tgItem.getEnchantLevel() > 0) {
                                tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                                pc.sendPackets(new S_ItemStatus(tgItem));
                                pc.getInventory().updateItem(tgItem, 4);
                                pc.getInventory().saveItem(tgItem, 4);
                                pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg3, tgItem.getName())));
                            }
                        }
                        else if (tgItem.getEnchantLevel() == 0 && ConfigOrAccessory.removeitem1 == -1) {
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg4, tgItem.getName())));
                        }
                        if (tgItem.getEnchantLevel() == 1 && ConfigOrAccessory.removeitem2 == 1) {
                            enchantExecutor.failureEnchant(pc, tgItem);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg2, tgItem.getName())));
                        }
                        else if (tgItem.getEnchantLevel() == 1 && ConfigOrAccessory.removeitem2 == 2) {
                            if (tgItem.getEnchantLevel() > 1) {
                                tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                                pc.sendPackets(new S_ItemStatus(tgItem));
                                pc.getInventory().updateItem(tgItem, 4);
                                pc.getInventory().saveItem(tgItem, 4);
                                pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg3, tgItem.getName())));
                            }
                        }
                        else if (tgItem.getEnchantLevel() == 1 && ConfigOrAccessory.removeitem2 == -1) {
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg4, tgItem.getName())));
                        }
                        if (tgItem.getEnchantLevel() == 2 && ConfigOrAccessory.removeitem3 == 1) {
                            enchantExecutor.failureEnchant(pc, tgItem);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg2, tgItem.getName())));
                        }
                        else if (tgItem.getEnchantLevel() == 2 && ConfigOrAccessory.removeitem3 == 2) {
                            if (tgItem.getEnchantLevel() > 0) {
                                tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                                pc.sendPackets(new S_ItemStatus(tgItem));
                                pc.getInventory().updateItem(tgItem, 4);
                                pc.getInventory().saveItem(tgItem, 4);
                                pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg3, tgItem.getName())));
                            }
                        }
                        else if (tgItem.getEnchantLevel() == 2 && ConfigOrAccessory.removeitem3 == -1) {
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg4, tgItem.getName())));
                        }
                        if (tgItem.getEnchantLevel() == 3 && ConfigOrAccessory.removeitem4 == 1) {
                            enchantExecutor.failureEnchant(pc, tgItem);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg2, tgItem.getName())));
                        }
                        else if (tgItem.getEnchantLevel() == 3 && ConfigOrAccessory.removeitem4 == 2) {
                            if (tgItem.getEnchantLevel() > 0) {
                                tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                                pc.sendPackets(new S_ItemStatus(tgItem));
                                pc.getInventory().updateItem(tgItem, 4);
                                pc.getInventory().saveItem(tgItem, 4);
                                pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg3, tgItem.getName())));
                            }
                        }
                        else if (tgItem.getEnchantLevel() == 3 && ConfigOrAccessory.removeitem4 == -1) {
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg4, tgItem.getName())));
                        }
                        if (tgItem.getEnchantLevel() == 4 && ConfigOrAccessory.removeitem5 == 1) {
                            enchantExecutor.failureEnchant(pc, tgItem);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg2, tgItem.getName())));
                        }
                        else if (tgItem.getEnchantLevel() == 4 && ConfigOrAccessory.removeitem5 == 2) {
                            if (tgItem.getEnchantLevel() > 0) {
                                tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                                pc.sendPackets(new S_ItemStatus(tgItem));
                                pc.getInventory().updateItem(tgItem, 4);
                                pc.getInventory().saveItem(tgItem, 4);
                                pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg3, tgItem.getName())));
                            }
                        }
                        else if (tgItem.getEnchantLevel() == 4 && ConfigOrAccessory.removeitem5 == -1) {
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg4, tgItem.getName())));
                        }
                        if (tgItem.getEnchantLevel() == 5 && ConfigOrAccessory.removeitem6 == 1) {
                            enchantExecutor.failureEnchant(pc, tgItem);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg2, tgItem.getName())));
                        }
                        else if (tgItem.getEnchantLevel() == 5 && ConfigOrAccessory.removeitem6 == 2) {
                            if (tgItem.getEnchantLevel() > 0) {
                                tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                                pc.sendPackets(new S_ItemStatus(tgItem));
                                pc.getInventory().updateItem(tgItem, 4);
                                pc.getInventory().saveItem(tgItem, 4);
                                pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg3, tgItem.getName())));
                            }
                        }
                        else if (tgItem.getEnchantLevel() == 5 && ConfigOrAccessory.removeitem6 == -1) {
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg4, tgItem.getName())));
                        }
                        if (tgItem.getEnchantLevel() == 6 && ConfigOrAccessory.removeitem7 == 1) {
                            enchantExecutor.failureEnchant(pc, tgItem);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg2, tgItem.getName())));
                        }
                        else if (tgItem.getEnchantLevel() == 6 && ConfigOrAccessory.removeitem7 == 2) {
                            if (tgItem.getEnchantLevel() > 0) {
                                tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                                pc.sendPackets(new S_ItemStatus(tgItem));
                                pc.getInventory().updateItem(tgItem, 4);
                                pc.getInventory().saveItem(tgItem, 4);
                                pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg3, tgItem.getName())));
                            }
                        }
                        else if (tgItem.getEnchantLevel() == 6 && ConfigOrAccessory.removeitem7 == -1) {
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg4, tgItem.getName())));
                        }
                        if (tgItem.getEnchantLevel() == 7 && ConfigOrAccessory.removeitem8 == 1) {
                            enchantExecutor.failureEnchant(pc, tgItem);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg2, tgItem.getName())));
                        }
                        else if (tgItem.getEnchantLevel() == 7 && ConfigOrAccessory.removeitem8 == 2) {
                            if (tgItem.getEnchantLevel() > 0) {
                                tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                                pc.sendPackets(new S_ItemStatus(tgItem));
                                pc.getInventory().updateItem(tgItem, 4);
                                pc.getInventory().saveItem(tgItem, 4);
                                pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg3, tgItem.getName())));
                            }
                        }
                        else if (tgItem.getEnchantLevel() == 7 && ConfigOrAccessory.removeitem8 == -1) {
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg4, tgItem.getName())));
                        }
                        if (tgItem.getEnchantLevel() == 8 && ConfigOrAccessory.removeitem9 == 1) {
                            enchantExecutor.failureEnchant(pc, tgItem);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg2, tgItem.getName())));
                        }
                        else if (tgItem.getEnchantLevel() == 8 && ConfigOrAccessory.removeitem9 == 2) {
                            if (tgItem.getEnchantLevel() > 0) {
                                tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                                pc.sendPackets(new S_ItemStatus(tgItem));
                                pc.getInventory().updateItem(tgItem, 4);
                                pc.getInventory().saveItem(tgItem, 4);
                                pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg3, tgItem.getName())));
                            }
                        }
                        else if (tgItem.getEnchantLevel() == 8 && ConfigOrAccessory.removeitem9 == -1) {
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(String.format(ConfigOrAccessory.msg4, tgItem.getName())));
                        }
                        飾品強化失敗記錄表("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "的" + "【  " + tgItem.getEnchantLevel() + " " + tgItem.getName() + "】- (爆了)" + "時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
                    }
                    return;
                }
            }
        }
    }
    
    public static void 飾品強化失敗記錄表(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[飾品強化[失敗]記錄表].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void 飾品強化成功記錄表(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[飾品強化[成功]記錄表].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
