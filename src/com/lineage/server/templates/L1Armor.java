package com.lineage.server.templates;

public class L1Armor extends L1Item
{
    private static final long serialVersionUID = 1L;
    private int _ac;
    private int _damageReduction;
    private int _weightReduction;
    private int _hitModifierByArmor;
    private int _dmgModifierByArmor;
    private int _bowHitModifierByArmor;
    private int _bowDmgModifierByArmor;
    private int _magicHitModifierByArmor;
    private int _defense_water;
    private int _defense_wind;
    private int _defense_fire;
    private int _defense_earth;
    private int _regist_stun;
    private int _regist_stone;
    private int _regist_sleep;
    private int _regist_freeze;
    private int _regist_sustain;
    private int _regist_blind;
    private int _greater;
    private int _up_hp_potion;
    private int _uhp_number;
    
    public L1Armor() {
        this._ac = 0;
        this._damageReduction = 0;
        this._weightReduction = 0;
        this._hitModifierByArmor = 0;
        this._dmgModifierByArmor = 0;
        this._bowHitModifierByArmor = 0;
        this._bowDmgModifierByArmor = 0;
        this._magicHitModifierByArmor = 0;
        this._defense_water = 0;
        this._defense_wind = 0;
        this._defense_fire = 0;
        this._defense_earth = 0;
        this._regist_stun = 0;
        this._regist_stone = 0;
        this._regist_sleep = 0;
        this._regist_freeze = 0;
        this._regist_sustain = 0;
        this._regist_blind = 0;
        this._greater = 3;
        this._up_hp_potion = 0;
        this._uhp_number = 0;
    }
    
    @Override
    public int get_ac() {
        return this._ac;
    }
    
    public void set_ac(int i) {
        this._ac = i;
    }
    
    @Override
    public int getDamageReduction() {
        return this._damageReduction;
    }
    
    public void setDamageReduction(int i) {
        this._damageReduction = i;
    }
    
    @Override
    public int getWeightReduction() {
        return this._weightReduction;
    }
    
    public void setWeightReduction(int i) {
        this._weightReduction = i;
    }
    
    @Override
    public int getHitModifierByArmor() {
        return this._hitModifierByArmor;
    }
    
    public void setHitModifierByArmor(int i) {
        this._hitModifierByArmor = i;
    }
    
    @Override
    public int getDmgModifierByArmor() {
        return this._dmgModifierByArmor;
    }
    
    public void setDmgModifierByArmor(int i) {
        this._dmgModifierByArmor = i;
    }
    
    @Override
    public int getBowHitModifierByArmor() {
        return this._bowHitModifierByArmor;
    }
    
    public void setBowHitModifierByArmor(int i) {
        this._bowHitModifierByArmor = i;
    }
    
    @Override
    public int getBowDmgModifierByArmor() {
        return this._bowDmgModifierByArmor;
    }
    
    public void setBowDmgModifierByArmor(int i) {
        this._bowDmgModifierByArmor = i;
    }
    
    public void set_defense_water(int i) {
        this._defense_water = i;
    }
    
    @Override
    public int get_defense_water() {
        return this._defense_water;
    }
    
    public void set_defense_wind(int i) {
        this._defense_wind = i;
    }
    
    @Override
    public int get_defense_wind() {
        return this._defense_wind;
    }
    
    public void set_defense_fire(int i) {
        this._defense_fire = i;
    }
    
    @Override
    public int get_defense_fire() {
        return this._defense_fire;
    }
    
    public void set_defense_earth(int i) {
        this._defense_earth = i;
    }
    
    @Override
    public int get_defense_earth() {
        return this._defense_earth;
    }
    
    public void set_regist_stun(int i) {
        this._regist_stun = i;
    }
    
    @Override
    public int get_regist_stun() {
        return this._regist_stun;
    }
    
    public void set_regist_stone(int i) {
        this._regist_stone = i;
    }
    
    @Override
    public int get_regist_stone() {
        return this._regist_stone;
    }
    
    public void set_regist_sleep(int i) {
        this._regist_sleep = i;
    }
    
    @Override
    public int get_regist_sleep() {
        return this._regist_sleep;
    }
    
    public void set_regist_freeze(int i) {
        this._regist_freeze = i;
    }
    
    @Override
    public int get_regist_freeze() {
        return this._regist_freeze;
    }
    
    public void set_regist_sustain(int i) {
        this._regist_sustain = i;
    }
    
    @Override
    public int get_regist_sustain() {
        return this._regist_sustain;
    }
    
    public void set_regist_blind(int i) {
        this._regist_blind = i;
    }
    
    @Override
    public int get_regist_blind() {
        return this._regist_blind;
    }
    
    public void set_greater(int greater) {
        this._greater = greater;
    }
    
    @Override
    public int get_greater() {
        return this._greater;
    }
    
    @Override
    public int getMagicHitModifierByArmor() {
        return this._magicHitModifierByArmor;
    }
    
    public void setMagicHitModifierByArmor(int i) {
        this._magicHitModifierByArmor = i;
    }
    
    @Override
    public int get_up_hp_potion() {
        return this._up_hp_potion;
    }
    
    public void set_up_hp_potion(int uhp) {
        this._up_hp_potion = uhp;
    }
    
    @Override
    public int get_uhp_number() {
        return this._uhp_number;
    }
    
    public void set_uhp_number(int uhp) {
        this._uhp_number = uhp;
    }
}
