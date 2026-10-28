package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.DarkElfLv50_1;
import com.lineage.data.quest.DarkElfLv45_1;
import com.lineage.data.quest.DarkElfLv30_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Bluedika extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Bluedika.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Bluedika();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq4"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq4"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq4"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq4"));
            }
            else if (pc.isDarkelf()) {
                if (!pc.getQuest().isEnd(DarkElfLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq3"));
                    return;
                }
                else if (pc.getQuest().isEnd(DarkElfLv45_1.QUEST.get_id())) {
                    if (pc.getQuest().isEnd(DarkElfLv50_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq8"));
                        return;
                    }
                    else {
                        if (pc.getLevel() >= DarkElfLv50_1.QUEST.get_questlevel()) {
                            switch (pc.getQuest().get_step(DarkElfLv50_1.QUEST.get_id())) {
                                case 0: {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq6"));
                                    break;
                                }
                                default: {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq7"));
                                    break;
                                }
                            }
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq5"));
                        }
                        return;
                    }
                }
                else if (pc.getLevel() >= DarkElfLv45_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(DarkElfLv45_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq1"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq2"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde7"));
                }
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq4"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq4"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq4"));
            }
        }
        catch (Exception e) {
            Npc_Bluedika._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDarkelf()) {
            if (pc.getQuest().isEnd(DarkElfLv45_1.QUEST.get_id())) {
                isCloseList = true;
                if (pc.getQuest().isEnd(DarkElfLv50_1.QUEST.get_id())) {
                    isCloseList = true;
                }
                else {
                    switch (pc.getQuest().get_step(DarkElfLv50_1.QUEST.get_id())) {
                        case 0: {
                            if (!cmd.equalsIgnoreCase("quest 24 bluedikaq7")) {
                                break;
                            }
                            else {
                                QuestClass.get().startQuest(pc, DarkElfLv50_1.QUEST.get_id());
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq7"));
                                isCloseList = false;
                                break;
                            }
                        }
                        default: {
                            if (!cmd.equalsIgnoreCase("request finger of death")) {
                                break;
                            }
                            else if (CreateNewItem.checkNewItem(pc, new int[] { 20037, 40603, 40541 }, new int[] { 1, 1, 1 }) < 1L) {
                                isCloseList = true;
                                break;
                            }
                            else {
                                CreateNewItem.createNewItem(pc, new int[] { 20037, 40603, 40541 }, new int[] { 1, 1, 1 }, new int[] { 13 }, 1L, new int[] { 1 });
                                QuestClass.get().endQuest(pc, DarkElfLv50_1.QUEST.get_id());
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq8"));
                                isCloseList = false;
                                break;
                            }
                        }
                    }
                }
            }
            else {
                switch (pc.getQuest().get_step(DarkElfLv45_1.QUEST.get_id())) {
                    case 0: {
                        if (!cmd.equalsIgnoreCase("quest 17 bluedikaq2")) {
                            break;
                        }
                        else {
                            QuestClass.get().startQuest(pc, DarkElfLv45_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq2"));
                            break;
                        }
                    }
                    default: {
                        if (!cmd.equalsIgnoreCase("request bluedikabag")) {
                            break;
                        }
                        else if (CreateNewItem.checkNewItem(pc, new int[] { 40572, 40595 }, new int[] { 1, 1 }) < 1L) {
                            isCloseList = true;
                            break;
                        }
                        else {
                            CreateNewItem.createNewItem(pc, new int[] { 40572, 40595 }, new int[] { 1, 1 }, new int[] { 40553 }, 1L, new int[] { 1 });
                            QuestClass.get().endQuest(pc, DarkElfLv45_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bluedikaq3"));
                            break;
                        }
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
