package com.lineage.data.item_etcitem.reel;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.model.L1ItemUpdata;
import com.lineage.data.cmd.EnchantWeapon;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class KalbasScroll extends ItemExecutor
{
    final Random random;
    
    private KalbasScroll() {
        this.random = new Random();
    }
    
    public static ItemExecutor get() {
        return new KalbasScroll();
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
            if (weaponId >= 246 && weaponId <= 255) {
                isErr = true;
            }
            if (weaponId >= 301 && weaponId <= 310) {
                isErr = true;
            }
            if (weaponId >= 100213 && weaponId <= 100217) {
                isErr = true;
            }
            if (weaponId >= 1040 && weaponId <= 1043) {
                isErr = true;
            }
            if (weaponId >= 100213 && weaponId <= 100217) {
                isErr = true;
            }
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else if (tgItem.getEnchantLevel() == 20) {
                pc.sendPackets(new S_ServerMessage("您的武器已達上限值。"));
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
                    int enchant_level_tmp;
                    if (safe_enchant == 0) {
                        enchant_level_tmp = enchant_level + 6;
                    }
                    else {
                        enchant_level_tmp = enchant_level;
                    }
                    int enchant_chance_wepon;
                    if (enchant_level >= 9) {
                        enchant_chance_wepon = (int)L1ItemUpdata.enchant_wepon_up9((double)enchant_level_tmp);
                    }
                    else {
                        enchant_chance_wepon = (int)L1ItemUpdata.enchant_wepon_dn9((double)enchant_level_tmp);
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
                    tgItem.setproctect(false);
                    tgItem.setproctect1(false);
                    tgItem.setproctect2(false);
                    tgItem.setproctect3(false);
                    tgItem.setproctect4(false);
                    tgItem.setproctect5(false);
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    if (pc.getAccessLevel() == 0 && !pc.getcheck_lv() && tgItem.getEnchantLevel() >= tgItem.getItem().get_safeenchant() + 3) {
                        World.get().broadcastPacketToAll(new S_BlueMessage(166, "\\f=【" + pc.getName() + "】的+" + enchant_level + " " + tgItem.getName() + "強化成功"));
                    }
                    pc.setcheck_lv(false);
                }
                else if (tgItem.getproctect()) {
                    tgItem.setproctect(false);
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.getInventory().updateItem(tgItem, 4);
                    pc.getInventory().saveItem(tgItem, 4);
                    pc.sendPackets(new S_ServerMessage("受到高級裝備保護卷軸的影響,失敗後物品無變化。"));
                }
                else if (tgItem.getproctect1()) {
                    tgItem.setproctect1(false);
                    tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.getInventory().updateItem(tgItem, 4);
                    pc.getInventory().saveItem(tgItem, 4);
                    pc.sendPackets(new S_ServerMessage("受到中級裝備保護卷軸的影響,失敗後物品倒扣1。"));
                }
                else if (tgItem.getproctect2()) {
                    tgItem.setproctect2(false);
                    tgItem.setEnchantLevel(0);
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.getInventory().updateItem(tgItem, 4);
                    pc.getInventory().saveItem(tgItem, 4);
                    pc.sendPackets(new S_ServerMessage("受到初級裝備保護卷軸的影響,失敗後物品歸0。"));
                }
                else if (tgItem.getproctect3()) {
                    if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
                        tgItem.setproctect3(false);
                        pc.setproctctran(0);
                        tgItem.setEnchantLevel(0);
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        pc.getInventory().updateItem(tgItem, 4);
                        pc.getInventory().saveItem(tgItem, 4);
                        pc.sendPackets(new S_ServerMessage("受到初級裝備保護卷軸的影響,失敗後物品歸0。"));
                    }
                    else {
                        pc.setproctctran(0);
                        enchantExecutor.failureEnchant(pc, tgItem);
                    }
                }
                else if (tgItem.getproctect4()) {
                    if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
                        tgItem.setproctect4(false);
                        pc.setproctctran(0);
                        tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        pc.getInventory().updateItem(tgItem, 4);
                        pc.getInventory().saveItem(tgItem, 4);
                        pc.sendPackets(new S_ServerMessage("受到中級裝備保護卷軸的影響,失敗後物品倒扣1。"));
                    }
                    else {
                        pc.setproctctran(0);
                        enchantExecutor.failureEnchant(pc, tgItem);
                    }
                }
                else if (tgItem.getproctect5()) {
                    if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
                        tgItem.setproctect5(false);
                        pc.setproctctran(0);
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        pc.getInventory().updateItem(tgItem, 4);
                        pc.getInventory().saveItem(tgItem, 4);
                        pc.sendPackets(new S_ServerMessage("受到高級裝備保護卷軸的影響,失敗後物品無變化。"));
                    }
                    else {
                        pc.setproctctran(0);
                        enchantExecutor.failureEnchant(pc, tgItem);
                    }
                }
                else {
                    enchantExecutor.failureEnchant(pc, tgItem);
                }
                return;
            }
        }
    }
}
