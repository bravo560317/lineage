package com.add;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

public class L1PrestigeRanking {

    public static String[] TopPrestige() throws SQLException {
        // 需要用到 #1~#20 (#1~#10 玩家名，#11~#20 威望值)
        String[] result = new String[21];
        for (int i = 1; i <= 20; i++) {
            result[i] = "空缺";
        }

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();

            String sql =
                "SELECT char_name, PrestigeLv " +
                "FROM characters " +
                "ORDER BY PrestigeLv DESC " +
                "LIMIT 10";

            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();

            int rank = 1;
            while (rs.next() && rank <= 10) {
                String charName = rs.getString("char_name");
                int prestige = rs.getInt("PrestigeLv");

                // 玩家名：#1 ~ #10
                result[rank] = charName;

                // 威望值：#11 ~ #20
                result[10 + rank] = String.valueOf(prestige);

                rank++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }

        return result;
    }
}
