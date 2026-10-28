package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.DarkElfLv30_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class DE30_Traitor extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(DE30_Traitor.class);
    }
    
    public static NpcExecutor get() {
        return new DE30_Traitor();
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
                if (pc.getQuest().isEnd(DarkElfLv30_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(DarkElfLv30_1.QUEST.get_id())) {
                    if (pc.getInventory().checkItem(40596)) {
                        return pc;
                    }
                    else {
                        if (!pc.get_otherList().ATKNPC.contains((Integer)npc.getNpcId())) {
                            pc.get_otherList().ATKNPC.add(npc.getNpcId());
                        }
                        if (pc.get_otherList().ATKNPC.size() >= 7) {
                            CreateNewItem.getQuestItem(pc, npc, 40596, 1L);
                            pc.get_otherList().ATKNPC.clear();
                            return pc;
                        }
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            DE30_Traitor._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
