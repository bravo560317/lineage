package com.lineage.data.npc.other;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Fishing_2 extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Fishing_2();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fk_out_0"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equals("teleport fishing-out")) {
            L1Teleport.teleport(pc, 32613, 32781, (short)4, 4, true);
        }
    }
}
