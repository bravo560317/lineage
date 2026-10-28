package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.DarkElfLv50_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class DE50_DiscipleDeath extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(DE50_DiscipleDeath.class);
    }
    
    public static NpcExecutor get() {
        return new DE50_DiscipleDeath();
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
                if (pc.getQuest().isEnd(DarkElfLv50_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(DarkElfLv50_1.QUEST.get_id())) {
                    if (pc.getInventory().checkItem(40541)) {
                        return pc;
                    }
                    else {
                        pc.getQuest().set_step(DarkElfLv50_1.QUEST.get_id(), 5);
                        CreateNewItem.getQuestItem(pc, npc, 40541, 1L);
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            DE50_DiscipleDeath._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
