package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.IllusionistLv45_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Wa_Egg extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Wa_Egg.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Wa_Egg();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg1"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg1"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg1"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg1"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg1"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg1"));
            }
            else if (pc.isIllusionist()) {
                if (pc.getQuest().isStart(IllusionistLv45_1.QUEST.get_id())) {
                    if (!pc.getInventory().checkItem(49199)) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg2"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg1"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg1"));
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg1"));
            }
        }
        catch (Exception e) {
            Npc_Wa_Egg._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (cmd.equalsIgnoreCase("a")) {
            if (pc.getQuest().isStart(IllusionistLv45_1.QUEST.get_id())) {
                if (!pc.getInventory().checkItem(49199)) {
                    L1ItemInstance item = pc.getInventory().checkItemX(49193, 1L);
                    if (item != null) {
                        pc.getInventory().removeItem(item, 1L);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wa_egg3"));
                        CreateNewItem.createNewItem(pc, 49199, 1L);
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(79));
                    isCloseList = true;
                }
            }
            else {
                isCloseList = true;
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
