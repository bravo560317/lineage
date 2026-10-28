package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.quest.WizardLv50_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Dspym extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Dspym.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Dspym();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            npc.onTalkAction(pc);
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym5"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym5"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym5"));
            }
            else if (pc.isWizard()) {
                if (pc.getQuest().isEnd(WizardLv50_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym4"));
                    return;
                }
                else if (pc.getLevel() >= WizardLv50_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(WizardLv50_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym5"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym1"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym3"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym5"));
                }
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym5"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym5"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym5"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym5"));
            }
        }
        catch (Exception e) {
            Npc_Dspym._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (npc.getMaster() != null) {
            return;
        }
        else {
            if (pc.isWizard()) {
                if (pc.getQuest().isEnd(WizardLv50_1.QUEST.get_id())) {
                    return;
                }
                else if (cmd.equalsIgnoreCase("quest 27 dspym2")) {
                    pc.getQuest().set_step(WizardLv50_1.QUEST.get_id(), 2);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dspym2"));
                }
            }
            else {
                isCloseList = true;
            }
            if (isCloseList) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
            return;
        }
    }
}
