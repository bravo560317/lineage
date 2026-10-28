package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Assassin_Credential extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Assassin_Credential();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getX() == 32778 && pc.getY() == 32738 && pc.getMapId() == 21) {
            L1Teleport.teleport(pc, 32781, 32728, (short)21, 5, true);
        }
        else if (pc.getX() == 32781 && pc.getY() == 32728 && pc.getMapId() == 21) {
            L1Teleport.teleport(pc, 32778, 32738, (short)21, 5, true);
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
