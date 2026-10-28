package com.lineage.data.npc.quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lineage.DatabaseFactory;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.utils.SQLUtil;

/**
 * 四部位紋章排行榜（左上、右上、左下、右下）
 * action 指令：
 * emblem1 = 左上（300001-300500）
 * emblem2 = 右上（400001-400500）
 * emblem3 = 左下（500001-500500）
 * emblem4 = 右下（600001-600500）
 */
public class Npc_EmblemRanking extends NpcExecutor {

    private Npc_EmblemRanking() { }

    public static NpcExecutor get() {
        return new Npc_EmblemRanking();
    }

    @Override
    public int type() {
        return 3;
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rkemb"));
    }

    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {

        String[] rank;

        switch (cmd) {

            case "emblem1": // 左上
                rank = getRanking(31001, 31100);
                break;

            case "emblem2": // 右上
                rank = getRanking(400001, 400500);
                break;

            case "emblem3": // 左下
                rank = getRanking(500001, 500500);
                break;

            case "emblem4": // 右下
                rank = getRanking(600001, 600500);
                break;

            default:
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rkemb"));
                return;
        }

        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "rkemb2", rank));
    }


    /**
     * 取得前十名（玩家名 #1~10、紋章名稱 #11~20）
     */
    private String[] getRanking(int minId, int maxId) {

        // 0 不用，1~10 = 玩家名，11~20 = 紋章名
        String[] result = new String[21];

        for (int i = 1; i <= 20; i++) {
            result[i] = " ";
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DatabaseFactory.get().getConnection();

            String sql =
                "SELECT c.char_name, MAX(ci.item_id) AS max_emblem " +
                "FROM characters c " +
                "JOIN character_items ci ON c.objid = ci.char_id " +
                "WHERE ci.item_id BETWEEN ? AND ? " +
                "GROUP BY c.char_name " +
                "ORDER BY max_emblem DESC " +
                "LIMIT 10";

            ps = con.prepareStatement(sql);
            ps.setInt(1, minId);
            ps.setInt(2, maxId);
            rs = ps.executeQuery();

            int rank = 1;

            while (rs.next() && rank <= 10) {
                String playerName = rs.getString("char_name");
                int itemId = rs.getInt("max_emblem");
                String emblemName = getItemName(itemId);

                result[rank] = playerName;             // 玩家名
                result[10 + rank] = emblemName;        // 紋章名

                rank++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(rs, ps, con);
        }

        return result;
    }


    /**
     * 取得道具名稱（armor / etcitem 都支援）
     */
    private String getItemName(int itemId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String out = "未知紋章";

        try {
            con = DatabaseFactory.get().getConnection();

            ps = con.prepareStatement(
                "SELECT name FROM etcitem WHERE item_id=? " +
                "UNION SELECT name FROM armor WHERE item_id=?"
            );

            ps.setInt(1, itemId);
            ps.setInt(2, itemId);

            rs = ps.executeQuery();

            if (rs.next()) {
                out = rs.getString("name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(rs, ps, con);
        }

        return out;
    }
}
