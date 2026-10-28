package com.lineage.server.datatables.lock;

import com.lineage.server.datatables.sql.OtherUserTradeTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.OtherUserTradeStorage;
import java.util.concurrent.locks.Lock;

public class OtherUserTitleReading
{
    private final Lock _lock;
    private final OtherUserTradeStorage _storage;
    
    public static OtherUserTitleReading get() {
        return OtherUserTitleReading.Holder.instance;
    }
    
    private OtherUserTitleReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new OtherUserTradeTable();
    }
    
    public void add(String itemname, int itemobjid, int itemadena, long itemcount, int pcobjid, String pcname, int srcpcobjid, String srcpcname, String ip, String acc) {
        this._lock.lock();
        try {
            this._storage.add(itemname, itemobjid, itemadena, itemcount, pcobjid, pcname, srcpcobjid, srcpcname, ip, acc);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    private static class Holder
    {
        static OtherUserTitleReading instance;
        
        static {
            OtherUserTitleReading.Holder.instance = new OtherUserTitleReading();
        }
    }
}
