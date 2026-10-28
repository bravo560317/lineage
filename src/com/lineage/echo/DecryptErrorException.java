package com.lineage.echo;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class DecryptErrorException extends Exception
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    
    static {
        _log = LogFactory.getLog(DecryptErrorException.class);
    }
    
    public DecryptErrorException() {
    }
    
    public DecryptErrorException(String string) {
        DecryptErrorException._log.error(string);
    }
    
    public DecryptErrorException(String string, Exception e) {
        DecryptErrorException._log.error(string, e);
    }
}
