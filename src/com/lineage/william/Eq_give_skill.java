package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class Eq_give_skill
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static Eq_give_skill _instance;
    public static final String TOKEN = ",";
    
    static {
        Eq_give_skill.aData = new ArrayList();
        Eq_give_skill.BUILD_DATA = false;
    }
    
    public static Eq_give_skill getInstance() {
        if (Eq_give_skill._instance == null) {
            Eq_give_skill._instance = new Eq_give_skill();
        }
        return Eq_give_skill._instance;
    }
    
    public static void main(String[] a) {
        while (true) {
            try {
                Server.main((String[])null);
                continue;
            }
            catch (Exception ex) {}
        }
    }
    
    public static void forIntensifyArmor(L1PcInstance pc, int itemid) {
        ArrayList<Object> aTempData = null;
        if (!Eq_give_skill.BUILD_DATA) {
            Eq_give_skill.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < Eq_give_skill.aData.size(); ++i) {
            aTempData = Eq_give_skill.aData.get(i);
            if ((Integer)aTempData.get(0) == itemid) {
                int[] skillid = (int[])aTempData.get(1);
                int[] array;
                for (int length = (array = skillid).length, j = 0; j < length; ++j) {
                    int element = array[j];
                    if (!pc.hasSkillEffect(element)) {
                        L1Skills skill = SkillsTable.get().getTemplate(element);
                        L1SkillUse skillUse = new L1SkillUse();
                        skillUse.handleCommands(pc, element, pc.getId(), pc.getX(), pc.getY(), skill.getBuffDuration(), 4);
                        pc.setBuffSkillList(element);
                    }
                }
            }
        }
    }
    
    public static void forIntensifyArmor1(L1PcInstance pc, int itemid) {
        ArrayList<Object> aTempData = null;
        if (!Eq_give_skill.BUILD_DATA) {
            Eq_give_skill.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < Eq_give_skill.aData.size(); ++i) {
            aTempData = Eq_give_skill.aData.get(i);
            if ((Integer)aTempData.get(0) == itemid) {
                int[] skillid = (int[])aTempData.get(1);
                int[] array;
                for (int length = (array = skillid).length, j = 0; j < length; ++j) {
                    int element = array[j];
                    pc.setBuffSkillList1(element);
                }
            }
        }
    }
    
    public static void forIntensifyArmor_login(L1PcInstance pc) {
        ArrayList<Object> aTempData = null;
        if (!Eq_give_skill.BUILD_DATA) {
            Eq_give_skill.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < Eq_give_skill.aData.size(); ++i) {
            aTempData = Eq_give_skill.aData.get(i);
            if (pc.getInventory().checkEquipped((Integer)aTempData.get(0))) {
                int[] skillid = (int[])aTempData.get(1);
                int[] array;
                for (int length = (array = skillid).length, j = 0; j < length; ++j) {
                    int element = array[j];
                    if (!pc.hasSkillEffect(element)) {
                        L1Skills skill = SkillsTable.get().getTemplate(element);
                        L1SkillUse skillUse = new L1SkillUse();
                        skillUse.handleCommands(pc, element, pc.getId(), pc.getX(), pc.getY(), skill.getBuffDuration(), 4);
                        pc.setBuffSkillList(element);
                    }
                }
            }
        }
    }
    
    private static void getData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM w_裝備穿著賦予狀態");
            rs = pstmt.executeQuery();
            ArrayList<Object> aReturn = null;
            if (rs != null) {
                while (rs.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rs.getInt("穿著物品編號")));
                    aReturn.add(1, getArray(rs.getString("技能"), ",", 1));
                    Eq_give_skill.aData.add(aReturn);
                }
            }
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstmt);
            SQLUtil.close(conn);
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
