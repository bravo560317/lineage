package com.lineage.server.datatables.sql;

import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.Set;
import com.lineage.server.templates.L1Quest;
import com.lineage.server.datatables.QuestTable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.CharQuest;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharacterQuestStorage;

public class CharacterQuestTable implements CharacterQuestStorage
{
    public static final Log _log;
    private static final Map<Integer, HashMap<Integer, CharQuest>> _questList;
    
    static {
        _log = LogFactory.getLog(CharacterQuestTable.class);
        _questList = new HashMap();
    }
    
    @Override
    public void load() {
        delete();
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `character_quests`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int char_id = rs.getInt("char_id");
                if (CharObjidTable.get().isChar(char_id) != null) {
                    int key = rs.getInt("quest_id");
                    switch (key) {
                        case 110:
                        case 117:
                        case 137:
                        case 139:
                        case 147: {
                            continue;
                        }
                        default: {
                            int quest_step = rs.getInt("quest_step");
                            int[] mob_count = getArray(rs.getString("mob_count"));
                            CharQuest quest = new CharQuest();
                            quest.set_quest_step(quest_step);
                            quest.set_mob_count(mob_count);
                            HashMap<Integer, CharQuest> hsMap = CharacterQuestTable._questList.get(new Integer(char_id));
                            if (hsMap == null) {
                                hsMap = new HashMap();
                                hsMap.put(new Integer(key), quest);
                                CharacterQuestTable._questList.put(new Integer(char_id), hsMap);
                                continue;
                            }
                            else {
                                hsMap.put(new Integer(key), quest);
                                continue;
                            }
                        }
                    }
                }
                else {
                    delete(char_id);
                }
            }
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        CharacterQuestTable._log.info("載入人物任務紀錄資料數量: " + CharacterQuestTable._questList.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete() {
        deleteData(110);
        deleteData(117);
        deleteData(137);
        deleteData(139);
        deleteData(147);
    }
    
    public static void deleteData(int questid) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("DELETE FROM `character_quests` WHERE `quest_id`=?");
            pm.setInt(1, questid);
            pm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    private static void delete(int objid) {
        CharacterQuestTable._questList.remove((Integer)objid);
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("DELETE FROM `character_quests` WHERE `char_id`=?");
            pm.setInt(1, objid);
            pm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public Map<Integer, CharQuest> get(int char_id) {
        return (Map)CharacterQuestTable._questList.get(new Integer(char_id));
    }
    
@Override
public void storeQuest(int char_id, int key, CharQuest value) {
    HashMap<Integer, CharQuest> hsMap = CharacterQuestTable._questList.get(new Integer(char_id));
    if (hsMap == null) {
        hsMap = new HashMap<Integer, CharQuest>();
        hsMap.put(new Integer(key), value);
        CharacterQuestTable._questList.put(new Integer(char_id), hsMap);
    } else {
        hsMap.put(new Integer(key), value);
    }

    L1Quest quest = null;
    if (value.get_quest_step() == 1) {
        quest = QuestTable.get().getTemplate(key);
    }

    Connection co = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        co = DatabaseFactory.get().getConnection();

        // 🔍 檢查角色是否已經有這個任務
        ps = co.prepareStatement("SELECT COUNT(*) FROM character_quests WHERE char_id=? AND quest_id=?");
        ps.setInt(1, char_id);
        ps.setInt(2, key);
        rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            // 已存在 → 直接略過，不新增、不更新
            return;
        }
        SQLUtil.close(rs);
        SQLUtil.close(ps);

        // 🧾 不存在 → 新增一筆
        String add = "";
        if (quest != null) {
            add = ",`note`=?";
        }
        ps = co.prepareStatement("INSERT INTO `character_quests` SET `char_id`=?,`quest_id`=?,`quest_step`=?,`mob_count`=?" + add);
        int i = 0;
        ps.setInt(++i, char_id);
        ps.setInt(++i, key);
        ps.setInt(++i, value.get_quest_step());
        ps.setString(++i, getStatus(value.get_mob_count()));
        if (quest != null) {
            ps.setString(++i, quest.get_note());
        }
        ps.execute();

    } catch (SQLException e) {
        CharacterQuestTable._log.error("storeQuest 發生例外: char_id=" + char_id + ", quest_id=" + key, e);
    } finally {
        SQLUtil.close(rs);
        SQLUtil.close(ps);
        SQLUtil.close(co);
    }
}
    
