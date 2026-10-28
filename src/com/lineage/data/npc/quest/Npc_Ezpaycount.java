package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.event.RankingSet;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Ezpaycount extends NpcExecutor
{
    private Npc_Ezpaycount() {
    }
    
    public static NpcExecutor get() {
        return new Npc_Ezpaycount();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        String[] userName = (String[])null;
        userName = RankingSet._killList;
        if (userName != null) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_ezpay", userName));
        }
    }
}
