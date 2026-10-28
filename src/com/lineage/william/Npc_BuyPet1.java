package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Npc_BuyPet1 {

    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";

    static {
        aData = new ArrayList<>();
        NO_GET_DATA = false;
    }

    /**
     * 玩家輸入禮包碼
     */
    public static boolean forNpcQuest(String s, L1PcInstance pc) {

        if (!NO_GET_DATA) {
            NO_GET_DATA = true;
            getData();
        }

        for (int i = 0; i < aData.size(); i++) {

            ArrayList<?> row = aData.get(i);

            String code = (String)row.get(0);

            if (code != null && code.equals(s)) {

                int itemId = (Integer)row.get(1);
                int itemCount = (Integer)row.get(2);
                int quest_id = (Integer)row.get(3);
                String note = (String)row.get(4);   // ★ note 來源：顯示文字

                // 背包超過 160
                if (pc.getInventory().getSize() >= 160) {
                    pc.sendPackets(new S_ServerMessage("角色身上攜帶超過160個道具,功能無效。"));
                    return false;
                }

                // 已領取過
                if (pc.getQuest().get_step(quest_id) == 1) {
                    pc.sendPackets(new S_SystemMessage("該腳色已領取此禮包無法重複領取"));
                    return false;
                }

                // 領取禮包
                pc.getInventory().storeItem(itemId, itemCount);
                pc.getQuest().set_step(quest_id, 1);

                // ★★★★★ 寫入 note ★★★★★
                saveGiftNote(pc, quest_id, note);

                // 發送訊息
                if (note != null && !note.equals("")) {
                    pc.sendPackets(new S_SystemMessage("\\fY" + note));
                }
            }
        }

        return false;
    }

    /**
     * ★★★★★★ 寫入 character_quests.note ★★★★★★
     */
    private static void saveGiftNote(L1PcInstance pc, int quest_id, String note) {

        if (note == null || note.trim().equals("")) return;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DatabaseFactory.get().getConnection();
            ps = con.prepareStatement(
                "UPDATE character_quests SET note=? WHERE char_id=? AND quest_id=?"
            );

            ps.setString(1, note);
            ps.setInt(2, pc.getId());
            ps.setInt(3, quest_id);

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(ps);
            SQLUtil.close(con);
        }
    }


    /**
     * 載入資料
     */
    private static void getData() {

        Connection con = null;

        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM 系統_輸入禮包碼");

            while (rs.next()) {

                ArrayList<Object> row = new ArrayList<>();

                row.add(0, rs.getString("禮包碼"));
                row.add(1, rs.getInt("給予道具"));
                row.add(2, rs.getInt("給予道具數量"));
                row.add(3, rs.getInt("紀錄領取編號"));
                row.add(4, rs.getString("顯示文字"));  // ★ note 來源

                aData.add(row);
            }

        } catch (Exception ignored) {}
        finally {
            SQLUtil.close(con);
        }
    }
}
