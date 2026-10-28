package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.ElfLv45_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class E45_SinSepia extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(E45_SinSepia.class);
    }
    
    public static NpcExecutor get() {
        return new E45_SinSepia();
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
                if (pc.getQuest().isEnd(ElfLv45_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getInventory().checkItem(40602)) {
                    return pc;
                }
                else if (pc.getQuest().isStart(ElfLv45_1.QUEST.get_id())) {
                    pc.getQuest().set_step(ElfLv45_1.QUEST.get_id(), 4);
                    CreateNewItem.getQuestItem(pc, npc, 40602, 1L);
                }
            }
            return pc;
        }
        catch (Exception e) {
            E45_SinSepia._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
