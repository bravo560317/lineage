package com.lineage.data.npc.quest2;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.WorldQuest;
import com.lineage.data.QuestClass;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.data.quest.ADLv80_3;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.templates.L1Inn;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.InnKeyTable;
import com.lineage.server.world.World;
import com.lineage.server.datatables.ItemTable;
import java.sql.Timestamp;
import java.util.Calendar;
import com.lineage.server.datatables.InnTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_DragonA3 extends NpcExecutor
{
    private static final Log _log;
    private Map<Integer, ArrayList<Integer>> _DOORPLAYERMAP;
    private ArrayList<Integer> key_runout_doorid;
    
    static {
        _log = LogFactory.getLog(Npc_DragonA3.class);
    }
    
    public Npc_DragonA3() {
        this._DOORPLAYERMAP = new HashMap();
        this.key_runout_doorid = new ArrayList();
    }
    
    public static NpcExecutor get() {
        return new Npc_DragonA3();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_n_dragon1", new String[] { "林德拜爾棲息地" }));
        }
        catch (Exception e) {
            Npc_DragonA3._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("0")) {
            if (this.isError2(pc, npc)) {
                return;
            }
            else {
                int npcId = npc.getNpcTemplate().get_npcId();
                for (L1ItemInstance item : pc.getInventory().getItems()) {
                    if (item.getInnNpcId() == npcId) {
                        for (int i = 0; i < 16; ++i) {
                            L1Inn inn = InnTable.getInstance().getTemplate(npcId, i);
                            if (inn.getKeyId() == item.getKeyId()) {
                                Timestamp dueTime = item.getDueTime();
                                if (dueTime != null) {
                                    Calendar cal = Calendar.getInstance();
                                    if ((cal.getTimeInMillis() - dueTime.getTime()) / 1000L < 0L) {
                                        pc.set_showId(item.getKeyId());
                                        this.staraQuestA(npc, pc);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (cmd.equalsIgnoreCase("1")) {
            amount = 32L;
            int npcId = npc.getNpcTemplate().get_npcId();
            boolean canRent = false;
            boolean findRoom = false;
            boolean isRented = false;
            int roomNumber = 0;
            byte roomCount = 0;
            for (int j = 0; j < 16; ++j) {
                L1Inn inn2 = InnTable.getInstance().getTemplate(npcId, j);
                if (inn2 != null) {
                    Timestamp dueTime2 = inn2.getDueTime();
                    Calendar cal2 = Calendar.getInstance();
                    long checkDueTime = (cal2.getTimeInMillis() - dueTime2.getTime()) / 1000L;
                    if (pc.getInventory().checkItem(82504, 1L)) {
                        isRented = true;
                        break;
                    }
                    else if (!findRoom && !isRented) {
                        if (checkDueTime >= 0L) {
                            canRent = true;
                            findRoom = true;
                            roomNumber = inn2.getRoomNumber();
                        }
                        else if (inn2.getLodgerId() == pc.getId()) {
                            canRent = true;
                            findRoom = true;
                            roomNumber = inn2.getRoomNumber();
                        }
                        else {
                            ++roomCount;
                        }
                    }
                }
            }
            if (isRented) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_n_dragon6"));
            }
            else if (roomCount >= 16) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_n_dragon3"));
            }
            else if (canRent && !this.key_runout_doorid.contains((Integer)npc.getId())) {
                if (pc.getInventory().checkItem(40308, 300L * amount)) {
                    L1Inn inn3 = InnTable.getInstance().getTemplate(npcId, roomNumber);
                    if (inn3 != null) {
                        Timestamp ts = new Timestamp(System.currentTimeMillis() + 7200000L);
                        L1ItemInstance item2 = ItemTable.get().createItem(82504);
                        if (item2 != null) {
                            item2.setKeyId(item2.getId());
                            item2.setInnNpcId(npcId);
                            item2.setHall(false);
                            item2.setDueTime(ts);
                            item2.setCount(amount);
                            inn3.setKeyId(item2.getKeyId());
                            inn3.setLodgerId(pc.getId());
                            inn3.setHall(false);
                            inn3.setDueTime(ts);
                            InnTable.getInstance().updateInn(inn3);
                            pc.getInventory().consumeItem(40308, 300L * amount);
                            L1Inventory inventory;
                            if (pc.getInventory().checkAddItem(item2, amount) == 0) {
                                inventory = pc.getInventory();
                            }
                            else {
                                inventory = World.get().getInventory(pc.getLocation());
                            }
                            inventory.storeItem(item2);
                            if (InnKeyTable.checkey(item2)) {
                                InnKeyTable.DeleteKey(item2);
                                InnKeyTable.StoreKey(item2);
                            }
                            else {
                                InnKeyTable.StoreKey(item2);
                            }
                            pc.sendPackets(new S_ServerMessage(143, npc.getName(), item2.getLogName()));
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_n_dragon2"));
                            this.key_runout_doorid.add(npc.getId());
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_n_dragon5"));
                }
            }
            else if (this.key_runout_doorid.contains((Integer)npc.getId())) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_n_dragon4"));
            }
        }
    }
    
    private void staraQuestA(L1NpcInstance npc, L1PcInstance pc) {
        try {
            int questid = ADLv80_3.QUEST.get_id();
            ArrayList<Integer> playerlist = this._DOORPLAYERMAP.get((Integer)npc.getId());
            if (playerlist == null) {
                playerlist = new ArrayList();
            }
            if (playerlist.contains((Integer)pc.getId())) {
                pc.sendPackets(new S_BlueMessage(166, "\\f3逃離副本後無法再進入"));
                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7854));
                SkillMode mode = L1SkillMode.get().getSkill(6799);
                if (mode != null) {
                    mode.start(pc, null, null, 43200);
                }
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                playerlist.add(pc.getId());
                this._DOORPLAYERMAP.put(npc.getId(), playerlist);
                QuestClass.get().startQuest(pc, ADLv80_3.QUEST.get_id());
                QuestClass.get().endQuest(pc, ADLv80_3.QUEST.get_id());
                L1QuestUser quest = WorldQuest.get().put(pc.get_showId(), 1017, questid, pc);
                L1Teleport.teleport(pc, 32673, 32925, (short)1017, 5, true);
                if (quest == null) {
                    Npc_DragonA3._log.error("副本設置過程發生異常!!");
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    return;
                }
                else {
                    Integer time = QuestMapTable.get().getTime(1017);
                    if (time != null) {
                        quest.set_time(time);
                    }
                    L1SpawnUtil.spawnDoor(quest, 10037, 8011, 32675, 33182, (short)1017, 0);
                    L1SpawnUtil.spawnDoor(quest, 10038, 8015, 32687, 33125, (short)1017, 1);
                    L1SpawnUtil.spawnDoor(quest, 10039, 8015, 32729, 33180, (short)1017, 1);
                    L1SpawnUtil.spawn(pc.get_showId(), 8138, 32717, 32914, 1017, 0);
                    for (L1NpcInstance npc2 : quest.npcList()) {
                        if (npc2 instanceof L1MonsterInstance) {
                            L1MonsterInstance mob = (L1MonsterInstance)npc2;
                            if (mob.getNpcId() >= 97204 && mob.getNpcId() <= 97206) {
                                continue;
                            }
                            else {
                                mob.set_storeDroped(true);
                                mob.getInventory().clearItems();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            Npc_DragonA3._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private boolean isError2(L1PcInstance pc, L1NpcInstance npc) {
        if (!pc.getInventory().checkItem(82504, 1L)) {
            pc.sendPackets(new S_SystemMessage("身上必須要有龍門憑證才能進入。"));
            return true;
        }
        else if (pc.getLevel() < 60) {
            pc.sendPackets(new S_SystemMessage("等級未滿60級，無法進入"));
            return true;
        }
        else if (pc.hasSkillEffect(6799)) {
            pc.sendPackets(new S_ServerMessage(1626));
            return true;
        }
        else {
            int users = QuestMapTable.get().getTemplate(1017);
            if (users == -1) {
                users = 127;
            }
            ArrayList<Integer> playerlist = this._DOORPLAYERMAP.get((Integer)npc.getId());
            if (playerlist != null && playerlist.size() >= users) {
                pc.sendPackets(new S_SystemMessage("已達進入人數上限，無法進入"));
                return true;
            }
            else {
                return false;
            }
        }
    }
}
