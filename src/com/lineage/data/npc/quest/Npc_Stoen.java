package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.WizardLv45_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Stoen extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Stoen.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Stoen();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm4"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm4"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm4"));
            }
            else if (pc.isWizard()) {
                if (pc.getQuest().isEnd(WizardLv45_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm3"));
                    return;
                }
                else if (pc.getLevel() >= WizardLv45_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(WizardLv45_1.QUEST.get_id())) {
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm1"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm2"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm3"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm4"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm4"));
                }
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm4"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm4"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm4"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm4"));
            }
        }
        catch (Exception e) {
            Npc_Stoen._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isWizard()) {
            if (pc.getQuest().isEnd(WizardLv45_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("quest 19 stoenm2")) {
                pc.getQuest().set_step(WizardLv45_1.QUEST.get_id(), 2);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm2"));
            }
            else if (cmd.equalsIgnoreCase("request scroll about ancient evil")) {
                int[] items = { 40542, 40189 };
                int[] counts = { 1, 1 };
                int[] gitems = { 40536 };
                int[] gcounts = { 1 };
                if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                    isCloseList = true;
                }
                else {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                    pc.getQuest().set_step(WizardLv45_1.QUEST.get_id(), 3);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "stoenm3"));
                }
            }
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
