package com.lineage.server.templates;

public class L1ServerItemGive
{
    private boolean _mob;
    private boolean _pet;
    private int _max;
    
    public boolean ismob() {
        return this._mob;
    }
    
    public void set_mob(boolean mob) {
        this._mob = mob;
    }
    
    public boolean ispet() {
        return this._pet;
    }
    
    public void set_pet(boolean pet) {
        this._pet = pet;
    }
    
    public int get_max() {
        return this._max;
    }
    
    public void set_max(int max) {
        this._max = max;
    }
}
