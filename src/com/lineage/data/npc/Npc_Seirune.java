package com.lineage.data.npc;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Seirune extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Seirune();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getLevel() >= 55) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune2"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune1"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("A")) {
            if (pc.getInventory().checkItem(49475, 1L) && pc.getInventory().checkItem(49476, 1L)) {
                pc.getInventory().consumeItem(49475, 1L);
                pc.getInventory().consumeItem(49476, 1L);
                L1ItemInstance item = pc.getInventory().storeItem(49866, 1L);
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getName()));
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune6"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune5"));
            }
        }
        else if (cmd.equalsIgnoreCase("B")) {
            if (pc.getInventory().checkItem(49475, 1L) && pc.getInventory().checkItem(49476, 1L)) {
                pc.getInventory().consumeItem(49475, 1L);
                pc.getInventory().consumeItem(49476, 1L);
                L1ItemInstance item = pc.getInventory().storeItem(49867, 1L);
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getName()));
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune6"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune5"));
            }
        }
        else if (cmd.equalsIgnoreCase("C")) {
            if (pc.getInventory().checkItem(49475, 1L) && pc.getInventory().checkItem(49476, 1L)) {
                pc.getInventory().consumeItem(49475, 1L);
                pc.getInventory().consumeItem(49476, 1L);
                L1ItemInstance item = pc.getInventory().storeItem(49868, 1L);
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getName()));
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune6"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune5"));
            }
        }
        else if (cmd.equalsIgnoreCase("D")) {
            if (pc.getInventory().checkItem(49475, 1L) && pc.getInventory().checkItem(49476, 1L)) {
                pc.getInventory().consumeItem(49475, 1L);
                pc.getInventory().consumeItem(49476, 1L);
                L1ItemInstance item = pc.getInventory().storeItem(49869, 1L);
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getName()));
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune6"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune5"));
            }
        }
        else if (cmd.equalsIgnoreCase("E")) {
            if (pc.getInventory().checkItem(49475, 1L) && pc.getInventory().checkItem(49476, 1L)) {
                pc.getInventory().consumeItem(49475, 1L);
                pc.getInventory().consumeItem(49476, 1L);
                L1ItemInstance item = pc.getInventory().storeItem(49870, 1L);
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getItem().getName()));
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune6"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "seirune5"));
            }
        }
    }
}
