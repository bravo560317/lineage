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

public class Attribute_Armor_Get extends ItemExecutor {

    private static final Log _log = LogFactory.getLog(Attribute_Armor_Get.class);
    private static final Random _random = new Random();

    public static ItemExecutor get() {
        return new Attribute_Armor_Get();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int targObjId = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);

            if (tgItem == null) return;

            // 必須為防具
            if (tgItem.getItem().getType2() != 2) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }

            // 不能穿著
            if (tgItem.isEquipped()) {
                pc.sendPackets(new S_ServerMessage("你必須先解除物品裝備!"));
                return;
            }

            // ============================
            // 第一次初始化（設置基本 attr_id）
            // ============================
            if (tgItem.get_ItemAttrName() != null) {
                int check = tgItem.get_ItemAttrName().get_attr_id();

                if (check != 112 && check != 119 && check != 126 && check != 133
                        && check != 140 && check != 147 && check != 154 && check != 161
                        && check != 167 && check != 175 && check != 182 && check != 189
                        && check != 196 && check != 203 && check != 210 && check != 217
                        && check != 224) { // ★ 新增脛甲初始化 224
                }
            } else {
                int attr_id = 0;
                int item_type = tgItem.getItem().getType();

                switch (item_type) {
                    case 1: attr_id = 112; break;
                    case 2: attr_id = 119; break;
                    case 3: attr_id = 126; break;
                    case 4: attr_id = 133; break;
                    case 5: attr_id = 140; break;
                    case 6: attr_id = 147; break;
                    case 7: attr_id = 154; break;
                    case 8: attr_id = 217; break;
                    case 9:
                    case 11: attr_id = 196; break;
                    case 10: attr_id = 210; break;
                    case 12: attr_id = 203; break;
                    case 13: attr_id = 189; break;
                    case 17: attr_id = 224; break; // ★★ 加入脛甲初始化
                }

                if (attr_id > 0) {
                    L1ItemSpecialAttributeChar ItemAttr = new L1ItemSpecialAttributeChar();
                    Timestamp time = new Timestamp(System.currentTimeMillis());

                    ItemAttr.set_itemobjid(tgItem.getId());
                    ItemAttr.set_item_name(tgItem.getNumberedViewName(tgItem.getCount()));
                    ItemAttr.set_attr_id(attr_id);
                    ItemAttr.set_add_pc_name(pc.getName());
                    ItemAttr.set_add_time(time);
                    ItemAttr.set_add_mon_name("初始化");
                    ItemAttr.set_mapname("無");
                    ItemAttr.set_Acquisition_mode("初始化");

                    tgItem.set_ItemAttrName(ItemAttr);
                    ItemSpecialAttributeCharTable.get().updateItem(tgItem.getId(), ItemAttr);
                    ItemSpecialAttributeCharTable.get().storeItem(tgItem.getId(), ItemAttr);
                    pc.sendPackets(new S_ItemName(tgItem));
                    pc.sendPackets(new S_ItemStatus(tgItem));
                } else {
                    pc.sendPackets(new S_ServerMessage(1411, tgItem.getLogName()));
                }
            }

            // ============================
            // 第二階段：隨機洗 6 種屬性
            // ============================

            int item_type = tgItem.getItem().getType();
            int attr_id = 0;
            int rand = Attribute_Armor_Get._random.nextInt(100);

            if (pc.isGm()) {
                rand = -1;
                pc.sendPackets(new S_ServerMessage("Gm100%成功模式開啟!"));
            }

            switch (item_type) {
                case 1:  attr_id = 106 + _random.nextInt(6); break;
                case 2:  attr_id = 113 + _random.nextInt(6); break;
                case 3:  attr_id = 120 + _random.nextInt(6); break;
                case 4:  attr_id = 127 + _random.nextInt(6); break;
                case 5:  attr_id = 134 + _random.nextInt(6); break;
                case 6:  attr_id = 141 + _random.nextInt(6); break;
                case 7:  attr_id = 148 + _random.nextInt(6); break;
                case 8:  attr_id = 211 + _random.nextInt(6); break;
                case 9:
                case 11: attr_id = 190 + _random.nextInt(6); break;
                case 10: attr_id = 204 + _random.nextInt(6); break;
                case 12: attr_id = 197 + _random.nextInt(6); break;
                case 13: attr_id = 183 + _random.nextInt(6); break;

                case 17: // ★★★ 脛甲洗練（6 種顏色）
                    attr_id = 218 + _random.nextInt(6);
                    break;
            }

            L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_id);

            // 防止 attr == null → 避免 NPE
            if (attr == null) {
                pc.sendPackets(new S_SystemMessage("脛甲炫色屬性未設定(attr_id=" + attr_id + ")，請檢查 item_special_attribute 表"));
                pc.getInventory().removeItem(item, 1L);
                return;
            }

            if (attr.get_add_rand() == 0) {
                attr_id = 0;
            }

            if (attr_id > 0) {
                Timestamp time2 = new Timestamp(System.currentTimeMillis());

                if (pc.isGm()) {
                    pc.sendPackets(new S_ServerMessage("GM顯示:DB機率:" + attr.get_add_rand() + " > 隨機 " + rand));
                }

                if (attr.get_add_rand() > rand) {

                    L1ItemSpecialAttributeChar ItemAttr2 = new L1ItemSpecialAttributeChar();

                    ItemAttr2.set_itemobjid(tgItem.getId());
                    ItemAttr2.set_item_name(tgItem.getNumberedName(tgItem.getCount()));
                    ItemAttr2.set_attr_id(attr_id);
                    ItemAttr2.set_add_pc_name(pc.getName());
                    ItemAttr2.set_add_time(time2);
                    ItemAttr2.set_add_mon_name("防具洗練");
                    ItemAttr2.set_mapname("無");
                    ItemAttr2.set_Acquisition_mode("防具洗練");

                    tgItem.set_ItemAttrName(ItemAttr2);
                    ItemSpecialAttributeCharTable.get().updateItem(tgItem.getId(), ItemAttr2);
                    ItemSpecialAttributeCharTable.get().storeItem(tgItem.getId(), ItemAttr2);
                    pc.sendPackets(new S_ItemName(tgItem));
                    pc.sendPackets(new S_ItemStatus(tgItem));

                    if (attr.get_msg() != null) {
                        World.get().broadcastPacketToAll(new S_SystemMessage(
                                String.format(attr.get_msg(), pc.getName(), tgItem.getLogName_attr(), attr.get_name())
                        ));
                    }
                    if (attr.get_msg1() != null) {
                        pc.sendPackets(new S_SystemMessage(
                                String.format(attr.get_msg1(), tgItem.getLogName_attr(), attr.get_name())
                        ));
                    }

                } else {
                    pc.sendPackets(new S_ServerMessage(1411, tgItem.getLogName()));
                }

            } else {
                pc.sendPackets(new S_ServerMessage(1411, tgItem.getLogName()));
            }

            pc.getInventory().removeItem(item, 1L);

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }
}
