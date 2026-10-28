package com.lineage.server.datatables.sql;

import org.apache.commons.logging.Log;
import com.lineage.config.Config;

public class SqlError
{
    private static boolean _debug;
    
    static {
        SqlError._debug = Config.DEBUG;
    }
    
    public static void isError(Log log, String string, Exception e) {
        if (SqlError._debug) {
            log.error(string, e);
        }
    }
}
