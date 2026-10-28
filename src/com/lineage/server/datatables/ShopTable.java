package com.lineage.server.datatables;

import com.lineage.server.model.L1NpcTalkData;
import java.util.List;
import com.lineage.server.templates.L1ShopItem;
import java.util.ArrayList;
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
import com.lineage.server.model.shop.L1Shop;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ShopTable
{
    private static final Log _log;
    private static ShopTable _instance;
    private static final Map<Integer, L1Shop> _allShops;
    private static final Map<Integer, Integer> _allShopItem;
    private static final Map<Integer, Integer> _noBuyList;
    public static final Map<Integer, Integer> _DailyItem;
    
    static {
        _log = LogFactory.getLog(ShopTable.class);
        _allShops = new HashMap();
        _allShopItem = new HashMap();
        _noBuyList = new HashMap();
        _DailyItem = new HashMap();
    }
    
    public static ShopTable get() {
        if (ShopTable._instance == null) {
            ShopTable._instance = new ShopTable();
        }
        return ShopTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM shop WHERE npc_id=? ORDER BY order_id");
            Iterator<Integer> iterator = enumNpcIds().iterator();
            while (iterator.hasNext()) {
                int npcId = (Integer)iterator.next();
                ps.setInt(1, npcId);
                rs = ps.executeQuery();
                L1Shop shop = loadShop(npcId, rs);
                ShopTable._allShops.put(npcId, shop);
                rs.close();
            }
        }
        catch (SQLException e) {
            ShopTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs, ps, cn);
        }
        ShopTable._log.info("載入商店販賣資料數量: " + ShopTable._allShops.size() + "(" + timer.get() + "ms)");
    }
    
    private static ArrayList<Integer> enumNpcIds() {
        ArrayList<Integer> ids = new ArrayList();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT DISTINCT `npc_id` FROM `shop`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("npc_id"));
            }
        }
        catch (SQLException e) {
            ShopTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs, pstm, con);
        }
        return ids;
    }
    
    private static L1Shop loadShop(int npcId, ResultSet rs) throws SQLException {
        List<L1ShopItem> sellingList = new ArrayList();
        List<L1ShopItem> purchasingList = new ArrayList();
        int currencyItemId = 40308;
        L1NpcTalkData action = NPCTalkDataTable.get().getTemplate(npcId);
        if (action != null && action.getCurrencyItemId() != 0) {
            currencyItemId = action.getCurrencyItemId();
        }
        while (rs.next()) {
            int itemId = rs.getInt("item_id");
            if (ItemTable.get().getTemplate(itemId) == null) {
                ShopTable._log.error("商店販賣資料錯誤: 沒有這個編號的道具:" + itemId + " 對應NPC編號:" + npcId);
                delete(npcId, itemId);
            }
            else {
                int sellingPrice = rs.getInt("selling_price");
                int purchasingPrice = rs.getInt("purchasing_price");
                int packCount = rs.getInt("pack_count");
                String note = rs.getString("note");
                int enchantlevel = rs.getInt("enchant_level");
                int dailybuyingCount = rs.getInt("每日限購數量");
                int dailybuyinglevel = rs.getInt("每日限購等級");
                if (dailybuyingCount > 0) {
                    ShopTable._DailyItem.put(itemId, dailybuyingCount);
                }
                Connection conI = null;
                PreparedStatement pstmI = null;
                ResultSet rsI = null;
                try {
                    conI = DatabaseFactory.get().getConnection();
                    pstmI = conI.prepareStatement("SELECT * FROM shop WHERE item_id='" + itemId + "'");
                    rsI = pstmI.executeQuery();
                    while (rsI.next()) {
                        if (rsI.getInt("selling_price") >= 1 && purchasingPrice > rsI.getInt("selling_price")) {
                            System.out.println("偵測到買低賣高錯誤!!! NpcId=" + rsI.getInt("npc_id") + ", ItemID=" + itemId + ", 價格錯誤!!!");
                            purchasingPrice = sellingPrice >> 1;
                        }
                    }
                    rsI.close();
                }
                catch (SQLException e) {
                    ShopTable._log.error(e.getLocalizedMessage(), e);
                }
                finally {
                    SQLUtil.close(rsI, pstmI, conI);
                }
                if (!note.contains("=>")) {
                    updata_name(npcId, itemId);
                }
                addSellList(itemId, sellingPrice, purchasingPrice, packCount);
                packCount = ((packCount == 0) ? 1 : packCount);
                if (sellingPrice >= 0) {
                    L1ShopItem item = new L1ShopItem(itemId, sellingPrice, packCount, enchantlevel, dailybuyingCount, dailybuyinglevel);
                    sellingList.add(item);
                }
                if (purchasingPrice >= 0) {
                    L1ShopItem item = new L1ShopItem(itemId, purchasingPrice, packCount, enchantlevel, dailybuyingCount, dailybuyinglevel);
                    purchasingList.add(item);
                }
                else {
                    continue;
                }
            }
        }
        return new L1Shop(npcId, currencyItemId, sellingList, purchasingList);
    }
    
    private static void updata_name(int npcId, int itemId) {
        Connection cn = null;
        PreparedStatement ps = null;
        String npcname = NpcTable.get().getNpcName(npcId);
        String itemname = ItemTable.get().getTemplate(itemId).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `shop` SET `note`=? WHERE `npc_id`=? AND `item_id`=?");
            int i = 0;
            ps.setString(++i, npcname + "=>" + itemname);
            ps.setInt(++i, npcId);
            ps.setInt(++i, itemId);
            ps.execute();
        }
        catch (SQLException e) {
            ShopTable._log.error(e.getLocalizedMessage(), e);
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
            ps = cn.prepareStatement("DELETE FROM `shop` WHERE `npc_id`=? AND `item_id`=?");
            ps.setInt(1, npc_id);
            ps.setInt(2, item_id);
            ps.execute();
        }
        catch (SQLException e) {
            ShopTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addSellList(int itemId, int sellingPrice, int purchasingPrice, int packCount) {
        if (ShopTable._noBuyList.get((Integer)itemId) != null) {
            return;
        }
        else {
            Integer price = ShopTable._allShopItem.get(new Integer(itemId));
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
                ShopTable._noBuyList.put(new Integer(itemId), new Integer((int)value3));
                if (price != null) {
                    ShopTable._allShopItem.remove(new Integer(itemId));
                }
                return;
            }
            else {
                if (price != null) {
                    if (value3 < (double)(int)price) {
                        ShopTable._allShopItem.put(new Integer(itemId), new Integer((int)value3));
                    }
                }
                else {
                    ShopTable._allShopItem.put(new Integer(itemId), new Integer((int)value3));
                }
                return;
            }
        }
    }
    
    public int getPrice(int itemid) {
        int tgprice = 0;
        Integer price = ShopTable._allShopItem.get(new Integer(itemid));
        if (price != null) {
            tgprice = price;
        }
        if (ShopTable._noBuyList.get((Integer)itemid) != null) {
            tgprice = 0;
        }
        return tgprice;
    }
    
    public L1Shop get(int npcId) {
        return ShopTable._allShops.get((Integer)npcId);
    }
}
