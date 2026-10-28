package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.utils.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Ancient_Items_Armor extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Ancient_Items_Armor();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int[] Armorid = { 20110 };
        int Armorran = Random.nextInt(Armorid.length);
        int[] goodArmorid = { 20160, 20156, 20159, 20127, 20146 };
        int goodArmorran = Random.nextInt(goodArmorid.length);
        int[] Enchant = { 0, 1, 2, 3, 4 };
        int[] HighEnchant = { 5, 6, 7 };
        int enchantlvl = 0;
        if (Random.nextInt(100) + 1 <= 1) {
            enchantlvl = HighEnchant[Random.nextInt(HighEnchant.length)];
        }
        else {
            enchantlvl = Enchant[Random.nextInt(Enchant.length)];
        }
        pc.getInventory().removeItem(item, 1L);
        if (Random.nextInt(100) < 99) {
            CreateNewItem.createNewItem_LV(pc, Armorid[Armorran], 1, enchantlvl);
        }
        else {
            CreateNewItem.createNewItem_LV(pc, goodArmorid[goodArmorran], 1, enchantlvl);
        }
    }
}
