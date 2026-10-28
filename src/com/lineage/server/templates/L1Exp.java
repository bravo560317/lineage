package com.lineage.server.templates;

public class L1Exp
{
    private int _level;
    private long _exp;
    private double _expPenalty;
    private double _dexp;
    private double _dexph;
    
    public int get_level() {
        return this._level;
    }
    
    public void set_level(int level) {
        this._level = level;
    }
    
    public long get_exp() {
        return this._exp;
    }
    
    public void set_exp(long exp) {
        this._exp = exp;
    }
    
    public double get_expPenalty() {
        return this._expPenalty;
    }
    
    public void set_expPenalty(double penalty) {
        this._expPenalty = penalty;
    }
    
    public double getdexp() {
        return this._dexp;
    }
    
    public void setdexp(double deathexp) {
        this._dexp = deathexp;
    }
    
    public double getdexph() {
        return this._dexph;
    }
    
    public void setdexph(double deathexph) {
        this._dexph = deathexph;
    }
}
