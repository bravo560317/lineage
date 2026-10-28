package com.lineage.server.model;

import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Iterator;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import org.apache.commons.logging.Log;

public class L1ChatParty
{
    private static final Log _log;
    private final List<L1PcInstance> _membersList;
    private L1PcInstance _leader;
    
    static {
        _log = LogFactory.getLog(L1ChatParty.class);
    }
    
    public L1ChatParty() {
        this._membersList = new ArrayList();
        this._leader = null;
    }
    
    public void addMember(L1PcInstance pc) {
        try {
            if (pc == null) {
                throw new NullPointerException();
            }
            else if (this._membersList.contains(pc)) {
                return;
            }
            else {
                if (this._membersList.isEmpty()) {
                    this.setLeader(pc);
                }
                this._membersList.add(pc);
                pc.setChatParty(this);
            }
        }
        catch (Exception e) {
            L1ChatParty._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void removeMember(L1PcInstance pc) {
        try {
            if (!this._membersList.contains(pc)) {
                return;
            }
            else {
                this._membersList.remove(pc);
                pc.setChatParty(null);
            }
        }
        catch (Exception e) {
            L1ChatParty._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean isVacancy() {
        return this._membersList.size() < 8;
    }
    
    public int getVacancy() {
        return 8 - this._membersList.size();
    }
    
    public boolean isMember(L1PcInstance pc) {
        return this._membersList.contains(pc);
    }
    
    private void setLeader(L1PcInstance pc) {
        this._leader = pc;
    }
    
    public L1PcInstance getLeader() {
        return this._leader;
    }
    
    public boolean isLeader(L1PcInstance pc) {
        return pc.getId() == this._leader.getId();
    }
    
    public String getMembersNameList() {
        String _result = new String("");
        for (L1PcInstance pc : this._membersList) {
            _result = _result + pc.getName() + " ";
        }
        return _result;
    }
    
    private void breakup() {
        try {
            L1PcInstance[] members = this.getMembers();
            L1PcInstance[] array;
            for (int length = (array = members).length, i = 0; i < length; ++i) {
                L1PcInstance member = array[i];
                this.removeMember(member);
                member.sendPackets(new S_ServerMessage(418));
            }
        }
        catch (Exception e) {
            L1ChatParty._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void leaveMember(L1PcInstance pc) {
        try {
            L1PcInstance[] members = this.getMembers();
            if (this.isLeader(pc)) {
                this.breakup();
            }
            else if (this.getNumOfMembers() == 2) {
                this.removeMember(pc);
                L1PcInstance leader = this.getLeader();
                this.removeMember(leader);
                this.sendLeftMessage(pc, pc);
                this.sendLeftMessage(leader, pc);
            }
            else {
                this.removeMember(pc);
                L1PcInstance[] array;
                for (int length = (array = members).length, i = 0; i < length; ++i) {
                    L1PcInstance member = array[i];
                    this.sendLeftMessage(member, pc);
                }
                this.sendLeftMessage(pc, pc);
            }
        }
        catch (Exception e) {
            L1ChatParty._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void kickMember(L1PcInstance pc) {
        try {
            if (this.getNumOfMembers() == 2) {
                this.removeMember(pc);
                L1PcInstance leader = this.getLeader();
                this.removeMember(leader);
            }
            else {
                this.removeMember(pc);
            }
            pc.sendPackets(new S_ServerMessage(419));
        }
        catch (Exception e) {
            L1ChatParty._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public L1PcInstance[] getMembers() {
        return (L1PcInstance[])this._membersList.toArray(new L1PcInstance[this._membersList.size()]);
    }
    
    public int getNumOfMembers() {
        return this._membersList.size();
    }
    
    private void sendLeftMessage(L1PcInstance sendTo, L1PcInstance left) {
        try {
            sendTo.sendPackets(new S_ServerMessage(420, left.getName()));
        }
        catch (Exception e) {
            L1ChatParty._log.error(e.getLocalizedMessage(), e);
        }
    }
}
