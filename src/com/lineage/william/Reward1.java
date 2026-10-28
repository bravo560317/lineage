package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;
import com.lineage.Server;

import java.util.ArrayList;

public class Reward1 {

    private static ArrayList<ArrayList<Object>> array = new ArrayList<>();
    private static boolean GET_ITEM = false;
    public static final String TOKEN = ",";

    public static void main(String[] a) {
        try {
            Server.main((String[]) null);
        } catch (Exception ex) {
        }
    }

    // ========================================================================
    // ★ 主流程：發放升級獎勵（合併七段職業程式碼）
    // ========================================================================
    public static void getItem(L1PcInstance pc) {

        if (!GET_ITEM) {
            GET_ITEM = true;
            getItemData();
        }

        for (ArrayList<Object> data : array) {

            int needLevel = (Integer) data.get(0);
            int giveCrown = (Integer) data.get(1);
            int giveKnight = (Integer) data.get(2);
            int giveMage = (Integer) data.get(3);
            int giveElf = (Integer) data.get(4);
            int giveDarkelf = (Integer) data.get(5);
            int giveDragonKnight = (Integer) data.get(6);
            int giveIllusionist = (Integer) data.get(7);

            int[] materials = (int[]) data.get(8);
            int[] counts = (int[]) data.get(9);
            int[] enchantLevel = (int[]) data.get(10);

            int questId = (Integer) data.get(11);
            int questStep = (Integer) data.get(12);
            String msg = (String) data.get(13);

            // 條件：等級不足 OR 任務已領取 → 跳過
            if (pc.getLevel() < needLevel)
                continue;

            if (pc.getQuest().get_step(questId) == questStep)
                continue;

            // 配對職業
            boolean matchClass =
                    (pc.isCrown() && giveCrown != 0) ||
                    (pc.isKnight() && giveKnight != 0) ||
                    (pc.isWizard() && giveMage != 0) ||
                    (pc.isElf() && giveElf != 0) ||
                    (pc.isDarkelf() && giveDarkelf != 0) ||
                    (pc.isDragonKnight() && giveDragonKnight != 0) ||
                    (pc.isIllusionist() && giveIllusionist != 0);

            if (!matchClass)
                continue;

            // ====================================================================
            // ★ 發放獎勵（單一流程）
            // ====================================================================
            boolean sendMsgOnce = false;

            for (int i = 0; i < materials.length; i++) {

                L1ItemInstance item = ItemTable.get().createItem(materials[i]);
                if (item == null)
                    continue;

                // 設定數量
                if (item.isStackable())
                    item.setCount(counts[i]);
                else
                    item.setCount(1);

                // 設定強化值
                if (item.getItem().getType2() == 1 || item.getItem().getType2() == 2)
                    item.setEnchantLevel(enchantLevel[i]);
                else
                    item.setEnchantLevel(0);

                // ★ 自動鑑定
                item.setIdentified(true);

                // 提示訊息（每次任務只提示一次）
                if (!sendMsgOnce && msg != null) {
                    pc.sendPackets(new S_SystemMessage(msg));
                    sendMsgOnce = true;
                }

                // 加進背包
                if (pc.getInventory().checkAddItem(item, item.getCount()) == 0) {
                    pc.getInventory().storeItem(item);
                } else {
                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                }

                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
            }

            // ====================================================================
            // ★ 設定任務步驟
            // ====================================================================
            pc.getQuest().set_step(questId, questStep);

            // ====================================================================
            // ★ 寫入 NOTE（記錄 message）
            // ====================================================================
            writeNote(pc, questId, msg);
        }
    }

    // ========================================================================
    // ★ 寫入 character_quests.note
    // ========================================================================
    private static void writeNote(L1PcInstance pc, int questId, String note) {

        if (note == null)
            note = "";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DatabaseFactory.get().getConnection();
            ps = con.prepareStatement(
                    "UPDATE character_quests SET note = ? WHERE char_id = ? AND quest_id = ?"
            );
            ps.setString(1, note);
            ps.setInt(2, pc.getId());
            ps.setInt(3, questId);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(ps);
            SQLUtil.close(con);
        }
    }

    // ========================================================================
    // ★ 讀取系統_升級獎勵_任務
    // ========================================================================
    private static void getItemData() {

        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM 系統_升級獎勵_任務");

            while (rset.next()) {
                ArrayList<Object> list = new ArrayList<>();

                list.add(0, rset.getInt("level"));
                list.add(1, rset.getInt("give_royal"));
                list.add(2, rset.getInt("give_knight"));
                list.add(3, rset.getInt("give_mage"));
                list.add(4, rset.getInt("give_elf"));
                list.add(5, rset.getInt("give_darkelf"));
                list.add(6, rset.getInt("give_dragonKnight"));
                list.add(7, rset.getInt("give_illusionist"));

                list.add(8, getArray(rset.getString("getItem"), ",", 1));
                list.add(9, getArray(rset.getString("count"), ",", 1));
                list.add(10, getArray(rset.getString("enchantlvl"), ",", 1));

                list.add(11, rset.getInt("quest_id"));
                list.add(12, rset.getInt("quest_step"));
                list.add(13, rset.getString("message"));

                array.add(list);
            }

        } catch (Exception ignored) {
        } finally {
            SQLUtil.close(con);
        }
    }

    // ========================================================================
    // ★ 字串轉 int[] / String[]
    // ========================================================================
    private static Object getArray(String s, String token, int type) {

        StringTokenizer st = new StringTokenizer(s, token);
        int size = st.countTokens();

        if (type == 1) { // int[]
            int[] arr = new int[size];
            for (int i = 0; i < size; i++)
                arr[i] = Integer.parseInt(st.nextToken());
            return arr;
        }

        if (type == 2) { // String[]
            String[] arr = new String[size];
            for (int i = 0; i < size; i++)
                arr[i] = st.nextToken();
            return arr;
        }

        if (type == 3) { // 最後一個字串
            String last = "";
            while (st.hasMoreTokens())
                last = st.nextToken();
            return last;
        }

        return null;
    }
}
