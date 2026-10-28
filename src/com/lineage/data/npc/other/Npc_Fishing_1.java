package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Fishing_1 extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Fishing_1();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fk_in_1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equals("a") && pc.getLevel() >= 45) {
            L1PolyMorph.undoPoly(pc);
            L1Teleport.teleport(pc, 32794, 32795, (short)5300, 6, true);
        }
        else {
            pc.sendPackets(new S_ServerMessage("等級45以上才能進入釣魚池"));
        }
    }
}
