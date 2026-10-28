package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.QuestClass;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.DragonKnightLv15_1;
import com.lineage.data.quest.DragonKnightLv30_1;
import com.lineage.data.quest.DragonKnightLv45_1;
import com.lineage.data.quest.DragonKnightLv50_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Prokel extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Prokel.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Prokel();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel1"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel1"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel1"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel1"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel1"));
            }
            else if (pc.isDragonKnight()) {
                if (pc.getQuest().isEnd(DragonKnightLv50_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel32"));
                    return;
                }
                else if (pc.getQuest().isEnd(DragonKnightLv45_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv50_1.QUEST.get_questlevel()) {
                        switch (pc.getQuest().get_step(DragonKnightLv50_1.QUEST.get_id())) {
                            case 0: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel21"));
                                break;
                            }
                            case 1: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel24"));
                                break;
                            }
                            case 2: {
                                if (pc.getInventory().checkItem(49202)) {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel33"));
                                    break;
                                }
                                else {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel27"));
                                    break;
                                }
                            }
                            case 3: {
                                if (pc.getInventory().checkItem(49202)) {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel33"));
                                    break;
                                }
                                else {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel30"));
                                    break;
                                }
                            }
                            case 4: {
                                if (pc.getInventory().checkItem(49231)) {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel37"));
                                    break;
                                }
                                else {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel25"));
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel20"));
                    }
                    return;
                }
                else if (pc.getQuest().isEnd(DragonKnightLv30_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv45_1.QUEST.get_questlevel()) {
                        switch (pc.getQuest().get_step(DragonKnightLv45_1.QUEST.get_id())) {
                            case 0: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel15"));
                                break;
                            }
                            case 1:
                            case 2:
                            case 3: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel16"));
                                break;
                            }
                            case 4: {
                                if (pc.getInventory().checkItem(49224)) {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel17"));
                                    break;
                                }
                                else {
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel19"));
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel14"));
                    }
                    return;
                }
                else if (pc.getQuest().isEnd(DragonKnightLv15_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv30_1.QUEST.get_questlevel()) {
                        switch (pc.getQuest().get_step(DragonKnightLv30_1.QUEST.get_id())) {
                            case 0: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel8"));
                                break;
                            }
                            case 1: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel10"));
                                break;
                            }
                        }
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel7"));
                    }
                }
                else if (pc.getLevel() >= DragonKnightLv15_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(DragonKnightLv15_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel2"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel4"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel22"));
                }
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel1"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel1"));
            }
        }
        catch (Exception e) {
            Npc_Prokel._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDragonKnight()) {
            if (cmd.equalsIgnoreCase("a")) {
                if (pc.getLevel() >= DragonKnightLv15_1.QUEST.get_questlevel()) {
                    CreateNewItem.getQuestItem(pc, npc, 49210, 1L);
                    QuestClass.get().startQuest(pc, DragonKnightLv15_1.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel3"));
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("b")) {
                if (pc.getLevel() >= DragonKnightLv15_1.QUEST.get_questlevel()) {
                    if (CreateNewItem.checkNewItem(pc, new int[] { 49217, 49218, 49219 }, new int[] { 1, 1, 1 }) < 1L) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel6"));
                    }
                    else {
                        CreateNewItem.createNewItem(pc, new int[] { 49217, 49218, 49219 }, new int[] { 1, 1, 1 }, new int[] { 49102, 275 }, 1L, new int[] { 1, 1 });
                        L1ItemInstance item = pc.getInventory().checkItemX(49210, 1L);
                        if (item != null) {
                            pc.getInventory().removeItem(item, 1L);
                        }
                        QuestClass.get().endQuest(pc, DragonKnightLv15_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel5"));
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("c")) {
                if (pc.getQuest().isEnd(DragonKnightLv15_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv30_1.QUEST.get_questlevel()) {
                        CreateNewItem.getQuestItem(pc, npc, 49211, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 49215, 1L);
                        QuestClass.get().startQuest(pc, DragonKnightLv30_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel9"));
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("d")) {
                if (pc.getQuest().isEnd(DragonKnightLv15_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv30_1.QUEST.get_questlevel()) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49221, 1L);
                        if (item != null) {
                            pc.getInventory().removeItem(item, 1L);
                            L1ItemInstance item2 = pc.getInventory().checkItemX(49211, 1L);
                            if (item2 != null) {
                                pc.getInventory().removeItem(item2, 1L);
                            }
                            QuestClass.get().endQuest(pc, DragonKnightLv30_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel11"));
                            CreateNewItem.getQuestItem(pc, npc, 49213, 1L);
                            CreateNewItem.getQuestItem(pc, npc, 49107, 1L);
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel12"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("e")) {
                if (pc.getQuest().isEnd(DragonKnightLv15_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv30_1.QUEST.get_questlevel()) {
                        if (pc.getInventory().checkItem(49223)) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel35"));
                            return;
                        }
                        else if (pc.getInventory().checkItem(49215)) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel35"));
                        }
                        else {
                            CreateNewItem.getQuestItem(pc, npc, 49215, 1L);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel13"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("f")) {
                if (pc.getQuest().isEnd(DragonKnightLv30_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv45_1.QUEST.get_questlevel()) {
                        CreateNewItem.getQuestItem(pc, npc, 49212, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 49209, 1L);
                        CreateNewItem.getQuestItem(pc, npc, 49226, 1L);
                        QuestClass.get().startQuest(pc, DragonKnightLv45_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel16"));
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("g")) {
                if (pc.getQuest().isEnd(DragonKnightLv30_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv45_1.QUEST.get_questlevel()) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49224, 1L);
                        if (item != null) {
                            pc.getInventory().removeItem(item, 1L);
                            L1ItemInstance item2 = pc.getInventory().checkItemX(49212, 1L);
                            if (item2 != null) {
                                pc.getInventory().removeItem(item2, 1L);
                            }
                            QuestClass.get().endQuest(pc, DragonKnightLv45_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel18"));
                            CreateNewItem.getQuestItem(pc, npc, 49214, 1L);
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel19"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("h")) {
                if (pc.getQuest().isEnd(DragonKnightLv45_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv50_1.QUEST.get_questlevel()) {
                        CreateNewItem.getQuestItem(pc, npc, 49546, 1L);
                        QuestClass.get().startQuest(pc, DragonKnightLv50_1.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel22"));
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("i")) {
                if (pc.getQuest().isEnd(DragonKnightLv45_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv50_1.QUEST.get_questlevel()) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49101, 100L);
                        if (item != null) {
                            pc.getInventory().removeItem(item, 100L);
                            L1ItemInstance item2 = pc.getInventory().checkItemX(49546, 1L);
                            if (item2 != null) {
                                pc.getInventory().removeItem(item2, 1L);
                            }
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel33"));
                            pc.getQuest().set_step(DragonKnightLv50_1.QUEST.get_id(), 2);
                            CreateNewItem.getQuestItem(pc, npc, 49547, 1L);
                            CreateNewItem.getQuestItem(pc, npc, 49202, 1L);
                            CreateNewItem.getQuestItem(pc, npc, 49216, 1L);
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel31"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("j")) {
                if (pc.getQuest().isEnd(DragonKnightLv45_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv50_1.QUEST.get_questlevel()) {
                        L1ItemInstance item = pc.getInventory().checkItemX(49231, 1L);
                        if (item != null) {
                            pc.getInventory().removeItem(item);
                            L1ItemInstance item2 = pc.getInventory().checkItemX(49547, 1L);
                            if (item2 != null) {
                                pc.getInventory().removeItem(item2);
                            }
                            L1ItemInstance item3 = pc.getInventory().checkItemX(49207, 1L);
                            if (item3 != null) {
                                pc.getInventory().removeItem(item3);
                            }
                            L1ItemInstance item4 = pc.getInventory().checkItemX(49202, 1L);
                            if (item4 != null) {
                                pc.getInventory().removeItem(item4);
                            }
                            L1ItemInstance item5 = pc.getInventory().checkItemX(49216, 1L);
                            if (item5 != null) {
                                pc.getInventory().removeItem(item5);
                            }
                            L1ItemInstance item6 = pc.getInventory().checkItemX(49229, 1L);
                            if (item6 != null) {
                                pc.getInventory().removeItem(item6);
                            }
                            L1ItemInstance item7 = pc.getInventory().checkItemX(49227, 1L);
                            if (item7 != null) {
                                pc.getInventory().removeItem(item7);
                            }
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel26"));
                            QuestClass.get().endQuest(pc, DragonKnightLv50_1.QUEST.get_id());
                            CreateNewItem.getQuestItem(pc, npc, 49228, 1L);
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(337, "$5733(1)"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("k")) {
                if (pc.getQuest().isEnd(DragonKnightLv45_1.QUEST.get_id())) {
                    if (pc.getLevel() >= DragonKnightLv50_1.QUEST.get_questlevel()) {
                        if (pc.getInventory().checkItem(49202)) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel29"));
                        }
                        else {
                            if (!pc.getInventory().checkItem(49216)) {
                                CreateNewItem.getQuestItem(pc, npc, 49216, 1L);
                            }
                            CreateNewItem.getQuestItem(pc, npc, 49202, 1L);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "prokel28"));
                        }
                    }
                    else {
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
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
