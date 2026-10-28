package com.add;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

public class L1CastleTime {

    public static String[] getCastleTime() {

        String[] result = new String[9]; // 1~8

        for (int i = 1; i <= 8; i++) {
            result[i] = "尚未設定";
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();

            String sql = "SELECT castle_id, war_time FROM server_castle ORDER BY castle_id";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("castle_id");
                String time = rs.getString("war_time");

                if (id >= 1 && id <= 8) {
                    result[id] = time;
                }
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