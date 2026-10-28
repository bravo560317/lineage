package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.templates.L1Item;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class ItemActionPolyBS
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    
    static {
        ItemActionPolyBS.aData = new ArrayList();
        ItemActionPolyBS.NO_GET_DATA = false;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc) {
        ArrayList aTempData = null;
        if (!ItemActionPolyBS.NO_GET_DATA) {
            ItemActionPolyBS.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < ItemActionPolyBS.aData.size(); ++i) {
            aTempData = ItemActionPolyBS.aData.get(i);
                if (((String)aTempData.get(0)).equals(s)) {
                if (pc.getcardspoly() > 0) {
                    pc.setcardspoly(0);
                    pc.setpolycheck(0);
                    pc.sendPackets(new S_SystemMessage("\\fY" + (String)aTempData.get(3)));
                    return false;
                }
                else if (pc.getQuest().get_step((Integer)aTempData.get(1)) != 1) {
                    pc.sendPackets(new S_SystemMessage("該變身尚未登入卡冊,無法開啟自動變身"));
                    pc.setcardspoly(0);
                    pc.setpolycheck(0);
                    return false;
                } 
              else {
                    pc.setpolycheck((Integer)aTempData.get(1));
                    pc.setcardspoly((Integer)aTempData.get(2));
                    pc.sendPackets(new S_SystemMessage("\\fY" + (String)aTempData.get(4)));                    
                    	if (!pc.getInventory().checkItem((Integer)aTempData.get(5), (long)(int)(Integer)aTempData.get(6))) {
                        L1Item temp = ItemTable.get().getTemplate((Integer)aTempData.get(5));
                        pc.sendPackets(new S_SystemMessage(temp.getNameId() + "不足( " + (int)(Integer)aTempData.get(6) + ")，無法啟動自動變身。"));
                        pc.setcardspoly(0);
                        pc.setpolycheck(0); 
                        return false;
                        }     
                    else {
                        pc.getInventory().consumeItem((Integer)aTempData.get(5), (long)(int)(Integer)aTempData.get(6));
                        pc.sendPackets(new S_CloseList(pc.getId()));
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
            ResultSet rset = stat.executeQuery("SELECT * FROM 卡冊變身自動變身");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    sTemp = rset.getString("action");
                    aReturn.add(0, sTemp);
                    aReturn.add(1, rset.getInt("任務編號"));
                    aReturn.add(2, new Integer(rset.getInt("變身編號")));
                    aReturn.add(3, rset.getString("msg1"));
                    aReturn.add(4, rset.getString("msg2"));
                    aReturn.add(5, new Integer(rset.getInt("變身消耗道具編號")));
                    aReturn.add(6, new Integer(rset.getInt("變身消耗道具數量")));
                    ItemActionPolyBS.aData.add(aReturn);
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