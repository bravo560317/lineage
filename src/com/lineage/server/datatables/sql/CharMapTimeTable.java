package com.lineage.server.datatables.sql;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharMapTimeStorage;

public class CharMapTimeTable implements CharMapTimeStorage
{
    private static final Log _log;
    private static final Map<Integer, HashMap<Integer, Integer>> _timeMap;
    
    static {
        _log = LogFactory.getLog(CharMapTimeTable.class);
        _timeMap = new ConcurrentHashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_maps_time` ORDER BY `char_obj_id`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_obj_id = rs.getInt("char_obj_id");
                if (CharObjidTable.get().isChar(char_obj_id) != null) {
                    int order_id = rs.getInt("order_id");
                    int used_time = rs.getInt("used_time");
                    this.addTime(char_obj_id, order_id, used_time);
                }
                else {
                    this.deleteTime(char_obj_id);
                }
            }
        }
        catch (SQLException e) {
            CharMapTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharMapTimeTable._log.info("載入地圖入場時間紀錄資料數量: " + CharMapTimeTable._timeMap.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public void update(int objid, int mapid, int time) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_maps_time` ORDER BY `char_obj_id`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_obj_id = rs.getInt("char_obj_id");
                if (CharObjidTable.get().isChar(char_obj_id) != null) {
                    int order_id = rs.getInt("order_id");
                    int used_time = rs.getInt("used_time");
                    this.addTime(char_obj_id, order_id, used_time);
                }
                else {
                    this.deleteTime(char_obj_id);
                }
            }
        }
        catch (SQLException e) {
            CharMapTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public Map<Integer, Integer> addTime(int objId, int order_id, int used_time) {
        HashMap<Integer, Integer> list = CharMapTimeTable._timeMap.get((Integer)objId);
        if (list == null) {
            HashMap<Integer, Integer> newlist = new HashMap();
            newlist.put(order_id, used_time);
            CharMapTimeTable._timeMap.put(objId, newlist);
            return (Map)newlist;
        }
        else {
            list.put(order_id, used_time);
            return (Map)list;
        }
    }
    
    @Override
    public void getTime(L1PcInstance pc) {
        HashMap<Integer, Integer> list = CharMapTimeTable._timeMap.get((Integer)pc.getId());
        if (list != null) {
            pc.setMapsList(list);
        }
    }
    
    @Override
    public void deleteTime(int objid) {
        HashMap<Integer, Integer> list = CharMapTimeTable._timeMap.get((Integer)objid);
        if (list != null) {
            list.clear();
        }
        CharMapTimeTable._timeMap.remove((Integer)objid);
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_maps_time` WHERE `char_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            CharMapTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public void saveAllTime() {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            cn.setAutoCommit(false);
            ps = cn.prepareStatement("DELETE FROM `character_maps_time`");
            ps.execute();
            String sql = "INSERT INTO `character_maps_time`SET `char_obj_id`=?,`order_id`=?,`used_time`=?";
            for (Map.Entry<Integer, HashMap<Integer, Integer>> entryList : CharMapTimeTable._timeMap.entrySet()) {
                for (Map.Entry<Integer, Integer> entryList2 : (entryList.getValue()).entrySet()) {
                    PreparedStatement ps_each = cn.prepareStatement("INSERT INTO `character_maps_time`SET `char_obj_id`=?,`order_id`=?,`used_time`=?");
                    try {
                        ps_each.setInt(1, entryList.getKey());
                        ps_each.setInt(2, entryList2.getKey());
                        ps_each.setInt(3, entryList2.getValue());
                        ps_each.execute();
                    }
                    finally {
                        if (ps_each != null) {
                            ps_each.close();
                        }
                    }
                }
            }
            cn.commit();
            cn.setAutoCommit(true);
        }
        catch (SQLException e) {
            try {
                cn.rollback();
            }
            catch (SQLException ex) {}
            CharMapTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public void clearAllTime() {
        for (HashMap<Integer, Integer> list : CharMapTimeTable._timeMap.values()) {
            if (list != null) {
                list.clear();
            }
        }
        CharMapTimeTable._timeMap.clear();
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_maps_time`");
            ps.execute();
        }
        catch (SQLException e) {
            CharMapTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
