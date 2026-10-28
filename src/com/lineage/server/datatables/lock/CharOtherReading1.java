package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1PcOther1;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.CharOtherTable1;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharOtherStorage1;
import java.util.concurrent.locks.Lock;

public class CharOtherReading1
{
    private final Lock _lock;
    private final CharOtherStorage1 _storage;
    private static CharOtherReading1 _instance;
    
    private CharOtherReading1() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharOtherTable1();
    }
    
    public static CharOtherReading1 get() {
        if (CharOtherReading1._instance == null) {
            CharOtherReading1._instance = new CharOtherReading1();
        }
        return CharOtherReading1._instance;
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
    
    public L1PcOther1 getOther(L1PcInstance pc) {
        this._lock.lock();
        L1PcOther1 tmp;
        try {
            tmp = this._storage.getOther(pc);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void storeOther(int objId, L1PcOther1 other) {
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
