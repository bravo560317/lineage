package com.lineage.server.datatables.storage;

import java.util.ArrayList;

public interface BadBuddyStorage
{
    public abstract void load();
    
    public abstract void addBuddy(int p0, int p1, String p2);
    
    public abstract void removeBuddy(int p0, String p1);
    
    public abstract ArrayList<String> getBuddyBadList(int p0);
    
    public abstract boolean check(int p0, String p1);
}
