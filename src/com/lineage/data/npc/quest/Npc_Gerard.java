package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.QuestClass;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.KnightLv30_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Gerard extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Gerard.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Gerard();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardp1"));
            }
            else if (pc.isKnight()) {
                if (pc.getQuest().isEnd(KnightLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkEcg"));
                    return;
                }
                else if (pc.getLevel() >= KnightLv30_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(KnightLv30_1.QUEST.get_id())) {
                        case 0:
                        case 1:
                        case 2:
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardk7"));
                            break;
                        }
                        case 4: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkE1"));
                            break;
                        }
                        case 5: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkE2"));
                            break;
                        }
                        case 6: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkE3"));
                            break;
                        }
                        case 7: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkE4"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkE3"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardk7"));
                }
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerarde1"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardw1"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardde1"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardk4"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardk4"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardk4"));
            }
        }
        catch (Exception e) {
            Npc_Gerard._log.error(e.getLocalizedMessage(), e);
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
                isCloseList = true;
            }
            else {
                switch (pc.getQuest().get_step(KnightLv30_1.QUEST.get_id())) {
                    case 4: {
                        if (!cmd.equalsIgnoreCase("quest 16 gerardkE2")) {
                            break;
                        }
                        else {
                            pc.getQuest().set_step(KnightLv30_1.QUEST.get_id(), 5);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkE2"));
                            break;
                        }
                    }
                    case 5: {
                        if (!cmd.equalsIgnoreCase("request potion of rebirth")) {
                            break;
                        }
                        else if (CreateNewItem.checkNewItem(pc, 40544, 1) < 1L) {
                            isCloseList = true;
                            break;
                        }
                        else {
                            CreateNewItem.createNewItem(pc, 40544, 1, 40607, 1);
                            pc.getQuest().set_step(KnightLv30_1.QUEST.get_id(), 6);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkE3"));
                            break;
                        }
                    }
                    case 6: {
                        if (!cmd.equalsIgnoreCase("quest 18 gerardkE4")) {
                            break;
                        }
                        else {
                            pc.getQuest().set_step(KnightLv30_1.QUEST.get_id(), 7);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkE4"));
                            break;
                        }
                    }
                    case 7: {
                        if (!cmd.equalsIgnoreCase("request shield of red knights")) {
                            break;
                        }
                        else if (CreateNewItem.checkNewItem(pc, 40529, 1) < 1L) {
                            isCloseList = true;
                            break;
                        }
                        else {
                            CreateNewItem.createNewItem(pc, 40529, 1, 20230, 1);
                            QuestClass.get().endQuest(pc, KnightLv30_1.QUEST.get_id());
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerardkE5"));
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
