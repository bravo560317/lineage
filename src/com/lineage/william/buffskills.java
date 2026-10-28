package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class buffskills
{
    private static ArrayList<ArrayList<Object>> aData15b;
    private static boolean NO_MORE_GET_DATA15b;
    public static final String TOKEN = ",";
    
    static {
        buffskills.aData15b = new ArrayList();
        buffskills.NO_MORE_GET_DATA15b = false;
    }
    
    public static void main(String[] a) {
        try {
            Server.main((String[])null);
        }
        catch (Exception ex) {}
    }
    
    public static void forItemUSe(L1PcInstance user, L1ItemInstance itemInstance) {
        int itemid = itemInstance.getItemId();
        ArrayList<Object> aTempData = null;
        if (!buffskills.NO_MORE_GET_DATA15b) {
            buffskills.NO_MORE_GET_DATA15b = true;
            getData15b();
        }
        int i = 0;
        while (i < buffskills.aData15b.size()) {
            aTempData = buffskills.aData15b.get(i);
            if ((Integer)aTempData.get(0) != itemid) {
                ++i;
            }
            else {
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
                    if ((int)(Integer)aTempData.get(1) != class_id) {
                        user.sendPackets(new S_SystemMessage("你的職業無法使用" + msg + "的專屬道具。"));
                        return;
                    }
                }
                if ((Integer)aTempData.get(2) != 0 && user.getLevel() < (Integer)aTempData.get(2)) {
                    user.sendPackets(new S_SystemMessage("等級" + (int)(Integer)aTempData.get(2) + "以上才可使用此道具。"));
                    return;
                }
                else {
                    if ((Integer)aTempData.get(3) != 0) {
                        L1ItemInstance item = user.getInventory().findItemId((Integer)aTempData.get(0));
                        user.getInventory().removeItem(item.getId(), 1L);
                    }
                    if ((int[])aTempData.get(4) != null) {
                        int[] Skills = (int[])aTempData.get(4);
                        int time = (Integer)aTempData.get(5);
                        for (int j = 0; j < Skills.length; ++j) {
                            L1SkillUse l1skilluse = new L1SkillUse();
                            l1skilluse.handleCommands(user, Skills[j], user.getId(), user.getX(), user.getY(), time, 4);
                            if ((Integer)aTempData.get(6) == 1) {
                                user.sendPacketsAll(new S_DoActionGFX(user.getId(), 19));
                            }
                        }
                    }
                    return;
                }
            }
        }
    }
    
    private static void getData15b() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_道具魔法卷軸");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("item_id")));
                    aReturn.add(1, new Integer(rset.getInt("checkClass")));
                    aReturn.add(2, new Integer(rset.getInt("level")));
                    aReturn.add(3, new Integer(rset.getInt("removeItem")));
                    if (rset.getString("Skills") != null && !rset.getString("Skills").equals("") && !rset.getString("Skills").equals("0")) {
                        aReturn.add(4, getArray(rset.getString("Skills"), ",", 1));
                    }
                    else {
                        aReturn.add(4, null);
                    }
                    aReturn.add(5, new Integer(rset.getInt("SkillsTime")));
                    aReturn.add(6, new Integer(rset.getInt("施放動作")));
                    buffskills.aData15b.add(aReturn);
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
