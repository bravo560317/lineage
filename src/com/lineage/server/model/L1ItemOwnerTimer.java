package com.lineage.server.model;

import java.util.Timer;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.TimerTask;

public class L1ItemOwnerTimer extends TimerTask
{
    private final L1ItemInstance _item;
    private final int _timeMillis;
    
    public L1ItemOwnerTimer(L1ItemInstance item, int timeMillis) {
        this._item = item;
        this._timeMillis = timeMillis;
    }
    
    @Override
    public void run() {
        this._item.setItemOwnerId(0);
        this.cancel();
    }
    
    public void begin() {
        Timer timer = new Timer();
        timer.schedule(this, (long)this._timeMillis);
    }
}
