package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Map;
import com.lineage.server.templates.L1ShopS;
import java.util.HashMap;
import com.lineage.server.datatables.sql.DwarfShopTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.DwarfShopStorage;
import java.util.concurrent.locks.Lock;

public class DwarfShopReading
{
    private final Lock _lock;
    private final DwarfShopStorage _storage;
    private static DwarfShopReading _instance;
    
    private DwarfShopReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new DwarfShopTable();
    }
    
    public static DwarfShopReading get() {
        if (DwarfShopReading._instance == null) {
            DwarfShopReading._instance = new DwarfShopReading();
        }
        return DwarfShopReading._instance;
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
    
    public int nextId() {
        this._lock.lock();
        int tmp = 1;
        try {
            tmp += this._storage.get_id();
            this._storage.set_id(tmp);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public HashMap<Integer, L1ShopS> allShopS() {
        this._lock.lock();
        HashMap tmp = null;
        try {
            tmp = this._storage.allShopS();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public Map<Integer, L1ItemInstance> allItems() {
        this._lock.lock();
        Map tmp = null;
        try {
            tmp = this._storage.allItems();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1ShopS getShopS(int objid) {
        this._lock.lock();
        L1ShopS tmp = null;
        try {
            tmp = this._storage.getShopS(objid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public HashMap<Integer, L1ShopS> getShopSMap(int pcobjid) {
        this._lock.lock();
        HashMap tmp = null;
        try {
            tmp = this._storage.getShopSMap(pcobjid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void insertItem(int key, L1ItemInstance item, L1ShopS shopS) {
        this._lock.lock();
        try {
            this._storage.insertItem(key, item, shopS);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateShopS(L1ShopS shopS) {
        this._lock.lock();
        try {
            this._storage.updateShopS(shopS);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteItem(int key) {
        this._lock.lock();
        try {
            this._storage.deleteItem(key);
        }
        finally {
            this._lock.unlock();
        }
    }
}
