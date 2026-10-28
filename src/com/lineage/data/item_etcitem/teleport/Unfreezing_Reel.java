package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.datatables.lock.UpdateLocReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Unfreezing_Reel extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Unfreezing_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            UpdateLocReading.get().setPcLoc(pc.getAccountName());
            pc.sendPackets(new S_ServerMessage("帳號內所有人物傳送回指定位置！"));
            return;
        }
    }
}
