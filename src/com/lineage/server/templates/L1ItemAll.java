package com.lineage.server.templates;

public class L1ItemAll
{
    private String _item_name;
    private String _Message;
    private String _not_materials_msg;
    private int _has_time;
    private int _type;
    private boolean _buff_save;
    private int _add_str;
    private int _add_dex;
    private int _add_con;
    private int _add_int;
    private int _add_wis;
    private int _add_cha;
    private int _add_ac;
    private int _add_hp;
    private int _add_mp;
    private int _add_hpr;
    private int _add_mpr;
    private int _add_dmg;
    private int _add_hit;
    private int _add_bow_dmg;
    private int _add_bow_hit;
    private int _add_dmg_r;
    private int _add_magic_r;
    private int _add_mr;
    private int _add_sp;
    private int _add_fire;
    private int _add_wind;
    private int _add_earth;
    private int _add_water;
    private int _add_stun;
    private int _add_stone;
    private int _add_sleep;
    private int _add_freeze;
    private int _add_sustain;
    private int _add_blind;
    private int _add_exp;
    private boolean _death_exp;
    private boolean _death_item;
    private boolean _death_skill;
    private boolean _death_score;
    private int _add_adena;
    private int _add_mf;
    private int _skin_id;
    private int _effect;
    private int _materials;
    private int _count;
    private String _type_msg;
    
    public L1ItemAll() {
        this._buff_save = false;
        this._type = 0;
        this._buff_save = false;
        this._has_time = 0;
        this._add_str = 0;
        this._add_dex = 0;
        this._add_con = 0;
        this._add_int = 0;
        this._add_wis = 0;
        this._add_cha = 0;
        this._add_ac = 0;
        this._add_hp = 0;
        this._add_mp = 0;
        this._add_hpr = 0;
        this._add_mpr = 0;
        this._add_dmg = 0;
        this._add_hit = 0;
        this._add_bow_dmg = 0;
        this._add_bow_hit = 0;
        this._add_dmg_r = 0;
        this._add_magic_r = 0;
        this._add_mr = 0;
        this._add_sp = 0;
        this._add_fire = 0;
        this._add_wind = 0;
        this._add_earth = 0;
        this._add_water = 0;
        this._add_stun = 0;
        this._add_stone = 0;
        this._add_sleep = 0;
        this._add_freeze = 0;
        this._add_sustain = 0;
        this._add_blind = 0;
        this._add_exp = 0;
        this._death_exp = false;
        this._death_item = false;
        this._death_skill = false;
        this._death_score = false;
        this._add_adena = 0;
        this._add_mf = 0;
        this._skin_id = 0;
        this._effect = 0;
        this._materials = 0;
        this._count = 0;
    }
    
    public String getItem_name() {
        return this._item_name;
    }
    
    public void setItem_name(String i) {
        this._item_name = i;
    }
    
    public String getMessage() {
        return this._Message;
    }
    
    public void setMessage(String i) {
        this._Message = i;
    }
    
    public String getnot_materials_msg() {
        return this._not_materials_msg;
    }
    
    public void setnot_materials_msg(String i) {
        this._not_materials_msg = i;
    }
    
    public String gettype_msg() {
        return this._type_msg;
    }
    
    public void settype_msg(String i) {
        this._type_msg = i;
    }
    
    public int get_has_time() {
        return this._has_time;
    }
    
    public void set_has_time(int has_time) {
        this._has_time = has_time;
    }
    
    public boolean is_buff_save() {
        return this._buff_save;
    }
    
    public void set_buff_save(boolean i) {
        this._buff_save = i;
    }
    
    public int get_type() {
        return this._type;
    }
    
    public void set_type(int i) {
        this._type = i;
    }
    
    public int getmaterials() {
        return this._materials;
    }
    
    public void setmaterials(int i) {
        this._materials = i;
    }
    
    public int getcount() {
        return this._count;
    }
    
    public void setcount(int i) {
        this._count = i;
    }
    
    public int get_add_str() {
        return this._add_str;
    }
    
    public void set_add_str(int add_str) {
        this._add_str = add_str;
    }
    
    public int get_add_dex() {
        return this._add_dex;
    }
    
    public void set_add_dex(int add_dex) {
        this._add_dex = add_dex;
    }
    
    public int get_add_con() {
        return this._add_con;
    }
    
    public void set_add_con(int add_con) {
        this._add_con = add_con;
    }
    
    public int get_add_int() {
        return this._add_int;
    }
    
    public void set_add_int(int add_int) {
        this._add_int = add_int;
    }
    
    public int get_add_wis() {
        return this._add_wis;
    }
    
    public void set_add_wis(int add_wis) {
        this._add_wis = add_wis;
    }
    
    public int get_add_cha() {
        return this._add_cha;
    }
    
    public void set_add_cha(int add_cha) {
        this._add_cha = add_cha;
    }
    
    public int get_add_ac() {
        return this._add_ac;
    }
    
    public void set_add_ac(int add_ac) {
        this._add_ac = add_ac;
    }
    
    public int get_add_hp() {
        return this._add_hp;
    }
    
