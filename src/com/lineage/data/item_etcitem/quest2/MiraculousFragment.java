package com.lineage.data.item_etcitem.quest2;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class MiraculousFragment extends ItemExecutor
{
    public static ItemExecutor get() {
        return new MiraculousFragment();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        long count = item.getCount();
        if (count >= 100L) {
            pc.getInventory().removeItem(item, 100L);
            CreateNewItem.createNewItem(pc, 49352, 1L);
        }
        else {
            pc.sendPackets(new S_ServerMessage(337, "奇蹟的碎片(" + (100L - count) + ")"));
        }
    }
}
