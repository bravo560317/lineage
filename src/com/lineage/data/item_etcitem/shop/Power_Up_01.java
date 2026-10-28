package com.lineage.data.item_etcitem.shop;

import com.lineage.server.templates.L1Item;
import java.util.Map;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_AddItem;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.serverpackets.S_DeleteInventoryItem;
import com.lineage.server.world.World;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1ItemPowerUpdate;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemPowerUpdateTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Power_Up_01 extends ItemExecutor
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(Power_Up_01.class);
        _random = new Random();
    }
    
    public static ItemExecutor get() {
        return new Power_Up_01();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int targObjId = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
            if (tgItem == null) {
                return;
            }
            else if (tgItem.isEquipped()) {
                pc.sendPackets(new S_SystemMessage("請先卸除該裝備在升級。"));
                return;
            }
            else {
                L1ItemPowerUpdate info = ItemPowerUpdateTable.get().get(tgItem.getItemId());
                if (info == null) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else if (info.get_mode() == 4) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else if (info.get_nedid() != item.getItemId()) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else {
                    Map<Integer, L1ItemPowerUpdate> tmplist = ItemPowerUpdateTable.get().get_type_id(tgItem.getItemId());
                    if (tmplist.isEmpty()) {
                        pc.sendPackets(new S_ServerMessage(79));
                        return;
                    }
                    else {
                        int order_id = info.get_order_id();
                        L1ItemPowerUpdate tginfo = tmplist.get((Integer)(order_id + 1));
                        if (tginfo == null) {
                            pc.sendPackets(new S_ServerMessage(79));
                            return;
                        }
                        else {
                            pc.getInventory().removeItem(item, 1L);
                            if (Power_Up_01._random.nextInt(1000) < info.get_random()) {
                                L1Item l1item = ItemTable.get().getTemplate(tginfo.get_itemid());
                                RecordTable.get().recordFailureArmor2(pc.getName(), tgItem.getName(), l1item.getName(), pc.getIp());
                                if (info.getallmsg() != null) {
                                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format(info.getallmsg(), pc.getName(), tgItem.getName(), l1item.getName())));
                                }
                                pc.sendPackets(new S_DeleteInventoryItem(tgItem.getId()));
                                tgItem.setItemId(tginfo.get_itemid());
                                tgItem.setItem(l1item);
                                if (tgItem.getBless() == 0) {
                                    l1item.setBless(0);
                                }
                                else {
                                    l1item.setBless(1);
                                }
                                try {
                                    CharItemsReading.get().updateItemId_Name(tgItem);
                                    CharItemsReading.get().updateItemBless(tgItem);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                pc.sendPackets(new S_AddItem(tgItem));
                                pc.sendPackets(new S_ServerMessage(1410, tgItem.getName()));
                            }
                            else {
                                switch (info.get_mode()) {
                                    case 0: {
                                        pc.sendPackets(new S_ServerMessage(1411, tgItem.getName()));
                                        break;
                                    }
case 1: {
    if (tgItem.getproctect6()) {
        tgItem.setproctect6(false);
        pc.sendPackets(new S_ItemStatus(tgItem));
        pc.getInventory().updateItem(tgItem, 4);
        pc.getInventory().saveItem(tgItem, 4);
        pc.sendPackets(new S_ServerMessage("該裝備受到保護卷的祝福,沒發生什麼事情"));
        return;
    } else {
        L1ItemPowerUpdate lastinfo = tmplist.get((Integer)(order_id - 1));
        pc.sendPackets(new S_ServerMessage(1411, tgItem.getName()));

        // 倒退處理（不記錄）
        L1ItemInstance lastitem = ItemTable.get().createItem(lastinfo.get_itemid());
        if (tgItem.getBless() == 0) {
            lastitem.setsaveBless(0);
        } else {
            lastitem.setsaveBless(1);
        }

        pc.getInventory().removeItem(tgItem, 1L);
        lastitem.setIdentified(true);
        lastitem.setCount(1L);
        pc.getInventory().storeItem(lastitem);
        break;
    }
}
                                    case 2: {
                                        if (tgItem.getproctect7()) {
                                            tgItem.setproctect7(false);
                                            pc.sendPackets(new S_ItemStatus(tgItem));
                                            pc.getInventory().updateItem(tgItem, 4);
                                            pc.getInventory().saveItem(tgItem, 4);
                                            pc.sendPackets(new S_ServerMessage("該裝備受到保護卷的祝福,沒發生什麼事情"));
                                            return;
                                        }
                                        else {
RecordTable.get().recordFailureArmor2(
    pc.getName(),
    tgItem.getName(),
    "（升級失敗消失）",
    pc.getIp()
);

pc.sendPackets(new S_ServerMessage(164, tgItem.getLogName(), "$252"));
pc.getInventory().removeItem(tgItem, 1L);
break;
                                        }
                                    }
                                    case 3: {
if (Power_Up_01._random.nextBoolean()) {
    L1ItemPowerUpdate lastinfo2 = tmplist.get((Integer)(order_id - 1));
    pc.sendPackets(new S_ServerMessage(1411, tgItem.getName()));

    L1ItemInstance lastitem2 = ItemTable.get().createItem(lastinfo2.get_itemid());

    RecordTable.get().recordFailureArmor2(
        pc.getName(),
        tgItem.getName(),
        lastitem2.getName(),
        pc.getIp()
    );

    pc.getInventory().removeItem(tgItem, 1L);

    lastitem2.setIdentified(true);
    lastitem2.setCount(1L);
    pc.getInventory().storeItem(lastitem2);
    break;
}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            Power_Up_01._log.error(e2.getLocalizedMessage(), e2);
        }
    }
}
