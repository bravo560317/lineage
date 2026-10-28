package com.lineage.server.timecontroller.pet;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class DollGetTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(DollGetTimer.class);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : all) {
                    if (tgpc.get_doll_get_time_src() > 0 && checkErr(tgpc)) {
                        int itemid = tgpc.get_doll_get()[0];
                        int count = tgpc.get_doll_get()[1];
                        L1ItemInstance item = ItemTable.get().createItem(itemid);
                        if (item != null) {
                            item.setCount((long)count);
                            if (tgpc.getInventory().checkAddItem(item, (long)count) == 0) {
                                tgpc.getInventory().storeItem(item);
                                tgpc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                            }
                        }
                        Thread.sleep(50L);
                    }
                }
            }
        }
        catch (Exception e) {
            DollGetTimer._log.error("魔法娃娃處理時間軸(贈送道具)異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            DollGetTimer dollTimer = new DollGetTimer();
            dollTimer.start();
        }
    }
    
    private static boolean checkErr(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
            else if (tgpc.isTeleport()) {
                return false;
            }
            else {
                int newtime = tgpc.get_doll_get_time() - 1;
                tgpc.set_doll_get_time(newtime);
                if (newtime <= 0) {
                    tgpc.set_doll_get_time(tgpc.get_doll_get_time_src());
                    return true;
                }
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }
}
