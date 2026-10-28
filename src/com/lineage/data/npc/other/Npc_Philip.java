package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_ShopBuyList;
import com.lineage.server.serverpackets.S_ShopSellList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Philip extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(Npc_Philip.class);
        Npc_Philip._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new Npc_Philip();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getLawful() < 0) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "philip2"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "philip1"));
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
