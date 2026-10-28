package com.lineage.data.item_etcitem.quest;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.datatables.lock.CharItemsTimeReading;
import java.sql.Timestamp;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class MagicReel_M01 extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(MagicReel_M01.class);
    }
    
    public static ItemExecutor get() {
        return new MagicReel_M01();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int itemobj = data[0];
            L1ItemInstance tgitem = pc.getInventory().getItem(itemobj);
            if (tgitem.getItem().getType2() == 0) {
                int itemid = -1;
                switch (tgitem.getItemId()) {
                    case 49317: {
                        itemid = 30421;
                        break;
                    }
                    case 49318: {
                        itemid = 30423;
                        break;
                    }
                    case 49319: {
                        itemid = 30425;
                        break;
                    }
                    case 49320: {
                        itemid = 30422;
                        break;
                    }
                    case 49321: {
                        itemid = 30424;
                        break;
                    }
                    case 49322: {
                        itemid = 30426;
                        break;
                    }
                    case 49323: {
                        itemid = 30429;
                        break;
                    }
                    case 49324: {
                        itemid = 30430;
                        break;
                    }
                    case 49325: {
                        itemid = 30428;
                        break;
                    }
                    case 49326: {
                        itemid = 30427;
                        break;
                    }
                    case 49327: {
                        itemid = 30471;
                        break;
                    }
                    case 49328: {
                        itemid = 30472;
                        break;
                    }
                    case 49329: {
                        itemid = 30473;
                        break;
                    }
                    case 49330: {
                        itemid = 30474;
                        break;
                    }
                    case 49331: {
                        itemid = 30475;
                        break;
                    }
                    case 49332: {
                        itemid = 30476;
                        break;
                    }
                    case 49333: {
                        itemid = 30477;
                        break;
                    }
                    case 49334: {
                        itemid = 30478;
                        break;
                    }
                    default: {
                        pc.sendPackets(new S_ServerMessage(79));
                        break;
                    }
                }
                if (itemid != -1) {
                    pc.getInventory().removeItem(item, 1L);
                    pc.getInventory().removeItem(tgitem, 1L);
                    L1ItemInstance newitem = ItemTable.get().createItem(itemid);
                    long time = System.currentTimeMillis();
                    long x1 = 21600L;
                    long x2 = x1 * 1000L;
                    long upTime = x2 + time;
                    Timestamp ts = new Timestamp(upTime);
                    newitem.set_time(ts);
                    CharItemsTimeReading.get().addTime(newitem.getId(), ts);
                    pc.sendPackets(new S_ItemName(newitem));
                    CreateNewItem.createNewItem(pc, newitem, 1L);
                }
            }
            else if (tgitem.getItem().getType2() == 2) {
                if (tgitem.getItemId() >= 21157 && tgitem.getItemId() <= 21178) {
                    pc.getInventory().removeItem(item, 1L);
                    long time2 = System.currentTimeMillis();
                    long x3 = 21600L;
                    long x4 = x3 * 1000L;
                    long upTime2 = x4 + time2;
                    Timestamp ts2 = new Timestamp(upTime2);
                    tgitem.set_time(ts2);
                    if (CharItemsTimeReading.get().isExistTimeData(tgitem.getId())) {
                        pc.sendPackets(new S_ServerMessage(79));
                        return;
                    }
                    else {
                        CharItemsTimeReading.get().addTime(tgitem.getId(), ts2);
                        pc.sendPackets(new S_ItemName(tgitem));
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
        }
        catch (Exception e) {
            MagicReel_M01._log.error(e.getLocalizedMessage(), e);
        }
    }
}
