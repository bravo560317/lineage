package com.lineage.server.datatables.lock;

import java.util.Map;
import com.lineage.server.datatables.sql.shop_lx;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.shop_lxStorage;
import java.util.concurrent.locks.Lock;

public class shop_lxReading
{
    private final Lock _lock;
    private final shop_lxStorage _storage;
    private static shop_lxReading _instance;
    
    private shop_lxReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new shop_lx();
    }
    
    public static shop_lxReading get() {
        if (shop_lxReading._instance == null) {
            shop_lxReading._instance = new shop_lxReading();
        }
        return shop_lxReading._instance;
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
    
    public void updatemejs(int mac, String name) {
        this._lock.lock();
        try {
            this._storage.updatemejs(mac, name);
        }
        finally {
            this._lock.unlock();
        }
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
    
    public boolean update() {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.update();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public boolean isExist(String mac) {
        boolean isExist = false;
        this._lock.lock();
        try {
            int count = this._storage.count(mac);
            if (count > 0) {
                isExist = true;
            }
        }
        finally {
            this._lock.unlock();
        }
        return isExist;
    }
    
    public void remove(String string) {
        this._lock.lock();
        try {
            this._storage.remove(string);
        }
        finally {
            this._lock.unlock();
        }
    }
}
