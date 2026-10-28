package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.william.drop_type_weapon_item2;
import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.william.drop_type_weapon_clean2;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random; 
import com.lineage.data.executor.ItemExecutor;

public class GmWeapon_Color1 extends ItemExecutor {

    private int type1;
    private static final Random RANDOM = new Random(); // 使用靜態 Random 實例以提高效率 (Use a static Random instance for efficiency)

    public static ItemExecutor get() {
        // 使用靜態 Random 實例以提高效率 (Use a static Random instance for efficiency)
        return new GmWeapon_Color1();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {

        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);

        if (tgItem == null) {
            return;
        }

        // 非可附魔
        if (!tgItem.getItem().isdropcolor()) {
            pc.sendPackets(new S_SystemMessage("似乎沒發生什麼事情。"));
            return;
        }

        // 不是武器
        if (tgItem.getItem().getUseType() != 1) {
            pc.sendPackets(new S_SystemMessage("似乎沒發生什麼事情。"));
            return;
        }

        int selectedType = this.type1; // 使用配置的 type1 (Use the configured type1)

        // 如果 type1 為 0 (表示隨機模式)，則生成一個隨機值 (If type1 is 0 (indicating random mode), generate a random value)
        if (selectedType == 0) {
            // 首先處理 100 的 0.01% 機率
            // 0.01% = 1 / 10000
            if (RANDOM.nextInt(10000) == 0) { // 生成 0 到 9999 的隨機數。如果為 0，則機率為 1/10000。
                selectedType = 100;
            } else {
                // 對於 1-99，偏向較小的數字。
                // 使用 Math.pow(RANDOM.nextDouble(), 2) 會使較小的數字（接近 0）更常出現。
                // 這裡的 2 是指數，可以調整以改變偏向的強度。
                // 乘以 99 並加 1，將範圍映射到 1 到 99。
                selectedType = (int) (Math.pow(RANDOM.nextDouble(), 2) * 99) + 1;
                // 確保結果在 1 到 99 之間，以防浮點數精度問題或邊界情況
                if (selectedType < 1) selectedType = 1;
                if (selectedType > 99) selectedType = 99;
            } // 移除顯示具體數字，改為統一訊息
            pc.sendPackets(new S_SystemMessage("暗黑系統炫色成功"));
        }

        // ⭐ 相同附魔直接阻擋 (Block if it's the same enchantment)
        if (tgItem.getItemArmorType() == selectedType) {
            pc.sendPackets(new S_SystemMessage("已經是相同附魔。"));
            // 道具最終仍會被消耗，與原始行為一致 (Item will still be consumed at the end, consistent with original behavior.)
        } else {
            // ⭐ 如果有舊附魔 → 先清除舊效果 (If there's an old enchantment → clear old effects first)
            if (tgItem.getItemArmorType() > 0) {
                drop_type_weapon_clean2.forIntensifyArmor(pc, tgItem);
            }

            // 覆蓋附魔
            tgItem.setItemArmorType(selectedType); // Use the selectedType (configured or random)

            try {
                CharItemsTable.get().updateItemArmorType(tgItem);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 套用新效果 (Apply new effects)
            drop_type_weapon_item2.forIntensifyArmor(pc, tgItem);
        }

        try {
            pc.getInventory().removeItem(item, 1L);
            pc.sendPackets(new S_ItemStatus(tgItem));
            pc.sendPackets(new S_SPMR(pc)); // 更新魔防 (Update magic resistance)
            pc.sendPackets(new S_OwnCharStatus(pc)); // 更新角色狀態 (Update character status)
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp())); // 更新HP (Update HP)
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp())); // 更新MP (Update MP)
        } catch (Exception e) {
            // 記錄或處理道具移除或封包發送期間的任何異常 (Log or handle any exceptions during item removal or packet sending)
            e.printStackTrace();
        }
    }

    
    @Override
    public void set_set(String[] set) {
        try {
            // 如果 set[1] 未提供或為空，Integer.parseInt 將拋出異常。
            // 在這種情況下，type1 將保持其默認值 0，我們可以用它來表示隨機模式。
            if (set.length > 1 && set[1] != null && !set[1].trim().isEmpty()) {
                this.type1 = Integer.parseInt(set[1]);
            } else {
                this.type1 = 0; // 如果沒有提供參數，明確設置為 0 以啟用隨機模式。
            }
        } catch (NumberFormatException e) {
            // 如果解析失敗 (例如，非整數字符串)，則默認為 0 以啟用隨機模式。
            this.type1 = 0;
        } catch (Exception e) {
            // 捕獲任何其他異常並默認為 0。
            this.type1 = 0;
        }
    }
}