package com.lineage.server.datatables.lock;

import java.util.Map;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.sql.ClanTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.ClanStorage;
import java.util.concurrent.locks.Lock;

public class ClanReading
{
    private final Lock _lock;
    private final ClanStorage _storage;
    private static ClanReading _instance;
    
    private ClanReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new ClanTable();
    }
    
    public static ClanReading get() {
        if (ClanReading._instance == null) {
            ClanReading._instance = new ClanReading();
        }
        return ClanReading._instance;
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
    
    public void addDeClan(Integer integer, L1Clan clan) {
        this._lock.lock();
        try {
            this._storage.addDeClan(integer, clan);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public L1Clan createClan(L1PcInstance player, String clan_name) {
        this._lock.lock();
        L1Clan tmp = null;
        try {
            tmp = this._storage.createClan(player, clan_name);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void updateClan(L1Clan clan) {
        this._lock.lock();
        try {
            this._storage.updateClan(clan);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteClan(String clan_name) {
        this._lock.lock();
        try {
            this._storage.deleteClan(clan_name);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public L1Clan getTemplate(int clan_id) {
        this._lock.lock();
        L1Clan tmp = null;
        try {
            tmp = this._storage.getTemplate(clan_id);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public Map<Integer, L1Clan> get_clans() {
        this._lock.lock();
        Map<Integer, L1Clan> tmp = null;
        try {
            tmp = this._storage.get_clans();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void updateClanSkill(L1Clan clan) {
        this._lock.lock();
        try {
            this._storage.updateClanSkill(clan);
        }
        finally {
            this._lock.unlock();
        }
    }
}
