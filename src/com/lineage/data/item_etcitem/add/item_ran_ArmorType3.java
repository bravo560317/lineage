package com.lineage.data.item_etcitem.add;

import com.lineage.william.item_ran_armor;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class item_ran_ArmorType3 extends ItemExecutor
{
    private int a;
    
    private item_ran_ArmorType3() {
    }
    
    public static ItemExecutor get() {
        return new item_ran_ArmorType3();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance l1iteminstance = pc.getInventory().getItem(targObjId);
        if (l1iteminstance == null) {
            return;
        }
        else if (!pc.getInventory().checkItem(l1iteminstance.getItem().getItemId(), 1L)) {
            return;
        }
        else if (l1iteminstance.isEquipped()) {
            pc.sendPackets(new S_SystemMessage("該裝備尚未脫掉。"));
            return;
        }
        else {
            if (l1iteminstance.getItem().getUseType() == 2 || l1iteminstance.getItem().getUseType() == 19 || l1iteminstance.getItem().getUseType() == 20 || l1iteminstance.getItem().getUseType() == 21 || l1iteminstance.getItem().getUseType() == 22) {
                if (l1iteminstance.getItemArmorType() >= 3) {
                    l1iteminstance.setItemAttack(0);
                    l1iteminstance.setItemBowAttack(0);
                    l1iteminstance.setItemHit(0);
                    l1iteminstance.setItemBowHit(0);
                    l1iteminstance.setItemReductionDmg(0);
                    l1iteminstance.setItemSp(0);
                    l1iteminstance.setItemprobability(0);
                    l1iteminstance.setItemStr(0);
                    l1iteminstance.setItemDex(0);
                    l1iteminstance.setItemInt(0);
                    l1iteminstance.setItemCon(0);
                    l1iteminstance.setItemWis(0);
                    l1iteminstance.setItemCha(0);
                    l1iteminstance.setItemMr(0);
                    l1iteminstance.setItemAc(0);
                    l1iteminstance.setItemHp(0);
                    l1iteminstance.setItemMp(0);
                    l1iteminstance.setItemMag_Red(0);
                    l1iteminstance.setItemMag_Hit(0);
                    l1iteminstance.setItemDg(0);
                    l1iteminstance.setItemistSustain(0);
                    l1iteminstance.setItemistStun(0);
                    l1iteminstance.setItemistStone(0);
                    l1iteminstance.setItemistSleep(0);
                    l1iteminstance.setItemistFreeze(0);
                    l1iteminstance.setItemistBlind(0);
                    pc.getInventory().removeItem(item, 1L);
                    item_ran_armor.forIntensifyArmor3(pc, l1iteminstance);
                }
                else {
                    pc.sendPackets(new S_SystemMessage("該裝備並不是三屬性"));
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("請選擇裝備進行!!"));
            }
            return;
        }
    }
}
