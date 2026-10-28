package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.*;
import com.lineage.server.utils.SQLUtil;
import com.lineage.Server;

import java.util.ArrayList;

public class L1Blend_buff {

    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static L1Blend_buff _instance;

    public static final String TOKEN = ",";

    static {
        aData = new ArrayList<>();
        BUILD_DATA = false;
    }

    public static L1Blend_buff getInstance() {
        if (_instance == null) {
            _instance = new L1Blend_buff();
        }
        return _instance;
    }

    public static void main(String[] a) {
        while (true) {
            try {
                Server.main(null);
            } catch (Exception ex) {}
        }
    }

    //====================================================
    // ★ 成就加成邏輯 + 寫入成就 note
    //====================================================
    public static void forIntensifyArmor(L1PcInstance pc) {

        if (!BUILD_DATA) {
            BUILD_DATA = true;
            getData();
        }

        for (ArrayList<Object> data : aData) {

            int questId = (Integer) data.get(0);

            // 玩家該成就是 step=2 → 已擁有成就 (與 CompleteQuest.java 統一)
            if (pc.getQuest().get_step(questId) == 2) {

                // ★ 取得資料表 note
                String note = (String) data.get(39);

                // ★ 寫入成就 note
                writeQuestNote(pc.getId(), questId, note);
            }
        }
    }

    //====================================================
    // ★ 寫入成就 note 至 character_quests
    //====================================================
    private static void writeQuestNote(int charId, int questId, String note) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DatabaseFactory.get().getConnection();

            ps = con.prepareStatement("SELECT quest_id FROM character_quests WHERE char_id=? AND quest_id=?");
            ps.setInt(1, charId);
            ps.setInt(2, questId);
            rs = ps.executeQuery();

            boolean exist = rs.next();
            SQLUtil.close(rs);
            SQLUtil.close(ps);

            if (exist) {
                ps = con.prepareStatement("UPDATE character_quests SET note=? WHERE char_id=? AND quest_id=?");
                ps.setString(1, note);
                ps.setInt(2, charId);
                ps.setInt(3, questId);
                ps.execute();

            } else {
                ps = con.prepareStatement(
                        "INSERT INTO character_quests (char_id, quest_id, quest_step, note, mob_count, clean) VALUES (?, ?, ?, ?, 0, 0)");
                ps.setInt(1, charId);
                ps.setInt(2, questId);
                ps.setInt(3, 2); // 統一使用 2 代表完成
                ps.setString(4, note);
                ps.execute();
            }

        } catch (Exception e) {
            System.out.println("writeQuestNote error: " + e.getLocalizedMessage());
        } finally {
            SQLUtil.close(ps);
            SQLUtil.close(con);
        }
    }

    //====================================================
    // ★ 讀取系統成就表（新增 note）
    //====================================================
    private static void getData() {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseFactory.get().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM w_npc成就圖鑑獎勵");
            rs = pstmt.executeQuery();

            while (rs.next()) {

                ArrayList<Object> list = new ArrayList<>();

                list.add(0, rs.getInt("quest"));
                list.add(1, 0);
                list.add(2, rs.getInt("Attack"));
                list.add(3, rs.getInt("BowAttack"));
                list.add(4, rs.getInt("Hit"));
                list.add(5, rs.getInt("BowHit"));
                list.add(6, rs.getInt("Sp"));
                list.add(7, rs.getInt("Str"));
                list.add(8, rs.getInt("Dex"));
                list.add(9, rs.getInt("Int"));
                list.add(10, rs.getInt("Con"));
                list.add(11, rs.getInt("Cha"));
                list.add(12, rs.getInt("Wis"));
                list.add(13, rs.getInt("Hp"));
                list.add(14, rs.getInt("Mp"));
                list.add(15, rs.getInt("Mr"));
                list.add(16, rs.getInt("ReductionDmg"));
                list.add(17, rs.getInt("Hpr"));
                list.add(18, rs.getInt("Mpr"));
                list.add(19, rs.getInt("hppotion"));
                list.add(20, rs.getInt("exp"));
                list.add(21, rs.getInt("ac"));
                list.add(22, rs.getInt("weight"));
                list.add(23, rs.getInt("regist_stun"));
                list.add(24, rs.getInt("regist_stone"));
                list.add(25, rs.getInt("regist_sleep"));
                list.add(26, rs.getInt("regist_freeze"));
                list.add(27, rs.getInt("regist_sustain"));
                list.add(28, rs.getInt("regist_blind"));
                list.add(29, rs.getInt("PvP_dmg"));
                list.add(30, rs.getInt("PvP_hit"));
                list.add(31, rs.getInt("PvP_bow_dmg"));
                list.add(32, rs.getInt("PvP_bow_hit"));
                list.add(33, rs.getInt("PvP_dmg_r"));
                list.add(34, rs.getInt("PvP_magic_r"));
                list.add(35, rs.getDouble("PvP_dmg_b"));
                list.add(36, rs.getDouble("PvP_magic_b"));
                list.add(37, rs.getInt("Ran_PvP_dmg_b"));
                list.add(38, rs.getInt("Ran_PvP_magic_b"));

                // ★★★ 新增：資料表裡面的 note ★★★
                list.add(39, rs.getString("note"));

                aData.add(list);
            }

        } catch (SQLException ex) {
            System.out.println("getData error: " + ex.getLocalizedMessage());
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstmt);
            SQLUtil.close(conn);
        }
    }
}
