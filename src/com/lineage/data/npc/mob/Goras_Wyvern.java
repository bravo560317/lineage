package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.IllusionistLv50_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Goras_Wyvern extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Goras_Wyvern.class);
    }
    
    public static NpcExecutor get() {
        return new Goras_Wyvern();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && pc.getQuest().isStart(IllusionistLv50_1.QUEST.get_id())) {
                switch (pc.getQuest().get_step(IllusionistLv50_1.QUEST.get_id())) {
                    case 2: {
                        CreateNewItem.getQuestItem(pc, npc, 49204, 1L);
                        break;
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            Goras_Wyvern._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
