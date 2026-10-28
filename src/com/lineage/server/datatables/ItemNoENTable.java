package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class ItemNoENTable
{
    private static final Log _log;
    private static ItemNoENTable _instance;
    public static final ArrayList<Integer> RESTRICTIONS;
    
    static {
        _log = LogFactory.getLog(ItemNoENTable.class);
        RESTRICTIONS = new ArrayList();
    }
    
    public static ItemNoENTable get() {
        if (ItemNoENTable._instance == null) {
            ItemNoENTable._instance = new ItemNoENTable();
        }
        return ItemNoENTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_裝備禁止強化`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                ItemNoENTable.RESTRICTIONS.add(itemid);
            }
        }
        catch (SQLException e) {
            ItemNoENTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemNoENTable._log.info("載入掛機限制掉落道具: " + ItemNoENTable.RESTRICTIONS.size() + "(" + timer.get() + "ms)");
    }
}
