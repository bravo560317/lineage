package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1Config;
import com.lineage.server.datatables.sql.CharacterConfigTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharacterConfigStorage;
import java.util.concurrent.locks.Lock;

public class CharacterConfigReading
{
    private final Lock _lock;
    private final CharacterConfigStorage _storage;
    private static CharacterConfigReading _instance;
    
    private CharacterConfigReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharacterConfigTable();
    }
    
    public static CharacterConfigReading get() {
        if (CharacterConfigReading._instance == null) {
            CharacterConfigReading._instance = new CharacterConfigReading();
        }
        return CharacterConfigReading._instance;
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
    
    public L1Config get(int objectId) {
        this._lock.lock();
        L1Config tmp;
        try {
            tmp = this._storage.get(objectId);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void storeCharacterConfig(int objectId, int length, byte[] data) {
        this._lock.lock();
        try {
            this._storage.storeCharacterConfig(objectId, length, data);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateCharacterConfig(int objectId, int length, byte[] data) {
        this._lock.lock();
        try {
            this._storage.updateCharacterConfig(objectId, length, data);
        }
        finally {
            this._lock.unlock();
        }
    }
}
