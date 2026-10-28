package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.utils.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Ancient_Items_Weapon extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Ancient_Items_Weapon();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int[] Weaponid = { 410164, 185, 63, 85, 165, 273, 271, 131, 180, 37, 194 };
        int Weaponran = Random.nextInt(Weaponid.length);
        int[] Enchant = { 0, 1, 2, 3, 4, 5, 6 };
        int[] HighEnchant = { 7, 8, 9 };
        int enchantlvl = 0;
        if (Random.nextInt(100) + 1 <= 1) {
            enchantlvl = HighEnchant[Random.nextInt(HighEnchant.length)];
        }
        else {
            enchantlvl = Enchant[Random.nextInt(Enchant.length)];
        }
        pc.getInventory().removeItem(item, 1L);
        CreateNewItem.createNewItem_LV(pc, Weaponid[Weaponran], 1, enchantlvl);
    }
}
