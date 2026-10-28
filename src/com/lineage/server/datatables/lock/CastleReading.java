package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1Castle;
import java.util.Map;
import com.lineage.server.datatables.sql.CastleTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CastleStorage;
import java.util.concurrent.locks.Lock;

public class CastleReading
{
    private final Lock _lock;
    private final CastleStorage _storage;
    private static CastleReading _instance;
    
    private CastleReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CastleTable();
    }
    
    public static CastleReading get() {
        if (CastleReading._instance == null) {
            CastleReading._instance = new CastleReading();
        }
        return CastleReading._instance;
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
    
    public Map<Integer, L1Castle> getCastleMap() {
        this._lock.lock();
        Map<Integer, L1Castle> tmp;
        try {
            tmp = this._storage.getCastleMap();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Castle[] getCastleTableList() {
        this._lock.lock();
        L1Castle[] tmp;
        try {
            tmp = this._storage.getCastleTableList();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Castle getCastleTable(int id) {
        this._lock.lock();
        L1Castle tmp;
        try {
            tmp = this._storage.getCastleTable(id);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void updateCastle(L1Castle castle) {
        this._lock.lock();
        try {
            this._storage.updateCastle(castle);
        }
        finally {
            this._lock.unlock();
        }
    }
}
