package com.lineage.server.datatables;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ShopItem;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ShopCnTable
{
    private static final Log _log;
    private static ShopCnTable _instance;
    private static final Map<Integer, ArrayList<L1ShopItem>> _shopList;
    private static final ArrayList<Integer> _CnitemidList;
    private static final Map<Integer, Integer> _allCnShopItem;
    public static final Map<Integer, Integer> _DailyCnItem;
    
    static {
        _log = LogFactory.getLog(ShopCnTable.class);
        _shopList = new HashMap();
        _CnitemidList = new ArrayList();
        _allCnShopItem = new HashMap();
        _DailyCnItem = new HashMap();
    }
    
    public static ShopCnTable get() {
        if (ShopCnTable._instance == null) {
            ShopCnTable._instance = new ShopCnTable();
        }
        return ShopCnTable._instance;
    }
    
    public void restshopCn() {
        ShopCnTable._shopList.clear();
        this.load();
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `shop_cn` ORDER BY `order_id`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int npcId = rs.getInt("npc_id");
                int itemId = rs.getInt("item_id");
                String note = rs.getString("note");
                int enchantlevel = rs.getInt("enchant_level");
                if (ItemTable.get().getTemplate(itemId) == null) {
                    ShopCnTable._log.error("商城販賣物品資料錯誤: 沒有這個編號的道具:" + itemId + " 對應NPC編號:" + npcId);
                    delete(npcId, itemId);
                }
                else {
                    int dailybuyingCount = rs.getInt("每日限購數量");
                    int dailybuyinglevel = rs.getInt("每日限購等級");
                    if (dailybuyingCount > 0) {
                        ShopCnTable._DailyCnItem.put(itemId, dailybuyingCount);
                    }
                    int sellingPrice = rs.getInt("selling_price");
                    int purchasingPrice = rs.getInt("purchasing_price");
                    int packCount = rs.getInt("pack_count");
                    if (sellingPrice > 0) {
                        L1ShopItem item = new L1ShopItem(id, itemId, sellingPrice, packCount, enchantlevel, dailybuyingCount, dailybuyinglevel);
                        this.addShopItem(npcId, item);
                    }
                    addSellList(itemId, sellingPrice, purchasingPrice, packCount);
                    ShopCnTable._CnitemidList.add(itemId);
                    if (!note.contains("=>")) {
                        updata_name(npcId, itemId);
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (SQLException e) {
            ShopCnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs, ps, cn);
        }
        ShopCnTable._log.info("載入商城販賣物品資料數量: " + ShopCnTable._shopList.size() + "(" + timer.get() + "ms)");
    }
    
    private static void addSellList(int itemId, int sellingPrice, int purchasingPrice, int packCount) {
        Integer currentprice = ShopCnTable._allCnShopItem.get(new Integer(itemId));
        double value3 = 0.0;
        if (purchasingPrice > 0) {
            if (packCount > 0) {
                value3 = (double)(sellingPrice / packCount) / 2.0;
            }
            else {
                value3 = (double)purchasingPrice;
            }
        }
        else if (sellingPrice > 0) {
            if (packCount > 0) {
                value3 = (double)(sellingPrice / packCount) / 2.0;
            }
            else {
                value3 = (double)sellingPrice / 2.0;
            }
        }
        if (value3 < 1.0) {
            if (currentprice != null) {
                ShopCnTable._allCnShopItem.remove(new Integer(itemId));
            }
            return;
        }
        else {
            if (currentprice != null) {
                if (value3 < (double)(int)currentprice) {
                    ShopCnTable._allCnShopItem.put(new Integer(itemId), new Integer((int)value3));
                }
            }
            else {
                ShopCnTable._allCnShopItem.put(new Integer(itemId), new Integer((int)value3));
            }
            return;
        }
    }
    
    public int getPrice(int itemid) {
        int tgprice = 0;
        Integer price = ShopCnTable._allCnShopItem.get(new Integer(itemid));
        if (price != null) {
            tgprice = price;
        }
        return tgprice;
    }
    
    public ArrayList<Integer> get_cnitemidlist() {
        return ShopCnTable._CnitemidList;
    }
    
    private static void updata_name(int npcId, int itemId) {
        Connection cn = null;
        PreparedStatement ps = null;
        String npcname = NpcTable.get().getNpcName(npcId);
        String itemname = ItemTable.get().getTemplate(itemId).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `shop_cn` SET `note`=? WHERE `npc_id`=? AND `item_id`=?");
            int i = 0;
            ps.setString(++i, npcname + "=>" + itemname);
            ps.setInt(++i, npcId);
            ps.setInt(++i, itemId);
            ps.execute();
        }
        catch (SQLException e) {
            ShopCnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void delete(int npc_id, int item_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `shop_cn` WHERE `npc_id`=? AND `item_id`=?");
            ps.setInt(1, npc_id);
            ps.setInt(2, item_id);
            ps.execute();
        }
        catch (SQLException e) {
            ShopCnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void addShopItem(int npcId, L1ShopItem item) {
        ArrayList<L1ShopItem> list = ShopCnTable._shopList.get(new Integer(npcId));
        if (list == null) {
            list = new ArrayList();
            list.add(item);
            ShopCnTable._shopList.put(npcId, list);
        }
        else {
            list.add(item);
        }
    }
    
    public ArrayList<L1ShopItem> get(int npcId) {
        ArrayList<L1ShopItem> list = ShopCnTable._shopList.get(new Integer(npcId));
        if (list != null) {
            return list;
        }
        else {
            return null;
        }
    }
    
    public L1ShopItem getTemp(int npcId, int id) {
        ArrayList<L1ShopItem> list = ShopCnTable._shopList.get(new Integer(npcId));
        if (list != null) {
            for (L1ShopItem shopItem : list) {
                if (shopItem.getId() == id) {
                    return shopItem;
                }
            }
        }
        return null;
    }
    
    public boolean isSelling(int npcId, int itemid) {
        ArrayList<L1ShopItem> list = ShopCnTable._shopList.get(new Integer(npcId));
        if (list != null) {
            for (L1ShopItem shopItem : list) {
                if (shopItem.getItemId() == itemid) {
                    return true;
                }
            }
        }
        return false;
    }
}
