package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.sql.CharItemsTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharItemsStorage;
import java.util.concurrent.locks.Lock;

public class CharItemsReading
{
    private final Lock _lock;
    private final CharItemsStorage _storage;
    private static CharItemsReading _instance;
    
    private CharItemsReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharItemsTable();
    }
    
    public static CharItemsReading get() {
        if (CharItemsReading._instance == null) {
            CharItemsReading._instance = new CharItemsReading();
        }
        return CharItemsReading._instance;
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
    
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(Integer objid) {
        this._lock.lock();
        CopyOnWriteArrayList<L1ItemInstance> tmp = null;
        try {
            tmp = this._storage.loadItems(objid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void delUserItems(Integer objid) {
        this._lock.lock();
        try {
            this._storage.delUserItems(objid);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void del_item(int itemid) {
        this._lock.lock();
        try {
            this._storage.del_item(itemid);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void storeItem(int objId, L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.storeItem(objId, item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteItem(int objid, L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.deleteItem(objid, item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemId_Name(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemId_Name(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemId(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemId(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemCount(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemCount(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemDurability(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemDurability(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemChargeCount(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemChargeCount(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemRemainingTime(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemRemainingTime(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemEnchantLevel(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemEnchantLevel(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemEquipped(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemEquipped(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemIdentified(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemIdentified(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemBless(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemBless(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemAttrEnchantKind(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemAttrEnchantKind(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemAttrEnchantLevel(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemAttrEnchantLevel(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateItemDelayEffect(L1ItemInstance item) throws Exception {
        this._lock.lock();
        try {
            this._storage.updateItemDelayEffect(item);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public int getItemCount(int objId) throws Exception {
        this._lock.lock();
        int tmp = 0;
        try {
            tmp = this._storage.getItemCount(objId);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void getAdenaCount(int objId, long count) throws Exception {
        this._lock.lock();
        try {
            this._storage.getAdenaCount(objId, count);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public boolean getUserItems(int pcObjId, int objid, long count) {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.getUserItems(pcObjId, objid, count);
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
    
    public int checkItemId(int itemId) {
        this._lock.lock();
        int tmp = 0;
        try {
            tmp = this._storage.checkItemId(itemId);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
}
