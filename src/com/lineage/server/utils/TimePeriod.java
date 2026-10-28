package com.lineage.server.utils;

import com.lineage.server.model.gametime.L1GameTime;
import java.sql.Time;

public class TimePeriod
{
    private final Time _timeStart;
    private final Time _timeEnd;
    
    public TimePeriod(Time timeStart, Time timeEnd) {
        if (timeStart.equals(timeEnd)) {
            throw new IllegalArgumentException("timeBegin must not equals timeEnd");
        }
        else {
            this._timeStart = timeStart;
            this._timeEnd = timeEnd;
            return;
        }
    }
    
    private boolean includes(L1GameTime time, Time timeStart, Time timeEnd) {
        Time when = time.toTime();
        return timeStart.compareTo(when) <= 0 && timeEnd.compareTo(when) > 0;
    }
    
    public boolean includes(L1GameTime time) {
        return this._timeStart.after(this._timeEnd) || (!this.includes(time, this._timeEnd, this._timeStart) && this.includes(time, this._timeStart, this._timeEnd));
    }
}
