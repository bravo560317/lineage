package com.lineage.server.datatables.lock;

import com.lineage.server.datatables.sql.ServerTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.ServerStorage;
import java.util.concurrent.locks.Lock;

public class ServerReading
{
    private final Lock _lock;
    private final ServerStorage _storage;
    private static ServerReading _instance;
    
    private ServerReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new ServerTable();
    }
    
    public static ServerReading get() {
        if (ServerReading._instance == null) {
            ServerReading._instance = new ServerReading();
        }
        return ServerReading._instance;
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
    
    public int minId() {
        this._lock.lock();
        int temp = 0;
        try {
            temp = this._storage.minId();
        }
        finally {
            this._lock.unlock();
        }
        return temp;
    }
    
    public int maxId() {
        this._lock.lock();
        int temp = 0;
        try {
            temp = this._storage.maxId();
        }
        finally {
            this._lock.unlock();
        }
        return temp;
    }
    
    public void isStop() {
        this._lock.lock();
        try {
            this._storage.isStop();
        }
        finally {
            this._lock.unlock();
        }
    }
}
