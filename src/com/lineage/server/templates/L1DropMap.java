package com.lineage.server.templates;

public class L1DropMap
{
    int _mapid;
    int _itemId;
    int _min;
    int _max;
    int _chance;
    
    public L1DropMap(int mapid, int itemId, int min, int max, int chance) {
        this._mapid = mapid;
        this._itemId = itemId;
        this._min = min;
        this._max = max;
        this._chance = chance;
    }
    
    public int get_mapid() {
        return this._mapid;
    }
    
    public int getChance() {
        return this._chance;
    }
    
    public int getItemid() {
        return this._itemId;
    }
    
    public int getMax() {
        return this._max;
    }
    
    public int getMin() {
        return this._min;
    }
}
