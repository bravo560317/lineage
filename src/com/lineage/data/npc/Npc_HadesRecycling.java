package com.lineage.data.npc;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_HadesRecycling extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_HadesRecycling();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun01"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        int oldcape = 0;
        int newitem = 0;
        boolean success = false;
        if (cmd.equalsIgnoreCase("A")) {
            oldcape = 301176;
            newitem = 82209;
            if (pc.getInventory().checkItemNotEquipped(oldcape, 1L)) {
                pc.getInventory().consumeItem(oldcape, 1L);
                CreateNewItem.createNewItem(pc, newitem, 1L);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun02"));
            }
        }
        else if (cmd.equalsIgnoreCase("B")) {
            oldcape = 301186;
            newitem = 82209;
            if (pc.getInventory().checkItemNotEquipped(oldcape, 1L)) {
                pc.getInventory().consumeItem(oldcape, 1L);
                CreateNewItem.createNewItem(pc, newitem, 1L);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun02"));
            }
        }
        else if (cmd.equalsIgnoreCase("C")) {
            oldcape = 301196;
            newitem = 82209;
            if (pc.getInventory().checkItemNotEquipped(oldcape, 1L)) {
                pc.getInventory().consumeItem(oldcape, 1L);
                CreateNewItem.createNewItem(pc, newitem, 1L);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun02"));
            }
        }
        else if (cmd.equalsIgnoreCase("D")) {
            oldcape = 301206;
            newitem = 82209;
            if (pc.getInventory().checkItemNotEquipped(oldcape, 1L)) {
                pc.getInventory().consumeItem(oldcape, 1L);
                CreateNewItem.createNewItem(pc, newitem, 1L);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun02"));
            }
        }
        else if (cmd.equalsIgnoreCase("E")) {
            oldcape = 301179;
            newitem = 82210;
            if (pc.getInventory().checkItemNotEquipped(oldcape, 1L)) {
                pc.getInventory().consumeItem(oldcape, 1L);
                CreateNewItem.createNewItem(pc, newitem, 1L);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun02"));
            }
        }
        else if (cmd.equalsIgnoreCase("F")) {
            oldcape = 301189;
            newitem = 82210;
            if (pc.getInventory().checkItemNotEquipped(oldcape, 1L)) {
                pc.getInventory().consumeItem(oldcape, 1L);
                CreateNewItem.createNewItem(pc, newitem, 1L);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun02"));
            }
        }
        else if (cmd.equalsIgnoreCase("G")) {
            oldcape = 301199;
            newitem = 82210;
            if (pc.getInventory().checkItemNotEquipped(oldcape, 1L)) {
                pc.getInventory().consumeItem(oldcape, 1L);
                CreateNewItem.createNewItem(pc, newitem, 1L);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun02"));
            }
        }
        else if (cmd.equalsIgnoreCase("H")) {
            oldcape = 301209;
            newitem = 82210;
            if (pc.getInventory().checkItemNotEquipped(oldcape, 1L)) {
                pc.getInventory().consumeItem(oldcape, 1L);
                CreateNewItem.createNewItem(pc, newitem, 1L);
                success = true;
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun02"));
            }
        }
        if (!success) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_atun03"));
        }
    }
}
