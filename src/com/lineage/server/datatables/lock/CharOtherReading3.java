package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1PcOther3;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.CharOtherTable3;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharOtherStorage3;
import java.util.concurrent.locks.Lock;

public class CharOtherReading3
{
    private final Lock _lock;
    private final CharOtherStorage3 _storage;
    private static CharOtherReading3 _instance;
    
    private CharOtherReading3() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharOtherTable3();
    }
    
    public static CharOtherReading3 get() {
        if (CharOtherReading3._instance == null) {
            CharOtherReading3._instance = new CharOtherReading3();
        }
        return CharOtherReading3._instance;
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
    
    public L1PcOther3 getOther(L1PcInstance pc) {
        this._lock.lock();
        L1PcOther3 tmp;
        try {
            tmp = this._storage.getOther(pc);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void storeOther(int objId, L1PcOther3 other) {
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
