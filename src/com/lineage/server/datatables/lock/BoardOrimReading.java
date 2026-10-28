package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1Rank;
import java.util.List;
import com.lineage.server.datatables.sql.BoardOrimTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.BoardOrimStorage;
import java.util.concurrent.locks.Lock;

public class BoardOrimReading
{
    private final Lock _lock;
    private final BoardOrimStorage _storage;
    private static BoardOrimReading _instance;
    
    private BoardOrimReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new BoardOrimTable();
    }
    
    public static BoardOrimReading get() {
        if (BoardOrimReading._instance == null) {
            BoardOrimReading._instance = new BoardOrimReading();
        }
        return BoardOrimReading._instance;
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
    
    public List<L1Rank> getTotalList() {
        this._lock.lock();
        List<L1Rank> tmp;
        try {
            tmp = this._storage.getTotalList();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public int writeTopic(int score, String leader, List<String> partyMember) {
        this._lock.lock();
        int tmp = 0;
        try {
            tmp = this._storage.writeTopic(score, leader, partyMember);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void renewPcName(String oriName, String newName) {
        this._lock.lock();
        try {
            this._storage.renewPcName(oriName, newName);
        }
        finally {
            this._lock.unlock();
        }
    }
}
