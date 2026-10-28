package com.lineage.data.item_etcitem;

import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Level_Down extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Level_Down();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getLevel() > 9) {
            pc.setExp(0L);
            pc.onChangeExp();
            pc.sendPackets(new S_ServerMessage(822));
            pc.getInventory().removeItem(item, 1L);
            RecordTable.get().reshp(pc.getName());
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
