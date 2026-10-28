package com.lineage.server.datatables;

import java.util.Set;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ServerQuestMob;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class ServerQuestMobTable
{
    private static final Log _log;
    private static ServerQuestMobTable _instance;
    private static final HashMap<Integer, HashMap<Integer, L1ServerQuestMob>> _questMobList;
    
    static {
        _log = LogFactory.getLog(ServerQuestMobTable.class);
        _questMobList = new HashMap();
    }
    
    public static ServerQuestMobTable get() {
        if (ServerQuestMobTable._instance == null) {
            ServerQuestMobTable._instance = new ServerQuestMobTable();
        }
        return ServerQuestMobTable._instance;
    }
    
    private ServerQuestMobTable() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_狩獵怪物任務`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int quest_id = rs.getInt("quest_id");
                int quest_step = rs.getInt("quest_step");
                String note = rs.getString("note");
                int lv = rs.getInt("lv");
                int[] mob_id = getArray(rs.getString("mob_id"));
                int[] mob_count = getArray(rs.getString("mob_count"));
                int[] item_id = getArray(rs.getString("item_id"));
                int[] item_lv = getArray(rs.getString("item_lv"));
                int[] item_count = getArray(rs.getString("item_count"));
                int save_quest_order = rs.getInt("save_quest_step");
                int tele_x = rs.getInt("tele_x");
                int tele_y = rs.getInt("tele_y");
                int tele_m = rs.getInt("tele_m");
                int tele_delay = rs.getInt("tele_delay");
                int addexp = rs.getInt("addexp");
                L1ServerQuestMob serverQuestMob = new L1ServerQuestMob();
                serverQuestMob.set_quest_id(quest_id);
                serverQuestMob.set_quest_step(quest_step);
                serverQuestMob.set_note(note);
                serverQuestMob.set_lv(lv);
                serverQuestMob.set_mob_id(mob_id);
                serverQuestMob.set_mob_count(mob_count);
                serverQuestMob.set_item_id(item_id);
                serverQuestMob.set_item_lv(item_lv);
                serverQuestMob.set_item_count(item_count);
                serverQuestMob.set_save_quest_order(save_quest_order);
                serverQuestMob.set_tele_x(tele_x);
                serverQuestMob.set_tele_y(tele_y);
                serverQuestMob.set_tele_m(tele_m);
                serverQuestMob.set_tele_delay(tele_delay);
                serverQuestMob.setaddexp(addexp);
                HashMap<Integer, L1ServerQuestMob> questMap = ServerQuestMobTable._questMobList.get((Integer)quest_id);
                if (questMap == null) {
                    questMap = new HashMap();
                    questMap.put(quest_step, serverQuestMob);
                    ServerQuestMobTable._questMobList.put(quest_id, questMap);
                }
                else {
                    questMap.put(quest_step, serverQuestMob);
                }
            }
            ServerQuestMobTable._log.info("載入[改寫]狩獵任務設置資料數量: " + ServerQuestMobTable._questMobList.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            ServerQuestMobTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void getQuestMobNote(L1PcInstance pc) {
        if (ServerQuestMobTable._questMobList == null) {
            return;
        }
        else {
            for (Integer quest_id : ServerQuestMobTable._questMobList.keySet()) {
                if (pc.getQuest().isStart(quest_id)) {
                    HashMap<Integer, L1ServerQuestMob> map = ServerQuestMobTable._questMobList.get(quest_id);
                    for (Integer quest_order : map.keySet()) {
                        if (pc.getQuest().get_step(quest_id) == quest_order) {
                            int[] mob_id = (map.get(quest_order)).get_mob_id();
                            int[] mob_count = (map.get(quest_order)).get_mob_count();
                            int[] pc_mob_count = pc.getQuest().get_mob_count(quest_id);
                            if (mob_id.length == mob_count.length) {
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append((map.get(quest_order)).get_note() + "\n");
                                for (int i = 0; i < mob_id.length; ++i) {
                                    String npc_name = NpcTable.get().getTemplate(mob_id[i]).get_name();
                                    stringBuffer.append(npc_name + "(" + pc_mob_count[i] + "/" + mob_count[i] + ")\n");
                                }
                                pc.sendPackets(new S_ServerMessage(stringBuffer.toString(), 11));
                            }
                            else if (mob_count.length == 1) {
                                pc.sendPackets(new S_ServerMessage((map.get(quest_order)).get_note() + "\n 該區域任意怪物(" + pc_mob_count[0] + "/" + mob_count[0] + ")", 11));
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            return;
        }
    }
    
    public synchronized void checkQuestMob(L1PcInstance pc, int mobId) {
        if (ServerQuestMobTable._questMobList == null) {
            return;
        }
        else {
            for (Integer quest_id : ServerQuestMobTable._questMobList.keySet()) {
                HashMap<Integer, L1ServerQuestMob> questMap = ServerQuestMobTable._questMobList.get(quest_id);
                this.cheakQuest(quest_id, mobId, pc, questMap);
                if (pc.getQuest().isStart(quest_id)) {
                    this.checkMob(quest_id, mobId, pc, questMap);
                }
            }
            return;
        }
    }
    private static void updateQuestNote(L1PcInstance pc, int questId, String note) {
    Connection cn = null;
    PreparedStatement ps = null;

    try {
        cn = DatabaseFactory.get().getConnection();

        ps = cn.prepareStatement(
            "UPDATE character_quests SET note=? WHERE char_id=? AND quest_id=?"
        );
        ps.setString(1, note);
        ps.setInt(2, pc.getId());
        ps.setInt(3, questId);
        ps.executeUpdate();

    } catch (Exception e) {
        _log.error("狩獵任務 note 寫入失敗 questId=" + questId, e);
    } finally {
        SQLUtil.close(ps);
        SQLUtil.close(cn);
    }
}

    private void cheakQuest(int quest_id, int mobId, L1PcInstance pc, HashMap<Integer, L1ServerQuestMob> questMap) {
        if (!pc.getQuest().isStart(quest_id)) {
            for (L1ServerQuestMob serverQuestMob : questMap.values()) {
                int[] mobid = serverQuestMob.get_mob_id();
                if (pc.getLevel() + pc.getMeteLevel() * 99 < serverQuestMob.get_lv()) {
                    return;
                }
                else if (pc.getQuest().get_step(quest_id) == serverQuestMob.get_save_quest_order()) {
                    return;
                }
                else {
                    for (int i = 0; i < serverQuestMob.get_mob_id().length; ++i) {
                        if (mobid[i] == mobId) {
                            if (pc.getQuest().get_step(quest_id) == 1) {
                                pc.getQuest().set_step(quest_id, serverQuestMob.get_quest_step());
    // ★ 新增：寫入任務 note
    updateQuestNote(pc, quest_id, serverQuestMob.get_note());
                            }
                            if (pc.isInParty()) {
                                for (L1Object object : World.get().getVisibleObjects(pc, 14)) {
                                    if (object instanceof L1PcInstance) {
                                        L1PcInstance red = (L1PcInstance)object;
                                        if (pc.getParty().isMember(red) && red.getQuest().get_step(quest_id) == 1) {
                                            red.getQuest().set_step(quest_id, serverQuestMob.get_quest_step());
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void checkMob(int quest_id, int mobId, L1PcInstance pc, HashMap<Integer, L1ServerQuestMob> questMap) {
        for (Integer quest_step : questMap.keySet()) {
            if (pc.getQuest().get_step(quest_id) == quest_step) {
                L1ServerQuestMob quest = questMap.get(quest_step);
                if (pc.getLevel() + pc.getMeteLevel() * 99 < quest.get_lv()) {
                    return;
                }
                else {
                    int[] mob_id = quest.get_mob_id();
                    int[] mob_count = quest.get_mob_count();
                    int[] pc_mob_count = pc.getQuest().get_mob_count(quest_id);
                    int mustMobCountLength = 0;
                    boolean mustMobCountOnly = false;
                    if (mob_id.length == mob_count.length) {
                        for (int i = 0; i < mob_id.length; ++i) {
                            if (mobId == mob_id[i] && pc_mob_count[i] < mob_count[i]) {
                                String npc_name = NpcTable.get().getTemplate(mob_id[i]).get_name();
                                pc.getQuest().add_mob_count(quest_id, i);
// ★ 新增：更新 character_quests.note（進度）
updateQuestNote(pc, quest_id, quest.get_note());
                                get().getQuestMobNote(pc);
                            }
                            if (pc_mob_count[i] == mob_count[i]) {
                                ++mustMobCountLength;
                            }
                        }
                    }
                    else if (mob_count.length == 1) {
                        for (int i = 0; i < mob_id.length; ++i) {
                            if (mobId == mob_id[i] && pc_mob_count[0] < mob_count[0]) {
                                pc.getQuest().add_mob_count(quest_id, 0);
                                get().getQuestMobNote(pc);
                            }
                            if (pc_mob_count[0] == mob_count[0]) {
                                mustMobCountOnly = true;
                            }
                        }
                    }
                    if (mustMobCountLength == mob_count.length) {
                        StringBuilder name = new StringBuilder();
                        int[] item_id = quest.get_item_id();
                        int[] item_lv = quest.get_item_lv();
                        int[] item_count = quest.get_item_count();
                        int addexp = quest.getaddexp();
                        if (quest.getaddexp() > 0) {
                            pc.addExp((long)addexp);
                            pc.sendPackets(new S_ServerMessage("\\fT完成了狩獵任務,獎勵經驗:"));
                            pc.sendPackets(new S_SystemMessage("狩獵獎勵經驗值獲得:" + addexp));
                        }
                        for (int j = 0; j < item_id.length; ++j) {
                            L1ItemInstance item = ItemTable.get().createItem(item_id[j]);
                            if (item != null) {
                                item.setCount((long)item_count[j]);
                                if (item_lv[j] != 0) {
                                    item.setEnchantLevel(item_lv[j]);
                                }
                                if (pc.getInventory().checkAddItem(item, (long)item_count[j]) == 0) {
                                    pc.getInventory().storeItem(item);
                                    pc.sendPackets(new S_ServerMessage("\\fT完成了狩獵任務,獎勵物品:"));
                                }
                                else {
                                    item.set_showId(pc.get_showId());
                                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                    pc.sendPackets(new S_ServerMessage("\\fT完成了狩獵任務,獎勵物品:"));
                                }
                                if (name.length() != 0) {
                                    name.append("\n\r");
                                }
                                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                            }
                            else {
                                ServerQuestMobTable._log.error("狩獵任務給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")");
                            }
                        }
                        int step = quest.get_save_quest_order();
                        if (step != 0) {
                            pc.getQuest().set_mob_count(quest_id, pc.getQuest().get_step(quest_id));
                            pc.getQuest().set_step(quest_id, step);
                        }
                        int x = quest.get_tele_x();
                        int y = quest.get_tele_y();
                        int tomapid = quest.get_tele_m();
                        int delay = quest.get_tele_delay();
                        if (x != 0 && y != 0 && tomapid >= 0) {
                            if (delay != 0) {
                                try {
                                    Thread.sleep((long)delay);
                                }
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            L1Teleport.teleport(pc, x, y, (short)tomapid, 5, true);
                        }
                    }
                    if (mustMobCountOnly) {
                        StringBuilder name = new StringBuilder();
                        int[] item_id = quest.get_item_id();
                        int[] item_lv = quest.get_item_lv();
                        int[] item_count = quest.get_item_count();
                        int addexp = quest.getaddexp();
                        if (quest.getaddexp() > 0) {
                            pc.addExp((long)addexp);
                            pc.sendPackets(new S_ServerMessage("\\fT完成了狩獵任務,獎勵經驗:"));
                            pc.sendPackets(new S_SystemMessage("狩獵獎勵經驗值獲得:" + addexp));
                        }
                        for (int j = 0; j < item_id.length; ++j) {
                            L1ItemInstance item = ItemTable.get().createItem(item_id[j]);
                            if (item != null) {
                                item.setCount((long)item_count[j]);
                                if (item_lv[j] != 0) {
                                    item.setEnchantLevel(item_lv[j]);
                                }
                                if (pc.getInventory().checkAddItem(item, (long)item_count[j]) == 0) {
                                    pc.getInventory().storeItem(item);
                                    pc.sendPackets(new S_ServerMessage("\\fT完成了狩獵任務,獎勵物品:"));
                                }
                                else {
                                    item.set_showId(pc.get_showId());
                                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                    pc.sendPackets(new S_ServerMessage("\\fT完成了狩獵任務,獎勵物品:"));
                                }
                                if (name.length() != 0) {
                                    name.append("\n\r");
                                }
                                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                            }
                            else {
                                ServerQuestMobTable._log.error("狩獵任務給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")");
                            }
                        }
                        int step = quest.get_save_quest_order();
                        if (step != 0) {
                            pc.getQuest().set_mob_count(quest_id, pc.getQuest().get_step(quest_id));
                            pc.getQuest().set_step(quest_id, step);
                        }
                        int x = quest.get_tele_x();
                        int y = quest.get_tele_y();
                        int tomapid = quest.get_tele_m();
                        int delay = quest.get_tele_delay();
                        if (x != 0 && y != 0 && tomapid >= 0) {
                            if (delay != 0) {
                                try {
                                    Thread.sleep((long)delay);
                                }
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            L1Teleport.teleport(pc, x, y, (short)tomapid, 5, true);
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        }
    }
    
    private static int[] getArray(String s) {
        StringTokenizer st = new StringTokenizer(s, ",");
        int iSize = st.countTokens();
        String sTemp = null;
        int[] iReturn = new int[iSize];
        for (int i = 0; i < iSize; ++i) {
            sTemp = st.nextToken();
            iReturn[i] = Integer.parseInt(sTemp);
        }
        return iReturn;
    }
    
    public int[] getMobCount(int quest_id, int quest_order) {
        if (ServerQuestMobTable._questMobList.containsKey((Integer)quest_id) && (ServerQuestMobTable._questMobList.get((Integer)quest_id)).containsKey((Integer)quest_order)) {
            int length = ((L1ServerQuestMob)(ServerQuestMobTable._questMobList.get((Integer)quest_id)).get((Integer)quest_order)).get_mob_count().length;
            int[] data = new int[length];
            return data;
        }
        else {
            return null;
        }
    }
    
    public Set<Integer> getQuestMobListId() {
        if (ServerQuestMobTable._questMobList != null) {
            return (Set)ServerQuestMobTable._questMobList.keySet();
        }
        else {
            return null;
        }
    }
}
