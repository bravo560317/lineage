package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class RuneEracePotion extends ItemExecutor
{
    public static ItemExecutor get() {
        return new RuneEracePotion();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(itemobj);
        if (tgItem == null) {
            return;
        }
        else {
            if (tgItem.getItemId() >= 600000 && tgItem.getItemId() <= 600080) {
                pc.getInventory().removeItem(item, 1L);
                pc.getInventory().removeItem(tgItem, 1L);
                CreateNewItem.createNewItem(pc, 49928, 1L);
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
            return;
        }
    }
}
