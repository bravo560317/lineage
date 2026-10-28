package com.lineage.server.clientpackets;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.lineage.server.model.shop.L1ShopBuyOrderList;
import com.lineage.server.model.shop.L1ShopSellOrderList;
import com.lineage.server.model.shop.L1Shop;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.datatables.lock.OtherUserBuyReading;
import com.lineage.server.templates.L1PrivateShopSellList;
import com.lineage.server.datatables.lock.OtherUserSellReading;
import com.lineage.server.templates.L1PrivateShopBuyList;
import com.lineage.server.datatables.lock.DwarfForChaReading;
import com.lineage.server.datatables.lock.DwarfReading;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.server.datatables.lock.DwarfForClanReading;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_Disconnect;
import java.sql.Timestamp;
import com.lineage.server.datatables.lock.DwarfForElfReading;
import com.lineage.server.timecontroller.event.GamblingTime;
import com.lineage.server.serverpackets.S_CnsSell;
import com.lineage.data.event.ShopXSet;
import com.lineage.data.npc.shop.Npc_ShopX;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.ShopXTable;
import java.util.Map;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.Shutdown;
import com.lineage.server.WriteLogTxt;

import java.util.HashMap;
import com.lineage.server.templates.L1Item;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1ItemUpdate;
import com.lineage.server.datatables.ItemUpdateTable;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.Instance.L1DwarfInstance;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1CnInstance;
import com.lineage.server.model.Instance.L1GamblingInstance;
import com.lineage.server.model.Instance.L1MerchantInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
public class C_Result extends ClientBasePacket
{
    public static final Log _log;
    public static final Random _random;
    
