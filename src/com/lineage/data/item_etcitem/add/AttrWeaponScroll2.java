package com.lineage.data.item_etcitem.add;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.templates.L1AttrWeapon;
import com.lineage.server.serverpackets.S_ItemStatus;
import java.sql.Timestamp;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ExtraAttrWeaponTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class AttrWeaponScroll2 extends ItemExecutor
{
    private static final Random _random;
    private int _attr_id;
    private int _type;
    
    static {
        _random = new Random();
    }
    
    public static ItemExecutor get() {
        return new AttrWeaponScroll2();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.getItem().getType2() != 1) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (tgItem.getBless() >= 128) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            int attrEnchantKind = tgItem.getAttrEnchantKind();
            int attrEnchantLevel = tgItem.getAttrEnchantLevel();
            if (attrEnchantKind != this._attr_id) {
                attrEnchantLevel = 0;
            }
            L1AttrWeapon attrWeapon = ExtraAttrWeaponTable.getInstance().get(this._attr_id, attrEnchantLevel + 1);
            if (attrWeapon == null || attrWeapon.getChance() <= 0) {
                pc.sendPackets(new S_SystemMessage("已達該屬性最高階級。"));
                return;
            }
            else {
                pc.getInventory().removeItem(item, 1L);
                if (AttrWeaponScroll2._random.nextInt(1000) < attrWeapon.getChance()) {
                    pc.sendPackets(new S_ServerMessage(1410, tgItem.getLogName()));
                    if (attrWeapon.getMessage() != null && !pc.isGm()) {
                        World.get().broadcastPacketToAll(new S_SystemMessage(String.format(attrWeapon.getMessage(), pc.getName(), "+" + tgItem.getEnchantLevel() + tgItem.getName(), attrWeapon.getName())));
                    }
                    tgItem.setAttrEnchantKind(this._attr_id);
                    tgItem.setAttrEnchantLevel(attrEnchantLevel + 1);
                    屬性卷軸成敗紀錄("玩家 :" + pc.getName() + "使用 " + item.getName() + "(升)至Lv" + tgItem.getAttrEnchantLevel() + " ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
                }
                else if (attrWeapon.gettype() == 0) {
                    pc.sendPackets(new S_SystemMessage("強化失敗,沒發生什麼事情。"));
                }
                else if (attrWeapon.gettype() == 1) {
                    if (attrEnchantLevel > 0) {
                        tgItem.setAttrEnchantLevel(attrEnchantLevel - 1);
                        pc.sendPackets(new S_SystemMessage("強化失敗,倒退-1。"));
                    }
                    if (attrEnchantLevel == 0) {
                        tgItem.setAttrEnchantKind(0);
                        tgItem.setAttrEnchantLevel(0);
                        pc.sendPackets(new S_SystemMessage("強化失敗,沒發生什麼事情。"));
                    }
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.getInventory().saveItem(tgItem, 4);
                }
                else if (attrWeapon.gettype() == 2) {
                    pc.getInventory().removeItem(tgItem, 1L);
                    pc.sendPackets(new S_SystemMessage("強化失敗,並刪除強化物品。"));
                }
                else if (attrWeapon.gettype() == 3) {
                    tgItem.setAttrEnchantKind(0);
                    tgItem.setAttrEnchantLevel(0);
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.getInventory().saveItem(tgItem, 4);
                    pc.sendPackets(new S_SystemMessage("強化失敗,該裝倍武器屬性歸0。"));
                }
                else {
                    pc.sendPackets(new S_SystemMessage("強化失敗,沒發生什麼事情。"));
                }
                pc.getInventory().updateItem(tgItem, 3072);
                pc.getInventory().saveItem(tgItem, 3072);
                return;
            }
        }
    }
    
    public static void 屬性卷軸成敗紀錄(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[記錄]-武器屬性卷軸.txt", true));
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
            this._attr_id = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._type = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}
