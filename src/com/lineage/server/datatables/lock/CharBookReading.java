package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1BookMark;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.sql.CharBookTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharBookStorage;
import java.util.concurrent.locks.Lock;

public class CharBookReading
{
    private final Lock _lock;
    private final CharBookStorage _storage;
    private static CharBookReading _instance;
    
    private CharBookReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharBookTable();
    }
    
    public static CharBookReading get() {
        if (CharBookReading._instance == null) {
            CharBookReading._instance = new CharBookReading();
        }
        return CharBookReading._instance;
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
    
    public ArrayList<L1BookMark> getBookMarks(L1PcInstance pc) {
        this._lock.lock();
        ArrayList<L1BookMark> tmp;
        try {
            tmp = this._storage.getBookMarks(pc);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1BookMark getBookMark(L1PcInstance pc, int i) {
        this._lock.lock();
        L1BookMark tmp;
        try {
            tmp = this._storage.getBookMark(pc, i);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void deleteBookmark(L1PcInstance pc, String s) {
        this._lock.lock();
        try {
            this._storage.deleteBookmark(pc, s);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void addBookmark(L1PcInstance pc, String s) {
        this._lock.lock();
        try {
            this._storage.addBookmark(pc, s);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void updateBookmarkName(L1BookMark bookmark) {
        this._lock.lock();
        try {
            this._storage.updateBookmarkName(bookmark);
        }
        finally {
            this._lock.unlock();
        }
    }
}
