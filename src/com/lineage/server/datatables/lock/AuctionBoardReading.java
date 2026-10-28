package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1AuctionBoardTmp;
import java.util.Map;
import com.lineage.server.datatables.sql.AuctionBoardTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.AuctionBoardStorage;
import java.util.concurrent.locks.Lock;

public class AuctionBoardReading
{
    private final Lock _lock;
    private final AuctionBoardStorage _storage;
    private static AuctionBoardReading _instance;
    
    private AuctionBoardReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new AuctionBoardTable();
    }
    
    public static AuctionBoardReading get() {
        if (AuctionBoardReading._instance == null) {
            AuctionBoardReading._instance = new AuctionBoardReading();
        }
        return AuctionBoardReading._instance;
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
    
    public Map<Integer, L1AuctionBoardTmp> getAuctionBoardTableList() {
        this._lock.lock();
        Map<Integer, L1AuctionBoardTmp> tmp;
        try {
            tmp = this._storage.getAuctionBoardTableList();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1AuctionBoardTmp getAuctionBoardTable(int houseId) {
        this._lock.lock();
        L1AuctionBoardTmp tmp;
        try {
            tmp = this._storage.getAuctionBoardTable(houseId);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void insertAuctionBoard(L1AuctionBoardTmp board) {
        this._lock.lock();
        try {
            this._storage.insertAuctionBoard(board);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateAuctionBoard(L1AuctionBoardTmp board) {
        this._lock.lock();
        try {
            this._storage.updateAuctionBoard(board);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deleteAuctionBoard(int houseId) {
        this._lock.lock();
        try {
            this._storage.deleteAuctionBoard(houseId);
        }
        finally {
            this._lock.unlock();
        }
    }
}
