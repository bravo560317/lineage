package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Value_Map extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Value_Map();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getInventory().checkItem(40621)) {
            pc.sendPackets(new S_ServerMessage(79));
        }
        else if (pc.getX() >= 32856 && pc.getX() <= 32858 && pc.getY() >= 32857 && pc.getY() <= 32858 && pc.getMapId() == 443) {
            L1Teleport.teleport(pc, 32794, 32839, (short)443, 5, true);
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
