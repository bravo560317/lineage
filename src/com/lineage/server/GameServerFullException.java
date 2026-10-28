package com.lineage.server;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class GameServerFullException extends Exception
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    
    static {
        _log = LogFactory.getLog(GameServer.class);
    }
    
    public GameServerFullException() {
    }
    
    public GameServerFullException(String string) {
        super(string);
        GameServerFullException._log.error(string);
    }
}
