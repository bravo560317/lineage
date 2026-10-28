package com.lineage.server.datatables;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class C1_Name_Table
{
    private static final Log _log;
    private static final Map<Integer, String> _names;
    private static C1_Name_Table _instance;
    
    static {
        _log = LogFactory.getLog(C1_Name_Table.class);
        _names = new HashMap();
    }
    
    public static C1_Name_Table get() {
        if (C1_Name_Table._instance == null) {
            C1_Name_Table._instance = new C1_Name_Table();
        }
        return C1_Name_Table._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_血盟_陣營`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int c1_id = rs.getInt("c1_id");
                String c1_name = rs.getString("c1_name");
                C1_Name_Table._names.put(c1_id, c1_name);
            }
        }
        catch (SQLException e) {
            C1_Name_Table._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        C1_Name_Table._log.info("載入陣營名稱記錄數量: " + C1_Name_Table._names.size() + "(" + timer.get() + "ms)");
    }
    
    public String get(int key) {
        return C1_Name_Table._names.get((Integer)key);
    }
    
    public Integer getv(String v) {
        for (Integer key : C1_Name_Table._names.keySet()) {
            String value = C1_Name_Table._names.get(key);
            if (value.equals(v)) {
                return key;
            }
        }
        return (Integer)(-1);
    }
}
