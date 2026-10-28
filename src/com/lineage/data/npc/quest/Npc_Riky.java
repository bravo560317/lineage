package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.KnightLv15_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Riky extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Riky.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Riky();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky2"));
            }
            else if (pc.isKnight()) {
                if (pc.getQuest().isEnd(KnightLv15_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rikycg"));
                    return;
                }
                else if (pc.getLevel() >= KnightLv15_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(KnightLv15_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky1"));
                            QuestClass.get().startQuest(pc, KnightLv15_1.QUEST.get_id());
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky3"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky5"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rikycg"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky6"));
                }
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky2"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky2"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky2"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky2"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky2"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky2"));
            }
        }
        catch (Exception e) {
            Npc_Riky._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isKnight()) {
            if (pc.getQuest().isEnd(KnightLv15_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("request hood of knight")) {
                if (pc.getQuest().isStart(KnightLv15_1.QUEST.get_id())) {
                    if (CreateNewItem.checkNewItem(pc, 40608, 1) < 1L) {
                        isCloseList = true;
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 40608, 1, 20005, 1);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riky4"));
                        pc.getQuest().set_step(KnightLv15_1.QUEST.get_id(), 2);
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
