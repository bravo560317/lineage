package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.LogEnchantTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.LogEnchantStorage;
import java.util.concurrent.locks.Lock;

public class LogEnchantReading
{
    private final Lock _lock;
    private final LogEnchantStorage _storage;
    private static LogEnchantReading _instance;
    
    private LogEnchantReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new LogEnchantTable();
    }
    
    public static LogEnchantReading get() {
        if (LogEnchantReading._instance == null) {
            LogEnchantReading._instance = new LogEnchantReading();
        }
        return LogEnchantReading._instance;
    }
    
    public void failureEnchant(L1PcInstance pc, L1ItemInstance item) {
        this._lock.lock();
        try {
            this._storage.failureEnchant(pc, item);
        }
        finally {
            this._lock.unlock();
        }
    }
}
