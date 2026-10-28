package com.lineage.server.datatables.lock;

import com.lineage.server.templates.L1Pet;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.datatables.sql.PetTable;
import java.util.concurrent.locks.ReentrantLock;
import com.lineage.server.datatables.storage.PetStorage;
import java.util.concurrent.locks.Lock;

public class PetReading
{
    private final Lock _lock;
    private final PetStorage _storage;
    private static PetReading _instance;
    
    private PetReading() {
        this._lock = new ReentrantLock(true);
        this._storage = new PetTable();
    }
    
    public static PetReading get() {
        if (PetReading._instance == null) {
            PetReading._instance = new PetReading();
        }
        return PetReading._instance;
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
    
    public void storeNewPet(L1NpcInstance pet, int objid, int itemobjid) {
        this._lock.lock();
        try {
            this._storage.storeNewPet(pet, objid, itemobjid);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void storePet(L1Pet pet) {
        this._lock.lock();
        try {
            this._storage.storePet(pet);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public void deletePet(int itemobjid) {
        this._lock.lock();
        try {
            this._storage.deletePet(itemobjid);
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public boolean isNameExists(String nameCaseInsensitive) {
        this._lock.lock();
        boolean tmp;
        try {
            tmp = this._storage.isNameExists(nameCaseInsensitive);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Pet getTemplate(int itemobjid) {
        this._lock.lock();
        L1Pet tmp;
        try {
            tmp = this._storage.getTemplate(itemobjid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Pet getTemplateX(int npcobjid) {
        this._lock.lock();
        L1Pet tmp;
        try {
            tmp = this._storage.getTemplateX(npcobjid);
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public L1Pet[] getPetTableList() {
        this._lock.lock();
        L1Pet[] tmp;
        try {
            tmp = this._storage.getPetTableList();
        }
        finally {
            this._lock.unlock();
        }
        return tmp;
    }
    
    public void buyNewPet(int petNpcId, int i, int id, int upLv, long lvExp) {
        this._lock.lock();
        try {
            this._storage.buyNewPet(petNpcId, i, id, upLv, lvExp);
        }
        finally {
            this._lock.unlock();
        }
    }
}
