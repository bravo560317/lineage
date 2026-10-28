package com.lineage.server.templates;

import java.sql.Timestamp;
import com.lineage.server.utils.TimePeriod;
import java.sql.Time;

public class L1SpawnTime
{
    private final int _spawnId;
    private final Time _timeStart;
    private final Time _timeEnd;
    private final TimePeriod _timePeriod;
    private final Timestamp _periodStart;
    private final Timestamp _periodEnd;
    private boolean _isDeleteAtEndTime;
    
    public boolean isDeleteAtEndTime() {
        return this._isDeleteAtEndTime;
    }
    
    private L1SpawnTime(L1SpawnTime.L1SpawnTimeBuilder builder) {
        this._spawnId = builder._spawnId;
        this._timeStart = builder._timeStart;
        this._timeEnd = builder._timeEnd;
        this._timePeriod = new TimePeriod(this._timeStart, this._timeEnd);
        this._periodStart = builder._periodStart;
        this._periodEnd = builder._periodEnd;
        this._isDeleteAtEndTime = builder._isDeleteAtEndTime;
    }
    
    public int getSpawnId() {
        return this._spawnId;
    }
    
    public Time getTimeStart() {
        return this._timeStart;
    }
    
    public Time getTimeEnd() {
        return this._timeEnd;
    }
    
    public Timestamp getPeriodStart() {
        return this._periodStart;
    }
    
    public Timestamp getPeriodEnd() {
        return this._periodEnd;
    }
    
    public TimePeriod getTimePeriod() {
        return this._timePeriod;
    }
    
    public static class L1SpawnTimeBuilder
    {
        private final int _spawnId;
        private Time _timeStart;
        private Time _timeEnd;
        private Timestamp _periodStart;
        private Timestamp _periodEnd;
        private boolean _isDeleteAtEndTime;
        
        public L1SpawnTimeBuilder(int spawnId) {
            this._spawnId = spawnId;
        }
        
        public L1SpawnTime build() {
            return new L1SpawnTime(this);
        }
        
        public void setTimeStart(Time timeStart) {
            this._timeStart = timeStart;
        }
        
        public void setTimeEnd(Time timeEnd) {
            this._timeEnd = timeEnd;
        }
        
        public void setPeriodStart(Timestamp periodStart) {
            this._periodStart = periodStart;
        }
        
        public void setPeriodEnd(Timestamp periodEnd) {
            this._periodEnd = periodEnd;
        }
        
        public void setDeleteAtEndTime(boolean f) {
            this._isDeleteAtEndTime = f;
        }
    }
}
