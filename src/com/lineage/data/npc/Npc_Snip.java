package com.lineage.data.npc;

import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Snip extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Snip();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getQuest().get_step(58001) == 1 && pc.getQuest().get_step(58002) == 1 && pc.getQuest().get_step(58003) == 1) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "slot5"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "slot7"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("A")) {
            if (pc.getQuest().get_step(58003) == 1) {
                pc.sendPackets(new S_ServerMessage(3254));
                return;
            }
            else if (pc.getLevel() < 76) {
                pc.sendPackets(new S_ServerMessage("您的等級條件不足。"));
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot11"));
                return;
            }
            else if (!pc.getInventory().checkItem(ConfigOther.checkitem76, (long)ConfigOther.checkitemcount76)) {
                pc.sendPackets(new S_ServerMessage("您的物品條件不足。"));
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot11"));
                return;
            }
            else if (pc.getQuest().get_step(58003) == 1) {
                pc.sendPackets(new S_ServerMessage(3254));
            }
            else {
                pc.setSlot(76);
                pc.sendPackets(new S_Message_YN(3312));
            }
        }
        else if (cmd.equalsIgnoreCase("B")) {
            if (pc.getQuest().get_step(58002) == 1) {
                pc.sendPackets(new S_ServerMessage(3254));
                return;
            }
            else if (pc.getLevel() < 81) {
                pc.sendPackets(new S_ServerMessage("您的條件不足。"));
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot11"));
                return;
            }
            else if (!pc.getInventory().checkItem(ConfigOther.checkitem81, (long)ConfigOther.checkitemcount81)) {
                pc.sendPackets(new S_ServerMessage("您的條件不足。"));
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot11"));
                return;
            }
            else if (pc.getQuest().get_step(58002) == 1) {
                pc.sendPackets(new S_ServerMessage(3254));
            }
            else {
                pc.setSlot(81);
                pc.sendPackets(new S_Message_YN(3313));
            }
        }
        else if (cmd.equalsIgnoreCase("D")) {
            if (pc.getQuest().get_step(58001) == 1) {
                pc.sendPackets(new S_ServerMessage(3254));
                return;
            }
            else if (pc.getLevel() < 59) {
                pc.sendPackets(new S_ServerMessage(3253));
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot11"));
                return;
            }
            else if (!pc.getInventory().checkItem(ConfigOther.checkitem59, (long)ConfigOther.checkitemcount59)) {
                pc.sendPackets(new S_ServerMessage("您的條件不足。"));
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot11"));
                return;
            }
            else if (pc.getQuest().get_step(58001) == 1) {
                pc.sendPackets(new S_ServerMessage(3254));
            }
            else {
                pc.setSlot(59);
                pc.sendPackets(new S_Message_YN(3589));
            }
        }
    }
}
