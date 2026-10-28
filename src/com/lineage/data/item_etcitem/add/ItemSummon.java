package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.william.L1WilliamItemSummon;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ItemSummon extends ItemExecutor
{
    public static ItemExecutor get() {
        return new ItemSummon();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        L1WilliamItemSummon.checkItemId(item.getItemId());
        L1WilliamItemSummon.getItemSummon(pc, item, item.getItemId());
        pc.sendPackets(new S_ServerMessage(79));
    }
}
