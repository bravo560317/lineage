package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigRate;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class High_Quality_Diamond extends ItemExecutor
{
    public static ItemExecutor get() {
        return new High_Quality_Diamond();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else {
            Random _random = new Random();
            if (itemId >= 40943 && 40958 >= itemId) {
                int ringId = item2.getItem().getItemId();
                int ringlevel = 0;
                int gmas = 0;
                int gmam = 0;
                if (ringId >= 41185 && 41200 >= ringId) {
                    if (itemId == 40943 || itemId == 40947 || itemId == 40951 || itemId == 40955) {
                        gmas = 443;
                        gmam = 447;
                    }
                    else if (itemId == 40944 || itemId == 40948 || itemId == 40952 || itemId == 40956) {
                        gmas = 442;
                        gmam = 446;
                    }
                    else if (itemId == 40945 || itemId == 40949 || itemId == 40953 || itemId == 40957) {
                        gmas = 441;
                        gmam = 445;
                    }
                    else if (itemId == 40946 || itemId == 40950 || itemId == 40954 || itemId == 40958) {
                        gmas = 444;
                        gmam = 448;
                    }
                    if (ringId == itemId + 242) {
                        if (_random.nextInt(99) + 1 < ConfigRate.CREATE_CHANCE_PROCESSING_DIAMOND) {
                            if (ringId == 41185) {
                                ringlevel = 20435;
                            }
                            else if (ringId == 41186) {
                                ringlevel = 20436;
                            }
                            else if (ringId == 41187) {
                                ringlevel = 20437;
                            }
                            else if (ringId == 41188) {
                                ringlevel = 20438;
                            }
                            else if (ringId == 41189) {
                                ringlevel = 20439;
                            }
                            else if (ringId == 41190) {
                                ringlevel = 20440;
                            }
                            else if (ringId == 41191) {
                                ringlevel = 20441;
                            }
                            else if (ringId == 41192) {
                                ringlevel = 20442;
                            }
                            else if (ringId == 41193) {
                                ringlevel = 20443;
                            }
                            else if (ringId == 41194) {
                                ringlevel = 20444;
                            }
                            else if (ringId == 41195) {
                                ringlevel = 20445;
                            }
                            else if (ringId == 41196) {
                                ringlevel = 20446;
                            }
                            else if (ringId == 41197) {
                                ringlevel = 20447;
                            }
                            else if (ringId == 41198) {
                                ringlevel = 20448;
                            }
                            else if (ringId == 41199) {
                                ringlevel = 20449;
                            }
                            else if (ringId == 41200) {
                                ringlevel = 20450;
                            }
                            pc.sendPackets(new S_ServerMessage(gmas, item2.getName()));
                            CreateNewItem.createNewItem(pc, ringlevel, 1L);
                            pc.getInventory().removeItem(item2, 1L);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(gmam, item.getName()));
                            pc.getInventory().removeItem(item, 1L);
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(79));
                }
            }
            return;
        }
    }
}
