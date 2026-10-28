package com.lineage.server.templates;

public class L1Furniture
{
    private int _npcid;
    private int _item_obj_id;
    private int _locx;
    private int _locy;
    private short _mapid;
    
    public int get_npcid() {
        return this._npcid;
    }
    
    public void set_npcid(int npcid) {
        this._npcid = npcid;
    }
    
    public int get_item_obj_id() {
        return this._item_obj_id;
    }
    
    public void set_item_obj_id(int itemObjId) {
        this._item_obj_id = itemObjId;
    }
    
    public int get_locx() {
        return this._locx;
    }
    
    public void set_locx(int locx) {
        this._locx = locx;
    }
    
    public int get_locy() {
        return this._locy;
    }
    
    public void set_locy(int locy) {
        this._locy = locy;
    }
    
    public short get_mapid() {
        return this._mapid;
    }
    
    public void set_mapid(short mapid) {
        this._mapid = mapid;
    }
}
