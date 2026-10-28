package com.lineage.server.timecontroller.pc;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.World;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.serverpackets.S_MapTimer;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Map;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public final class MapTimerThread extends TimerTask
{
    private static MapTimerThread _instance;
    private static final Log _log;
    public static final Map<L1PcInstance, Integer> TIMINGMAP;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(MapTimerThread.class);
        TIMINGMAP = new ConcurrentHashMap();
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    public static MapTimerThread getInstance() {
        if (MapTimerThread._instance == null) {
            (MapTimerThread._instance = new MapTimerThread()).start();
        }
        return MapTimerThread._instance;
    }
    
    public static void put(L1PcInstance pc, int time) {
        pc.sendPackets(new S_MapTimer(time));
        MapTimerThread.TIMINGMAP.put(pc, new Integer(time));
    }
    
    private void MapTimeCheck(L1PcInstance pc) {
        int mapid = pc.getMapId();
        int maxMapUsetime = MapsTable.get().getMapTime(mapid) * 60;
        int usedtime = pc.getMapUseTime(mapid);
        if (usedtime > maxMapUsetime) {
            this.teleport(pc);
        }
        else {
            ++usedtime;
            this.message(pc);
            pc.setMapUseTime(mapid, usedtime);
            int leftTime = maxMapUsetime - usedtime;
            if (leftTime % 60 == 0) {
                pc.sendPackets(new S_MapTimer(leftTime));
            }
        }
    }
    
    private void message(L1PcInstance pc) {
        int map = pc.getMapId();
        int maxMapUsetime = MapsTable.get().getMapTime(map) * 60;
        int usedtime = pc.getMapUseTime(map);
        int lefttime = maxMapUsetime - usedtime;
        S_ServerMessage msg = null;
        switch (lefttime) {
            case 1: {
                msg = new S_ServerMessage(1528, String.valueOf(1));
                break;
            }
            case 2: {
                msg = new S_ServerMessage(1528, String.valueOf(2));
                break;
            }
            case 3: {
                msg = new S_ServerMessage(1528, String.valueOf(3));
                break;
            }
            case 4: {
                msg = new S_ServerMessage(1528, String.valueOf(4));
                break;
            }
            case 5: {
                msg = new S_ServerMessage(1528, String.valueOf(5));
                break;
            }
            case 6: {
                msg = new S_ServerMessage(1528, String.valueOf(6));
                break;
            }
            case 7: {
                msg = new S_ServerMessage(1528, String.valueOf(7));
                break;
            }
            case 8: {
                msg = new S_ServerMessage(1528, String.valueOf(8));
                break;
            }
            case 9: {
                msg = new S_ServerMessage(1528, String.valueOf(9));
                break;
            }
            case 10: {
                msg = new S_ServerMessage(1528, String.valueOf(10));
                break;
            }
            case 60: {
                msg = new S_ServerMessage(1527, String.valueOf(1));
                break;
            }
            case 120: {
                msg = new S_ServerMessage(1527, String.valueOf(2));
                break;
            }
            case 300: {
                msg = new S_ServerMessage(1527, String.valueOf(5));
                break;
            }
            case 600: {
                msg = new S_ServerMessage(1527, String.valueOf(10));
                break;
            }
            case 900: {
                msg = new S_ServerMessage(1527, String.valueOf(15));
                break;
            }
            case 1800: {
                msg = new S_ServerMessage(1527, String.valueOf(30));
                break;
            }
            case 3600: {
                msg = new S_ServerMessage(1526, String.valueOf(1));
                break;
            }
            case 7200: {
                msg = new S_ServerMessage(1526, String.valueOf(2));
                break;
            }
            case 10800: {
                msg = new S_ServerMessage(1526, String.valueOf(3));
                break;
            }
        }
        pc.sendPackets(msg);
    }
    
    @Override
    public void run() {
        try {
            if (!MapTimerThread.TIMINGMAP.isEmpty()) {
                for (L1PcInstance pc : MapTimerThread.TIMINGMAP.keySet()) {
                    if (pc != null) {
                        if (pc.getNetConnection() == null) {
                            continue;
                        }
                        else if (pc.isDead()) {
                            continue;
                        }
                        else if (pc.isInTimeMap()) {
                            this.MapTimeCheck(pc);
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (Throwable e) {
            MapTimerThread._log.error("計時地圖時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            MapTimerThread MapTimer = new MapTimerThread();
            MapTimer.start();
        }
    }
    
    private void teleport(L1PcInstance pc) {
        MapTimerThread.TIMINGMAP.remove(pc);
        if (World.get().getPlayer(pc.getName()) == null) {
            return;
        }
        else {
            if (pc.isInTimeMap()) {
                int locx = 33427;
                int locy = 32822;
                int mapid = 4;
                int heading = 5;
                L1Teleport.teleport(pc, 33427, 32822, (short)4, 5, true);
            }
            return;
        }
    }
}
