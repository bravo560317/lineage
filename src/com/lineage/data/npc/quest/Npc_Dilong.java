package com.lineage.data.npc.quest;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.quest.WizardLv30_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Dilong extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Dilong.class);
    }
    
    private Npc_Dilong() {
    }
    
    public static NpcExecutor get() {
        return new Npc_Dilong();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong2"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong2"));
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong2"));
            }
            else if (pc.isWizard()) {
                if (pc.getQuest().isEnd(WizardLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong3"));
                    return;
                }
                else if (pc.getLevel() >= WizardLv30_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(WizardLv30_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong3"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong1"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong2"));
                }
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong2"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong2"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong2"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilong2"));
            }
        }
        catch (Exception e) {
            Npc_Dilong._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isWizard()) {
            if (pc.getQuest().isEnd(WizardLv30_1.QUEST.get_id())) {
                return;
            }
            else if (!pc.getQuest().isStart(WizardLv30_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("teleportURL")) {
                if (!pc.getInventory().checkItem(40581)) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilongn"));
                    return;
                }
                else if (pc.getInventory().checkItem(40579)) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilongn"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "dilongs"));
                }
            }
            else if (cmd.equalsIgnoreCase("teleport mage-quest-dungen")) {
                staraQuest(pc);
            }
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    public static void staraQuest(L1PcInstance pc) {
        try {
            int questid = WizardLv30_1.QUEST.get_id();
            int mapid = 201;
            int showId = WorldQuest.get().nextId();
            L1QuestUser quest = WorldQuest.get().put(showId, 201, questid, pc);
            if (quest == null) {
                Npc_Dilong._log.error("副本設置過程發生異常!!");
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                Integer time = QuestMapTable.get().getTime(201);
                if (time != null) {
                    quest.set_time(time);
                }
                ArrayList<L1NpcInstance> npcs = quest.npcList(81109);
                if (npcs != null) {
                    for (L1NpcInstance npc : npcs) {
                        L1PolyMorph.doPoly(npc, 1128, 1800, 1);
                    }
                }
                L1SpawnUtil.spawnDoor(quest, 10000, 89, 32809, 32795, (short)201, 1);
                L1SpawnUtil.spawnDoor(quest, 10001, 88, 32812, 32909, (short)201, 0);
                L1SpawnUtil.spawnDoor(quest, 10002, 89, 32825, 32920, (short)201, 1);
                L1SpawnUtil.spawnDoor(quest, 10003, 90, 32868, 32919, (short)201, 0);
                pc.getInventory().takeoffEquip(945);
                L1Teleport.teleport(pc, 32791, 32788, (short)201, 5, true);
            }
        }
        catch (Exception e) {
            Npc_Dilong._log.error(e.getLocalizedMessage(), e);
        }
    }
}
