package com.lineage.server.templates;

public final class L1Taketreasure
{
    private final int _item_id;
    private final int _level;
    private final int _steal_chance;
    private final int _min_steal_count;
    private final int _max_steal_count;
    private final int _drop_on_floor;
    private final int _itemid;
    private final int _itemcount;
    private final int _anti_steal_item_id;
    private String _dropmsg;
    private String _dropmsg1;
    private String _dropmsg2;
    private final int _deaditemid;
    private final int _deaditemcount;
    
    public L1Taketreasure(int item_id, int level, int steal_chance, int min_steal_count, int max_steal_count, int drop_on_floor, int anti_steal_item_id, String dropmsg, String dropmsg1, String dropmsg2, int itemid, int itemcount, int deaditemid, int deaditemcount) {
        this._item_id = item_id;
        this._level = level;
        this._steal_chance = steal_chance;
        this._min_steal_count = min_steal_count;
        this._max_steal_count = max_steal_count;
        this._drop_on_floor = drop_on_floor;
        this._anti_steal_item_id = anti_steal_item_id;
        this._dropmsg = dropmsg;
        this._dropmsg1 = dropmsg1;
        this._dropmsg2 = dropmsg2;
        this._itemid = itemid;
        this._itemcount = itemcount;
        this._deaditemid = deaditemid;
        this._deaditemcount = deaditemcount;
    }
    
    public String getdropmsg() {
        return this._dropmsg;
    }
    
    public void setdropmsg(String _dropmsg) {
        this._dropmsg = _dropmsg;
    }
    
    public String getdropmsg1() {
        return this._dropmsg1;
    }
    
    public void setdropmsg1(String _dropmsg1) {
        this._dropmsg1 = _dropmsg1;
    }
    
    public String getdropmsg2() {
        return this._dropmsg2;
    }
    
    public void setdropmsg2(String _dropmsg2) {
        this._dropmsg2 = _dropmsg2;
    }
    
    public final int getItemId() {
        return this._item_id;
    }
    
    public final int getLevel() {
        return this._level;
    }
    
    public final int getStealChance() {
        return this._steal_chance;
    }
    
    public final int getMinStealCount() {
        return this._min_steal_count;
    }
    
    public final int getMaxStealCount() {
        return this._max_steal_count;
    }
    
    public final int isDropOnFloor() {
        return this._drop_on_floor;
    }
    
    public final int getitemid() {
        return this._itemid;
    }
    
    public final int getitemcount() {
        return this._itemcount;
    }
    
    public final int getAntiStealItemId() {
        return this._anti_steal_item_id;
    }
    
    public final int getdeaditemid() {
        return this._deaditemid;
    }
    
    public final int getdeaditemcount() {
        return this._deaditemcount;
    }
}
