package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.QuestClass;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.DarkElfLv15_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Pears extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Pears.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Pears();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (!pc.isCrown() && !pc.isKnight() && !pc.isElf() && !pc.isWizard()) {
                if (pc.isDarkelf()) {
                    if (pc.getQuest().isEnd(DarkElfLv15_1.QUEST.get_id())) {
                        return;
                    }
                    else if (pc.getLevel() >= DarkElfLv15_1.QUEST.get_questlevel()) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pears1"));
                    }
                }
                else if (!pc.isDragonKnight()) {
                    pc.isIllusionist();
                }
            }
        }
        catch (Exception e) {
            Npc_Pears._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDarkelf()) {
            if (pc.getLevel() >= DarkElfLv15_1.QUEST.get_questlevel()) {
                if (pc.getQuest().isEnd(DarkElfLv15_1.QUEST.get_id())) {
                    return;
                }
                else if (cmd.equalsIgnoreCase("request silver sting knife")) {
                    if (CreateNewItem.checkNewItem(pc, 40321, 1) < 1L) {
                        isCloseList = true;
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 40321, 1, 40738, 1000);
                        QuestClass.get().startQuest(pc, DarkElfLv15_1.QUEST.get_id());
                        QuestClass.get().endQuest(pc, DarkElfLv15_1.QUEST.get_id());
                        isCloseList = true;
                    }
                }
                else if (cmd.equalsIgnoreCase("request heavy sting knife")) {
                    if (CreateNewItem.checkNewItem(pc, 40322, 1) < 1L) {
                        isCloseList = true;
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 40322, 1, 40740, 2000);
                        QuestClass.get().startQuest(pc, DarkElfLv15_1.QUEST.get_id());
                        QuestClass.get().endQuest(pc, DarkElfLv15_1.QUEST.get_id());
                        isCloseList = true;
                    }
                }
                else if (cmd.equalsIgnoreCase("request pears itembag")) {
                    if (CreateNewItem.checkNewItem(pc, 40323, 1) < 1L) {
                        isCloseList = true;
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 40323, 1, 40715, 1);
                        QuestClass.get().startQuest(pc, DarkElfLv15_1.QUEST.get_id());
                        QuestClass.get().endQuest(pc, DarkElfLv15_1.QUEST.get_id());
                        isCloseList = true;
                    }
                }
                else if (cmd.equalsIgnoreCase("request jin gauntlet")) {
                    if (CreateNewItem.checkNewItem(pc, 40324, 1) < 1L) {
                        isCloseList = true;
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 40324, 1, 194, 1);
                        QuestClass.get().startQuest(pc, DarkElfLv15_1.QUEST.get_id());
                        QuestClass.get().endQuest(pc, DarkElfLv15_1.QUEST.get_id());
                        isCloseList = true;
                    }
                }
            }
        }
        else {
            isCloseList = true;
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
