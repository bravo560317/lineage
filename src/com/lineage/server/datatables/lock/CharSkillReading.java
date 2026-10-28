package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1UserSkillTmp;
import java.util.ArrayList;
import com.lineage.server.datatables.sql.CharSkillTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.CharSkillStorage;
import java.util.concurrent.locks.Lock;

public class CharSkillReading
{
    private final Lock _lock;
    private final CharSkillStorage _storage;
    private static CharSkillReading _instance;
    
    private CharSkillReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new CharSkillTable();
    }
    
    public static CharSkillReading get() {
        if (CharSkillReading._instance == null) {
            CharSkillReading._instance = new CharSkillReading();
        }
        return CharSkillReading._instance;
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
    
    public ArrayList<L1UserSkillTmp> skills(int playerobjid) {
        this._lock.lock();
        ArrayList<L1UserSkillTmp> tmp;
        try {
            tmp = this._storage.skills(playerobjid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void spellMastery(int playerobjid, int skillid, String skillname, int active, int time) {
        this._lock.lock();
        try {
            if (skillname.equals("none")) {
                return;
            }
            else {
                this._storage.spellMastery(playerobjid, skillid, skillname, active, time);
            }
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void spellLost(int playerobjid, int skillid) {
        this._lock.lock();
        try {
            this._storage.spellLost(playerobjid, skillid);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public boolean spellCheck(int playerobjid, int skillid) {
        this._lock.lock();
        boolean tmp;
        try {
            tmp = this._storage.spellCheck(playerobjid, skillid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void setAuto(int mode, int objid, int skillid) {
        this._lock.lock();
        try {
            this._storage.setAuto(mode, objid, skillid);
        }
        finally {
            this._lock.unlock();
        }
    }
}
