package com.lineage;

import java.sql.Connection;
import java.sql.SQLException;
import com.lineage.config.ConfigSQL;
import org.apache.commons.logging.LogFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.logging.Log;

public class DatabaseFactoryLogin
{
    private static final Log _log;
    private static DatabaseFactoryLogin _instance;
    private ComboPooledDataSource _source;
    private static String _driver;
    private static String _url;
    private static String _user;
    private static String _password;
    
    static {
        _log = LogFactory.getLog(DatabaseFactoryLogin.class);
    }
    
    public static void setDatabaseSettings() {
        DatabaseFactoryLogin._driver = ConfigSQL.DB_DRIVER;
        DatabaseFactoryLogin._url = ConfigSQL.DB_URL1_LOGIN + ConfigSQL.DB_URL2_LOGIN + ConfigSQL.DB_URL3_LOGIN;
        DatabaseFactoryLogin._user = ConfigSQL.DB_LOGIN_LOGIN;
        DatabaseFactoryLogin._password = ConfigSQL.DB_PASSWORD_LOGIN;
    }
    
    public DatabaseFactoryLogin() throws SQLException {
        try {
            (this._source = new ComboPooledDataSource()).setDriverClass(DatabaseFactoryLogin._driver);
            this._source.setJdbcUrl(DatabaseFactoryLogin._url);
            this._source.setUser(DatabaseFactoryLogin._user);
            this._source.setPassword(DatabaseFactoryLogin._password);
            this._source.getConnection().close();
        }
        catch (SQLException e) {
            DatabaseFactoryLogin._log.fatal("資料庫讀取錯誤!", e);
        }
        catch (Exception e2) {
            DatabaseFactoryLogin._log.fatal("資料庫讀取錯誤!", e2);
        }
    }
    
    public void shutdown() {
        try {
            this._source.close();
        }
        catch (Exception e) {
            DatabaseFactoryLogin._log.error(e.getLocalizedMessage(), e);
        }
        try {
            this._source = null;
        }
        catch (Exception e) {
            DatabaseFactoryLogin._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static DatabaseFactoryLogin get() throws SQLException {
        if (DatabaseFactoryLogin._instance == null) {
            DatabaseFactoryLogin._instance = new DatabaseFactoryLogin();
        }
        return DatabaseFactoryLogin._instance;
    }
    
    public Connection getConnection() {
        Connection con = null;
        while (con == null) {
            try {
                con = this._source.getConnection();
            }
            catch (SQLException e) {
                DatabaseFactoryLogin._log.error(e.getLocalizedMessage(), e);
            }
        }
        return con;
    }
}
