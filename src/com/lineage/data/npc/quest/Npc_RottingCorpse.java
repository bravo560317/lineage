package com.lineage.data.npc.quest;

import com.lineage.server.templates.L1QuestUser;
import java.util.Iterator;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.CKEWLv50_1;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_RottingCorpse extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_RottingCorpse.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_RottingCorpse();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (!pc.isInParty()) {
                return;
            }
            else {
                int i = 0;
                for (L1PcInstance otherPc : pc.getParty().partyUsers().values()) {
                    if (otherPc.isCrown()) {
                        ++i;
                    }
                    else if (otherPc.isKnight()) {
                        i += 2;
                    }
                    else if (otherPc.isElf()) {
                        i += 4;
                    }
                    else if (otherPc.isWizard()) {
                        i += 8;
                    }
                    else {
                        continue;
                    }
                }
                if (i != 15) {
                    L1QuestUser quest = WorldQuest.get().get(pc.get_showId());
                    quest.endQuest();
                    return;
                }
                else if (pc.isCrown()) {
                    if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && !pc.getInventory().checkItem(49239)) {
                        CreateNewItem.getQuestItem(pc, npc, 49239, 1L);
                    }
                }
                else if (pc.isKnight()) {
                    if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && !pc.getInventory().checkItem(49239)) {
                        CreateNewItem.getQuestItem(pc, npc, 49239, 1L);
                    }
                }
                else if (pc.isElf()) {
                    if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && !pc.getInventory().checkItem(49239)) {
                        CreateNewItem.getQuestItem(pc, npc, 49239, 1L);
                    }
                }
                else if (pc.isWizard()) {
                    if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && !pc.getInventory().checkItem(49239)) {
                        CreateNewItem.getQuestItem(pc, npc, 49239, 1L);
                    }
                }
                else if (!pc.isDarkelf() && !pc.isDragonKnight()) {
                    pc.isIllusionist();
                }
            }
        }
        catch (Exception e) {
            Npc_RottingCorpse._log.error(e.getLocalizedMessage(), e);
        }
    }
}
