package com.lineage.server.templates;

public class L1DropMob
{
    int _min;
    int _max;
    int _chance;
    private int _week;
    private int _starttime;
    private int _unitytime;
    
    public int getweek() {
        return this._week;
    }
    
    public void setweek(int i) {
        this._week = i;
    }
    
    public int getStarttime() {
        return this._starttime;
    }
    
    public void setStarttime(int i) {
        this._starttime = i;
    }
    
    public int getUnitytime() {
        return this._unitytime;
    }
    
    public void setunitytime(int i) {
        this._unitytime = i;
    }
    
    public int getMin() {
        return this._min;
    }
    
    public void setMin(int i) {
        this._min = i;
    }
    
    public int getMax() {
        return this._max;
    }
    
    public void setMax(int i) {
        this._max = i;
    }
    
    public int getChance() {
        return this._chance;
    }
    
    public void setChance(int i) {
        this._chance = i;
    }
}
