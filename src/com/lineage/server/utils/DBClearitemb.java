package com.lineage.server.utils;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.lineage.DatabaseFactoryLogin;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class DBClearitemb
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(DBClearitemb.class);
    }
    
    public static void start() {
        try {
            Connection cn = null;
            PreparedStatement ps = null;
            try {
                cn = DatabaseFactoryLogin.get().getConnection();
                ps = cn.prepareStatement("TRUNCATE TABLE `系統_裝備贖回`");
                ps.execute();
            }
            catch (SQLException e) {
                DBClearitemb._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(cn);
            }
        }
        catch (Exception e2) {
            DBClearitemb._log.error(e2.getLocalizedMessage(), e2);
        }
    }
}
