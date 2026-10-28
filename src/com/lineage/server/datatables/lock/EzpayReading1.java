package com.lineage.server.datatables.lock;

import java.util.Map;
import com.lineage.server.datatables.sql.EzpayTable1;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.EzpayStorage1;
import java.util.concurrent.locks.Lock;

public class EzpayReading1
{
    private final Lock _lock;
    private final EzpayStorage1 _storage;
    private static EzpayReading1 _instance;
    
    private EzpayReading1() {
        this._lock = new ReentrantLock(true);
        this._storage = new EzpayTable1();
    }
    
    public static EzpayReading1 get() {
        if (EzpayReading1._instance == null) {
            EzpayReading1._instance = new EzpayReading1();
        }
        return EzpayReading1._instance;
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
    
    public int[] ezpayInfo(String loginName, int id) {
        this._lock.lock();
        int[] tmp = null;
        try {
            tmp = this._storage.ezpayInfo(loginName, id);
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
