package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.william.drop_type_armor_clean;
import com.lineage.william.drop_type_weapon_clean;
import com.lineage.william.drop_type_armor_item1;
import com.lineage.william.drop_type_weapon_item1;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Armor_Color1 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Armor_Color1();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (!tgItem.getItem().isdropcolor()) {
            pc.sendPackets(new S_SystemMessage("似乎沒發生什麼事情。"));
            return;
        }
        else {
            if (tgItem.getItemArmorType() == 0) {
                if (tgItem.getItem().getType2() == 1) {
                    drop_type_weapon_item1.forIntensifyArmor(pc, tgItem);
                    pc.getInventory().removeItem(item, 1L);
                }
                else if (tgItem.getItem().getType2() == 2) {
                    drop_type_armor_item1.forIntensifyArmor(pc, tgItem);
                    pc.getInventory().removeItem(item, 1L);
                }
                else {
                    pc.sendPackets(new S_SystemMessage("似乎沒發生什麼事情。"));
                }
            }
            else if (tgItem.getItemArmorType() > 0) {
                if (tgItem.getItem().getType2() == 1) {
                    drop_type_weapon_clean.forIntensifyArmor(pc, tgItem);
                    pc.getInventory().removeItem(item, 1L);
                }
                else if (tgItem.getItem().getType2() == 2) {
                    drop_type_armor_clean.forIntensifyArmor(pc, tgItem);
                    pc.getInventory().removeItem(item, 1L);
                }
                else {
                    pc.sendPackets(new S_SystemMessage("似乎沒發生什麼事情。"));
                }
            }
            pc.sendPackets(new S_ItemStatus(tgItem));
            pc.getInventory().updateItem(tgItem, 4);
            pc.getInventory().saveItem(tgItem, 4);
            return;
        }
    }
}
