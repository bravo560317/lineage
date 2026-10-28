package com.lineage.data.npc.quest;

import com.lineage.server.templates.L1QuestUser;
import java.util.Iterator;
import com.lineage.server.model.L1Party;
import com.lineage.data.quest.Chapter01R;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.data.QuestClass;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.world.WorldQuest;
import com.lineage.data.quest.Chapter01;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Yuki extends NpcExecutor
{
    private static final Log _log;
    public static final Random _random;
    
    static {
        _log = LogFactory.getLog(Npc_Yuki.class);
        _random = new Random();
    }
    
    public static NpcExecutor get() {
        return new Npc_Yuki();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "id0"));
        }
        catch (Exception e) {
            Npc_Yuki._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            boolean isCloseList = false;
            if (cmd.equalsIgnoreCase("enter")) {
                if (!pc.isInParty()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "id0_1"));
                    return;
                }
                else {
                    int count = 5;
                    if (pc.isGm()) {
                        count = 2;
                    }
                    if (pc.getParty().getNumOfMembers() < count) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "id0_1"));
                        return;
                    }
                    else if (!pc.getParty().isLeader(pc)) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "id0_2"));
                        return;
                    }
                    else {
                        this.teltport_all(pc);
                        isCloseList = true;
                    }
                }
            }
            if (isCloseList) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        catch (Exception e) {
            Npc_Yuki._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void teltport_all(L1PcInstance pc) {
        try {
            int questid = Chapter01.QUEST.get_id();
            int mapid = 9000;
            int showId = WorldQuest.get().nextId();
            L1Party party = pc.getParty();
            for (L1PcInstance otherPc : party.partyUsers().values()) {
                if (otherPc.getId() != party.getLeaderID()) {
                    L1PolyMorph.undoPoly(otherPc);
                    WorldQuest.get().put(showId, 9000, questid, otherPc);
                    L1BuffUtil.cancelAbsoluteBarrier(otherPc);
                    L1Teleport.teleport(otherPc, 32729 + (Npc_Yuki._random.nextInt(5) - 2), 32725 + (Npc_Yuki._random.nextInt(5) - 2), (short)9000, 0, true);
                    QuestClass.get().startQuest(otherPc, Chapter01.QUEST.get_id());
                }
            }
            L1QuestUser quest = WorldQuest.get().put(showId, 9000, questid, pc);
            if (quest == null) {
                Npc_Yuki._log.error("副本設置過程發生異常!!");
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                quest.set_info(false);
                Integer time = QuestMapTable.get().getTime(9000);
                if (time != null) {
                    quest.set_time(time);
                }
                L1PolyMorph.undoPoly(pc);
                L1BuffUtil.cancelAbsoluteBarrier(pc);
                L1Teleport.teleport(pc, 32729, 32725, (short)9000, 2, true);
                QuestClass.get().startQuest(pc, Chapter01.QUEST.get_id());
                Chapter01R chapter01R = new Chapter01R(party, showId, Chapter01.QUEST.get_id());
                chapter01R.startR();
            }
        }
        catch (Exception e) {
            Npc_Yuki._log.error(e.getLocalizedMessage(), e);
        }
    }
}
