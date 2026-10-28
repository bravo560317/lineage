package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.ElfLv45_2;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Zybril extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Zybril.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Zybril();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril16"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril16"));
            }
            else if (pc.isElf()) {
                if (pc.getQuest().isEnd(ElfLv45_2.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril19"));
                    return;
                }
                else if (pc.getLevel() >= ElfLv45_2.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(ElfLv45_2.QUEST.get_id())) {
                        case 0:
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril15"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril1"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril7"));
                            break;
                        }
                        case 4: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril8"));
                            break;
                        }
                        case 5: {
                            if (pc.getInventory().checkItem(41349, 1L)) {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril18"));
                                break;
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril17"));
                                break;
                            }
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril19"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril16"));
                }
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril16"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril16"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril16"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril16"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril16"));
            }
        }
        catch (Exception e) {
            Npc_Zybril._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isElf()) {
            if (pc.getQuest().isEnd(ElfLv45_2.QUEST.get_id())) {
                return;
            }
            else {
                switch (pc.getQuest().get_step(ElfLv45_2.QUEST.get_id())) {
                    case 0:
                    case 1: {
                        break;
                    }
                    case 2: {
                        if (!cmd.equals("A")) {
                            break;
                        }
                        else {
                            L1ItemInstance item = pc.getInventory().findItemId(41348);
                            if (item != null) {
                                pc.getInventory().removeItem(item, 1L);
                                pc.getQuest().set_step(ElfLv45_2.QUEST.get_id(), 3);
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril3"));
                                break;
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril11"));
                                break;
                            }
                        }
                    }
                    case 3: {
                        if (!cmd.equals("B")) {
                            break;
                        }
                        else if (CreateNewItem.checkNewItem(pc, new int[] { 40048, 40049, 40050, 40051 }, new int[] { 10, 10, 10, 10 }) < 1L) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril12"));
                            break;
                        }
                        else {
                            CreateNewItem.createNewItem(pc, new int[] { 40048, 40049, 40050, 40051 }, new int[] { 10, 10, 10, 10 }, new int[] { 41353 }, 1L, new int[] { 1 });
                            pc.getQuest().set_step(ElfLv45_2.QUEST.get_id(), 4);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril8"));
                            break;
                        }
                    }
                    case 4: {
                        if (!cmd.equals("C")) {
                            break;
                        }
                        else if (CreateNewItem.checkNewItem(pc, new int[] { 40514, 41353 }, new int[] { 10, 1 }) < 1L) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril13"));
                            break;
                        }
                        else {
                            CreateNewItem.createNewItem(pc, new int[] { 40514, 41353 }, new int[] { 10, 1 }, new int[] { 41354 }, 1L, new int[] { 1 });
                            pc.getQuest().set_step(ElfLv45_2.QUEST.get_id(), 5);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril9"));
                            break;
                        }
                    }
                    case 5: {
                        if (!cmd.equals("D")) {
                            break;
                        }
                        else if (CreateNewItem.checkNewItem(pc, new int[] { 41349 }, new int[] { 1 }) < 1L) {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril14"));
                            break;
                        }
                        else {
                            CreateNewItem.createNewItem(pc, new int[] { 41349 }, new int[] { 1 }, new int[] { 41351 }, 1L, new int[] { 1 });
                            pc.getQuest().set_step(ElfLv45_2.QUEST.get_id(), 6);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "zybril10"));
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
