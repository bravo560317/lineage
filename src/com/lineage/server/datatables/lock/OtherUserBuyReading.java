package com.lineage.server.datatables.lock;

import com.lineage.server.datatables.sql.OtherUserBuyTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.OtherUserBuyStorage;
import java.util.concurrent.locks.Lock;

public class OtherUserBuyReading
{
    private final Lock _lock;
    private final OtherUserBuyStorage _storage;
    private static OtherUserBuyReading _instance;
    
    private OtherUserBuyReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new OtherUserBuyTable();
    }
    
    public static OtherUserBuyReading get() {
        if (OtherUserBuyReading._instance == null) {
            OtherUserBuyReading._instance = new OtherUserBuyReading();
        }
        return OtherUserBuyReading._instance;
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
