package com.lineage.data.item_etcitem.quest;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class MarbinBox extends ItemExecutor
{
    private final Random _random;
    
    public MarbinBox() {
        this._random = new Random();
    }
    
    public static ItemExecutor get() {
        return new MarbinBox();
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
            int maxchargeCount = item.getChargeCount();
            if (maxchargeCount - 1 <= 0) {
                item.setChargeCount(0);
                pc.getInventory().removeItem(item, 1L);
                CreateNewItem.createNewItem(pc, 92119, 1L);
            }
            else {
                item.setChargeCount(maxchargeCount - 1);
                CreateNewItem.createNewItem(pc, 92118, 1L);
            }
            pc.getInventory().updateItem(item, 128);
            return;
        }
    }
}
