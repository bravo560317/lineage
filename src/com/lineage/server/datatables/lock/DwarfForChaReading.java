package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import com.lineage.server.datatables.sql.DwarfForChaTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.DwarfForChaStorage;
import java.util.concurrent.locks.Lock;

public class DwarfForChaReading
{
    private final Lock _lock;
    private final DwarfForChaStorage _storage;
    private static DwarfForChaReading _instance;
    
    private DwarfForChaReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new DwarfForChaTable();
    }
    
    public static DwarfForChaReading get() {
        if (DwarfForChaReading._instance == null) {
            DwarfForChaReading._instance = new DwarfForChaReading();
        }
        return DwarfForChaReading._instance;
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
    
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(String owner_name) {
        this._lock.lock();
        CopyOnWriteArrayList<L1ItemInstance> tmp = null;
        try {
            tmp = this._storage.loadItems(owner_name);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void delUserItems(String owner_name) {
        this._lock.lock();
        try {
            this._storage.delUserItems(owner_name);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void insertItem(String owner_name, L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.insertItem(owner_name, item);
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
    
    public void deleteItem(String owner_name, L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.deleteItem(owner_name, item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public boolean getUserItems(String owner_name, int objid, int count) {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.getUserItems(owner_name, objid, count);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void updateCharName(String newname, String srcname) {
        this._lock.lock();
        try {
            this._storage.updateCharName(newname, srcname);
        }
        finally {
            this._lock.unlock();
        }
    }
}
