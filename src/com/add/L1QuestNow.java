package com.add;

import java.sql.*;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1QuestNow {

    public static String[] getNowQuest(L1PcInstance pc) {

        String[] data = new String[22]; // 對應 HTML 的 #1 ~ #21

        Connection cn = null;
        Statement st = null;

        try {

            cn = DatabaseFactory.get().getConnection();
            st = cn.createStatement();

            // ======================
            // 主線任務（9001~9200）- 對應 #1 ~ #7
            // ======================
            int mainQuest = 0;

            for (int i = 9001; i <= 9200; i++) {
                int step = pc.getQuest().get_step(i);
                if (step > 0 && step != 255) {
                    mainQuest = i;
                    break;
                }
            }

            if (mainQuest > 0) {

                ResultSet rs = st.executeQuery(
                    "SELECT note, item_id, item_count FROM w_狩獵怪物任務 WHERE quest_id=" + mainQuest
                );

                if (rs.next()) {

                    data[1] = rs.getString("note");

                    Statement st2 = cn.createStatement();
                    ResultSet rs2 = st2.executeQuery(
                        "SELECT mob_count FROM character_quests WHERE char_id=" + pc.getId() + " AND quest_id=" + mainQuest
                    );

                    if (rs2.next()) {
                        data[2] = rs2.getString("mob_count");
                    } else {
                        data[2] = "0";
                    }

                    rs2.close();
                    st2.close();

                    setReward(cn, rs.getString("item_id"), rs.getString("item_count"), data, 3);
                }

                rs.close();

            } else {
                data[1] = "無主線任務";
                data[2] = "-";
            }

            // ======================
            // 循環任務（9500~9600）- 對應 #8 ~ #14
            // ======================
            int loopQuest = 0;

            for (int i = 9500; i <= 9600; i++) {
                int step = pc.getQuest().get_step(i);
                if (step > 0 && step != 255) {
                    loopQuest = i;
                    break;
                }
            }

            if (loopQuest > 0) {

                ResultSet rs = st.executeQuery(
                    "SELECT note, 完成後給予道具, 完成後給予數量 FROM w_狩獵怪物任務_地圖 WHERE 任務記錄編號=" + loopQuest
                );

                if (rs.next()) {

                    data[8] = rs.getString("note");
                    data[9] = String.valueOf(pc.get_other3().get_type1());

                    setReward(cn,
                            rs.getString("完成後給予道具"),
                            rs.getString("完成後給予數量"),
                            data,
                            10);
                }

                rs.close();

            } else {
                data[8] = "無循環任務";
                data[9] = "-";
            }

            // ======================
            // 關卡任務（8001~8050）- 對應 #15 ~ #21
            // ======================
            int stageQuest = 0;

            for (int i = 8001; i <= 8050; i++) {
                int step = pc.getQuest().get_step(i);
                if (step > 0 && step != 255) {
                    stageQuest = i;
                    break;
                }
            }

            if (stageQuest > 0) {

                ResultSet rs = st.executeQuery(
                    "SELECT note, item_id, item_count FROM w_狩獵怪物任務 WHERE quest_id=" + stageQuest
                );

                if (rs.next()) {

                    data[15] = rs.getString("note");

                    Statement st2 = cn.createStatement();
                    ResultSet rs2 = st2.executeQuery(
                        "SELECT mob_count FROM character_quests WHERE char_id=" + pc.getId() + " AND quest_id=" + stageQuest
                    );

                    if (rs2.next()) {
                        data[16] = rs2.getString("mob_count");
                    } else {
                        data[16] = "0";
                    }

                    rs2.close();
                    st2.close();

                    setReward(cn, rs.getString("item_id"), rs.getString("item_count"), data, 17);
                }

                rs.close();

            } else {
                data[15] = "無關卡任務";
                data[16] = "-";
            }

        } catch (Exception e) {
            data[1] = "錯誤：" + e.getMessage();
        } finally {
            try {
                if (st != null) st.close();
                if (cn != null) cn.close();
            } catch (Exception e) {}
        }

        return data;
    }

    // ======================
    // 獎勵轉中文（支援 etcitem, armor, weapon）
    // ======================
    private static void setReward(Connection cn, String itemIds, String counts, String[] data, int startIndex) {

        if (itemIds == null || itemIds.equals("")) {
            return;
        }

        try {

            String[] ids = itemIds.split(",");
            String[] cnts = counts.split(",");

            int line = startIndex;

            for (int i = 0; i < ids.length && line < startIndex + 5; i++) {

                String name = "未知(" + ids[i] + ")";

                // 1. 先從 etcitem 找
                try (Statement st = cn.createStatement();
                     ResultSet rs = st.executeQuery("SELECT name FROM etcitem WHERE item_id=" + ids[i])) {
                    if (rs.next()) {
                        name = rs.getString("name");
                    }
                } catch (Exception e) {}

                // 2. 如果 etcitem 找不到，改從 armor 找
                if (name.startsWith("未知")) {
                    try (Statement st = cn.createStatement();
                         ResultSet rs = st.executeQuery("SELECT name FROM armor WHERE item_id=" + ids[i])) {
                        if (rs.next()) {
                            name = rs.getString("name");
                        }
                    } catch (Exception e) {}
                }

                // 3. 如果 armor 也找不到，改從 weapon 找
                if (name.startsWith("未知")) {
                    try (Statement st = cn.createStatement();
                         ResultSet rs = st.executeQuery("SELECT name FROM weapon WHERE item_id=" + ids[i])) {
                        if (rs.next()) {
                            name = rs.getString("name");
                        }
                    } catch (Exception e) {}
                }

                data[line] = name + "x" + cnts[i];
                line++;
            }

        } catch (Exception e) {
        }
    }
}