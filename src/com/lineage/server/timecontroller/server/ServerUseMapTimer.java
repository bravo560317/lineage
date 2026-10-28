package com.lineage.server.timecontroller.server;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.World;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_PacketBoxGame;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerUseMapTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    public static final Map<L1PcInstance, Integer> MAP;
    
    static {
        _log = LogFactory.getLog(ServerUseMapTimer.class);
        MAP = new ConcurrentHashMap();
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    public static void put(L1PcInstance pc, int time) {
        ServerUseMapTimer.MAP.put(pc, new Integer(time));
    }
    
    @Override
    public void run() {
        try {
            if (!ServerUseMapTimer.MAP.isEmpty()) {
                for (L1PcInstance key : ServerUseMapTimer.MAP.keySet()) {
                    Integer value = ServerUseMapTimer.MAP.get(key);
                    if (key.getMapId() != key.get_other().get_usemap()) {
                        teleport(key);
                        return;
                    }
                    else {
                        value = (Integer)(value - 1);
                        if (value <= 6) {
                            teleport(key);
                            Thread.sleep(1L);
                        }
                        else if (value <= 4) {
                            teleport(key);
                            Thread.sleep(1L);
                        }
                        else if (value <= 2) {
                            teleport(key);
                            Thread.sleep(1L);
                        }
                        else if (value == 0) {
                            teleport(key);
                            Thread.sleep(1L);
                        }
                        else if (value % 1 == 0) {
                            key.get_other().set_usemapTime(value);
                            put(key, value);
                            key.sendPackets(new S_PacketBoxGame(71, key.get_other().get_usemapTime()));
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            ServerUseMapTimer._log.error("短時間計時地圖時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerUseMapTimer useMapTimer = new ServerUseMapTimer();
            useMapTimer.start();
        }
    }
    
    public static void teleport(L1PcInstance tgpc) {
        ServerUseMapTimer.MAP.remove(tgpc);
        if (World.get().getPlayer(tgpc.getName()) == null) {
            return;
        }
        else {
            if (tgpc.getMapId() == tgpc.get_other().get_usemap()) {
                L1Teleport.teleport(tgpc, 33430, 32808, (short)4, 5, true);
            }
            tgpc.get_other().set_usemapTime(0);
            tgpc.get_other().set_usemap(-1);
            tgpc.sendPackets(new S_PacketBoxGame(72));
            return;
        }
    }
}
