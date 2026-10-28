package com.lineage.data.npc.quest;

import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.quest.DarkElfLv50_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_CorruptSoul extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_CorruptSoul.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_CorruptSoul();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (!pc.getInventory().checkEquipped(20037)) {
                return;
            }
            else if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq2"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq2"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq2"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq2"));
            }
            else if (pc.isDarkelf()) {
                if (pc.getQuest().isEnd(DarkElfLv50_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq3"));
                    return;
                }
                else {
                    switch (pc.getQuest().get_step(DarkElfLv50_1.QUEST.get_id())) {
                        case 0:
                        case 1:
                        case 2:
                        case 3: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulqn"));
                            break;
                        }
                        case 4: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq1"));
                            break;
                        }
                        default: {
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq3"));
                            break;
                        }
                    }
                }
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq2"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq2"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulq2"));
            }
        }
        catch (Exception e) {
            Npc_CorruptSoul._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDarkelf()) {
            if (pc.getQuest().isEnd(DarkElfLv50_1.QUEST.get_id())) {
                isCloseList = true;
            }
            else if (pc.getInventory().checkItem(20037)) {
                if (pc.getQuest().get_step(DarkElfLv50_1.QUEST.get_id()) == 4) {
                    if (cmd.equalsIgnoreCase("teleportURL")) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "csoulqs"));
                    }
                    else if (cmd.equalsIgnoreCase("teleport evil-dungeon")) {
                        this.staraQuest(pc);
                        isCloseList = true;
                    }
                }
                else {
                    isCloseList = true;
                }
            }
            else {
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
            int questid = DarkElfLv50_1.QUEST.get_id();
            int mapid = 306;
            int showId = WorldQuest.get().nextId();
            L1QuestUser quest = WorldQuest.get().put(showId, 306, questid, pc);
            if (quest == null) {
                Npc_CorruptSoul._log.error("副本設置過程發生異常!!");
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                quest.set_info(false);
                Integer time = QuestMapTable.get().getTime(306);
                if (time != null) {
                    quest.set_time(time);
                }
                L1Teleport.teleport(pc, 32748, 32799, (short)306, 5, true);
            }
        }
        catch (Exception e) {
            Npc_CorruptSoul._log.error(e.getLocalizedMessage(), e);
        }
    }
}
