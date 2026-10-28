package com.lineage.config;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class ConfigErrorException extends Exception
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    
    static {
        _log = LogFactory.getLog(ConfigErrorException.class);
    }
    
    public ConfigErrorException() {
    }
    
    public ConfigErrorException(String string) {
        ConfigErrorException._log.error(string);
    }
}
