package com.lineage.data.npc.quest;

import com.lineage.server.templates.L1QuestUser;
import java.util.Iterator;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.World;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.quest.CrownLv30_1;
import com.lineage.data.quest.CrownLv15_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Ants extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Ants.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Ants();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            boolean isTak = false;
            if (pc.getTempCharGfx() == 1039) {
                isTak = true;
            }
            if (pc.getTempCharGfx() == 1037) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ants3"));
                return;
            }
            else if (!isTak) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ants2"));
                return;
            }
            else if (pc.isCrown()) {
                if (!pc.getQuest().isEnd(CrownLv15_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
                }
                else if (pc.getQuest().isEnd(CrownLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
                }
                else if (pc.getLevel() >= CrownLv30_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(CrownLv30_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
                    }
                    else if (pc.getInventory().checkItem(40547)) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ants1"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
                }
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antsn"));
            }
        }
        catch (Exception e) {
            Npc_Ants._log.error(e.getLocalizedMessage(), e);
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
                isCloseList = true;
            }
            else if (pc.getInventory().checkItem(40547)) {
                isCloseList = true;
            }
            else if (cmd.equalsIgnoreCase("teleportURL")) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "antss"));
            }
            else if (cmd.equalsIgnoreCase("teleport mutant-dungen")) {
                this.staraQuest(pc);
                isCloseList = true;
            }
        }
        else {
            isCloseList = true;
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void staraQuest(L1PcInstance pc) {
        try {
            int questid = CrownLv30_1.QUEST.get_id();
            int mapid = 217;
            int showId = WorldQuest.get().nextId();
            int users = QuestMapTable.get().getTemplate(217);
            if (users == -1) {
                users = 127;
            }
            int i = 0;
            for (L1PcInstance otherPc : World.get().getVisiblePlayer(pc, 3)) {
                if (otherPc.getClanid() != 0 && otherPc.getClanid() == pc.getClanid() && otherPc.getId() != pc.getId()) {
                    if (i <= users - 1) {
                        WorldQuest.get().put(showId, 217, questid, otherPc);
                        L1Teleport.teleport(otherPc, 32662, 32786, (short)217, 3, true);
                    }
                    ++i;
                }
            }
            L1QuestUser quest = WorldQuest.get().put(showId, 217, questid, pc);
            if (quest == null) {
                Npc_Ants._log.error("副本設置過程發生異常!!");
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                Integer time = QuestMapTable.get().getTime(217);
                if (time != null) {
                    quest.set_time(time);
                }
                L1Teleport.teleport(pc, 32662, 32786, (short)217, 3, true);
                pc.getQuest().set_step(CrownLv30_1.QUEST.get_id(), 2);
            }
        }
        catch (Exception e) {
            Npc_Ants._log.error(e.getLocalizedMessage(), e);
        }
    }
}
