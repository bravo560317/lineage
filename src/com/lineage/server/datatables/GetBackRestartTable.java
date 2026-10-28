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
import com.lineage.server.templates.L1GetBackRestart;
import java.util.Map;
import org.apache.commons.logging.Log;

public class GetBackRestartTable
{
    private static final Log _log;
    private static GetBackRestartTable _instance;
    private static final Map<Integer, L1GetBackRestart> _getbackrestart;
    
    static {
        _log = LogFactory.getLog(GetBackRestartTable.class);
        _getbackrestart = new HashMap();
    }
    
    public static GetBackRestartTable get() {
        if (GetBackRestartTable._instance == null) {
            GetBackRestartTable._instance = new GetBackRestartTable();
        }
        return GetBackRestartTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `getback_restart`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1GetBackRestart gbr = new L1GetBackRestart();
                int area = rs.getInt("area");
                gbr.setArea(area);
                gbr.setLocX(rs.getInt("locx"));
                gbr.setLocY(rs.getInt("locy"));
                gbr.setMapId(rs.getShort("mapid"));
                GetBackRestartTable._getbackrestart.put(new Integer(area), gbr);
            }
        }
        catch (SQLException e) {
            GetBackRestartTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        GetBackRestartTable._log.info("載入回城座標資料數量: " + GetBackRestartTable._getbackrestart.size() + "(" + timer.get() + "ms)");
    }
    
    public void add(int area, int locx, int locy, int map) {
        L1GetBackRestart tmp = GetBackRestartTable._getbackrestart.get(new Integer(area));
        if (tmp == null) {
            L1GetBackRestart gbr = new L1GetBackRestart();
            gbr.setArea(area);
            gbr.setLocX(locx);
            gbr.setLocY(locy);
            gbr.setMapId((short)map);
            GetBackRestartTable._getbackrestart.put(new Integer(area), gbr);
        }
    }
    
    public L1GetBackRestart getGetBackRestart(int mapid) {
        L1GetBackRestart tmp = GetBackRestartTable._getbackrestart.get(new Integer(mapid));
        if (tmp == null) {
            return null;
        }
        else {
            return tmp;
        }
    }
    
    public L1GetBackRestart[] getGetBackRestartTableList() {
        return (L1GetBackRestart[])GetBackRestartTable._getbackrestart.values().toArray(new L1GetBackRestart[GetBackRestartTable._getbackrestart.size()]);
    }
}
