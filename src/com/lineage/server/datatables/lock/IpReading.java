package com.lineage.server.datatables.lock;

import java.sql.Timestamp;
import com.lineage.server.datatables.sql.IpTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.IpStorage;
import java.util.concurrent.locks.Lock;

public class IpReading
{
    private final Lock _lock;
    private final IpStorage _storage;
    private static IpReading _instance;
    
    private IpReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new IpTable();
    }
    
    public static IpReading get() {
        if (IpReading._instance == null) {
            IpReading._instance = new IpReading();
        }
        return IpReading._instance;
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
    
    public void setUnbanTime(Timestamp time) {
        this._lock.lock();
        try {
            this._storage.setUnbanTime(time);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void add(String ip, String info) {
        this._lock.lock();
        try {
            this._storage.add(ip, info);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void remove(String ip) {
        this._lock.lock();
        try {
            this._storage.remove(ip);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void checktime(String key) {
        this._lock.lock();
        try {
            this._storage.checktime(key);
        }
        finally {
            this._lock.unlock();
        }
    }
}
