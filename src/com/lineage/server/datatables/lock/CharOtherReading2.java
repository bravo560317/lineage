package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1PcOther2;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.CharOtherTable2;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharOtherStorage2;
import java.util.concurrent.locks.Lock;

public class CharOtherReading2
{
    private final Lock _lock;
    private final CharOtherStorage2 _storage;
    private static CharOtherReading2 _instance;
    
    private CharOtherReading2() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharOtherTable2();
    }
    
    public static CharOtherReading2 get() {
        if (CharOtherReading2._instance == null) {
            CharOtherReading2._instance = new CharOtherReading2();
        }
        return CharOtherReading2._instance;
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
    
    public L1PcOther2 getOther(L1PcInstance pc) {
        this._lock.lock();
        L1PcOther2 tmp;
        try {
            tmp = this._storage.getOther(pc);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void storeOther(int objId, L1PcOther2 other) {
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
