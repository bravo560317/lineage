package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1EmblemIcon;

public interface ClanEmblemStorage
{
    public abstract void load();
    
    public abstract L1EmblemIcon get(int p0);
    
    public abstract L1EmblemIcon getEmblem(int p0);
    
    public abstract void addDeclanEmblem(int p0, L1EmblemIcon p1);
    
    public abstract void deleteIcon(int p0);
    
    public abstract L1EmblemIcon storeClanIcon(int p0, byte[] p1, int p2);
    
    public abstract void updateClanIcon(L1EmblemIcon p0);
}
