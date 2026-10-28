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

public class Attribute_Armor_Reset extends ItemExecutor
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(Attribute_Armor_Reset.class);
        _random = new Random();
    }
    
    public static ItemExecutor get() {
        return new Attribute_Armor_Reset();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int targObjId = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
            if (tgItem == null) {
                return;
            }
            else if (tgItem.getItem().getType2() != 2) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else if (tgItem.get_ItemAttrName() == null) {
                pc.sendPackets(new S_ServerMessage("此物品沒有具有特殊顏色，無需重置!"));
                return;
            }
            else if (tgItem.get_ItemAttrName().get_attr_id() == 112 || tgItem.get_ItemAttrName().get_attr_id() == 119 || tgItem.get_ItemAttrName().get_attr_id() == 126 || tgItem.get_ItemAttrName().get_attr_id() == 133 || tgItem.get_ItemAttrName().get_attr_id() == 140 || tgItem.get_ItemAttrName().get_attr_id() == 147 || tgItem.get_ItemAttrName().get_attr_id() == 154 || tgItem.get_ItemAttrName().get_attr_id() == 161 || tgItem.get_ItemAttrName().get_attr_id() == 168 || tgItem.get_ItemAttrName().get_attr_id() == 175 || tgItem.get_ItemAttrName().get_attr_id() == 182 || tgItem.get_ItemAttrName().get_attr_id() == 189) {
                pc.sendPackets(new S_ServerMessage("此物品沒有具有特殊顏色，無需重置!"));
                return;
            }
            else if (tgItem.isEquipped()) {
                pc.sendPackets(new S_ServerMessage("你必須先解除物品裝備!"));
                return;
            }
            else {
                int attr_id = 0;
                int item_type = tgItem.getItem().getType();
                switch (item_type) {
                    case 1: {
                        attr_id = 112;
                        break;
                    }
                    case 2: {
                        attr_id = 119;
                        break;
                    }
                    case 3: {
                        attr_id = 126;
                        break;
                    }
                    case 4: {
                        attr_id = 133;
                        break;
                    }
                    case 5: {
                        attr_id = 140;
                        break;
                    }
                    case 6: {
                        attr_id = 147;
                        break;
                    }
                    case 7: {
                        attr_id = 154;
                        break;
                    }
                    case 8: {
                        attr_id = 161;
                        break;
                    }
                    case 9:
                    case 11: {
                        attr_id = 168;
                        break;
                    }
                    case 10: {
                        attr_id = 175;
                        break;
                    }
                    case 12: {
                        attr_id = 182;
                        break;
                    }
                    case 13: {
                        attr_id = 189;
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
            Attribute_Armor_Reset._log.error(e.getLocalizedMessage(), e);
        }
    }
}
