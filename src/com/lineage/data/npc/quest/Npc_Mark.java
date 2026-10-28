package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.KnightLv30_1;
import com.lineage.data.quest.KnightLv15_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Mark extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Mark.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Mark();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark3"));
            }
            else if (pc.isKnight()) {
                if (!pc.getQuest().isEnd(KnightLv15_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark3"));
                    return;
                }
                else if (pc.getQuest().isEnd(KnightLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "markcg"));
                    return;
                }
                else if (pc.getLevel() >= KnightLv30_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(KnightLv30_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark1"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark2"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark3"));
                }
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark3"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark3"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark3"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark3"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark3"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark3"));
            }
        }
        catch (Exception e) {
            Npc_Mark._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isKnight()) {
            if (pc.getQuest().isEnd(KnightLv30_1.QUEST.get_id())) {
                return;
            }
            else if (!pc.getQuest().isStart(KnightLv30_1.QUEST.get_id())) {
                if (cmd.equalsIgnoreCase("quest 13 mark2")) {
                    QuestClass.get().startQuest(pc, KnightLv30_1.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mark2"));
                }
            }
            else {
                isCloseList = true;
            }
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