    @Override
    public void storeQuest2(int char_id, int key, int value) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            String add = "";
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("INSERT INTO `character_quests` SET `char_id`=?,`quest_id`=?,`quest_step`=?" + add);
            int i = 0;
            pm.setInt(++i, char_id);
            pm.setInt(++i, key);
            pm.setInt(++i, value);
            pm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void updateQuest(int char_id, int key, CharQuest value) {
        HashMap<Integer, CharQuest> hsMap = CharacterQuestTable._questList.get(new Integer(char_id));
        if (hsMap == null) {
            hsMap = new HashMap();
            hsMap.put(new Integer(key), value);
            CharacterQuestTable._questList.put(new Integer(char_id), hsMap);
        }
        else {
            hsMap.put(new Integer(key), value);
        }
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("UPDATE `character_quests` SET `quest_step`=?, `mob_count`=? WHERE `char_id`=? AND `quest_id`=?");
            int i = 0;
            pm.setInt(++i, value.get_quest_step());
            pm.setString(++i, getStatus(value.get_mob_count()));
            pm.setInt(++i, char_id);
            pm.setInt(++i, key);
            pm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void delQuest(int char_id, int key) {
        HashMap<Integer, CharQuest> hsMap = CharacterQuestTable._questList.get(new Integer(char_id));
        if (hsMap == null) {
            return;
        }
        else {
            hsMap.remove((Integer)key);
            Connection co = null;
            PreparedStatement pm = null;
            try {
                co = DatabaseFactory.get().getConnection();
                pm = co.prepareStatement("DELETE FROM `character_quests` WHERE `char_id`=? AND `quest_id`=?");
                int i = 0;
                pm.setInt(++i, char_id);
                pm.setInt(++i, key);
                pm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pm);
                SQLUtil.close(co);
            }
            return;
        }
    }
    
    @Override
    public void delQuest2(int questId) {
        Map<Integer, HashMap<Integer, CharQuest>> hsMap = CharacterQuestTable._questList;
        Set<Integer> keySet = (Set)hsMap.keySet();
        for (Integer charId : keySet) {
            HashMap<Integer, CharQuest> pchasQuest = CharacterQuestTable._questList.get(new Integer(charId));
            if (pchasQuest == null) {
                continue;
            }
            else {
                pchasQuest.remove((Integer)questId);
            }
        }
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("DELETE FROM `character_quests` WHERE `quest_id`=?");
            int i = 0;
            pm.setInt(++i, questId);
            pm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    private static int[] getArray(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        else {
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
    }
    
    private static String getStatus(int[] status) {
        if (status == null) {
            return "0";
        }
        else {
            StringBuilder result = new StringBuilder();
            try {
                for (int statussize = status.length, i = 0; i < statussize; ++i) {
                    if (i > 0) {
                        result.append(",");
                    }
                    result.append(status[i]);
                }
            }
            catch (Exception e) {
                CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            }
            return result.toString();
        }
    }
    
    private void cleanMissionStatus() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_quests WHERE clean = 1");
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void storeQuest(int char_id, int key, CharQuest value, int clean) {
        HashMap<Integer, CharQuest> hsMap = CharacterQuestTable._questList.get(new Integer(char_id));
        if (hsMap == null) {
            hsMap = new HashMap();
            hsMap.put(new Integer(key), value);
            CharacterQuestTable._questList.put(new Integer(char_id), hsMap);
        }
        else {
            hsMap.put(new Integer(key), value);
        }
        L1Quest quest = null;
        if (value.get_quest_step() == 1) {
            quest = QuestTable.get().getTemplate(key);
        }
        Connection co = null;
        PreparedStatement pm = null;
        try {
            String add = "";
            if (quest != null) {
                add = ",`note`=?";
            }
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("INSERT INTO `character_quests` SET `char_id`=?,`quest_id`=?,`quest_step`=?,`clean`=?,`mob_count`=?" + add);
            int i = 0;
            pm.setInt(++i, char_id);
            pm.setInt(++i, key);
            pm.setInt(++i, value.get_quest_step());
            pm.setInt(++i, clean);
            pm.setString(++i, getStatus(value.get_mob_count()));
            if (quest != null) {
                pm.setString(++i, quest.get_note());
            }
            pm.execute();
        }
        catch (SQLException e) {
            CharacterQuestTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
}
