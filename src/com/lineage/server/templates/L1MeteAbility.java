package com.lineage.server.templates;

public class L1MeteAbility
{
    private final String _title;
    private final int _ac;
    private final int _hp;
    private final int _mp;
    private final int _hpr;
    private final int _mpr;
    private final int _str;
    private final int _con;
    private final int _dex;
    private final int _wis;
    private final int _cha;
    private final int _int;
    private final int _sp;
    private final int _mr;
    private final int _hit_modifier;
    private final int _dmg_modifier;
    private final int _bow_hit_modifier;
    private final int _bow_dmg_modifier;
    private final int _magic_hit_modifier;
    private final int _magic_dmg_modifier;
    private final int _magic_dmg_reduction;
    private final int _reduction_dmg;
    private final int _defense_water;
    private final int _defense_wind;
    private final int _defense_fire;
    private final int _defense_earth;
    private final int _regist_stun;
    private final int _regist_stone;
    private final int _regist_sleep;
    private final int _regist_freeze;
    private final int _regist_sustain;
    private final int _regist_blind;
    
    public L1MeteAbility(String title, int ac, int hp, int mp, int hpr, int mpr, int str, int con, int dex, int wis, int cha, int intel, int sp, int mr, int hit_modifier, int dmg_modifier, int bow_hit_modifier, int bow_dmg_modifier, int magic_hit_modifier, int magic_dmg_modifier, int magic_dmg_reduction, int reduction_dmg, int defense_water, int defense_wind, int defense_fire, int defense_earth, int regist_stun, int regist_stone, int regist_sleep, int regist_freeze, int regist_sustain, int regist_blind) {
        this._title = title;
        this._ac = ac;
        this._hp = hp;
        this._mp = mp;
        this._hpr = hpr;
        this._mpr = mpr;
        this._str = str;
        this._con = con;
        this._dex = dex;
        this._wis = wis;
        this._cha = cha;
        this._int = intel;
        this._sp = sp;
        this._mr = mr;
        this._hit_modifier = hit_modifier;
        this._dmg_modifier = dmg_modifier;
        this._bow_hit_modifier = bow_hit_modifier;
        this._bow_dmg_modifier = bow_dmg_modifier;
        this._magic_dmg_modifier = magic_dmg_modifier;
        this._magic_dmg_reduction = magic_dmg_reduction;
        this._reduction_dmg = reduction_dmg;
        this._defense_water = defense_water;
        this._defense_wind = defense_wind;
        this._defense_fire = defense_fire;
        this._defense_earth = defense_earth;
        this._regist_stun = regist_stun;
        this._regist_stone = regist_stone;
        this._regist_sleep = regist_sleep;
        this._regist_freeze = regist_freeze;
        this._regist_sustain = regist_sustain;
        this._regist_blind = regist_blind;
        this._magic_hit_modifier = magic_hit_modifier;
    }
    
    public final String getTitle() {
        return this._title;
    }
    
    public final int getAc() {
        return this._ac;
    }
    
    public final int getHp() {
        return this._hp;
    }
    
    public final int getMp() {
        return this._mp;
    }
    
    public final int getHpr() {
        return this._hpr;
    }
    
    public final int getMpr() {
        return this._mpr;
    }
    
    public final int getStr() {
        return this._str;
    }
    
    public final int getCon() {
        return this._con;
    }
    
    public final int getDex() {
        return this._dex;
    }
    
    public final int getWis() {
        return this._wis;
    }
    
    public final int getCha() {
        return this._cha;
    }
    
    public final int getInt() {
        return this._int;
    }
    
    public final int getSp() {
        return this._sp;
    }
    
    public final int getMr() {
        return this._mr;
    }
    
    public final int getHitModifier() {
        return this._hit_modifier;
    }
    
    public final int getDmgModifier() {
        return this._dmg_modifier;
    }
    
    public final int getBowHitModifier() {
        return this._bow_hit_modifier;
    }
    
    public final int getBowDmgModifier() {
        return this._bow_dmg_modifier;
    }
    
    public final int getMagicHitModifier() {
        return this._magic_hit_modifier;
    }
    
    public final int getMagicDmgModifier() {
        return this._magic_dmg_modifier;
    }
    
    public final int getMagicDmgReduction() {
        return this._magic_dmg_reduction;
    }
    
    public final int getReductionDmg() {
        return this._reduction_dmg;
    }
    
    public final int getDefenseWater() {
        return this._defense_water;
    }
    
    public final int getDefenseWind() {
        return this._defense_wind;
    }
    
    public final int getDefenseFire() {
        return this._defense_fire;
    }
    
    public final int getDefenseEarth() {
        return this._defense_earth;
    }
    
    public final int getRegistStun() {
        return this._regist_stun;
    }
    
    public final int getRegistStone() {
        return this._regist_stone;
    }
    
    public final int getRegistSleep() {
        return this._regist_sleep;
    }
    
    public final int getRegistFreeze() {
        return this._regist_freeze;
    }
    
    public final int getRegistSustain() {
        return this._regist_sustain;
    }
    
    public final int getRegistBlind() {
        return this._regist_blind;
    }
}
