package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.ElfLv45_1;
import com.lineage.data.quest.ElfLv30_1;
import com.lineage.data.quest.KnightLv45_1;
import com.lineage.data.quest.KnightLv30_1;
import com.lineage.data.quest.CrownLv45_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.CrownLv30_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Masha extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Masha.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Masha();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                if (!pc.getQuest().isEnd(CrownLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
                    return;
                }
                else if (pc.getQuest().isEnd(CrownLv45_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha4"));
                    return;
                }
                else if (pc.getLevel() >= CrownLv45_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(CrownLv45_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha1"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha3"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
                }
            }
            else if (pc.isKnight()) {
                if (!pc.getQuest().isEnd(KnightLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
                    return;
                }
                else if (pc.getQuest().isEnd(KnightLv45_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashak3"));
                    return;
                }
                else if (pc.getLevel() >= KnightLv45_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(KnightLv45_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashak1"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashak2"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
                }
            }
            else if (pc.isElf()) {
                if (!pc.getQuest().isEnd(ElfLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
                    return;
                }
                else if (pc.getQuest().isEnd(ElfLv45_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashae3"));
                    return;
                }
                else if (pc.getLevel() >= ElfLv45_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(ElfLv45_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashae1"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashae2"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
                }
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha"));
            }
        }
        catch (Exception e) {
            Npc_Masha._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isCrown()) {
            if (pc.getQuest().isEnd(CrownLv45_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("quest 15 masha2")) {
                QuestClass.get().startQuest(pc, CrownLv45_1.QUEST.get_id());
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha2"));
            }
            else if (cmd.equalsIgnoreCase("request ring of guardian")) {
                if (CreateNewItem.checkNewItem(pc, new int[] { 40586, 40587 }, new int[] { 1, 1 }) < 1L) {
                    isCloseList = true;
                }
                else {
                    CreateNewItem.createNewItem(pc, new int[] { 40586, 40587 }, new int[] { 1, 1 }, new int[] { 20287 }, 1L, new int[] { 1 });
                    QuestClass.get().endQuest(pc, CrownLv45_1.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "masha4"));
                }
            }
        }
        else if (pc.isKnight()) {
            if (pc.getQuest().isEnd(KnightLv45_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("quest 20 mashak2")) {
                QuestClass.get().startQuest(pc, KnightLv45_1.QUEST.get_id());
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashak2"));
            }
            else if (cmd.equalsIgnoreCase("request belt of bravery")) {
                if (CreateNewItem.checkNewItem(pc, new int[] { 40597, 40593 }, new int[] { 1, 1 }) < 1L) {
                    isCloseList = true;
                }
                else {
                    CreateNewItem.createNewItem(pc, new int[] { 40597, 40593 }, new int[] { 1, 1 }, new int[] { 20318 }, 1L, new int[] { 1 });
                    L1ItemInstance item = pc.getInventory().findItemId(20026);
                    if (item != null) {
                        if (item.isEquipped()) {
                            pc.getInventory().setEquipped(item, false);
                        }
                        pc.getInventory().removeItem(item, 1L);
                    }
                    QuestClass.get().endQuest(pc, KnightLv45_1.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashak3"));
                }
            }
        }
        else if (pc.isElf()) {
            if (pc.getQuest().isEnd(ElfLv45_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("quest 14 mashae2")) {
                QuestClass.get().startQuest(pc, ElfLv45_1.QUEST.get_id());
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashae2"));
            }
            else if (cmd.equalsIgnoreCase("request bag of masha")) {
                if (CreateNewItem.checkNewItem(pc, new int[] { 40533, 192 }, new int[] { 1, 1 }) < 1L) {
                    isCloseList = true;
                }
                else {
                    CreateNewItem.createNewItem(pc, new int[] { 40533, 192 }, new int[] { 1, 1 }, new int[] { 40546 }, 1L, new int[] { 1 });
                    L1ItemInstance item = pc.getInventory().findItemId(40566);
                    if (item != null) {
                        pc.getInventory().removeItem(item, 1L);
                    }
                    QuestClass.get().endQuest(pc, ElfLv45_1.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "mashae3"));
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
