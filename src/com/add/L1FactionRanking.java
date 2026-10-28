package com.add;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.utils.SQLUtil;

/**
 * 陣營排行榜（修正版）
 */
public class L1FactionRanking {

    /**
     * 顯示排行榜（給 RankingManager 呼叫）
     */
    public void show(L1PcInstance pc) {
        try {
            String[] data = TopFaction();

            // ⚠️ HTML名稱：ranking2-c.htm
            pc.sendPackets(new S_NPCTalkReturn(0, "ranking2", data));

        } catch (Exception e) {
            pc.sendPackets(new S_SystemMessage("陣營排行榜讀取失敗"));
            e.printStackTrace();
        }
    }

    /**
     * 取得排行榜資料
     */
    public static String[] TopFaction() {
        String[] result = new String[21];

        // 預設填空
        for (int i = 1; i <= 20; i++) {
            result[i] = "空缺";
        }

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
		

        try {
            con = DatabaseFactory.get().getConnection();

            String sql =
                "SELECT c.char_name, o.score " +
                "FROM character_other o " +
                "JOIN characters c ON c.objid = o.char_obj_id " +
                "ORDER BY o.score DESC " +
                "LIMIT 10";

            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();

            int rank = 1;

            while (rs.next() && rank <= 10) {
                String charName = rs.getString("char_name");
                int score = rs.getInt("score");

                // 名次1~10
                result[rank] = charName;

                // 分數11~20
                // 如果分數超過 99999，就只顯示 99999
                if (score > 99999) {
                    result[10 + rank] = "99999";
                } else {
                    result[10 + rank] = String.valueOf(score);
                }

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