package com.lineage.server.datatables.storage;

import com.lineage.server.templates.CharQuest;
import java.util.Map;

public interface CharacterQuestStorage
{
    public abstract void load();
    
    public abstract Map<Integer, CharQuest> get(int p0);
    
    public abstract void storeQuest(int p0, int p1, CharQuest p2);
    
    public abstract void storeQuest(int p0, int p1, CharQuest p2, int p3);
    
    public abstract void updateQuest(int p0, int p1, CharQuest p2);
    
    public abstract void delQuest(int p0, int p1);
    
    public abstract void storeQuest2(int p0, int p1, int p2);
    
    public abstract void delQuest2(int p0);
}
