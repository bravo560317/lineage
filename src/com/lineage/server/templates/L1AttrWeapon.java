package com.lineage.server.templates;

public class L1AttrWeapon
{
    private final String _name;
    private final int _stage;
    private final int _chance;
    private final int _probability;
    private final int _type;
    private final int _fix_damage;
    private final int _random_damage;
    private final int _c_hp;
    private final int _c_mp;
    private final double _type_bind;
    private final double _type_drain_hp;
    private final int _type_drain_mp;
    private final double _type_dmgup;
    private final int _type_range;
    private final int _type_range_dmg;
    private final int _type_light_dmg;
    private final boolean _type_skill_1;
    private final boolean _type_skill_2;
    private final boolean _type_skill_3;
    private final boolean _type_skill_4;
    private final double _type_skill_time;
    private final String[] _type_poly_list;
    private final boolean _type_remove_weapon;
    private final boolean _type_remove_doll;
    private final int _type_remove_armor;
    private final int _gfixd;
    private String _Message;
    
    public L1AttrWeapon(String name, int stage, int chance, int probability, int type, int fix_damage, int random_damage, int c_hp, int c_mp, double type_bind, double type_drain_hp, int type_drain_mp, double type_dmgup, int type_range, int type_range_dmg, int type_light_dmg, boolean type_skill_1, boolean type_skill_2, boolean type_skill_3, boolean type_skill_4, double type_skill_time, String[] type_poly_list, boolean type_remove_weapon, boolean type_remove_doll, int type_remove_armor, int gfixd, String Message) {
        this._name = name;
        this._stage = stage;
        this._chance = chance;
        this._probability = probability;
        this._type = type;
        this._fix_damage = fix_damage;
        this._random_damage = random_damage;
        this._c_hp = c_hp;
        this._c_mp = c_mp;
        this._type_bind = type_bind;
        this._type_drain_hp = type_drain_hp;
        this._type_drain_mp = type_drain_mp;
        this._type_dmgup = type_dmgup;
        this._type_range = type_range;
        this._type_range_dmg = type_range_dmg;
        this._type_light_dmg = type_light_dmg;
        this._type_skill_1 = type_skill_1;
        this._type_skill_2 = type_skill_2;
        this._type_skill_3 = type_skill_3;
        this._type_skill_4 = type_skill_4;
        this._type_skill_time = type_skill_time;
        this._type_poly_list = type_poly_list;
        this._type_remove_weapon = type_remove_weapon;
        this._type_remove_doll = type_remove_doll;
        this._type_remove_armor = type_remove_armor;
        this._gfixd = gfixd;
        this._Message = Message;
    }
    
    public final String getName() {
        return this._name;
    }
    
    public final int getStage() {
        return this._stage;
    }
    
    public final int getChance() {
        return this._chance;
    }
    
    public final int getfix_damage() {
        return this._fix_damage;
    }
    
    public final int getrandom_damage() {
        return this._random_damage;
    }
    
    public final int getc_hp() {
        return this._c_hp;
    }
    
    public final int getc_mp() {
        return this._c_mp;
    }
    
    public final int getProbability() {
        return this._probability;
    }
    
    public final int gettype() {
        return this._type;
    }
    
    public final double getTypeBind() {
        return this._type_bind;
    }
    
    public final double getTypeDrainHp() {
        return this._type_drain_hp;
    }
    
    public final int getTypeDrainMp() {
        return this._type_drain_mp;
    }
    
    public final double getTypeDmgup() {
        return this._type_dmgup;
    }
    
    public final int getTypeRange() {
        return this._type_range;
    }
    
    public final int getTypeRangeDmg() {
        return this._type_range_dmg;
    }
    
    public final int getTypeLightDmg() {
        return this._type_light_dmg;
    }
    
    public final boolean getTypeSkill1() {
        return this._type_skill_1;
    }
    
    public final boolean getTypeSkill2() {
        return this._type_skill_2;
    }
    
    public final boolean getTypeSkill3() {
        return this._type_skill_3;
    }
    
    public final boolean getTypeSkill4() {
        return this._type_skill_4;
    }
    
    public final double getTypeSkillTime() {
        return this._type_skill_time;
    }
    
    public final String[] getTypePolyList() {
        return this._type_poly_list;
    }
    
    public final boolean getTypeRemoveWeapon() {
        return this._type_remove_weapon;
    }
    
    public final boolean getTypeRemoveDoll() {
        return this._type_remove_doll;
    }
    
    public final int getTypeRemoveArmor() {
        return this._type_remove_armor;
    }
    
    public final int getgfixd() {
        return this._gfixd;
    }
    
    public String getMessage() {
        return this._Message;
    }
    
    public void setMessage(String i) {
        this._Message = i;
    }
}
