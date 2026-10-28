package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.DarkElfLv30_1;
import com.lineage.data.quest.DarkElfLv15_2;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Ronde extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Ronde.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Ronde();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde6"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde6"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde6"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde6"));
            }
            else if (pc.isDarkelf()) {
                if (!pc.getQuest().isEnd(DarkElfLv15_2.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde7"));
                    return;
                }
                else if (pc.getQuest().isEnd(DarkElfLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde5"));
                    return;
                }
                else if (pc.getLevel() >= DarkElfLv30_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(DarkElfLv30_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde1"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde2"));
                            break;
                        }
                        case 2: {
                            if (pc.getInventory().checkItem(40596, 1L)) {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde4"));
                                break;
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde3"));
                                break;
                            }
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde7"));
                }
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde6"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde6"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde6"));
            }
        }
        catch (Exception e) {
            Npc_Ronde._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDarkelf()) {
            if (pc.getQuest().isEnd(DarkElfLv30_1.QUEST.get_id())) {
                return;
            }
            else {
                switch (pc.getQuest().get_step(DarkElfLv30_1.QUEST.get_id())) {
                    case 0: {
                        if (!cmd.equalsIgnoreCase("quest 13 ronde2")) {
                            break;
                        }
                        else {
                            QuestClass.get().startQuest(pc, DarkElfLv30_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde2"));
                            break;
                        }
                    }
                    case 1: {
                        if (!cmd.equalsIgnoreCase("request close list of assassination")) {
                            break;
                        }
                        else if (CreateNewItem.checkNewItem(pc, new int[] { 40554 }, new int[] { 1 }) < 1L) {
                            isCloseList = true;
                            break;
                        }
                        else {
                            CreateNewItem.createNewItem(pc, new int[] { 40554 }, new int[] { 1 }, new int[] { 40556 }, 1L, new int[] { 1 });
                            pc.getQuest().set_step(DarkElfLv30_1.QUEST.get_id(), 2);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde3"));
                            break;
                        }
                    }
                    case 2: {
                        if (cmd.equalsIgnoreCase("quest 15 ronde4")) {
                            if (pc.hasSkillEffect(4007)) {
                                pc.removeSkillEffect(4007);
                            }
                            if (pc.hasSkillEffect(4006)) {
                                pc.removeSkillEffect(4006);
                            }
                            if (pc.hasSkillEffect(4005)) {
                                pc.removeSkillEffect(4005);
                            }
                            pc.setSkillEffect(4003, 1500000);
                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7245));
                            pc.sendPackets(new S_ServerMessage(1454));
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde4"));
                            break;
                        }
                        else if (cmd.equalsIgnoreCase("request rondebag")) {
                            if (CreateNewItem.checkNewItem(pc, new int[] { 40596 }, new int[] { 1 }) < 1L) {
                                isCloseList = true;
                                break;
                            }
                            else {
                                CreateNewItem.createNewItem(pc, new int[] { 40596 }, new int[] { 1 }, new int[] { 40545 }, 1L, new int[] { 1 });
                                QuestClass.get().endQuest(pc, DarkElfLv30_1.QUEST.get_id());
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ronde5"));
                                int[] removeItemId = { 40557, 40558, 40559, 40560, 40561, 40562, 40563 };
                                int[] array;
                                for (int length = (array = removeItemId).length, i = 0; i < length; ++i) {
                                    int itemId = array[i];
                                    L1ItemInstance item = pc.getInventory().checkItemX(itemId, 1L);
                                    if (item != null) {
                                        pc.getInventory().removeItem(item, 1L);
                                    }
                                }
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    default: {
                        isCloseList = true;
                        break;
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
