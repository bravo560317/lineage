package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Trade;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.data.event.TimeTrade;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_TradeAddItem extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_TradeAddItem.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {}
            if (pc.isDead() || pc.isTeleport()) {
                return;
            }
            else {
                int itemObjid = this.readD();
                long itemcount = (long)this.readD();
                if (itemcount > 2147483647L) {
                    itemcount = 2147483647L;
                }
                itemcount = Math.max(0L, itemcount);
                L1ItemInstance item = pc.getInventory().getItem(itemObjid);
                if (item == null) {
                    return;
                }
                else if (item.getCount() <= 0L) {
                    return;
                }
                else if ((item.getItemId() == 40308 || item.getItemId() == 44070) && pc.getLevel() < 35) {
                    pc.sendPackets(new S_ServerMessage("預防作弊啟動，35級以下無法做此動作"));
                    return;
                }
                else if (item.getItemId() == 47011 && pc.getLevel() < 52) {
                    return;
                }
                else {
                    if (!pc.isGm()) {
                        if (!item.getItem().isTradable() && !item.getproctect10()) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            return;
                        }
                        else if (item.getBless() >= 128) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            return;
                        }
                        else if (TimeTrade.START && item.get_time() != null) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            return;
                        }
                        else if (ItemRestrictionsTable.RESTRICTIONS.contains((Integer)item.getItemId())) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            return;
                        }
                    }
                    if (item.isEquipped()) {
                        pc.sendPackets(new S_ServerMessage(141));
                        return;
                    }
                    else {
                        Object[] petlist = pc.getPetList().values().toArray();
                        Object[] array;
                        for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                            Object petObject = array[i];
                            if (petObject instanceof L1PetInstance) {
                                L1PetInstance pet = (L1PetInstance)petObject;
                                if (item.getId() == pet.getItemObjId()) {
                                    pc.sendPackets(new S_ServerMessage(1187));
                                    return;
                                }
                            }
                        }
                        if (item.getId() == pc.getQuest().get_step(74)) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
                            return;
                        }
                        else if (pc.getDoll(item.getId()) != null) {
                            pc.sendPackets(new S_ServerMessage(1181));
                            return;
                        }
                        else if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getItemId()) {
                            pc.sendPackets(new S_ServerMessage(1181));
                            return;
                        }
                        else {
                            L1PcInstance tradingPartner = (L1PcInstance)World.get().findObject(pc.getTradeID());
                            if (tradingPartner == null) {
                                return;
                            }
                            else if (pc.getTradeOk()) {
                                return;
                            }
                            else if (tradingPartner.getInventory().checkAddItem(item, itemcount) != 0) {
                                tradingPartner.sendPackets(new S_ServerMessage(270));
                                pc.sendPackets(new S_ServerMessage(271));
                                return;
                            }
                            else {
                                L1Trade trade = new L1Trade();
                                if (itemcount <= 0L) {
                                    C_TradeAddItem._log.error("要求增加交易物品傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                                    return;
                                }
                                else {
                                    trade.tradeAddItem(pc, itemObjid, (long)(int)itemcount);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
