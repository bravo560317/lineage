package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.quest.DarkElfLv45_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Assassin extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Assassin.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Assassin();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            boolean isTak = false;
            if (pc.getTempCharGfx() == 3634) {
                isTak = true;
            }
            if (!isTak) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin4"));
                return;
            }
            else if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
            }
            else if (pc.isDarkelf()) {
                if (pc.getQuest().isEnd(DarkElfLv45_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
                    return;
                }
                else if (pc.getLevel() >= DarkElfLv45_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(DarkElfLv45_1.QUEST.get_id())) {
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin1"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
                }
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin3"));
            }
        }
        catch (Exception e) {
            Npc_Assassin._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDarkelf()) {
            if (pc.getQuest().isEnd(DarkElfLv45_1.QUEST.get_id())) {
                return;
            }
            else {
                switch (pc.getQuest().get_step(DarkElfLv45_1.QUEST.get_id())) {
                    case 1: {
                        if (!cmd.equalsIgnoreCase("quest 18 assassin2")) {
                            break;
                        }
                        else {
                            pc.getQuest().set_step(DarkElfLv45_1.QUEST.get_id(), 2);
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "assassin2"));
                            break;
                        }
                    }
                    default: {
                        isCloseList = true;
                        break;
                    }
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
