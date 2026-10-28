package com.lineage.server.templates;

public class L1ItemUpdate
{
    public static final String _html_01 = "y_up_i0";
    public static final String _html_02 = "y_up_i1";
    public static final String _html_03 = "y_up_i2";
    private int _item_id;
    private int _toid;
    private int[] _needids;
    private int[] _needcounts;
    
    public L1ItemUpdate() {
        this._needids = null;
        this._needcounts = null;
    }
    
    public int get_item_id() {
        return this._item_id;
    }
    
    public void set_item_id(int _item_id) {
        this._item_id = _item_id;
    }
    
    public int get_toid() {
        return this._toid;
    }
    
    public void set_toid(int _toid) {
        this._toid = _toid;
    }
    
    public int[] get_needids() {
        return this._needids;
    }
    
    public void set_needids(int[] _needids) {
        this._needids = _needids;
    }
    
    public int[] get_needcounts() {
        return this._needcounts;
    }
    
    public void set_needcounts(int[] _needcounts) {
        this._needcounts = _needcounts;
    }
}
