package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.CrownLv30_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.CrownLv15_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Aria extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Aria.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Aria();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                if (!pc.getQuest().isEnd(CrownLv15_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria4"));
                    return;
                }
                else if (pc.getQuest().isEnd(CrownLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria3"));
                    return;
                }
                else if (pc.getLevel() >= CrownLv30_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(CrownLv30_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria1"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria2"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria4"));
                }
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria4"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria4"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria4"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria4"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria4"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria4"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria4"));
            }
        }
        catch (Exception e) {
            Npc_Aria._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isCrown()) {
            if (!pc.getQuest().isEnd(CrownLv15_1.QUEST.get_id())) {
                return;
            }
            else if (pc.getQuest().isEnd(CrownLv30_1.QUEST.get_id())) {
                return;
            }
            else if (!pc.getQuest().isStart(CrownLv30_1.QUEST.get_id())) {
                if (cmd.equalsIgnoreCase("quest 13 aria2")) {
                    QuestClass.get().startQuest(pc, CrownLv30_1.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria2"));
                }
            }
            else if (cmd.equalsIgnoreCase("request questitem")) {
                if (CreateNewItem.checkNewItem(pc, 40547, 1) < 1L) {
                    isCloseList = true;
                }
                else {
                    CreateNewItem.createNewItem(pc, 40547, 1, 40570, 1);
                    QuestClass.get().endQuest(pc, CrownLv30_1.QUEST.get_id());
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "aria3"));
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
