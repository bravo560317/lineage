package com.lineage.server.timecontroller.server;

import java.util.Iterator;
import com.lineage.server.datatables.LightSpawnTable;
import com.lineage.server.model.Instance.L1FieldObjectInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.datatables.NpcSpawnTable;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerLightTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static boolean isSpawn;
    
    static {
        _log = LogFactory.getLog(ServerLightTimer.class);
        ServerLightTimer.isSpawn = false;
    }
    
    public void start() {
        int timeMillis = 60000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            checkLightTime();
            NpcSpawnTable.get().checkMaps();
        }
        catch (Exception e) {
            ServerLightTimer._log.error("照明物件召喚時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerLightTimer lightTimer = new ServerLightTimer();
            lightTimer.start();
        }
    }
    
    private static void checkLightTime() {
        try {
            int serverTime = L1GameTimeClock.getInstance().currentTime().getSeconds();
            int nowTime = serverTime % 86400;
            if (nowTime >= 21300 && nowTime < 64500) {
                if (ServerLightTimer.isSpawn) {
                    ServerLightTimer.isSpawn = false;
                    for (L1Object object : World.get().getObject()) {
                        if (object instanceof L1FieldObjectInstance) {
                            L1FieldObjectInstance npc = (L1FieldObjectInstance)object;
                            if ((npc.getNpcTemplate().get_npcId() == 81177 || npc.getNpcTemplate().get_npcId() == 81178 || npc.getNpcTemplate().get_npcId() == 81179 || npc.getNpcTemplate().get_npcId() == 81180 || npc.getNpcTemplate().get_npcId() == 81181) && (npc.getMapId() == 0 || npc.getMapId() == 4)) {
                                npc.deleteMe();
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            else if ((nowTime >= 64500 && nowTime <= 86400) || (nowTime >= 0 && nowTime < 21300 && !ServerLightTimer.isSpawn)) {
                ServerLightTimer.isSpawn = true;
                LightSpawnTable.getInstance();
            }
        }
        catch (Exception e) {
            ServerLightTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
