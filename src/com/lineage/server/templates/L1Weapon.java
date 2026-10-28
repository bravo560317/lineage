package com.lineage.server.templates;

import java.util.Random;

public class L1Weapon extends L1Item
{
    private static final long serialVersionUID = 1L;
    private static Random _random;
    private int _add_dmg_min;
    private int _add_dmg_max;
    private int _range;
    private int _hitModifier;
    private int _dmgModifier;
    private int _doubleDmgChance;
    private int _magicDmgModifier;
    private int _canbedmg;
    
    static {
        L1Weapon._random = new Random();
    }
    
    public L1Weapon() {
        this._add_dmg_min = 0;
        this._add_dmg_max = 0;
        this._range = 0;
        this._hitModifier = 0;
        this._dmgModifier = 0;
        this._magicDmgModifier = 0;
        this._canbedmg = 0;
    }
    
    public void set_add_dmg(int add_dmg_min, int add_dmg_max) {
        this._add_dmg_min = add_dmg_min;
        this._add_dmg_max = add_dmg_max;
    }
    
    @Override
    public int get_add_dmg() {
        int add_dmg = 0;
        if (this._add_dmg_min != 0 && this._add_dmg_max != 0) {
            add_dmg = this._add_dmg_min + L1Weapon._random.nextInt(this._add_dmg_max - this._add_dmg_min);
        }
        return add_dmg;
    }
    
    @Override
    public int getRange() {
        return this._range;
    }
    
    public void setRange(int i) {
        this._range = i;
    }
    
    @Override
    public int getHitModifier() {
        return this._hitModifier;
    }
    
    public void setHitModifier(int i) {
        this._hitModifier = i;
    }
    
    @Override
    public int getDmgModifier() {
        return this._dmgModifier;
    }
    
    public void setDmgModifier(int i) {
        this._dmgModifier = i;
    }
    
    @Override
    public int getDoubleDmgChance() {
        return this._doubleDmgChance;
    }
    
    public void setDoubleDmgChance(int i) {
        this._doubleDmgChance = i;
    }
    
    @Override
    public int getMagicDmgModifier() {
        return this._magicDmgModifier;
    }
    
    public void setMagicDmgModifier(int i) {
        this._magicDmgModifier = i;
    }
    
    @Override
    public int get_canbedmg() {
        return this._canbedmg;
    }
    
    public void set_canbedmg(int i) {
        this._canbedmg = i;
    }
    
    @Override
    public boolean isTwohandedWeapon() {
        int weapon_type = this.getType();
        boolean bool = weapon_type == 3 || weapon_type == 4 || weapon_type == 5 || weapon_type == 11 || weapon_type == 12 || weapon_type == 15 || weapon_type == 16 || weapon_type == 18;
        return bool;
    }
}
