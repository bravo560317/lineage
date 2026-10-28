package com.lineage.data.item_etcitem.extra;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.data.cmd.EnchantExecutor;
import java.sql.Timestamp;
import com.lineage.data.cmd.EnchantAccessory;
import java.util.Random;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class OrimAccessory extends ItemExecutor
{
    private int typelv;
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private boolean type2;
    
    public OrimAccessory() {
        this.type2 = false;
    }
    
    public static ItemExecutor get() {
        return new OrimAccessory();
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
                case 23:
                case 24:
                case 37:
                case 40: {
                    if (tgItem.getItem().get_greater() == 3) {
                        isErr = true;
                    }
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
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                int enchant_level = tgItem.getEnchantLevel();
                if (enchant_level == this.typelv) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else {
                    pc.getInventory().removeItem(item, 1L);
                    Random random = new Random();
                    EnchantExecutor enchantExecutor = new EnchantAccessory();
                    int lv = 0;
                    if (tgItem.getEnchantLevel() == 0) {
                        lv = this.a;
                    }
                    else if (tgItem.getEnchantLevel() == 1) {
                        lv = this.b;
                    }
                    else if (tgItem.getEnchantLevel() == 2) {
                        lv = this.c;
                    }
                    else if (tgItem.getEnchantLevel() == 3) {
                        lv = this.d;
                    }
                    else if (tgItem.getEnchantLevel() == 4) {
                        lv = this.e;
                    }
                    else if (tgItem.getEnchantLevel() == 5) {
                        lv = this.f;
                    }
                    else if (tgItem.getEnchantLevel() == 6) {
                        lv = this.g;
                    }
                    else if (tgItem.getEnchantLevel() == 7) {
                        lv = this.h;
                    }
                    else if (tgItem.getEnchantLevel() == 8) {
                        lv = this.i;
                    }
                    if (random.nextInt(100) <= lv) {
                        enchantExecutor.successEnchant(pc, tgItem, 1);
                        歐林飾品強化成功記錄表("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "的" + "【  " + tgItem.getEnchantLevel() + " " + tgItem.getName() + "】(強化成功)" + "時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
                    }
                    else if (item.getBless() == 0) {
                        enchantExecutor.successEnchant(pc, tgItem, 0);
                    }
                    else if (tgItem.getEnchantLevel() == 0) {
                        pc.sendPackets(new S_ServerMessage(79));
                        return;
                    }
                    else {
                        if (this.type2) {
                            enchantExecutor.successEnchant(pc, tgItem, -1);
                        }
                        歐林飾品強化失敗記錄表("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "的" + "【 " + tgItem.getEnchantLevel() + " " + tgItem.getName() + "】(強化失敗-1)" + "時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
                    }
                    if (enchant_level < -6) {
                        enchantExecutor.failureEnchant(pc, tgItem);
                    }
                    return;
                }
            }
        }
    }
    
    public static void 歐林飾品強化失敗記錄表(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[歐林飾品強化[失敗]記錄表].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void 歐林飾品強化成功記錄表(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[歐林飾品強化[成功]記錄表].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this.typelv = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this.a = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this.b = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this.c = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
        try {
            this.d = Integer.parseInt(set[5]);
        }
        catch (Exception ex5) {}
        try {
            this.e = Integer.parseInt(set[6]);
        }
        catch (Exception ex6) {}
        try {
            this.f = Integer.parseInt(set[7]);
        }
        catch (Exception ex7) {}
        try {
            this.g = Integer.parseInt(set[8]);
        }
        catch (Exception ex8) {}
        try {
            this.h = Integer.parseInt(set[9]);
        }
        catch (Exception ex9) {}
        try {
            this.i = Integer.parseInt(set[10]);
        }
        catch (Exception ex10) {}
        try {
            this.type2 = Boolean.parseBoolean(set[11]);
        }
        catch (Exception ex11) {}
    }
}
