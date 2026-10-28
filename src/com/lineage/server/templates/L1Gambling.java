package com.lineage.server.templates;

public class L1Gambling
{
    private int _id;
    private long _adena;
    private double _rate;
    private String _gamblingno;
    private int _outcount;
    
    public int get_id() {
        return this._id;
    }
    
    public void set_id(int id) {
        this._id = id;
    }
    
    public long get_adena() {
        return this._adena;
    }
    
    public void set_adena(long adena) {
        this._adena = adena;
    }
    
    public double get_rate() {
        return this._rate;
    }
    
    public void set_rate(double rate) {
        this._rate = rate;
    }
    
    public String get_gamblingno() {
        return this._gamblingno;
    }
    
    public void set_gamblingno(String gamblingno) {
        this._gamblingno = gamblingno;
    }
    
    public int get_outcount() {
        return this._outcount;
    }
    
    public void set_outcount(int outcount) {
        this._outcount = outcount;
    }
}
