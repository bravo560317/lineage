package com.lineage.data.npc;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Bossroom extends NpcExecutor
{
    private Bossroom() {
    }
    
    public static NpcExecutor get() {
        return new Bossroom();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bossroom"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equals("enterboosroom1")) {
            TextTask.getInstance().enterTextTask(pc);
        }
        else if (cmd.equals("enterboosroom2")) {
            TextTask1.getInstance().enterTextTask(pc);
        }
        else if (cmd.equals("enterboosroom3")) {
            TextTask2.getInstance().enterTextTask(pc);
        }
    }
}
