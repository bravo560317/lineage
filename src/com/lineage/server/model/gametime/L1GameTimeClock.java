package com.lineage.server.model.gametime;

import com.lineage.server.thread.GeneralThreadPool;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import org.apache.commons.logging.Log;

public class L1GameTimeClock
{
    private static final Log _log;
    private static L1GameTimeClock _instance;
    private volatile L1GameTime _currentTime;
    private L1GameTime _previousTime;
    private List<L1GameTimeListener> _listeners;
    
    static {
        _log = LogFactory.getLog(L1GameTimeClock.class);
    }
    
    private boolean isFieldChanged(int field) {
        return this._previousTime.get(field) != this._currentTime.get(field);
    }
    
    private void notifyChanged() {
        if (this.isFieldChanged(2)) {
            for (L1GameTimeListener listener : this._listeners) {
                listener.onMonthChanged(this._currentTime);
            }
        }
        if (this.isFieldChanged(5)) {
            for (L1GameTimeListener listener : this._listeners) {
                listener.onDayChanged(this._currentTime);
            }
        }
        if (this.isFieldChanged(11)) {
            for (L1GameTimeListener listener : this._listeners) {
                listener.onHourChanged(this._currentTime);
            }
        }
        if (this.isFieldChanged(12)) {
            for (L1GameTimeListener listener : this._listeners) {
                listener.onMinuteChanged(this._currentTime);
            }
        }
    }
    
    private L1GameTimeClock() {
        this._currentTime = L1GameTime.fromSystemCurrentTime();
        this._previousTime = null;
        this._listeners = new CopyOnWriteArrayList();
        GeneralThreadPool.get().execute(new L1GameTimeClock.TimeUpdater());
    }
    
    public static void init() {
        L1GameTimeClock._instance = new L1GameTimeClock();
    }
    
    public static L1GameTimeClock getInstance() {
        return L1GameTimeClock._instance;
    }
    
    public L1GameTime currentTime() {
        return this._currentTime;
    }
    
    public void addListener(L1GameTimeListener listener) {
        this._listeners.add(listener);
    }
    
    public void removeListener(L1GameTimeListener listener) {
        this._listeners.remove(listener);
    }
    
    /*
    static void access$1(L1GameTimeClock l1GameTimeClock, L1GameTime previousTime) {
        l1GameTimeClock._previousTime = previousTime;
    }
    */
    
    /*
    static void access$2(L1GameTimeClock l1GameTimeClock, L1GameTime currentTime) {
        l1GameTimeClock._currentTime = currentTime;
    }
    */
    
    private class TimeUpdater implements Runnable
    {
        @Override
        public void run() {
            while (true) {
                try {
                    L1GameTimeClock.this._previousTime = L1GameTimeClock.this._currentTime;
                    L1GameTimeClock.this._currentTime = L1GameTime.fromSystemCurrentTime();
                    L1GameTimeClock.this.notifyChanged();
                    Thread.sleep(500L);
                    continue;
                }
                catch (Exception e) {
                    L1GameTimeClock._log.error(e.getLocalizedMessage(), e);
                    return;
                }
            }
        }
    }
}
