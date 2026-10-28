package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class ItemUse
{
    private static ArrayList<ArrayList<Object>> aData15b;
    private static boolean NO_MORE_GET_DATA15b;
    public static final String TOKEN = ",";
    
    static {
        ItemUse.aData15b = new ArrayList();
        ItemUse.NO_MORE_GET_DATA15b = false;
    }
    
    public static void main(String[] a) {
        try {
            Server.main((String[])null);
        }
        catch (Exception ex) {}
    }
    
    public static void forItemUSe(L1PcInstance user, L1ItemInstance itemInstance) {
        int itemid = itemInstance.getItemId();
        ArrayList<?> aTempData = null;
        if (!ItemUse.NO_MORE_GET_DATA15b) {
            ItemUse.NO_MORE_GET_DATA15b = true;
            getData15b();
        }
        int i = 0;
        while (i < ItemUse.aData15b.size()) {
            aTempData = ItemUse.aData15b.get(i);
            if ((Integer)aTempData.get(0) == itemid) {
                if ((Integer)aTempData.get(1) != 0) {
                    byte class_id = 0;
                    String msg = "";
                    if (user.isCrown()) {
                        class_id = 1;
                    }
                    else if (user.isKnight()) {
                        class_id = 2;
                    }
                    else if (user.isWizard()) {
                        class_id = 3;
                    }
                    else if (user.isElf()) {
                        class_id = 4;
                    }
                    else if (user.isDarkelf()) {
                        class_id = 5;
                    }
                    else if (user.isDragonKnight()) {
                        class_id = 6;
                    }
                    else if (user.isIllusionist()) {
                        class_id = 7;
                    }
                    switch ((Integer)aTempData.get(1)) {
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
                            msg = "黑妖";
                            break;
                        }
                        case 6: {
                            msg = "龍騎";
                            break;
                        }
                        case 7: {
                            msg = "幻術";
                            break;
                        }
                    }
                    if ((int)(Integer)aTempData.get(1) != class_id) {
                        user.sendPackets(new S_SystemMessage("你的職業無法使用" + msg + "的專屬道具。"));
                        return;
                    }
                }
                if ((Integer)aTempData.get(14) != 0 && user.getLevel() < (Integer)aTempData.get(14)) {
                    user.sendPackets(new S_SystemMessage("等級" + (int)(Integer)aTempData.get(14) + "以上才可使用此道具。"));
                    return;
                }
                else if ((Integer)aTempData.get(2) != 0 && !user.getInventory().checkItem((Integer)aTempData.get(2))) {
                    L1Item temp = ItemTable.get().getTemplate((Integer)aTempData.get(2));
                    user.sendPackets(new S_SystemMessage("使用此道具需要(" + temp.getName() + ")來作為媒介。"));
                    return;
                }
                else {
                    if ((Integer)aTempData.get(3) != 0) {
                        L1ItemInstance item = user.getInventory().findItemId((Integer)aTempData.get(0));
                        user.getInventory().removeItem(item.getId(), 1L);
                    }
                    if ((Integer)aTempData.get(4) != 0 && (Integer)aTempData.get(17) != 0) {
                        if (user.hasSkillEffect(67)) {
                            user.removeSkillEffect(67);
                        }
                        L1PolyMorph.doPoly(user, (Integer)aTempData.get(4), (Integer)aTempData.get(5), 1);
                    }
                    else if ((Integer)aTempData.get(4) != 0) {
                        if (user.hasSkillEffect(67)) {
                            user.removeSkillEffect(67);
                        }
                        L1PolyMorph.doPoly(user, (Integer)aTempData.get(4), (Integer)aTempData.get(5), 1);
                    }
                    if ((Integer)aTempData.get(6) != 0) {
                        user.addBaseMaxHp((short)(int)(Integer)aTempData.get(6));
                        user.sendPackets(new S_SystemMessage("HP永久增加了(" + (int)(Integer)aTempData.get(6) + ")滴。"));
                        user.sendPackets(new S_OwnCharStatus(user));
                    }
                    if ((Integer)aTempData.get(7) != 0) {
                        user.addBaseMaxMp((short)(int)(Integer)aTempData.get(7));
                        user.sendPackets(new S_SystemMessage("MP永久增加了(" + (int)(Integer)aTempData.get(7) + ")滴。"));
                        user.sendPackets(new S_OwnCharStatus(user));
                    }
                    if ((Integer)aTempData.get(8) != 0) {
                        user.addBaseStr((int)(byte)(int)(Integer)aTempData.get(8));
                        user.sendPackets(new S_SystemMessage("力量永久增加了(" + (int)(Integer)aTempData.get(8) + ")點。"));
                        user.sendPackets(new S_OwnCharStatus2(user));
                        user.setElixirStats(user.getElixirStats() + (Integer)aTempData.get(8));
                    }
                    if ((Integer)aTempData.get(9) != 0) {
                        user.addBaseDex((int)(byte)(int)(Integer)aTempData.get(9));
                        user.sendPackets(new S_SystemMessage("敏捷永久增加了(" + (int)(Integer)aTempData.get(9) + ")點。"));
                        user.sendPackets(new S_OwnCharStatus2(user));
                        user.setElixirStats(user.getElixirStats() + (Integer)aTempData.get(9));
                    }
                    if ((Integer)aTempData.get(10) != 0) {
                        user.addBaseCon((int)(byte)(int)(Integer)aTempData.get(10));
                        user.sendPackets(new S_SystemMessage("體質永久增加了(" + (int)(Integer)aTempData.get(10) + ")點。"));
                        user.sendPackets(new S_OwnCharStatus2(user));
                        user.setElixirStats(user.getElixirStats() + (Integer)aTempData.get(10));
                    }
                    if ((Integer)aTempData.get(11) != 0) {
                        user.addBaseWis((int)(byte)(int)(Integer)aTempData.get(11));
                        user.sendPackets(new S_SystemMessage("精神永久增加了(" + (int)(Integer)aTempData.get(11) + ")點。"));
                        user.sendPackets(new S_OwnCharStatus2(user));
                        user.setElixirStats(user.getElixirStats() + (Integer)aTempData.get(11));
                    }
                    if ((Integer)aTempData.get(12) != 0) {
                        user.addBaseInt((int)(byte)(int)(Integer)aTempData.get(12));
                        user.sendPackets(new S_SystemMessage("智力永久增加了(" + (int)(Integer)aTempData.get(12) + ")點。"));
                        user.sendPackets(new S_OwnCharStatus2(user));
                        user.setElixirStats(user.getElixirStats() + (Integer)aTempData.get(12));
                    }
                    if ((Integer)aTempData.get(13) != 0) {
                        user.addBaseCha((int)(byte)(int)(Integer)aTempData.get(13));
                        user.sendPackets(new S_SystemMessage("魅力永久增加了(" + (int)(Integer)aTempData.get(13) + ")點。"));
                        user.sendPackets(new S_OwnCharStatus2(user));
                        user.setElixirStats(user.getElixirStats() + (Integer)aTempData.get(13));
                    }
                    if ((Integer)aTempData.get(15) != 0) {
                        if (user.hasSkillEffect(71)) {
                            user.sendPackets(new S_ServerMessage(698));
                            return;
                        }
                        else if (user.hasSkillEffect(5167)) {
                            user.setCurrentHp(user.getCurrentHp() - RandomArrayList.getInc(32, 12));
                        }
                        else {
                            user.setCurrentHp(user.getCurrentHp() + (Integer)aTempData.get(15));
                        }
                    }
                    if ((Integer)aTempData.get(16) != 0) {
                        if (user.hasSkillEffect(71)) {
                            user.sendPackets(new S_ServerMessage(698));
                            return;
                        }
                        else {
                            user.setCurrentMp(user.getCurrentMp() + (Integer)aTempData.get(16));
                            user.sendPackets(new S_ServerMessage(338, "$1084"));
                        }
                    }
                    if ((Integer)aTempData.get(20) != 0) {
                        try {
                            long exp = (long)(int)(Integer)aTempData.get(20);
                            if (exp > 0L) {
                                user.addExp(exp);
                                user.sendPackets(new S_OwnCharStatus(user));
                            }
                        }
                        catch (Exception ex) {}
                    }
                    if ((Integer)aTempData.get(21) != 0) {
                        int Lawful = user.getLawful() + (Integer)aTempData.get(21);
                        if (Lawful > 32767) {
                            Lawful = 32767;
                        }
                        else if (Lawful < -32768) {
                            Lawful = -32768;
                        }
                        user.setLawful(Lawful);
                    }
                    if ((Integer)aTempData.get(22) != 0) {
                        user.sendPackets(new S_SkillSound(user.getId(), (Integer)aTempData.get(22)));
                        user.broadcastPacketAll(new S_SkillSound(user.getId(), (Integer)aTempData.get(22)));
                    }
                    if ((int[])(Object)aTempData.get(27) != null) {
                        int[] Skills = (int[])(Object)aTempData.get(27);
                        int time = (Integer)aTempData.get(28);
                        for (int j = 0; j < Skills.length; ++j) {
                            L1SkillUse l1skilluse = new L1SkillUse();
                            l1skilluse.handleCommands(user, Skills[j], user.getId(), user.getX(), user.getY(), time, 4);
                        }
                    }
                    if ((Integer)aTempData.get(33) == 0) {
                        return;
                    }
                    else {
                        user.setKarma(user.getKarma() + (Integer)aTempData.get(33));
                        return;
                    }
                }
            }
            else {
                ++i;
            }
        }
    }
    
    private static void getData15b() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_道具使用類別_2");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("item_id")));
                    aReturn.add(1, new Integer(rset.getInt("checkClass")));
                    aReturn.add(2, new Integer(rset.getInt("checkItem")));
                    aReturn.add(3, new Integer(rset.getInt("removeItem")));
                    aReturn.add(4, new Integer(rset.getInt("polyId")));
                    aReturn.add(5, new Integer(rset.getInt("polyTime")));
                    aReturn.add(6, new Integer(rset.getInt("permanenceHp")));
                    aReturn.add(7, new Integer(rset.getInt("permanenceMp")));
                    aReturn.add(8, new Integer(rset.getInt("permanenceStr")));
                    aReturn.add(9, new Integer(rset.getInt("permanenceDex")));
                    aReturn.add(10, new Integer(rset.getInt("permanenceCon")));
                    aReturn.add(11, new Integer(rset.getInt("permanenceWis")));
                    aReturn.add(12, new Integer(rset.getInt("permanenceInt")));
                    aReturn.add(13, new Integer(rset.getInt("permanenceCha")));
                    aReturn.add(14, new Integer(rset.getInt("level")));
                    aReturn.add(15, new Integer(rset.getInt("hp")));
                    aReturn.add(16, new Integer(rset.getInt("mp")));
                    aReturn.add(17, new Integer(rset.getInt("讀取變身能力")));
                    aReturn.add(18, 0);
                    aReturn.add(19, 0);
                    aReturn.add(20, new Integer(rset.getInt("Exp")));
                    aReturn.add(21, new Integer(rset.getInt("Lawful")));
                    aReturn.add(22, new Integer(rset.getInt("Gfx")));
                    aReturn.add(23, 0);
                    aReturn.add(24, 0);
                    aReturn.add(25, 0);
                    aReturn.add(26, 0);
                    if (rset.getString("Skills") != null && !rset.getString("Skills").equals("") && !rset.getString("Skills").equals("0")) {
                        aReturn.add(27, getArray(rset.getString("Skills"), ",", 1));
                    }
                    else {
                        aReturn.add(27, null);
                    }
                    aReturn.add(28, new Integer(rset.getInt("SkillsTime")));
                    aReturn.add(29, 0);
                    aReturn.add(30, 0);
                    aReturn.add(31, 0);
                    aReturn.add(32, 0);
                    aReturn.add(33, new Integer(rset.getInt("Karma")));
                    ItemUse.aData15b.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
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
