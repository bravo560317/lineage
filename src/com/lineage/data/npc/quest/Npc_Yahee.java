package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Yahee extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Yahee.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Yahee();
    }
    
    @Override
    public int type() {
        return 33;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.get_hardinR() != null) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_ep009"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html05"));
            }
        }
        catch (Exception e) {
            Npc_Yahee._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void spawn(L1NpcInstance npc) {
        Npc_Yahee.YaheeR yaheeR = new Npc_Yahee.YaheeR(npc);
        GeneralThreadPool.get().execute(yaheeR);
    }
    
    class YaheeR implements Runnable
    {
        private final L1NpcInstance _npc;
        
        public YaheeR(L1NpcInstance npc) {
            this._npc = npc;
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep(7000L);
                this._npc.broadcastPacketAll(new S_NpcChat(this._npc, "$7657"));
            }
            catch (Exception e) {
                Npc_Yahee._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
