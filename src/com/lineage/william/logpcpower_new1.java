package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1PcQuest;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class logpcpower_new1
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static logpcpower_new1 _instance;
    public static final String TOKEN = ",";
    
    static {
        logpcpower_new1.aData = new ArrayList();
        logpcpower_new1.BUILD_DATA = false;
    }
    
    public static logpcpower_new1 getInstance() {
        if (logpcpower_new1._instance == null) {
            logpcpower_new1._instance = new logpcpower_new1();
        }
        return logpcpower_new1._instance;
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
    
    public static void forIntensifyArmor(L1PcInstance pc) {
        ArrayList<Object> aTempData = null;
        if (!logpcpower_new1.BUILD_DATA) {
            logpcpower_new1.BUILD_DATA = true;
            getData();
        }
        L1PcQuest quest = pc.getQuest();
        for (int i = 0; i < logpcpower_new1.aData.size(); ++i) {
            aTempData = logpcpower_new1.aData.get(i);
            if (pc.getMeteLevel() == (Integer)aTempData.get(0)) {
                int[] giveItem = (int[])aTempData.get(1);
                int[] giveCount = (int[])aTempData.get(2);
                if (giveItem != null && giveCount != null) {
                    for (int j = 0; j < giveItem.length; ++j) {
                        L1ItemInstance item = ItemTable.get().createItem(giveItem[j]);
                        if (item.isStackable()) {
                            item.setCount((long)giveCount[j]);
                        }
                        else {
                            item.setCount(1L);
                        }
                        if (item != null) {
                            if (pc.getInventory().checkAddItem(item, (long)giveCount[j]) == 0) {
                                pc.getInventory().storeItem(item);
                            }
                            else {
                                World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                            }
                            pc.sendPackets(new S_SystemMessage("獲得轉生獎勵：" + item.getName() + " " + giveCount[j]));
                        }
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
            pstmt = conn.prepareStatement("SELECT * FROM 系統_轉生給予物品");
            rs = pstmt.executeQuery();
            ArrayList<Object> aReturn = null;
            if (rs != null) {
                while (rs.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rs.getInt("logpcpower")));
                    aReturn.add(1, getArray(rs.getString("getItem"), ",", 1));
                    aReturn.add(2, getArray(rs.getString("count"), ",", 1));
                    logpcpower_new1.aData.add(aReturn);
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
