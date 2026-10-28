package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.DarkElfLv15_2;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Kanguard extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Kanguard.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Kanguard();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard4"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard4"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard4"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard4"));
            }
            else if (pc.isDarkelf()) {
                if (pc.getQuest().isEnd(DarkElfLv15_2.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard3"));
                    return;
                }
                else if (pc.getLevel() >= DarkElfLv15_2.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(DarkElfLv15_2.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard1"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard2"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard5"));
                }
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard4"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard4"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard4"));
            }
        }
        catch (Exception e) {
            Npc_Kanguard._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDarkelf()) {
            if (pc.getLevel() >= DarkElfLv15_2.QUEST.get_questlevel()) {
                if (pc.getQuest().isEnd(DarkElfLv15_2.QUEST.get_id())) {
                    return;
                }
                else if (cmd.equalsIgnoreCase("quest 11 kanguard2")) {
                    QuestClass.get().startQuest(pc, DarkElfLv15_2.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard2"));
                }
                else if (cmd.equalsIgnoreCase("request kanbag")) {
                    if (CreateNewItem.checkNewItem(pc, 40585, 1) < 1L) {
                        isCloseList = true;
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 40585, 1, 40598, 1);
                        QuestClass.get().endQuest(pc, DarkElfLv15_2.QUEST.get_id());
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kanguard3"));
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
