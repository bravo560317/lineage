package com.lineage.server.timecontroller.server;

import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.sql.CharWeaponTimeTable;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.model.L1Object;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.world.WorldPet;
import com.lineage.server.datatables.lock.PetReading;
import com.lineage.server.datatables.lock.CharItemsTimeReading;
import com.lineage.server.datatables.sql.CharItemsTimeTable;
import com.lineage.server.datatables.ItemVIPTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.sql.Timestamp;
import com.lineage.server.world.WorldItem;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerItemUserTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ServerItemUserTimer.class);
    }
    
    public void start() {
        int timeMillis = 60000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 60000L, 60000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1ItemInstance> items = WorldItem.get().all();
            if (items.isEmpty()) {
                return;
            }
            else {
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                for (L1ItemInstance item : items) {
                    if (item.get_time() == null) {
                        continue;
                    }
                    else {
                        if (item.get_magic_weapon() != null && item.get_magic_weapon().getMaxUseTime() > 0) {
                            Timestamp date = item.get_time();
                            if (date != null && date.before(ts)) {
                                checkItem2(item, ts);
                                continue;
                            }
                        }
                        checkItem(item, ts);
                        Thread.sleep(5L);
                    }
                }
            }
        }
        catch (Exception e) {
            ServerItemUserTimer._log.error("物品使用期限计时时间轴异常重启", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerItemUserTimer userTimer = new ServerItemUserTimer();
            userTimer.start();
        }
    }
    
    private static void checkItem(L1ItemInstance item, Timestamp ts) throws Exception {
        try {
            if (item.get_time().before(ts)) {
                L1Object object = World.get().findObject(item.get_char_objid());
                if (object != null) {
                    if (object instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)object;
                        int itemId = item.getItemId();
                        if (item.isEquipped()) {
                            tgpc.getInventory().setEquipped(item, false);
                            Thread.sleep(1000L);
                            if (ItemVIPTable.get().checkVIP(itemId)) {
                                ItemVIPTable.get().deleItemVIP(tgpc, itemId);
                                CharItemsTimeTable.delete(item.getId());
                                CharItemsTimeReading.get().load();
                            }
                        }
                        tgpc.getInventory().removeItem(item);
                        Thread.sleep(500L);
                        L1Pet pet = PetReading.get().getTemplate(item.getId());
                        if (pet != null) {
                            L1PetInstance tgpet = WorldPet.get().get(pet.get_objid());
                            if (tgpet != null) {
                                tgpet.dropItem();
                                tgpet.deleteMe();
                            }
                        }
                    }
                }
                else {
                    CharItemsReading.get().deleteItem(item.get_char_objid(), item);
                }
            }
        }
        catch (Exception e) {
            ServerItemUserTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void checkItem2(L1ItemInstance item, Timestamp ts) throws Exception {
        item.set_magic_weapon(null);
        item.set_time(null);
        CharWeaponTimeTable.delete(item.getId());
        L1Object object = World.get().findObject(item.get_char_objid());
        if (object != null && object instanceof L1PcInstance) {
            L1PcInstance tgpc = (L1PcInstance)object;
            tgpc.sendPackets(new S_ItemStatus(item));
        }
    }
}
