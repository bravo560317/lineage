package com.lineage.data.npc.mob;

import com.lineage.data.quest.KnightLv45_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class K45_GiantGuardian extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(K45_GiantGuardian.class);
    }
    
    public static NpcExecutor get() {
        return new K45_GiantGuardian();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null) {
                if (pc.getQuest().isEnd(KnightLv45_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(KnightLv45_1.QUEST.get_id())) {
                    if (pc.getInventory().checkItem(40528)) {
                        return pc;
                    }
                    else if (pc.getInventory().checkItem(40597)) {
                        return pc;
                    }
                    else if (pc.getInventory().checkItem(40537)) {
                        return pc;
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            K45_GiantGuardian._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
