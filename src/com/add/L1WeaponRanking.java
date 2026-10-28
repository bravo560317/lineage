package com.add;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;

public class L1WeaponRanking {

    public static String[] TopWeapon() throws SQLException {
        // 需要用到 #1~#20 (#1~#10 玩家名，#11~#20 武器名，#21~#30 加成)
        String[] result = new String[31];
        for (int i = 1; i <= 30; i++) {
            result[i] = "空缺";
        }

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();

            String sql =
                "SELECT c.char_name, w.name, ci.enchantlvl " +
                "FROM character_items ci " +
                "JOIN characters c ON c.objid = ci.char_id " +
                "JOIN weapon w ON ci.item_id = w.item_id " +
                "WHERE ci.is_equipped = 1 " +
                "ORDER BY ci.enchantlvl DESC " +
                "LIMIT 10";

            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();

            int rank = 1;
            while (rs.next() && rank <= 10) {
                String charName = rs.getString("char_name");
                String weaponName = rs.getString("name");
                int enchant = rs.getInt("enchantlvl");

                // 玩家名：#1 ~ #10
                result[rank] = charName;

                // 武器名：#11 ~ #20
                result[10 + rank] = weaponName;

                // 加成：#21 ~ #30
                result[20 + rank] = "+" + enchant;

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
