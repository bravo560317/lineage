package com.lineage.server.templates;

import java.sql.Timestamp;

public class L1ItemSpecialAttributeChar
{
    private int _itemobjid;
    private String _item_name;
    private int _attr_id;
    private String _add_pc_name;
    private Timestamp _add_time;
    private String _add_mon_name;
    private String _mapname;
    private String _Acquisition_mode;
    
    public int get_itemobjid() {
        return this._itemobjid;
    }
    
    public void set_itemobjid(int itemobjid) {
        this._itemobjid = itemobjid;
    }
    
    public String get_item_name() {
        return this._item_name;
    }
    
    public void set_item_name(String item_name) {
        this._item_name = item_name;
    }
    
    public int get_attr_id() {
        return this._attr_id;
    }
    
    public void set_attr_id(int attr_id) {
        this._attr_id = attr_id;
    }
    
    public String get_add_pc_name() {
        return this._add_pc_name;
    }
    
    public void set_add_pc_name(String add_pc_name) {
        this._add_pc_name = add_pc_name;
    }
    
    public Timestamp get_add_time() {
        return this._add_time;
    }
    
    public void set_add_time(Timestamp add_time) {
        this._add_time = add_time;
    }
    
    public String get_add_mon_name() {
        return this._add_mon_name;
    }
    
    public void set_add_mon_name(String add_mon_name) {
        this._add_mon_name = add_mon_name;
    }
    
    public String get_mapname() {
        return this._mapname;
    }
    
    public void set_mapname(String mapname) {
        this._mapname = mapname;
    }
    
    public String get_Acquisition_mode() {
        return this._Acquisition_mode;
    }
    
    public void set_Acquisition_mode(String Acquisition_mode) {
        this._Acquisition_mode = Acquisition_mode;
    }
}
