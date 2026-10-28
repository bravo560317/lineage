package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.data.quest.Chapter00B;
import com.lineage.server.model.L1Teleport;
import com.lineage.data.quest.Chapter00A;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_YouLiya extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_YouLiya.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_YouLiya();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getInventory().checkItem(49312)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html00"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html01"));
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html05"));
            Npc_YouLiya._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            if (cmd.equalsIgnoreCase("a")) {
                int[] items = { 40308, 56214 };
                int[] counts = { 10000, 1 };
                if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html02"));
                }
                else {
                    L1ItemInstance item1 = pc.getInventory().checkItemX(items[0], (long)counts[0]);
                    boolean error = false;
                    if (item1 != null) {
                        pc.getInventory().removeItem(item1, (long)counts[0]);
                    }
                    else {
                        error = true;
                    }
                    L1ItemInstance item2 = pc.getInventory().checkItemX(items[1], (long)counts[1]);
                    if (item2 != null && !error) {
                        long remove = (long)counts[1];
                        if (item2.getCount() >= 2L) {
                            remove = item2.getCount() - 1L;
                        }
                        pc.getInventory().removeItem(item2, remove);
                    }
                    else {
                        error = true;
                    }
                    if (!error) {
                        L1PolyMorph.undoPoly(pc);
                        pc.set_showId(Chapter00A.QUEST.get_id());
                        L1Teleport.teleport(pc, 32747, 32861, (short)9100, 5, true);
                    }
                }
            }
            else if (cmd.equalsIgnoreCase("b")) {
                int[] items = { 40308, 56215 };
                int[] counts = { 10000, 1 };
                if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html02"));
                }
                else {
                    L1ItemInstance item1 = pc.getInventory().checkItemX(items[0], (long)counts[0]);
                    boolean error = false;
                    if (item1 != null) {
                        pc.getInventory().removeItem(item1, (long)counts[0]);
                    }
                    else {
                        error = true;
                    }
                    L1ItemInstance item2 = pc.getInventory().checkItemX(items[1], (long)counts[1]);
                    if (item2 != null && !error) {
                        long remove = (long)counts[1];
                        if (item2.getCount() >= 2L) {
                            remove = item2.getCount() - 1L;
                        }
                        pc.getInventory().removeItem(item2, remove);
                    }
                    else {
                        error = true;
                    }
                    if (!error) {
                        L1PolyMorph.undoPoly(pc);
                        pc.set_showId(Chapter00B.QUEST.get_id());
                        L1Teleport.teleport(pc, 32747, 32861, (short)9202, 5, true);
                    }
                }
            }
            else if (cmd.equalsIgnoreCase("c")) {
                if (!pc.getInventory().checkItem(49312)) {
                    CreateNewItem.createNewItem(pc, 49312, 1L);
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html03"));
                }
            }
            else if (cmd.equalsIgnoreCase("d")) {
                int[] items = { 49301, 49302, 49303, 49304, 49305, 49306, 49307, 49308, 49309, 49310 };
                int[] counts = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
                int[] gitems = { 49311 };
                int[] gcounts = { 1 };
                if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html06"));
                }
                else {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html04"));
                }
            }
            else if (cmd.equalsIgnoreCase("e")) {
                int[] items = { 56216, 56217, 56218, 56219, 56220, 56221, 56222, 56223, 56224, 56225, 56226, 56227, 56228, 56229, 56230, 56231, 56232, 56233 };
                int[] counts = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
                int[] gitems = { 56234 };
                int[] gcounts = { 1 };
                if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html06"));
                }
                else {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html04"));
                }
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "j_html05"));
            Npc_YouLiya._log.error(e.getLocalizedMessage(), e);
        }
    }
}
