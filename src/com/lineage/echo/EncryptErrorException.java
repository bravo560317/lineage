package com.lineage.echo;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class EncryptErrorException extends Exception
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    
    static {
        _log = LogFactory.getLog(EncryptErrorException.class);
    }
    
    public EncryptErrorException() {
    }
    
    public EncryptErrorException(String string) {
        EncryptErrorException._log.error(string);
    }
    
    public EncryptErrorException(String string, Exception e) {
        EncryptErrorException._log.error(string, e);
    }
}
