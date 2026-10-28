package com.lineage.data.npc;

import com.add.L1PrestigeRanking;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_PrestigeRanking extends NpcExecutor {

    public static NpcExecutor get() {
        return new Npc_PrestigeRanking();
    }

    @Override
    public int type() {
        return 3; // 跟 Npc_WeaponRanking 保持一致
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            String[] prestigeRank = L1PrestigeRanking.TopPrestige();
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ranking", prestigeRank));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
