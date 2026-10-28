package com.lineage.server.model;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.List;

public class L1MobGroupInfo
{
    private final List<L1NpcInstance> _membersList;
    private L1NpcInstance _leader;
    private L1Spawn _spawn;
    private boolean _isRemoveGroup;
    
    public L1MobGroupInfo() {
        this._membersList = new ArrayList();
    }
    
    public void setLeader(L1NpcInstance npc) {
        this._leader = npc;
    }
    
    public L1NpcInstance getLeader() {
        return this._leader;
    }
    
    public boolean isLeader(L1NpcInstance npc) {
        return npc.getId() == this._leader.getId();
    }
    
    public void setSpawn(L1Spawn spawn) {
        this._spawn = spawn;
    }
    
    public L1Spawn getSpawn() {
        return this._spawn;
    }
    
    public void addMember(L1NpcInstance npc) {
        if (npc == null) {
            throw new NullPointerException();
        }
        else {
            if (this._membersList.isEmpty()) {
                this.setLeader(npc);
                if (npc.isReSpawn()) {
                    this.setSpawn(npc.getSpawn());
                }
            }
            if (!this._membersList.contains(npc)) {
                this._membersList.add(npc);
            }
            npc.setMobGroupInfo(this);
            npc.setMobGroupId(this._leader.getId());
            return;
        }
    }
    
    public synchronized int removeMember(L1NpcInstance npc) {
        if (npc == null) {
            throw new NullPointerException();
        }
        else {
            if (this._membersList.contains(npc)) {
                this._membersList.remove(npc);
            }
            npc.setMobGroupInfo(null);
            if (this.isLeader(npc)) {
                if (this.isRemoveGroup() && this._membersList.size() != 0) {
                    for (L1NpcInstance minion : this._membersList) {
                        minion.setMobGroupInfo(null);
                        minion.setSpawn(null);
                        minion.setreSpawn(false);
                    }
                    return 0;
                }
                else if (this._membersList.size() != 0) {
                    this.setLeader(this._membersList.get(0));
                }
            }
            return this._membersList.size();
        }
    }
    
    public int getNumOfMembers() {
        return this._membersList.size();
    }
    
    public boolean isRemoveGroup() {
        return this._isRemoveGroup;
    }
    
    public void setRemoveGroup(boolean flag) {
        this._isRemoveGroup = flag;
    }
    
    public List<L1NpcInstance> getList() {
        return this._membersList;
    }
}
