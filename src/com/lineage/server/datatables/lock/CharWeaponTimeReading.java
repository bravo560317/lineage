package com.lineage.server.datatables.lock;

import java.sql.Timestamp;
import com.lineage.server.datatables.sql.CharWeaponTimeTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharWeaponStorage;
import java.util.concurrent.locks.Lock;

public class CharWeaponTimeReading
{
    private final Lock _lock;
    private final CharWeaponStorage _storage;
    private static CharWeaponTimeReading _instance;
    
    private CharWeaponTimeReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharWeaponTimeTable();
    }
    
    public static CharWeaponTimeReading get() {
        if (CharWeaponTimeReading._instance == null) {
            CharWeaponTimeReading._instance = new CharWeaponTimeReading();
        }
        return CharWeaponTimeReading._instance;
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
    
    public void addTime(int itemr_obj_id, Timestamp usertime, int magic_weapon) {
        this._lock.lock();
        try {
            this._storage.addTime(itemr_obj_id, usertime, magic_weapon);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateTime(int itemr_obj_id, Timestamp usertime, int magic_weapon, int steps, int same) {
        this._lock.lock();
        try {
            this._storage.updateTime(itemr_obj_id, usertime, magic_weapon, steps, same);
        }
        finally {
            this._lock.unlock();
        }
    }
}
