package com.lineage.server.templates;

import java.util.Calendar;

public class L1AuctionBoardTmp
{
    private int _houseId;
    private String _houseName;
    private int _houseArea;
    private Calendar _deadline;
    private long _price;
    private String _location;
    private String _oldOwner;
    private int _oldOwnerId;
    private String _bidder;
    private int _bidderId;
    
    public int getHouseId() {
        return this._houseId;
    }
    
    public void setHouseId(int i) {
        this._houseId = i;
    }
    
    public String getHouseName() {
        return this._houseName;
    }
    
    public void setHouseName(String s) {
        this._houseName = s;
    }
    
    public int getHouseArea() {
        return this._houseArea;
    }
    
    public void setHouseArea(int i) {
        this._houseArea = i;
    }
    
    public Calendar getDeadline() {
        return this._deadline;
    }
    
    public void setDeadline(Calendar i) {
        this._deadline = i;
    }
    
    public long getPrice() {
        return this._price;
    }
    
    public void setPrice(long i) {
        this._price = i;
    }
    
    public String getLocation() {
        return this._location;
    }
    
    public void setLocation(String s) {
        this._location = s;
    }
    
    public String getOldOwner() {
        return this._oldOwner;
    }
    
    public void setOldOwner(String s) {
        this._oldOwner = s;
    }
    
    public int getOldOwnerId() {
        return this._oldOwnerId;
    }
    
    public void setOldOwnerId(int i) {
        this._oldOwnerId = i;
    }
    
    public String getBidder() {
        return this._bidder;
    }
    
    public void setBidder(String s) {
        this._bidder = s;
    }
    
    public int getBidderId() {
        return this._bidderId;
    }
    
    public void setBidderId(int i) {
        this._bidderId = i;
    }
}
