package com.lineage.data.npc;

import com.add.L1FactionRanking;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_FactionRanking extends NpcExecutor {

    public static NpcExecutor get() {
        return new Npc_FactionRanking();
    }

    @Override
    public int type() {
        return 3; // 跟其他排行榜保持一致
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            String[] factionRank = L1FactionRanking.TopFaction();
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ranking2", factionRank));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