    static {
        _log = LogFactory.getLog(C_Result.class);
        _random = new Random();
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        if (!pc.isPrivateShop()) {
                            int npcObjectId = this.readD();
                            int resultType = this.readC();
                            int size = this.readC();
                            int unknown = this.readC();
                            int npcId = 0;
                            boolean isPrivateShop = false;
                            L1Object findObject = World.get().findObject(npcObjectId);
                            if (findObject != null) {
                                boolean isStop = false;
                                if (findObject instanceof L1NpcInstance) {
                                    L1NpcInstance targetNpc = (L1NpcInstance)findObject;
                                    npcId = targetNpc.getNpcTemplate().get_npcId();
                                    isStop = true;
                                }
                                else if (findObject instanceof L1PcInstance) {
                                    isPrivateShop = true;
                                    isStop = true;
                                }
                                if (isStop) {
                                    int diffLocX = Math.abs(pc.getX() - findObject.getX());
                                    Math.abs(pc.getY() - findObject.getY());
                                }
                            }
                            switch (resultType) {
                                case 0: {
                                    if (size > 0) {
                                        if (findObject instanceof L1MerchantInstance) {
                                            switch (npcId) {
                                                case 70535: {
                                                    this.mode_shopS(pc, size);
                                                    break;
                                                }
                                                case 210109: {
                                                    this.mode_shopS(pc, size);
                                                    break;
                                                }
                                                default: {
                                                    this.mode_buy(pc, npcId, size);
                                                    break;
                                                }
                                            }
                                            return;
                                        }
                                        else if (findObject instanceof L1GamblingInstance) {
                                            this.mode_gambling(pc, npcId, size, true);
                                            return;
                                        }
                                        else if (findObject instanceof L1CnInstance) {
                                            this.mode_cn_buy(pc, size);
                                            return;
                                        }
                                        else if (findObject instanceof L1DeInstance) {
                                            L1DeInstance de = (L1DeInstance)findObject;
                                            this.mode_buyde(pc, de, size);
                                            return;
                                        }
                                        else if (pc.equals(findObject)) {
                                            this.mode_cn_buy(pc, size);
                                            return;
                                        }
                                        else if (findObject instanceof L1PcInstance && isPrivateShop) {
                                            L1PcInstance targetPc = (L1PcInstance)findObject;
                                            this.mode_buypc(pc, targetPc, size);
                                            return;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 1: {
                                    if (size > 0) {
                                        if (findObject instanceof L1MerchantInstance) {
                                            switch (npcId) {
                                                case 70023: {
                                                    this.mode_sellall(pc, size);
                                                    break;
                                                }
                                                case 111414: {
                                                    this.mode_firecrystal(pc, size);
                                                    break;
                                                }
                                                default: {
                                                    this.mode_sell(pc, npcId, size);
                                                    break;
                                                }
                                            }
                                            return;
                                        }
                                        else if (findObject instanceof L1CnInstance) {
                                            this.mode_cn_sell(pc, size);
                                            return;
                                        }
                                        else if (findObject instanceof L1GamblingInstance) {
                                            this.mode_gambling(pc, npcId, size, false);
                                            return;
                                        }
                                        else if (findObject instanceof L1DeInstance) {
                                            if (findObject instanceof L1DeInstance) {
                                                L1DeInstance de = (L1DeInstance)findObject;
                                                this.mode_sellde(pc, de, size);
                                            }
                                            return;
                                        }
                                        else if (findObject instanceof L1PcInstance && isPrivateShop) {
                                            L1PcInstance targetPc = (L1PcInstance)findObject;
                                            this.mode_sellpc(pc, targetPc, size);
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 2: {
                                    if (size > 0 && findObject instanceof L1DwarfInstance) {
                                        int level = pc.getLevel();
                                        if (level >= 5) {
                                            this.mode_warehouse_in(pc, npcId, size);
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 3: {
                                    if (size > 0 && findObject instanceof L1DwarfInstance) {
                                        int level = pc.getLevel();
                                        if (level >= 5) {
                                            this.mode_warehouse_out(pc, npcId, size);
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 4: {
                                    if (size > 0 && findObject instanceof L1DwarfInstance) {
                                        int level = pc.getLevel();
                                        if (level >= 15) {
                                            this.mode_warehouse_clan_in(pc, npcId, size);
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 5: {
                                    if (size > 0) {
                                        if (findObject instanceof L1DwarfInstance) {
                                            int level = pc.getLevel();
                                            if (level >= 5) {
                                                this.mode_warehouse_clan_out(pc, npcId, size);
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                        if (clan != null) {
                                            clan.setWarehouseUsingChar(0);
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                }
                                case 8: {
                                    if (size > 0 && findObject instanceof L1DwarfInstance) {
                                        int level = pc.getLevel();
                                        if (level >= 5 && pc.isElf()) {
                                            this.mode_warehouse_elf_in(pc, npcId, size);
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 9: {
                                    if (size > 0 && findObject instanceof L1DwarfInstance) {
                                        int level = pc.getLevel();
                                        if (level >= 5 && pc.isElf()) {
                                            this.mode_warehouse_elf_out(pc, npcId, size);
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 10: {
                                    if (size > 0) {
                                        switch (npcId) {
                                            case 111410: {
                                                this.mode_update_item(pc, size, npcObjectId);
                                                break;
                                            }
                                            default: {
                                                break;
                                            }
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 12: {
                                    if (size > 0) {
                                        switch (npcId) {
                                            case 70535: {
                                                this.mode_shop_item(pc, size, npcObjectId);
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 17: {
                                    if (size > 0 && findObject instanceof L1DwarfInstance) {
                                        int level = pc.getLevel();
                                        if (level >= 155) {
                                            this.mode_warehouse_cha_in(pc, npcId, size);
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                case 18: {
                                    if (size > 0 && findObject instanceof L1DwarfInstance) {
                                        int level = pc.getLevel();
                                        if (level >= 155) {
                                            this.mode_warehouse_cha_out(pc, npcId, size);
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                            return;
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
    
    private void mode_update_item(L1PcInstance pc, int size, int npcObjectId) {
        try {
            if (size != 1) {
                pc.sendPackets(new S_ServerMessage("\\fR你只能選取一樣裝備用來升級。"));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                int orderId = this.readD();
                int count = Math.max(0, this.readD());
                if (count != 1) {
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    return;
                }
                else {
                    L1ItemInstance item = pc.getInventory().getItem(orderId);
                    ArrayList<L1ItemUpdate> items = ItemUpdateTable.get().get(item.getItemId());
                    String[] names = new String[items.size()];
                    for (int index = 0; index < items.size(); ++index) {
                        int toid = (items.get(index)).get_toid();
                        L1Item tgitem = ItemTable.get().getTemplate(toid);
                        if (tgitem != null) {
                            names[index] = tgitem.getName();
                        }
                    }
                    pc.set_mode_id(orderId);
                    pc.sendPackets(new S_NPCTalkReturn(npcObjectId, "y_up_i1", names));
                }
            }
        }
        catch (Exception e) {
            C_Result._log.error("升級裝備物品數據異常: " + pc.getName());
        }
    }
    
    private void mode_shopS(L1PcInstance pc, int size) {
        try {
            Map<Integer, Integer> sellScoreMapMap = new HashMap();
            for (int i = 0; i < size; ++i) {
                int orderId = this.readD();
                int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                }
                else if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                    return;
                }
                else {
                    sellScoreMapMap.put(new Integer(orderId), new Integer(count));
                }
            }
            pc.get_otherList().get_buyCnS(sellScoreMapMap);
        }
        catch (Exception e) {
            C_Result._log.error("購買人物託售物品數據異常: " + pc.getName());
        }
    }
    
    private void mode_shop_item(L1PcInstance pc, int size, int npcObjectId) {
        try {
            if (size == 1) {
                int objid = this.readD();
                L1Object object = pc.getInventory().getItem(objid);
                boolean isError = false;
                if (object instanceof L1ItemInstance) {
                    L1ItemInstance item = (L1ItemInstance)object;
                    if (!item.getItem().isTradable()) {
                        isError = true;
                    }
                    if (item.isEquipped()) {
                        isError = true;
                    }
                    if (!item.isIdentified()) {
                        isError = true;
                    }
                    if (item.getItem().getMaxUseTime() != 0) {
                        isError = true;
                    }
                    if (item.get_time() != null) {
                        isError = true;
                    }
                    if (ShopXTable.get().getTemplate(item.getItem().getItemId()) != null) {
                        isError = true;
                    }
                    Object[] petlist = pc.getPetList().values().toArray();
                    Object[] array;
                    for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                        Object petObject = array[i];
                        if (petObject instanceof L1PetInstance) {
                            L1PetInstance pet = (L1PetInstance)petObject;
                            if (item.getId() == pet.getItemObjId()) {
                                isError = true;
                            }
                        }
                    }
                    if (pc.getDoll(item.getId()) != null) {
                        isError = true;
                    }
                    if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getId()) {
                        isError = true;
                    }
                    if (item.getraceGamNo() != null) {
                        isError = true;
                    }
                    if (item.getEnchantLevel() < 0) {
                        isError = true;
                    }
                    if (item.getItem().getMaxChargeCount() != 0 && item.getChargeCount() <= 0) {
                        isError = true;
                    }
                    if (isError) {
                        pc.sendPackets(new S_NPCTalkReturn(npcObjectId, "y_x_e1"));
                    }
                    else {
                        L1ItemInstance itemT = pc.getInventory().checkItemX(Npc_ShopX._itemid, (long)ShopXSet.ADENA);
                        if (itemT == null) {
                            pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(Npc_ShopX._itemid).getName()));
                            pc.sendPackets(new S_CloseList(pc.getId()));
                            return;
                        }
                        else {
                            pc.get_other().set_item(item);
                            pc.sendPackets(new S_CnsSell(npcObjectId, "y_x_3", "ma"));
                        }
                    }
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npcObjectId, "y_x_e"));
            }
        }
        catch (Exception e) {
            C_Result._log.error("人物託售物品數據異常: " + pc.getName());
        }
    }
    
    private void mode_sellall(L1PcInstance pc, int size) {
        try {
            Map<Integer, Integer> sellallMap = new HashMap();
            int i = 0;
            while (i < size) {
                int objid = this.readD();
                int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                    return;
                }
                else if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                    return;
                }
                else {
                    sellallMap.put(new Integer(objid), new Integer(count));
                    ++i;
                }
            }
            pc.get_otherList().sellall(sellallMap);
        }
        catch (Exception e) {
            C_Result._log.error("玩家賣出物品給予萬物回收商數據異常: " + pc.getName());
        }
    }
    
    private void mode_cn_buy(L1PcInstance pc, int size) {
        try {
            Map<Integer, Integer> cnMap = new HashMap();
            int i = 0;
            while (i < size) {
                int orderId = this.readD();
                int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                    return;
                }
                else if (count > 9999) {
                    C_Result._log.error("要求列表物品取得傳回數量大於9999: " + pc.getName() + ":" + pc.getNetConnection().kick());
                    return;
                }
                else if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                    return;
                }
                else if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                    return;
                }
                else {
                    cnMap.put(new Integer(orderId), new Integer(count));
                    ++i;
                }
            }
            pc.get_otherList().get_buyCn(cnMap);
        }
        catch (Exception e) {
            C_Result._log.error("玩家買入商城物品數據異常: " + pc.getName());
        }
    }
    
    private void mode_cn_sell(L1PcInstance pc, int size) {
        try {
            Map<Integer, Integer> cnsellMap = new HashMap();
            int i = 0;
            while (i < size) {
                int objid = this.readD();
                int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                    return;
                }
                else if (count > 9999) {
                    C_Result._log.error("要求列表物品取得傳回數量大於9999: " + pc.getName() + ":" + pc.getNetConnection().kick());
                    return;
                }
                else if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                    return;
                }
                else {
                    cnsellMap.put(new Integer(objid), new Integer(count));
                    ++i;
                }
            }
            pc.get_otherList().sellcnitem(cnsellMap);
        }
        catch (Exception e) {
            C_Result._log.error("玩家賣出物品給予商城道具回收專員數據異常: " + pc.getName());
        }
    }
    
    private void mode_firecrystal(L1PcInstance pc, int size) {
        try {
            Map<Integer, Integer> FCMap = new HashMap();
            int i = 0;
            while (i < size) {
                int objid = this.readD();
                int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                    return;
                }
                else if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                    return;
                }
                else {
                    FCMap.put(new Integer(objid), new Integer(count));
                    ++i;
                }
            }
            pc.get_otherList().sellforfirecrystal(FCMap);
        }
        catch (Exception e) {
            C_Result._log.error("玩家賣出物品取得火神結晶數據異常: " + pc.getName());
        }
    }
    
    private void mode_gambling(L1PcInstance pc, int npcId, int size, boolean isShop) {
        if (isShop) {
            if (GamblingTime.isStart()) {
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                Map<Integer, Integer> gamMap = new HashMap();
                int i = 0;
                while (i < size) {
                    int orderId = this.readD();
                    int count = Math.max(0, this.readD());
                    if (count <= 0) {
                        C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                        return;
                    }
                    else if (Shutdown.isSHUTDOWN) {
                        pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                        return;
                    }
                    else {
                        gamMap.put(new Integer(orderId), new Integer(count));
                        ++i;
                    }
                }
                pc.get_otherList().get_buyGam(gamMap);
            }
        }
        else {
            for (int j = 0; j < size; ++j) {
                int objid = this.readD();
                int count2 = Math.max(0, this.readD());
                if (count2 <= 0) {
                    C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                }
                else {
                    pc.get_otherList().get_sellGam(objid, count2);
                }
            }
        }
    }
    
    private void mode_warehouse_elf_out(L1PcInstance pc, int npcId, int size) {
        int i = 0;
        while (i < size) {
            int objectId = this.readD();
            int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error("要求精靈倉庫取出傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                pc.getNetConnection().kick("精靈倉庫存入數量異常");
                return;
            }
            else {
                L1ItemInstance item = pc.getDwarfForElfInventory().getItem(objectId);
                if (item == null) {
                    C_Result._log.error("精靈倉庫取出數據異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                    return;
                }
                else if (!DwarfForElfReading.get().getUserItems(pc.getAccountName(), objectId, count)) {
                    C_Result._log.error("精靈倉庫取出數據異常(該倉庫指定數據有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                    return;
                }
                else if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                    return;
                }
                else if (pc.getInventory().checkDBResultItemCount(pc.getId(), item.getId(), count, 1)) {
                    result("IP(" + pc.getNetConnection().getIp() + ")" + "玩家:【 " + pc.getName() + " 】疑似利用角色專屬倉庫洗道具，請注意 " + "時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。", 10);
                    return;
                }
                else if (!this.isAvailableTrade(pc, objectId, item, count)) {
                    break;
                }
                else if (objectId != item.getId()) {
                    pc.sendPackets(new S_Disconnect());
                    break;
                }
                else if (!item.isStackable() && count != 1) {
                    pc.sendPackets(new S_Disconnect());
                    break;
                }
                else if (item != null && item.getCount() >= (long)count && count > 0) {
                    if (item.getCount() <= 0L) {
                        break;
                    }
                    else if (item.getCount() > 10000000L) {
                        break;
                    }
                    else if (count > 10000000) {
                        break;
                    }
                    else {
                        if ((long)count > item.getCount()) {
                            count = (int)item.getCount();
                        }
                        if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                            if (pc.getInventory().consumeItem(40494, 2L)) {
                                if (item.isStackable()) {
                                    if (this.getPcItemCountelf(item, pc) >= count && (long)this.getPcItemCountelf(item, pc) == item.getCount()) {
                                        pc.getDwarfForElfInventory().tradeItem(item, (long)count, pc.getInventory());
                                        RecordTable.get().recordeWarehouse_elf(pc.getName(), "領取", "妖倉", item.getAllName(), count, item.getId(), pc.getIp());
                                    }
                                    else {
                                        result("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "倉庫領取異常【" + item.getName() + "】時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。", 7);
                                    }
                                }
                                else {
                                    pc.getDwarfForElfInventory().tradeItem(item, (long)count, pc.getInventory());
                                    pc.getDwarfForElfInventory().tradeItem(item, (long)count, pc.getInventory());
                                    RecordTable.get().recordeWarehouse_elf(pc.getName(), "領取", "妖倉", item.getAllName(), count, item.getId(), pc.getIp());
                                }
                                ++i;
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage("純粹的米索利塊不足2個"));
                                break;
                            }
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(270));
                            break;
                        }
                    }
                }
                else {
                    break;
                }
            }
        }
    }
    
    private void mode_warehouse_elf_in(L1PcInstance pc, int npcId, int size) {
		int objectId, count;
		for (int i = 0; i < size; i++) {
			objectId = this.readD();
			count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error("要求精靈倉庫存入傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                pc.getNetConnection().kick("角色倉庫存入數量異常");
            }
            else {
                L1Object object = pc.getInventory().getItem(objectId);
                if (object == null) {
                    C_Result._log.error("人物背包資料取出數據異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                    return;
                }
                else if (!CharItemsReading.get().getUserItems(pc.getId(), objectId, (long)count)) {
                    C_Result._log.error("人物背包資料取出數據異常(該倉庫指定數據有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                    return;
                }
                else if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                    return;
                }
                else {
                    L1ItemInstance item = (L1ItemInstance)object;
                    if (item.getItemId() == 44070) {
                        pc.sendPackets(new S_ServerMessage("元寶無法存放倉庫"));
                        return;
                    }
                    else if (item.getItemId() == 40308) {
                        pc.sendPackets(new S_ServerMessage("金幣無法存放倉庫"));
                        return;
                    }
                    else if (!item.getItem().isTradable()) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        return;
                    }
                    else if (item.get_time() != null) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        return;
                    }
                    else if (!this.isAvailableTrade(pc, objectId, item, count)) {
                        return;
                    }
                    else if (item != null && item.getCount() >= (long)count && count > 0) {
                        if (item.getCount() <= 0L) {
                            break;
                        }
                        else if (item.getCount() > 100000000L) {
                            break;
                        }
                        else if (count > 100000000) {
                            break;
                        }
                        else if (pc.getDwarfInventory().checkAddItemToWarehouse(item, (long)count, 0) == 1) {
                            pc.sendPackets(new S_ServerMessage(75));
                            break;
                        }
                        else {
                            Object[] petlist = pc.getPetList().values().toArray();
                            Object[] array;
                            for (int length = (array = petlist).length, j = 0; j < length; ++j) {
                                Object petObject = array[j];
                                if (petObject instanceof L1PetInstance) {
                                    L1PetInstance pet = (L1PetInstance)petObject;
                                    if (item.getId() == pet.getItemObjId()) {
                                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                        break;
                                    }
                                }
                            }
                            if (pc.getDoll(item.getId()) != null) {
                                pc.sendPackets(new S_ServerMessage(1181));
                                return;
                            }
                            else if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getId()) {
                                pc.sendPackets(new S_ServerMessage(1181));
                                break;
                            }                        
                        else if (pc.getDwarfForElfInventory().checkAddItemToWarehouse(item, (long)count, 0) == 1) {
                            pc.sendPackets(new S_ServerMessage(75));
                            break;
                        }               
            		         RecordTable.get().recordeWarehouse_elf(pc.getName(), "存入", "妖倉", item.getAllName(), count, item.getId(), pc.getIp());
            		        pc.getInventory().tradeItem(objectId, count, (L1Inventory)pc.getDwarfForElfInventory());
            		                           }
            		                           }                  
            		                      else {
            		                                          pc.getInventory().deleteItem(item);
            		                                          result("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "妖精倉庫存取異常(並刪除)【" + item.getName() + "】時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。", 7);
            		                       }
            		                  }
            		              }
            		          }       
            		      }
    private void mode_warehouse_clan_out(L1PcInstance pc, int npcId, int size) {
        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        try {
            if (clan != null) {
                int i = 0;
                while (i < size) {
                    int objectId = this.readD();
                    int count = Math.max(0, this.readD());
                    if (count <= 0) {
                        C_Result._log.error("要求血盟倉庫取出傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                pc.getNetConnection().kick("倉庫取出數量異常");
                        return;
                    }
                    else {
                        L1ItemInstance item = clan.getDwarfForClanInventory().getItem(objectId);
                        if (item == null) {
                            C_Result._log.error("血盟倉庫取出數據異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                            return;
                        }
                        else if (!DwarfForClanReading.get().getUserItems(pc.getClanname(), objectId, count)) {
                            C_Result._log.error("血盟倉庫取出數據異常(該倉庫指定數據有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                            return;
                        }
                        else if (Shutdown.isSHUTDOWN) {
                            pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                            return;
                        }
                        else if (clan.getWarehouseUsingChar() != pc.getId()) {
                            pc.sendPackets(new S_ServerMessage("血盟倉庫有人正在使用中。"));
                            return;
                        }
                        else if (pc.getInventory().checkDBResultItemCount(pc.getId(), item.getId(), count, 2)) {
                            return;
                        }
                        else if (item != null && item.getCount() >= (long)count && count > 0) {
                            if (item.getCount() <= 0L) {
                                break;
                            }
                            else if (!this.isAvailableTrade(pc, objectId, item, count)) {
                                break;
                            }
                            else if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                                if (pc.getInventory().consumeItem(40308, 30L)) {
                                    RecordTable.get().recordeWarehouse_clan(pc.getName(), "領取", "盟倉", item.getAllName(), count, item.getId(), pc.getIp());
                                    clan.getDwarfForClanInventory().tradeItem(item, (long)count, pc.getInventory());
                                    clan.getDwarfForClanInventory().writeHistory(pc, item, count, 1);
                                    ++i;
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage(189));
                                    break;
                                }
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage(270));
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
                if (clan != null) {
                    clan.setWarehouseUsingChar(0);
                }
            }
        }
        catch (Exception e) {
            C_Result._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void mode_warehouse_clan_in(L1PcInstance pc, int npcId, int size) {
        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        try {
            if (clan != null) {
                int i = 0;
                while (i < size) {
                    int objectId = this.readD();
                    int count = Math.max(0, this.readD());
                    if (count <= 0) {
                        C_Result._log.error("要求血盟倉庫存入傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                        clan.setWarehouseUsingChar(0); // 釋放倉庫使用權
                        pc.getNetConnection().kick("血盟倉庫取出數量異常");
                        return;
                    }
                    else {
                        L1Object object = pc.getInventory().getItem(objectId);
                        if (object == null) {
                            C_Result._log.error("人物背包資料取出數據異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                            return;
                        }
                        else if (!CharItemsReading.get().getUserItems(pc.getId(), objectId, (long)count)) {
                            C_Result._log.error("人物背包資料取出數據異常(該倉庫指定數據有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                            return;
                        }
                        else if (Shutdown.isSHUTDOWN) {
                            pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                            return;
                        }
                        else {
                            L1ItemInstance item = (L1ItemInstance)object;
                            if (!pc.getInventory().checkDBItemCount(pc.getId(), item.getItemId(), (long)count)) {
                                return;
                            }
                            else if (item.getCount() <= 0L) {
                                break;
                            }
                            else if (item.getItemId() == 44070) {
                                pc.sendPackets(new S_ServerMessage("元寶無法存放倉庫"));
                                return;
                            }
                            else if (item.getItemId() == 40308) {
                                pc.sendPackets(new S_ServerMessage("金幣限制存放倉庫"));
                                return;
                            }
                            else if (!item.getItem().isTradable()) {
                                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                return;
                            }
                            else if (item.get_time() != null) {
                                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                return;
                            }
                            else if (ItemRestrictionsTable.RESTRICTIONS.contains((Integer)item.getItemId())) {
                                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                return;
                            }
                            else if (!this.isAvailableTrade(pc, objectId, item, count)) {
                                return;
                            }
                            else if (item != null && item.getCount() >= (long)count && count > 0) {
                                if (item.getCount() <= 0L) {
                                    break;
                                }
                                else if (item.getCount() > 100000000L) {
                                    break;
                                }
                                else if (count > 100000000) {
                                    break;
                                }
                                else {
                                    Object[] petlist = pc.getPetList().values().toArray();
                                    Object[] array;
                                    for (int length = (array = petlist).length, j = 0; j < length; ++j) {
                                        Object petObject = array[j];
                                        if (petObject instanceof L1PetInstance) {
                                            L1PetInstance pet = (L1PetInstance)petObject;
                                            if (item.getId() == pet.getItemObjId()) {
                                                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                                break;
                                            }
                                        }
                                    }
                                    if (pc.getDoll(item.getId()) != null) {
                                        pc.sendPackets(new S_ServerMessage(1181));
                                        return;
                                    }
                                    else if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getId()) {
                                        pc.sendPackets(new S_ServerMessage(1181));
                                        break;
                                    }
                                    else if (clan.getDwarfForClanInventory().checkAddItemToWarehouse(item, (long)count, 1) == 1) {
                                        pc.sendPackets(new S_ServerMessage(75));
                                        return;
                                    }
                                    else {
                                        RecordTable.get().recordeWarehouse_clan(pc.getName(), "存入", "盟倉", item.getAllName(), count, item.getId(), pc.getIp());
                                        pc.getInventory().tradeItem(objectId, (long)count, clan.getDwarfForClanInventory());
                                        clan.getDwarfForClanInventory().writeHistory(pc, item, count, 0);
                                        ++i;
                                    }
                                }
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(208));
            }
        }
        catch (Exception e) {
            C_Result._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void mode_warehouse_out(L1PcInstance pc, int npcId, int size) {
        int i = 0;
        while (i < size) {
            int objectId = this.readD();
            int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error("要求個人倉庫取出傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                pc.getNetConnection().kick("個人倉庫取出數量異常");
            }
            else {
                L1ItemInstance item = pc.getDwarfInventory().getItem(objectId);
                if (item == null) {
                    C_Result._log.error("個人倉庫取出數據異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
                    break;
                }
                else if (!DwarfReading.get().getUserItems(pc.getAccountName(), objectId, count)) {
                    C_Result._log.error("帳號倉庫取出數據異常(該倉庫指定數據有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
                    break;
                }
                else if (pc.getInventory().checkDBResultItemCount(pc.getId(), item.getId(), count, 1)) {
                    result("IP(" + pc.getNetConnection().getIp() + ")" + "玩家:【 " + pc.getName() + " 】疑似利用角色專屬倉庫洗道具，請注意 " + "時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。", 10);
                    return;
                }
                else if (item != null && item.getCount() >= (long)count && count > 0) {
                    if (item.getCount() <= 0L) {
                        break;
                    }
                    else if (!this.isAvailableTrade(pc, objectId, item, count)) {
                        break;
                    }
                    else if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                        if (pc.getInventory().consumeItem(40308, 30L)) {
                            RecordTable.get().recordeWarehouse_pc(pc.getName(), "領取", "個倉", item.getAllName(), count, item.getId(), pc.getIp());
                            if (item.isStackable()) {
                                if (this.getPcItemCount(item, pc) >= count && (long)this.getPcItemCount(item, pc) == item.getCount()) {
                                    pc.getDwarfInventory().tradeItem(item, (long)count, pc.getInventory());
                                }
                                else {
                                    result("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "倉庫領取異常【" + item.getName() + "】時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。", 7);
                                }
                            }
                            else {
                                pc.getDwarfInventory().tradeItem(item, (long)count, pc.getInventory());
                            }
                            ++i;
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(189));
                            break;
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(270));
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
    }   
    private void mode_warehouse_in(L1PcInstance pc, int npcId, int size) {
		int objectId, count;
		for (int i = 0; i < size; i++) {
			objectId = this.readD();
			count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error("要求個人倉庫存入傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                pc.getNetConnection().kick("個人倉庫存入數量異常");
            }
            else {
                L1Object object = pc.getInventory().getItem(objectId);
                if (object == null) {
                    C_Result._log.error("人物背包資料取出數據異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
                    break;
                }
                else if (!CharItemsReading.get().getUserItems(pc.getId(), objectId, (long)count)) {
                    C_Result._log.error("人物背包資料取出數據異常(該倉庫指定數據有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
                    break;
                }
                else {
                    L1ItemInstance item = (L1ItemInstance)object;
                    if (!pc.getInventory().checkDBItemCount(pc.getId(), item.getItemId(), (long)count)) {
                        return;
                    }
                    else if (item.getCount() <= 0L) {
                        break;
                    }
                    else if (!item.getItem().isTradable()) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        break;
                    }
                    else if (!this.isAvailableTrade(pc, objectId, item, count)) {
                        return;
                    }
                    else if (item != null && item.getCount() >= (long)count && count > 0) {
                        if (item.getCount() <= 0L) {
                            break;
                        }
                        else if (item.getCount() > 100000000L) {
                            break;
                        }
                        else if (count > 100000000) {
                            break;
                        }
                        else {
                            Object[] petlist = pc.getPetList().values().toArray();
                            Object[] array;
                            for (int length = (array = petlist).length, j = 0; j < length; ++j) {
                                Object petObject = array[j];
                                if (petObject instanceof L1PetInstance) {
                                    L1PetInstance pet = (L1PetInstance)petObject;
                                    if (item.getId() == pet.getItemObjId()) {
                                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                        break;
                                    }
                                }
                            }
                            if (pc.getDoll(item.getId()) != null) {
                                pc.sendPackets(new S_ServerMessage(1181));
                                break;
                            }
                			if (pc.getDwarfInventory().checkAddItemToWarehouse(item, count,
                					L1Inventory.WAREHOUSE_TYPE_PERSONAL) == L1Inventory.SIZE_OVER) {
                				pc.sendPackets(new S_ServerMessage(75)); // \f1これ以上ものを置く場所がありません。
                				break;               			
                            }
                		      RecordTable.get().recordeWarehouse_pc(pc.getName(), "存入", "個倉", item.getAllName(), count, item.getId(), pc.getIp());
                		      pc.getInventory().tradeItem(objectId, count, (L1Inventory)pc.getDwarfInventory());
                         }
                         }                  
                    else {
                        pc.getInventory().deleteItem(item);
                        result("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "倉庫存取異常(並刪除)【" + item.getName() + "】時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。", 7);
                    }
                }
            }
        }       
    }
    private void mode_warehouse_cha_out(L1PcInstance pc, int npcId, int size) {
        int i = 0;
        while (i < size) {
            int objectId = this.readD();
            int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error("要求角色專屬倉庫取出傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                pc.getNetConnection().kick("角色倉庫取出數量異常");
            }
            else {
                L1ItemInstance item = pc.getDwarfForChaInventory().getItem(objectId);
                if (item == null) {
                    C_Result._log.error("角色專屬倉庫取出數據異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                    return;
                }
                else if (!DwarfForChaReading.get().getUserItems(pc.getName(), objectId, count)) {
                    C_Result._log.error("角色專屬倉庫取出數據異常(該倉庫指定數據有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode() + pc.getNetConnection().kick());
                    return;
                }
                else if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                    return;
                }
                else if (pc.getInventory().checkDBResultItemCount(pc.getId(), item.getId(), count, 1)) {
                    return;
                }
                else if (item != null && item.getCount() >= (long)count && count > 0) {
                    if (item.getCount() <= 0L) {
                        break;
                    }
                    else if (!this.isAvailableTrade(pc, objectId, item, count)) {
                        break;
                    }
                    else if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                        if (pc.getInventory().consumeItem(40308, 30L)) {
                            pc.getDwarfForChaInventory().tradeItem(item, (long)count, pc.getInventory());
                            ++i;
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(189));
                            break;
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(270));
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
    }
    
    private void mode_warehouse_cha_in(L1PcInstance pc, int npcId, int size) {
        int i = 0;
        while (i < size) {
            int objectId = this.readD();
            int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error("要求角色專屬倉庫存入傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                        pc.getNetConnection().kick("血盟倉庫存入數量異常");
            }
            else {
                L1Object object = pc.getInventory().getItem(objectId);
                if (object == null) {
                    C_Result._log.error("人物背包資料取出數據異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
                    return;
                }
                else if (!CharItemsReading.get().getUserItems(pc.getId(), objectId, (long)count)) {
                    C_Result._log.error("人物背包資料取出數據異常(該人物背包指定數據有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
                    return;
                }
                else {
                    L1ItemInstance item = (L1ItemInstance)object;
                    if (item.getCount() <= 0L) {
                        return;
                    }
                    else if (!this.isAvailableTrade(pc, objectId, item, count)) {
                        return;
                    }
                    else if (item != null && item.getCount() >= (long)count && count > 0) {
                        if (item.getCount() <= 0L) {
                            break;
                        }
                        else if (item.getCount() > 100000000L) {
                            break;
                        }
                        else if (count > 100000000) {
                            break;
                        }
                        else if (Shutdown.isSHUTDOWN) {
                            pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                            return;
                        }
                        else if (item.getItemId() == 44070) {
                            pc.sendPackets(new S_ServerMessage("元寶無法存放倉庫"));
                            return;
                        }
                        else if (item.getItemId() == 40308) {
                            pc.sendPackets(new S_ServerMessage("金幣無法存放倉庫"));
                            return;
                        }
                        else if (!item.getItem().isTradable()) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            break;
                        }
                        else if (item.get_time() != null) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            break;
                        }
                        else {
                            Object[] petlist = pc.getPetList().values().toArray();
                            Object[] array;
                            for (int length = (array = petlist).length, j = 0; j < length; ++j) {
                                Object petObject = array[j];
                                if (petObject instanceof L1PetInstance) {
                                    L1PetInstance pet = (L1PetInstance)petObject;
                                    if (item.getId() == pet.getItemObjId()) {
                                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                        break;
                                    }
                                }
                            }
                            if (pc.getDoll(item.getId()) != null) {
                                pc.sendPackets(new S_ServerMessage(1181));
                                break;
                            }
                            else if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getId()) {
                                pc.sendPackets(new S_ServerMessage(1181));
                                break;
                            }
                            else if (pc.getDwarfForChaInventory().checkAddItemToWarehouse(item, (long)count, 0) == 1) {
                                pc.sendPackets(new S_ServerMessage(75));
                                break;
                            }
                            else {
                                pc.getInventory().tradeItem(objectId, (long)count, pc.getDwarfForChaInventory());
                                ++i;
                            }
                        }
                    }
                    else {
                        break;
                    }
                }
            }
        }
    }
    
    private void mode_sellpc(L1PcInstance pc, L1PcInstance targetPc, int size) {
        ArrayList<L1PrivateShopBuyList> buyList = targetPc.getBuyList();
        boolean[] isRemoveFromList = new boolean[8];
        if (targetPc.isTradingInPrivateShop()) {
            return;
        }
        else {
            int adenaItemId = targetPc.getMap().isUsableShop();
            if (adenaItemId <= 0 || ItemTable.get().getTemplate(adenaItemId) == null) {
                return;
            }
            else {
                targetPc.setTradingInPrivateShop(true);
                int i = 0;
                while (i < size) {
                    int itemObjectId = this.readD();
                    int count = this.readCH();
                    count = Math.max(0, count);
                    if (count <= 0) {
                        C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                        pc.getNetConnection().kick("個人商店交易數量異常");
                    }
                    else {
                        int order = this.readC();
                        L1ItemInstance item = pc.getInventory().getItem(itemObjectId);
                        if (item != null) {
                            if (item.get_time() == null) {
                                L1PrivateShopBuyList psbl = buyList.get(order);
                                int buyItemObjectId = psbl.getItemObjectId();
                                long buyPrice = (long)psbl.getBuyPrice();
                                int buyTotalCount = psbl.getBuyTotalCount();
                                int buyCount = psbl.getBuyCount();
                                if (count > buyTotalCount - buyCount) {
                                    count = buyTotalCount - buyCount;
                                }
                                if (item.isEquipped()) {
                                    pc.sendPackets(new S_ServerMessage(905));
                                }
                                else {
                                    L1ItemInstance srcItem = targetPc.getInventory().getItem(buyItemObjectId);
                                    if (srcItem.get_time() == null) {
                                        if (item.getItemId() == srcItem.getItemId() && item.getEnchantLevel() == srcItem.getEnchantLevel()) {
                                            if (targetPc.getInventory().checkAddItem(item, (long)count) == 0) {
                                                int j = 0;
                                                while (j < count) {
                                                    if (buyPrice * (long)j > 2000000000L) {
                                                        targetPc.sendPackets(new S_ServerMessage(904, "2000000000"));
                                                        return;
                                                    }
                                                    else {
                                                        ++j;
                                                    }
                                                }
                                                if (targetPc.getInventory().checkItem(adenaItemId, (long)count * buyPrice)) {
                                                    L1ItemInstance adena = targetPc.getInventory().findItemId(adenaItemId);
                                                    if (adena != null) {
                                                        if (pc.getInventory().checkDBItemCount(pc.getId(), item.getItemId(), (long)count)) {
                                                            if (item.getCount() < (long)count) {
                                                                pc.sendPackets(new S_ServerMessage(989));
                                                                C_Result._log.error("可能使用bug進行交易 人物名稱(賣出道具給予個人商店/交易數量不吻合): " + pc.getName() + " objid:" + pc.getId());
                                                            }
                                                            else {
                                                                OtherUserSellReading.get().add(item.getItem().getName(), item.getId(), (int)buyPrice, (long)count, pc.getId(), pc.getName(), targetPc.getId(), targetPc.getName());
                                                                targetPc.getInventory().tradeItem(adena, (long)count * buyPrice, pc.getInventory());
                                                                pc.getInventory().tradeItem(item, (long)count, targetPc.getInventory());
                                                                String message = item.getItem().getNameId() + " (" + String.valueOf(count) + ")";
                                                                pc.sendPackets(new S_ServerMessage(877, targetPc.getName(), message));
                                                                psbl.setBuyCount(count + buyCount);
                                                                buyList.set(order, psbl);
                                                                if (psbl.getBuyCount() == psbl.getBuyTotalCount()) {
                                                                    isRemoveFromList[order] = true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                else {
                                                    targetPc.sendPackets(new S_ServerMessage(189));
                                                    break;
                                                }
                                            }
                                            else {
                                                pc.sendPackets(new S_ServerMessage(271));
                                                break;
                                            }
                                        }
                                        else {
                                            C_Result._log.error("可能使用bug進行交易 人物名稱(賣出道具給予個人商店/交易條件不吻合): " + pc.getName() + " objid:" + pc.getId());
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                        ++i;
                    }
                }
                for (i = 7; i >= 0; --i) {
                    if (isRemoveFromList[i]) {
                        buyList.remove(i);
                    }
                }
                targetPc.setTradingInPrivateShop(false);
                return;
            }
        }
    }
    
    private void mode_buypc(L1PcInstance pc, L1PcInstance targetPc, int size) {
        ArrayList<L1PrivateShopSellList> sellList = targetPc.getSellList();
        boolean[] isRemoveFromList = new boolean[8];
        if (targetPc.isTradingInPrivateShop()) {
            return;
        }
        else {
            int adenaItemId = targetPc.getMap().isUsableShop();
            if (adenaItemId <= 0 || ItemTable.get().getTemplate(adenaItemId) == null) {
                return;
            }
            else {
                sellList = targetPc.getSellList();
                synchronized (sellList) {
                    if (pc.getPartnersPrivateShopItemCount() != sellList.size()) {
                        return;
                    }
                    else {
                        targetPc.setTradingInPrivateShop(true);
                        int i = 0;
                        while (i < size) {
                            int order = this.readD();
                            int count = Math.max(0, this.readD());
                            if (count <= 0) {
                                C_Result._log.error("要求買入個人商店物品傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                                pc.getNetConnection().kick("個人商店交易數量異常");
                            }
                            else if (Shutdown.isSHUTDOWN) {
                                pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                                return;
                            }
                            else {
                                L1PrivateShopSellList pssl = sellList.get(order);
                                int itemObjectId = pssl.getItemObjectId();
                                int sellPrice = pssl.getSellPrice();
                                int sellTotalCount = pssl.getSellTotalCount();
                                int sellCount = pssl.getSellCount();
                                L1ItemInstance item = targetPc.getInventory().getItem(itemObjectId);
                                if (item != null) {
                                    if (item.get_time() == null) {
                                        if (count > sellTotalCount - sellCount) {
                                            count = sellTotalCount - sellCount;
                                        }
                                        if (count > 0) {
                                            if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                                                int j = 0;
                                                while (j < count) {
                                                    if (sellPrice * j > 2000000000) {
                                                        pc.sendPackets(new S_ServerMessage(904, "2000000000"));
                                                        targetPc.setTradingInPrivateShop(false);
                                                        return;
                                                    }
                                                    else {
                                                        ++j;
                                                    }
                                                }
                                                int price = count * sellPrice;
                                                L1ItemInstance adena = pc.getInventory().findItemId(adenaItemId);
                                                if (adena == null) {
                                                    pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(adenaItemId).getName()));
                                                }
                                                else if (adena.getCount() < (long)price) {
                                                    pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(adenaItemId).getName()));
                                                }
                                                else if (targetPc != null && item.getCount() < (long)count) {
                                                    pc.sendPackets(new S_ServerMessage(989));
                                                }
                                                else if (targetPc != null) {
                                                    if (pc.getInventory().checkDBItemCount(pc.getId(), adena.getItemId(), (long)price)) {
                                                        if (adena.getCount() >= (long)price) {
                                                            if (item.getCount() < (long)count) {
                                                                pc.sendPackets(new S_ServerMessage(989));
                                                            }
                                                            else {
                                                                OtherUserBuyReading.get().add(item.getItem().getName(), item.getId(), sellPrice, (long)count, pc.getId(), pc.getName(), targetPc.getId(), targetPc.getName());
                                                                targetPc.getInventory().tradeItem(item, (long)count, pc.getInventory());
                                                                pc.getInventory().tradeItem(adena, (long)price, targetPc.getInventory());
                                                                String message = item.getItem().getNameId() + " (" + String.valueOf(count) + ")";
                                                                targetPc.sendPackets(new S_ServerMessage(877, pc.getName(), message));
                                                                pssl.setSellCount(count + sellCount);
                                                                sellList.set(order, pssl);
                                                                if (pssl.getSellCount() == pssl.getSellTotalCount()) {
                                                                    isRemoveFromList[order] = true;
                                                                }
                                                                RecordTable.get().recordeShop(pc.getName(), targetPc.getName(), item.getAllName(), count, item.getId(), price, adena.getName(), pc.getIp(), targetPc.getIp());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else {
                                                pc.sendPackets(new S_ServerMessage(270));
                                                break;
                                            }
                                        }
                                    }
                                }
                                ++i;
                            }
                        }
                        for (i = 7; i >= 0; --i) {
                            if (isRemoveFromList[i]) {
                                sellList.remove(i);
                            }
                        }
                        targetPc.setTradingInPrivateShop(false);
                    }
                }
                return;
            }
        }
    }
    
    private void mode_sell(L1PcInstance pc, int npcId, int size) {
        L1Shop shop = ShopTable.get().get(npcId);
        if (shop != null) {
            L1ShopSellOrderList orderList = shop.newSellOrderList(pc);
            for (int i = 0; i < size; ++i) {
                int objid = this.readD();
                int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                    pc.getNetConnection().kick("NPC商店交易數量異常");
                }
                else {
                    orderList.add(objid, count);
                }
            }
            shop.buyItems(pc, orderList);
        }
        else {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void mode_buy(L1PcInstance pc, int npcId, int size) {
        L1Shop shop = ShopTable.get().get(npcId);
        if (shop != null) {
            L1ShopBuyOrderList orderList = shop.newBuyOrderList();
            for (int i = 0; i < size; ++i) {
                int orderId = this.readD();
                int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
                    pc.getNetConnection().kick("NPC商店交易數量異常");
                }
                else {
                    orderList.add(orderId, count);
                }
            }
            shop.sellItems(pc, orderList);
        }
        else {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void mode_sellde(L1PcInstance pc, L1DeInstance de, int size) {
        Map<Integer, int[]> buyList = de.get_buyList();
        Queue<Integer> removeList = new ConcurrentLinkedQueue();
        for (int i = 0; i < size; ++i) {
            int itemObjectId = this.readD();
            int count = this.readCH();
            count = Math.max(0, count);
            if (count <= 0) {
                C_Result._log.error("要求列表物品取得傳回數量小於等於0: " + pc.getName() + ":" + pc.getNetConnection().kick());
            }
            else {
                L1ItemInstance item = pc.getInventory().getItem(itemObjectId);
                if (item != null) {
                    if (item.get_time() != null) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                    }
                    else if (item.isEquipped()) {
                        pc.sendPackets(new S_ServerMessage(905));
                    }
                    else {
                        int[] sellX = buyList.get((Integer)item.getItemId());
                        if (sellX != null) {
                            int price = sellX[0];
                            int enchantlvl = sellX[1];
                            int buycount = sellX[2];
                            if (item.getEnchantLevel() == enchantlvl && buycount >= count) {
                                if (pc.getInventory().removeItem(itemObjectId, (long)count) == (long)count) {
                                    pc.getInventory().storeItem(40308, (long)(price * count));
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage(989));
                                }
                                int newcount = buycount - count;
                                if (newcount <= 0) {
                                    removeList.add(item.getItemId());
                                }
                                else {
                                    int[] newSellX = { price, enchantlvl, newcount };
                                    de.updateBuyList(item.getItemId(), newSellX);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!removeList.isEmpty()) {
            Iterator<Integer> iter = (Iterator)removeList.iterator();
            while (iter.hasNext()) {
                Integer reitem = iter.next();
                iter.remove();
                buyList.remove(reitem);
            }
        }
        pc.get_otherList().DELIST.clear();
    }
    
    private void mode_buyde(L1PcInstance pc, L1DeInstance de, int size) {
        Map<L1ItemInstance, Integer> sellList = de.get_sellList();
        Queue<L1ItemInstance> removeList = new ConcurrentLinkedQueue();
        int i = 0;
        while (i < size) {
            int order = this.readD();
            int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error("要求買入個人商店物品傳回數量小於等於0: " + pc.getName() + pc.getNetConnection().kick());
                break;
            }
            else {
                Map<Integer, L1ItemInstance> list = pc.get_otherList().DELIST;
                Iterator<Integer> iterator = list.keySet().iterator();
                while (iterator.hasNext()) {
                    int key = (Integer)iterator.next();
                    if (order == key) {
                        L1ItemInstance item = list.get((Integer)key);
                        if (item == null) {
                            continue;
                        }
                        else if (item.getCount() < (long)count) {
                            continue;
                        }
                        else if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                            long price = (long)(count * sellList.get(item));
                            if (pc.getInventory().consumeItem(40308, price)) {
                                if (item.isStackable()) {
                                    pc.getInventory().storeItem(item.getItemId(), (long)count);
                                    long newCount = item.getCount() - (long)count;
                                    if (newCount > 0L) {
                                        item.setCount(newCount);
                                    }
                                    else {
                                        removeList.add(item);
                                    }
                                }
                                else {
                                    pc.getInventory().storeTradeItem(item);
                                    removeList.add(item);
                                }
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage(189));
                                break;
                            }
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(270));
                            break;
                        }
                    }
                }
                ++i;
            }
        }
        if (!removeList.isEmpty()) {
            Iterator<L1ItemInstance> iter = (Iterator)removeList.iterator();
            while (iter.hasNext()) {
                L1ItemInstance reitem = iter.next();
                iter.remove();
                sellList.remove(reitem);
            }
        }
        pc.get_otherList().DELIST.clear();
    }
    
    private boolean isAvailableTrade(L1PcInstance pc, int objectId, L1ItemInstance item, int count) {
        boolean result = true;
        if (item == null) {
            result = false;
        }
        if (objectId != item.getId()) {
            result = false;
        }
        if (!item.isStackable() && count != 1) {
            result = false;
        }
        if (item.getCount() <= 0L || item.getCount() > 2000000000L) {
            result = false;
        }
        if (count <= 0 || count > 2000000000) {
            result = false;
        }
        if (!result) {
            pc.sendPackets(new S_Disconnect());
        }
        return result;
    }
    
    private static void result(String info, int x) {
        String y = "";
        if (x == 1) {
            y = "玩家紀錄/個人倉庫存放紀錄.txt";
        }
        else if (x == 2) {
            y = "玩家紀錄/個人倉庫領取紀錄.txt";
        }
        else if (x == 3) {
            y = "玩家紀錄/妖精倉庫存放紀錄.txt";
        }
        else if (x == 4) {
            y = "玩家紀錄/妖精倉庫領取紀錄.txt";
        }
        else if (x == 5) {
            y = "玩家紀錄/血盟倉庫存放紀錄.txt";
        }
        else if (x == 6) {
            y = "玩家紀錄/血盟倉庫領取紀錄.txt";
        }
        else if (x == 7) {
            y = "玩家紀錄/倉庫領取異常記錄.txt";
        }
        else if (x == 8) {
            y = "玩家紀錄/個人商店掛賣紀錄.txt";
        }
        else if (x == 9) {
            y = "玩家紀錄/個人商店掛收紀錄.txt";
        }
        else if (x == 10) {
            y = "玩家紀錄/個人與NPC商店異常記錄.txt";
        }
        else if (x == 11) {
            y = "玩家紀錄/個人商店元寶掛賣紀錄.txt";
        }
        else if (x == 12) {
            y = "玩家紀錄/個人商店元寶掛收紀錄.txt";
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(y, true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getPcItemCount(L1ItemInstance item, L1PcInstance pc) {
        int n = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select count from character_warehouse where account_name=? && item_id =? order by id DESC limit 0,1;");
            pstm.setString(1, pc.getAccountName());
            pstm.setInt(2, item.getItemId());
            rs = pstm.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            C_Result._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return n;
    }
    
    public int getPcItemCountelf(L1ItemInstance item, L1PcInstance pc) {
        int n = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select count from character_elf_warehouse where account_name=? && item_id =? order by id DESC limit 0,1;");
            pstm.setString(1, pc.getAccountName());
            pstm.setInt(2, item.getItemId());
            rs = pstm.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            C_Result._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return n;
    }
    
    public int getPcItemCount2(L1ItemInstance item, L1PcInstance pc) {
        int n = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select count from character_items where char_id=? && item_id =? order by id DESC limit 0,1;");
            pstm.setInt(1, pc.getId());
            pstm.setInt(2, item.getItemId());
            rs = pstm.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            C_Result._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return n;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}