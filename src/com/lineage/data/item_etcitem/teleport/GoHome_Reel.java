package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.GetbackTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class GoHome_Reel extends ItemExecutor
{
    public static ItemExecutor get() {
        return new GoHome_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            if (pc.getMap().isEscapable() || pc.isGm()) {
                int[] loc = GetbackTable.GetBack_Location(pc, true);
                L1Teleport.teleport(pc, loc[0], loc[1], (short)loc[2], 5, true);
                pc.getInventory().removeItem(item, 1L);
                if (pc.isActived()) {
                    pc.setActived(false);
                    pc.killSkillEffectTimer(8853);
                    pc.sendPackets(new S_ServerMessage("掛機中請勿使用手動卷軸。"));
                    pc.sendPackets(new S_ServerMessage("自動狩獵已停止。"));
                    if (pc.getAutoX() > 0) {
                        pc.setAutoX(0);
                        pc.setAutoY(0);
                        pc.setAutoMap(0);
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(647));
                pc.sendPackets(new S_Paralysis(7, false));
            }
            L1BuffUtil.cancelAbsoluteBarrier(pc);
            return;
        }
    }
}
