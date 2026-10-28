package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.List;

public class GiveBack
{
    public static final List<Integer> savepcid;
    public static final List<L1ItemInstance> saveweapon;
    public static final List<String> savename;
    
    static {
        savepcid = new ArrayList();
        saveweapon = new ArrayList();
        savename = new ArrayList();
    }
    
    public static void load() {
        Connection conn = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM william_npcdieshare");
            while (rs != null && rs.next()) {}
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (Exception ex) {}
    }
}
