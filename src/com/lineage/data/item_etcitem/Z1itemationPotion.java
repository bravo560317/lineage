package com.lineage.data.item_etcitem;

import java.sql.Timestamp;
import com.lineage.DatabaseFactory;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.lock.VIPReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.utils.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Z1itemationPotion extends ItemExecutor {

    private static final int ADENA_ITEM_ID = 40308;

    private static final int SELL_MODE_ALL = 0;
    private static final int SELL_MODE_HIGH = 1;
    private static final int SELL_MODE_RARE = 2;
    private static final int SELL_MODE_HERO = 3;
    private static final int SELL_MODE_LEGEND = 4;
    private static final int SELL_MODE_MYTH = 5;
    private static final int SELL_MODE_EMPEROR = 6;

    // 保持靜態變數，避免你的 FollowAutoAttackUpdate.java 報錯
    private static int sellMode = SELL_MODE_ALL;

    private Z1itemationPotion() {}

    public static ItemExecutor get() {
        return new Z1itemationPotion();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            if (pc == null) {
                return;
            }

            Timestamp vipTime = VIPReading.get().getOther(pc);
            if (vipTime == null || vipTime.getTime() <= System.currentTimeMillis()) {
                pc.sendPackets(new S_SystemMessage("一鍵出售，未解鎖（需VIP月卡）"));
                pc.sendPackets(new S_SystemMessage("\\fU提醒:月卡可藉由簽到累積進行月卡製作"));
                return;
            }

            List<L1ItemInstance> allItems = pc.getInventory().getItems();
            if (allItems == null || allItems.isEmpty()) {
                pc.sendPackets(new S_SystemMessage("\\aF背包是空的"));
                return;
            }

            List<L1ItemInstance> sellTargets = new ArrayList<>();
            int totalPrice = 0;
            int sellCount = 0;

            for (L1ItemInstance i : allItems) {
                // 排除數量異常、穿戴中、有加成過(強化等級不為0)、或是【已鑑定】的裝備 (isIdentified() 等同於資料庫 is_id == 1)
                if (i == null || i.getCount() <= 0 || i.isEquipped() || i.getEnchantLevel() != 0 || i.isIdentified()) {
                    continue;
                }
                if (!isTargetItem(i.getItemId())) {
                    continue;
                }
                if (!isSellableItem(i)) {
                    continue;
                }

                int price = getPurchasingPrice(i.getItemId());
                if (price < 0) {
                    continue;
                }

                int count = (int) Math.max(1L, i.getCount());
                sellTargets.add(i);
                totalPrice += price * count;
                sellCount += count;
            }

            if (sellTargets.isEmpty()) {
                pc.sendPackets(new S_SystemMessage("\\aF沒有符合一鍵出售條件的物品"));
                return;
            }

            for (L1ItemInstance target : sellTargets) {
                pc.getInventory().removeItem(target, target.getCount());
            }

            if (totalPrice > 0) {
                pc.getInventory().storeItem(ADENA_ITEM_ID, (long) totalPrice);
            }

            pc.sendPackets(new S_SystemMessage("\\aF一鍵出售完成，已出售 " + sellCount + " 件物品，獲得 " + totalPrice + " 金幣"));
            pc.saveInventory();
            pc.save();
        } catch (Exception e) {
            pc.sendPackets(new S_SystemMessage("\\aF一鍵出售發生錯誤"));
            e.printStackTrace();
        }
    }

    private boolean isTargetItem(int itemId) {
        return (itemId >= 1 && itemId <= 5000) || (itemId >= 20001 && itemId <= 36000);
    }

    public static int getSellMode() {
        return sellMode;
    }

    public static void cycleSellMode() {
        sellMode++;
        if (sellMode > SELL_MODE_EMPEROR) {
            sellMode = SELL_MODE_ALL;
        }
    }

    public static String getSellModeLabel() {
        if (sellMode == SELL_MODE_ALL) {
            return "全賣";
        }

        switch (sellMode) {
            case SELL_MODE_HIGH:
                return "高級以下";
            case SELL_MODE_RARE:
                return "稀有以下";
            case SELL_MODE_HERO:
                return "英雄以下";
            case SELL_MODE_LEGEND:
                return "傳說以下";
            case SELL_MODE_MYTH:
                return "神話以下";
            case SELL_MODE_EMPEROR:
                return "帝王以下";
        }

        return "全賣";
    }

    public static String getSettingsSummary() {
        return "目前模式：" + getSellModeLabel();
    }

    private boolean isSellableItem(L1ItemInstance item) {
        if (item == null || item.getItem() == null) {
            return false;
        }
        if (item.getItem().getType2() != 1 && item.getItem().getType2() != 2) {
            return false;
        }

        int armorType = item.getItemArmorType();
        
        switch (sellMode) {
            case SELL_MODE_HIGH:
                return armorType < 1;   
            case SELL_MODE_RARE:
                return armorType < 11;  
            case SELL_MODE_HERO:
                return armorType < 52;  
            case SELL_MODE_LEGEND:
                return armorType < 69;  
            case SELL_MODE_MYTH:
                return armorType < 81;  
            case SELL_MODE_EMPEROR:
                return armorType < 100; 
            case SELL_MODE_ALL:
            default:
                return true;
        }
    }

    private int getPurchasingPrice(int itemId) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT purchasing_price FROM shop WHERE item_id=? ORDER BY purchasing_price DESC LIMIT 1");
            ps.setInt(1, itemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("purchasing_price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(rs, ps, cn);
        }
        return -1;
    }
}