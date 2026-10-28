package com.lineage.server.timecontroller.pc;

import com.lineage.server.templates.L1Item;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Fishing;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.FishingTable;
import com.lineage.server.thread.PcOtherThreadPool;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class PcFishingTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private int _time;
    private static final List<L1PcInstance> _fishingList;
    
    static {
        _log = LogFactory.getLog(PcFishingTimer.class);
        _fishingList = new ArrayList();
    }
    
    public PcFishingTimer() {
        this._time = 0;
    }
    
    public void start() {
        int timeMillis = 80000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 80000L, 80000L);
    }
    
    @Override
    public void run() {
        try {
            this.fishing();
        }
        catch (Exception e) {
            PcFishingTimer._log.error("釣魚時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            PcFishingTimer timerTask = new PcFishingTimer();
            timerTask.start();
        }
    }
    
    public static void addMember(L1PcInstance pc) {
        if (pc == null || PcFishingTimer._fishingList.contains(pc)) {
            return;
        }
        else {
            PcFishingTimer._fishingList.add(pc);
            return;
        }
    }
    
    public static void removeMember(L1PcInstance pc) {
        if (pc == null || !PcFishingTimer._fishingList.contains(pc)) {
            return;
        }
        else {
            PcFishingTimer._fishingList.remove(pc);
            return;
        }
    }
    
    private void fishing() {
        if (!PcFishingTimer._fishingList.isEmpty()) {
            ++this._time;
            for (int i = 0; i < PcFishingTimer._fishingList.size(); ++i) {
                L1PcInstance pc = PcFishingTimer._fishingList.get(i);
                if (pc.isFishing()) {
                    if (pc.getMapId() != 5300) {
                        finishFishing(pc, true);
                    }
                    else if (!pc.getInventory().checkItem(83002, 1L)) {
                        finishFishing(pc, true);
                    }
                    else if (pc.getOnlineStatus() != 1) {
                        finishFishing(pc, true);
                    }
                    else if (pc.getNetConnection() == null) {
                        finishFishing(pc, true);
                    }
                    else if (pc.getFishingPoleId() == 0) {
                        finishFishing(pc, true);
                    }
                    else {
                        int fishingpoleid = pc.getFishingPoleId();
                        L1ItemInstance fishingpole = pc.getInventory().findItemId(fishingpoleid);
                        if (fishingpole != null && fishingpoleid != 83001) {
                            L1Fishing fish = FishingTable.get().get_fish(fishingpoleid);
                            this.successFishing(pc, fish);
                            fishingpole.setChargeCount(fishingpole.getChargeCount() - 1);
                            pc.getInventory().updateItem(fishingpole, 128);
                            if (fishingpole.getChargeCount() <= 0) {
                                pc.getInventory().consumeItem(fishingpoleid, 1L);
                                L1ItemInstance newitem = ItemTable.get().createItem(83001);
                                newitem.setIdentified(true);
                                pc.getInventory().storeItem(newitem);
                                pc.setFishingPoleId(83001);
                            }
                        }
                        else if (this._time == 3 && fishingpole != null && fishingpoleid == 83001) {
                            L1Fishing fish = FishingTable.get().get_fish(fishingpoleid);
                            this.successFishing(pc, fish);
                        }
                    }
                }
            }
            if (this._time >= 3) {
                this._time = 0;
            }
        }
    }
    
    public static void finishFishing(L1PcInstance pc, boolean msg) {
        pc.setFishing(false, -1, -1);
        if (msg) {
            pc.sendPackets(new S_ServerMessage(1163));
        }
        pc.sendPacketsAll(new S_CharVisualUpdate(pc));
        removeMember(pc);
    }
    
    private void successFishing(L1PcInstance pc, L1Fishing fish) {
        if (fish == null) {
            if (!pc.getInventory().consumeItem(83002, 1L)) {
                finishFishing(pc, true);
            }
        }
        else {
            L1Item item = ItemTable.get().getTemplate(fish.get_itemid());
            if (item != null) {
                if (pc.getInventory().consumeItem(83002, 1L)) {
                    CreateNewItem.createNewItem(pc, fish.get_itemid(), 1L);
                }
                else {
                    finishFishing(pc, true);
                }
            }
        }
    }
}
