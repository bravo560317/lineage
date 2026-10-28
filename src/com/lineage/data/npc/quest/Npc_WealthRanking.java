package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.timecontroller.event.ranking.RankingWealthTimer;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_WealthRanking extends NpcExecutor
{
    private Npc_WealthRanking() {
    }
    
    public static NpcExecutor get() {
        return new Npc_WealthRanking();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        String[] userName = RankingWealthTimer.userName();
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_w_1", userName));
    }
}
