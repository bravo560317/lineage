package com.lineage.server.templates;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.sql.Timestamp;

public class L1ShopS
{
    private int _id;
    private int _item_obj_id;
    private int _user_obj_id;
    private int _adena;
    private Timestamp _overtime;
    private int _end;
    private String _none;
    private L1ItemInstance _item;
    
    public L1ShopS() {
        this._item = null;
    }
    
    public int get_id() {
        return this._id;
    }
    
    public void set_id(int id) {
        this._id = id;
    }
    
    public int get_item_obj_id() {
        return this._item_obj_id;
    }
    
    public void set_item_obj_id(int itemObjId) {
        this._item_obj_id = itemObjId;
    }
    
    public int get_user_obj_id() {
        return this._user_obj_id;
    }
    
    public void set_user_obj_id(int userObjId) {
        this._user_obj_id = userObjId;
    }
    
    public int get_adena() {
        return this._adena;
    }
    
    public void set_adena(int adena) {
        this._adena = adena;
    }
    
    public Timestamp get_overtime() {
        return this._overtime;
    }
    
    public void set_overtime(Timestamp overtime) {
        this._overtime = overtime;
    }
    
    public int get_end() {
        return this._end;
    }
    
    public void set_end(int end) {
        this._end = end;
    }
    
    public String get_none() {
        return this._none;
    }
    
    public void set_none(String none) {
        this._none = none;
    }
    
    public L1ItemInstance get_item() {
        return this._item;
    }
    
    public void set_item(L1ItemInstance item) {
        this._item = item;
    }
}
