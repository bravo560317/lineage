package com.lineage.data.npc;

import com.add.L1WeaponRanking;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_WeaponRanking extends NpcExecutor {

    public static NpcExecutor get() {
        return new Npc_WeaponRanking();
    }

    @Override
    public int type() {
        return 3;
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            String[] weaponRank = L1WeaponRanking.TopWeapon();
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "weapon_rank2", weaponRank));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
