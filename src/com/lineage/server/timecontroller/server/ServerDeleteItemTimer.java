package com.lineage.server.timecontroller.server;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import com.lineage.server.model.L1HouseLocation;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.config.ConfigAlt;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerDeleteItemTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static final ArrayList<L1ItemInstance> _itemList;
    
    static {
        _log = LogFactory.getLog(ServerDeleteItemTimer.class);
        _itemList = new ArrayList();
    }
    
    public void start() {
        int timeMillis = ConfigAlt.ALT_ITEM_DELETION_TIME * 60 * 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    public static void add(L1ItemInstance item) {
        ServerDeleteItemTimer._itemList.add(item);
    }
    
    public static boolean contains(L1ItemInstance item) {
        return ServerDeleteItemTimer._itemList.contains(item);
    }
    
    public static void remove(L1ItemInstance item) {
        if (!ServerDeleteItemTimer._itemList.remove(item)) {
            ServerDeleteItemTimer._log.error("地面物件刪除失敗 OBJID:" + item.getId());
        }
        int x = item.getX();
        int y = item.getY();
        short m = item.getMapId();
        L1Inventory inventory = World.get().getInventory(x, y, m);
        if (inventory.getItem(item.getId()) != null) {
            inventory.removeItem(item);
        }
    }
    
    @Override
    public void run() {
        try {
            if (ServerDeleteItemTimer._itemList.isEmpty()) {
                return;
            }
            else {
                World.get().broadcastPacketToAll(new S_SystemMessage("\\fU10秒後將清除地上垃圾。"));
                Thread.sleep(5000L);
                World.get().broadcastPacketToAll(new S_SystemMessage("\\fU5秒後將清除地上垃圾。"));
                Thread.sleep(1000L);
                World.get().broadcastPacketToAll(new S_SystemMessage("\\fU4秒後將清除地上垃圾。"));
                Thread.sleep(1000L);
                World.get().broadcastPacketToAll(new S_SystemMessage("\\fU3秒後將清除地上垃圾。"));
                Thread.sleep(1000L);
                World.get().broadcastPacketToAll(new S_SystemMessage("\\fU2秒後將清除地上垃圾。"));
                Thread.sleep(1000L);
                World.get().broadcastPacketToAll(new S_SystemMessage("\\fU1秒後將清除地上垃圾。"));
                Thread.sleep(1000L);
                World.get().broadcastPacketToAll(new S_SystemMessage("\\fU完成清理地上多餘的垃圾。"));
                Object[] array;
                for (int length = (array = ServerDeleteItemTimer._itemList.toArray()).length, i = 0; i < length; ++i) {
                    Object object = array[i];
                    L1ItemInstance e = (L1ItemInstance)object;
                    if (e != null) {
                        if (checkItem(e)) {
                            remove(e);
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            ServerDeleteItemTimer._log.error("自動清除地面物件時間軸異常重啟", e2);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerDeleteItemTimer deleteItemTimer = new ServerDeleteItemTimer();
            deleteItemTimer.start();
        }
    }
    
    private static boolean checkItem(L1ItemInstance item) {
        List<L1PcInstance> players = (List)World.get().getVisiblePlayer(item);
        if (item.getItem().getItemId() == 40515) {
            return false;
        }
        else if (L1HouseLocation.isInHouse(item.getX(), item.getY(), item.getMapId())) {
            return false;
        }
        else {
            int x = item.getX();
            int y = item.getY();
            short m = item.getMapId();
            L1Inventory inventory = World.get().getInventory(x, y, m);
            if (inventory.getItem(item.getId()) == null) {
                remove(item);
                return false;
            }
            else {
                return true;
            }
        }
    }
}
