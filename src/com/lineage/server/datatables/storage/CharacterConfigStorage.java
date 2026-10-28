package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1Config;

public interface CharacterConfigStorage
{
    public abstract void load();
    
    public abstract L1Config get(int p0);
    
    public abstract void storeCharacterConfig(int p0, int p1, byte[] p2);
    
    public abstract void updateCharacterConfig(int p0, int p1, byte[] p2);
}
