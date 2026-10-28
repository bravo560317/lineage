package com.lineage.data.npc.shop;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class NPC_Skill extends NpcExecutor
{
    public static NpcExecutor get() {
        return new NPC_Skill();
    }
    
    @Override
    public int type() {
        return 17;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_skill_01"));
    }
}
