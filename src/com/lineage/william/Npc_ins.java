package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import java.util.Calendar;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class Npc_ins
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    
    static {
        Npc_ins.aData = new ArrayList();
        Npc_ins.NO_GET_DATA = false;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        ArrayList aTempData = null;
        if (!Npc_ins.NO_GET_DATA) {
            Npc_ins.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < Npc_ins.aData.size(); ++i) {
            aTempData = Npc_ins.aData.get(i);
            if (aTempData.get(0) != null && (Integer)aTempData.get(0) == npcid && ((String)aTempData.get(1)).equals(s) && (((int[])aTempData.get(11) != null && (int[])aTempData.get(12) != null && !pc.getInventory().checkItem((int[])aTempData.get(11), (int[])aTempData.get(12))) || ((int[])aTempData.get(11) == null && (int[])aTempData.get(12) == null)) && (((int[])aTempData.get(9) != null && (int[])aTempData.get(10) != null && pc.getInventory().checkItem((int[])aTempData.get(9), (int[])aTempData.get(10))) || ((int[])aTempData.get(9) == null && (int[])aTempData.get(10) == null))) {
                    // ===== 就加在這裡 =====
    int needQuestId = (Integer)aTempData.get(7);
    int needQuestStep = (Integer)aTempData.get(8);

    if (needQuestId > 0) {
        if (pc.getQuest().get_step(needQuestId) != needQuestStep) {
            pc.sendPackets(new S_SystemMessage("尚未完成前置任務"));
            return false;
        }
    }
    // ===== 加到這裡結束 =====
				if (aTempData.get(0) != null && (Integer)aTempData.get(0) == 220179 && ((String)aTempData.get(1)).equals(s) && !pc.getDolls().isEmpty()) {
                    pc.sendPackets(new S_ServerMessage("請先解除魔法娃娃。"));
                    return false;
                }
                else {
                    int week = (Integer)aTempData.get(29);
                    int starttime = (Integer)aTempData.get(30);
                    int unitytime = (Integer)aTempData.get(31);
                    Calendar date = Calendar.getInstance();
                    int nowWeek = date.get(7) - 1;
                    int nowHour = date.get(11);
                    if (nowWeek == 0) {
                        nowWeek += 7;
                    }
                    if (week > 0 && starttime < 0 && unitytime < 0) {
                        if (week != nowWeek) {
                            pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【" + week + " 】"));
                            return false;
                        }
                    }
                    else if (week > 0 && starttime >= 0 && unitytime >= 0) {
                        if (week != nowWeek || nowHour < starttime || nowHour >= unitytime) {
                            pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【 " + week + " 】時間為【 " + starttime + " 】點，至【 " + unitytime + "】點"));
                            return false;
                        }
                    }
                    else if (week < 0 && starttime >= 0 && unitytime >= 0 && (nowHour < starttime || nowHour >= unitytime)) {
                        pc.sendPackets(new S_ServerMessage(166, "使用時間限制:時間為【 " + starttime + " 】點，至【 " + unitytime + "】點"));
                        return false;
                    }
                    if ((Integer)aTempData.get(3) != 0) {
                        byte class_id = 0;
                        String msg = "";
                        if (pc.isCrown()) {
                            class_id = 1;
                        }
                        else if (pc.isKnight()) {
                            class_id = 2;
                        }
                        else if (pc.isWizard()) {
                            class_id = 3;
                        }
                        else if (pc.isElf()) {
                            class_id = 4;
                        }
                        else if (pc.isDarkelf()) {
                            class_id = 5;
                        }
                        else if (pc.isDragonKnight()) {
                            class_id = 6;
                        }
                        else if (pc.isIllusionist()) {
                            class_id = 7;
                        }
                        switch ((Integer)aTempData.get(3)) {
                            case 1: {
                                msg = "王族";
                                break;
                            }
                            case 2: {
                                msg = "騎士";
                                break;
                            }
                            case 3: {
                                msg = "法師";
                                break;
                            }
                            case 4: {
                                msg = "妖精";
                                break;
                            }
                            case 5: {
                                msg = "黑暗妖精";
                                break;
                            }
                            case 6: {
                                msg = "龍騎士";
                                break;
                            }
                            case 7: {
                                msg = "幻術士";
                                break;
                            }
                        }
                        if ((int)(Integer)aTempData.get(3) != class_id) {
                            pc.sendPackets(new S_SystemMessage("你的職業無法使用" + msg + "的專屬道具。"));
                            return false;
                        }
                    }
                    if ((Integer)aTempData.get(0) == npcid && ((String)aTempData.get(1)).equals(s) && pc.getQuest().get_step((Integer)aTempData.get(5)) == 255) {
                        pc.sendPackets(new S_SystemMessage("您已經執行過上限1次"));
                        return false;
                    }
// ===== 1. 地圖任務限制 (9501~9520)：只有玩家要接地圖任務時，才會檢查是否有其他地圖任務在進行 =====
else if (((Integer)aTempData.get(18) >= 9501 && (Integer)aTempData.get(18) <= 9520) &&
    (pc.getQuest().get_step(9501) == 1 || pc.getQuest().get_step(9502) == 1 || pc.getQuest().get_step(9503) == 1 || pc.getQuest().get_step(9504) == 1 || pc.getQuest().get_step(9505) == 1 || pc.getQuest().get_step(9506) == 1 || pc.getQuest().get_step(9507) == 1 || pc.getQuest().get_step(9508) == 1 || pc.getQuest().get_step(9509) == 1 || pc.getQuest().get_step(9510) == 1 || pc.getQuest().get_step(9511) == 1 || pc.getQuest().get_step(9512) == 1 || pc.getQuest().get_step(9513) == 1 || pc.getQuest().get_step(9514) == 1 || pc.getQuest().get_step(9515) == 1 || pc.getQuest().get_step(9516) == 1 || pc.getQuest().get_step(9517) == 1 || pc.getQuest().get_step(9518) == 1 || pc.getQuest().get_step(9519) == 1 || pc.getQuest().get_step(9520) == 1)) {
    pc.sendPackets(new S_SystemMessage("您目前有地圖任務進行中,請先完成在接下個任務。。"));
    return false;
}

// ===== 2. 主線任務限制 (9001~9100)：只有玩家要接主線任務時，才會檢查是否有其他主線任務在進行 =====
else if ((Integer)aTempData.get(35) == 0 && (Integer)aTempData.get(6) != 255 && 
    ((Integer)aTempData.get(18) >= 9001 && (Integer)aTempData.get(18) <= 9100) &&
    (pc.getQuest().get_step(9001) == 1 || pc.getQuest().get_step(9002) == 1 || pc.getQuest().get_step(9003) == 1 || pc.getQuest().get_step(9004) == 1 || pc.getQuest().get_step(9005) == 1 || pc.getQuest().get_step(9006) == 1 || pc.getQuest().get_step(9007) == 1 || pc.getQuest().get_step(9008) == 1 || pc.getQuest().get_step(9009) == 1 || pc.getQuest().get_step(9010) == 1 || pc.getQuest().get_step(9011) == 1 || pc.getQuest().get_step(9012) == 1 || pc.getQuest().get_step(9013) == 1 || pc.getQuest().get_step(9014) == 1 || pc.getQuest().get_step(9015) == 1 || pc.getQuest().get_step(9016) == 1 || pc.getQuest().get_step(9017) == 1 || pc.getQuest().get_step(9018) == 1 || pc.getQuest().get_step(9019) == 1 || pc.getQuest().get_step(9020) == 1 || pc.getQuest().get_step(9021) == 1 || pc.getQuest().get_step(9022) == 1 || pc.getQuest().get_step(9023) == 1 || pc.getQuest().get_step(9024) == 1 || pc.getQuest().get_step(9025) == 1 || pc.getQuest().get_step(9026) == 1 || pc.getQuest().get_step(9027) == 1 || pc.getQuest().get_step(9028) == 1 || pc.getQuest().get_step(9029) == 1 || pc.getQuest().get_step(9030) == 1)) {
    pc.sendPackets(new S_SystemMessage("您目前有主線任務進行中,請先完成在接下個任務。。"));
    return false;
}
                    else if ((Integer)aTempData.get(6) != 0 && pc.getQuest().get_step((Integer)aTempData.get(18)) == (Integer)aTempData.get(6)) {
                        pc.sendPackets(new S_SystemMessage("您已經執行過上限" + (int)(Integer)aTempData.get(6) + "次。"));
                        return false;
                    }
                    else if (pc.getMapId() == 8544 || pc.getMapId() == 99 || (pc.getMapId() >= 8700 && pc.getMapId() <= 8726)) {
                        pc.sendPackets(new S_ServerMessage(166, "\\fU此地區無法進行!!"));
                        return false;
                    }
                    else if (pc.getLevel() >= (Integer)aTempData.get(2) && pc.getLevel() <= (Integer)aTempData.get(32)) {
                        boolean isGet = false;
                        boolean isCreate = true;
                        if ((int[])aTempData.get(13) != null && (int[])aTempData.get(14) != null) {
                            int[] materials = (int[])aTempData.get(13);
                            int[] counts = (int[])aTempData.get(14);
                            for (int j = 0; j < materials.length; ++j) {
                                if (!pc.getInventory().checkItem(materials[j], (long)counts[j])) {
                                    L1Item temp = ItemTable.get().getTemplate(materials[j]);
                                    pc.sendPackets(new S_ServerMessage(337, temp.getName() + "(" + ((long)counts[j] - pc.getInventory().countItems(temp.getItemId())) + ")"));
                                    isCreate = false;
                                }
                            }
                            if (!isCreate) {
                                if ((String[])aTempData.get(23) != null) {
                                    pc.sendPackets(new S_NPCTalkReturn(oid, (String)aTempData.get(22), (String[])aTempData.get(23)));
                                }
                                else if (aTempData.get(22) != null) {
                                    pc.sendPackets(new S_NPCTalkReturn(oid, (String)aTempData.get(22)));
                                }
                                return true;
                            }
                            else if (isCreate && (Integer)aTempData.get(15) == 0 && (Integer)aTempData.get(34) == 1) {
                                for (int k = 0; k < materials.length; ++k) {
                                    pc.getInventory().consumeItem(materials[k], (long)counts[k]);
                                }
                            }
                        }
                        if ((Integer)aTempData.get(15) == 0 && (int[])aTempData.get(16) != null && (int[])aTempData.get(17) != null) {
                            int[] giveMaterials = (int[])aTempData.get(16);
                            int[] giveCounts = (int[])aTempData.get(17);
                            int l = 0;
                            while (l < giveMaterials.length) {
                                L1ItemInstance item = ItemTable.get().createItem(giveMaterials[l]);
                                if (item.getBless() >= 4) {
                                    pc.sendPackets(new S_SystemMessage("該裝備受到封印卷軸的影響。"));
                                    return false;
                                }
                                else {
                                    if (item.isStackable()) {
                                        item.setCount((long)giveCounts[l]);
                                    }
                                    else {
                                        item.setCount(1L);
                                    }
                                    if (item != null) {
                                        isGet = true;
                                        if (pc.getInventory().checkAddItem(item, (long)giveCounts[l]) != 0) {
                                            return false;
                                        }
                                        else {
                                            pc.getInventory().storeItem(item);
                                            pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                                        }
                                    }
                                    ++l;
                                }
                            }
                        }
int questId = (Integer)aTempData.get(18);
int questStep = (Integer)aTempData.get(19);

pc.getQuest().set_step(questId, questStep);

// ✅ 新增：任務啟動時寫入 note（只寫一次）
if ((String)aTempData.get(28) != null) {
    updateQuestNote(pc, questId, (String)aTempData.get(28));
}

if ((String)aTempData.get(28) != null) {
    pc.sendPackets(new S_SystemMessage("\\fY" + (String)aTempData.get(28)));
}
                        if ((Integer)aTempData.get(27) != 0 && (isGet || isCreate)) {
                            pc.get_other().set_usemap(-1);
                            pc.get_other().set_usemapTime(0);
                            L1Teleport.teleport(pc, (Integer)aTempData.get(24), (Integer)aTempData.get(25), (short)(int)(Integer)aTempData.get(26), 5, true);
                            pc.get_other().set_usemap((Integer)aTempData.get(26));
                            ServerUseMapTimer.put(pc, (Integer)aTempData.get(27));
                            pc.sendPackets(new S_ServerMessage("時間限制:" + (int)(Integer)aTempData.get(27) + "秒"));
                        }
                        else if ((Integer)aTempData.get(27) == 0 && (Integer)aTempData.get(24) > 0 && (isGet || isCreate)) {
                            L1Teleport.teleport(pc, (Integer)aTempData.get(24), (Integer)aTempData.get(25), (short)(int)(Integer)aTempData.get(26), 5, true);
                        }
                        if ((Integer)aTempData.get(33) > 0) {
                            pc.addExp((long)(int)(Integer)aTempData.get(33));
                        }
                        if ((String[])aTempData.get(21) != null && (isGet || isCreate)) {
                            pc.sendPackets(new S_NPCTalkReturn(oid, (String)aTempData.get(20), (String[])aTempData.get(21)));
                            return true;
                        }
                        else if (aTempData.get(20) != null && (isGet || isCreate)) {
                            pc.sendPackets(new S_NPCTalkReturn(oid, (String)aTempData.get(20)));
                            return true;
                        }
                        else {
                            pc.sendPackets(new S_NPCTalkReturn(oid, ""));
                            return true;
                        }
                    }
                    else {
                        pc.sendPackets(new S_SystemMessage("等級最低需求:" + (int)(Integer)aTempData.get(2)));
                        pc.sendPackets(new S_SystemMessage("等級最高需求:" + (int)(Integer)aTempData.get(32)));
                    }
                }
            }
        }
        return false;
    }
    
    private static void getData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_npc多項功能");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("npcid")));
                    sTemp = rset.getString("action");
                    aReturn.add(1, sTemp);
                    aReturn.add(2, rset.getInt("checkLevel_min"));
                    aReturn.add(3, rset.getInt("checkClass"));
                    aReturn.add(4, new Integer(rset.getInt("checkPoly")));
                    aReturn.add(5, new Integer(rset.getInt("check_QuestId")));
                    aReturn.add(6, new Integer(rset.getInt("check_Quest_set")));
                    aReturn.add(7, new Integer(rset.getInt("notHaveQuestId")));
                    aReturn.add(8, new Integer(rset.getInt("notHaveQuestOrder")));
                    if (rset.getString("checkItem") != null && !rset.getString("checkItem").equals("") && !rset.getString("checkItem").equals("0")) {
                        aReturn.add(9, getArray(rset.getString("checkItem"), ",", 1));
                    }
                    else {
                        aReturn.add(9, null);
                    }
                    if (rset.getString("checkItemCount") != null && !rset.getString("checkItemCount").equals("") && !rset.getString("checkItemCount").equals("0")) {
                        aReturn.add(10, getArray(rset.getString("checkItemCount"), ",", 1));
                    }
                    else {
                        aReturn.add(10, null);
                    }
                    if (rset.getString("notHaveItem") != null && !rset.getString("notHaveItem").equals("") && !rset.getString("notHaveItem").equals("0")) {
                        aReturn.add(11, getArray(rset.getString("notHaveItem"), ",", 1));
                    }
                    else {
                        aReturn.add(11, null);
                    }
                    if (rset.getString("notHaveItemCount") != null && !rset.getString("notHaveItemCount").equals("") && !rset.getString("notHaveItemCount").equals("0")) {
                        aReturn.add(12, getArray(rset.getString("notHaveItemCount"), ",", 1));
                    }
                    else {
                        aReturn.add(12, null);
                    }
                    if (rset.getString("material") != null && !rset.getString("material").equals("") && !rset.getString("material").equals("0")) {
                        aReturn.add(13, getArray(rset.getString("material"), ",", 1));
                    }
                    else {
                        aReturn.add(13, null);
                    }
                    if (rset.getString("materialCount") != null && !rset.getString("materialCount").equals("") && !rset.getString("materialCount").equals("0")) {
                        aReturn.add(14, getArray(rset.getString("materialCount"), ",", 1));
                    }
                    else {
                        aReturn.add(14, null);
                    }
                    aReturn.add(15, new Integer(rset.getInt("justCheckMaterial")));
                    if (rset.getString("GiveItem") != null && !rset.getString("GiveItem").equals("") && !rset.getString("GiveItem").equals("0")) {
                        aReturn.add(16, getArray(rset.getString("GiveItem"), ",", 1));
                    }
                    else {
                        aReturn.add(16, null);
                    }
                    if (rset.getString("GiveItemCount") != null && !rset.getString("GiveItemCount").equals("") && !rset.getString("GiveItemCount").equals("0")) {
                        aReturn.add(17, getArray(rset.getString("GiveItemCount"), ",", 1));
                    }
                    else {
                        aReturn.add(17, null);
                    }
                    aReturn.add(18, new Integer(rset.getInt("save_QuestId")));
                    aReturn.add(19, new Integer(rset.getInt("save_Questset")));
                    if (rset.getString("ShowHtml") != null && !rset.getString("ShowHtml").equals("")) {
                        aReturn.add(20, new String(rset.getString("ShowHtml")));
                    }
                    else {
                        aReturn.add(20, null);
                    }
                    if (rset.getString("ShowHtmlData") != null && !rset.getString("ShowHtmlData").equals("")) {
                        aReturn.add(21, getArray(rset.getString("ShowHtmlData"), ",", 2));
                    }
                    else {
                        aReturn.add(21, null);
                    }
                    if (rset.getString("ShowNotHaveHtml") != null && !rset.getString("ShowNotHaveHtml").equals("")) {
                        aReturn.add(22, new String(rset.getString("ShowNotHaveHtml")));
                    }
                    else {
                        aReturn.add(22, null);
                    }
                    if (rset.getString("ShowNotHaveHtmlData") != null && !rset.getString("ShowNotHaveHtmlData").equals("")) {
                        aReturn.add(23, getArray(rset.getString("ShowNotHaveHtmlData"), ",", 2));
                    }
                    else {
                        aReturn.add(23, null);
                    }
                    aReturn.add(24, new Integer(rset.getInt("tele_x")));
                    aReturn.add(25, new Integer(rset.getInt("tele_y")));
                    aReturn.add(26, new Integer(rset.getInt("tele_mapid")));
                    aReturn.add(27, new Integer(rset.getInt("time")));
                    aReturn.add(28, rset.getString("pc_message"));
                    aReturn.add(29, new Integer(rset.getInt("week")));
                    aReturn.add(30, new Integer(rset.getInt("startime")));
                    aReturn.add(31, new Integer(rset.getInt("endtime")));
                    aReturn.add(32, rset.getInt("checkLevel_max"));
                    aReturn.add(33, rset.getInt("addexp"));
                    aReturn.add(34, rset.getInt("刪除觸發道具"));
                    aReturn.add(35, rset.getInt("questend"));
                    Npc_ins.aData.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
    }
    private static void updateQuestNote(L1PcInstance pc, int questId, String note) {
    Connection cn = null;
    Statement ps = null;

    try {
        cn = DatabaseFactory.get().getConnection();
        ps = cn.createStatement();

        String sql = "UPDATE character_quests "
                   + "SET note='" + note + "' "
                   + "WHERE char_id=" + pc.getId()
                   + " AND quest_id=" + questId;

        ps.executeUpdate(sql);

    } catch (Exception e) {
        // 這裡不丟訊息給玩家，只記 log
    } finally {
        try {
            if (ps != null) ps.close();
            if (cn != null) cn.close();
        } catch (Exception e) {}
    }
}

    private static Object getArray(String s, String sToken, int iType) {
        StringTokenizer st = new StringTokenizer(s, sToken);
        int iSize = st.countTokens();
        String sTemp = null;
        if (iType == 1) {
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                iReturn[i] = Integer.parseInt(sTemp);
            }
            return iReturn;
        }
        else if (iType == 2) {
            String[] sReturn = new String[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                sReturn[i] = sTemp;
            }
            return sReturn;
        }
        else if (iType == 3) {
            String sReturn2 = null;
            for (int i = 0; i < iSize; ++i) {
                sTemp = (sReturn2 = st.nextToken());
            }
            return sReturn2;
        }
        else {
            return null;
        }
    }
}
