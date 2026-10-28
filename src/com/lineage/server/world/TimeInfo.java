package com.lineage.server.world;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import com.lineage.config.Config;
import java.util.Calendar;
import java.sql.Timestamp;

public class TimeInfo
{
    private static TimeInfo _instance;
    private long _begin;
    
    public TimeInfo() {
        this._begin = System.currentTimeMillis();
    }
    
    public static TimeInfo time() {
        if (TimeInfo._instance == null) {
            TimeInfo._instance = new TimeInfo();
        }
        return TimeInfo._instance;
    }
    
    public Calendar timestampToCalendar(Timestamp ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        return cal;
    }
    
    public Calendar getNowTime() {
        TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(tz);
        return cal;
    }
    
    public String getNow_YMDHMS() {
        String nowDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        return nowDate;
    }
    
    public String getNow_YMDHMS(int i) {
        Calendar c = Calendar.getInstance();
        String[] nowDate = new String[6];
        nowDate[0] = String.valueOf(c.get(1));
        nowDate[1] = String.valueOf(c.get(2) + 1);
        if (nowDate[1].length() == 1) {
            nowDate[1] = "0" + nowDate[1];
        }
        nowDate[2] = String.valueOf(c.get(5));
        if (nowDate[2].length() == 1) {
            nowDate[2] = "0" + nowDate[2];
        }
        nowDate[3] = String.valueOf(c.get(11));
        if (nowDate[3].length() == 1) {
            nowDate[3] = "0" + nowDate[3];
        }
        nowDate[4] = String.valueOf(c.get(12));
        if (nowDate[4].length() == 1) {
            nowDate[4] = "0" + nowDate[4];
        }
        nowDate[5] = String.valueOf(c.get(13));
        if (nowDate[5].length() == 1) {
            nowDate[5] = "0" + nowDate[5];
        }
        switch (i) {
            case 0: {
                return String.valueOf(nowDate[0]) + "-" + nowDate[1] + "-" + nowDate[2];
            }
            case 1: {
                return String.valueOf(nowDate[3]) + ":" + nowDate[4] + ":" + nowDate[5];
            }
            case 2: {
                return String.valueOf(nowDate[0]) + "-" + nowDate[1] + "-" + nowDate[2] + " " + nowDate[3] + ":" + nowDate[4] + ":" + nowDate[5];
            }
            case 3: {
                return nowDate[4];
            }
            case 4: {
                return nowDate[3];
            }
            case 5: {
                return nowDate[2];
            }
            default: {
                return null;
            }
        }
    }
    
    public void reset() {
        this._begin = System.currentTimeMillis();
    }
    
    public long get() {
        return System.currentTimeMillis() - this._begin;
    }
}
