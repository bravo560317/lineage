package com.lineage.data.npc.quest2;

import com.lineage.server.templates.L1QuestUser;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Mimic extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Mimic.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Mimic();
    }
    
    @Override
    public int type() {
        return 12;
    }
    
    @Override
    public void attack(L1PcInstance pc, L1NpcInstance npc) {
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && npc.get_quest_id() > 0) {
                L1QuestUser quest = WorldQuest.get().get(npc.get_showId());
                if (quest != null && quest.get_mapid() == 9101 && quest.get_orimR() != null) {
                    CreateNewItem.createNewItem(pc, 56252, 1L);
                    quest.get_orimR().checkQuestOrder(pc, npc.get_quest_id());
                }
            }
            return pc;
        }
        catch (Exception e) {
            Npc_Mimic._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
