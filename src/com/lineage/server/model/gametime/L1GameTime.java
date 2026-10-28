package com.lineage.server.model.gametime;

import java.text.SimpleDateFormat;
import com.lineage.server.utils.RangeInt;
import java.sql.Time;
import java.util.TimeZone;
import java.util.Calendar;

public class L1GameTime
{
    private static final long BASE_TIME_IN_MILLIS_REAL = 1547121600000L;
    private final int _time;
    private final Calendar _calendar;
    
    private Calendar makeCalendar(int time) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(0L);
        cal.add(13, time);
        return cal;
    }
    
    private L1GameTime(int time) {
        this._time = time;
        this._calendar = this.makeCalendar(time);
    }
    
    public static L1GameTime valueOf(long timeMillis) {
        long t1 = timeMillis - BASE_TIME_IN_MILLIS_REAL;
        if (t1 < 0L) {
            throw new IllegalArgumentException();
        }
        else {
            int t2 = (int)(t1 * 6L / 1000L);
            int t3 = t2 % 3;
            return new L1GameTime(t2 - t3);
        }
    }
    
    public static L1GameTime fromSystemCurrentTime() {
        return valueOf(System.currentTimeMillis());
    }
    
    public static L1GameTime valueOfGameTime(Time time) {
        long t = time.getTime() + (long)TimeZone.getDefault().getRawOffset();
        return new L1GameTime((int)(t / 1000L));
    }
    
    public Time toTime() {
        int t = this._time % 86400;
        return new Time((long)t * 1000L - (long)TimeZone.getDefault().getRawOffset());
    }
    
    public int get(int field) {
        return this._calendar.get(field);
    }
    
    public int getSeconds() {
        return this._time;
    }
    
    public Calendar getCalendar() {
        return (Calendar)this._calendar.clone();
    }
    
    public boolean isNight() {
        int hour = this._calendar.get(11);
        return !RangeInt.includes(hour, 6, 17);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        f.setTimeZone(this._calendar.getTimeZone());
        return f.format(this._calendar.getTime()) + "(" + this.getSeconds() + ")";
    }
}
