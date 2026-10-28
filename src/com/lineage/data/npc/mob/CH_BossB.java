package com.lineage.data.npc.mob;

import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class CH_BossB extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(CH_BossB.class);
    }
    
    public static NpcExecutor get() {
        return new CH_BossB();
    }
    
    @Override
    public int type() {
        return 40;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && pc.get_hardinR() != null) {
                pc.get_hardinR().boss_b_death();
            }
            return pc;
        }
        catch (Exception e) {
            CH_BossB._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    @Override
    public void spawn(L1NpcInstance npc) {
        try {
            switch (npc.getNpcId()) {
                case 91296: {
                    CH_BossB.BossBR boss = new CH_BossB.BossBR(npc);
                    GeneralThreadPool.get().execute(boss);
                    break;
                }
            }
        }
        catch (Exception e) {
            CH_BossB._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    class BossBR implements Runnable
    {
        private final L1NpcInstance _npc;
        
        public BossBR(L1NpcInstance npc) {
            this._npc = npc;
        }
        
        @Override
        public void run() {
            try {
                if (!this._npc.isDead()) {
                    Thread.sleep(4000L);
                    this._npc.broadcastPacketAll(new S_NpcChat(this._npc, "$7588"));
                }
                if (!this._npc.isDead()) {
                    Thread.sleep(4000L);
                    this._npc.broadcastPacketAll(new S_NpcChat(this._npc, "$7591"));
                }
                if (!this._npc.isDead()) {
                    Thread.sleep(4000L);
                    this._npc.broadcastPacketAll(new S_NpcChat(this._npc, "$7593"));
                }
            }
            catch (Exception e) {
                CH_BossB._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
