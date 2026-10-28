package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Random;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class Reward2
{
    private static ArrayList<ArrayList<Object>> array;
    private static boolean GET_ITEM;
    public static final String TOKEN = ",";
    
    static {
        Reward2.array = new ArrayList();
        Reward2.GET_ITEM = false;
    }
    
    public static void main(String[] a) {
        try {
            Server.main((String[])null);
        }
        catch (Exception ex) {}
    }
    
    public static void getItem(L1PcInstance pc) {
        ArrayList<?> data = null;
        if (!Reward2.GET_ITEM) {
            Reward2.GET_ITEM = true;
            getItemData();
        }
        for (int i = 0; i < Reward2.array.size(); ++i) {
            data = Reward2.array.get(i);
            Random _random = new Random();
            if (pc.getInventory().checkItem((Integer)data.get(0)) && _random.nextInt(100) + 1 <= 80) {
                pc.sendPackets(new S_ServerMessage("(預防誤點機制啟動)裝備中無法強化"));
            }
        }
    }
    
    private static void getItemData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM 系統_升級獎勵1");
            ArrayList<Object> arraylist = null;
            if (rset != null) {
                while (rset.next()) {
                    arraylist = new ArrayList();
                    arraylist.add(0, new Integer(rset.getInt("level")));
                    Reward2.array.add(arraylist);
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
