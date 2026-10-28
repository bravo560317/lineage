package com.lineage.server.model;

import com.lineage.server.serverpackets.S_AddItem;
import com.lineage.server.datatables.lock.OtherUserTitleReading;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.lock.CharItemsReading;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_TradeStatus;
import com.lineage.server.serverpackets.S_TradeAddItem;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_DeleteInventoryItem;
import com.lineage.server.templates.L1TradeItem;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Trade
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Trade.class);
    }
    
    public void tradeAddItem(L1PcInstance pc, int itemObjid, long itemcount) {
        L1PcInstance trading_partner = null;
        try {
            trading_partner = (L1PcInstance)World.get().findObject(pc.getTradeID());
            L1ItemInstance item = pc.getInventory().getItem(itemObjid);
            if (item == null) {
                return;
            }
            else if (trading_partner == null) {
                return;
            }
            else if (item.isEquipped()) {
                return;
            }
            else {
                ArrayList<L1TradeItem> map = pc.get_trade_items();
                if (map.size() >= 16) {
                    return;
                }
                else {
                    long count = 0L;
                    for (L1TradeItem tg : map) {
                        if (tg.get_objid() == item.getId()) {
                            count += tg.get_count();
                        }
                    }
                    itemcount = Math.max(0L, itemcount);
                    long now_count = itemcount + count;
                    boolean checkItem = pc.getInventory().checkItem(item.getItemId(), now_count);
                    if (checkItem) {
                        L1TradeItem info = new L1TradeItem();
                        info.set_objid(item.getId());
                        info.set_item_id(item.getItemId());
                        info.set_item(item);
                        info.set_count(itemcount);
                        pc.add_trade_item(info);
                        long out_count = item.getCount() - now_count;
                        if (out_count <= 0L) {
                            pc.sendPackets(new S_DeleteInventoryItem(item.getId()));
                        }
                        else {
                            pc.sendPackets(new S_ItemStatus(item, out_count));
                        }
                        pc.sendPackets(new S_TradeAddItem(item, itemcount, 0));
                        trading_partner.sendPackets(new S_TradeAddItem(item, itemcount, 1));
                    }
                    else {
                        pc.sendPackets(new S_TradeStatus(1));
                        trading_partner.sendPackets(new S_TradeStatus(1));
                        pc.setTradeOk(false);
                        trading_partner.setTradeOk(false);
                        pc.setTradeID(0);
                        trading_partner.setTradeID(0);
                        return;
                    }
                }
            }
        }
        catch (Exception e) {
            if (pc != null) {
                pc.get_trade_clear();
            }
            if (trading_partner != null) {
                trading_partner.get_trade_clear();
            }
            L1Trade._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void tradeOK(L1PcInstance pc) {
        L1PcInstance trading_partner = null;
        try {
            trading_partner = (L1PcInstance)World.get().findObject(pc.getTradeID());
            if (trading_partner != null) {
                ArrayList<L1TradeItem> map_1 = pc.get_trade_items();
                ArrayList<L1TradeItem> map_2 = trading_partner.get_trade_items();
                HashMap<L1ItemInstance, Long> temp1 = new HashMap();
                HashMap<L1ItemInstance, Long> temp2 = new HashMap();
                if (!map_1.isEmpty()) {
                    for (L1TradeItem tg : map_1) {
                        if (!CharItemsReading.get().getUserItems(pc.getId(), tg.get_objid(), tg.get_count())) {
                            L1Trade._log.error("人物交易異常(指定數據數量有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
                            pc.getNetConnection().kick();
                            trading_partner.getNetConnection().kick();
                        }
                        else {
                            L1ItemInstance tg_item = pc.getInventory().getItem(tg.get_objid());
                            if (tg_item == null) {
                                L1Trade._log.error("人物交易異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
                                pc.getNetConnection().kick();
                                trading_partner.getNetConnection().kick();
                            }
                            else {
                                Long count = temp1.get(tg_item);
                                if (count == null) {
                                    temp1.put(tg_item, tg.get_count());
                                }
                                else {
                                    temp1.put(tg_item, (Long)(tg.get_count() + count));
                                }
                                RecordTable.get().recordeTrade(pc.getName(), trading_partner.getName(), tg_item.getAllName(), (int)tg.get_count(), tg_item.getId(), pc.getIp(), trading_partner.getIp());
                            }
                        }
                    }
                }
                if (!map_2.isEmpty()) {
                    for (L1TradeItem tg : map_2) {
                        if (!CharItemsReading.get().getUserItems(trading_partner.getId(), tg.get_objid(), tg.get_count())) {
                            L1Trade._log.error("人物交易異常(指定數據數量有誤): " + trading_partner.getName() + "/" + trading_partner.getNetConnection().hashCode());
                            pc.getNetConnection().kick();
                            trading_partner.getNetConnection().kick();
                        }
                        else {
                            L1ItemInstance tg_item = trading_partner.getInventory().getItem(tg.get_objid());
                            if (tg_item == null) {
                                L1Trade._log.error("人物交易異常(物品為空): " + trading_partner.getName() + "/" + trading_partner.getNetConnection().hashCode());
                                pc.getNetConnection().kick();
                                trading_partner.getNetConnection().kick();
                            }
                            else {
                                Long count = temp2.get(tg_item);
                                if (count == null) {
                                    temp2.put(tg_item, tg.get_count());
                                }
                                else {
                                    temp2.put(tg_item, (Long)(tg.get_count() + count));
                                }
                                RecordTable.get().recordeTrade(trading_partner.getName(), pc.getName(), tg_item.getAllName(), (int)tg.get_count(), tg_item.getId(), trading_partner.getIp(), pc.getIp());
                            }
                        }
                    }
                }
                if (!temp1.isEmpty()) {
                    for (L1ItemInstance item : temp1.keySet()) {
                        long count2 = temp1.get(item);
                        pc.getInventory().tradeItem(item, count2, trading_partner.getInventory());
                        OtherUserTitleReading.get().add(item.getLogName() + "(" + item.getItemId() + ")", item.getId(), 0, count2, trading_partner.getId(), trading_partner.getName(), pc.getId(), pc.getName(), pc.getIp(), pc.getAccountName());
                    }
                }
                if (!temp2.isEmpty()) {
                    for (L1ItemInstance item : temp2.keySet()) {
                        long count2 = temp2.get(item);
                        trading_partner.getInventory().tradeItem(item, count2, pc.getInventory());
                        OtherUserTitleReading.get().add(item.getLogName() + "(" + item.getItemId() + ")", item.getId(), 0, count2, pc.getId(), pc.getName(), trading_partner.getId(), trading_partner.getName(), trading_partner.getIp(), trading_partner.getAccountName());
                    }
                }
                temp1.clear();
                temp2.clear();
                pc.sendPackets(new S_TradeStatus(0));
                trading_partner.sendPackets(new S_TradeStatus(0));
                pc.setTradeOk(false);
                trading_partner.setTradeOk(false);
                pc.setTradeID(0);
                trading_partner.setTradeID(0);
            }
        }
        catch (Exception e) {
            L1Trade._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            if (pc != null) {
                pc.get_trade_clear();
            }
            if (trading_partner != null) {
                trading_partner.get_trade_clear();
            }
        }
    }
    
    public void tradeCancel(L1PcInstance pc) {
        L1PcInstance trading_partner = null;
        try {
            trading_partner = (L1PcInstance)World.get().findObject(pc.getTradeID());
            if (trading_partner != null) {
                ArrayList<L1TradeItem> map_1 = pc.get_trade_items();
                ArrayList<L1TradeItem> map_2 = trading_partner.get_trade_items();
                HashMap<Integer, Long> temp1 = new HashMap();
                HashMap<Integer, Long> temp2 = new HashMap();
                if (!map_1.isEmpty()) {
                    for (L1TradeItem tg : map_1) {
                        Long count = temp1.get((Integer)tg.get_objid());
                        if (count == null) {
                            temp1.put(tg.get_objid(), tg.get_count());
                        }
                        else {
                            temp1.put(tg.get_objid(), (Long)(tg.get_count() + count));
                        }
                    }
                }
                if (!map_2.isEmpty()) {
                    for (L1TradeItem tg : map_2) {
                        Long count = temp2.get((Integer)tg.get_objid());
                        if (count == null) {
                            temp2.put(tg.get_objid(), tg.get_count());
                        }
                        else {
                            temp2.put(tg.get_objid(), (Long)(tg.get_count() + count));
                        }
                    }
                }
                if (!temp1.isEmpty()) {
                    for (Integer key : temp1.keySet()) {
                        long count2 = temp1.get(key);
                        L1ItemInstance tg_item = pc.getInventory().getItem(key);
                        if (tg_item != null) {
                            if (count2 == tg_item.getCount()) {
                                pc.sendPackets(new S_AddItem(tg_item));
                            }
                            else {
                                pc.sendPackets(new S_ItemStatus(tg_item, tg_item.getCount()));
                            }
                        }
                    }
                }
                if (!temp2.isEmpty()) {
                    for (Integer key : temp2.keySet()) {
                        long count2 = temp2.get(key);
                        L1ItemInstance tg_item = trading_partner.getInventory().getItem(key);
                        if (count2 == tg_item.getCount()) {
                            trading_partner.sendPackets(new S_AddItem(tg_item));
                        }
                        else {
                            trading_partner.sendPackets(new S_ItemStatus(tg_item, tg_item.getCount()));
                        }
                    }
                }
                temp1.clear();
                temp2.clear();
                pc.sendPackets(new S_TradeStatus(1));
                trading_partner.sendPackets(new S_TradeStatus(1));
                pc.setTradeOk(false);
                trading_partner.setTradeOk(false);
                pc.setTradeID(0);
                trading_partner.setTradeID(0);
            }
        }
        catch (Exception e) {
            L1Trade._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            if (pc != null) {
                pc.get_trade_clear();
            }
            if (trading_partner != null) {
                trading_partner.get_trade_clear();
            }
        }
    }
}
