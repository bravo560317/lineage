package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_GoddessAtonement extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_GoddessAtonement();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "restore1pk"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("pk")) {
            if (pc.getLawful() < 30000) {
                pc.sendPackets(new S_ServerMessage(559));
            }
            else if (pc.get_PKcount() < 50) {
                pc.sendPackets(new S_ServerMessage(560));
            }
            else if (pc.getInventory().consumeItem(40308, 700000L)) {
                pc.set_PKcount(pc.get_PKcount() - 5);
                pc.sendPackets(new S_ServerMessage(561, String.valueOf(pc.get_PKcount())));
            }
            else {
                pc.sendPackets(new S_ServerMessage(189));
            }
        }
    }
}
