package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.data.QuestClass;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.ALv40_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Lavienue extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Lavienue.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Lavienue();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getQuest().isEnd(ALv40_1.QUEST.get_id())) {
                return;
            }
            else if (pc.getLevel() >= 40) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "lavienue9"));
            }
        }
        catch (Exception e) {
            Npc_Lavienue._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            boolean isCloseList = false;
            if (cmd.equalsIgnoreCase("request tear of dark")) {
                int[] items = { 40324, 40524, 40443 };
                int[] counts = { 1, 3, 1 };
                int[] gitems = { 40525 };
                int[] gcounts = { 1 };
                long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                if (xcount == 1L) {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                    QuestClass.get().startQuest(pc, ALv40_1.QUEST.get_id());
                    QuestClass.get().endQuest(pc, ALv40_1.QUEST.get_id());
                    isCloseList = true;
                }
                else if (xcount > 1L) {
                    pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "a1"));
                }
                else if (xcount < 1L) {
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("a1")) {
                int[] items = { 40324, 40524, 40443 };
                int[] counts = { 1, 3, 1 };
                int[] gitems = { 40525 };
                int[] gcounts = { 1 };
                long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                if (xcount >= amount) {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
                    QuestClass.get().startQuest(pc, ALv40_1.QUEST.get_id());
                    QuestClass.get().endQuest(pc, ALv40_1.QUEST.get_id());
                }
                isCloseList = true;
            }
            else if (cmd.equalsIgnoreCase("request spellbook62")) {
                int[] items = { 40162, 40413, 40409, 40169 };
                int[] counts = { 1, 1, 1, 1 };
                int[] gitems = { 40208 };
                int[] gcounts = { 1 };
                long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                if (xcount >= 1L) {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                    QuestClass.get().startQuest(pc, ALv40_1.QUEST.get_id());
                    QuestClass.get().endQuest(pc, ALv40_1.QUEST.get_id());
                }
                isCloseList = true;
            }
            else if (cmd.equalsIgnoreCase("request spellbook113")) {
                int[] items = { 40162, 40413, 40409, 40169 };
                int[] counts = { 1, 1, 1, 1 };
                int[] gitems = { 40227 };
                int[] gcounts = { 1 };
                long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                if (xcount >= 1L) {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                    QuestClass.get().startQuest(pc, ALv40_1.QUEST.get_id());
                    QuestClass.get().endQuest(pc, ALv40_1.QUEST.get_id());
                }
                isCloseList = true;
            }
            else if (cmd.equalsIgnoreCase("request spellbook116")) {
                int[] items = { 40162, 40413, 40409, 40169 };
                int[] counts = { 2, 2, 2, 2 };
                int[] gitems = { 40230 };
                int[] gcounts = { 1 };
                long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                if (xcount >= 1L) {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                    QuestClass.get().startQuest(pc, ALv40_1.QUEST.get_id());
                    QuestClass.get().endQuest(pc, ALv40_1.QUEST.get_id());
                }
                isCloseList = true;
            }
            else if (cmd.equalsIgnoreCase("request spellbook114")) {
                int[] items = { 40162, 40413, 40409, 40169 };
                int[] counts = { 4, 4, 4, 4 };
                int[] gitems = { 40229 };
                int[] gcounts = { 1 };
                long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                if (xcount >= 1L) {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                    QuestClass.get().startQuest(pc, ALv40_1.QUEST.get_id());
                    QuestClass.get().endQuest(pc, ALv40_1.QUEST.get_id());
                }
                isCloseList = true;
            }
            else if (cmd.equalsIgnoreCase("request spellbook117")) {
                int[] items = { 40162, 40413, 40409, 40169 };
                int[] counts = { 3, 3, 3, 3 };
                int[] gitems = { 40231 };
                int[] gcounts = { 1 };
                long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                if (xcount >= 1L) {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                    QuestClass.get().startQuest(pc, ALv40_1.QUEST.get_id());
                    QuestClass.get().endQuest(pc, ALv40_1.QUEST.get_id());
                }
                isCloseList = true;
            }
            if (isCloseList) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        catch (Exception e) {
            Npc_Lavienue._log.error(e.getLocalizedMessage(), e);
        }
    }
}
