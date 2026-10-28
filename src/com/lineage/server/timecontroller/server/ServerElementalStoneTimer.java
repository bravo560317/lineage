package com.lineage.server.timecontroller.server;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.types.Point;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Location;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.config.ConfigAlt;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Object;
import java.util.Random;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.L1GroundInventory;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerElementalStoneTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static final short ELVEN_FOREST_MAPID = 4;
    private static final int MAX_COUNT;
    private static final int INTERVAL = 3;
    private static final int SLEEP_TIME = 30;
    private static final int FIRST_X = 32911;
    private static final int FIRST_Y = 32210;
    private static final int LAST_X = 33141;
    private static final int LAST_Y = 32500;
    private static final int ELEMENTAL_STONE_ID = 40515;
    private ArrayList<L1GroundInventory> _itemList;
    private final L1Map _map;
    private Random _random;
    private final L1Object _dummy;
    
    static {
        _log = LogFactory.getLog(ServerElementalStoneTimer.class);
        MAX_COUNT = ConfigAlt.ELEMENTAL_STONE_AMOUNT;
    }
    
    public ServerElementalStoneTimer() {
        this._itemList = new ArrayList(ServerElementalStoneTimer.MAX_COUNT);
        this._map = L1WorldMap.get().getMap((short)4);
        this._random = new Random();
        this._dummy = new L1Object();
    }
    
    public void start() {
        int timeMillis = 30000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 30000L, 30000L);
    }
    
    private boolean canPut(L1Location loc) {
        this._dummy.setMap(loc.getMap());
        this._dummy.setX(loc.getX());
        this._dummy.setY(loc.getY());
        return World.get().getVisiblePlayer(this._dummy).size() <= 0;
    }
    
    private Point nextPoint() {
        int newX = this._random.nextInt(230) + 32911;
        int newY = this._random.nextInt(290) + 32210;
        return new Point(newX, newY);
    }
    
    private void removeItemsPickedUp() {
        for (int i = 0; i < this._itemList.size(); ++i) {
            L1GroundInventory gInventory = this._itemList.get(i);
            if (!gInventory.checkItem(40515)) {
                this._itemList.remove(i);
                --i;
            }
        }
    }
    
    private void putElementalStone(L1Location loc) {
        L1GroundInventory gInventory = World.get().getInventory(loc);
        L1ItemInstance item = ItemTable.get().createItem(40515);
        item.setEnchantLevel(0);
        item.setCount(1L);
        gInventory.storeItem(item);
        this._itemList.add(gInventory);
    }
    
    @Override
    public void run() {
        try {
            this.removeItemsPickedUp();
            while (this._itemList.size() < ServerElementalStoneTimer.MAX_COUNT) {
                L1Location loc = new L1Location(this.nextPoint(), this._map);
                if (!this.canPut(loc)) {
                    continue;
                }
                else {
                    this.putElementalStone(loc);
                    Thread.sleep(3000L);
                }
            }
        }
        catch (Throwable e) {
            ServerElementalStoneTimer._log.error("元素石生成時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerElementalStoneTimer elementalStoneTimer = new ServerElementalStoneTimer();
            elementalStoneTimer.start();
        }
    }
}
