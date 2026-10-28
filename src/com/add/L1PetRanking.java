package com.add;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

/**
 * 寵物血量排行榜（前 10 名）
 * #1~#10  = 寵物名
 * #11~#20 = 寵物HP
 */
public class L1PetRanking {

    public static String[] TopPetHp() {
        // #1~#10 = 名字, #11~#20 = HP
        String[] result = new String[21];

        // 初始化
        for (int i = 1; i <= 20; i++) {
            result[i] = "空缺";
        }

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();

            String sql =
                "SELECT name, hp " +
                "FROM character_pets " +
                "ORDER BY hp DESC " +
                "LIMIT 10";

            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();

            int rank = 1;
            while (rs.next() && rank <= 10) {

                String petName = rs.getString("name");
                int hp = rs.getInt("hp");

                // 寵物名 → #1 ~ #10
                result[rank] = petName;

                // HP → #11 ~ #20
                result[10 + rank] = String.valueOf(hp);

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
