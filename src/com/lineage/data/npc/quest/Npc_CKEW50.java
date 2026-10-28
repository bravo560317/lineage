package com.lineage.data.npc.quest;

import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.serverpackets.S_CloseList;
import java.util.Iterator;
import com.lineage.data.QuestClass;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.CKEWLv50_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_CKEW50 extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_CKEW50.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_CKEW50();
    }
    
    @Override
    public int type() {
        return 3;
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
                    return;
                }
                else if (pc.isCrown()) {
                    if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && npc.getNpcId() == 80135) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "50quest_p"));
                    }
                    else {
                        QuestClass.get().startQuest(pc, CKEWLv50_1.QUEST.get_id());
                    }
                }
                else if (pc.isKnight()) {
                    if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && npc.getNpcId() == 80136) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "50quest_k"));
                    }
                    else {
                        QuestClass.get().startQuest(pc, CKEWLv50_1.QUEST.get_id());
                    }
                }
                else if (pc.isElf()) {
                    if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && npc.getNpcId() == 80133) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "50quest_e"));
                    }
                    else {
                        QuestClass.get().startQuest(pc, CKEWLv50_1.QUEST.get_id());
                    }
                }
                else if (pc.isWizard()) {
                    if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && npc.getNpcId() == 80134) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "50quest_w"));
                    }
                    else {
                        QuestClass.get().startQuest(pc, CKEWLv50_1.QUEST.get_id());
                    }
                }
                else if (!pc.isDarkelf() && !pc.isDragonKnight()) {
                    pc.isIllusionist();
                }
            }
        }
        catch (Exception e) {
            Npc_CKEW50._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isCrown()) {
            if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && npc.getNpcId() == 80135 && cmd.equalsIgnoreCase("ent")) {
                this.staraQuest(pc);
            }
            isCloseList = true;
        }
        else if (pc.isKnight()) {
            if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && npc.getNpcId() == 80136 && cmd.equalsIgnoreCase("ent")) {
                this.staraQuest(pc);
            }
            isCloseList = true;
        }
        else if (pc.isElf()) {
            if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && npc.getNpcId() == 80133 && cmd.equalsIgnoreCase("ent")) {
                this.staraQuest(pc);
            }
            isCloseList = true;
        }
        else if (pc.isWizard()) {
            if (pc.getQuest().isStart(CKEWLv50_1.QUEST.get_id()) && npc.getNpcId() == 80134 && cmd.equalsIgnoreCase("ent")) {
                this.staraQuest(pc);
            }
            isCloseList = true;
        }
        else {
            isCloseList = (pc.isDarkelf() || pc.isDragonKnight() || !pc.isIllusionist() || true);
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void staraQuest(L1PcInstance pc) {
        try {
            if (!pc.isInParty()) {
                return;
            }
            else {
                int questid = CKEWLv50_1.QUEST.get_id();
                int mapid = 2000;
                int showId = -1;
                int users = QuestMapTable.get().getTemplate(2000);
                if (users == -1) {
                    users = 127;
                }
                int i = 0;
                for (L1PcInstance otherPc : pc.getParty().partyUsers().values()) {
                    if (otherPc.get_showId() != -1) {
                        showId = otherPc.get_showId();
                    }
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
                    return;
                }
                else {
                    if (showId == -1) {
                        showId = WorldQuest.get().nextId();
                    }
                    L1QuestUser quest = WorldQuest.get().put(showId, 2000, questid, pc);
                    if (quest == null) {
                        Npc_CKEW50._log.error("副本設置過程發生異常!!");
                        pc.sendPackets(new S_CloseList(pc.getId()));
                        return;
                    }
                    else {
                        quest.set_info(false);
                        quest.set_outStop(true);
                        Integer time = QuestMapTable.get().getTime(2000);
                        if (time != null) {
                            quest.set_time(time);
                        }
                        if (pc.isCrown()) {
                            L1Teleport.teleport(pc, 32720, 32900, (short)2000, 2, true);
                        }
                        else if (pc.isKnight()) {
                            L1Teleport.teleport(pc, 32721, 32853, (short)2000, 3, true);
                        }
                        else if (pc.isElf()) {
                            L1Teleport.teleport(pc, 32725, 32940, (short)2000, 1, true);
                        }
                        else if (pc.isWizard()) {
                            L1Teleport.teleport(pc, 32810, 32941, (short)2000, 7, true);
                        }
                        pc.getQuest().set_step(CKEWLv50_1.QUEST.get_id(), 2);
                    }
                }
            }
        }
        catch (Exception e) {
            Npc_CKEW50._log.error(e.getLocalizedMessage(), e);
        }
    }
}
