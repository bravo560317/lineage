package com.lineage.data.npc.teleport;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Maetnob extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Maetnob();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equals("J")) {
            pc.getInventory().consumeItem(40308, 300L);
            L1Teleport.teleport(pc, 33767, 32864, (short)106, 5, true);
        }
        if (cmd.equals("A")) {
            L1ItemInstance item = pc.getInventory().checkItemX(40104, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, 32744, 32864, (short)116, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob2"));
            }
        }
        else if (cmd.equals("B")) {
            L1ItemInstance item = pc.getInventory().checkItemX(40105, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, 32744, 32864, (short)126, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob2"));
            }
        }
        else if (cmd.equals("C")) {
            L1ItemInstance item = pc.getInventory().checkItemX(40106, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, 32744, 32864, (short)136, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob2"));
            }
        }
        else if (cmd.equals("D")) {
            L1ItemInstance item = pc.getInventory().checkItemX(40107, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, 32744, 32864, (short)146, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob2"));
            }
        }
        else if (cmd.equals("E")) {
            L1ItemInstance item = pc.getInventory().checkItemX(40108, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, 32737, 32801, (short)156, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob2"));
            }
        }
        else if (cmd.equals("F")) {
            L1ItemInstance item = pc.getInventory().checkItemX(40109, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, 32737, 32801, (short)166, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob2"));
            }
        }
        else if (cmd.equals("G")) {
            L1ItemInstance item = pc.getInventory().checkItemX(40110, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, 32737, 32801, (short)176, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob2"));
            }
        }
        else if (cmd.equals("H")) {
            L1ItemInstance item = pc.getInventory().checkItemX(40111, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, 32737, 32801, (short)186, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob2"));
            }
        }
        else if (cmd.equals("I")) {
            L1ItemInstance item = pc.getInventory().checkItemX(40112, 2L);
            L1ItemInstance adena = pc.getInventory().checkItemX(40308, 300L);
            if (item != null && adena != null) {
                pc.getInventory().removeItem(item, 2L);
                pc.getInventory().removeItem(adena, 300L);
                L1Teleport.teleport(pc, 32737, 32801, (short)196, 5, true);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "maetnob2"));
            }
        }
    }
}
