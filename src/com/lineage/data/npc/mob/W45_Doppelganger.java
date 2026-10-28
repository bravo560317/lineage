package com.lineage.data.npc.mob;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.WizardLv45_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class W45_Doppelganger extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(W45_Doppelganger.class);
    }
    
    public static NpcExecutor get() {
        return new W45_Doppelganger();
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
                if (pc.getQuest().isEnd(WizardLv45_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(WizardLv45_1.QUEST.get_id())) {
                    if (pc.getInventory().checkItem(40536)) {
                        return pc;
                    }
                    else if (pc.getInventory().checkItem(40542)) {
                        return pc;
                    }
                    else {
                        L1ItemInstance item = npc.getInventory().checkItemX(40032, 1L);
                        if (item != null && npc.getInventory().removeItem(item, 1L) == 1L) {
                            CreateNewItem.getQuestItem(pc, npc, 40542, 1L);
                        }
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            W45_Doppelganger._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
