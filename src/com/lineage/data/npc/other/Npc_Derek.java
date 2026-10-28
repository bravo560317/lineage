package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_ShopBuyList;
import com.lineage.server.serverpackets.S_ShopSellList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Derek extends NpcExecutor
{
    private static boolean _spr;
    
    static {
        Npc_Derek._spr = false;
    }
    
    public static NpcExecutor get() {
        return new Npc_Derek();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getLawful() < 0) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "derek2"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "derek1"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("buy")) {
            pc.sendPackets(new S_ShopSellList(npc.getId()));
        }
        else if (cmd.equalsIgnoreCase("sell")) {
            pc.sendPackets(new S_ShopBuyList(npc.getId(), pc));
        }
    }
}
