package com.lineage.server.datatables.lock;

import java.util.List;
import com.lineage.server.model.L1Spawn;
import java.util.Map;
import java.util.Calendar;
import com.lineage.server.datatables.sql.SpawnBossTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.SpawnBossStorage;
import java.util.concurrent.locks.Lock;

public class SpawnBossReading
{
    private final Lock _lock;
    private final SpawnBossStorage _storage;
    private static SpawnBossReading _instance;
    
    private SpawnBossReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new SpawnBossTable();
    }
    
    public static SpawnBossReading get() {
        if (SpawnBossReading._instance == null) {
            SpawnBossReading._instance = new SpawnBossReading();
        }
        return SpawnBossReading._instance;
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
    
    public void upDateNextSpawnTime(int id, Calendar spawnTime) {
        this._lock.lock();
        try {
            this._storage.upDateNextSpawnTime(id, spawnTime);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public Map<Integer, L1Spawn> get_bossSpawnTable() {
        this._lock.lock();
        Map<Integer, L1Spawn> tmp = null;
        try {
            tmp = this._storage.get_bossSpawnTable();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Spawn getTemplate(int key) {
        this._lock.lock();
        L1Spawn tmp = null;
        try {
            tmp = this._storage.getTemplate(key);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public List<Integer> bossIds() {
        this._lock.lock();
        List<Integer> tmp = null;
        try {
            tmp = this._storage.bossIds();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public List<Integer> bossreid() {
        this._lock.lock();
        List<Integer> tmp = null;
        try {
            tmp = this._storage.bossreid();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
}
