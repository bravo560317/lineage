package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Map;
import com.lineage.server.datatables.sql.CharMapTimeTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharMapTimeStorage;
import java.util.concurrent.locks.Lock;

public class CharMapTimeReading
{
    private final Lock _lock;
    private final CharMapTimeStorage _storage;
    private static CharMapTimeReading _instance;
    
    private CharMapTimeReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharMapTimeTable();
    }
    
    public static CharMapTimeReading get() {
        if (CharMapTimeReading._instance == null) {
            CharMapTimeReading._instance = new CharMapTimeReading();
        }
        return CharMapTimeReading._instance;
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
    
    public Map<Integer, Integer> addTime(int objId, int order_id, int used_time) {
        this._lock.lock();
        Map<Integer, Integer> tmp;
        try {
            tmp = this._storage.addTime(objId, order_id, used_time);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void getTime(L1PcInstance pc) {
        this._lock.lock();
        try {
            this._storage.getTime(pc);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteTime(int objid) {
        this._lock.lock();
        try {
            this._storage.deleteTime(objid);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void saveAllTime() {
        this._lock.lock();
        try {
            this._storage.saveAllTime();
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void clearAllTime() {
        this._lock.lock();
        try {
            this._storage.clearAllTime();
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void update(int objid, int mapid, int time) {
        this._lock.lock();
        try {
            this._storage.update(objid, mapid, time);
        }
        finally {
            this._lock.unlock();
        }
    }
}
