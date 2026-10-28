package com.lineage.server.datatables;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class ServerQuestMaPTable {

    private static final Log _log;
    private static ArrayList<ArrayList<Object>> _array;
    private static ServerQuestMaPTable _instance;

    static {
        _log = LogFactory.getLog(ServerQuestMaPTable.class);
        _array = new ArrayList<>();
    }

    public static ServerQuestMaPTable get() {
        if (_instance == null) {
            _instance = new ServerQuestMaPTable();
        }
        return _instance;
    }

    private ServerQuestMaPTable() {
        PerformanceTimer timer = new PerformanceTimer();
        getData();
        _log.info("載入地圖狩獵數據設置資料數量: " + _array.size() + "(" + timer.get() + "ms)");
        if (_array.size() <= 0) {
            _array.clear();
            _array = null;
        }
    }

    /* ======================================================
     * 【新增】檢查 9000~9200 是否已有進行中的任務
     * ====================================================== */
    private static boolean hasRunningQuest(L1PcInstance pc, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (pc.getQuest().get_step(i) == 1) {
                return true;
            }
        }
        return false;
    }

    /* ======================================================
     * 接取任務檢查
     * ====================================================== */
    public static boolean checkMobMap(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {

        if (_array == null || _array.size() <= 0) {
            return false;
        }

        ArrayList<?> aTempData;

        for (int i = 0; i < _array.size(); i++) {
            aTempData = _array.get(i);

            if (aTempData.get(0) != null
                    && (Integer) aTempData.get(0) == npcid
                    && ((String) aTempData.get(1)).equals(s)) {

                int questId = (Integer) aTempData.get(5);

                // 任務進行中
                if (pc.getQuest().get_step(questId) == 1) {
                    pc.sendPackets(new S_SystemMessage("該任務正在進行中"));
                    return false;
                }

                // 任務已完成
                if (pc.getQuest().get_step(questId) == 255) {
                    pc.sendPackets(new S_SystemMessage("該狩獵任務已完成"));
                    return false;
                }

                // 等級不足
                if (pc.getLevel() < (Integer) aTempData.get(2)) {
                    pc.sendPackets(new S_SystemMessage("等級不足:" + aTempData.get(2)));
                    return false;
                }

// ★★★★★ 任務分類限制（主線 / 循環分開）★★★★★

questId = (Integer) aTempData.get(5);

// ===== 主線任務 9000~9200 =====
if (questId >= 9000 && questId <= 9200) {
    for (int i2 = 9000; i2 <= 9200; i2++) {
        if (pc.getQuest().get_step(i2) == 1) {
            pc.sendPackets(new S_SystemMessage("主線任務進行中，請先完成"));
            return false;
        }
    }
}

// ===== 循環任務 9500~9600 =====
if (questId >= 9500 && questId <= 9600) {
    for (int i2 = 9500; i2 <= 9600; i2++) {
        if (pc.getQuest().get_step(i2) == 1) {
            pc.sendPackets(new S_SystemMessage("循環任務進行中，請先完成"));
            return false;
        }
    }
}

                // 任務需求道具不足
                if (!pc.getInventory().checkItem(
                        (Integer) aTempData.get(3),
                        (long) (int) (Integer) aTempData.get(4))) {

                    L1Item temp = ItemTable.get().getTemplate((Integer) aTempData.get(3));
                    pc.sendPackets(new S_ServerMessage(
                            337,
                            temp.getName() + "("
                            + ((long) (int) (Integer) aTempData.get(4)
                            - pc.getInventory().countItems(temp.getItemId()))
                            + ")"));
                    return false;
                }

                // 接取任務
                pc.getInventory().consumeItem(
                        (Integer) aTempData.get(3),
                        (long) (int) (Integer) aTempData.get(4));

                pc.getQuest().set_step(questId, 1);

// ★ 新增：寫入 character_quests.note
updateQuestNote(pc, questId, (String) aTempData.get(10));

                pc.get_other3().set_type1(0);

                if ((Integer) aTempData.get(6) > 0) {
                    pc.setSave_Quest_Map1((Integer) aTempData.get(6));
                    pc.get_other3().set_type2((Integer) aTempData.get(6));
                }
                if ((Integer) aTempData.get(12) > 0) {
                    pc.setSave_Quest_Map2((Integer) aTempData.get(12));
                    pc.get_other3().set_type3((Integer) aTempData.get(12));
                }
                if ((Integer) aTempData.get(13) > 0) {
                    pc.setSave_Quest_Map3((Integer) aTempData.get(13));
                    pc.get_other3().set_type4((Integer) aTempData.get(13));
                }
                if ((Integer) aTempData.get(14) > 0) {
                    pc.setSave_Quest_Map4((Integer) aTempData.get(14));
                    pc.get_other3().set_type5((Integer) aTempData.get(14));
                }
                if ((Integer) aTempData.get(15) > 0) {
                    pc.setSave_Quest_Map5((Integer) aTempData.get(15));
                    pc.get_other3().set_type6((Integer) aTempData.get(15));
                }

                pc.get_other3().set_type1((Integer) aTempData.get(7));
                pc.sendPackets(new S_SystemMessage((String) aTempData.get(10)));
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        return false;
    }

    /* ======================================================
     * 地圖擊殺計算
     * ====================================================== */
    public static void check(L1PcInstance pc) {

        if (_array == null || _array.size() <= 0) {
            return;
        }

        ArrayList<?> aTempData;

        for (int i = 0; i < _array.size(); i++) {
            aTempData = _array.get(i);

            if (pc.getQuest().get_step((Integer) aTempData.get(5)) == 1
                    && !pc.hasSkillEffect(80552)
                    && ((pc.getSave_Quest_Map1() > 0 && pc.getSave_Quest_Map1() == pc.getMapId())
                    || (pc.getSave_Quest_Map2() > 0 && pc.getSave_Quest_Map2() == pc.getMapId())
                    || (pc.getSave_Quest_Map3() > 0 && pc.getSave_Quest_Map3() == pc.getMapId())
                    || (pc.getSave_Quest_Map4() > 0 && pc.getSave_Quest_Map4() == pc.getMapId())
                    || (pc.getSave_Quest_Map5() > 0 && pc.getSave_Quest_Map5() == pc.getMapId()))) {

                pc.get_other3().set_type1(pc.get_other3().get_type1() - 1);
                pc.sendPackets(new S_SystemMessage(
                        (String) aTempData.get(16) + pc.get_other3().get_type1()));

                if (pc.get_other3().get_type1() < 1) {
                    pc.get_other3().set_type1(0);
                    pc.setSkillEffect(80552, 2000);
                }
            }
        }
    }

    /* ======================================================
     * 完成任務給獎
     * ====================================================== */
public static void checkcount(L1PcInstance pc) {

    if (_array == null || _array.size() <= 0) {
        return;
    }

    ArrayList<?> aTempData;

    for (int i = 0; i < _array.size(); i++) {
        aTempData = _array.get(i);

        if (pc.getQuest().get_step((Integer) aTempData.get(5)) == 1) {

            // 完成訊息
            pc.sendPackets(new S_SystemMessage((String) aTempData.get(11)));

            // ===== 發獎開始 =====
int[] materials = (int[]) aTempData.get(8);
int[] counts = (int[]) aTempData.get(9);

if (materials != null && counts != null) {
    for (int j = 0; j < counts.length; j++) {

        int itemId = materials[j];
        int count = counts[j];

        L1ItemInstance item = ItemTable.get().createItem(itemId);
        if (item == null) {
            continue;
        }

        item.setIdentified(true);

        if (pc.getInventory().checkAddItem(item, count) == 0) {
            pc.getInventory().storeItem(itemId, count);
            pc.sendPackets(
                new S_ServerMessage(403, item.getName() + "(" + count + ")")
            );
        } else {
            World.get()
                .getInventory(pc.getX(), pc.getY(), pc.getMapId())
                .storeItem(itemId, count);

            pc.sendPackets(
                new S_ServerMessage(403, item.getName() + "(" + count + ")")
            );
        }
    }
}
            // ===== 發獎結束 =====

            // 設為完成
            checkquest(pc);

            // 清狀態
            pc.setSave_Quest_Map1(0);
            pc.setSave_Quest_Map2(0);
            pc.setSave_Quest_Map3(0);
            pc.setSave_Quest_Map4(0);
            pc.setSave_Quest_Map5(0);

            pc.get_other3().set_type1(0);
            pc.get_other3().set_type2(0);
            pc.get_other3().set_type3(0);
            pc.get_other3().set_type4(0);
            pc.get_other3().set_type5(0);
            pc.get_other3().set_type6(0);
        }
    }
}
    /* ======================================================
     * 完成後設為 255（9000~9200）
     * ====================================================== */
private static boolean checkquest(L1PcInstance pc) {
    try {
        // ===== 只處理循環任務 =====
        for (int i = 9500; i <= 9600; i++) {
            if (pc.getQuest().get_step(i) == 1) {
                pc.getQuest().set_step(i, 255);
            }
        }
    } catch (Exception e) {
        return false;
    }
    return true;
}

    /* ======================================================
     * 讀取資料表
     * ====================================================== */
    private void getData() {
        Connection cn = null;
        Statement ps = null;
        ResultSet rset = null;

        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.createStatement();
            rset = ps.executeQuery("SELECT * FROM w_狩獵怪物任務_地圖");

            while (rset.next()) {
                ArrayList<Object> a = new ArrayList<>();
                a.add(rset.getInt("npcid"));
                a.add(rset.getString("action"));
                a.add(rset.getInt("等級"));
                a.add(rset.getInt("任務需求道具"));
                a.add(rset.getInt("任務需求數量"));
                a.add(rset.getInt("任務記錄編號"));
                a.add(rset.getInt("指定地圖1"));
                a.add(rset.getInt("擊殺怪物數量"));
                a.add(getArray(rset.getString("完成後給予道具"), ",", 1));
                a.add(getArray(rset.getString("完成後給予數量"), ",", 1));
                a.add(rset.getString("接取任務訊息"));
                a.add(rset.getString("完成任務訊息"));
                a.add(rset.getInt("指定地圖2"));
                a.add(rset.getInt("指定地圖3"));
                a.add(rset.getInt("指定地圖4"));
                a.add(rset.getInt("指定地圖5"));
                a.add(rset.getString("顯示狩獵文字"));
                _array.add(a);
            }
        } catch (Exception e) {
            _log.error(e.getMessage(), e);
        } finally {
            SQLUtil.close(rset);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
private static void updateQuestNote(L1PcInstance pc, int questId, String note) {
    Connection cn = null;
    Statement ps = null;

    try {
        cn = DatabaseFactory.get().getConnection();
        ps = cn.createStatement();

        String sql = "UPDATE character_quests "
                   + "SET note='" + note + "' "
                   + "WHERE char_id=" + pc.getId()
                   + " AND quest_id=" + questId;

        ps.executeUpdate(sql);

    } catch (Exception e) {
        _log.error("寫入 character_quests.note 失敗", e);
    } finally {
        SQLUtil.close(ps);
        SQLUtil.close(cn);
    }
}
    private static Object getArray(String s, String token, int type) {
        if (s == null || s.equals("") || s.equals("0")) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(s, token);
        int size = st.countTokens();
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = Integer.parseInt(st.nextToken());
        }
        return data;
    }
}
