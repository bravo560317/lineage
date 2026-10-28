package com.lineage.server.datatables.lock;

import com.lineage.server.templates.CharQuest;
import java.util.Map;
import com.lineage.server.datatables.sql.CharacterQuestTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharacterQuestStorage;
import java.util.concurrent.locks.Lock;

public class CharacterQuestReading
{
    private final Lock _lock;
    private final CharacterQuestStorage _storage;
    private static CharacterQuestReading _instance;
    
    private CharacterQuestReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharacterQuestTable();
    }
    
    public static CharacterQuestReading get() {
        if (CharacterQuestReading._instance == null) {
            CharacterQuestReading._instance = new CharacterQuestReading();
        }
        return CharacterQuestReading._instance;
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
    
    public Map<Integer, CharQuest> get(int char_id) {
        this._lock.lock();
        Map<Integer, CharQuest> tmp = null;
        try {
            tmp = this._storage.get(char_id);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void storeQuest(int char_id, int key, CharQuest value) {
        this._lock.lock();
        try {
            this._storage.storeQuest(char_id, key, value);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void storeQuest2(int char_id, int key, int value) {
        this._lock.lock();
        try {
            this._storage.storeQuest2(char_id, key, value);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateQuest(int char_id, int key, CharQuest value) {
        this._lock.lock();
        try {
            this._storage.updateQuest(char_id, key, value);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void delQuest(int char_id, int key) {
        this._lock.lock();
        try {
            this._storage.delQuest(char_id, key);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void delQuest2(int key) {
        this._lock.lock();
        try {
            this._storage.delQuest2(key);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void storeQuest(int char_id, int key, CharQuest value, int clear) {
        this._lock.lock();
        try {
            this._storage.storeQuest(char_id, key, value, clear);
        }
        finally {
            this._lock.unlock();
        }
    }
}
