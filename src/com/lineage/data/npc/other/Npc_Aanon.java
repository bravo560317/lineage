package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.QuestClass;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.KnightLv15_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Aanon extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Aanon.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Aanon();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon8"));
            }
            else if (pc.isKnight()) {
                if (pc.getQuest().isEnd(KnightLv15_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon8"));
                }
                else if (pc.getLevel() >= KnightLv15_1.QUEST.get_questlevel()) {
                    if (pc.getQuest().get_step(KnightLv15_1.QUEST.get_id()) == 2) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon4"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon8"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon8"));
                }
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon8"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon8"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon8"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon8"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon8"));
            }
        }
        catch (Exception e) {
            Npc_Aanon._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isKnight() && cmd.equalsIgnoreCase("request hood of red knight")) {
            if (pc.getQuest().isEnd(KnightLv15_1.QUEST.get_id())) {
                return;
            }
            else if (pc.getQuest().get_step(KnightLv15_1.QUEST.get_id()) == 2) {
                if (CreateNewItem.checkNewItem(pc, new int[] { 40540, 40601, 20005 }, new int[] { 1, 1, 1 }) < 1L) {
                    isCloseList = true;
                }
                else {
                    CreateNewItem.createNewItem(pc, new int[] { 40540, 40601, 20005 }, new int[] { 1, 1, 1 }, new int[] { 20027 }, 1L, new int[] { 1 });
                    QuestClass.get().endQuest(pc, KnightLv15_1.QUEST.get_id());
                    isCloseList = true;
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aanon1"));
            }
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
