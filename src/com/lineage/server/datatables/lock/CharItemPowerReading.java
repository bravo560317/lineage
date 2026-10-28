package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.datatables.sql.CharItemsPowerTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharItemsPowerStorage;
import java.util.concurrent.locks.Lock;

public class CharItemPowerReading
{
    private final Lock _lock;
    private final CharItemsPowerStorage _storage;
    private static CharItemPowerReading _instance;
    
    private CharItemPowerReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharItemsPowerTable();
    }
    
    public static CharItemPowerReading get() {
        if (CharItemPowerReading._instance == null) {
            CharItemPowerReading._instance = new CharItemPowerReading();
        }
        return CharItemPowerReading._instance;
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
    
    public void storeItem(int objId, L1ItemPower_name power) {
        this._lock.lock();
        try {
            this._storage.storeItem(objId, power);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItem(int item_obj_id, L1ItemPower_name power) {
        this._lock.lock();
        try {
            this._storage.updateItem(item_obj_id, power);
        }
        finally {
            this._lock.unlock();
        }
    }
}
