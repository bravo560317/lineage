package com.lineage.server.datatables.lock;

import java.util.Map;
import com.lineage.server.templates.L1Bank;
import com.lineage.server.datatables.sql.AccountBankTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.AccountBankStorage;
import java.util.concurrent.locks.Lock;

public class AccountBankReading
{
    private final Lock _lock;
    private final AccountBankStorage _storage;
    private static AccountBankReading _instance;
    
    private AccountBankReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new AccountBankTable();
    }
    
    public static AccountBankReading get() {
        if (AccountBankReading._instance == null) {
            AccountBankReading._instance = new AccountBankReading();
        }
        return AccountBankReading._instance;
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
    
    public L1Bank get(String account_name) {
        this._lock.lock();
        L1Bank tmp = null;
        try {
            tmp = this._storage.get(account_name);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public Map<String, L1Bank> map() {
        this._lock.lock();
        Map tmp = null;
        try {
            tmp = this._storage.map();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void create(String loginName, L1Bank bank) {
        this._lock.lock();
        try {
            this._storage.create(loginName, bank);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updatePass(String loginName, String pwd) {
        this._lock.lock();
        try {
            this._storage.updatePass(loginName, pwd);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateAdena(String loginName, long adena) {
        this._lock.lock();
        try {
            this._storage.updateAdena(loginName, adena);
        }
        finally {
            this._lock.unlock();
        }
    }
}
