package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.QuestClass;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.CrownLv15_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Zero extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Zero.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Zero();
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
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero6"));
                }
                else if (pc.getLevel() >= CrownLv15_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(CrownLv15_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero1"));
                            QuestClass.get().startQuest(pc, CrownLv15_1.QUEST.get_id());
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero1"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero5"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero6"));
                }
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero2"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero2"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero2"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero2"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero2"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero2"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero2"));
            }
        }
        catch (Exception e) {
            Npc_Zero._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isCrown()) {
            if (cmd.equalsIgnoreCase("request cloak of red")) {
                switch (pc.getQuest().get_step(CrownLv15_1.QUEST.get_id())) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        if (CreateNewItem.checkNewItem(pc, 40565, 1) < 1L) {
                            isCloseList = true;
                            break;
                        }
                        else {
                            CreateNewItem.createNewItem(pc, 40565, 1, 20065, 1);
                            pc.getQuest().set_step(CrownLv15_1.QUEST.get_id(), 2);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero5"));
                            break;
                        }
                    }
                    default: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zero5"));
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
