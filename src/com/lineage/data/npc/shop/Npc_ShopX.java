package com.lineage.data.npc.shop;

import com.lineage.server.datatables.lock.CharItemPowerReading;
import com.lineage.data.cmd.CreateNewItem;
import java.util.Iterator;
import java.util.List;
import com.lineage.server.serverpackets.S_CnSRetrieve;
import com.lineage.server.datatables.ItemRestrictionsTable;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import com.lineage.server.datatables.ShopXTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1ShopS;
import java.util.Map;
import com.lineage.server.datatables.lock.DwarfShopReading;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_CnSShopSellListarmor;
import com.lineage.server.serverpackets.S_CnSShopSellList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.event.ShopXSet;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_ShopX extends NpcExecutor
{
    private static final Log _log;
    private static final int _count = 200;
    public static int _itemid;
    
    static {
        _log = LogFactory.getLog(Npc_ShopX.class);
        Npc_ShopX._itemid = 0;
    }
    
    private Npc_ShopX() {
    }
    
    public static NpcExecutor get() {
        return new Npc_ShopX();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.get_other().set_item(null);
            String[] info = { String.valueOf(ShopXSet.ADENA), String.valueOf(ShopXSet.DATE), String.valueOf(ShopXSet.MIN), String.valueOf(ShopXSet.MAX) };
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_x_1", info));
        }
        catch (Exception e) {
            Npc_ShopX._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("s")) {
            cmd_s1(pc, npc);
        }
        else if (cmd.equalsIgnoreCase("shopweapon")) {
            pc.sendPackets(new S_CnSShopSellList(pc, npc));
        }
        else if (cmd.equalsIgnoreCase("shoparmor2")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 2));
        }
        else if (cmd.equalsIgnoreCase("shoparmor18")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 18));
        }
        else if (cmd.equalsIgnoreCase("shoparmor19")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 19));
        }
        else if (cmd.equalsIgnoreCase("shoparmor20")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 20));
        }
        else if (cmd.equalsIgnoreCase("shoparmor21")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 21));
        }
        else if (cmd.equalsIgnoreCase("shoparmor22")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 22));
        }
        else if (cmd.equalsIgnoreCase("shoparmor23")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 23));
        }
        else if (cmd.equalsIgnoreCase("shoparmor24")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 24));
        }
        else if (cmd.equalsIgnoreCase("shoparmor25")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 25));
        }
        else if (cmd.equalsIgnoreCase("shoparmor40")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 40));
        }
        else if (cmd.equalsIgnoreCase("shoparmor37")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 37));
        }
        else if (cmd.equalsIgnoreCase("shoparmorother")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 50));
        }
        // 全部商品 (武器、防具、道具)
        else if (cmd.equalsIgnoreCase("shopall")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 0));
        }
        else if (cmd.equalsIgnoreCase("l")) {
            cmd_1(pc, npc);
        }
        else if (cmd.equalsIgnoreCase("ma")) {
            cmd_ma(pc, npc, amount);
        }
        else if (cmd.equals("up")) {
            int page = pc.get_other().get_page() - 1;
            showPage(pc, npc.getId(), page);
        }
        else if (cmd.equals("dn")) {
            int page = pc.get_other().get_page() + 1;
            showPage(pc, npc.getId(), page);
        }
        else if (cmd.equals("over")) {
            cmd_over(pc, npc);
        }
        else if (cmd.equals("no")) {
            pc.setTempID(0);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
        else if (cmd.matches("[0-9]+")) {
            String pagecmd = String.valueOf(pc.get_other().get_page()) + cmd;
            update(pc, npc, Integer.parseInt(pagecmd));
        }
    }
    
    public static void cmd_1(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.get_otherList().SHOPXMAP.clear();
            Map<Integer, L1ShopS> temp = (Map)DwarfShopReading.get().getShopSMap(pc.getId());
            if (temp != null) {
                pc.get_otherList().SHOPXMAP.putAll(temp);
                temp.clear();
            }
            showPage(pc, npc.getId(), 0);
        }
        catch (Exception e) {
            Npc_ShopX._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void cmd_ma(L1PcInstance pc, L1NpcInstance npc, long amount) {
        try {
            // 如果上架費大於 0，才檢查並消耗
            boolean isError = false;
            if (ShopXSet.ADENA > 0) {
                // 直接檢查金幣是否足夠
                if (!pc.getInventory().checkItem(40308, (long)ShopXSet.ADENA)) { // 手續費強制檢查金幣
                    pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(40308).getName())); // 顯示金幣不足
                    isError = true;
                }
            }
            if (amount < (long)ShopXSet.MIN) {
                isError = true;
            }
            if (amount > (long)ShopXSet.MAX) {
                isError = true;
            }
            if (isError) {
                pc.sendPackets(new S_CloseList(pc.getId()));
                pc.get_other().set_item(null);
                return;
            }
            else {
                L1ItemInstance item = pc.get_other().get_item();
                if (item == null) {
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    return;
                }
                else {
                    if (item.isEquipped()) {
                        isError = true;
                    }
                    if (!item.isIdentified()) {
                        isError = true;
                    }
                    if (item.getItem().getMaxUseTime() != 0) {
                        isError = true;
                    }
                    if (ShopXTable.get().getTemplate(item.getItem().getItemId()) != null) {
                        isError = true;
                    }
                    if (item.getEnchantLevel() < 0) {
                        isError = true;
                    }
                    if (item.getItem().getMaxChargeCount() != 0 && item.getChargeCount() <= 0) {
                        isError = true;
                    }
                    if (isError) {
                        pc.sendPackets(new S_CloseList(pc.getId()));
                        pc.get_other().set_item(null);
                        return;
                    }
                    else {
                        pc.get_other().set_item(null);
                        long time = (long)(ShopXSet.DATE * 24 * 60 * 60 * 1000);
                        Timestamp overTime = new Timestamp(System.currentTimeMillis() + time);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String key = sdf.format(overTime);
                        String[] info = { item.getLogName(), String.valueOf(amount), key };
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_x_4", info));
                        L1ShopS shopS = new L1ShopS();
                        shopS.set_id(DwarfShopReading.get().nextId());
                        shopS.set_item_obj_id(item.getId());
                        shopS.set_user_obj_id(pc.getId());
                        shopS.set_adena((int)amount);
                        shopS.set_overtime(overTime);
                        shopS.set_end(0);
                        String outname = item.getNumberedName_to_String();
                        shopS.set_none(outname);
                        shopS.set_item(item);
                        // 如果上架費大於 0，才消耗
                        if (ShopXSet.ADENA > 0) {
                            pc.getInventory().consumeItem(40308, (long)ShopXSet.ADENA); // 手續費強制消耗金幣
                        }
                        pc.getInventory().removeItem(item);
                        DwarfShopReading.get().insertItem(item.getId(), item, shopS);
                        try {
                            pc.save();
                            pc.saveInventory();
                        }
                        catch (Exception e) {
                            Npc_ShopX._log.error(e.getLocalizedMessage(), e);
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            Npc_ShopX._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    public static void cmd_over(L1PcInstance pc, L1NpcInstance npc) {
        try {
            L1ShopS shopS = DwarfShopReading.get().getShopS(pc.getTempID());
            pc.setTempID(0);
            shopS.set_end(3);
            shopS.set_item(null);
            DwarfShopReading.get().updateShopS(shopS);
            showPage(pc, npc.getId(), pc.get_other().get_page()); // 重新顯示當前頁面
        }
        catch (Exception e) {
            Npc_ShopX._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void cmd_s1(L1PcInstance pc, L1NpcInstance npc) {
        try {
            Map<Integer, L1ShopS> allShopS = (Map)DwarfShopReading.get().allShopS();

            // 檢查個人最大刊登數量
            final int maxPerPlayer = 10;
            Map<Integer, L1ShopS> playerShopMap = DwarfShopReading.get().getShopSMap(pc.getId());
            int consignCount = 0;
            if (playerShopMap != null) {
                for (L1ShopS shopS : playerShopMap.values()) {
                    // 只計算尚未領回的物品 (狀態 0, 1, 3)
                    if (shopS.get_end() == 0 || shopS.get_end() == 1 || shopS.get_end() == 3) {
                        consignCount++;
                    }
                }
            }
            if (consignCount >= maxPerPlayer) {
                pc.sendPackets(new S_ServerMessage(166, "每人最多只能刊登 " + maxPerPlayer + " 件物品。"));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }

            if (allShopS.size() >= 200) {
                pc.sendPackets(new S_ServerMessage(75));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                L1ItemInstance itemT = pc.getInventory().checkItemX(40308, (long)ShopXSet.ADENA); // 手續費強制檢查金幣
                if (itemT == null) {
                    pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(40308).getName()));
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    return;
                }
                else {
                    List<L1ItemInstance> itemsx = new CopyOnWriteArrayList();
                    List<L1ItemInstance> items = pc.getInventory().getItems();
                    for (L1ItemInstance item : items) {
                        if (ShopXTable.get().getTemplate(item.getItem().getItemId()) != null) {
                            continue;
                        }
                        else if (item.isEquipped()) {
                            continue;
                        }
                        else if (!item.isIdentified()) {
                            continue;
                        }
                        else if (item.getItem().getMaxUseTime() != 0) {
                            continue;
                        }
                        else if (item.get_time() != null) {
                            continue;
                        }
                        else if (item.getEnchantLevel() < 0) {
                            continue;
                        }
                        else if (item.getItem().getMaxChargeCount() != 0 && item.getChargeCount() <= 0) {
                            continue;
                        }
                        // else if (ItemRestrictionsTable.RESTRICTIONS.contains((Integer)item.getItemId())) {
                        //     continue;
                        // }
                        else {
                            itemsx.add(item);
                        }
                    }
                    pc.sendPackets(new S_CnSRetrieve(pc, npc.getId(), itemsx));
                    allShopS.clear();
                }
            }
        }
        catch (Exception e) {
            Npc_ShopX._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void cmd_s2(L1PcInstance pc, L1NpcInstance npc) {
        try {
            Map<Integer, L1ShopS> allShopS = (Map)DwarfShopReading.get().allShopS();

            // 檢查個人最大刊登數量
            final int maxPerPlayer = 10;
            Map<Integer, L1ShopS> playerShopMap = DwarfShopReading.get().getShopSMap(pc.getId());
            int consignCount = 0;
            if (playerShopMap != null) {
                for (L1ShopS shopS : playerShopMap.values()) {
                    // 只計算尚未領回的物品 (狀態 0, 1, 3)
                    if (shopS.get_end() == 0 || shopS.get_end() == 1 || shopS.get_end() == 3) {
                        consignCount++;
                    }
                }
            }
            if (consignCount >= maxPerPlayer) {
                pc.sendPackets(new S_ServerMessage(166, "每人最多只能刊登 " + maxPerPlayer + " 件物品。"));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }

            if (allShopS.size() >= 200) {
                pc.sendPackets(new S_ServerMessage(75));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            if (ShopXSet.ADENA > 0) {
                L1ItemInstance itemT = pc.getInventory().checkItemX(40308, (long)ShopXSet.ADENA); // 手續費強制檢查金幣
                if (itemT == null) {
                    pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(40308).getName()));
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    return;
                }
                else {
                    List<L1ItemInstance> itemsx = new CopyOnWriteArrayList();
                    List<L1ItemInstance> items = pc.getInventory().getItems();
                    for (L1ItemInstance item : items) {
                        if (ShopXTable.get().getTemplate(item.getItem().getItemId()) != null) {
                            continue;
                        }
                        else if (!item.getItem().isTradable()) {
                            continue;
                        }
                        else if (item.isEquipped()) {
                            continue;
                        }
                        else if (!item.isIdentified()) {
                            continue;
                        }
                        else if (item.getItem().getMaxUseTime() != 0) {
                            continue;
                        }
                        else if (item.get_time() != null) {
                            continue;
                        }
                        else if (item.getEnchantLevel() < 0) {
                            continue;
                        }
                        else if (item.getItem().getMaxChargeCount() != 0 && item.getChargeCount() <= 0) {
                            continue;
                        }
                        // else if (ItemRestrictionsTable.RESTRICTIONS.contains((Integer)item.getItemId())) {
                        //     continue;
                        // }
                        else {
                            itemsx.add(item);
                        }
                    }
                    pc.sendPackets(new S_CnSRetrieve(pc, npc.getId(), itemsx));
                    allShopS.clear();
                }
            }
        } catch (Exception e) {
            Npc_ShopX._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void update(L1PcInstance pc, L1NpcInstance npc, int key) {
        Map<Integer, L1ShopS> list = pc.get_otherList().SHOPXMAP;
        pc.setTempID(0);
        L1ShopS shopS = list.get((Integer)key);
        switch (shopS.get_end()) {
            case 0: {
                pc.setTempID(shopS.get_item_obj_id());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String overTime = sdf.format(shopS.get_overtime());
                String[] info = { shopS.get_item().getLogName() + "(" + shopS.get_item().getCount() + ")", String.valueOf(shopS.get_adena()), overTime };
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_x_5", info));
                break;
            }
            case 1: {
                shopS.set_end(2);
                DwarfShopReading.get().updateShopS(shopS);
                // 計算 10% 手續費，最低 1 元
                long price = shopS.get_adena();
                long commission = (long) (price * 0.1);
                if (commission < 1) { // 最低 1 元手續費
                    commission = 1;
                }
                long finalAmount = price - commission;
                CreateNewItem.createNewItem(pc, Npc_ShopX._itemid, finalAmount);
                showPage(pc, npc.getId(), pc.get_other().get_page()); // 重新顯示當前頁面
                break;
            }
            case 2: {
                pc.sendPackets(new S_ServerMessage(166, "該託售物品收入已領回"));
                showPage(pc, npc.getId(), pc.get_other().get_page()); // 重新顯示當前頁面
                break;
            }
            case 3: {
                shopS.set_end(4);
                shopS.set_item(null);
                DwarfShopReading.get().updateShopS(shopS);
                L1ItemInstance item = DwarfShopReading.get().allItems().get((Integer)shopS.get_item_obj_id());
                DwarfShopReading.get().deleteItem(shopS.get_item_obj_id());
                pc.getInventory().storeTradeItem(item);
                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                showPage(pc, npc.getId(), pc.get_other().get_page()); // 重新顯示當前頁面
                break;
            }
            case 4: {
                pc.sendPackets(new S_ServerMessage(166, "該託售物品已領回"));
                showPage(pc, npc.getId(), pc.get_other().get_page()); // 重新顯示當前頁面
                break;
            }
        }
        pc.get_other().set_item(null);
    }
    
    public static void showPage(L1PcInstance pc, int npcobjid, int page) {
        Map<Integer, L1ShopS> list = pc.get_otherList().SHOPXMAP;
        if (list == null) {
            return;
        }
        else {
            int allpage = list.size() / 10;
            if (page > allpage || page < 0) {
                page = 0;
            }
            if (list.size() % 10 != 0) {
                ++allpage;
            }
            pc.get_other().set_page(page);
            int or = page * 10;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.valueOf(page + 1) + "/" + allpage + ",");
            for (int key = or; key < or + 10; ++key) {
                L1ShopS shopS = list.get((Integer)key);
                if (shopS != null) {
                    stringBuilder.append(shopS.get_none() + " / " + shopS.get_adena() + ",");
                    switch (shopS.get_end()) {
                        case 0: {
                            stringBuilder.append("出售中,");
                            break;
                        }
                        case 1: {
                            stringBuilder.append("已售出未領回,");
                            break;
                        }
                        case 2: {
                            stringBuilder.append("已售出已領回,");
                            break;
                        }
                        case 3: {
                            stringBuilder.append("未售出未領回,");
                            break;
                        }
                        case 4: {
                            stringBuilder.append("未售出已領回,");
                            break;
                        }
                    }
                }
                else {
                    stringBuilder.append(" ,");
                }
            }
            if (allpage >= page + 1) {
                String out = stringBuilder.toString();
                String[] clientStrAry = out.split(",");
                pc.sendPackets(new S_NPCTalkReturn(npcobjid, "y_x_2", clientStrAry));
            }
            else {
                pc.sendPackets(new S_ServerMessage(166, "沒有可以顯示的項目"));
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            // 讀取 NPC 的專屬貨幣設定 (用於購買/領款)
            Npc_ShopX._itemid = Integer.parseInt(set[1]);
        }
        catch (Exception e) {
            Npc_ShopX._log.error("NPC專屬貨幣設置錯誤:檢查CLASSNAME為Npc_Strange的NPC設置!");
        }
    }
}