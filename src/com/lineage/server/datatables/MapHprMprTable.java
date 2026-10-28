package com.lineage.server.datatables;

import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class MapHprMprTable
{
    private static final Log _log;
    private static MapHprMprTable _instance;
    private static final Map<Integer, MapHprMprTable.MapHprMpr> _maphprmprList;
    
    static {
        _log = LogFactory.getLog(MapHprMprTable.class);
        _maphprmprList = new HashMap();
    }
    
    public static MapHprMprTable get() {
        if (MapHprMprTable._instance == null) {
            MapHprMprTable._instance = new MapHprMprTable();
        }
        return MapHprMprTable._instance;
    }
    
    private MapHprMprTable() {
        this.load();
    }
    
    public void load() {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `系統_地圖回血魔`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int mapid = rs.getInt("mapid");
                int hpr = rs.getInt("hpr");
                int mpr = rs.getInt("mpr");
                MapHprMprTable.MapHprMpr map = new MapHprMprTable.MapHprMpr();
                map._hpr = hpr;
                map._mpr = mpr;
                MapHprMprTable._maphprmprList.put(new Integer(mapid), map);
            }
        }
        catch (SQLException e) {
            MapHprMprTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        MapHprMprTable._log.info("載入地圖回血回魔:" + MapHprMprTable._maphprmprList.size());
    }
    
    public int getMapHpr(L1PcInstance pc, int mapid) {
        if (MapHprMprTable._maphprmprList.containsKey((Integer)mapid)) {
            if (pc.getMap().isUnderwater()) {
                if (pc.getInventory().checkEquipped(20207)) {
                    return 0;
                }
                else if (pc.hasSkillEffect(1003)) {
                    return 0;
                }
                else if (pc.getInventory().checkEquipped(21048) && pc.getInventory().checkEquipped(21049) && pc.getInventory().checkEquipped(21050)) {
                    return 0;
                }
            }
            return (MapHprMprTable._maphprmprList.get((Integer)mapid))._hpr;
        }
        else {
            return 0;
        }
    }
    
    public int getMapMpr(int mapid) {
        if (MapHprMprTable._maphprmprList.containsKey((Integer)mapid)) {
            return (MapHprMprTable._maphprmprList.get((Integer)mapid))._mpr;
        }
        else {
            return 0;
        }
    }
    
    private class MapHprMpr
    {
        private int _hpr;
        private int _mpr;
        
        /*
        static void access$1(MapHprMprTable.MapHprMpr mapHprMpr, int hpr) {
            mapHprMpr._hpr = hpr;
        }
        */
        
        /*
        static void access$2(MapHprMprTable.MapHprMpr mapHprMpr, int mpr) {
            mapHprMpr._mpr = mpr;
        }
        */
    }
}
