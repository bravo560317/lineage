package com.lineage.data.npc.quest;

import com.lineage.data.quest.CrownLv30_1;
import com.lineage.data.quest.CrownLv15_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Ant extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Ant.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Ant();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            boolean isTak = false;
            if (pc.getTempCharGfx() == 1037) {
                isTak = true;
            }
            if (pc.getTempCharGfx() == 1039) {
                isTak = true;
            }
            if (!isTak) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant2"));
                return;
            }
            else if (pc.isCrown()) {
                if (!pc.getQuest().isEnd(CrownLv15_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
                }
                else if (pc.getQuest().isEnd(CrownLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
                }
                else if (pc.getLevel() >= CrownLv30_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(CrownLv30_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant1"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
                }
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ant3"));
            }
        }
        catch (Exception e) {
            Npc_Ant._log.error(e.getLocalizedMessage(), e);
        }
    }
}
