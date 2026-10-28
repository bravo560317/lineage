package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.DragonKnightLv30_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class DK30_OrcEmissaryB extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(DK30_OrcEmissaryB.class);
    }
    
    public static NpcExecutor get() {
        return new DK30_OrcEmissaryB();
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
                if (pc.getQuest().isEnd(DragonKnightLv30_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(DragonKnightLv30_1.QUEST.get_id())) {
                    if (pc.getInventory().checkItem(49221)) {
                        return pc;
                    }
                    else {
                        CreateNewItem.getQuestItem(pc, npc, 49221, 1L);
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            DK30_OrcEmissaryB._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
