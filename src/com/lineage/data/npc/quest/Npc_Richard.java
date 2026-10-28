package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.CrownLv45_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Richard extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Richard.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Richard();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                if (pc.getQuest().isEnd(CrownLv45_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
                    return;
                }
                else if (pc.getLevel() >= CrownLv45_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(CrownLv45_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
                    }
                    else if (pc.getInventory().checkItem(40586)) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard4"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard1"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
                }
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "richard3"));
            }
        }
        catch (Exception e) {
            Npc_Richard._log.error(e.getLocalizedMessage(), e);
        }
    }
}
