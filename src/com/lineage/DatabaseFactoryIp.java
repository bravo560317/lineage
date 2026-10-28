package com.lineage;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.logging.LogFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.logging.Log;

public class DatabaseFactoryIp
{
    private static final Log _log;
    private ComboPooledDataSource _source;
    private static String _driver;
    private static String _url;
    private static String _user;
    private static String _password;
    
    static {
        _log = LogFactory.getLog(DatabaseFactoryIp.class);
    }
    
    public static DatabaseFactoryIp get() {
        return DatabaseFactoryIp.Holder.instance;
    }
    
    private DatabaseFactoryIp() {
        try {
            (this._source = new ComboPooledDataSource()).setDriverClass(DatabaseFactoryIp._driver);
            this._source.setJdbcUrl(DatabaseFactoryIp._url);
            this._source.setUser(DatabaseFactoryIp._user);
            this._source.setPassword(DatabaseFactoryIp._password);
            this._source.getConnection().close();
        }
        catch (SQLException ex) {}
        catch (Exception ex2) {}
    }
    
    public static void setDatabaseSettings() {
        DatabaseFactoryIp._driver = "com.mysql.jdbc.Driver";
        DatabaseFactoryIp._url = "jdbc:mysql://43.254.16.35:3306/阿維驗證?useUnicode=true&characterEncoding=utf8";
        DatabaseFactoryIp._user = "gm01";
        DatabaseFactoryIp._password = "qazzwsxx123";
    }
    
    public void shutdown() {
        try {
            this._source.close();
        }
        catch (Exception ex) {}
        try {
            this._source = null;
        }
        catch (Exception ex2) {}
    }
    
    public Connection getConnection() {
        Connection con = null;
        while (con == null) {
            try {
                con = this._source.getConnection();
            }
            catch (SQLException ex) {}
        }
        return con;
    }
    
    private static class Holder
    {
        static DatabaseFactoryIp instance;
        
        static {
            DatabaseFactoryIp.Holder.instance = new DatabaseFactoryIp();
        }
    }
}