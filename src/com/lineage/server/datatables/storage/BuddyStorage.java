package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1BuddyTmp;
import java.util.ArrayList;

public interface BuddyStorage
{
    public abstract void load();
    
    public abstract ArrayList<L1BuddyTmp> userBuddy(int p0);
    
    public abstract void addBuddy(int p0, int p1, String p2);
    
    public abstract void removeBuddy(int p0, String p1);
}
