package com.lineage.server.datatables.lock;

import java.util.Map;
import com.lineage.server.datatables.sql.EzpayTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.EzpayStorage;
import java.util.concurrent.locks.Lock;

public class EzpayReading
{
    private final Lock _lock;
    private final EzpayStorage _storage;
    private static EzpayReading _instance;
    
    private EzpayReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new EzpayTable();
    }
    
    public static EzpayReading get() {
        if (EzpayReading._instance == null) {
            EzpayReading._instance = new EzpayReading();
        }
        return EzpayReading._instance;
    }
    
    public Map<Integer, int[]> ezpayInfo(String loginName) {
        this._lock.lock();
        Map<Integer, int[]> tmp = null;
        try {
            tmp = this._storage.ezpayInfo(loginName);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public boolean update(String loginName, int id, String pcname, String ip) {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.update(loginName, id, pcname, ip);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
}
