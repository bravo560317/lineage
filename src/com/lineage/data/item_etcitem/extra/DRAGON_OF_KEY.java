package com.lineage.data.item_etcitem.extra;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_DragonDoor;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class DRAGON_OF_KEY extends ItemExecutor
{
    private DRAGON_OF_KEY() {
    }
    
    public static ItemExecutor get() {
        return new DRAGON_OF_KEY();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getMapId() < 10000 && !L1CastleLocation.checkInAllWarArea(pc.getLocation())) {
            pc.sendPackets(new S_DragonDoor(pc));
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
