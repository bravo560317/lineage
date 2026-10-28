package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import com.lineage.server.datatables.sql.DwarfForVIPTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.DwarfForVIPStorage;
import java.util.concurrent.locks.Lock;

public class DwarfForVIPReading
{
    private final Lock _lock;
    private final DwarfForVIPStorage _storage;
    private static DwarfForVIPReading _instance;
    
    private DwarfForVIPReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new DwarfForVIPTable();
    }
    
    public static DwarfForVIPReading get() {
        if (DwarfForVIPReading._instance == null) {
            DwarfForVIPReading._instance = new DwarfForVIPReading();
        }
        return DwarfForVIPReading._instance;
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
    
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(String char_name) {
        this._lock.lock();
        CopyOnWriteArrayList<L1ItemInstance> tmp = null;
        try {
            tmp = this._storage.loadItems(char_name);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void delUserItems(String char_name) {
        this._lock.lock();
        try {
            this._storage.delUserItems(char_name);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void insertItem(String char_name, L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.insertItem(char_name, item);
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
    
    public void deleteItem(String char_name, L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.deleteItem(char_name, item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public boolean getUserItems(String char_name, int objid, int count) {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.getUserItems(char_name, objid, count);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
}
