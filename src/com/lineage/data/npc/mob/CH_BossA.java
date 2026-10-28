package com.lineage.data.npc.mob;

import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class CH_BossA extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(CH_BossA.class);
    }
    
    public static NpcExecutor get() {
        return new CH_BossA();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && pc.get_hardinR() != null) {
                pc.get_hardinR().boss_a_death();
            }
            return pc;
        }
        catch (Exception e) {
            CH_BossA._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
