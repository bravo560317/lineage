package com.lineage.server.datatables.sql;

import com.lineage.server.IdFactory;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.config.Config;
import com.lineage.DatabaseFactoryLogin;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.ServerStorage;

public class ServerTable implements ServerStorage
{
    private static final Log _log;
    private int _maxId;
    private int _minId;
    private static int _srcminId;
    
    static {
        _log = LogFactory.getLog(ServerTable.class);
        ServerTable._srcminId = 10000;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `server_info`";
            ps = co.prepareStatement("SELECT * FROM `server_info`");
            rs = ps.executeQuery();
            boolean isInfo = false;
            while (rs.next()) {
                int id = rs.getInt("id");
                if (Config.SERVERNO == id) {
                    isInfo = true;
                    int minid = rs.getInt("minid");
                    int maxid = rs.getInt("maxid");
                    this._minId = minid;
                    if (this._minId < ServerTable._srcminId) {
                        this._minId = ServerTable._srcminId;
                    }
                    this._maxId = maxid;
                    this.set_start();
                }
            }
            if (!isInfo) {
                createServer();
                this._minId = ServerTable._srcminId;
            }
        }
        catch (Exception e) {
            ServerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        ServerTable._log.info("載入服務器存檔資料完成  (" + timer.get() + "ms)");
    }
    
    private static void createServer() {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "INSERT INTO `server_info` SET `id`=?,`minid`=?,`maxid`=?,`start`=?";
            ps = cn.prepareStatement("INSERT INTO `server_info` SET `id`=?,`minid`=?,`maxid`=?,`start`=?");
            int i = 0;
            ps.setInt(++i, Config.SERVERNO);
            ps.setInt(++i, ServerTable._srcminId);
            ps.setInt(++i, 0);
            ps.setBoolean(++i, true);
            ps.execute();
            ServerTable._log.info("新服務器資料表建立 - 編號:" + Config.SERVERNO);
        }
        catch (SQLException e) {
            ServerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void set_start() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "UPDATE `server_info` SET `start`=? WHERE `id`=?";
            pstm = con.prepareStatement("UPDATE `server_info` SET `start`=? WHERE `id`=?");
            int i = 0;
            pstm.setBoolean(++i, true);
            pstm.setInt(++i, Config.SERVERNO);
            pstm.execute();
        }
        catch (Exception e) {
            ServerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void isStop() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "UPDATE `server_info` SET `maxid`=?,`start`=? WHERE `id`=?";
            pstm = con.prepareStatement("UPDATE `server_info` SET `maxid`=?,`start`=? WHERE `id`=?");
            int i = 0;
            pstm.setInt(++i, IdFactory.get().maxId());
            pstm.setBoolean(++i, false);
            pstm.setInt(++i, Config.SERVERNO);
            pstm.execute();
        }
        catch (Exception e) {
            ServerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public int minId() {
        return this._minId;
    }
    
    @Override
    public int maxId() {
        return this._maxId;
    }
}
