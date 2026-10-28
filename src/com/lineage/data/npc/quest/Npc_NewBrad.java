package com.lineage.data.npc.quest;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_NewBrad extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_NewBrad.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_NewBrad();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "newbrad1"));
        }
        catch (Exception e) {
            Npc_NewBrad._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            if (cmd.equalsIgnoreCase("a")) {
                L1Teleport.teleport(pc, 32740, 32834, (short)72, 5, true);
            }
            else if (cmd.equalsIgnoreCase("b")) {
                if (pc.getInventory().consumeItem(92121, 1L)) {
                    L1Teleport.teleport(pc, 32797, 32799, (short)2100, 5, true);
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "newbrad3"));
                }
            }
        }
        catch (Exception e) {
            Npc_NewBrad._log.error(e.getLocalizedMessage(), e);
        }
    }
}
