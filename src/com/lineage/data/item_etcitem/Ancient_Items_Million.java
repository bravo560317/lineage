package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.utils.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Ancient_Items_Million extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Ancient_Items_Million();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int[] Armorid = { 120479 };
        int Armorran = Random.nextInt(Armorid.length);
        int[] goodArmorid = { 120479 };
        int goodArmorran = Random.nextInt(goodArmorid.length);
        int[] Enchant = { 0, 1, 2, 3, 4, 5 };
        int[] HighEnchant = { 6, 7, 8 };
        int enchantlvl = 0;
        if (Random.nextInt(100) < 95) {
            enchantlvl = Enchant[Random.nextInt(Enchant.length)];
            CreateNewItem.createNewItem_LV(pc, Armorid[Armorran], 1, enchantlvl);
        }
        else {
            enchantlvl = HighEnchant[Random.nextInt(HighEnchant.length)];
            CreateNewItem.createNewItem_LV(pc, goodArmorid[goodArmorran], 1, enchantlvl);
        }
        pc.getInventory().removeItem(item, 1L);
    }
}
