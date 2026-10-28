package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.ALv45_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Jeron extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Jeron.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Jeron();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getQuest().isEnd(ALv45_1.QUEST.get_id())) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron10"));
            }
            else if (pc.getLevel() >= ALv45_1.QUEST.get_questlevel()) {
                switch (pc.getQuest().get_step(ALv45_1.QUEST.get_id())) {
                    case 0:
                    case 1: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron10"));
                        break;
                    }
                    case 2: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron1"));
                        break;
                    }
                    case 3: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron7"));
                        break;
                    }
                    default: {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron10"));
                        break;
                    }
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron10"));
            }
        }
        catch (Exception e) {
            Npc_Jeron._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        int[] items = (int[])null;
        int[] counts = (int[])null;
        int[] gitems = (int[])null;
        int[] gcounts = (int[])null;
        if (cmd.equalsIgnoreCase("A")) {
            items = new int[] { 41340 };
            counts = new int[] { 1 };
            if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron10"));
                return;
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron2"));
            }
        }
        else if (cmd.equalsIgnoreCase("B")) {
            items = new int[] { 40308 };
            counts = new int[] { 1000000 };
            if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron8"));
                return;
            }
            else {
                gitems = new int[] { 41341 };
                gcounts = new int[] { 1 };
                L1ItemInstance item = pc.getInventory().checkItemX(41340, 1L);
                if (item != null) {
                    pc.getInventory().removeItem(item, 1L);
                }
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                pc.getQuest().set_step(ALv45_1.QUEST.get_id(), 3);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron6"));
            }
        }
        else if (cmd.equalsIgnoreCase("C")) {
            items = new int[] { 41342 };
            counts = new int[] { 1 };
            if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron9"));
                return;
            }
            else {
                gitems = new int[] { 41341 };
                gcounts = new int[] { 1 };
                L1ItemInstance item = pc.getInventory().checkItemX(41340, 1L);
                if (item != null) {
                    pc.getInventory().removeItem(item, 1L);
                }
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                pc.getQuest().set_step(ALv45_1.QUEST.get_id(), 3);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jeron5"));
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
