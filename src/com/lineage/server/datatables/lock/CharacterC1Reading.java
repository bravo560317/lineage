package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1User_Power;
import com.lineage.server.datatables.sql.CharacterC1Table;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharacterC1Storage;
import java.util.concurrent.locks.Lock;

public class CharacterC1Reading
{
    private final Lock _lock;
    private final CharacterC1Storage _storage;
    private static CharacterC1Reading _instance;
    
    private CharacterC1Reading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharacterC1Table();
    }
    
    public static CharacterC1Reading get() {
        if (CharacterC1Reading._instance == null) {
            CharacterC1Reading._instance = new CharacterC1Reading();
        }
        return CharacterC1Reading._instance;
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
    
    public L1User_Power get(int objectId) {
        this._lock.lock();
        L1User_Power tmp;
        try {
            tmp = this._storage.get(objectId);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void storeCharacterC1(L1PcInstance pc) {
        this._lock.lock();
        try {
            this._storage.storeCharacterC1(pc);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateCharacterC1(int object_id, int c1_type, String note) {
        this._lock.lock();
        try {
            this._storage.updateCharacterC1(object_id, c1_type, note);
        }
        finally {
            this._lock.unlock();
        }
    }
}
