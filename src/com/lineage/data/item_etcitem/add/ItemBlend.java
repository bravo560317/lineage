package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Blend;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ItemBlend extends ItemExecutor
{
    private ItemBlend() {
    }
    
    public static ItemExecutor get() {
        return new ItemBlend();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (L1Blend.checkItemId(item.getItem().getItemId()) != 0) {
            L1Blend.getItemBlend(pc, item, item.getItem().getItemId());
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
