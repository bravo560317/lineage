package com.lineage.server.datatables.lock;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Board;
import java.util.Map;
import com.lineage.server.datatables.sql.BoardTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.BoardStorage;
import java.util.concurrent.locks.Lock;

public class BoardReading
{
    private final Lock _lock;
    private final BoardStorage _storage;
    private static BoardReading _instance;
    
    private BoardReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new BoardTable();
    }
    
    public static BoardReading get() {
        if (BoardReading._instance == null) {
            BoardReading._instance = new BoardReading();
        }
        return BoardReading._instance;
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
    
    public Map<Integer, L1Board> getBoardMap() {
        this._lock.lock();
        Map<Integer, L1Board> tmp;
        try {
            tmp = this._storage.getBoardMap();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Board[] getBoardTableList() {
        this._lock.lock();
        L1Board[] tmp;
        try {
            tmp = this._storage.getBoardTableList();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Board getBoardTable(int houseId) {
        this._lock.lock();
        L1Board tmp;
        try {
            tmp = this._storage.getBoardTable(houseId);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public int getMaxId() {
        this._lock.lock();
        int tmp = 0;
        try {
            tmp = this._storage.getMaxId();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void writeTopic(L1PcInstance pc, String date, String title, String content) {
        this._lock.lock();
        try {
            this._storage.writeTopic(pc, date, title, content);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteTopic(int number) {
        this._lock.lock();
        try {
            this._storage.deleteTopic(number);
        }
        finally {
            this._lock.unlock();
        }
    }
}
