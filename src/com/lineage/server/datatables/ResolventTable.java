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

public final class ResolventTable
{
    private static final Log _log;
    private static ResolventTable _instance;
    private static final Map<Integer, Integer> _resolvent;
    
    static {
        _log = LogFactory.getLog(ResolventTable.class);
        _resolvent = new HashMap();
    }
    
    public static ResolventTable get() {
        if (ResolventTable._instance == null) {
            ResolventTable._instance = new ResolventTable();
        }
        return ResolventTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM resolvent");
            rs = ps.executeQuery();
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int crystalCount = rs.getInt("crystal_count");
                ResolventTable._resolvent.put(new Integer(itemId), crystalCount);
            }
        }
        catch (SQLException e) {
            ResolventTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        ResolventTable._log.info("載入溶解物品設置資料數量: " + ResolventTable._resolvent.size() + "(" + timer.get() + "ms)");
    }
    
    public int getCrystalCount(int itemId) {
        int crystalCount = 0;
        if (ResolventTable._resolvent.containsKey((Integer)itemId)) {
            crystalCount = ResolventTable._resolvent.get((Integer)itemId);
        }
        return crystalCount;
    }
}
