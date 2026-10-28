package com.lineage.server.storage;

import com.lineage.server.model.Instance.L1PcInstance;

public interface CharacterStorage
{
    public abstract void createCharacter(L1PcInstance p0) throws Exception;
    
    public abstract void deleteCharacter(String p0, String p1) throws Exception;
    
    public abstract void storeCharacter(L1PcInstance p0) throws Exception;
    
    public abstract L1PcInstance loadCharacter(String p0) throws Exception;
    
    public abstract void alldeleteCharacter(String p0, String p1) throws Exception;
}
