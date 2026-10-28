package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class ItemActionDoll
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    
    static {
        ItemActionDoll.aData = new ArrayList();
        ItemActionDoll.NO_GET_DATA = false;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc) {
        ArrayList aTempData = null;
        if (!ItemActionDoll.NO_GET_DATA) {
            ItemActionDoll.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < ItemActionDoll.aData.size(); ++i) {
            aTempData = ItemActionDoll.aData.get(i);
            if (((String)aTempData.get(0)).equals(s)) {
                if (pc.getdollcheck() > 0 && pc.getdollcheckitem() > 0) {
                    pc.setdollcheck(0);
                    pc.setdollcheckitem(0);
                    pc.sendPackets(new S_SystemMessage("\\fY" + (String)aTempData.get(3)));
                    if (!pc.getDolls().isEmpty()) {
                        Object[] dolls1 = pc.getDolls().values().toArray();
                        Object[] array;
                        for (int length = (array = dolls1).length, j = 0; j < length; ++j) {
                            Object obj = array[j];
                            L1DollInstance doll = (L1DollInstance)obj;
                            if (doll != null) {
                                doll.deleteDoll();
                            }
                        }
                        pc.getDolls().clear();
                    }
                    return false;
                }
                else if (pc.getdollcheck1() > 0 && pc.getdollcheckitem1() > 0) {
                    pc.setdollcheck1(0);
                    pc.setdollcheckitem1(0);
                    pc.sendPackets(new S_SystemMessage("\\fY" + (String)aTempData.get(3)));
                    if (!pc.getDolls().isEmpty()) {
                        Object[] dolls1 = pc.getDolls().values().toArray();
                        Object[] array2;
                        for (int length2 = (array2 = dolls1).length, k = 0; k < length2; ++k) {
                            Object obj = array2[k];
                            L1DollInstance doll = (L1DollInstance)obj;
                            if (doll != null) {
                                doll.deleteDoll();
                            }
                        }
                        pc.getDolls().clear();
                    }
                    return false;
                }
                else {
                    L1Doll type = DollPowerTable.get().get_type((Integer)aTempData.get(2));
                    if (type.get_mode() == 0) {
                        pc.setdollcheck((Integer)aTempData.get(1));
                        pc.setdollcheckitem((Integer)aTempData.get(2));
                    }
                    if (type.get_mode() == 1) {
                        pc.setdollcheck1((Integer)aTempData.get(1));
                        pc.setdollcheckitem1((Integer)aTempData.get(2));
                    }
                    if (pc.getQuest().get_step(pc.getdollcheck()) != 1) {
                        pc.sendPackets(new S_SystemMessage("該娃娃尚未登入卡冊,無法開啟自動召喚"));
                        pc.setdollcheck(0);
                        pc.setdollcheckitem(0);
                        if (!pc.getDolls().isEmpty()) {
                            Object[] dolls2 = pc.getDolls().values().toArray();
                            Object[] array3;
                            for (int length3 = (array3 = dolls2).length, l = 0; l < length3; ++l) {
                                Object obj2 = array3[l];
                                L1DollInstance doll2 = (L1DollInstance)obj2;
                                if (doll2 != null) {
                                    doll2.deleteDoll();
                                }
                            }
                            pc.getDolls().clear();
                        }
                        return false;
                    }
                    else {
                        pc.sendPackets(new S_SystemMessage("\\fY" + (String)aTempData.get(4)));
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
            ResultSet rset = stat.executeQuery("SELECT * FROM 卡冊娃娃自動召換");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    sTemp = rset.getString("action");
                    aReturn.add(0, sTemp);
                    aReturn.add(1, rset.getInt("任務編號"));
                    aReturn.add(2, new Integer(rset.getInt("娃娃道具編號")));
                    aReturn.add(3, rset.getString("msg1"));
                    aReturn.add(4, rset.getString("msg2"));
                    ItemActionDoll.aData.add(aReturn);
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
