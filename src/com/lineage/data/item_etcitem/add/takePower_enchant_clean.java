package com.lineage.data.item_etcitem.add;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.*;

import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.william.drop_type_weapon_clean2;
import com.lineage.william.drop_type_armor_clean2;

import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class takePower_enchant_clean extends ItemExecutor {

    private static final Log _log = LogFactory.getLog(takePower_enchant_clean.class);

    public static ItemExecutor get() {
        return new takePower_enchant_clean();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {

        try {
            int targObjId = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);

            if (tgItem == null) {
                return;
            }

            // 必須先解除裝備
            if (tgItem.isEquipped()) {
                pc.sendPackets(new S_ServerMessage("\\fR請先解除裝備後再操作"));
                return;
            }

            // 是否有附魔
            int enchantType = tgItem.getItemArmorType();
            if (enchantType <= 0) {
                pc.sendPackets(new S_ServerMessage("\\fT此物品沒有附魔可摘除"));
                return;
            }

            boolean isWeapon = tgItem.getItem().getUseType() == 1;

            // ===== 1. 清除附魔能力 =====
            if (isWeapon) {
                drop_type_weapon_clean2.forIntensifyArmor(pc, tgItem);
            } else {
                drop_type_armor_clean2.forIntensifyArmor(pc, tgItem);
            }

            // ===== 2. 查詢並退回原本的附魔卷軸 =====
            int returnItemId = getReturnEnchantItemId(enchantType, isWeapon);
            if (returnItemId > 0) {
                pc.getInventory().storeItem(returnItemId, 1);
            } else {
                pc.sendPackets(new S_ServerMessage(
                    "\\fR找不到對應的附魔卷軸，請通知管理員"));
            }

            // ===== 3. 清除附魔狀態 =====
            tgItem.setItemArmorType(0);
            new CharItemsTable().updateItemArmorType(tgItem);

            // ===== 4. 更新狀態 =====
            pc.sendPackets(new S_ItemStatus(tgItem));
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));

            // ===== 5. 吃掉道具 =====
            pc.getInventory().removeItem(item, 1L);

            pc.sendPackets(new S_ServerMessage(
                "\\fT附魔已成功摘除（類型：" + enchantType + "）"));

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 依照附魔類型，從 etcitem 查回「當初使用的附魔卷軸」
     */
    private int getReturnEnchantItemId(int enchantType, boolean isWeapon) {

        String className;
        if (isWeapon) {
            className = "GmWeapon_Color1 " + enchantType;
        } else {
            className = "GmArmor_Color1 " + enchantType;
        }

        int itemId = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();
            ps = con.prepareStatement(
                "SELECT item_id FROM etcitem WHERE classname = ? LIMIT 1"
            );
            ps.setString(1, className);
            rs = ps.executeQuery();

            if (rs.next()) {
                itemId = rs.getInt("item_id");
            }

        } catch (Exception e) {
            _log.error("查詢附魔卷軸失敗: " + className, e);
        } finally {
            SQLUtil.close(rs, ps, con);
        }

        return itemId;
    }
}
