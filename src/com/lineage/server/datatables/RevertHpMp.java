package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.collections.Maps;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class RevertHpMp
{
    private static final Log _log;
    private static final Map<Integer, RevertHpMp> _relist;
    private static RevertHpMp _instance;
    private int _id;
    private int _map;
    private int _sx;
    private int _sy;
    private int _ex;
    private int _ey;
    private int _hpr;
    private int _mpr;
    
    static {
        _log = LogFactory.getLog(RevertHpMp.class);
        _relist = Maps.newHashMap();
    }
    
    public static RevertHpMp get() {
        if (RevertHpMp._instance == null) {
            RevertHpMp._instance = new RevertHpMp();
        }
        return RevertHpMp._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i = 0;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `系統_地圖回血魔_範圍`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                int map = rs.getInt("地圖編號");
                int sx = rs.getInt("範圍MinX");
                int sy = rs.getInt("範圍MinY");
                int ex = rs.getInt("範圍MaxX");
                int ey = rs.getInt("範圍MaxY");
                int hpr = rs.getInt("HP回復量");
                int mpr = rs.getInt("MP回復量");
                RevertHpMp re = new RevertHpMp();
                re.setId(id);
                re.setMapId(map);
                re.setSx(sx);
                re.setSy(sy);
                re.setEx(ex);
                re.setEy(ey);
                re.setHpr(hpr);
                re.setMpr(mpr);
                RevertHpMp._relist.put(id, re);
                ++i;
            }
        }
        catch (SQLException e) {
            RevertHpMp._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        RevertHpMp._log.info("讀取->指定地圖回血魔系統: " + i + "(" + timer.get() + "ms)");
    }
    
    public int getId() {
        return this._id;
    }
    
    private void setId(int i) {
        this._id = i;
    }
    
    public int getMapId() {
        return this._map;
    }
    
    private void setMapId(int i) {
        this._map = i;
    }
    
    public int getSx() {
        return this._sx;
    }
    
    private void setSx(int i) {
        this._sx = i;
    }
    
    public int getSy() {
        return this._sy;
    }
    
    private void setSy(int i) {
        this._sy = i;
    }
    
    public int getEx() {
        return this._ex;
    }
    
    private void setEx(int i) {
        this._ex = i;
    }
    
    public int getEy() {
        return this._ey;
    }
    
    private void setEy(int i) {
        this._ey = i;
    }
    
    public int getHpr() {
        return this._hpr;
    }
    
    private void setHpr(int i) {
        this._hpr = i;
    }
    
    public int getMpr() {
        return this._mpr;
    }
    
    private void setMpr(int i) {
        this._mpr = i;
    }
    
    public int RevertSize() {
        return RevertHpMp._relist.size();
    }
    
    public RevertHpMp getRevert(int id) {
        return RevertHpMp._relist.get((Integer)id);
    }
}
