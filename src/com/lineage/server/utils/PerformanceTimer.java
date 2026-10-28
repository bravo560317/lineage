package com.lineage.server.utils;

import java.util.TimeZone;
import com.lineage.config.Config;
import java.util.Calendar;

public class PerformanceTimer
{
    private long _begin;
    
    public PerformanceTimer() {
        this._begin = System.currentTimeMillis();
    }
    
    public void reset() {
        this._begin = System.currentTimeMillis();
    }
    
    public long get() {
        return System.currentTimeMillis() - this._begin;
    }
    
    public static Calendar getRealTime() {
        TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(_tz);
        return cal;
    }
}
