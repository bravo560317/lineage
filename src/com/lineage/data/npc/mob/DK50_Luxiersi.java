package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.DragonKnightLv50_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class DK50_Luxiersi extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(DK50_Luxiersi.class);
    }
    
    public static NpcExecutor get() {
        return new DK50_Luxiersi();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && pc.getQuest().isStart(DragonKnightLv50_1.QUEST.get_id())) {
                if (pc.getInventory().checkItem(49231)) {
                    return pc;
                }
                else {
                    switch (pc.getQuest().get_step(DragonKnightLv50_1.QUEST.get_id())) {
                        case 3: {
                            CreateNewItem.getQuestItem(pc, npc, 49231, 1L);
                            pc.getQuest().set_step(DragonKnightLv50_1.QUEST.get_id(), 4);
                            break;
                        }
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            DK50_Luxiersi._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
