package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1MapCheck;
import java.util.List;
import org.apache.commons.logging.Log;

public final class MapCheckTable
{
    private static final Log _log;
    private static final List<L1MapCheck> _list;
    
    static {
        _log = LogFactory.getLog(MapTileTable.class);
        _list = new ArrayList();
    }
    
    public static MapCheckTable get() {
        return MapCheckTable.Holder.instance;
    }
    
    private MapCheckTable() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `mapids_check`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1MapCheck mapCheck = new L1MapCheck();
                mapCheck.setMapid(rs.getInt("mapid"));
                mapCheck.setStartX(rs.getInt("startX"));
                mapCheck.setEndX(rs.getInt("endX"));
                mapCheck.setStartY(rs.getInt("startY"));
                mapCheck.setEndY(rs.getInt("endY"));
                MapCheckTable._list.add(mapCheck);
            }
        }
        catch (SQLException e) {
            MapCheckTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public List<L1MapCheck> getList() {
        return MapCheckTable._list;
    }
    
    private static class Holder
    {
        static MapCheckTable instance;
        
        static {
            MapCheckTable.Holder.instance = new MapCheckTable();
        }
    }
}
