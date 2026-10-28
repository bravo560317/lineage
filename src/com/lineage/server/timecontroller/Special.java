package com.lineage.server.timecontroller;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import com.lineage.config.Config;
import java.util.Calendar;
import com.lineage.server.thread.PcOtherThreadPool;
import java.util.Timer;
import java.util.concurrent.ScheduledFuture;
import java.util.TimerTask;

public class Special extends TimerTask
{
    private ScheduledFuture<?> _timer;
    private Timer _timeHandler;
    private static Special _instance;
    private boolean _isTark1;
    private boolean _isToSweepStart;
    private boolean _isTark1end;
    private boolean _isToSweepend;
    
    public static Special getStart() {
        if (Special._instance == null) {
            Special._instance = new Special();
        }
        return Special._instance;
    }
    
    private Special() {
        this._timeHandler = new Timer(true);
        this._isTark1 = false;
        this._isToSweepStart = false;
        this._isTark1end = false;
        this._isToSweepend = false;
        int timeMillis = 1000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    private static Calendar timestampToCalendar() {
        TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(_tz);
        return cal;
    }
    
    @Override
    public void run() {
        Calendar cals = timestampToCalendar();
        String HH = new SimpleDateFormat("HH").format(cals.getTime());
        int HHi = Integer.parseInt(HH);
        String mm = new SimpleDateFormat("mm").format(cals.getTime());
        int mmi = Integer.parseInt(mm);
        if (HHi == 8 || HHi == 20) {
            if (!this._isToSweepStart) {
                this._isToSweepStart = true;
            }
            if (!this._isToSweepend) {
                this._isToSweepend = true;
            }
        }
        if (HHi == 8 || HHi == 20) {
            switch (mmi) {
                case 0: {
                    if (this._isToSweepStart && !this._isTark1) {
                        this._isToSweepStart = false;
                        this._isTark1 = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1: {
                    if (this._isTark1) {
                        this._isTark1 = false;
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
        }
    }
}
