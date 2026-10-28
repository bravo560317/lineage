package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.ElfLv15_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class E15_ContaminatedCyclops extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(E15_ContaminatedCyclops.class);
        E15_ContaminatedCyclops._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new E15_ContaminatedCyclops();
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
                if (pc.getQuest().isEnd(ElfLv15_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(ElfLv15_1.QUEST.get_id()) && E15_ContaminatedCyclops._random.nextInt(100) < 25) {
                    CreateNewItem.getQuestItem(pc, npc, 40696, 1L);
                }
            }
            return pc;
        }
        catch (Exception e) {
            E15_ContaminatedCyclops._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
