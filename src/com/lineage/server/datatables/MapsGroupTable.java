package com.lineage.server.datatables;

import java.util.Iterator;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1MapsLimitTime;
import java.util.Map;
import org.apache.commons.logging.Log;

public final class MapsGroupTable
{
    private static final Log _log;
    private static final Map<Integer, L1MapsLimitTime> _mapsGroup;
    private static MapsGroupTable _instance;
    
    static {
        _log = LogFactory.getLog(MapsGroupTable.class);
        _mapsGroup = new HashMap();
    }
    
    public static MapsGroupTable get() {
        if (MapsGroupTable._instance == null) {
            MapsGroupTable._instance = new MapsGroupTable();
        }
        return MapsGroupTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `mapids_group` ORDER BY order_id DESC");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                String mapIds = rs.getString("mapids");
                String mapName = rs.getString("name");
                int limitTime = rs.getInt("time");
                int outMapX = rs.getInt("out_x");
                int outMapY = rs.getInt("out_y");
                short outMapId = rs.getShort("out_map");
                List<Integer> mapList = new ArrayList();
                String[] split;
                for (int length = (split = mapIds.split(",")).length, i = 0; i < length; ++i) {
                    String map_str = split[i];
                    mapList.add(Integer.parseInt(map_str));
                }
                L1MapsLimitTime mapsLimitTime = new L1MapsLimitTime(order_id, mapList, mapName, limitTime, outMapX, outMapY, outMapId);
                MapsGroupTable._mapsGroup.put(order_id, mapsLimitTime);
            }
        }
        catch (SQLException e) {
            MapsGroupTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        MapsGroupTable._log.info("讀取->地圖群組設置資料 (入場時間限制) 數量: " + MapsGroupTable._mapsGroup.size() + "(" + timer.get() + "ms)");
    }
    
    public final Map<Integer, L1MapsLimitTime> getGroupMaps() {
        return MapsGroupTable._mapsGroup;
    }
    
    public final L1MapsLimitTime findGroupMap(int mapId) {
        for (L1MapsLimitTime mapsLimitTime : MapsGroupTable._mapsGroup.values()) {
            if (mapsLimitTime.getMapList().contains((Integer)mapId)) {
                return mapsLimitTime;
            }
        }
        return null;
    }
}
