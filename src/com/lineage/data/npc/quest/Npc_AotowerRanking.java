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
 * 傲塔徽章排行榜
 * 顯示 玩家名稱 + 道具名稱
 */
public class Npc_AotowerRanking extends NpcExecutor {

    private Npc_AotowerRanking() {
    }

    public static NpcExecutor get() {
        return new Npc_AotowerRanking();
    }

    @Override
    public int type() {
        return 3;
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        // 打開初始視窗 (選單或提示)
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ranking3"));
    }

    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("aotower")) {
            try {
                String[] rank = getRanking();
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ranking3", rank));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查詢 armor + character_items，找出每位玩家最高的徽章
     */
private String[] getRanking() {
    String[] result = new String[21];
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // 初始化
        for (int i = 1; i <= 10; i++) {
            result[i] = "空缺";
        }

        con = DatabaseFactory.get().getConnection();
        String sql =
            "SELECT c.char_name, MAX(ci.item_id) AS max_badge " +
            "FROM character_items ci " +
            "JOIN characters c ON c.objid = ci.char_id " +
            "JOIN etcitem e ON ci.item_id = e.item_id " +
            "WHERE ci.item_id BETWEEN 101001 AND 101600 " +
            "GROUP BY c.char_name " +
            "ORDER BY max_badge DESC " +
            "LIMIT 10";

        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        int rank = 1;
        while (rs.next() && rank <= 10) {
            String charName = rs.getString("char_name");
            int badgeId = rs.getInt("max_badge");
            String badgeName = getItemName(badgeId);

            result[rank] = charName + " - " + badgeName;
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
 * 取 etcitem 表中的道具名稱（傲塔徽章）
 */
private String getItemName(int itemId) {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String name = "";

    try {
        con = DatabaseFactory.get().getConnection();
        ps = con.prepareStatement(
            "SELECT name FROM etcitem WHERE item_id=?"
        );
        ps.setInt(1, itemId);
        rs = ps.executeQuery();

        if (rs.next()) {
            name = rs.getString("name");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        SQLUtil.close(rs, ps, con);
    }
    return name;
}
}
