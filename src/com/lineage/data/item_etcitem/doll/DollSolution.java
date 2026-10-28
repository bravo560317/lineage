package com.lineage.data.item_etcitem.doll;

import java.util.Map;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1ItemPowerUpdate;
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemPowerUpdateTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class DollSolution extends ItemExecutor
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(DollSolution.class);
        _random = new Random();
    }
    
    public static ItemExecutor get() {
        return new DollSolution();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int targObjId = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
            if (tgItem == null) {
                return;
            }
            else {
                L1ItemPowerUpdate info = ItemPowerUpdateTable.get().get(tgItem.getItemId());
                if (info == null) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else {
                    L1Doll doll = DollPowerTable.get().get_type(tgItem.getItemId());
                    if (doll == null) {
                        pc.sendPackets(new S_ServerMessage(2477));
                        return;
                    }
                    else if (pc.getDoll(tgItem.getId()) != null) {
                        pc.sendPackets(new S_ServerMessage(1181));
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
                                int remainingtime = tgItem.getRemainingTime();
                                pc.getInventory().removeItem(item, 1L);
                                if (DollSolution._random.nextInt(1000) < info.get_random()) {
                                    pc.getInventory().removeItem(tgItem, 1L);
                                    L1ItemInstance tginfo_item = ItemTable.get().createItem(tginfo.get_itemid());
                                    if (tginfo_item != null) {
                                        if (remainingtime > 0) {
                                            tginfo_item.setRemainingTime(remainingtime);
                                        }
                                        tginfo_item.setIdentified(true);
                                        tginfo_item.setCount(1L);
                                        pc.getInventory().storeItem(tginfo_item);
                                        pc.sendPackets(new S_ServerMessage("獲得嶄新的" + tginfo_item.getName() + "。"));
                                        RecordTable.get().recordFailureArmor2(pc.getName(), tgItem.getName(), tginfo_item.getName(), pc.getIp());
                                        if (!pc.isGm() && info.getallmsg() != null) {
                                            World.get().broadcastPacketToAll(new S_SystemMessage(String.format(info.getallmsg(), pc.getName(), tgItem.getName(), tginfo_item.getName())));
                                        }
                                        return;
                                    }
                                    else {
                                        DollSolution._log.error("給予物件失敗 原因: 指定編號物品不存在(" + tginfo.get_itemid() + ")");
                                        return;
                                    }
                                }
                                else {
                                    switch (info.get_mode()) {
                                        case 0: {
                                            pc.sendPackets(new S_ServerMessage(160, tgItem.getLogName(), "$252", "$248"));
                                            break;
                                        }
                                        case 1: {
                                            if (tgItem.getproctect6()) {
                                                tgItem.setproctect6(false);
                                                pc.sendPackets(new S_ItemStatus(tgItem));
                                                pc.getInventory().updateItem(tgItem, 4);
                                                pc.getInventory().saveItem(tgItem, 4);
                                                pc.sendPackets(new S_ServerMessage("該裝備受到保護卷的祝福,沒發生什麼事情"));
                                                RecordTable.get().recordFailureArmor(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到保護卷的保護", pc.getIp());
                                                return;
                                            }
                                            else {
                                                L1ItemPowerUpdate ole1 = tmplist.get((Integer)(order_id - 1));
                                                pc.sendPackets(new S_ServerMessage(tgItem.getName() + "升級失敗。"));
                                                pc.getInventory().removeItem(tgItem, 1L);
                                                L1ItemInstance ole1item = ItemTable.get().createItem(ole1.get_itemid());
                                                ole1item.setIdentified(true);
                                                ole1item.setCount(1L);
                                                pc.getInventory().storeItem(ole1item);
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
                                                RecordTable.get().recordFailureArmor(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到保護卷的保護", pc.getIp());
                                                return;
                                            }
                                            else {
                                                pc.sendPackets(new S_ServerMessage(164, tgItem.getLogName(), "$252"));
                                                pc.getInventory().removeItem(tgItem, 1L);
                                                break;
                                            }
                                        }
                                        case 3: {
                                            if (DollSolution._random.nextBoolean()) {
                                                L1ItemPowerUpdate ole2 = tmplist.get((Integer)(order_id - 1));
                                                pc.sendPackets(new S_ServerMessage(tgItem.getName() + "升級失敗。"));
                                                pc.getInventory().removeItem(tgItem, 1L);
                                                L1ItemInstance ole2item = ItemTable.get().createItem(ole2.get_itemid());
                                                ole2item.setIdentified(true);
                                                ole2item.setCount(1L);
                                                pc.getInventory().storeItem(ole2item);
                                                break;
                                            }
                                            else {
                                                pc.sendPackets(new S_ServerMessage(164, tgItem.getLogName(), "$252"));
                                                pc.getInventory().removeItem(tgItem, 1L);
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
        }
        catch (Exception e) {
            DollSolution._log.error(e.getLocalizedMessage(), e);
        }
    }
}
