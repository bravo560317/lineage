package com.lineage.server.model;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Map;
import com.lineage.server.serverpackets.S_HPMeter;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_PacketBoxParty;
import com.lineage.config.ConfigOther;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class L1Party
{
    private static final Log _log;
    public static final int MAXPT;
    private final ConcurrentHashMap<Integer, L1PcInstance> _membersList;
    private L1PcInstance _leader;
    private boolean _isLeader;
    
    static {
        _log = LogFactory.getLog(L1Party.class);
        MAXPT = ConfigOther.partycount;
    }
    
    public L1Party() {
        this._membersList = new ConcurrentHashMap();
        this._leader = null;
        this._isLeader = false;
    }
    
    public void addMember(L1PcInstance pc) {
        try {
            int key = 1;
            if (pc == null) {
                throw new NullPointerException();
            }
            else if (this._membersList.size() == L1Party.MAXPT || this._membersList.contains(pc)) {
                return;
            }
            else {
                boolean hpUpdate = false;
                if (this._membersList.isEmpty()) {
                    this.setLeader(pc);
                }
                else {
                    hpUpdate = true;
                }
                while (this._membersList.get((Integer)key) != null) {
                    ++key;
                }
                this._membersList.put(key, pc);
                pc.setParty(this);
                for (L1PcInstance member : this._membersList.values()) {
                    if (!member.equals(this._leader)) {
                        member.sendPackets(new S_PacketBoxParty(member, this));
                    }
                    else if (this._isLeader) {
                        member.sendPackets(new S_PacketBoxParty(pc));
                    }
                    member.sendPackets(new S_PacketBoxParty(this, member));
                }
                if (!this._isLeader) {
                    this._isLeader = true;
                }
                if (hpUpdate) {
                    this.createMiniHp(pc);
                }
            }
        }
        catch (Exception e) {
            L1Party._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void removeMember(L1PcInstance pc) {
        try {
            int removeKey = -1;
            Iterator<Integer> iterator = this._membersList.keySet().iterator();
            while (iterator.hasNext()) {
                int key = (Integer)iterator.next();
                L1PcInstance tgpc = this._membersList.get((Integer)key);
                if (pc.equals(tgpc)) {
                    removeKey = key;
                }
            }
            if (removeKey != -1) {
                this._membersList.remove((Integer)removeKey);
                pc.setParty(null);
                if (!this._membersList.isEmpty()) {
                    this.deleteMiniHp(pc);
                }
            }
        }
        catch (Exception e) {
            L1Party._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int partyUserInMap(short mapid) {
        int i = 0;
        if (this._membersList.isEmpty()) {
            return 0;
        }
        else if (this._membersList.size() <= 0) {
            return 0;
        }
        else {
            for (L1PcInstance tgpc : this._membersList.values()) {
                short tgpcMapid = tgpc.getMapId();
                if (tgpcMapid == mapid) {
                    ++i;
                }
            }
            return i;
        }
    }
    
    public boolean isVacancy() {
        return this._membersList.size() < L1Party.MAXPT;
    }
    
    public int getVacancy() {
        return L1Party.MAXPT - this._membersList.size();
    }
    
    public boolean isMember(L1PcInstance pc) {
        for (L1PcInstance tgpc : this._membersList.values()) {
            if (pc.equals(tgpc)) {
                return true;
            }
        }
        return false;
    }
    
    public void setLeader(L1PcInstance pc) {
        this._leader = pc;
    }
    
    public L1PcInstance getLeader() {
        return this._leader;
    }
    
    public int getLeaderID() {
        return this._leader.getId();
    }
    
    public boolean isLeader(L1PcInstance pc) {
        return pc.getId() == this._leader.getId();
    }
    
    public String getMembersNameList() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this._membersList.isEmpty()) {
            return null;
        }
        else if (this._membersList.size() <= 0) {
            return null;
        }
        else {
            for (L1PcInstance pc : this._membersList.values()) {
                stringBuilder.append(pc.getName() + " ");
            }
            return stringBuilder.toString();
        }
    }
    
    private void createMiniHp(L1PcInstance pc) {
        if (this._membersList.isEmpty()) {
            return;
        }
        else if (this._membersList.size() <= 0) {
            return;
        }
        else {
            for (L1PcInstance member : this._membersList.values()) {
                member.sendPackets(new S_HPMeter(pc.getId(), 100 * pc.getCurrentHp() / pc.getMaxHp()));
                pc.sendPackets(new S_HPMeter(member.getId(), 100 * member.getCurrentHp() / member.getMaxHp()));
            }
            return;
        }
    }
    
    private void deleteMiniHp(L1PcInstance pc) {
        if (this._membersList.isEmpty()) {
            return;
        }
        else if (this._membersList.size() <= 0) {
            return;
        }
        else {
            for (Map.Entry<Integer, L1PcInstance> info : this._membersList.entrySet()) {
                L1PcInstance member = info.getValue();
                pc.sendPackets(new S_HPMeter(member.getId(), 255));
                member.sendPackets(new S_HPMeter(pc.getId(), 255));
            }
            return;
        }
    }
    
    public void updateMiniHP(L1PcInstance pc) {
        if (this._membersList.isEmpty()) {
            return;
        }
        else if (this._membersList.size() <= 0) {
            return;
        }
        else {
            for (L1PcInstance member : this._membersList.values()) {
                member.sendPackets(new S_HPMeter(pc.getId(), 100 * pc.getCurrentHp() / pc.getMaxHp()));
            }
            return;
        }
    }
    
    public void breakup() {
        if (this._membersList.isEmpty()) {
            return;
        }
        else if (this._membersList.size() <= 0) {
            return;
        }
        else {
            for (Map.Entry<Integer, L1PcInstance> info : this._membersList.entrySet()) {
                L1PcInstance member = info.getValue();
                this.removeMember(member);
                member.sendPackets(new S_ServerMessage(418));
            }
            return;
        }
    }
    
    public void leaveMember(L1PcInstance pc) {
        if (this.isLeader(pc)) {
            this.breakup();
        }
        else if (this.getNumOfMembers() == 2) {
            this.removeMember(pc);
            L1PcInstance leader = this.getLeader();
            this.removeMember(leader);
            this.sendLeftMessage(pc, pc);
            this.sendLeftMessage(leader, pc);
            leader.sendPackets(new S_ServerMessage(418));
            pc.sendPackets(new S_ServerMessage(418));
        }
        else {
            this.removeMember(pc);
            for (Map.Entry<Integer, L1PcInstance> info : this._membersList.entrySet()) {
                L1PcInstance member = info.getValue();
                this.sendLeftMessage(member, pc);
            }
            this.sendLeftMessage(pc, pc);
        }
    }
    
    public void kickMember(L1PcInstance pc) {
        if (this.getNumOfMembers() == 2) {
            this.removeMember(pc);
            L1PcInstance leader = this.getLeader();
            this.removeMember(leader);
            this.sendLeftMessage(pc, pc);
            this.sendLeftMessage(leader, pc);
            leader.sendPackets(new S_ServerMessage(418));
            pc.sendPackets(new S_ServerMessage(418));
        }
        else {
            this.removeMember(pc);
            for (L1PcInstance member : this._membersList.values()) {
                this.sendLeftMessage(member, pc);
            }
            this.sendLeftMessage(pc, pc);
        }
        pc.sendPackets(new S_ServerMessage(419));
    }
    
    public ConcurrentHashMap<Integer, L1PcInstance> partyUsers() {
        return this._membersList;
    }
    
    public int getNumOfMembers() {
        return this._membersList.size();
    }
    
    private void sendLeftMessage(L1PcInstance sendTo, L1PcInstance left) {
        try {
            sendTo.sendPackets(new S_ServerMessage(420, left.getName()));
        }
        catch (Exception e) {
            L1Party._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public final int checkMentor(L1Apprentice apprentice) {
        int checkType = 0;
        for (L1PcInstance member : this._membersList.values()) {
            if (apprentice.getMaster().getId() == member.getId()) {
                checkType += 4;
            }
            else if (apprentice.getTotalList().contains(member)) {
                ++checkType;
            }
            else {
                continue;
            }
        }
        return checkType;
    }
    
    public void msgToAll() {
        for (L1PcInstance member : this._membersList.values()) {
            if (!member.equals(this._leader)) {
                member.sendPackets(new S_PacketBoxParty(this._leader.getId(), this._leader.getName()));
            }
        }
    }
    
    public L1PcInstance partyUser() {
        L1PcInstance user = null;
        for (L1PcInstance pc : this._membersList.values()) {
            if (user == null && !this._leader.equals(pc)) {
                user = pc;
            }
        }
        return user;
    }
    
    public List<String> getPartyMembers() {
        List<String> partyMembers = new CopyOnWriteArrayList();
        for (L1PcInstance pc : this._membersList.values()) {
            if (!this._leader.equals(pc)) {
                partyMembers.add(pc.getName());
            }
        }
        return partyMembers;
    }
}
