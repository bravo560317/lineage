package com.lineage.data.npc.gam;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.add.GuardTower;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.ItemTable;
import com.lineage.config.ConfigGuardTower;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_GuardTower extends NpcExecutor
{
    private Npc_GuardTower() {
    }
    
    public static NpcExecutor get() {
        return new Npc_GuardTower();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(ConfigGuardTower.LEVEL) + ",");
        L1Item item = ItemTable.get().getTemplate(ConfigGuardTower.NEEDITEM);
        stringBuilder.append(item.getName() + ",");
        stringBuilder.append(String.valueOf(ConfigGuardTower.NEEDITEMCOUNT) + ",");
        L1Item winitem = ItemTable.get().getTemplate(ConfigGuardTower.GIFT);
        stringBuilder.append(winitem.getName() + ",");
        L1Item lostitem = ItemTable.get().getTemplate(ConfigGuardTower.COMFORTGIFT);
        stringBuilder.append(lostitem.getName() + ",");
        stringBuilder.append(String.valueOf(ConfigGuardTower.MAXPC) + ",");
        String[] clientStrAry = stringBuilder.toString().split(",");
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_guard_0", clientStrAry));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("g1")) {
            if (pc.getLevel() >= ConfigGuardTower.LEVEL) {
                GuardTower.get().enterRoom(pc);
            }
            else {
                pc.sendPackets(new S_SystemMessage("\\fR等級不足。"));
            }
        }
    }
}
