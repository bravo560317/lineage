package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import com.lineage.server.datatables.sql.DwarfTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.DwarfStorage;
import java.util.concurrent.locks.Lock;

public class DwarfReading
{
    private final Lock _lock;
    private final DwarfStorage _storage;
    private static DwarfReading _instance;
    
    private DwarfReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new DwarfTable();
    }
    
    public static DwarfReading get() {
        if (DwarfReading._instance == null) {
            DwarfReading._instance = new DwarfReading();
        }
        return DwarfReading._instance;
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
    
    public Map<String, CopyOnWriteArrayList<L1ItemInstance>> allItems() {
        this._lock.lock();
        Map<String, CopyOnWriteArrayList<L1ItemInstance>> tmp = null;
        try {
            tmp = this._storage.allItems();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(String account_name) {
        this._lock.lock();
        CopyOnWriteArrayList<L1ItemInstance> tmp = null;
        try {
            tmp = this._storage.loadItems(account_name);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void delUserItems(String account_name) {
        this._lock.lock();
        try {
            this._storage.delUserItems(account_name);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void insertItem(String account_name, L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.insertItem(account_name, item);
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
    
    public void deleteItem(String account_name, L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.deleteItem(account_name, item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public boolean getUserItems(String account_name, int objid, int count) {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.getUserItems(account_name, objid, count);
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
