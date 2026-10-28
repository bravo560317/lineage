package com.lineage.server.templates;

import java.util.Calendar;

public class L1House
{
    private int _houseId;
    private String _houseName;
    private int _houseArea;
    private String _location;
    private int _keeperId;
    private boolean _isOnSale;
    private boolean _isPurchaseBasement;
    private Calendar _taxDeadline;
    
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
    
    public String getLocation() {
        return this._location;
    }
    
    public void setLocation(String s) {
        this._location = s;
    }
    
    public int getKeeperId() {
        return this._keeperId;
    }
    
    public void setKeeperId(int i) {
        this._keeperId = i;
    }
    
    public boolean isOnSale() {
        return this._isOnSale;
    }
    
    public void setOnSale(boolean flag) {
        this._isOnSale = flag;
    }
    
    public boolean isPurchaseBasement() {
        return this._isPurchaseBasement;
    }
    
    public void setPurchaseBasement(boolean flag) {
        this._isPurchaseBasement = flag;
    }
    
    public Calendar getTaxDeadline() {
        return this._taxDeadline;
    }
    
    public void setTaxDeadline(Calendar cal) {
        this._taxDeadline = cal;
    }
}
