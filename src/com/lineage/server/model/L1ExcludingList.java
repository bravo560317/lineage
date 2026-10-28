package com.lineage.server.model;

import java.util.Iterator;
import java.util.ArrayList;

public class L1ExcludingList
{
    private ArrayList<String> _nameList;
    
    public L1ExcludingList() {
        this._nameList = new ArrayList();
    }
    
    public void add(String name) {
        this._nameList.add(name);
    }
    
    public String remove(String name) {
        for (String each : this._nameList) {
            if (each.equalsIgnoreCase(name)) {
                this._nameList.remove(each);
                return each;
            }
        }
        return null;
    }
    
    public boolean contains(String name) {
        for (String each : this._nameList) {
            if (each.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isFull() {
        return this._nameList.size() >= 16;
    }
}
