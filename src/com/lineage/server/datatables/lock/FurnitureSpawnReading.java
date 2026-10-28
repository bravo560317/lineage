package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1FurnitureInstance;
import com.lineage.server.datatables.sql.FurnitureSpawnTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.FurnitureSpawnStorage;
import java.util.concurrent.locks.Lock;

public class FurnitureSpawnReading
{
    private final Lock _lock;
    private final FurnitureSpawnStorage _storage;
    private static FurnitureSpawnReading _instance;
    
    private FurnitureSpawnReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new FurnitureSpawnTable();
    }
    
    public static FurnitureSpawnReading get() {
        if (FurnitureSpawnReading._instance == null) {
            FurnitureSpawnReading._instance = new FurnitureSpawnReading();
        }
        return FurnitureSpawnReading._instance;
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
    
    public void insertFurniture(L1FurnitureInstance furniture) {
        this._lock.lock();
        try {
            this._storage.insertFurniture(furniture);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteFurniture(L1FurnitureInstance furniture) {
        this._lock.lock();
        try {
            this._storage.deleteFurniture(furniture);
        }
        finally {
            this._lock.unlock();
        }
    }
}
