package com.lineage.server.datatables.lock;

import java.sql.Timestamp;
import com.lineage.server.datatables.sql.CharItemsTimeTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharItemsTimeStorage;
import java.util.concurrent.locks.Lock;

public class CharItemsTimeReading
{
    private final Lock _lock;
    private final CharItemsTimeStorage _storage;
    private static CharItemsTimeReading _instance;
    
    private CharItemsTimeReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharItemsTimeTable();
    }
    
    public static CharItemsTimeReading get() {
        if (CharItemsTimeReading._instance == null) {
            CharItemsTimeReading._instance = new CharItemsTimeReading();
        }
        return CharItemsTimeReading._instance;
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
    
    public void addTime(int itemr_obj_id, Timestamp usertime) {
        this._lock.lock();
        try {
            this._storage.addTime(itemr_obj_id, usertime);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateTime(int itemr_obj_id, Timestamp usertime) {
        this._lock.lock();
        try {
            this._storage.updateTime(itemr_obj_id, usertime);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public boolean isExistTimeData(int itemr_obj_id) {
        this._lock.lock();
        boolean exist = false;
        try {
            exist = this._storage.isExistTimeData(itemr_obj_id);
        }
        finally {
            this._lock.unlock();
        }
        return exist;
    }
}
