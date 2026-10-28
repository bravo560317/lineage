package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1House;
import java.util.Map;
import com.lineage.server.datatables.sql.HouseTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.HouseStorage;
import java.util.concurrent.locks.Lock;

public class HouseReading
{
    private final Lock _lock;
    private final HouseStorage _storage;
    private static HouseReading _instance;
    
    private HouseReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new HouseTable();
    }
    
    public static HouseReading get() {
        if (HouseReading._instance == null) {
            HouseReading._instance = new HouseReading();
        }
        return HouseReading._instance;
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
    
    public Map<Integer, L1House> getHouseTableList() {
        this._lock.lock();
        Map<Integer, L1House> tmp;
        try {
            tmp = this._storage.getHouseTableList();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1House getHouseTable(int houseId) {
        this._lock.lock();
        L1House tmp;
        try {
            tmp = this._storage.getHouseTable(houseId);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void updateHouse(L1House house) {
        this._lock.lock();
        try {
            this._storage.updateHouse(house);
        }
        finally {
            this._lock.unlock();
        }
    }
}
