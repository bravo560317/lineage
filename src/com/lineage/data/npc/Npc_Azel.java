package com.lineage.data.npc;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Azel extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Azel.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Azel();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "azel1"));
        }
        catch (Exception e) {
            Npc_Azel._log.error(e.getLocalizedMessage(), e);
        }
    }
}
