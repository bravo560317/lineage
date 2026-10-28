package com.lineage.server.datatables.lock;

import com.lineage.server.datatables.sql.BadBuddyTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.BadBuddyStorage;
import java.util.concurrent.locks.Lock;

public class BadBuddyReading
{
    private final Lock _lock;
    private final BadBuddyStorage _storage;
    private static BadBuddyReading _instance;
    
    private BadBuddyReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new BadBuddyTable();
    }
    
    public static BadBuddyReading get() {
        if (BadBuddyReading._instance == null) {
            BadBuddyReading._instance = new BadBuddyReading();
        }
        return BadBuddyReading._instance;
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
    
    public boolean check(int pc_id, String name) {
        this._lock.lock();
        boolean tmp;
        try {
            tmp = this._storage.check(pc_id, name);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void addBuddy(int charId, int objId, String name) {
        this._lock.lock();
        try {
            this._storage.addBuddy(charId, objId, name);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void removeBuddy(int charId, String buddyName) {
        this._lock.lock();
        try {
            this._storage.removeBuddy(charId, buddyName);
        }
        finally {
            this._lock.unlock();
        }
    }
}
