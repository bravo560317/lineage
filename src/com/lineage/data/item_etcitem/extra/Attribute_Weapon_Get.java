package com.lineage.data.item_etcitem.extra;

import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.datatables.ItemSpecialAttributeCharTable;
import java.sql.Timestamp;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Attribute_Weapon_Get extends ItemExecutor
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(Attribute_Weapon_Get.class);
        _random = new Random();
    }
    
    public static ItemExecutor get() {
        return new Attribute_Weapon_Get();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int targObjId = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
            if (tgItem == null) {
                return;
            }
            else if (tgItem.getItem().getType2() != 1) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else if (tgItem.isEquipped()) {
                pc.sendPackets(new S_ServerMessage("你必須先解除物品裝備!"));
                return;
            }
            else {
                if (tgItem.get_ItemAttrName() != null && tgItem.get_ItemAttrName().get_attr_id() != 7 && tgItem.get_ItemAttrName().get_attr_id() != 14 && tgItem.get_ItemAttrName().get_attr_id() != 21 && tgItem.get_ItemAttrName().get_attr_id() != 28 && tgItem.get_ItemAttrName().get_attr_id() != 35 && tgItem.get_ItemAttrName().get_attr_id() != 42 && tgItem.get_ItemAttrName().get_attr_id() != 49 && tgItem.get_ItemAttrName().get_attr_id() != 56 && tgItem.get_ItemAttrName().get_attr_id() != 63 && tgItem.get_ItemAttrName().get_attr_id() != 70 && tgItem.get_ItemAttrName().get_attr_id() != 77 && tgItem.get_ItemAttrName().get_attr_id() != 84 && tgItem.get_ItemAttrName().get_attr_id() != 91 && tgItem.get_ItemAttrName().get_attr_id() != 98 && tgItem.get_ItemAttrName().get_attr_id() != 105) {
                    int attr_id = 0;
                    int item_type = tgItem.getItem().getType();
                    switch (item_type) {
                        case 1: {
                            attr_id = 7;
                            break;
                        }
                        case 2: {
                            attr_id = 14;
                            break;
                        }
                        case 3: {
                            attr_id = 21;
                            break;
                        }
                        case 4: {
                            attr_id = 28;
                            break;
                        }
                        case 5: {
                            attr_id = 35;
                            break;
                        }
                        case 6: {
                            attr_id = 42;
                            break;
                        }
                        case 7: {
                            attr_id = 49;
                            break;
                        }
                        case 11: {
                            attr_id = 56;
                            break;
                        }
                        case 12: {
                            attr_id = 63;
                            break;
                        }
                        case 13: {
                            attr_id = 70;
                            break;
                        }
                        case 14: {
                            attr_id = 77;
                            break;
                        }
                        case 15: {
                            attr_id = 84;
                            break;
                        }
                        case 16: {
                            attr_id = 91;
                            break;
                        }
                        case 17: {
                            attr_id = 98;
                            break;
                        }
                        case 18: {
                            attr_id = 105;
                            break;
                        }
                    }
                    if (attr_id > 0) {
                        L1ItemSpecialAttributeChar ItemAttr = new L1ItemSpecialAttributeChar();
                        Timestamp time = new Timestamp(System.currentTimeMillis());
                        ItemAttr.set_itemobjid(tgItem.getId());
                        ItemAttr.set_item_name(tgItem.getNumberedViewName(tgItem.getCount()));
                        ItemAttr.set_attr_id(attr_id);
                        ItemAttr.set_add_pc_name(pc.getName());
                        ItemAttr.set_add_time(time);
                        tgItem.set_ItemAttrName(ItemAttr);
                        ItemAttr.set_add_mon_name("初始化");
                        ItemAttr.set_mapname("無");
                        ItemAttr.set_Acquisition_mode("初始化");
                        ItemSpecialAttributeCharTable.get().updateItem(tgItem.getId(), ItemAttr);
                        ItemSpecialAttributeCharTable.get().storeItem(tgItem.getId(), ItemAttr);
                        pc.sendPackets(new S_ItemName(tgItem));
                        pc.sendPackets(new S_ItemStatus(tgItem));
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(1411, tgItem.getLogName()));
                    }
                }
                int attr_id = 0;
                int item_type = tgItem.getItem().getType();
                int rand = Attribute_Weapon_Get._random.nextInt(100);
                if (pc.isGm()) {
                    rand = -1;
                    pc.sendPackets(new S_ServerMessage("Gm100%成功模式開啟!"));
                }
                switch (item_type) {
                    case 1: {
                        attr_id = 1 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 2: {
                        attr_id = 8 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 3: {
                        attr_id = 15 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 4: {
                        attr_id = 22 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 5: {
                        attr_id = 29 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 6: {
                        attr_id = 36 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 7: {
                        attr_id = 43 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 11: {
                        attr_id = 50 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 12: {
                        attr_id = 57 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 13: {
                        attr_id = 64 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 14: {
                        attr_id = 71 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 15: {
                        attr_id = 78 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 16: {
                        attr_id = 85 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 17: {
                        attr_id = 92 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                    case 18: {
                        attr_id = 99 + Attribute_Weapon_Get._random.nextInt(6);
                        break;
                    }
                }
                L1ItemSpecialAttributeChar ItemAttr2 = new L1ItemSpecialAttributeChar();
                L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_id);
                if (attr_id > 0) {
                    Timestamp time2 = new Timestamp(System.currentTimeMillis());
                    if (pc.isGm()) {
                        pc.sendPackets(new S_ServerMessage("GM顯示:DB機率:" + attr.get_add_rand() + " > 隨機 " + rand));
                    }
                    if (attr.get_add_rand() > rand) {
                        ItemAttr2.set_itemobjid(tgItem.getId());
                        ItemAttr2.set_item_name(tgItem.getNumberedName(tgItem.getCount()));
                        ItemAttr2.set_attr_id(attr_id);
                        ItemAttr2.set_add_pc_name(pc.getName());
                        ItemAttr2.set_add_time(time2);
                        ItemAttr2.set_add_mon_name("武器洗練");
                        ItemAttr2.set_mapname("無");
                        ItemAttr2.set_Acquisition_mode("武器洗練");
                        tgItem.set_ItemAttrName(ItemAttr2);
                        ItemSpecialAttributeCharTable.get().updateItem(tgItem.getId(), ItemAttr2);
                        ItemSpecialAttributeCharTable.get().storeItem(tgItem.getId(), ItemAttr2);
                        pc.sendPackets(new S_ItemName(tgItem));
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        if (attr.get_msg() != null) {
                            World.get().broadcastPacketToAll(new S_SystemMessage(String.format(attr.get_msg(), pc.getName(), tgItem.getLogName_attr(), attr.get_name())));
                        }
                        if (attr.get_msg1() != null) {
                            pc.sendPackets(new S_SystemMessage(String.format(attr.get_msg1(), tgItem.getLogName_attr(), attr.get_name())));
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(1411, tgItem.getLogName()));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(1411, tgItem.getLogName()));
                }
                pc.getInventory().removeItem(item, 1L);
            }
        }
        catch (Exception e) {
            Attribute_Weapon_Get._log.error(e.getLocalizedMessage(), e);
        }
    }
}
