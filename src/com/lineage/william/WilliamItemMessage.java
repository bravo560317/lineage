package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.ArrayList;

public class WilliamItemMessage
{
    private static ArrayList<ArrayList<Object>> list;
    
    static {
        WilliamItemMessage.list = new ArrayList();
    }
    
    private WilliamItemMessage() {
    }
    
    public static ArrayList<String> getItemInfo(L1ItemInstance item) {
        ArrayList<String> a = new ArrayList();
        for (ArrayList<Object> objects : WilliamItemMessage.list) {
            int itemid = (Integer)objects.get(0);
            if (itemid == -1 || itemid == item.getItemId()) {
                a.add((String)objects.get(1));
            }
        }
        return a;
    }
    
    public static void getData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_自訂物品顯示");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("Item_Id")));
                    aReturn.add(1, rset.getString("Info"));
                    WilliamItemMessage.list.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
    }
}
