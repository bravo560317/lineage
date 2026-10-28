package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.sql.ServerCnInfoTable;
import java.util.concurrent.locks.Lock;

public class ServerCnInfoReading
{
    private final Lock _lock;
    private final ServerCnInfoTable _storage;
    private static ServerCnInfoReading _instance;
    
    private ServerCnInfoReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new ServerCnInfoTable();
    }
    
    public static ServerCnInfoReading get() {
        if (ServerCnInfoReading._instance == null) {
            ServerCnInfoReading._instance = new ServerCnInfoReading();
        }
        return ServerCnInfoReading._instance;
    }
    
    public void create(L1PcInstance pc, L1Item itemtmp, long count, boolean mode, int itemid_cn) {
        this._lock.lock();
        try {
            this._storage.create(pc, itemtmp, count, mode, itemid_cn);
        }
        finally {
            this._lock.unlock();
        }
    }
}
