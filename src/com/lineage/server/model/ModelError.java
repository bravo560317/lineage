package com.lineage.server.model;

import org.apache.commons.logging.Log;
import com.lineage.config.Config;

public class ModelError
{
    private static boolean _debug;
    
    static {
        ModelError._debug = Config.DEBUG;
    }
    
    public static void isError(Log log, String string, Exception e) {
        if (ModelError._debug) {
            log.error(string, e);
        }
    }
}
