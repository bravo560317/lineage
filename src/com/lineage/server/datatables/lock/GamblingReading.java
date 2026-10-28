package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1Gambling;
import com.lineage.server.datatables.sql.GamblingTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.GamblingStorage;
import java.util.concurrent.locks.Lock;

public class GamblingReading
{
    private final Lock _lock;
    private final GamblingStorage _storage;
    private static GamblingReading _instance;
    
    private GamblingReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new GamblingTable();
    }
    
    public static GamblingReading get() {
        if (GamblingReading._instance == null) {
            GamblingReading._instance = new GamblingReading();
        }
        return GamblingReading._instance;
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
    
    public L1Gambling getGambling(String key) {
        this._lock.lock();
        L1Gambling tmp;
        try {
            tmp = this._storage.getGambling(key);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Gambling getGambling(int key) {
        this._lock.lock();
        L1Gambling tmp;
        try {
            tmp = this._storage.getGambling(key);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void add(L1Gambling gambling) {
        this._lock.lock();
        try {
            this._storage.add(gambling);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateGambling(int id, int outcount) {
        this._lock.lock();
        try {
            this._storage.updateGambling(id, outcount);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public int[] winCount(int npcid) {
        this._lock.lock();
        int[] tmp;
        try {
            tmp = this._storage.winCount(npcid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public int maxId() {
        this._lock.lock();
        int tmp;
        try {
            tmp = this._storage.maxId();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
}
