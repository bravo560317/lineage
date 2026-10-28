package com.lineage.server.templates;

import java.util.Collections;
import com.lineage.server.utils.collections.Lists;
import java.util.List;

public class L1MobGroup
{
    private final int _id;
    private final int _leaderId;
    private final List<L1NpcCount> _minions;
    private final boolean _isRemoveGroupIfLeaderDie;
    
    public L1MobGroup(int id, int leaderId, List<L1NpcCount> minions, boolean isRemoveGroupIfLeaderDie) {
        this._minions = (List)Lists.newArrayList();
        this._id = id;
        this._leaderId = leaderId;
        this._minions.addAll(minions);
        this._isRemoveGroupIfLeaderDie = isRemoveGroupIfLeaderDie;
    }
    
    public int getId() {
        return this._id;
    }
    
    public int getLeaderId() {
        return this._leaderId;
    }
    
    public List<L1NpcCount> getMinions() {
        return (List)Collections.unmodifiableList(this._minions);
    }
    
    public boolean isRemoveGroupIfLeaderDie() {
        return this._isRemoveGroupIfLeaderDie;
    }
}
