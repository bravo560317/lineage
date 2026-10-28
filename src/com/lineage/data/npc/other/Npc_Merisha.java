package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Merisha extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Merisha();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "merisha1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equals("0")) {
            if (pc.getInventory().checkItem(40969, 20L) && !pc.hasSkillEffect(8000)) {
                pc.setSkillEffect(8000, 3600000);
                pc.getInventory().consumeItem(40969, 20L);
                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 9268));
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "merisha2"));
            }
            else if (!pc.getInventory().checkItem(40969, 20L)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "merisha3"));
                return;
            }
            else if (pc.hasSkillEffect(8000)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "merisha2"));
            }
        }
    }
}
