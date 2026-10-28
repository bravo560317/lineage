package com.lineage.william;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lineage.DatabaseFactory;
import com.lineage.server.world.World;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.utils.SQLUtil;

public class Reward {

    private static final ArrayList<ArrayList<Object>> REWARD_LIST = new ArrayList<>();
    private static boolean LOADED = false;

    public static final String TOKEN = ",";

    /**
     * 玩家領取升級獎勵
     */
    public static synchronized void getItem(L1PcInstance pc) {

        if (!LOADED) {
            loadRewardData();
            LOADED = true;
        }

        for (ArrayList<Object> data : REWARD_LIST) {

            int needLevel  = (Integer) data.get(0);
            int giveRoyal  = (Integer) data.get(1);
            int giveKnight = (Integer) data.get(2);
            int giveMage   = (Integer) data.get(3);
            int giveElf    = (Integer) data.get(4);
            int giveDE     = (Integer) data.get(5);
            int giveDK     = (Integer) data.get(6);
            int giveIllu   = (Integer) data.get(7);

            int[] items  = (int[]) data.get(8);
            int[] counts = (int[]) data.get(9);
            int[] enchLv = (int[]) data.get(10);

            int questId   = (Integer) data.get(11);
            int questStep = (Integer) data.get(12);
            String message = (String) data.get(13);

            // 等級不足
            if (pc.getLevel() < needLevel) {
                continue;
            }

            // ★ 防止重複領取
            if (pc.getQuest().get_step(questId) >= questStep) {
                continue;
            }

            // 職業判斷
            if (!canReceive(pc, giveRoyal, giveKnight, giveMage, giveElf, giveDE, giveDK, giveIllu)) {
                continue;
            }

            // 再次確認 (雙重保護)
            if (pc.getQuest().get_step(questId) >= questStep) {
                continue;
            }

            // 發放物品
            giveRewardItems(pc, items, counts, enchLv, message);

            // 設定任務
            pc.getQuest().set_step(questId, questStep);

            // 寫入 NOTE
            writeQuestNote(pc.getId(), questId, questStep, message);
        }
    }

    /**
     * 職業判斷
     */
    private static boolean canReceive(L1PcInstance pc, int royal, int kn, int mage, int elf, int de, int dk, int illu) {

        if (royal != 0 && pc.isCrown()) return true;
        if (kn != 0 && pc.isKnight()) return true;
        if (mage != 0 && pc.isWizard()) return true;
        if (elf != 0 && pc.isElf()) return true;
        if (de != 0 && pc.isDarkelf()) return true;
        if (dk != 0 && pc.isDragonKnight()) return true;
        if (illu != 0 && pc.isIllusionist()) return true;

        return false;
    }

    /**
     * 發放獎勵
     */
    private static void giveRewardItems(L1PcInstance pc, int[] itemIds, int[] counts, int[] enchantLv, String message) {

        boolean showMsg = false;

        for (int i = 0; i < itemIds.length; i++) {

            L1ItemInstance item = ItemTable.get().createItem(itemIds[i]);
            if (item == null) {
                continue;
            }

            if (item.isStackable()) {
                item.setCount(counts[i]);
            } else {
                item.setCount(1);
            }

            if (item.getItem().getType2() == 1 || item.getItem().getType2() == 2) {
                item.setEnchantLevel(enchantLv[i]);
            } else {
                item.setEnchantLevel(0);
            }

            if (message != null && !showMsg) {
                pc.sendPackets(new S_SystemMessage(message));
                showMsg = true;
            }

            if (pc.getInventory().checkAddItem(item, item.getCount()) == 0) {
                pc.getInventory().storeItem(item);
            } else {
                World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
            }

            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
        }
    }

    /**
     * 寫入 quest note
     */
    private static void writeQuestNote(int charId, int questId, int questStep, String note) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DatabaseFactory.get().getConnection();

            ps = con.prepareStatement(
                    "SELECT quest_id FROM character_quests WHERE char_id=? AND quest_id=?");

            ps.setInt(1, charId);
            ps.setInt(2, questId);

            rs = ps.executeQuery();
            boolean exists = rs.next();

            rs.close();
            ps.close();

            if (exists) {

                ps = con.prepareStatement(
                        "UPDATE character_quests SET note=?, quest_step=? WHERE char_id=? AND quest_id=?");

                ps.setString(1, note);
                ps.setInt(2, questStep);
                ps.setInt(3, charId);
                ps.setInt(4, questId);

                ps.execute();

            } else {

                ps = con.prepareStatement(
                        "INSERT INTO character_quests (char_id, quest_id, quest_step, note, mob_count, clean) VALUES (?, ?, ?, ?, 0, 0)");

                ps.setInt(1, charId);
                ps.setInt(2, questId);
                ps.setInt(3, questStep);
                ps.setString(4, note);

                ps.execute();
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(con);
        }
    }

    /**
     * 載入資料
     */
    private static void loadRewardData() {

        Connection con = null;

        try {

            REWARD_LIST.clear();

            con = DatabaseFactory.get().getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM 系統_升級獎勵");

            while (rs.next()) {

                ArrayList<Object> row = new ArrayList<>();

                row.add(rs.getInt("level"));
                row.add(rs.getInt("give_royal"));
                row.add(rs.getInt("give_knight"));
                row.add(rs.getInt("give_mage"));
                row.add(rs.getInt("give_elf"));
                row.add(rs.getInt("give_darkelf"));
                row.add(rs.getInt("give_dragonKnight"));
                row.add(rs.getInt("give_illusionist"));

                row.add(getArray(rs.getString("getItem"), ",", 1));
                row.add(getArray(rs.getString("count"), ",", 1));
                row.add(getArray(rs.getString("enchantlvl"), ",", 1));

                row.add(rs.getInt("quest_id"));
                row.add(rs.getInt("quest_step"));

                row.add(rs.getString("message"));

                REWARD_LIST.add(row);
            }

            rs.close();
            st.close();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            SQLUtil.close(con);
        }
    }

    /**
     * 分割字串
     */
    private static Object getArray(String s, String token, int type) {

        StringTokenizer st = new StringTokenizer(s, token);
        int size = st.countTokens();

        if (type == 1) {

            int[] arr = new int[size];

            for (int i = 0; i < size; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            return arr;
        }

        if (type == 2) {

            String[] arr = new String[size];

            for (int i = 0; i < size; i++) {
                arr[i] = st.nextToken();
            }

            return arr;
        }

        return null;
    }
}