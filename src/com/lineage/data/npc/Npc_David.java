package com.lineage.data.npc;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_David extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_David();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getInventory().checkItem(49031, 1L)) {
            if (pc.getInventory().checkItem(21081, 1L)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout1"));
            }
            else if (pc.getInventory().checkItem(21082, 1L)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout2"));
            }
            else if (pc.getInventory().checkItem(21083, 1L)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout3"));
            }
            else if (pc.getInventory().checkItem(21084, 1L)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout4"));
            }
            else if (pc.getInventory().checkItem(21085, 1L)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout5"));
            }
            else if (pc.getInventory().checkItem(21086, 1L)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout6"));
            }
            else if (pc.getInventory().checkItem(21087, 1L)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout7"));
            }
            else if (pc.getInventory().checkItem(21088, 1L)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout8"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout17"));
            }
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gemout0"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        String npcName = npc.getNpcTemplate().get_name();
        if (cmd.equalsIgnoreCase("b")) {
            if (pc.getInventory().consumeItem(21081, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
                CreateNewItem.createNewItem_NPC(pc, npcName, 21082, 1);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("c")) {
            if (pc.getInventory().consumeItem(21082, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
                CreateNewItem.createNewItem_NPC(pc, npcName, 21083, 1);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("d")) {
            if (pc.getInventory().consumeItem(21083, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
                CreateNewItem.createNewItem_NPC(pc, npcName, 21084, 1);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("e")) {
            if (pc.getInventory().consumeItem(21084, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
                CreateNewItem.createNewItem_NPC(pc, npcName, 21085, 1);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("f")) {
            if (pc.getInventory().consumeItem(21085, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
                CreateNewItem.createNewItem_NPC(pc, npcName, 21086, 1);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("g")) {
            if (pc.getInventory().consumeItem(21086, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
                CreateNewItem.createNewItem_NPC(pc, npcName, 21087, 1);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("h")) {
            if (pc.getInventory().consumeItem(21087, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
                CreateNewItem.createNewItem_NPC(pc, npcName, 21088, 1);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("i")) {
            if (pc.getInventory().consumeItem(21088, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
                CreateNewItem.createNewItem_NPC(pc, npcName, 21089, 1);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("j")) {
            if (pc.getInventory().consumeItem(21088, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
                CreateNewItem.createNewItem_NPC(pc, npcName, 21090, 1);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("k") && pc.getInventory().consumeItem(21088, 1L) && pc.getInventory().consumeItem(49031, 1L)) {
            CreateNewItem.createNewItem_NPC(pc, npcName, 21091, 1);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
