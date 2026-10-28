package com.lineage.data.item_etcitem.add;

import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_SystemMessage;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ArmorEnchant extends ItemExecutor
{
    private int _type;
    
    private ArmorEnchant() {
    }
    
    public static ItemExecutor get() {
        return new ArmorEnchant();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.getItem().getType2() != 2) {
            return;
        }
        else {
            L1ItemInstance enchant_item = pc.getInventory().getItem(tgItem.getId());
            int i;
            int itemId = i = item.getItem().getItemId();
            int j = enchant_item.getskilltype();
            int k = enchant_item.getskilltypelv();
            Random random = new Random();
            int chance = random.nextInt(100) + 1;
            int a1 = this._type;
            int a2 = this._type;
            int a3 = this._type;
            int a4 = this._type;
            int a5 = this._type;
            boolean is_enchanted = false;
            if (k == 5) {
                pc.sendPackets(new S_SystemMessage("該裝備已經Lv5了..已達上限"));
                return;
            }
            else {
                switch (i) {
                    case 92256: {
                        if (tgItem.getItem().getUseType() != 2) {
                            pc.sendPackets(new S_SystemMessage("\\fT此卷軸只能附加在盔甲"));
                            return;
                        }
                        else if (a1 < chance) {
                            is_enchanted = false;
                            break;
                        }
                        else if (j == 113 || j == 114 || j == 115 || j == 116 || j == 117) {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 1);
                            is_enchanted = true;
                            break;
                        }
                        else if (j != 0) {
                            break;
                        }
                        else {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 113);
                            is_enchanted = true;
                            break;
                        }
                    }
                    case 92257: {
                        if (tgItem.getItem().getUseType() != 20) {
                            pc.sendPackets(new S_SystemMessage("\\fT此卷軸只能附加在手套"));
                            return;
                        }
                        else if (a2 < chance) {
                            is_enchanted = false;
                            break;
                        }
                        else if (j == 118 || j == 119 || j == 120 || j == 121 || j == 122) {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 1);
                            is_enchanted = true;
                            break;
                        }
                        else if (j != 0) {
                            break;
                        }
                        else {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 118);
                            is_enchanted = true;
                            break;
                        }
                    }
                    case 92258: {
                        if (tgItem.getItem().getUseType() != 22) {
                            pc.sendPackets(new S_SystemMessage("\\fT此卷軸只能附加在頭盔"));
                            return;
                        }
                        else if (a3 < chance) {
                            is_enchanted = false;
                            break;
                        }
                        else if (j == 123 || j == 124 || j == 125 || j == 126 || j == 127) {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 1);
                            is_enchanted = true;
                            break;
                        }
                        else if (j != 0) {
                            break;
                        }
                        else {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 123);
                            is_enchanted = true;
                            break;
                        }
                    }
                    case 92259: {
                        if (tgItem.getItem().getUseType() != 21) {
                            pc.sendPackets(new S_SystemMessage("\\fT此卷軸只能附加在長靴"));
                            return;
                        }
                        else if (a4 < chance) {
                            is_enchanted = false;
                            break;
                        }
                        else if (j == 128 || j == 129 || j == 130 || j == 131 || j == 132) {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 1);
                            is_enchanted = true;
                            break;
                        }
                        else if (j != 0) {
                            break;
                        }
                        else {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 128);
                            is_enchanted = true;
                            break;
                        }
                    }
                    case 92260: {
                        if (tgItem.getItem().getUseType() != 19) {
                            pc.sendPackets(new S_SystemMessage("\\fT此卷軸只能附加在斗篷"));
                            return;
                        }
                        else if (a5 < chance) {
                            is_enchanted = false;
                            break;
                        }
                        else if (j == 133 || j == 134 || j == 135 || j == 136 || j == 137) {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 1);
                            is_enchanted = true;
                            break;
                        }
                        else if (j != 0) {
                            break;
                        }
                        else {
                            tgItem.setskilltypelv(k + 1);
                            tgItem.setskilltype(j + 133);
                            is_enchanted = true;
                            break;
                        }
                    }
                }
                if (is_enchanted) {
                    pc.getInventory().removeItem(item, 1L);
                    pc.sendPackets(new S_SystemMessage("\\fS產生強大光芒，防具天賦瞬間被神秘力量加強"));
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.sendPackets(new S_ItemName(tgItem));
                    CharItemsTable cit = new CharItemsTable();
                    try {
                        pc.save();
                        cit.updateskilltypelv(tgItem);
                        cit.updateskilltype(tgItem);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    pc.getInventory().removeItem(item, 1L);
                    pc.sendPackets(new S_SystemMessage("\\fT強大光芒.但裝備沒任何影響.卷軸化為碎片"));
                }
                return;
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._type = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
