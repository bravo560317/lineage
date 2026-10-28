package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.WizardLv15_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Jem extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Jem.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Jem();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem7"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem7"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem7"));
            }
            else if (pc.isWizard()) {
                if (pc.getQuest().isEnd(WizardLv15_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem6"));
                }
                else if (pc.getLevel() >= WizardLv15_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(WizardLv15_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem1"));
                            QuestClass.get().startQuest(pc, WizardLv15_1.QUEST.get_id());
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem1"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem4"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem2"));
                }
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem7"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem7"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem7"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem7"));
            }
        }
        catch (Exception e) {
            Npc_Jem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isWizard()) {
            if (pc.getQuest().isEnd(WizardLv15_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("request cursed spellbook")) {
                if (pc.getQuest().isStart(WizardLv15_1.QUEST.get_id())) {
                    if (CreateNewItem.checkNewItem(pc, new int[] { 40539, 40538 }, new int[] { 1, 1 }) < 1L) {
                        pc.sendPackets(new S_CloseList(pc.getId()));
                    }
                    else {
                        CreateNewItem.createNewItem(pc, new int[] { 40539, 40538 }, new int[] { 1, 1 }, new int[] { 40591 }, 1L, new int[] { 1 });
                        pc.getQuest().set_step(WizardLv15_1.QUEST.get_id(), 2);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem4"));
                    }
                }
            }
            else if (cmd.equalsIgnoreCase("request book of magical powers")) {
                if (CreateNewItem.checkNewItem(pc, new int[] { 40605, 40591 }, new int[] { 1, 1 }) < 1L) {
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
                else {
                    CreateNewItem.createNewItem(pc, new int[] { 40605, 40591 }, new int[] { 1, 1 }, new int[] { 20226 }, 1L, new int[] { 1 });
                    QuestClass.get().endQuest(pc, WizardLv15_1.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jem6"));
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
