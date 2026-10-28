package com.lineage.data.npc.shop;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.WizardLv30_1;
import com.lineage.data.quest.WizardLv15_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class NPC_Gereng extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(NPC_Gereng.class);
        NPC_Gereng._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new NPC_Gereng();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengp1"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengk1"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerenge1"));
            }
            else if (pc.isWizard()) {
                if (!pc.getQuest().isEnd(WizardLv15_1.QUEST.get_id())) {
                    int htmlid = NPC_Gereng._random.nextInt(6) + 1;
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengTe" + htmlid));
                    return;
                }
                else if (pc.getQuest().isEnd(WizardLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengw5"));
                    return;
                }
                else if (pc.getLevel() >= WizardLv30_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(WizardLv30_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengT1"));
                            break;
                        }
                        case 1: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengT2"));
                            break;
                        }
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengT3"));
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengT4"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengw5"));
                            break;
                        }
                    }
                }
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengde1"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengdk1"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengi1"));
            }
        }
        catch (Exception e) {
            NPC_Gereng._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isWizard()) {
            if (!pc.getQuest().isEnd(WizardLv15_1.QUEST.get_id())) {
                return;
            }
            else if (pc.getQuest().isEnd(WizardLv30_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("quest 12 gerengT2")) {
                QuestClass.get().startQuest(pc, WizardLv30_1.QUEST.get_id());
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengT2"));
            }
            else if (cmd.equalsIgnoreCase("request bone piece of undead")) {
                L1ItemInstance tgitem = pc.getInventory().checkItemX(40579, 1L);
                if (tgitem != null) {
                    if (pc.getInventory().removeItem(tgitem, 1L) == 1L) {
                        pc.getQuest().set_step(WizardLv30_1.QUEST.get_id(), 2);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengT3"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(337, "$2033 (1)"));
                    isCloseList = true;
                }
            }
            else if (cmd.equalsIgnoreCase("quest 14 gerengT4")) {
                pc.getQuest().set_step(WizardLv30_1.QUEST.get_id(), 3);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "gerengT4"));
            }
            else if (cmd.equalsIgnoreCase("request mystery staff")) {
                if (CreateNewItem.checkNewItem(pc, 40567, 1) < 1L) {
                    isCloseList = true;
                }
                else {
                    CreateNewItem.createNewItem(pc, new int[] { 40567 }, new int[] { 1 }, new int[] { 40580, 40569 }, 1L, new int[] { 1, 1 });
                    pc.getQuest().set_step(WizardLv30_1.QUEST.get_id(), 4);
                    isCloseList = true;
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
