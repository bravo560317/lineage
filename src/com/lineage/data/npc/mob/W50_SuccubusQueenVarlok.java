package com.lineage.data.npc.mob;

import com.lineage.data.quest.WizardLv50_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class W50_SuccubusQueenVarlok extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(W50_SuccubusQueenVarlok.class);
        W50_SuccubusQueenVarlok._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new W50_SuccubusQueenVarlok();
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
                if (pc.getQuest().isEnd(WizardLv50_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getInventory().checkItem(49164)) {
                    return pc;
                }
                else if (pc.getQuest().isStart(WizardLv50_1.QUEST.get_id())) {
                    switch (pc.getQuest().get_step(WizardLv50_1.QUEST.get_id())) {
                        case 2: {
                            W50_SuccubusQueenVarlok._random.nextInt(100);
                            break;
                        }
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            W50_SuccubusQueenVarlok._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
