package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.quest.DarkElfLv50_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Kima extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Kima.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Kima();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
            }
            else if (pc.isDarkelf()) {
                if (pc.getQuest().isEnd(DarkElfLv50_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
                    return;
                }
                else if (pc.getLevel() >= DarkElfLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(DarkElfLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
                            break;
                        }
                        case 1:
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kimaq1"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kimaq3"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kimaq4"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
                }
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kima1"));
            }
        }
        catch (Exception e) {
            Npc_Kima._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDarkelf()) {
            if (pc.getQuest().isEnd(DarkElfLv50_1.QUEST.get_id())) {
                isCloseList = true;
            }
            else {
                switch (pc.getQuest().get_step(DarkElfLv50_1.QUEST.get_id())) {
                    case 1:
                    case 2: {
                        if (!cmd.equalsIgnoreCase("request mask of true")) {
                            break;
                        }
                        else {
                            L1ItemInstance item = pc.getInventory().checkItemX(40583, 1L);
                            if (item == null) {
                                pc.sendPackets(new S_ServerMessage(337, "$2654 (1)"));
                                isCloseList = true;
                                break;
                            }
                            else {
                                pc.getInventory().removeItem(item, 1L);
                                pc.getQuest().set_step(DarkElfLv50_1.QUEST.get_id(), 3);
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kimaq3"));
                                break;
                            }
                        }
                    }
                    case 3: {
                        if (!cmd.equalsIgnoreCase("quest 26 kimaq4")) {
                            break;
                        }
                        else {
                            CreateNewItem.getQuestItem(pc, npc, 20037, 1L);
                            pc.getQuest().set_step(DarkElfLv50_1.QUEST.get_id(), 4);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "kimaq4"));
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
