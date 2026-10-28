package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Exp_FB extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Exp_FB.class);
    }
    
    public static NpcExecutor get() {
        return new Exp_FB();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "inflin2"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
    }
}
