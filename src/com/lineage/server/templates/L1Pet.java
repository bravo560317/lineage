package com.lineage.server.templates;

public class L1Pet
{
    private int _itemobjid;
    private int _objid;
    private int _npcid;
    private String _name;
    private int _level;
    private int _hp;
    private int _mp;
    private int _exp;
    private int _lawful;
private int weapon_objid;
private int weapon_itemid;
private String weapon_name;

private int armor_objid;
private int armor_itemid;
private String armor_name;
    
    public int get_itemobjid() {
        return this._itemobjid;
    }
    
    public void set_itemobjid(int i) {
        this._itemobjid = i;
    }
    
    public int get_objid() {
        return this._objid;
    }
    
    public void set_objid(int i) {
        this._objid = i;
    }
    
    public int get_npcid() {
        return this._npcid;
    }
    
    public void set_npcid(int i) {
        this._npcid = i;
    }
    
    public String get_name() {
        return this._name;
    }
    
    public void set_name(String s) {
        this._name = s;
    }
    
    public int get_level() {
        return this._level;
    }
    
    public void set_level(int i) {
        this._level = i;
    }
    
    public int get_hp() {
        return this._hp;
    }
    
    public void set_hp(int i) {
        this._hp = i;
    }
    
    public int get_mp() {
        return this._mp;
    }
    
    public void set_mp(int i) {
        this._mp = i;
    }
    
    public int get_exp() {
        return this._exp;
    }
    
    public void set_exp(int i) {
        this._exp = i;
    }
    
    public int get_lawful() {
        return this._lawful;
    }
    
    public void set_lawful(int i) {
        this._lawful = i;
    }
public int getWeaponObjId() { return weapon_objid; }
public void setWeaponObjId(int i) { weapon_objid = i; }

public int getWeaponItemId() { return weapon_itemid; }
public void setWeaponItemId(int i) { weapon_itemid = i; }

public String getWeaponName() { return weapon_name; }
public void setWeaponName(String s) { weapon_name = s; }

public int getArmorObjId() { return armor_objid; }
public void setArmorObjId(int i) { armor_objid = i; }

public int getArmorItemId() { return armor_itemid; }
public void setArmorItemId(int i) { armor_itemid = i; }

public String getArmorName() { return armor_name; }
public void setArmorName(String s) { armor_name = s; }
}
