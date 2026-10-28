package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1Pet;
import com.lineage.server.model.Instance.L1NpcInstance;

public interface PetStorage
{
    public abstract void load();
    
    public abstract void storeNewPet(L1NpcInstance p0, int p1, int p2);
    
    public abstract void storePet(L1Pet p0);
    
    public abstract void deletePet(int p0);
    
    public abstract boolean isNameExists(String p0);
    
    public abstract L1Pet getTemplate(int p0);
    
    public abstract L1Pet getTemplateX(int p0);
    
    public abstract L1Pet[] getPetTableList();
    
    public abstract void buyNewPet(int p0, int p1, int p2, int p3, long p4);
}
