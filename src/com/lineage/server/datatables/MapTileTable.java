package com.lineage.server.datatables;

import com.lineage.server.datatables.sql.SqlError;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1MapTile;
import java.util.List;
import org.apache.commons.logging.Log;

public final class MapTileTable
{
    private static final Log _log;
    private static final List<L1MapTile> _list;
    
    static {
        _log = LogFactory.getLog(MapTileTable.class);
        _list = new ArrayList();
    }
    
    public static MapTileTable get() {
        return MapTileTable.Holder.instance;
    }
    
    private MapTileTable() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `mapids_tile`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int mapId = rs.getInt("map_id");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int tile = rs.getInt("tile");
                L1MapTile mapTile = new L1MapTile();
                mapTile.setMapid(mapId);
                mapTile.setX(x);
                mapTile.setY(y);
                mapTile.setTile(tile);
                MapTileTable._list.add(mapTile);
            }
        }
        catch (SQLException e) {
            MapTileTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public List<L1MapTile> getList() {
        return MapTileTable._list;
    }
    
    public void addData(int mapId, int x, int y, int tile) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `mapids_tile` SET `map_id`=?,`x`=?,`y`=?,`tile`=?");
            int i = 0;
            ps.setInt(++i, mapId);
            ps.setInt(++i, x);
            ps.setInt(++i, y);
            ps.setLong(++i, (long)tile);
            ps.execute();
        }
        catch (Exception e) {
            SqlError.isError(MapTileTable._log, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    private static class Holder
    {
        static MapTileTable instance;
        
        static {
            MapTileTable.Holder.instance = new MapTileTable();
        }
    }
}
