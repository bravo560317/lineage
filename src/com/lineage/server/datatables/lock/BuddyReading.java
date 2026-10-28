package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1BuddyTmp;
import java.util.ArrayList;
import com.lineage.server.datatables.sql.BuddyTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.BuddyStorage;
import java.util.concurrent.locks.Lock;

public class BuddyReading
{
    private final Lock _lock;
    private final BuddyStorage _storage;
    private static BuddyReading _instance;
    
    private BuddyReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new BuddyTable();
    }
    
    public static BuddyReading get() {
        if (BuddyReading._instance == null) {
            BuddyReading._instance = new BuddyReading();
        }
        return BuddyReading._instance;
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
    
    public ArrayList<L1BuddyTmp> userBuddy(int playerobjid) {
        this._lock.lock();
        ArrayList<L1BuddyTmp> tmp;
        try {
            tmp = this._storage.userBuddy(playerobjid);
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
