package com.lineage.server.datatables.lock;

import com.lineage.server.datatables.sql.MaryTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.MaryStorage;
import java.util.concurrent.locks.Lock;

public class MaryReading
{
    private final Lock _lock;
    private final MaryStorage _storage;
    private static MaryReading _instance;
    
    private MaryReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new MaryTable();
    }
    
    public static MaryReading get() {
        if (MaryReading._instance == null) {
            MaryReading._instance = new MaryReading();
        }
        return MaryReading._instance;
    }
    
    public void load() {
        this._lock.lock();
        try {
            this._storage.load();
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void update(long all_stake, long all_user_prize, int count) {
        this._lock.lock();
        try {
            this._storage.update(all_stake, all_user_prize, count);
        }
        finally {
            this._lock.unlock();
        }
    }
}
