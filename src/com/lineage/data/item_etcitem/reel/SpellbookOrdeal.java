package com.lineage.data.item_etcitem.reel;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.config.ConfigRate;
import java.util.Random;
import com.lineage.data.cmd.EnchantWeapon;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class SpellbookOrdeal extends ItemExecutor
{
    private SpellbookOrdeal() {
    }
    
    public static ItemExecutor get() {
        return new SpellbookOrdeal();
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
                case 1: {
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
            isErr = (weaponId < 246 || weaponId > 255);
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (weaponId >= 100213 && weaponId <= 100217) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                int enchant_level = tgItem.getEnchantLevel();
                EnchantExecutor enchantExecutor = new EnchantWeapon();
                int randomELevel = enchantExecutor.randomELevel(tgItem, item.getBless());
                pc.getInventory().removeItem(item, 1L);
                boolean isEnchant = true;
                if (enchant_level < -6) {
                    isEnchant = false;
                }
                else if (enchant_level < safe_enchant) {
                    isEnchant = true;
                }
                else {
                    Random random = new Random();
                    int rnd = random.nextInt(100) + 1;
                    int enchant_chance_wepon;
                    if (enchant_level >= 9) {
                        enchant_chance_wepon = (100 + 3 * ConfigRate.ENCHANT_CHANCE_WEAPON) / 6;
                    }
                    else {
                        enchant_chance_wepon = (100 + 3 * ConfigRate.ENCHANT_CHANCE_WEAPON) / 3;
                    }
                    if (rnd < enchant_chance_wepon) {
                        isEnchant = true;
                    }
                    else if (enchant_level >= 9 && rnd < enchant_chance_wepon * 2) {
                        randomELevel = 0;
                    }
                    else {
                        isEnchant = false;
                    }
                }
                if (randomELevel <= 0 && enchant_level > -6) {
                    isEnchant = true;
                }
                if (isEnchant) {
                    enchantExecutor.successEnchant(pc, tgItem, randomELevel);
                }
                else {
                    enchantExecutor.failureEnchant(pc, tgItem);
                }
                return;
            }
        }
    }
}
