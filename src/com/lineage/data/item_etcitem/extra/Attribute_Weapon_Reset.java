package com.lineage.data.item_etcitem.extra;

import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.datatables.ItemSpecialAttributeCharTable;
import java.sql.Timestamp;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Attribute_Weapon_Reset extends ItemExecutor
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(Attribute_Weapon_Reset.class);
        _random = new Random();
    }
    
    public static ItemExecutor get() {
        return new Attribute_Weapon_Reset();
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
            else if (tgItem.get_ItemAttrName() == null) {
                pc.sendPackets(new S_ServerMessage("此物品沒有具有特殊顏色，無需重置!"));
                return;
            }
            else if (tgItem.get_ItemAttrName().get_attr_id() == 7 || tgItem.get_ItemAttrName().get_attr_id() == 14 || tgItem.get_ItemAttrName().get_attr_id() == 21 || tgItem.get_ItemAttrName().get_attr_id() == 28 || tgItem.get_ItemAttrName().get_attr_id() == 35 || tgItem.get_ItemAttrName().get_attr_id() == 42 || tgItem.get_ItemAttrName().get_attr_id() == 49 || tgItem.get_ItemAttrName().get_attr_id() == 56 || tgItem.get_ItemAttrName().get_attr_id() == 63 || tgItem.get_ItemAttrName().get_attr_id() == 70 || tgItem.get_ItemAttrName().get_attr_id() == 77 || tgItem.get_ItemAttrName().get_attr_id() == 84 || tgItem.get_ItemAttrName().get_attr_id() == 91 || tgItem.get_ItemAttrName().get_attr_id() == 98 || tgItem.get_ItemAttrName().get_attr_id() == 105) {
                pc.sendPackets(new S_ServerMessage("此物品沒有具有特殊顏色，無需重置!"));
                return;
            }
            else if (tgItem.isEquipped()) {
                pc.sendPackets(new S_ServerMessage("你必須先解除物品裝備!"));
                return;
            }
            else {
                int attr_id = 0;
                int rand = Attribute_Weapon_Reset._random.nextInt(100);
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
                    L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_id);
                    Timestamp time = new Timestamp(System.currentTimeMillis());
                    ItemAttr.set_itemobjid(tgItem.getId());
                    ItemAttr.set_item_name(tgItem.getNumberedViewName(tgItem.getCount()));
                    ItemAttr.set_attr_id(attr_id);
                    ItemAttr.set_add_pc_name(pc.getName());
                    ItemAttr.set_add_time(time);
                    ItemAttr.set_add_mon_name("初始化");
                    ItemAttr.set_Acquisition_mode("初始化");
                    tgItem.set_ItemAttrName(ItemAttr);
                    ItemSpecialAttributeCharTable.get().updateItem(tgItem.getId(), ItemAttr);
                    ItemSpecialAttributeCharTable.get().storeItem(tgItem.getId(), ItemAttr);
                    pc.sendPackets(new S_ItemName(tgItem));
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.sendPackets(new S_ServerMessage("已成功把" + tgItem.getLogName() + "洗白了！"));
                }
                else {
                    pc.sendPackets(new S_ServerMessage(1411, tgItem.getLogName()));
                }
                pc.getInventory().removeItem(item, 1L);
            }
        }
        catch (Exception e) {
            Attribute_Weapon_Reset._log.error(e.getLocalizedMessage(), e);
        }
    }
}
