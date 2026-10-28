package com.lineage.data.npc.other;

import com.lineage.server.model.SoulTower.L1SoulTower;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_SoulTower extends NpcExecutor
{
    private Npc_SoulTower() {
    }
    
    public static NpcExecutor get() {
        return new Npc_SoulTower();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ch_sihonez1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("enter")) {
            L1SoulTower.get().soulTowerStart(pc);
        }
    }
}
