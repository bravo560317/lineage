package com.add;

import com.lineage.config.ConfigOther;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;

public class L1DayPresentTimer
{
    private static L1DayPresentTimer.MessageTimer _messageTimer;
    
    public static void initialize() {
        L1DayPresentTimer._messageTimer = new L1DayPresentTimer.MessageTimer();
        GeneralThreadPool.get().execute(L1DayPresentTimer._messageTimer);
    }
    
    private static void ShowMessageToAll() {
        for (L1PcInstance pc : World.get().getAllPlayers()) {
            L1DayPresent.forTimeMessage(pc);
        }
    }
    
    private static class MessageTimer implements Runnable
    {
        @Override
        public void run() {
            int time = ConfigOther.onlydaytime;
            while (true) {
                try {
                    Thread.sleep((long)time);
                }
                catch (Exception exception) {
                    return;
                }
                ShowMessageToAll();
            }
        }
    }
}
