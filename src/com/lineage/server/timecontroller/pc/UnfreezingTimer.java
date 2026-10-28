package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_Teleport;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.lock.UpdateLocReading;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import java.util.Random;
import java.util.TimerTask;

public class UnfreezingTimer extends TimerTask
{
    private static Random _random;
    
    static {
        UnfreezingTimer._random = new Random();
    }
    
    public void start() {
        int timeMillis = 1000;
        PcOtherThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        Collection<L1PcInstance> all = World.get().getAllPlayers();
        if (all.isEmpty()) {
            return;
        }
        else {
            for (L1PcInstance tgpc : all) {
                if (tgpc.get_unfreezingTime() != 0) {
                    int time = tgpc.get_unfreezingTime() - 1;
                    tgpc.set_unfreezingTime(time);
                    if (time <= 0) {
                        UpdateLocReading.get().setPcLoc(tgpc.getAccountName());
                        tgpc.sendPackets(new S_ServerMessage("\\fR帳號內所有人物傳送回指定位置！"));
                        L1Teleport.teleport(tgpc, 33443, 32797, (short)4, 5, true);
                    }
                    else {
                        tgpc.sendPackets(new S_ServerMessage("\\fR" + time + "秒後傳送回指定位置！"));
                    }
                }
                if (tgpc.get_misslocTime() != 0) {
                    int time = tgpc.get_misslocTime() - 1;
                    tgpc.set_misslocTime(time);
                    if (time <= 0) {
                        tgpc.setTeleportX(tgpc.getX() + UnfreezingTimer._random.nextInt(2));
                        tgpc.setTeleportY(tgpc.getY() + UnfreezingTimer._random.nextInt(2));
                        tgpc.setTeleportMapId(tgpc.getMapId());
                        tgpc.setTeleportHeading(5);
                        tgpc.sendPackets(new S_Teleport(tgpc));
                        tgpc.sendPackets(new S_ServerMessage("\\fR解除人物錯位！"));
                    }
                    else {
                        tgpc.sendPackets(new S_ServerMessage("\\fR" + time + "秒後解除人物錯位！"));
                    }
                }
            }
            return;
        }
    }
}