    public void set_add_hp(int add_hp) {
        this._add_hp = add_hp;
    }
    
    public int get_add_mp() {
        return this._add_mp;
    }
    
    public void set_add_mp(int add_mp) {
        this._add_mp = add_mp;
    }
    
    public int get_add_hpr() {
        return this._add_hpr;
    }
    
    public void set_add_hpr(int add_hpr) {
        this._add_hpr = add_hpr;
    }
    
    public int get_add_mpr() {
        return this._add_mpr;
    }
    
    public void set_add_mpr(int add_mpr) {
        this._add_mpr = add_mpr;
    }
    
    public int get_add_dmg() {
        return this._add_dmg;
    }
    
    public void set_add_dmg(int add_dmg) {
        this._add_dmg = add_dmg;
    }
    
    public int get_add_hit() {
        return this._add_hit;
    }
    
    public void set_add_hit(int add_hit) {
        this._add_hit = add_hit;
    }
    
    public int get_add_bow_dmg() {
        return this._add_bow_dmg;
    }
    
    public void set_add_bow_dmg(int add_bow_dmg) {
        this._add_bow_dmg = add_bow_dmg;
    }
    
    public int get_add_bow_hit() {
        return this._add_bow_hit;
    }
    
    public void set_add_bow_hit(int add_bow_hit) {
        this._add_bow_hit = add_bow_hit;
    }
    
    public int get_add_dmg_r() {
        return this._add_dmg_r;
    }
    
    public void set_add_dmg_r(int add_dmg_r) {
        this._add_dmg_r = add_dmg_r;
    }
    
    public int get_add_magic_r() {
        return this._add_magic_r;
    }
    
    public void set_add_magic_r(int add_magic_r) {
        this._add_magic_r = add_magic_r;
    }
    
    public int get_add_mr() {
        return this._add_mr;
    }
    
    public void set_add_mr(int add_mr) {
        this._add_mr = add_mr;
    }
    
    public int get_add_sp() {
        return this._add_sp;
    }
    
    public void set_add_sp(int add_sp) {
        this._add_sp = add_sp;
    }
    
    public int get_add_fire() {
        return this._add_fire;
    }
    
    public void set_add_fire(int add_fire) {
        this._add_fire = add_fire;
    }
    
    public int get_add_wind() {
        return this._add_wind;
    }
    
    public void set_add_wind(int add_wind) {
        this._add_wind = add_wind;
    }
    
    public int get_add_earth() {
        return this._add_earth;
    }
    
    public void set_add_earth(int add_earth) {
        this._add_earth = add_earth;
    }
    
    public int get_add_water() {
        return this._add_water;
    }
    
    public void set_add_water(int add_water) {
        this._add_water = add_water;
    }
    
    public int get_add_stun() {
        return this._add_stun;
    }
    
    public void set_add_stun(int add_stun) {
        this._add_stun = add_stun;
    }
    
    public int get_add_stone() {
        return this._add_stone;
    }
    
    public void set_add_stone(int add_stone) {
        this._add_stone = add_stone;
    }
    
    public int get_add_sleep() {
        return this._add_sleep;
    }
    
    public void set_add_sleep(int add_sleep) {
        this._add_sleep = add_sleep;
    }
    
    public int get_add_freeze() {
        return this._add_freeze;
    }
    
    public void set_add_freeze(int add_freeze) {
        this._add_freeze = add_freeze;
    }
    
    public int get_add_sustain() {
        return this._add_sustain;
    }
    
    public void set_add_sustain(int add_sustain) {
        this._add_sustain = add_sustain;
    }
    
    public int get_add_blind() {
        return this._add_blind;
    }
    
    public void set_add_blind(int add_blind) {
        this._add_blind = add_blind;
    }
    
    public int get_add_exp() {
        return this._add_exp;
    }
    
    public void set_add_exp(int add_exp) {
        this._add_exp = add_exp;
    }
    
    public boolean get_death_exp() {
        return this._death_exp;
    }
    
    public void set_death_exp(boolean death_exp) {
        this._death_exp = death_exp;
    }
    
    public boolean get_death_item() {
        return this._death_item;
    }
    
    public void set_death_item(boolean death_item) {
        this._death_item = death_item;
    }
    
    public boolean get_death_skill() {
        return this._death_skill;
    }
    
    public void set_death_skill(boolean death_skill) {
        this._death_skill = death_skill;
    }
    
    public boolean get_death_score() {
        return this._death_score;
    }
    
    public void set_death_score(boolean death_score) {
        this._death_score = death_score;
    }
    
    public int get_add_adena() {
        return this._add_adena;
    }
    
    public void set_add_adena(int add_adena) {
        this._add_adena = add_adena;
    }
    
    public int get_add_mf() {
        return this._add_mf;
    }
    
    public void set_add_mf(int mf) {
        this._add_mf = mf;
    }
    
    public int get_skin_id() {
        return this._skin_id;
    }
    
    public void set_skin_id(int i) {
        this._skin_id = i;
    }
    
    public int geteffect() {
        return this._effect;
    }
    
    public void seteffect(int effect) {
        this._effect = effect;
    }
}
