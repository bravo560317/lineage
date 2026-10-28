package com.lineage;

import java.sql.Connection;
import java.sql.SQLException;
import com.lineage.config.ConfigSQL;
import org.apache.commons.logging.LogFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.logging.Log;

public class DatabaseFactory
{
    private static final Log _log;
    private static DatabaseFactory _instance;
    private ComboPooledDataSource _source;
    private static String _driver;
    private static String _url;
    private static String _user;
    private static String _password;
    
    static {
        _log = LogFactory.getLog(DatabaseFactory.class);
    }
    
    public static void setDatabaseSettings() {
        DatabaseFactory._driver = ConfigSQL.DB_DRIVER;
        DatabaseFactory._url = ConfigSQL.DB_URL1 + ConfigSQL.DB_URL2 + ConfigSQL.DB_URL3;
        DatabaseFactory._user = ConfigSQL.DB_LOGIN;
        DatabaseFactory._password = ConfigSQL.DB_PASSWORD;
    }
    
    public DatabaseFactory() throws SQLException {
        try {
            (this._source = new ComboPooledDataSource()).setDriverClass(DatabaseFactory._driver);
            this._source.setJdbcUrl(DatabaseFactory._url);
            this._source.setUser(DatabaseFactory._user);
            this._source.setPassword(DatabaseFactory._password);
            
            _log.info("【c3p0 連線池參數】");
            _log.info("initialPoolSize = " + _source.getInitialPoolSize());
            _log.info("minPoolSize = " + _source.getMinPoolSize());
            _log.info("maxPoolSize = " + _source.getMaxPoolSize());
            _log.info("acquireIncrement = " + _source.getAcquireIncrement());
            _log.info("checkoutTimeout = " + _source.getCheckoutTimeout());
            _log.info("idleConnectionTestPeriod = " + _source.getIdleConnectionTestPeriod());
            _log.info("maxIdleTime = " + _source.getMaxIdleTime());
            _log.info("maxStatementsPerConnection = " + _source.getMaxStatementsPerConnection());
            _log.info("acquireRetryAttempts = " + _source.getAcquireRetryAttempts());
            _log.info("acquireRetryDelay = " + _source.getAcquireRetryDelay());
            _log.info("autoCommitOnClose = " + _source.isAutoCommitOnClose());

            
            this._source.getConnection().close();
        }
        catch (SQLException e) {
            DatabaseFactory._log.fatal("資料庫讀取錯誤!", e);
        }
        catch (Exception e2) {
            DatabaseFactory._log.fatal("資料庫讀取錯誤!", e2);
        }
    }
    
    public void shutdown() {
        try {
            this._source.close();
        }
        catch (Exception e) {
            DatabaseFactory._log.error(e.getLocalizedMessage(), e);
        }
        try {
            this._source = null;
        }
        catch (Exception e) {
            DatabaseFactory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static DatabaseFactory get() throws SQLException {
        if (DatabaseFactory._instance == null) {
            DatabaseFactory._instance = new DatabaseFactory();
        }
        return DatabaseFactory._instance;
    }
    
    public Connection getConnection() {
        Connection con = null;
        while (con == null) {
            try {
                con = this._source.getConnection();
            }
            catch (SQLException e) {
                DatabaseFactory._log.error(e.getLocalizedMessage(), e);
            }
        }
        return con;
    }
public static DatabaseFactory getInstance() throws SQLException {
	if (_instance == null)
		_instance = new DatabaseFactory();
	return _instance;
}
}
