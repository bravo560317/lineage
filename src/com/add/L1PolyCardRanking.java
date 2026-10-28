package com.add;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

/**
 * 變身卡牌排行榜（前 10 名）
 * #1~#10  = 玩家名稱
 * #11~#20 = 卡牌數量
 */
public class L1PolyCardRanking {

    public static String[] TopPolyCard() {

        String[] result = new String[21];

        // 初始化
        for (int i = 1; i <= 20; i++) {
            result[i] = "空缺";
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();

            String sql =
                "SELECT c.char_name, COUNT(q.quest_id) AS cnt " +
                "FROM character_quests q " +
                "JOIN characters c ON c.objid = q.char_id " +
                "WHERE q.quest_id BETWEEN 5001 AND 5250 " +
                "GROUP BY q.char_id " +
                "ORDER BY cnt DESC " +
                "LIMIT 10";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            int rank = 1;
            while (rs.next() && rank <= 10) {

                String charName = rs.getString("char_name");
                int count = rs.getInt("cnt");

                // #1~#10 玩家名
                result[rank] = charName;

                // #11~#20 卡牌數量
                result[10 + rank] = String.valueOf(count);

                rank++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(con);
        }

        return result;
    }
}
