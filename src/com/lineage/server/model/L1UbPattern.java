package com.lineage.server.model;

import java.util.Iterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class L1UbPattern
{
    private boolean _isFrozen;
    private Map<Integer, ArrayList<L1UbSpawn>> _groups;
    
    public L1UbPattern() {
        this._isFrozen = false;
        this._groups = new HashMap();
    }
    
    public void addSpawn(int groupNumber, L1UbSpawn spawn) {
        if (this._isFrozen) {
            return;
        }
        else {
            ArrayList spawnList = this._groups.get((Integer)groupNumber);
            if (spawnList == null) {
                spawnList = new ArrayList();
                this._groups.put(groupNumber, spawnList);
            }
            spawnList.add(spawn);
            return;
        }
    }
    
    public void freeze() {
        if (this._isFrozen) {
            return;
        }
        else {
            for (ArrayList object45 : this._groups.values()) {
                ArrayList spawnList = object45;
                Collections.sort(spawnList);
            }
            this._isFrozen = true;
            return;
        }
    }
    
    public boolean isFrozen() {
        return this._isFrozen;
    }
    
    public ArrayList<L1UbSpawn> getSpawnList(int groupNumber) {
        if (!this._isFrozen) {
            return null;
        }
        else {
            return this._groups.get((Integer)groupNumber);
        }
    }
}
