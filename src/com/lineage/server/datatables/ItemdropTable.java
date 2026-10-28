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

public class ItemdropTable
{
    private static final Log _log;
    private static ItemdropTable _instance;
    public static final ArrayList<Integer> RESTRICTIONS;
    
    static {
        _log = LogFactory.getLog(ItemdropTable.class);
        RESTRICTIONS = new ArrayList();
    }
    
    public static ItemdropTable get() {
        if (ItemdropTable._instance == null) {
            ItemdropTable._instance = new ItemdropTable();
        }
        return ItemdropTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `內掛中禁止打到物品`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                String itemid = rs.getString("itemid");
                ItemdropTable.RESTRICTIONS.add(Integer.valueOf(itemid));
            }
        }
        catch (SQLException e) {
            ItemdropTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemdropTable._log.info("載入掛機限制掉落道具: " + ItemdropTable.RESTRICTIONS.size() + "(" + timer.get() + "ms)");
    }
}
