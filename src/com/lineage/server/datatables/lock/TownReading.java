package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Town;
import com.lineage.server.datatables.sql.TownTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.TownStorage;
import java.util.concurrent.locks.Lock;

public class TownReading
{
    private final Lock _lock;
    private final TownStorage _storage;
    private static TownReading _instance;
    
    private TownReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new TownTable();
    }
    
    public static TownReading get() {
        if (TownReading._instance == null) {
            TownReading._instance = new TownReading();
        }
        return TownReading._instance;
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
    
    public L1Town[] getTownTableList() {
        this._lock.lock();
        L1Town[] tmp;
        try {
            tmp = this._storage.getTownTableList();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Town getTownTable(int id) {
        this._lock.lock();
        L1Town tmp;
        try {
            tmp = this._storage.getTownTable(id);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public boolean isLeader(L1PcInstance pc, int town_id) {
        this._lock.lock();
        boolean tmp;
        try {
            tmp = this._storage.isLeader(pc, town_id);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void addSalesMoney(int town_id, int salesMoney) {
        this._lock.lock();
        try {
            this._storage.addSalesMoney(town_id, salesMoney);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateTaxRate() {
        this._lock.lock();
        try {
            this._storage.updateTaxRate();
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateSalesMoneyYesterday() {
        this._lock.lock();
        try {
            this._storage.updateSalesMoneyYesterday();
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public String totalContribution(int townId) {
        this._lock.lock();
        String tmp;
        try {
            tmp = this._storage.totalContribution(townId);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void clearHomeTownID() {
        this._lock.lock();
        try {
            this._storage.clearHomeTownID();
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public int getPay(int objid) {
        this._lock.lock();
        int tmp = 0;
        try {
            this._storage.getPay(objid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
}
