package com.lineage.data.npc.quest;

import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.ElfLv45_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Sepia extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Sepia.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Sepia();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (!pc.isCrown() && !pc.isKnight()) {
                if (pc.isElf()) {
                    if (pc.getQuest().isEnd(ElfLv45_1.QUEST.get_id())) {
                        return;
                    }
                    else if (pc.getLevel() >= ElfLv45_1.QUEST.get_questlevel()) {
                        switch (pc.getQuest().get_step(ElfLv45_1.QUEST.get_id())) {
                            case 0:
                            case 1: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "sepia2"));
                                break;
                            }
                            case 2:
                            case 3: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "sepia1"));
                                break;
                            }
                        }
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "sepia2"));
                    }
                }
                else if (!pc.isWizard() && !pc.isDarkelf() && !pc.isDragonKnight()) {
                    pc.isIllusionist();
                }
            }
        }
        catch (Exception e) {
            Npc_Sepia._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isElf()) {
            if (pc.getQuest().isEnd(ElfLv45_1.QUEST.get_id())) {
                return;
            }
            else if (!pc.getQuest().isStart(ElfLv45_1.QUEST.get_id())) {
                isCloseList = true;
            }
            else {
                switch (pc.getQuest().get_step(ElfLv45_1.QUEST.get_id())) {
                    case 2:
                    case 3: {
                        if (!cmd.equalsIgnoreCase("teleport sepia-dungen")) {
                            break;
                        }
                        else {
                            pc.getQuest().set_step(ElfLv45_1.QUEST.get_id(), 3);
                            staraQuest(pc, 302);
                            isCloseList = true;
                            break;
                        }
                    }
                    default: {
                        isCloseList = true;
                        break;
                    }
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
    
    public static void staraQuest(L1PcInstance pc, int mapid) {
        try {
            int questid = ElfLv45_1.QUEST.get_id();
            int showId = WorldQuest.get().nextId();
            L1QuestUser quest = WorldQuest.get().put(showId, mapid, questid, pc);
            if (quest == null) {
                Npc_Sepia._log.error("副本設置過程發生異常!!");
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                Integer time = QuestMapTable.get().getTime(mapid);
                if (time != null) {
                    quest.set_time(time);
                }
                L1Teleport.teleport(pc, 32745, 32872, (short)mapid, 0, true);
            }
        }
        catch (Exception e) {
            Npc_Sepia._log.error(e.getLocalizedMessage(), e);
        }
    }
}
