package com.lineage.data.item_etcitem.reel;

import com.lineage.data.cmd.EnchantExecutor;
import java.util.Random;
import com.lineage.data.cmd.EnchantArmor;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ScrollArmorSihonCoin extends ItemExecutor
{
    private ScrollArmorSihonCoin() {
    }
    
    public static ItemExecutor get() {
        return new ScrollArmorSihonCoin();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.isEquipped()) {
            pc.sendPackets(new S_ServerMessage("\\aE你必須先解除物品裝備。"));
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
                case 70: {
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
            isErr = (tgItem.getItemId() < 120444 || tgItem.getItemId() > 120448);
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                int enchant_level = tgItem.getEnchantLevel();
                if (enchant_level >= 20) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else {
                    EnchantExecutor enchantExecutor = new EnchantArmor();
                    pc.getInventory().removeItem(item, 1L);
                    boolean isEnchant = false;
                    Random random = new Random();
                    int rand = 1;
                    if (enchant_level >= 9) {
                        rand = 5;
                    }
                    else if (enchant_level == 8) {
                        rand = 10;
                    }
                    else if (enchant_level == 7) {
                        rand = 15;
                    }
                    else if (enchant_level == 6) {
                        rand = 20;
                    }
                    else if (enchant_level == 5) {
                        rand = 25;
                    }
                    else if (enchant_level == 4) {
                        rand = 30;
                    }
                    else if (enchant_level == 3) {
                        rand = 35;
                    }
                    else if (enchant_level == 2) {
                        rand = 40;
                    }
                    else if (enchant_level == 1) {
                        rand = 45;
                    }
                    else if (enchant_level == 0) {
                        rand = 50;
                    }
                    if (random.nextInt(100) + 1 < rand) {
                        isEnchant = true;
                    }
                    if (isEnchant) {
                        enchantExecutor.successEnchant(pc, tgItem, 1);
                    }
                    else {
                        enchantExecutor.failureEnchant(pc, tgItem);
                    }
                    return;
                }
            }
        }
    }
}
