package com.lineage.server.model.shop;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.datatables.lock.TownReading;
import com.lineage.server.model.L1TownLocation;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Castle;
import com.lineage.server.model.L1TaxCalculator;
import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.templates.L1Item;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.utils.RangeInt;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;
import com.lineage.server.model.L1PcInventory;
import com.lineage.config.ConfigRate;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Iterator;
import com.lineage.server.templates.L1ShopItem;
import java.util.List;

public class L1Shop
{
    private final int _npcId;
    private final int _currencyItemId;
    private final List<L1ShopItem> _sellingItems;
    private final List<L1ShopItem> _purchasingItems;
    
    public L1Shop(int npcId, int currencyItemId, List<L1ShopItem> sellingItems, List<L1ShopItem> purchasingItems) {
        if (sellingItems == null || purchasingItems == null) {
            throw new NullPointerException();
        }
        else {
            this._npcId = npcId;
            this._currencyItemId = currencyItemId;
            this._sellingItems = sellingItems;
            this._purchasingItems = purchasingItems;
            return;
        }
    }
    
    public int getNpcId() {
        return this._npcId;
    }
    
    public List<L1ShopItem> getSellingItems() {
        return this._sellingItems;
    }
    
    public List<L1ShopItem> getPurchasingItems() {
        return this._purchasingItems;
    }
    
