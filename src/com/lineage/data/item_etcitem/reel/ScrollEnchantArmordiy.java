package com.lineage.data.item_etcitem.reel;

import com.lineage.data.cmd.EnchantExecutor;
import java.util.Random;
import com.lineage.data.cmd.EnchantWeapondiy;
import com.lineage.config.Configtype;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ScrollEnchantArmordiy extends ItemExecutor
{
    private int type1;
    private int type2;
    
    private ScrollEnchantArmordiy() {
    }
    
    public static ItemExecutor get() {
        return new ScrollEnchantArmordiy();
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
        else {
            int safe_enchant = tgItem.getItem().get_safeenchant();
            boolean isErr = false;
            int use_type = tgItem.getItem().getUseType();
            switch (use_type) {
                case 2:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 25:
                case 47: {
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
            int weaponId = tgItem.getItem().getItemId();
            if (weaponId >= 246 && weaponId <= 255) {
                isErr = true;
            }
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (tgItem.getItem().getItemId() >= 120444 && tgItem.getItem().getItemId() <= 120448) {
                isErr = true;
            }
            if (tgItem.getItem().getItemId() >= 301 && weaponId <= 310) {
                isErr = true;
            }
            if (tgItem.getItem().getItemId() >= 1040 && weaponId <= 1043) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else if (tgItem.getEnchantLevel() == Configtype.weaponlv) {
                pc.sendPackets(new S_ServerMessage("您的防具已達上限值。"));
                return;
            }
            else {
                int enchant_level = tgItem.getEnchantLevel();
                EnchantExecutor enchantExecutor = new EnchantWeapondiy();
                int randomELevel = enchantExecutor.randomELevel(tgItem, item.getBless());
                Random random = new Random();
                int rnd = random.nextInt(100) + 1; // 產生 1-100 的隨機數
                
                // 這裡改成與武器相同的邏輯
                if (enchant_level >= 0 && enchant_level <= this.type1) { // 判斷：防具強化等級是否在 0 ~ type1 之間
                    if (rnd <= this.type2) {
                        enchantExecutor.successEnchant(pc, tgItem, randomELevel);
                        pc.getInventory().removeItem(item, 1L);
                    }
                    else {
                        // 強化失敗，發送自訂訊息 (取代原本會爆裝的 failureEnchant)
                        pc.sendPackets(new S_ServerMessage("強化失敗！因防具受到保護，沒事情發生。"));
                        pc.getInventory().removeItem(item, 1L);
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("您的防具強化值需小於等於:+" + this.type1 + "才允許強化。"));
                }
                return;
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this.type1 = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this.type2 = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}