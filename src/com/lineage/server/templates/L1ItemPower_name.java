package com.lineage.server.templates;

import java.sql.Timestamp;

public class L1ItemPower_name
{
    private int _item_obj_id;
    private int _hole_count;
    private int _hole_1;
    private int _hole_2;
    private int _hole_3;
    private int _hole_4;
    private int _hole_5;
    private L1WeaponMagSkill _magic_weapon;
    private Timestamp _date_time;
    
    public int get_item_obj_id() {
        return this._item_obj_id;
    }
    
    public void set_item_obj_id(int item_obj_id) {
        this._item_obj_id = item_obj_id;
    }
    
    public int get_hole_count() {
        return this._hole_count;
    }
    
    public void set_hole_count(int _hole_count) {
        this._hole_count = _hole_count;
    }
    
    public int get_hole_1() {
        return this._hole_1;
    }
    
    public void set_hole_1(int _hole_1) {
        this._hole_1 = _hole_1;
    }
    
    public int get_hole_2() {
        return this._hole_2;
    }
    
    public void set_hole_2(int _hole_2) {
        this._hole_2 = _hole_2;
    }
    
    public int get_hole_3() {
        return this._hole_3;
    }
    
    public void set_hole_3(int _hole_3) {
        this._hole_3 = _hole_3;
    }
    
    public int get_hole_4() {
        return this._hole_4;
    }
    
    public void set_hole_4(int _hole_4) {
        this._hole_4 = _hole_4;
    }
    
    public int get_hole_5() {
        return this._hole_5;
    }
    
    public void set_hole_5(int _hole_5) {
        this._hole_5 = _hole_5;
    }
    
    public int get_hole(int i) {
        if (i == 1) {
            return this._hole_1;
        }
        else if (i == 2) {
            return this._hole_2;
        }
        else if (i == 3) {
            return this._hole_3;
        }
        else if (i == 4) {
            return this._hole_4;
        }
        else if (i == 5) {
            return this._hole_5;
        }
        else {
            return 0;
        }
    }
    
    public final L1WeaponMagSkill get_magic_weapon() {
        return this._magic_weapon;
    }
    
    public final void set_magic_weapon(L1WeaponMagSkill value) {
        this._magic_weapon = value;
    }
    
    public final Timestamp get_date_time() {
        return this._date_time;
    }
    
    public final void set_date_time(Timestamp value) {
        this._date_time = value;
    }
}
