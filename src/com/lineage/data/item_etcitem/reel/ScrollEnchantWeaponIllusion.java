package com.lineage.data.item_etcitem.reel;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.server.model.L1ItemUpdata;
import java.util.Random;
import com.lineage.data.cmd.EnchantWeapon;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ScrollEnchantWeaponIllusion extends ItemExecutor
{
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    
    private ScrollEnchantWeaponIllusion() {
    }
    
    public static ItemExecutor get() {
        return new ScrollEnchantWeaponIllusion();
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
            if (weaponId != 36) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else if (tgItem.getEnchantLevel() == 9) {
                pc.sendPackets(new S_ServerMessage("幻象武器強化已達上限。"));
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
                    int en = enchant_level - safe_enchant;
                    int enchant_level_tmp;
                    if (safe_enchant == 0) {
                        enchant_level_tmp = enchant_level + 6;
                    }
                    else {
                        enchant_level_tmp = enchant_level;
                    }
                    int enchant_chance_wepon;
                    if (en == 0) {
                        enchant_chance_wepon = this.e;
                    }
                    else if (en == 1) {
                        enchant_chance_wepon = this.f;
                    }
                    else if (en == 2) {
                        enchant_chance_wepon = this.g;
                    }
                    else if (en == 3) {
                        enchant_chance_wepon = this.h;
                    }
                    else if (en == 4) {
                        enchant_chance_wepon = this.i;
                    }
                    else if (en == 5) {
                        enchant_chance_wepon = this.j;
                    }
                    else {
                        enchant_chance_wepon = (int)L1ItemUpdata.enchant_wepon_dn9((double)enchant_level_tmp);
                    }
                    if (pc.isGm()) {
                        pc.sendPackets(new S_ServerMessage("幻象機率" + enchant_chance_wepon));
                    }
                    isEnchant = (rnd < enchant_chance_wepon);
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
    
    @Override
    public void set_set(String[] set) {
        try {
            this.e = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this.f = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this.g = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this.h = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
        try {
            this.i = Integer.parseInt(set[5]);
        }
        catch (Exception ex5) {}
        try {
            this.j = Integer.parseInt(set[6]);
        }
        catch (Exception ex6) {}
    }
}