    public boolean isSelling(int itemid) {
        for (L1ShopItem shopitem : this._sellingItems) {
            if (shopitem.getItemId() == itemid) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isPurchasing(int itemid) {
        for (L1ShopItem shopitem : this._purchasingItems) {
            if (shopitem.getItemId() == itemid) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isPurchaseableItem(L1ItemInstance item) {
        return item != null && !item.isEquipped() && item.getEnchantLevel() == 0 && item.getBless() < 128;
    }
    
    private L1ShopItem getPurchasingItem(int itemId) {
        for (L1ShopItem shopItem : this._purchasingItems) {
            if (shopItem.getItemId() == itemId) {
                return shopItem;
            }
        }
        return null;
    }
    
    public L1AssessedItem assessItem(L1ItemInstance item) {
        L1ShopItem shopItem = this.getPurchasingItem(item.getItemId());
        if (shopItem == null) {
            return null;
        }
        else {
            return new L1AssessedItem(item.getId(), this.getAssessedPrice(shopItem), item.getName2());
        }
    }
    
    private int getAssessedPrice(L1ShopItem item) {
        return (int)((double)item.getPrice() * ConfigRate.RATE_SHOP_PURCHASING_PRICE / (double)item.getPackCount());
    }
    
    public List<L1AssessedItem> assessItems(L1PcInventory inv) {
        List<L1AssessedItem> result = new ArrayList();
        for (L1ShopItem item : this._purchasingItems) {
            L1ItemInstance[] itemsId;
            for (int length = (itemsId = inv.findItemsId(item.getItemId())).length, i = 0; i < length; ++i) {
                L1ItemInstance targetItem = itemsId[i];
                if (this.isPurchaseableItem(targetItem)) {
                    result.add(new L1AssessedItem(targetItem.getId(), this.getAssessedPrice(item), targetItem.getName()));
                }
            }
        }
        return result;
    }
    
    private boolean ensureSell(L1PcInstance pc, L1ShopBuyOrderList orderList) {
        int price = orderList.getTotalPrice();
        if (!RangeInt.includes(price, 0, 2000000000)) {
            pc.sendPackets(new S_ServerMessage(904, "2000000000"));
            return false;
        }
        else {
            for (L1ShopBuyOrder order : orderList.getList()) {
                int itemid = order.getItem().getItemId();
                if (itemid == 56148 && (pc.getInventory().findItemId(56147) != null || pc.getInventory().findItemId(56148) != null)) {
                    pc.sendPackets(new S_ServerMessage("身上已有妲蒂斯的魔力。"));
                    return false;
                }
                else if (!pc.getInventory().checkItem(this._currencyItemId, (long)price)) {
                    L1Item item = ItemTable.get().getTemplate(this._currencyItemId);
                    pc.sendPackets(new S_ServerMessage(337, item.getName()));
                    return false;
                }
                else {
                    int currentWeight = pc.getInventory().getWeight() * 1000;
                    if ((double)(currentWeight + orderList.getTotalWeight()) > pc.getMaxWeight() * 1000.0) {
                        pc.sendPackets(new S_ServerMessage(82));
                        return false;
                    }
                    else {
                        int DailyBuyinglevel = order.getItem().getDailyBuyinglevel();
                        if (DailyBuyinglevel != 0 && pc.getLevel() < DailyBuyinglevel) {
                            pc.sendPackets(new S_ServerMessage("該商品限制購買等級:" + DailyBuyinglevel));
                            return false;
                        }
                        else {
                            int DailyBuyingCount = order.getItem().getDailyBuyingCount();
                            if (DailyBuyingCount > 0) {
                                int AlreadyBoughtCount = pc.getQuest().get_step(itemid);
                                int buyingcount = order.getCount();
                                if (AlreadyBoughtCount < DailyBuyingCount && buyingcount + AlreadyBoughtCount <= DailyBuyingCount) {
                                    pc.getQuest().set_step(itemid, buyingcount + AlreadyBoughtCount);
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage("超過每日限制購買數量上限:" + order.getItem().getDailyBuyingCount()));
                                    return false;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            int totalCount = pc.getInventory().getSize();
            for (L1ShopBuyOrder order2 : orderList.getList()) {
                L1Item temp = order2.getItem().getItem();
                if (temp.isStackable()) {
                    if (!pc.getInventory().checkItem(temp.getItemId())) {
                        ++totalCount;
                    }
                    else {
                        continue;
                    }
                }
                else {
                    ++totalCount;
                }
            }
            if (totalCount > 180) {
                pc.sendPackets(new S_ServerMessage(263));
                return false;
            }
            else {
                return true;
            }
        }
    }
    
    private void payCastleTax(L1ShopBuyOrderList orderList) {
        L1TaxCalculator calc = orderList.getTaxCalculator();
        int price = orderList.getTotalPrice();
        int castleId = L1CastleLocation.getCastleIdByNpcid(this._npcId);
        int castleTax = calc.calcCastleTaxPrice(price);
        int nationalTax = calc.calcNationalTaxPrice(price);
        if (castleId == 7 || castleId == 8) {
            castleTax += nationalTax;
            nationalTax = 0;
        }
        if (castleId != 0 && castleTax > 0) {
            L1Castle castle = CastleReading.get().getCastleTable(castleId);
            synchronized (castle) {
                long money = castle.getPublicMoney();
                money += (long)castleTax;
                castle.setPublicMoney(money);
                CastleReading.get().updateCastle(castle);
            }
            if (nationalTax > 0) {
                L1Castle aden = CastleReading.get().getCastleTable(7);
                synchronized (aden) {
                    long money2 = aden.getPublicMoney();
                    money2 += (long)nationalTax;
                    aden.setPublicMoney(money2);
                    CastleReading.get().updateCastle(aden);
                }
            }
        }
    }
    
    private void payDiadTax(L1ShopBuyOrderList orderList) {
        L1TaxCalculator calc = orderList.getTaxCalculator();
        int price = orderList.getTotalPrice();
        int diadTax = calc.calcDiadTaxPrice(price);
        if (diadTax <= 0) {
            return;
        }
        else {
            L1Castle castle = CastleReading.get().getCastleTable(8);
            synchronized (castle) {
                long money = castle.getPublicMoney();
                money += (long)diadTax;
                castle.setPublicMoney(money);
                CastleReading.get().updateCastle(castle);
            }
            return;
        }
    }
    
    private void payTownTax(L1ShopBuyOrderList orderList) {
        int price = orderList.getTotalPrice();
        if (!World.get().isProcessingContributionTotal()) {
            int town_id = L1TownLocation.getTownIdByNpcid(this._npcId);
            if (town_id >= 1 && town_id <= 10) {
                TownReading.get().addSalesMoney(town_id, price);
            }
        }
    }
    
    private void payTax(L1ShopBuyOrderList orderList) {
        this.payCastleTax(orderList);
        this.payTownTax(orderList);
        this.payDiadTax(orderList);
    }
    
    private void sellItems(L1PcInventory inv, L1ShopBuyOrderList orderList, L1PcInstance pc) {
        if (orderList == null) {
            return;
        }
        else {
            int price = orderList.getTotalPrice();
            if (price <= 0) {
                return;
            }
            else if (!inv.consumeItem(this._currencyItemId, (long)price)) {
                return;
            }
            else {
                for (L1ShopBuyOrder order : orderList.getList()) {
                    int itemId = order.getItem().getItemId();
                    long amount = (long)order.getCount();
                    int EnchantLevel = order.getItem().getEnchantLevel();
                    L1ItemInstance item = ItemTable.get().createItem(itemId);
                    if (amount <= 0L) {
                        continue;
                    }
                    else {
                        item.setCount(amount);
                        item.setEnchantLevel(EnchantLevel);
                        item.setIdentified(true);
                        inv.storeItem(item);
                        pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                    }
                }
                return;
            }
        }
    }
    
    public void sellItems(L1PcInstance pc, L1ShopBuyOrderList orderList) {
        if (orderList.isEmpty()) {
            return;
        }
        else if (!this.ensureSell(pc, orderList)) {
            return;
        }
        else if (pc.getInventory().getSize() + orderList.getList().size() >= 180) {
            pc.sendPackets(new S_ServerMessage(263));
            return;
        }
        else if (this.getNpcId() == 93214) {
            if (!this.ensureCashSell2(pc, orderList, this.getNpcId())) {
                return;
            }
            else {
                this.sellCashItems2(pc, pc.getInventory(), orderList, this.getNpcId());
                return;
            }
        }
        else {
            this.sellItems(pc.getInventory(), orderList, pc);
            return;
        }
    }
    
    private boolean ensureCashSell2(L1PcInstance pc, L1ShopBuyOrderList orderList, int npcId) {
        int price = orderList.getTotalPrice();
        L1Item item = ItemTable.get().getTemplate(95294);
        long srcCount = pc.getInventory().countItems(95294);
        long nc = (long)price - srcCount;
        if (!pc.getInventory().checkItem(95294, (long)price)) {
            pc.sendPackets(new S_ServerMessage(337, item.getNameId() + "(" + nc + ")"));
            return false;
        }
        else {
            int currentWeight = pc.getInventory().getWeight() * 1000;
            if ((double)(currentWeight + orderList.getTotalWeight()) > pc.getMaxWeight() * 1000.0) {
                pc.sendPackets(new S_ServerMessage(82));
                return false;
            }
            else {
                int totalCount = pc.getInventory().getSize();
                for (L1ShopBuyOrder order : orderList.getList()) {
                    L1Item temp = order.getItem().getItem();
                    if (temp.isStackable()) {
                        if (!pc.getInventory().checkItem(temp.getItemId())) {
                            ++totalCount;
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        ++totalCount;
                    }
                }
                if (totalCount > 180) {
                    pc.sendPackets(new S_ServerMessage(263));
                    return false;
                }
                else if (price <= 0 || price > 100000000) {
                    pc.sendPackets(new S_Disconnect());
                    return false;
                }
                else {
                    return true;
                }
            }
        }
    }
    
    private void sellCashItems2(L1PcInstance pc, L1PcInventory inv, L1ShopBuyOrderList orderList, int npcId) {
        if (!inv.consumeItem(95294, (long)orderList.getTotalPrice())) {
            throw new IllegalStateException("屍魂幣不足.");
        }
        else {
            L1ItemInstance item = null;
            for (L1ShopBuyOrder order : orderList.getList()) {
                int itemId = order.getItem().getItemId();
                int amount = order.getCount();
                int EnchantLevel = order.getItem().getEnchantLevel();
                item = ItemTable.get().createItem(itemId);
                if (amount <= 0) {
                    continue;
                }
                else {
                    item.setCount((long)amount);
                    item.setEnchantLevel(EnchantLevel);
                    item.setIdentified(true);
                    inv.storeItem(item);
                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                }
            }
            return;
        }
    }
    
    public void buyItems(L1PcInstance pc, L1ShopSellOrderList orderList) {
        L1PcInventory inv = orderList.getPc().getInventory();
        int totalPrice = 0;
        for (L1ShopSellOrder order : orderList.getList()) {
            long count = inv.removeItem(order.getItem().getTargetId(), (long)order.getCount());
            if (count > 0L) {
                totalPrice = (int)((long)totalPrice + (long)order.getItem().getAssessedPrice() * count);
                sellnpcitem("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【" + pc.getName() + "】將" + order.getItem().getTargetName() + "(" + order.getCount() + ")個,賣給Npc獲得金幣(" + (long)order.getItem().getAssessedPrice() * count + ")時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
            }
            else {
                sellnpcitem("IP(" + pc.getNetConnection().getIp() + ")" + "玩家非法販售,數量小於0" + ":【" + pc.getName() + "】將" + order.getItem().getTargetId() + "(" + order.getCount() + ")個,賣給Npc獲得金幣(" + (long)order.getItem().getAssessedPrice() * count + ")時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
            }
        }
        totalPrice = RangeInt.ensure(totalPrice, 0, 2000000000);
        if (totalPrice > 0) {
            L1ItemInstance item = ItemTable.get().createItem(this._currencyItemId);
            item.setCount((long)totalPrice);
            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
            inv.storeItem(item);
        }
    }
    
    public L1ShopBuyOrderList newBuyOrderList() {
        return new L1ShopBuyOrderList(this);
    }
    
    public L1ShopSellOrderList newSellOrderList(L1PcInstance pc) {
        return new L1ShopSellOrderList(this, pc);
    }
    
    private static void sellnpcitem(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("玩家紀錄/萬物回收商紀錄.txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
