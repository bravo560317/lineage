package com.lineage.config;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.net.InetAddress;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import com.lineage.DatabaseFactoryLogin;
import com.lineage.SerialNumberUtil;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public final class ConfigServer
{ 
    private static final Log _log;
    public static boolean DBClearAll;
    
    static {
        _log = LogFactory.getLog(ConfigServer.class);
    }
  
    public static void loadDB() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int size = 0;
        int i = 0;
        try {
            cn = DatabaseFactoryLogin.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `accall_一鍵洗白`");
            rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String set = rs.getString("設置");
                int set_int = 0;
                boolean set_boolean = false;
                if (set.equalsIgnoreCase("false") || set.equalsIgnoreCase("true")) {
                    set_boolean = rs.getBoolean("設置");
                }
                else {
                    try {
                        set_int = rs.getInt("設置");
                    }
                    catch (Exception ex) {}
                }
                if (id.equalsIgnoreCase("DBClearAll")) {
                    ConfigServer.DBClearAll = set_boolean;
                    ++i;
                }
            }
        }
        catch (SQLException e) {
            ConfigServer._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        ConfigServer._log.info("Config->Server設置: " + size + "筆 / 處理: " + i + "筆");
    }
}
