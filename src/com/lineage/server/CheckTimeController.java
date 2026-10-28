package com.lineage.server;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1MapsLimitTime;
import java.util.Iterator;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.MapsGroupTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;

public final class CheckTimeController implements Runnable
{
    private static final Log _log;
    private static CheckTimeController _instance;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(CheckTimeController.class);
    }
    
    public static CheckTimeController getInstance() {
        if (CheckTimeController._instance == null) {
            CheckTimeController._instance = new CheckTimeController();
        }
        return CheckTimeController._instance;
    }
    
    @Override
    public void run() {
        try {
            for (L1PcInstance pc : World.get().getAllPlayers()) {
                if (pc != null) {
                    L1MapsLimitTime mapsLimitTime = MapsGroupTable.get().findGroupMap((int)pc.getMapId());
                    if (mapsLimitTime != null) {
                        int order_id = mapsLimitTime.getOrderId();
                        int used_time = pc.getMapsTime(order_id);
                        int limit_time = mapsLimitTime.getLimitTime();
                        if (used_time > limit_time) {
                            L1Teleport.teleport(pc, mapsLimitTime.getOutMapX(), mapsLimitTime.getOutMapY(), mapsLimitTime.getOutMapId(), pc.getHeading(), true);
                        }
                        else {
                            pc.putMapsTime(order_id, used_time + 1);
                        }
                    }
                }
                Thread.sleep(1L);
            }
        }
        catch (Exception e) {
            CheckTimeController._log.error("PC檢查時間軸異常重啟");
            GeneralThreadPool.get().cancel(this._timer, false);
            (CheckTimeController._instance = new CheckTimeController()).start();
        }
    }
    
    private final void checkTime(L1PcInstance pc, int usedTime, int limitTime) {
        int remainTime = limitTime - usedTime;
        if (remainTime % 60 == 0) {
            if (remainTime < 3600) {
                pc.sendPackets(new S_ServerMessage(1527, String.valueOf(remainTime / 60)));
            }
            else if (remainTime % 3600 == 0) {
                pc.sendPackets(new S_ServerMessage(1526, String.valueOf(remainTime / 3600)));
            }
            else {
                pc.sendPackets(new S_ServerMessage(1525, String.valueOf(remainTime / 3600), String.valueOf(remainTime % 3600 / 60)));
            }
        }
        else if (remainTime < 60 && (remainTime % 5 == 0 || remainTime < 5)) {
            pc.sendPackets(new S_ServerMessage(1528, String.valueOf(remainTime)));
        }
    }
    
    public final void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
}
