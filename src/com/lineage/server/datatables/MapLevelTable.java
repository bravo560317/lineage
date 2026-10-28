package com.lineage.server.datatables;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1PcInstance;
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

public class MapLevelTable
{
    private static final Log _log;
    private static MapLevelTable _instance;
    private static final Map<Integer, int[]> _level;
    
    static {
        _log = LogFactory.getLog(MapLevelTable.class);
        _level = new HashMap();
    }
    
    public static MapLevelTable get() {
        if (MapLevelTable._instance == null) {
            MapLevelTable._instance = new MapLevelTable();
        }
        return MapLevelTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `mapids_level`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int mapid = rs.getInt("mapid");
                int[] level = new int[5];
                int min = rs.getInt("min");
                int max = rs.getInt("max");
                int locx = rs.getInt("locx");
                int locy = rs.getInt("locy");
                int tomapid = rs.getInt("tomapid");
                level[0] = min;
                level[1] = max;
                level[2] = locx;
                level[3] = locy;
                level[4] = tomapid;
                MapLevelTable._level.put(new Integer(mapid), level);
            }
        }
        catch (SQLException e) {
            MapLevelTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        MapLevelTable._log.info("載入地圖等極限制資料數量: " + MapLevelTable._level.size() + "(" + timer.get() + "ms)");
    }
    
    public void get_level(int mapid, L1PcInstance pc) {
        int[] levelX = MapLevelTable._level.get(new Integer(mapid));
        if (levelX == null) {
            return;
        }
        else if (pc.getLevel() >= levelX[0] && pc.getLevel() < levelX[1]) {
            return;
        }
        else {
            if (!pc.isGm()) {
                L1Teleport.teleport(pc, levelX[2], levelX[3], (short)levelX[4], 5, true);
            }
            return;
        }
    }
}
