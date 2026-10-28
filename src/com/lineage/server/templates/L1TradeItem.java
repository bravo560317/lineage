package com.lineage.server.templates;

import com.lineage.server.model.Instance.L1ItemInstance;

public class L1TradeItem
{
    private int _objid;
    private int _item_id;
    private L1ItemInstance _item;
    private long _count;
    
    public L1ItemInstance get_item() {
        return this._item;
    }
    
    public void set_item(L1ItemInstance _item) {
        this._item = _item;
    }
    
    public long get_count() {
        return this._count;
    }
    
    public void set_count(long _count) {
        this._count = _count;
    }
    
    public int get_objid() {
        return this._objid;
    }
    
    public void set_objid(int _objid) {
        this._objid = _objid;
    }
    
    public int get_item_id() {
        return this._item_id;
    }
    
    public void set_item_id(int _item_id) {
        this._item_id = _item_id;
    }
}
