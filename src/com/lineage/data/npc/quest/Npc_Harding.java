package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Harding extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Harding.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Harding();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.get_hardinR() != null) {
                if (pc.get_hardinR().get_time() > 0 && pc.get_hardinR().get_time() <= 180) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_ep002"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_ep001"));
                }
            }
        }
        catch (Exception e) {
            Npc_Harding._log.error(e.getLocalizedMessage(), e);
        }
    }
}
