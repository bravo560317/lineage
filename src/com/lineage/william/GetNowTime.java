package com.lineage.william;

import java.util.Calendar;

public class GetNowTime
{
    public static int GetNowDay() {
        Calendar rightNow = Calendar.getInstance();
        int nowDay = rightNow.get(5);
        return nowDay;
    }
    
    public static int GetNowHour() {
        Calendar rightNow = Calendar.getInstance();
        int nowHour = rightNow.get(11);
        return nowHour;
    }
    
    public static int GetNowMinute() {
        Calendar rightNow = Calendar.getInstance();
        int nowMinute = rightNow.get(12);
        return nowMinute;
    }
    
    public static int GetNowMonth() {
        Calendar rightNow = Calendar.getInstance();
        int nowMonth = rightNow.get(2);
        return nowMonth;
    }
    
    public static int GetNowSecond() {
        Calendar rightNow = Calendar.getInstance();
        int nowSecond = rightNow.get(13);
        return nowSecond;
    }
    
    public static int GetNowYear() {
        Calendar rightNow = Calendar.getInstance();
        int nowYear = rightNow.get(1);
        return nowYear;
    }
    
    public static int GetNowDayWeek() {
        Calendar rightNow = Calendar.getInstance();
        int nowDayWeek = rightNow.get(7);
        return nowDayWeek;
    }
}
