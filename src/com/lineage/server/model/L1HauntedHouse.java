package com.lineage.server.model;

import java.util.Timer;
import java.util.TimerTask;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.utils.collections.Lists;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;

public class L1HauntedHouse
{
    public static final int STATUS_NONE = 0;
    public static final int STATUS_READY = 1;
    public static final int STATUS_PLAYING = 2;
    private final List<L1PcInstance> _members;
    private int _hauntedHouseStatus;
    private int _winnersCount;
    private int _goalCount;
    private static L1HauntedHouse _instance;
    
    public L1HauntedHouse() {
        this._members = (List)Lists.newArrayList();
        this._hauntedHouseStatus = 0;
        this._winnersCount = 0;
        this._goalCount = 0;
    }
    
    public static L1HauntedHouse getInstance() {
        if (L1HauntedHouse._instance == null) {
            L1HauntedHouse._instance = new L1HauntedHouse();
        }
        return L1HauntedHouse._instance;
    }
    
    private void readyHauntedHouse() {
        this.setHauntedHouseStatus(1);
        L1HauntedHouse.L1HauntedHouseReadyTimer hhrTimer = new L1HauntedHouse.L1HauntedHouseReadyTimer();
        hhrTimer.begin();
    }
    
    private void startHauntedHouse() {
        this.setHauntedHouseStatus(2);
        int membersCount = this.getMembersCount();
        if (membersCount <= 4) {
            this.setWinnersCount(1);
        }
        else if (5 >= membersCount && membersCount <= 7) {
            this.setWinnersCount(2);
        }
        else if (8 >= membersCount && membersCount <= 10) {
            this.setWinnersCount(3);
        }
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            L1SkillUse l1skilluse = new L1SkillUse();
            l1skilluse.handleCommands(pc, 44, pc.getId(), pc.getX(), pc.getY(), 0, 1);
            L1PolyMorph.doPoly(pc, 6284, 300, 4);
        }
        for (L1Object object : World.get().getObject()) {
            if (object instanceof L1DoorInstance) {
                L1DoorInstance door = (L1DoorInstance)object;
                if (door.getMapId() == 5140) {
                    door.open();
                }
                else {
                    continue;
                }
            }
        }
    }
    
    public void endHauntedHouse() {
        this.setHauntedHouseStatus(0);
        this.setWinnersCount(0);
        this.setGoalCount(0);
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            if (pc.getMapId() == 5140) {
                L1SkillUse l1skilluse = new L1SkillUse();
                l1skilluse.handleCommands(pc, 44, pc.getId(), pc.getX(), pc.getY(), 0, 1);
                L1Teleport.teleport(pc, 32624, 32813, (short)4, 5, true);
            }
        }
        this.clearMembers();
        for (L1Object object : World.get().getObject()) {
            if (object instanceof L1DoorInstance) {
                L1DoorInstance door = (L1DoorInstance)object;
                if (door.getMapId() == 5140) {
                    door.close();
                }
                else {
                    continue;
                }
            }
        }
    }
    
    public void removeRetiredMembers() {
        L1PcInstance[] temp = this.getMembersArray();
        L1PcInstance[] array;
        for (int length = (array = temp).length, i = 0; i < length; ++i) {
            L1PcInstance element = array[i];
            if (element.getMapId() != 5140) {
                this.removeMember(element);
            }
        }
    }
    
    public void sendMessage(int type, String msg) {
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            pc.sendPackets(new S_ServerMessage(type, msg));
        }
    }
    
    public void addMember(L1PcInstance pc) {
        if (!this._members.contains(pc)) {
            this._members.add(pc);
        }
        if (this.getMembersCount() == 1 && this.getHauntedHouseStatus() == 0) {
            this.readyHauntedHouse();
        }
    }
    
    public void removeMember(L1PcInstance pc) {
        this._members.remove(pc);
    }
    
    public void clearMembers() {
        this._members.clear();
    }
    
    public boolean isMember(L1PcInstance pc) {
        return this._members.contains(pc);
    }
    
    public L1PcInstance[] getMembersArray() {
        return (L1PcInstance[])this._members.toArray(new L1PcInstance[this._members.size()]);
    }
    
    public int getMembersCount() {
        return this._members.size();
    }
    
    private void setHauntedHouseStatus(int i) {
        this._hauntedHouseStatus = i;
    }
    
    public int getHauntedHouseStatus() {
        return this._hauntedHouseStatus;
    }
    
    private void setWinnersCount(int i) {
        this._winnersCount = i;
    }
    
    public int getWinnersCount() {
        return this._winnersCount;
    }
    
    public void setGoalCount(int i) {
        this._goalCount = i;
    }
    
    public int getGoalCount() {
        return this._goalCount;
    }
    
    public class L1HauntedHouseReadyTimer extends TimerTask
    {
        @Override
        public void run() {
            L1HauntedHouse.this.startHauntedHouse();
            L1HauntedHouse.L1HauntedHouseTimer hhTimer = new L1HauntedHouse.L1HauntedHouseTimer();
            hhTimer.begin();
        }
        
        public void begin() {
            Timer timer = new Timer();
            timer.schedule(this, 90000L);
        }
    }
    
    public class L1HauntedHouseTimer extends TimerTask
    {
        @Override
        public void run() {
            L1HauntedHouse.this.endHauntedHouse();
            this.cancel();
        }
        
        public void begin() {
            Timer timer = new Timer();
            timer.schedule(this, 300000L);
        }
    }
}
