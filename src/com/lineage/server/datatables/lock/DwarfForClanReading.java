package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.sql.DwarfForClanTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.DwarfForClanStorage;
import java.util.concurrent.locks.Lock;

public class DwarfForClanReading
{
    private final Lock _lock;
    private final DwarfForClanStorage _storage;
    private static DwarfForClanReading _instance;
    
    private DwarfForClanReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new DwarfForClanTable();
    }
    
    public static DwarfForClanReading get() {
        if (DwarfForClanReading._instance == null) {
            DwarfForClanReading._instance = new DwarfForClanReading();
        }
        return DwarfForClanReading._instance;
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
    
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(String clan_name) {
        this._lock.lock();
        CopyOnWriteArrayList tmp = null;
        try {
            tmp = this._storage.loadItems(clan_name);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void delUserItems(String clan_name) {
        this._lock.lock();
        try {
            this._storage.delUserItems(clan_name);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void insertItem(String clan_name, L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.insertItem(clan_name, item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItem(L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.updateItem(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteItem(String clan_name, L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.deleteItem(clan_name, item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public boolean getUserItems(String clan_name, int objid, int count) {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.getUserItems(clan_name, objid, count);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public boolean getUserItem(int objid) {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.getUserItem(objid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
}
