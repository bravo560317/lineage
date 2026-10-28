package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.ElfLv45_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_NpcChat;
import java.util.Random;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Heit extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Heit.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Heit();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getLawful() < 0) {
                Random random = new Random();
                if (random.nextInt(100) < 20) {
                    npc.broadcastPacketX8(new S_NpcChat(npc, "$4991"));
                }
                return;
            }
            else if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
            }
            else if (pc.isElf()) {
                if (pc.getQuest().isEnd(ElfLv45_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
                    return;
                }
                else if (pc.getLevel() >= ElfLv45_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(ElfLv45_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit1"));
                            break;
                        }
                        case 2:
                        case 3:
                        case 4: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit2"));
                            break;
                        }
                        case 5: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit3"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit5"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
                }
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit4"));
            }
        }
        catch (Exception e) {
            Npc_Heit._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isElf()) {
            if (pc.getQuest().isEnd(ElfLv45_1.QUEST.get_id())) {
                return;
            }
            else if (!pc.getQuest().isStart(ElfLv45_1.QUEST.get_id())) {
                isCloseList = true;
            }
            else {
                switch (pc.getQuest().get_step(ElfLv45_1.QUEST.get_id())) {
                    case 1: {
                        if (!cmd.equalsIgnoreCase("quest 15 heit2")) {
                            break;
                        }
                        else {
                            pc.getQuest().set_step(ElfLv45_1.QUEST.get_id(), 2);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit2"));
                            break;
                        }
                    }
                    case 2:
                    case 3:
                    case 4: {
                        if (!cmd.equalsIgnoreCase("request mystery shell")) {
                            break;
                        }
                        else {
                            L1ItemInstance item = pc.getInventory().checkItemX(40602, 1L);
                            if (item != null) {
                                pc.getInventory().removeItem(item, 1L);
                                pc.getQuest().set_step(ElfLv45_1.QUEST.get_id(), 5);
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit3"));
                                break;
                            }
                            else {
                                isCloseList = true;
                                break;
                            }
                        }
                    }
                    case 5: {
                        if (!cmd.equalsIgnoreCase("quest 17 heit5")) {
                            break;
                        }
                        else if (pc.getInventory().checkItem(40566)) {
                            isCloseList = true;
                            break;
                        }
                        else {
                            pc.getQuest().set_step(ElfLv45_1.QUEST.get_id(), 6);
                            CreateNewItem.getQuestItem(pc, npc, 40566, 1L);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "heit5"));
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
