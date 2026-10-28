package com.lineage.data.npc;

import com.add.L1PolyCardRanking;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;

public class Npc_PolyCardRanking extends NpcExecutor {

    public static NpcExecutor get() {
        return new Npc_PolyCardRanking();
    }

    @Override
    public int type() {
        return 3;
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            String[] rank = L1PolyCardRanking.TopPolyCard();
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rankingpoly", rank));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
