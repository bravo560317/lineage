package com.lineage.server.timecontroller.event;

import com.lineage.william.William_Online_Reward;
import com.lineage.william.GetNowTime;
import com.lineage.server.world.TimeInfo;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.Timer;
import java.util.TimerTask;

public class T_Special extends TimerTask
{
    private static T_Special _instance;
    private Timer _timeHandler;
    public boolean _William_Online_Reward;
    
    public static T_Special getStart() {
        if (T_Special._instance == null) {
            T_Special._instance = new T_Special();
        }
        return T_Special._instance;
    }
    
    private T_Special() {
        this._timeHandler = new Timer(true);
        this._William_Online_Reward = false;
        int timeMillis = 60000;
        this._timeHandler.schedule(this, 60000L, 60000L);
        GeneralThreadPool.get().execute(this);
    }
    
    @Override
    public void run() {
        String mTime = TimeInfo.time().getNow_YMDHMS(3);
        String hTime = TimeInfo.time().getNow_YMDHMS(4);
        int mm = Integer.parseInt(mTime);
        int hh = Integer.parseInt(hTime);
        int dw = GetNowTime.GetNowDayWeek();
        if (!this._William_Online_Reward) {
            if (William_Online_Reward.isTime(dw, hh, mm)) {
                this._William_Online_Reward = true;
            }
        }
        else if (!William_Online_Reward.isTime(dw, hh, mm)) {
            this._William_Online_Reward = false;
        }
    }
}
