package com.lineage.server.datatables.sql;

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
import com.lineage.server.templates.L1BookConfig;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharBookConfigStorage;

public class CharBookConfigTable implements CharBookConfigStorage
{
    private static final Log _log;
    private static final Map<Integer, L1BookConfig> _configList;
    
    static {
        _log = LogFactory.getLog(CharBookConfigTable.class);
        _configList = new ConcurrentHashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `character_teleport_config`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int objid = rs.getInt("object_id");
                if (CharObjidTable.get().isChar(objid) != null) {
                    L1BookConfig bookConfig = new L1BookConfig();
                    bookConfig.setObjid(objid);
                    bookConfig.setMaxSize(rs.getInt("max_size"));
                    bookConfig.setData(rs.getBytes("data"));
                    CharBookConfigTable._configList.put(objid, bookConfig);
                }
                else {
                    delete(objid);
                }
            }
        }
        catch (SQLException e) {
            CharBookConfigTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        CharBookConfigTable._log.info("載入人物記憶座標清單資料數量: " + CharBookConfigTable._configList.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_teleport_config` WHERE `object_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            CharBookConfigTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public L1BookConfig get(int objectId) {
        return CharBookConfigTable._configList.get((Integer)objectId);
    }
    
    @Override
    public L1BookConfig storeCharBookConfig(int objectId, byte[] data) {
        L1BookConfig bookConfig = new L1BookConfig();
        bookConfig.setObjid(objectId);
        bookConfig.setData(data);
        CharBookConfigTable._configList.put(objectId, bookConfig);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `character_teleport_config` SET `object_id`=?,`data`=?");
            int i = 0;
            pstm.setInt(++i, bookConfig.getObjid());
            pstm.setBytes(++i, bookConfig.getData());
            pstm.execute();
        }
        catch (SQLException e) {
            CharBookConfigTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return bookConfig;
    }
    
    @Override
    public void updateCharBookConfig(int objectId, byte[] data) {
        L1BookConfig configl = CharBookConfigTable._configList.get((Integer)objectId);
        configl.setObjid(objectId);
        configl.setData(data);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `character_teleport_config` SET `data`=? WHERE `object_id`=?");
            int i = 0;
            pstm.setBytes(++i, configl.getData());
            pstm.setInt(++i, configl.getObjid());
            pstm.execute();
        }
        catch (SQLException e) {
            CharBookConfigTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateCharBookMaxSize(int objectId, int maxSize) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `character_teleport_config` SET `max_size`=? WHERE `object_id`=?");
            int i = 0;
            pstm.setInt(++i, maxSize);
            pstm.setInt(++i, objectId);
            pstm.execute();
        }
        catch (SQLException e) {
            CharBookConfigTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
