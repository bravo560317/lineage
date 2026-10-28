package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Weak_Soul extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Weak_Soul();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getX() >= 32844 && pc.getX() <= 32845 && pc.getY() >= 32693 && pc.getY() <= 32694 && pc.getMapId() == 550) {
            L1Teleport.teleport(pc, 32833, 33089, (short)550, 5, true);
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
