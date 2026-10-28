package com.add;

import java.sql.Timestamp;

public final class L1ItemTimeadd
{
    private final Timestamp _remain_time;
    
    public L1ItemTimeadd(Timestamp next_reset_time) {
        this._remain_time = next_reset_time;
    }
    
    public Timestamp get_remain_time() {
        return this._remain_time;
    }
}
