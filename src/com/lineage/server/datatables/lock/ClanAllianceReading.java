package com.lineage.server.datatables.lock;

import com.lineage.server.model.L1Clan;
import java.util.ArrayList;
import com.lineage.server.model.L1Alliance;
import com.lineage.server.datatables.sql.ClanAllianceTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.ClanAllianceStorage;
import java.util.concurrent.locks.Lock;

public class ClanAllianceReading
{
    private final Lock _lock;
    private final ClanAllianceStorage _storage;
    private static ClanAllianceReading _instance;
    
    private ClanAllianceReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new ClanAllianceTable();
    }
    
    public static ClanAllianceReading get() {
        if (ClanAllianceReading._instance == null) {
            ClanAllianceReading._instance = new ClanAllianceReading();
        }
        return ClanAllianceReading._instance;
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
    
    public void insertAlliance(L1Alliance alliance) {
        this._lock.lock();
        try {
            this._storage.insertAlliance(alliance);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateAlliance(int order_id, ArrayList<L1Clan> totalList) {
        this._lock.lock();
        try {
            this._storage.updateAlliance(order_id, totalList);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteAlliance(int order_id) {
        this._lock.lock();
        try {
            this._storage.deleteAlliance(order_id);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public L1Alliance getAlliance(int clan_id) {
        this._lock.lock();
        L1Alliance tmp;
        try {
            tmp = this._storage.getAlliance(clan_id);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
}
