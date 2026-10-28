package com.lineage.server.model.gametime;

public interface L1GameTimeListener
{
    public abstract void onMonthChanged(L1GameTime p0);
    
    public abstract void onDayChanged(L1GameTime p0);
    
    public abstract void onHourChanged(L1GameTime p0);
    
    public abstract void onMinuteChanged(L1GameTime p0);
}
