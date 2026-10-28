package com.lineage.server.datatables.lock;

import com.lineage.server.datatables.sql.UpdateLocTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.UpdateLocStorage;
import java.util.concurrent.locks.Lock;

public class UpdateLocReading
{
    private final Lock _lock;
    private final UpdateLocStorage _storage;
    private static UpdateLocReading _instance;
    
    private UpdateLocReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new UpdateLocTable();
    }
    
    public static UpdateLocReading get() {
        if (UpdateLocReading._instance == null) {
            UpdateLocReading._instance = new UpdateLocReading();
        }
        return UpdateLocReading._instance;
    }
    
    public void setPcLoc(String accName) {
        this._lock.lock();
        try {
            this._storage.setPcLoc(accName);
        }
        finally {
            this._lock.unlock();
        }
    }
}
