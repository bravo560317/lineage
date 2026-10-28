package com.add;

import java.sql.Timestamp;
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

public class ItemTimeTableadd
{
    private static final Log _log;
    public static final Map<Integer, L1ItemTimeadd> TIME;
    private static ItemTimeTableadd _instance;
    
    static {
        _log = LogFactory.getLog(ItemTimeTableadd.class);
        TIME = new HashMap();
    }
    
    public static ItemTimeTableadd get() {
        if (ItemTimeTableadd._instance == null) {
            ItemTimeTableadd._instance = new ItemTimeTableadd();
        }
        return ItemTimeTableadd._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_物品日期限制`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("道具編號");
                Timestamp next_reset_time = rs.getTimestamp("物品時間限制使用");
                L1ItemTimeadd itemTime = new L1ItemTimeadd(next_reset_time);
                ItemTimeTableadd.TIME.put(key, itemTime);
            }
        }
        catch (SQLException e) {
            ItemTimeTableadd._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
