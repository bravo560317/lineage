package com.lineage.server.templates;

public final class L1ItemTime
{
    private final int _remain_time;
    private final boolean _equip;
    
    public L1ItemTime(int remain_time, boolean equip) {
        this._remain_time = remain_time;
        this._equip = equip;
    }
    
    public int get_remain_time() {
        return this._remain_time;
    }
    
    public boolean is_equipped() {
        return this._equip;
    }
}
