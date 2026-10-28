package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_IdentifyDesc;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Appraisal_Reel extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Appraisal_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        if (item2 == null) {
            return;
        }
        else {
            if (!item2.isIdentified()) {
                item2.setIdentified(true);
                pc.getInventory().updateItem(item2, 2);
            }
            pc.sendPackets(new S_IdentifyDesc(item2));
            pc.sendPackets(new S_ItemStatus(item2));
            pc.getInventory().removeItem(item, 1L);
            return;
        }
    }
}
