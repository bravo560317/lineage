package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Dark_Temple_Key3 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Dark_Temple_Key3();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            if (pc.getMapId() == 523) {
                L1Teleport.teleport(pc, 32691, 32894, (short)524, 5, true);
            }
            else {
                pc.sendPackets(new S_ServerMessage("此座標尚未再傳送範圍內.另尋座標點擊"));
            }
            return;
        }
    }
}
