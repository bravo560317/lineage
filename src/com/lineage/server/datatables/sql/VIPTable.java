package com.lineage.server.datatables.sql;

import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import java.sql.Timestamp;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.VIPStorage;

public class VIPTable implements VIPStorage {
    private static final Log _log;
    private static final Map<Integer, Timestamp> _vipMap;
    
    // ★ 補上這段讓外部可以呼叫 get()
    private static VIPTable _instance;
    public static VIPTable get() {
        if (_instance == null) {
            _instance = new VIPTable();
        }
        return _instance;
    }
    static {
        _log = LogFactory.getLog(VIPTable.class);
        _vipMap = new ConcurrentHashMap<>();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_vip`");
            rs = ps.executeQuery();
            _vipMap.clear(); // 重新載入時先清空快取
            while (rs.next()) {
                int char_obj_id = rs.getInt("char_obj_id");
                Timestamp overtime = rs.getTimestamp("overtime");
                if (overtime != null) {
                    // 安全直接載入，不再受 CharObjidTable 載入順序影響而誤刪
                    _vipMap.put(char_obj_id, overtime);
                }
            }
        }
        catch (SQLException e) {
            VIPTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        VIPTable._log.info("載入VIP資料數量: " + VIPTable._vipMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_vip` WHERE `char_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
            VIPTable._vipMap.remove((Integer)objid);
        }
        catch (SQLException e) {
            VIPTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public Map<Integer, Timestamp> map() {
        return VIPTable._vipMap;
    }
    
    @Override
    public Timestamp getOther(L1PcInstance pc) {
        return VIPTable._vipMap.get(pc.getId());
    }
    
    @Override
    public void storeOther(int key, Timestamp value) {
        // 直接更新記憶體與資料庫 (利用 ON DUPLICATE KEY UPDATE)
        VIPTable._vipMap.put(key, value);
        addNewOther(key, value);
    }
    
    @Override
    public void delOther(int key) {
        if (VIPTable._vipMap.remove((Integer)key) != null) {
            delete(key);
        }
    }
    
    private static void updateOther(int objId, Timestamp other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_vip` SET `overtime`=? WHERE `char_obj_id`=?");
            int i = 0;
            ps.setTimestamp(++i, other);
            ps.setInt(++i, objId);
            ps.execute();
        }
        catch (SQLException e) {
            VIPTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addNewOther(int objId, Timestamp other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `character_vip` SET `char_obj_id`=?, `overtime`=? ON DUPLICATE KEY UPDATE `overtime`=?");
            int i = 0;
            ps.setInt(++i, objId);
            ps.setTimestamp(++i, other);
            ps.setTimestamp(++i, other);
            ps.execute();
        }
        catch (SQLException e) {
            VIPTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
}