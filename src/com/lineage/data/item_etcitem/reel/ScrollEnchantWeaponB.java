package com.lineage.data.item_etcitem.reel;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import java.util.Random;
import com.lineage.data.cmd.EnchantWeapon;
import com.lineage.config.Configtype;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ScrollEnchantWeaponB extends ItemExecutor
{
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    
    private ScrollEnchantWeaponB() {
    }
    
    public static ItemExecutor get() {
        return new ScrollEnchantWeaponB();
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
            boolean isEnchant1 = false;
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
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else if (tgItem.getEnchantLevel() == Configtype.weaponlv) {
                pc.sendPackets(new S_ServerMessage("您的武器已達上限值。"));
                return;
            }
            else {
                int enchant_level = tgItem.getEnchantLevel();
                EnchantExecutor enchantExecutor = new EnchantWeapon();
                int randomELevel = enchantExecutor.randomELevel(tgItem, item.getBless());
                int lv1 = tgItem.getEnchantLevel() - tgItem.getItem().get_safeenchant() + 1;
                pc.getInventory().removeItem(item, 1L);
                pc.setoldAllName(tgItem.getAllName());
                Random random = new Random();
                int lv2 = 100;
                if (tgItem.getEnchantLevel() < tgItem.getItem().get_safeenchant()) {
                    lv2 = 100;
                    if (pc.isGm()) {
                        pc.sendPackets(new S_ServerMessage("1GM顯示機率:" + lv2));
                    }
                }
                else if (lv1 == 1) {
                    lv2 = this.b;
                    if (pc.isGm()) {
                        pc.sendPackets(new S_ServerMessage("2GM顯示機率:" + lv2));
                    }
                }
                else if (lv1 == 2) {
                    lv2 = this.c;
                    if (pc.isGm()) {
                        pc.sendPackets(new S_ServerMessage("3GM顯示機率:" + lv2));
                    }
                }
                else if (lv1 == 3) {
                    lv2 = this.d;
                    if (pc.isGm()) {
                        pc.sendPackets(new S_ServerMessage("4GM顯示機率:" + lv2));
                    }
                }
                else if (lv1 == 4) {
                    lv2 = this.e;
                    if (pc.isGm()) {
                        pc.sendPackets(new S_ServerMessage("5GM顯示機率:" + lv2));
                    }
                }
                else if (lv1 == 5) {
                    lv2 = this.f;
                    if (pc.isGm()) {
                        pc.sendPackets(new S_ServerMessage("6GM顯示機率:" + lv2));
                    }
                }
                if (random.nextInt(100) <= lv2) {
                    enchantExecutor.successEnchant(pc, tgItem, randomELevel);
                    if (Configtype.armorbroadtrue == 1 && !isEnchant1 && tgItem.getEnchantLevel() - tgItem.getItem().get_safeenchant() >= Configtype.armor_savebroad) {
                        if (isEnchant1) {
                            isEnchant1 = false;
                        }
                        World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Configtype.msg1_true, pc.getName(), pc.getoldAllName(), tgItem.getAllName())));
                        pc.setoldAllName(null);
                    }
                }
                else {
                    switch (this.a) {
                        case 1: {
                            tgItem.setEnchantLevel(0);
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(tgItem.getLogName() + "受到神秘卷軸力量[歸0]。"));
                            break;
                        }
                        case 2: {
                            tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.getInventory().updateItem(tgItem, 4);
                            pc.getInventory().saveItem(tgItem, 4);
                            pc.sendPackets(new S_ServerMessage(tgItem.getLogName() + "受到神秘卷軸力量[-1]。"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_ServerMessage(tgItem.getLogName() + "受到神秘卷軸力量[沒發生什麼事]。"));
                            break;
                        }
                    }
                }
                return;
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this.a = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this.b = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this.c = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this.d = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
        try {
            this.e = Integer.parseInt(set[5]);
        }
        catch (Exception ex5) {}
        try {
            this.f = Integer.parseInt(set[6]);
        }
        catch (Exception ex6) {}
    }
}
