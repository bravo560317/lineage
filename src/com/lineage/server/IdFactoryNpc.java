package com.lineage.server;

import org.apache.commons.logging.LogFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.Log;

public class IdFactoryNpc
{
    private static final Log _log;
    private static IdFactoryNpc _instance;
    private Object _monitor;
    private AtomicInteger _nextId;
    
    static {
        _log = LogFactory.getLog(IdFactoryNpc.class);
    }
    
    public static IdFactoryNpc get() {
        if (IdFactoryNpc._instance == null) {
            IdFactoryNpc._instance = new IdFactoryNpc();
        }
        return IdFactoryNpc._instance;
    }
    
    public IdFactoryNpc() {
        try {
            this._monitor = new Object();
            this._nextId = new AtomicInteger(2000000000);
        }
        catch (Exception e) {
            IdFactoryNpc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int nextId() {
        synchronized (this._monitor) {
            int andIncrement = this._nextId.getAndIncrement();
            return andIncrement;
        }
    }
    
    public int maxId() {
        synchronized (this._monitor) {
            int value = this._nextId.get();
            return value;
        }
    }
}
