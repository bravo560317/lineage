package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.QuestClass;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.KnightLv30_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.CrownLv15_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Qunter extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Qunter.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Qunter();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                if (pc.getQuest().isEnd(CrownLv15_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterp11"));
                    return;
                }
                else if (pc.getLevel() >= CrownLv15_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(CrownLv15_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterp9"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterp9"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterp1"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterpev1"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterp12"));
                }
            }
            else if (pc.isKnight()) {
                if (pc.getLawful() < 0) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterk12"));
                    return;
                }
                else if (pc.getQuest().isEnd(KnightLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterkEcg"));
                    return;
                }
                else if (pc.getLevel() >= KnightLv30_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(KnightLv30_1.QUEST.get_id())) {
                        case 0:
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterk9"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterkE1"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterkE2"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterkE3"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterk9"));
                }
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "guntere1"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterw1"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterdw1"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterdwk1"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunteriw1"));
            }
        }
        catch (Exception e) {
            Npc_Qunter._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isCrown()) {
            if (cmd.equalsIgnoreCase("guntertest")) {
                if (!pc.getQuest().isEnd(CrownLv15_1.QUEST.get_id())) {
                    switch (pc.getQuest().get_step(CrownLv15_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterp9"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterp9"));
                            break;
                        }
                        case 2: {
                            pc.getQuest().set_step(CrownLv15_1.QUEST.get_id(), 3);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterpev1"));
                            break;
                        }
                    }
                }
            }
            else if (cmd.equalsIgnoreCase("request spellbook112")) {
                if (pc.getQuest().get_step(CrownLv15_1.QUEST.get_id()) == 3) {
                    if (CreateNewItem.checkNewItem(pc, 40564, 1) < 1L) {
                        isCloseList = true;
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 40564, 1, 40226, 1);
                        QuestClass.get().endQuest(pc, CrownLv15_1.QUEST.get_id());
                        isCloseList = true;
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterp9"));
                }
            }
        }
        else if (pc.isKnight()) {
            if (cmd.equalsIgnoreCase("quest 14 gunterkE2")) {
                pc.getQuest().set_step(KnightLv30_1.QUEST.get_id(), 3);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterkE2"));
            }
            else if (cmd.equalsIgnoreCase("request sword of red knights")) {
                if (pc.getQuest().get_step(KnightLv30_1.QUEST.get_id()) == 3) {
                    if (CreateNewItem.checkNewItem(pc, 40590, 1) < 1L) {
                        isCloseList = true;
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 40590, 1, 30, 1);
                        pc.getQuest().set_step(KnightLv30_1.QUEST.get_id(), 4);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gunterkE3"));
                    }
                }
                else {
                    isCloseList = true;
                }
            }
        }
        else if (pc.isElf() && cmd.equalsIgnoreCase("guntertest")) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "guntereev1"));
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
