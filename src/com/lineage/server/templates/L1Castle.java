package com.lineage.server.templates;

import java.util.Calendar;

public class L1Castle
{
    private int _id;
    private String _name;
    private Calendar _warTime;
    private int _taxRate;
    private long _publicMoney;
    
    public L1Castle(int id, String name) {
        this._id = id;
        this._name = name;
    }
    
    public int getId() {
        return this._id;
    }
    
    public String getName() {
        return this._name;
    }
    
    public Calendar getWarTime() {
        return this._warTime;
    }
    
    public void setWarTime(Calendar i) {
        this._warTime = i;
    }
    
    public int getTaxRate() {
        return this._taxRate;
    }
    
    public void setTaxRate(int i) {
        this._taxRate = i;
    }
    
    public long getPublicMoney() {
        return this._publicMoney;
    }
    
    public void setPublicMoney(long i) {
        this._publicMoney = i;
    }
}
