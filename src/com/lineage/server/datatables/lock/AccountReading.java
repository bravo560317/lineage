package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1Account;
import com.lineage.server.datatables.sql.AccountTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.AccountStorage;
import java.util.concurrent.locks.Lock;

public class AccountReading
{
    private final Lock _lock;
    private final AccountStorage _storage;
    private static AccountReading _instance;
    
    private AccountReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new AccountTable();
    }
    
    public static AccountReading get() {
        if (AccountReading._instance == null) {
            AccountReading._instance = new AccountReading();
        }
        return AccountReading._instance;
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
    
    public boolean isAccountUT(String loginName) {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.isAccountUT(loginName);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Account create(String loginName, String pwd, String ip, String host, String spwd) {
        this._lock.lock();
        L1Account tmp = null;
        try {
            tmp = this._storage.create(loginName, pwd, ip, host, spwd);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public boolean isAccount(String loginName) {
        this._lock.lock();
        boolean tmp = false;
        try {
            tmp = this._storage.isAccount(loginName);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Account getAccount(String loginName) {
        this._lock.lock();
        L1Account tmp = null;
        try {
            tmp = this._storage.getAccount(loginName);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void updateWarehouse(String loginName, int pwd) {
        this._lock.lock();
        try {
            this._storage.updateWarehouse(loginName, pwd);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateLastActive(L1Account account) {
        this._lock.lock();
        try {
            this._storage.updateLastActive(account);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateCharacterSlot(String loginName, int count) {
        this._lock.lock();
        try {
            this._storage.updateCharacterSlot(loginName, count);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updatePwd(String loginName, String newpwd) {
        this._lock.lock();
        try {
            this._storage.updatePwd(loginName, newpwd);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateLan(String loginName, boolean islan) {
        this._lock.lock();
        try {
            this._storage.updateLan(loginName, islan);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateLan() {
        this._lock.lock();
        try {
            this._storage.updateLan();
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public String getPlayer(String playName) {
        this._lock.lock();
        String name = null;
        try {
            name = this._storage.getPalyer(playName);
        }
        finally {
            this._lock.unlock();
        }
        return name;
    }
    
    public void updatefp(String loginName, int fp) {
        this._lock.lock();
        try {
            this._storage.updatefp(loginName, fp);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void update_idcard(String loginName, int fp) {
        this._lock.lock();
        try {
            this._storage.update_idcard(loginName, fp);
        }
        finally {
            this._lock.unlock();
        }
    }
}
