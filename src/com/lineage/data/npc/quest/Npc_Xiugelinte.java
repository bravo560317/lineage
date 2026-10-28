package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Xiugelinte extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Xiugelinte.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Xiugelinte();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getInventory().checkItem(56235)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hugrint5"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hugrint1"));
            }
        }
        catch (Exception e) {
            Npc_Xiugelinte._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            if (cmd.equalsIgnoreCase("0")) {
                if (pc.getInventory().checkItem(49335) || pc.getInventory().checkItem(49336)) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hugrint4"));
                    return;
                }
                else {
                    L1ItemInstance item = pc.getInventory().checkItemX(40308, 1000L);
                    if (item != null) {
                        pc.getInventory().removeItem(item, 1000L);
                        CreateNewItem.createNewItem(pc, 49335, 1L);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hugrint2"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hugrint3"));
                    }
                }
            }
            else if (cmd.equalsIgnoreCase("1")) {
                if (pc.getInventory().checkItem(49335) || pc.getInventory().checkItem(49336)) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hugrint4"));
                    return;
                }
                else {
                    L1ItemInstance item = pc.getInventory().checkItemX(56235, 5L);
                    if (item != null) {
                        pc.getInventory().removeItem(item, 5L);
                        CreateNewItem.createNewItem(pc, 49336, 1L);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hugrint2"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hugrint6"));
                    }
                }
            }
        }
        catch (Exception e) {
            Npc_Xiugelinte._log.error(e.getLocalizedMessage(), e);
        }
    }
}
