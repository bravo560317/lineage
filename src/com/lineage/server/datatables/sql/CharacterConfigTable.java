package com.lineage.server.datatables.sql;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Config;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharacterConfigStorage;

public class CharacterConfigTable implements CharacterConfigStorage
{
    private static final Log _log;
    private static final Map<Integer, L1Config> _configList;
    private static CharacterConfigTable _instance;
    
    static {
        _log = LogFactory.getLog(CharacterConfigTable.class);
        _configList = new HashMap();
    }
    
    public static CharacterConfigTable getInstance() {
        if (CharacterConfigTable._instance == null) {
            CharacterConfigTable._instance = new CharacterConfigTable();
        }
        return CharacterConfigTable._instance;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `character_config`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int objid = rs.getInt("object_id");
                if (CharObjidTable.get().isChar(objid) != null) {
                    L1Config l1Configl = new L1Config();
                    l1Configl.setObjid(objid);
                    l1Configl.setLength(rs.getInt("length"));
                    l1Configl.setData(rs.getBytes("data"));
                    CharacterConfigTable._configList.put(objid, l1Configl);
                }
                else {
                    delete(objid);
                }
            }
        }
        catch (SQLException e) {
            CharacterConfigTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        CharacterConfigTable._log.info("載入人物快速鍵紀錄資料數量: " + CharacterConfigTable._configList.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_config` WHERE `object_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            CharacterConfigTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public L1Config get(int objectId) {
        return CharacterConfigTable._configList.get((Integer)objectId);
    }
    
    @Override
    public void storeCharacterConfig(int objectId, int length, byte[] data) {
        L1Config configl = new L1Config();
        configl.setObjid(objectId);
        configl.setLength(length);
        configl.setData(data);
        CharacterConfigTable._configList.put(objectId, configl);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `character_config` SET `object_id`=?,`length`=?,`data`=?");
            int i = 0;
            pstm.setInt(++i, configl.getObjid());
            pstm.setInt(++i, configl.getLength());
            pstm.setBytes(++i, configl.getData());
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterConfigTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateCharacterConfig(int objectId, int length, byte[] data) {
        L1Config configl = CharacterConfigTable._configList.get((Integer)objectId);
        configl.setObjid(objectId);
        configl.setLength(length);
        configl.setData(data);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `character_config` SET `length`=?,`data`=? WHERE `object_id`=?");
            int i = 0;
            pstm.setInt(++i, configl.getLength());
            pstm.setBytes(++i, configl.getData());
            pstm.setInt(++i, configl.getObjid());
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterConfigTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public int countCharacterConfig(int objectId) {
        int result = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT count(*) as cnt FROM character_config WHERE object_id=?");
            pstm.setInt(1, objectId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("cnt");
            }
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return result;
    }
}
