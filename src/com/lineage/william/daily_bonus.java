package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class daily_bonus
{
    private static ArrayList<ArrayList<Object>> aData17; // 一般簽到
    private static ArrayList<ArrayList<Object>> aDataVip; // 高級簽到
    private static boolean NO_MORE_GET_DATA17 = false;
    private static boolean NO_MORE_GET_DATAVIP = false;
    public static final String TOKEN = ",";
    
    static {
        daily_bonus.aData17 = new ArrayList();
        daily_bonus.aDataVip = new ArrayList();
    }
    
    // 檢查是否為 VIP (設為 public 供外部呼叫)
    public static boolean isVip(int objId) {
        Connection con = null;
        Statement stat = null;
        ResultSet rset = null;
        try {
            con = DatabaseFactory.get().getConnection();
            stat = con.createStatement();
            rset = stat.executeQuery("SELECT * FROM character_vip WHERE char_obj_id = " + objId);
            if (rset.next()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        }
        return false;
    }

    // 取得一般簽到最大天數
    public static int getMaxDay() {
        if (!NO_MORE_GET_DATA17) {
            NO_MORE_GET_DATA17 = true;
            getData17();
        }
        int max = 0;
        for (int i = 0; i < aData17.size(); ++i) {
            ArrayList<?> temp = aData17.get(i);
            int day = (Integer) temp.get(0);
            if (day > max) max = day;
        }
        return max;
    }

    // 取得高級簽到最大天數 (設為 public)
    public static int getMaxVipDay() {
        if (!NO_MORE_GET_DATAVIP) {
            NO_MORE_GET_DATAVIP = true;
            getDataVip();
        }
        int max = 0;
        for (int i = 0; i < aDataVip.size(); ++i) {
            ArrayList<?> temp = aDataVip.get(i);
            int day = (Integer) temp.get(0);
            if (day > max) max = day;
        }
        return max;
    }

    private static void getData17() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_npc每日簽到控制");
            while (rset.next()) {
                ArrayList<Object> aReturn = new ArrayList();
                aReturn.add(0, rset.getInt("簽到幾天"));
                aReturn.add(1, getArray(rset.getString("簽到物品獎勵"), ",", 1));
                aReturn.add(2, getArray(rset.getString("簽到物品數量"), ",", 1));
                aReturn.add(3, rset.getString("完成簽到顯示"));
                aReturn.add(4, 0);
                aReturn.add(5, rset.getInt("永久物攻增加"));
                aReturn.add(6, rset.getInt("永久減傷增加"));
                aReturn.add(7, rset.getInt("永久血量增加"));
                aReturn.add(8, rset.getInt("永久魔量增加"));
                aData17.add(aReturn);
            }
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        } catch (Exception ex) {}
    }

    private static void getDataVip() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_npc每日高級簽到控制");
            while (rset.next()) {
                ArrayList<Object> aReturn = new ArrayList();
                aReturn.add(0, rset.getInt("簽到幾天"));
                aReturn.add(1, getArray(rset.getString("簽到物品獎勵"), ",", 1));
                aReturn.add(2, getArray(rset.getString("簽到物品數量"), ",", 1));
                aReturn.add(3, rset.getString("完成簽到顯示"));
                aReturn.add(4, 0);
                aReturn.add(5, rset.getInt("永久物攻增加"));
                aReturn.add(6, rset.getInt("永久減傷增加"));
                aReturn.add(7, rset.getInt("永久血量增加"));
                aReturn.add(8, rset.getInt("永久魔量增加"));
                aDataVip.add(aReturn);
            }
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        } catch (Exception ex) {}
    }

    // 一般簽到邏輯
    public static void dailybonus(L1PcInstance pc) {
        if (!NO_MORE_GET_DATA17) {
            NO_MORE_GET_DATA17 = true;
            getData17();
        }
        for (int i = 0; i < aData17.size(); ++i) {
            ArrayList<?> aTempData = aData17.get(i);
            if (pc.getday() == (Integer)aTempData.get(0)) {
                giveRewards(pc, aTempData);
                pc.getQuest().set_step(8991, 1);
                updateQuestNote(pc.getId(), 8991, "一般每日簽到");
                pc.sendPackets(new S_SystemMessage((String)aTempData.get(3)));
                applyPermanentBuff(pc, aTempData);
            }
        }
    }

    // 高級 VIP 簽到邏輯 (設為 public，並以 Quest 8993 記錄已領狀態，VIP天數由 8992 記錄)
    public static void dailybonusVip(L1PcInstance pc, int nextVipDay) {
        if (!NO_MORE_GET_DATAVIP) {
            NO_MORE_GET_DATAVIP = true;
            getDataVip();
        }
        for (int i = 0; i < aDataVip.size(); ++i) {
            ArrayList<?> aTempData = aDataVip.get(i);
            if (nextVipDay == (Integer)aTempData.get(0)) {
                giveRewards(pc, aTempData);
                pc.getQuest().set_step(8992, nextVipDay); // 用 8992 記錄 VIP 目前累積簽到天數
                pc.getQuest().set_step(8993, 1);       // 用 8993 記錄今日 VIP 是否已簽到
                updateQuestNote(pc.getId(), 8992, "高級VIP簽到天數");
                updateQuestNote(pc.getId(), 8993, "今日高級VIP已簽到");
                pc.sendPackets(new S_SystemMessage((String)aTempData.get(3)));
                applyPermanentBuff(pc, aTempData);
            }
        }
    }

    private static void updateQuestNote(int charId, int questId, String noteText) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE character_quests SET note = ? WHERE char_id = ? AND quest_id = ?");
            pstm.setString(1, noteText);
            pstm.setInt(2, charId);
            pstm.setInt(3, questId);
            pstm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    private static void giveRewards(L1PcInstance pc, ArrayList<?> aTempData) {
        int[] giveMaterials = (int[])aTempData.get(1);
        int[] giveCounts = (int[])aTempData.get(2);
        for (int l = 0; l < giveMaterials.length; ++l) {
            L1ItemInstance item = ItemTable.get().createItem(giveMaterials[l]);
            if (item != null) {
                item.setCount(item.isStackable() ? giveCounts[l] : 1L);
                if (pc.getInventory().checkAddItem(item, (long)giveCounts[l]) == 0) {
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                }
            }
        }
    }

    private static void applyPermanentBuff(L1PcInstance pc, ArrayList<?> aTempData) {
        if ((Integer)aTempData.get(5) != 0) pc.setpcdmg(pc.getpcdmg() + (Integer)aTempData.get(5));
        if ((Integer)aTempData.get(6) != 0) pc.setReductionDmg(pc.getReductionDmg() + (Integer)aTempData.get(6));
        if ((Integer)aTempData.get(7) != 0) pc.get_other().set_addhp(pc.get_other().get_addhp() + (Integer)aTempData.get(7));
        if ((Integer)aTempData.get(8) != 0) pc.get_other().set_addmp(pc.get_other().get_addmp() + (Integer)aTempData.get(8));
    }

    private static Object getArray(String s, String sToken, int iType) {
        StringTokenizer st = new StringTokenizer(s, sToken);
        int iSize = st.countTokens();
        if (iType == 1) {
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; ++i) iReturn[i] = Integer.parseInt(st.nextToken());
            return iReturn;
        }
        return null;
    }
}