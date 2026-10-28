package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.DragonKnightLv30_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Elas extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Elas.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Elas();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas2"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas2"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas2"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas2"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas2"));
            }
            else if (pc.isDragonKnight()) {
                if (pc.getQuest().isStart(DragonKnightLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas6"));
                }
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas3"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas3"));
            }
        }
        catch (Exception e) {
            Npc_Elas._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDragonKnight()) {
            if (pc.getQuest().isStart(DragonKnightLv30_1.QUEST.get_id())) {
                if (cmd.equalsIgnoreCase("a")) {
                    if (pc.getInventory().checkItem(49220)) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas5"));
                    }
                    else {
                        CreateNewItem.getQuestItem(pc, npc, 49220, 1L);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "elas4"));
                    }
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
