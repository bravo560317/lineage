package com.lineage.server.datatables.lock;

import com.lineage.server.datatables.sql.OtherUserSellTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.OtherUserSellStorage;
import java.util.concurrent.locks.Lock;

public class OtherUserSellReading
{
    private final Lock _lock;
    private final OtherUserSellStorage _storage;
    private static OtherUserSellReading _instance;
    
    private OtherUserSellReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new OtherUserSellTable();
    }
    
    public static OtherUserSellReading get() {
        if (OtherUserSellReading._instance == null) {
            OtherUserSellReading._instance = new OtherUserSellReading();
        }
        return OtherUserSellReading._instance;
    }
    
    public void add(String itemname, int itemobjid, int itemadena, long itemcount, int pcobjid, String pcname, int srcpcobjid, String srcpcname) {
        this._lock.lock();
        try {
            this._storage.add(itemname, itemobjid, itemadena, itemcount, pcobjid, pcname, srcpcobjid, srcpcname);
        }
        finally {
            this._lock.unlock();
        }
    }
}
