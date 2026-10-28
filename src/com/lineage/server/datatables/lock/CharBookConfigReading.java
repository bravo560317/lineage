package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1BookConfig;
import com.lineage.server.datatables.sql.CharBookConfigTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharBookConfigStorage;
import java.util.concurrent.locks.Lock;

public class CharBookConfigReading
{
    private final Lock _lock;
    private final CharBookConfigStorage _storage;
    private static CharBookConfigReading _instance;
    
    private CharBookConfigReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharBookConfigTable();
    }
    
    public static CharBookConfigReading get() {
        if (CharBookConfigReading._instance == null) {
            CharBookConfigReading._instance = new CharBookConfigReading();
        }
        return CharBookConfigReading._instance;
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
    
    public L1BookConfig get(int objectId) {
        this._lock.lock();
        L1BookConfig tmp;
        try {
            tmp = this._storage.get(objectId);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1BookConfig storeCharBookConfig(int objectId, byte[] data) {
        this._lock.lock();
        L1BookConfig tmp;
        try {
            tmp = this._storage.storeCharBookConfig(objectId, data);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void updateCharBookConfig(int objectId, byte[] data) {
        this._lock.lock();
        try {
            this._storage.updateCharBookConfig(objectId, data);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateCharBookMaxSize(int objectId, int maxSize) {
        this._lock.lock();
        try {
            this._storage.updateCharBookMaxSize(objectId, maxSize);
        }
        finally {
            this._lock.unlock();
        }
    }
}
