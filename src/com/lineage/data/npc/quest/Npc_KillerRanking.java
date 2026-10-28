package com.lineage.data.npc.quest;

import com.lineage.server.timecontroller.event.ranking.RankingKillTimer;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_KillerRanking extends NpcExecutor
{
    private Npc_KillerRanking() {
    }
    
    public static NpcExecutor get() {
        return new Npc_KillerRanking();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_k_1", new String[] { String.valueOf(ConfigOther.killlevel) }));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        String[] userName = null;
        if (cmd.equalsIgnoreCase("1")) {
            userName = RankingKillTimer.killName();
            if (userName != null) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_k_2", userName));
            }
        }
        else if (cmd.equalsIgnoreCase("2")) {
            userName = RankingKillTimer.deathName();
            if (userName != null) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_k_3", userName));
            }
        }
    }
}
