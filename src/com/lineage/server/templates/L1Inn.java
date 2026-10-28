package com.lineage.server.templates;

import java.sql.Timestamp;

public class L1Inn
{
    private int _npcId;
    private int _roomNumber;
    private int _keyId;
    private int _lodgerId;
    private boolean _hall;
    private Timestamp _dueTime;
    
    public int getKeyId() {
        return this._keyId;
    }
    
    public void setKeyId(int i) {
        this._keyId = i;
    }
    
    public int getInnNpcId() {
        return this._npcId;
    }
    
    public void setInnNpcId(int i) {
        this._npcId = i;
    }
    
    public int getRoomNumber() {
        return this._roomNumber;
    }
    
    public void setRoomNumber(int i) {
        this._roomNumber = i;
    }
    
    public int getLodgerId() {
        return this._lodgerId;
    }
    
    public void setLodgerId(int i) {
        this._lodgerId = i;
    }
    
    public boolean isHall() {
        return this._hall;
    }
    
    public void setHall(boolean hall) {
        this._hall = hall;
    }
    
    public Timestamp getDueTime() {
        return this._dueTime;
    }
    
    public void setDueTime(Timestamp i) {
        this._dueTime = i;
    }
}
