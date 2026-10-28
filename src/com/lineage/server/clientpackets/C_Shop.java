package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_DoActionShop;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.templates.L1PrivateShopBuyList;
import com.lineage.server.templates.L1PrivateShopSellList;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.Shutdown;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Shop extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Shop.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        int mapId = pc.getMapId();
if (mapId != 340 && mapId != 350 && mapId != 360 && mapId != 370 && mapId != 800
    && mapId != 0 && mapId != 4) {
    pc.sendPackets(new S_ServerMessage(876));
    return;
}

                        else {
                            ArrayList<L1PrivateShopSellList> sellList = pc.getSellList();
                            ArrayList<L1PrivateShopBuyList> buyList = pc.getBuyList();
                            boolean tradable = true;
                            int type = this.readC();
                            if (type == 0) {
                                int sellTotalCount = this.readH();
                                int i = 0;
                                while (i < sellTotalCount) {
                                    int sellObjectId = this.readD();
                                    int sellPrice = Math.max(0, this.readD());
                                    if (sellPrice <= 0) {
                                        C_Shop._log.error("要求開設個人商店傳回金幣小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                                        break;
                                    }
                                    else {
                                        int sellCount = Math.max(0, this.readD());
                                        if (sellCount <= 0) {
                                            C_Shop._log.error("要求開設個人商店傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                                            break;
                                        }
                                        else if (Shutdown.isSHUTDOWN) {
                                            pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                                            return;
                                        }
                                        else {
                                            L1ItemInstance checkItem = pc.getInventory().getItem(sellObjectId);
                                            if (!checkItem.getItem().isTradable()) {
                                                tradable = false;
                                                pc.sendPackets(new S_ServerMessage(1497));
                                            }
                                            if (checkItem.get_time() != null) {
                                                pc.sendPackets(new S_ServerMessage(1497));
                                                tradable = false;
                                            }
                                            if (checkItem.getBless() >= 128) {
                                                pc.sendPackets(new S_ServerMessage(1497));
                                                return;
                                            }
                                            else if (checkItem.isEquipped()) {
                                                pc.sendPackets(new S_ServerMessage(141));
                                                return;
                                            }
                                            else if (ItemRestrictionsTable.RESTRICTIONS.contains((Integer)checkItem.getItemId())) {
                                                pc.sendPackets(new S_ServerMessage(210, checkItem.getItem().getNameId()));
                                                return;
                                            }
                                            else if (pc.get_other().get_item() != null) {
                                                pc.sendPackets(new S_SystemMessage("\\fT物品正在進行託售中,請在次重新操作一次"));
                                                pc.sendPackets(new S_CloseList(pc.getId()));
                                                pc.get_other().set_item(null);
                                                return;
                                            }
                                            else {
                                                Object[] petlist = pc.getPetList().values().toArray();
                                                Object[] array;
                                                for (int length = (array = petlist).length, k = 0; k < length; ++k) {
                                                    Object petObject = array[k];
                                                    if (petObject instanceof L1PetInstance) {
                                                        L1PetInstance pet = (L1PetInstance)petObject;
                                                        if (checkItem.getId() == pet.getItemObjId()) {
                                                            tradable = false;
                                                            pc.sendPackets(new S_ServerMessage(1187));
                                                            return;
                                                        }
                                                    }
                                                }
                                                if (checkItem.getId() == pc.getQuest().get_step(74)) {
                                                    pc.sendPackets(new S_ServerMessage(166, checkItem.getName(), " 無法交易"));
                                                    return;
                                                }
                                                else if (pc.getDoll(checkItem.getId()) != null) {
                                                    pc.sendPackets(new S_ServerMessage(1181));
                                                    return;
                                                }
                                                else {
                                                    L1PrivateShopSellList pssl = new L1PrivateShopSellList();
                                                    pssl.setItemObjectId(sellObjectId);
                                                    pssl.setSellPrice(sellPrice);
                                                    pssl.setSellTotalCount(sellCount);
                                                    sellList.add(pssl);
                                                    ++i;
                                                }
                                            }
                                        }
                                    }
                                }
                                int buyTotalCount = this.readH();
                                int j = 0;
                                while (j < buyTotalCount) {
                                    int buyObjectId = this.readD();
                                    int buyPrice = Math.max(0, this.readD());
                                    if (buyPrice <= 0) {
                                        C_Shop._log.error("要求買入道具傳回金幣小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                                        break;
                                    }
                                    else {
                                        int buyCount = Math.max(0, this.readD());
                                        if (buyCount <= 0) {
                                            C_Shop._log.error("要求買入道具傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                                            break;
                                        }
                                        else {
                                            L1ItemInstance checkItem2 = pc.getInventory().getItem(buyObjectId);
                                            if (checkItem2.getCount() > 0L) {
                                                if (!checkItem2.getItem().isTradable()) {
                                                    tradable = false;
                                                    pc.sendPackets(new S_ServerMessage(1497));
                                                }
                                                if (checkItem2.get_time() != null) {
                                                    pc.sendPackets(new S_ServerMessage(1497));
                                                    tradable = false;
                                                }
                                                if (checkItem2.getBless() >= 128) {
                                                    pc.sendPackets(new S_ServerMessage(1497));
                                                    return;
                                                }
                                                else if (checkItem2.isEquipped()) {
                                                    pc.sendPackets(new S_ServerMessage(141));
                                                    return;
                                                }
                                                else {
                                                    Object[] petlist2 = pc.getPetList().values().toArray();
                                                    Object[] array2;
                                                    for (int length2 = (array2 = petlist2).length, l = 0; l < length2; ++l) {
                                                        Object petObject2 = array2[l];
                                                        if (petObject2 instanceof L1PetInstance) {
                                                            L1PetInstance pet2 = (L1PetInstance)petObject2;
                                                            if (checkItem2.getId() == pet2.getItemObjId()) {
                                                                tradable = false;
                                                                pc.sendPackets(new S_ServerMessage(1187));
                                                                return;
                                                            }
                                                        }
                                                    }
                                                    if (checkItem2.getId() == pc.getQuest().get_step(74)) {
                                                        pc.sendPackets(new S_ServerMessage(166, checkItem2.getName(), " 無法交易"));
                                                        return;
                                                    }
                                                    else if (pc.getDoll(checkItem2.getId()) != null) {
                                                        pc.sendPackets(new S_ServerMessage(1181));
                                                        return;
                                                    }
                                                    else {
                                                        L1PrivateShopBuyList psbl = new L1PrivateShopBuyList();
                                                        psbl.setItemObjectId(buyObjectId);
                                                        psbl.setBuyPrice(buyPrice);
                                                        psbl.setBuyTotalCount(buyCount);
                                                        buyList.add(psbl);
                                                    }
                                                }
                                            }
                                            ++j;
                                        }
                                    }
                                }
                                if (!tradable) {
                                    sellList.clear();
                                    buyList.clear();
                                    pc.setPrivateShop(false);
                                    pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 3));
                                    return;
                                }
                                else {
                                    byte[] chat = this.readByte();
                                    pc.setShopChat(chat);
                                    pc.setPrivateShop(true);
                                    int SelectedPolyNum = 0;
                                    try {
                                        SelectedPolyNum = Integer.parseInt(new String(chat, "utf8").split("tradezone")[1].substring(0, 1));
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    L1PolyMorph.doPolyPraivateShop(pc, SelectedPolyNum);
                                    pc.sendPacketsAll(new S_DoActionShop(pc.getId(), chat));
                                    return;
                                }
                            }
                            else if (type == 1) {
                                sellList.clear();
                                buyList.clear();
                                pc.setPrivateShop(false);
                                pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 3));
                                L1PolyMorph.undoPolyPrivateShop(pc);
                                return;
                            }
                            else {
                                return;
                            }
                        }
                    }
                }
            }
            return;
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
