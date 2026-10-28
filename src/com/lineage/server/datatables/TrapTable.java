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
import com.lineage.server.templates.L1Trap;
import java.util.Map;
import org.apache.commons.logging.Log;

public class TrapTable
{
    private static final Log _log;
    private static TrapTable _instance;
    private static final Map<Integer, L1Trap> _traps;
    
    static {
        _log = LogFactory.getLog(TrapTable.class);
        _traps = new HashMap();
    }
    
    public static TrapTable get() {
        if (TrapTable._instance == null) {
            TrapTable._instance = new TrapTable();
        }
        return TrapTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `trap`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1Trap trap = new L1Trap(rs);
                TrapTable._traps.put(trap.getId(), trap);
            }
        }
        catch (SQLException e) {
            TrapTable._log.error(e.getLocalizedMessage(), e);
        }
        catch (Exception e2) {
            TrapTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        TrapTable._log.info("載入陷阱資料數量: " + TrapTable._traps.size() + "(" + timer.get() + "ms)");
    }
    
    public static void reload() {
        TrapTable._instance = new TrapTable();
        TrapTable._traps.clear();
        TrapTable._instance.load();
    }
    
    public L1Trap getTemplate(int id) {
        return TrapTable._traps.get((Integer)id);
    }
}
