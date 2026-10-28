package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.KnightLv45_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Giant extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Giant.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Giant();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk4"));
            }
            else if (pc.isKnight()) {
                if (pc.getQuest().isEnd(KnightLv45_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk3"));
                    return;
                }
                else if (pc.getLevel() >= KnightLv45_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(KnightLv45_1.QUEST.get_id())) {
                        case 0:
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk4"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk1"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk2"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk3"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk4"));
                }
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk4"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk4"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk4"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk4"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk4"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk4"));
            }
        }
        catch (Exception e) {
            Npc_Giant._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isKnight()) {
            if (pc.getQuest().isEnd(KnightLv45_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("quest 23 giantk2")) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk2"));
                pc.getQuest().set_step(KnightLv45_1.QUEST.get_id(), 3);
            }
            else if (cmd.equalsIgnoreCase("request head part of ancient key")) {
                if (CreateNewItem.checkNewItem(pc, 40537, 1) < 1L) {
                    isCloseList = true;
                }
                else {
                    CreateNewItem.createNewItem(pc, 40537, 1, 40534, 1);
                    pc.getQuest().set_step(KnightLv45_1.QUEST.get_id(), 4);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giantk3"));
                }
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
