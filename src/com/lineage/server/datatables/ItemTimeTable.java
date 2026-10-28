package com.lineage.server.datatables;

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

public class ItemTimeTable
{
    private static final Log _log;
    private static ItemTimeTable _instance;
    public static final Map<Integer, int[]> TIME;
    
    static {
        _log = LogFactory.getLog(ItemTimeTable.class);
        TIME = new HashMap();
    }
    
    public static ItemTimeTable get() {
        if (ItemTimeTable._instance == null) {
            ItemTimeTable._instance = new ItemTimeTable();
        }
        return ItemTimeTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_物品時間限制`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("itemid");
                int[] value = { rs.getInt("d"), rs.getInt("h"), rs.getInt("m") };
                ItemTimeTable.TIME.put(key, value);
            }
        }
        catch (SQLException e) {
            ItemTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemTimeTable._log.info("載入時間限制道具: " + ItemTimeTable.TIME.size() + "(" + timer.get() + "ms)");
    }
}
