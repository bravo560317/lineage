package com.lineage.data;

import org.apache.commons.logging.Log;
import com.lineage.config.Config;

public class DataError
{
    private static boolean _debug;
    
    static {
        DataError._debug = Config.DEBUG;
    }
    
    public static void isError(Log log, String string, Exception e) {
        if (DataError._debug) {
            log.error(string, e);
        }
    }
}
