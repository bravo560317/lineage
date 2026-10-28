package com.lineage.data.npc.quest;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.CrownLv45_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Pieta extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Pieta.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Pieta();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                if (pc.getQuest().isEnd(CrownLv45_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta9"));
                    return;
                }
                else if (pc.getLevel() >= CrownLv45_1.QUEST.get_questlevel()) {
                    switch (pc.getQuest().get_step(CrownLv45_1.QUEST.get_id())) {
                        case 0: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta8"));
                            break;
                        }
                        case 1:
                        case 2: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta2"));
                            pc.getQuest().set_step(CrownLv45_1.QUEST.get_id(), 3);
                            break;
                        }
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta4"));
                            break;
                        }
                        case 4: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta6"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta9"));
                            break;
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta8"));
                }
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta1"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta1"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta1"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta1"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta1"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta1"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta1"));
            }
        }
        catch (Exception e) {
            Npc_Pieta._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (pc.isCrown()) {
            if (pc.getQuest().isEnd(CrownLv45_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("a")) {
                if (CreateNewItem.checkNewItem(pc, new int[] { 41422 }, new int[] { 1 }) < 1L) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta10"));
                }
                else {
                    CreateNewItem.createNewItem(pc, new int[] { 41422 }, new int[] { 1 }, new int[] { 40568 }, 1L, new int[] { 1 });
                    pc.getQuest().set_step(CrownLv45_1.QUEST.get_id(), 4);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta5"));
                }
            }
            else if (cmd.equalsIgnoreCase("b")) {
                if (CreateNewItem.checkNewItem(pc, new int[] { 41422 }, new int[] { 1 }) < 1L) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta10"));
                }
                else {
                    CreateNewItem.createNewItem(pc, new int[] { 41422 }, new int[] { 1 }, new int[] { 40568 }, 1L, new int[] { 1 });
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "pieta5"));
                }
            }
        }
    }
}
