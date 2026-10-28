package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.CharShiftingTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharShiftingStorage;
import java.util.concurrent.locks.Lock;

public class CharShiftingReading
{
    private final Lock _lock;
    private final CharShiftingStorage _storage;
    private static CharShiftingReading _instance;
    
    private CharShiftingReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharShiftingTable();
    }
    
    public static CharShiftingReading get() {
        if (CharShiftingReading._instance == null) {
            CharShiftingReading._instance = new CharShiftingReading();
        }
        return CharShiftingReading._instance;
    }
    
    public void newShifting(L1PcInstance pc, int tgId, String tgName, int srcObjid, L1Item srcItem, L1ItemInstance newItem, int mode) {
        this._lock.lock();
        try {
            this._storage.newShifting(pc, tgId, tgName, srcObjid, srcItem, newItem, mode);
        }
        finally {
            this._lock.unlock();
        }
    }
}
