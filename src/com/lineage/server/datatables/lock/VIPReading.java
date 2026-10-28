package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.Timestamp;
import java.util.Map;
import com.lineage.server.datatables.sql.VIPTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.VIPStorage;
import java.util.concurrent.locks.Lock;

public class VIPReading
{
    private final Lock _lock;
    private final VIPStorage _storage;
    private static VIPReading _instance;
    
    private VIPReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new VIPTable();
    }
    
    public static VIPReading get() {
        if (VIPReading._instance == null) {
            VIPReading._instance = new VIPReading();
        }
        return VIPReading._instance;
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
    
    public Map<Integer, Timestamp> map() {
        this._lock.lock();
        Map<Integer, Timestamp> tmp;
        try {
            tmp = this._storage.map();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public Timestamp getOther(L1PcInstance pc) {
        this._lock.lock();
        Timestamp tmp;
        try {
            tmp = this._storage.getOther(pc);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void storeOther(int key, Timestamp value) {
        this._lock.lock();
        try {
            this._storage.storeOther(key, value);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void delOther(int key) {
        this._lock.lock();
        try {
            this._storage.delOther(key);
        }
        finally {
            this._lock.unlock();
        }
    }
}