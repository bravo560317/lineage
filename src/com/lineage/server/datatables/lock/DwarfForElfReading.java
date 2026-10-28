package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.sql.DwarfForElfTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.DwarfForElfStorage;
import java.util.concurrent.locks.Lock;

public class DwarfForElfReading
{
    private final Lock _lock;
    private final DwarfForElfStorage _storage;
    private static DwarfForElfReading _instance;
    
    private DwarfForElfReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new DwarfForElfTable();
    }
    
    public static DwarfForElfReading get() {
        if (DwarfForElfReading._instance == null) {
            DwarfForElfReading._instance = new DwarfForElfReading();
        }
        return DwarfForElfReading._instance;
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
    
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(String account_name) {
        this._lock.lock();
        CopyOnWriteArrayList tmp = null;
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
}
