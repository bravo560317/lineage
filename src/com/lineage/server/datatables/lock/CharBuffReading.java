package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.CharBuffTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharBuffStorage;
import java.util.concurrent.locks.Lock;

public class CharBuffReading
{
    private final Lock _lock;
    private final CharBuffStorage _storage;
    private static CharBuffReading _instance;
    
    private CharBuffReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharBuffTable();
    }
    
    public static CharBuffReading get() {
        if (CharBuffReading._instance == null) {
            CharBuffReading._instance = new CharBuffReading();
        }
        return CharBuffReading._instance;
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
    
    public void saveBuff(L1PcInstance pc) {
        this._lock.lock();
        try {
            this._storage.saveBuff(pc);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void buff(L1PcInstance pc) {
        this._lock.lock();
        try {
            this._storage.buff(pc);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteBuff(L1PcInstance pc) {
        this._lock.lock();
        try {
            this._storage.deleteBuff(pc);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteBuff(int objid) {
        this._lock.lock();
        try {
            this._storage.deleteBuff(objid);
        }
        finally {
            this._lock.unlock();
        }
    }
}
