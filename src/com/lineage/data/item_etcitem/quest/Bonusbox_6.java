package com.lineage.data.item_etcitem.quest;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class Bonusbox_6 extends ItemExecutor
{
    private final Random _random;
    private final int[] bonus_list;
    
    public Bonusbox_6() {
        this._random = new Random();
        this.bonus_list = new int[] { 56216, 56217, 56218, 56219, 56220, 56221, 56222, 56223, 56224, 56225, 56226, 56227, 56228, 56229, 56230, 56231, 56232, 56233, 49334, 49333, 49332, 49331, 49330, 49329, 49328, 49327, 56235, 49336 };
    }
    
    public static ItemExecutor get() {
        return new Bonusbox_6();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            for (int value = this._random.nextInt(2) + 1, i = 0; i < value; ++i) {
                CreateNewItem.createNewItem(pc, this.bonus_list[this._random.nextInt(this.bonus_list.length)], 1L);
            }
            return;
        }
    }
}
