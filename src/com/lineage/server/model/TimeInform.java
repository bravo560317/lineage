package com.lineage.server.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import com.lineage.config.Config;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeInform
{
    static TimeZone timezone;
    static Calendar rightNow;
    private static TimeInform _instance;
    
    static {
        TimeInform.timezone = TimeZone.getTimeZone(Config.TIME_ZONE);
        TimeInform.rightNow = Calendar.getInstance(TimeInform.timezone);
    }
    
    public static TimeInform time() {
        if (TimeInform._instance == null) {
            TimeInform._instance = new TimeInform();
        }
        return TimeInform._instance;
    }
    
    public static String getYear(int type, int i) {
        String year = null;
        if (type == 0) {
            year = String.valueOf(TimeInform.rightNow.get(1) + i);
        }
        else if (type == 1) {
            year = "西元 " + String.valueOf(TimeInform.rightNow.get(1));
        }
        else if (type == 2) {
            year = "民國 " + String.valueOf(TimeInform.rightNow.get(1) - 1911);
        }
        else {
            year = null;
        }
        return year;
    }
    
    public static String getMonth() {
        return String.valueOf(TimeInform.rightNow.get(2) + 1);
    }
    
    public static String getDay() {
        return String.valueOf(TimeInform.rightNow.get(5));
    }
    
    public static String getHour() {
        return String.valueOf(TimeInform.rightNow.get(11));
    }
    
    public static String getMinute() {
        return String.valueOf(TimeInform.rightNow.get(12));
    }
    
    public static String getSecond() {
        return String.valueOf(TimeInform.rightNow.get(12));
    }
    
    public static String getNowTime(int type, int type_year) {
        String NowTime = null;
        switch (type) {
            case 1: {
                NowTime = getYear(type_year, 0) + "年 " + getMonth() + "月" + getDay() + "日 " + getDayOfWeek();
                break;
            }
            case 2: {
                NowTime = getHour() + "時" + getMinute() + "分" + getSecond() + "秒";
                break;
            }
            case 3: {
                NowTime = getYear(type_year, 0) + "年" + getMonth() + "月" + getDay() + "日" + getHour() + "時" + getMinute() + "分" + getSecond() + "秒";
                break;
            }
        }
        return NowTime;
    }
    
    public static String getDayOfWeek() {
        String DayOfWeek = null;
        switch (TimeInform.rightNow.get(7)) {
            case 1: {
                DayOfWeek = "星期日";
                break;
            }
            case 2: {
                DayOfWeek = "星期一";
                break;
            }
            case 3: {
                DayOfWeek = "星期二";
                break;
            }
            case 4: {
                DayOfWeek = "星期三";
                break;
            }
            case 5: {
                DayOfWeek = "星期四";
                break;
            }
            case 6: {
                DayOfWeek = "星期五";
                break;
            }
            case 7: {
                DayOfWeek = "星期六";
                break;
            }
        }
        return DayOfWeek;
    }
    
    public String getNowTime_Standard() {
        String NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return NowTime;
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
}
