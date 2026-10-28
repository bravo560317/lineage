package com.lineage.data.event;

import java.util.Iterator;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.concurrent.ScheduledFuture;
import java.util.TimerTask;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.templates.L1Event;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class OnlineGiftSet extends EventExecutor
{
    private static final Log _log;
    private static int _time;
    private static int _level;
    private static int _isFishing;
    private static int _FishingItemId;
    private static int _isPrivateShop;
    private static int _PrivateShopItemId;
    private static int _isSafeZone;
    private static int _SafeZoneItemId;
    private static int _isNormal;
    private static int _NormalItemId;
    private static int _count1;
    private static int _count2;
    private static int _count3;
    private static int _count4;
    private static final Map<L1PcInstance, Integer> _getMap;
    
    static {
        _log = LogFactory.getLog(OnlineGiftSet.class);
        OnlineGiftSet._time = 0;
        OnlineGiftSet._level = 0;
        OnlineGiftSet._isFishing = 0;
        OnlineGiftSet._FishingItemId = 0;
        OnlineGiftSet._isPrivateShop = 0;
        OnlineGiftSet._PrivateShopItemId = 0;
        OnlineGiftSet._isSafeZone = 0;
        OnlineGiftSet._SafeZoneItemId = 0;
        OnlineGiftSet._isNormal = 0;
        OnlineGiftSet._NormalItemId = 0;
        OnlineGiftSet._count1 = 0;
        OnlineGiftSet._count2 = 0;
        OnlineGiftSet._count3 = 0;
        OnlineGiftSet._count4 = 0;
        _getMap = new ConcurrentHashMap();
    }
    
    private OnlineGiftSet() {
    }
    
    public static EventExecutor get() {
        return new OnlineGiftSet();
    }
    
    public static void add(L1PcInstance tgpc) {
        if (OnlineGiftSet._time == 0) {
            return;
        }
        else {
            if (OnlineGiftSet._getMap.get(tgpc) == null) {
                OnlineGiftSet._getMap.put(tgpc, OnlineGiftSet._time);
            }
            return;
        }
    }
    
    public static void remove(L1PcInstance tgpc) {
        if (OnlineGiftSet._time == 0) {
            return;
        }
        else {
            OnlineGiftSet._getMap.remove(tgpc);
            return;
        }
    }
    
    private static void getitem(L1PcInstance tgpc) {
        try {
            boolean OK = false;
            int itemid = 0;
            int count = 0;
            if (check(tgpc)) {
                if (tgpc.isFishing() && OnlineGiftSet._isFishing == 1) {
                    OK = true;
                    itemid = OnlineGiftSet._FishingItemId;
                    count = OnlineGiftSet._count1;
                }
                else if (tgpc.isPrivateShop() && OnlineGiftSet._isPrivateShop == 1) {
                    OK = true;
                    itemid = OnlineGiftSet._PrivateShopItemId;
                    count = OnlineGiftSet._count2;
                }
                else if (tgpc.isSafetyZone() && !tgpc.isPrivateShop() && !tgpc.isFishing() && OnlineGiftSet._isSafeZone == 1) {
                    OK = true;
                    itemid = OnlineGiftSet._SafeZoneItemId;
                    count = OnlineGiftSet._count3;
                }
                else if (!tgpc.isPrivateShop() && !tgpc.isFishing() && !tgpc.isSafetyZone() && OnlineGiftSet._isNormal == 1) {
                    OK = true;
                    itemid = OnlineGiftSet._NormalItemId;
                    count = OnlineGiftSet._count4;
                }
                if (OK && itemid != 0) {
                    L1ItemInstance item = ItemTable.get().createItem(itemid);
                    if (item != null) {
                        if (!item.isStackable()) {
                            OnlineGiftSet._log.error("設定給予獎勵物品異常 - 這個編號的物品無發堆疊:" + itemid);
                        }
                        else {
                            item.setCount((long)count);
                            if (tgpc.getInventory().checkAddItem(item, 1L) == 0) {
                                tgpc.getInventory().storeItem(item);
                                tgpc.sendPackets(new S_ServerMessage("獲得連線獎勵: " + item.getLogName()));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            OnlineGiftSet._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static boolean check(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getLevel() < OnlineGiftSet._level) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
        }
        catch (Exception e) {
            OnlineGiftSet._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }
    
    @Override
    public void execute(L1Event event) {
        PerformanceTimer timer = new PerformanceTimer();
        boolean isError = false;
        try {
            String[] set;
            while (true) {
                set = event.get_eventother().split(",");
                try {
                    OnlineGiftSet._time = Integer.parseInt(set[0]);
                    if (OnlineGiftSet._time <= 0) {
                        OnlineGiftSet._log.error("設定給予獎勵的時間(分鐘)異常 - 將不啟用本項設置");
                        isError = true;
                        return;
                    }
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定給予獎勵的時間(分鐘)異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._level = Integer.parseInt(set[1]);
                    if (OnlineGiftSet._level <= 0 && OnlineGiftSet._level >= 100) {
                        OnlineGiftSet._log.error("設定給予獎勵的等級異常 - 將不啟用本項設置");
                        isError = true;
                        return;
                    }
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定給予獎勵的等級異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._isFishing = Integer.parseInt(set[2]);
                    if (OnlineGiftSet._isFishing > 1) {
                        OnlineGiftSet._log.error("設定釣魚狀態給予獎勵異常 - 將不啟用本項設置");
                        isError = true;
                        return;
                    }
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定釣魚狀態給予獎勵異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._FishingItemId = Integer.parseInt(set[3]);
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定釣魚狀態給予獎勵道具異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._count1 = Integer.parseInt(set[4]);
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定釣魚數量狀態給予獎勵道具異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._isPrivateShop = Integer.parseInt(set[5]);
                    if (OnlineGiftSet._isPrivateShop > 1) {
                        OnlineGiftSet._log.error("設定擺攤狀態給予獎勵異常 - 將不啟用本項設置");
                        isError = true;
                        return;
                    }
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定擺攤狀態給予獎勵異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._PrivateShopItemId = Integer.parseInt(set[6]);
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定擺攤狀態給予獎勵道具異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._count2 = Integer.parseInt(set[7]);
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定擺攤數量狀態給予獎勵道具異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._isSafeZone = Integer.parseInt(set[8]);
                    if (OnlineGiftSet._isSafeZone > 1) {
                        OnlineGiftSet._log.error("設定安全區狀態給予獎勵異常 - 將不啟用本項設置");
                        isError = true;
                        return;
                    }
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定安全區狀態給予獎勵異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._SafeZoneItemId = Integer.parseInt(set[9]);
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定安全區狀態給予獎勵道具異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._count3 = Integer.parseInt(set[10]);
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定安全區狀態數量給予獎勵道具異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                try {
                    OnlineGiftSet._isNormal = Integer.parseInt(set[11]);
                    if (OnlineGiftSet._isNormal > 1) {
                        OnlineGiftSet._log.error("設定練功狀態給予獎勵異常 - 將不啟用本項設置");
                        isError = true;
                        return;
                    }
                }
                catch (Exception e) {
                    OnlineGiftSet._log.error("設定練功狀態給予獎勵異常 - 將不啟用本項設置");
                    isError = true;
                    return;
                }
                break;
            }
            try {
                OnlineGiftSet._NormalItemId = Integer.parseInt(set[12]);
            }
            catch (Exception e) {
                OnlineGiftSet._log.error("設定練功狀態給予獎勵道具異常 - 將不啟用本項設置");
                isError = true;
                return;
            }
            try {
                OnlineGiftSet._count4 = Integer.parseInt(set[13]);
            }
            catch (Exception e) {
                OnlineGiftSet._log.error("設定練功狀態給予獎勵道具異常 - 將不啟用本項設置");
                isError = true;
                return;
            }
        }
        catch (Exception e2) {
            OnlineGiftSet._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            if (!isError) {
                OnlineGiftSet.GetItemTimer getItemTimer = new OnlineGiftSet.GetItemTimer();
                getItemTimer.start();
                OnlineGiftSet._log.info("載入連線獎勵系統:(" + timer.get() + "ms)");
            }
        }
    }
    
    private class GetItemTimer extends TimerTask
    {
        private ScheduledFuture<?> _timer;
        
        public void start() {
            int timeMillis = 60000;
            this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 60000L, 60000L);
        }
        
        @Override
        public void run() {
            try {
                if (!OnlineGiftSet._getMap.isEmpty()) {
                    for (L1PcInstance tgpc : OnlineGiftSet._getMap.keySet()) {
                        Thread.sleep(1L);
                        if (World.get().getPlayer(tgpc.getName()) == null) {
                            OnlineGiftSet._getMap.remove(tgpc);
                        }
                        else {
                            Integer time = OnlineGiftSet._getMap.get(tgpc);
                            if (time != null) {
                                time = (Integer)(time - 1);
                                if (time <= 0) {
                                    getitem(tgpc);
                                    OnlineGiftSet._getMap.put(tgpc, (Integer)OnlineGiftSet._time);
                                }
                                else {
                                    OnlineGiftSet._getMap.put(tgpc, time);
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                OnlineGiftSet._log.error("在線獎勵清單時間軸異常重啟", e);
                GeneralThreadPool.get().cancel(this._timer, false);
                OnlineGiftSet.GetItemTimer getItemTimer = new OnlineGiftSet.GetItemTimer();
                getItemTimer.start();
            }
        }
    }
}
