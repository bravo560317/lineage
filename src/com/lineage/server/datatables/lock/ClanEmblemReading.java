package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1EmblemIcon;
import com.lineage.server.datatables.sql.ClanEmblemTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.ClanEmblemStorage;
import java.util.concurrent.locks.Lock;

public class ClanEmblemReading
{
    private final Lock _lock;
    private final ClanEmblemStorage _storage;
    private static ClanEmblemReading _instance;
    
    private ClanEmblemReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new ClanEmblemTable();
    }
    
    public static ClanEmblemReading get() {
        if (ClanEmblemReading._instance == null) {
            ClanEmblemReading._instance = new ClanEmblemReading();
        }
        return ClanEmblemReading._instance;
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
    
    public L1EmblemIcon get(int clan_id) {
        this._lock.lock();
        L1EmblemIcon tmp;
        try {
            tmp = this._storage.get(clan_id);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1EmblemIcon getEmblem(int emblemid) {
        this._lock.lock();
        L1EmblemIcon tmp;
        try {
            tmp = this._storage.getEmblem(emblemid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void addDeclanEmblem(int clan_id, L1EmblemIcon emblemIcon) {
        this._lock.lock();
        try {
            this._storage.addDeclanEmblem(clan_id, emblemIcon);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteIcon(int clan_id) {
        this._lock.lock();
        try {
            this._storage.deleteIcon(clan_id);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public L1EmblemIcon storeClanIcon(int clan_id, byte[] emblemicon, int emblemid) {
        this._lock.lock();
        L1EmblemIcon tmp;
        try {
            tmp = this._storage.storeClanIcon(clan_id, emblemicon, emblemid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void updateClanIcon(L1EmblemIcon emblemIcon) {
        this._lock.lock();
        try {
            this._storage.updateClanIcon(emblemIcon);
        }
        finally {
            this._lock.unlock();
        }
    }
}
