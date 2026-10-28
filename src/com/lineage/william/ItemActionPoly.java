package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class ItemActionPoly
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    
    static {
        ItemActionPoly.aData = new ArrayList();
        ItemActionPoly.NO_GET_DATA = false;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc) {
        ArrayList aTempData = null;
        if (!ItemActionPoly.NO_GET_DATA) {
            ItemActionPoly.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < ItemActionPoly.aData.size(); ++i) {
            aTempData = ItemActionPoly.aData.get(i);
            if (((String)aTempData.get(0)).equals(s) && pc.getLevel() >= (Integer)aTempData.get(1)) {
                if (!pc.getInventory().checkItem((Integer)aTempData.get(3), (long)(int)(Integer)aTempData.get(4))) {
                    L1Item temp = ItemTable.get().getTemplate((Integer)aTempData.get(3));
                    pc.sendPackets(new S_SystemMessage(temp.getNameId() + "不足( " + (int)(Integer)aTempData.get(4) + ")，無法使用。"));
                    return false;
                }
                else {
                    pc.getInventory().consumeItem((Integer)aTempData.get(3), (long)(int)(Integer)aTempData.get(4));
                    L1PolyMorph.doPoly(pc, (Integer)aTempData.get(2), 1800, 1);
                    pc.sendPackets(new S_CloseList(pc.getId()));
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
            ResultSet rset = stat.executeQuery("SELECT * FROM w_自訂變形卷軸");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    sTemp = rset.getString("action");
                    aReturn.add(0, sTemp);
                    aReturn.add(1, rset.getInt("checkLevel_min"));
                    aReturn.add(2, new Integer(rset.getInt("checkPoly")));
                    aReturn.add(3, new Integer(rset.getInt("扣除道具編號")));
                    aReturn.add(4, new Integer(rset.getInt("扣除道具數量")));
                    ItemActionPoly.aData.add(aReturn);
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
