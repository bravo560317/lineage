package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1PcOther;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.CharOtherTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharOtherStorage;
import java.util.concurrent.locks.Lock;

public class CharOtherReading
{
    private final Lock _lock;
    private final CharOtherStorage _storage;
    private static CharOtherReading _instance;
    
    private CharOtherReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharOtherTable();
    }
    
    public static CharOtherReading get() {
        if (CharOtherReading._instance == null) {
            CharOtherReading._instance = new CharOtherReading();
        }
        return CharOtherReading._instance;
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
    
    public L1PcOther getOther(L1PcInstance pc) {
        this._lock.lock();
        L1PcOther tmp;
        try {
            tmp = this._storage.getOther(pc);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void storeOther(int objId, L1PcOther other) {
        this._lock.lock();
        try {
            this._storage.storeOther(objId, other);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void tam() {
        this._lock.lock();
        try {
            this._storage.tam();
        }
        finally {
            this._lock.unlock();
        }
    }
}
