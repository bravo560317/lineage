package com.lineage.server.datatables;

import java.util.Iterator;
import java.util.Map;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.HashMap;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactoryLogin;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.templates.DeShopItem;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class DeShopItemTable
{
    private static final Log _log;
    private static final ArrayList<DeShopItem> _itemList;
    private static int _buyCount;
    private static DeShopItemTable _instance;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(DeShopItemTable.class);
        _itemList = new ArrayList();
        DeShopItemTable._buyCount = 0;
        _random = new Random();
    }
    
    public static DeShopItemTable get() {
        if (DeShopItemTable._instance == null) {
            DeShopItemTable._instance = new DeShopItemTable();
        }
        return DeShopItemTable._instance;
    }
    
    public void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `de_shopds`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int item_id = rs.getInt("item_id");
                int price = rs.getInt("price");
                if (price <= 0) {
                    price = 1;
                }
                int sellcount = rs.getInt("sellcount");
                int buycount = rs.getInt("buycount");
                int enchantlvl = rs.getInt("enchantlvl");
                if (this.check_item(item_id)) {
                    DeShopItem de = new DeShopItem();
                    de.set_id(id);
                    de.set_item_id(item_id);
                    de.set_price(price);
                    de.set_sellcount(sellcount);
                    if (buycount > 0) {
                        ++DeShopItemTable._buyCount;
                    }
                    de.set_buycount(buycount);
                    de.set_enchantlvl(enchantlvl);
                    DeShopItemTable._itemList.add(de);
                }
            }
        }
        catch (SQLException e) {
            DeShopItemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private boolean check_item(int itemId) {
        L1Item itemTemplate = ItemTable.get().getTemplate(itemId);
        if (itemTemplate == null) {
            errorItem(itemId);
            return false;
        }
        else {
            return true;
        }
    }
    
    private static void errorItem(int itemid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `de_shopds` WHERE `item_id`=?");
            pstm.setInt(1, itemid);
            pstm.execute();
        }
        catch (SQLException e) {
            DeShopItemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void getItems(L1DeInstance deInstance) {
        DeShopItemTable.SetDeShopItem de_shop = new DeShopItemTable.SetDeShopItem(deInstance);
        GeneralThreadPool.get().schedule(de_shop, 250L);
    }
    
    class SetDeShopItem implements Runnable
    {
        private final L1DeInstance _deInstance;
        
        public SetDeShopItem(L1DeInstance deInstance) {
            this._deInstance = deInstance;
        }
        
        @Override
        public void run() {
            Map<L1ItemInstance, Integer> sellList = new HashMap();
            Map<Integer, int[]> buyList = new HashMap();
            try {
                int count = DeShopItemTable._random.nextInt(7) + 1;
                while (sellList.size() < count && this._deInstance.isShop()) {
                    int size = DeShopItemTable._itemList.size();
                    if (size <= 0) {
                        return;
                    }
                    else {
                        int index = DeShopItemTable._random.nextInt(DeShopItemTable._itemList.size());
                        DeShopItem iter = DeShopItemTable._itemList.get(index);
                        if (iter.get_sellcount() != 0) {
                            boolean isError = false;
                            for (L1ItemInstance srcItem : sellList.keySet()) {
                                if (iter.get_item_id() == srcItem.getItemId()) {
                                    isError = true;
                                }
                            }
                            if (!isError) {
                                L1ItemInstance item = ItemTable.get().createItem(iter.get_item_id());
                                if (item != null) {
                                    if (item.getItem().isStackable()) {
                                        item.setCount((long)(DeShopItemTable._random.nextInt(iter.get_sellcount()) + 1));
                                    }
                                    else {
                                        item.setCount((long)iter.get_sellcount());
                                    }
                                    item.setIdentified(true);
                                    item.setBless(item.getItem().getBless());
                                    item.setEnchantLevel(iter.get_enchantlvl());
                                    int price = 0;
                                    if (iter.get_price() > 10000) {
                                        switch (DeShopItemTable._random.nextInt(11)) {
                                            case 0: {
                                                price = (int)((double)iter.get_price() * 0.8);
                                                break;
                                            }
                                            case 1: {
                                                price = (int)((double)iter.get_price() * 0.82);
                                                break;
                                            }
                                            case 2: {
                                                price = (int)((double)iter.get_price() * 0.84);
                                                break;
                                            }
                                            case 3: {
                                                price = (int)((double)iter.get_price() * 0.86);
                                                break;
                                            }
                                            case 4: {
                                                price = (int)((double)iter.get_price() * 0.88);
                                                break;
                                            }
                                            case 5: {
                                                price = (int)((double)iter.get_price() * 0.9);
                                                break;
                                            }
                                            case 6: {
                                                price = (int)((double)iter.get_price() * 0.92);
                                                break;
                                            }
                                            case 7: {
                                                price = (int)((double)iter.get_price() * 0.94);
                                                break;
                                            }
                                            case 8: {
                                                price = (int)((double)iter.get_price() * 0.96);
                                                break;
                                            }
                                            case 9: {
                                                price = (int)((double)iter.get_price() * 0.9);
                                                break;
                                            }
                                            default: {
                                                price = iter.get_price();
                                                break;
                                            }
                                        }
                                        String t = String.valueOf(price);
                                        t = t.substring(0, t.length() - 3) + "000";
                                        price = Integer.parseInt(t);
                                    }
                                    else {
                                        price = iter.get_price();
                                    }
                                    sellList.put(item, price);
                                    if (World.get().findObject(item.getId()) == null) {
                                        World.get().storeObject(item);
                                    }
                                    if (iter.get_buycount() > 0 && DeShopItemTable._random.nextInt(100) < 10) {
                                        int itemid = iter.get_item_id();
                                        L1Item src = ItemTable.get().getTemplate(itemid);
                                        if (src != null) {
                                            int[] info = new int[3];
                                            if (iter.get_price() > 10000) {
                                                switch (DeShopItemTable._random.nextInt(11)) {
                                                    case 0: {
                                                        info[0] = (int)((double)iter.get_price() * 0.6);
                                                        break;
                                                    }
                                                    case 1: {
                                                        info[0] = (int)((double)iter.get_price() * 0.62);
                                                        break;
                                                    }
                                                    case 2: {
                                                        info[0] = (int)((double)iter.get_price() * 0.64);
                                                        break;
                                                    }
                                                    case 3: {
                                                        info[0] = (int)((double)iter.get_price() * 0.66);
                                                        break;
                                                    }
                                                    case 4: {
                                                        info[0] = (int)((double)iter.get_price() * 0.68);
                                                        break;
                                                    }
                                                    case 5: {
                                                        info[0] = (int)((double)iter.get_price() * 0.7);
                                                        break;
                                                    }
                                                    case 6: {
                                                        info[0] = (int)((double)iter.get_price() * 0.72);
                                                        break;
                                                    }
                                                    case 7: {
                                                        info[0] = (int)((double)iter.get_price() * 0.74);
                                                        break;
                                                    }
                                                    case 8: {
                                                        info[0] = (int)((double)iter.get_price() * 0.76);
                                                        break;
                                                    }
                                                    case 9: {
                                                        info[0] = (int)((double)iter.get_price() * 0.78);
                                                        break;
                                                    }
                                                    default: {
                                                        info[0] = (int)((double)iter.get_price() * 0.79);
                                                        break;
                                                    }
                                                }
                                                String t2 = String.valueOf(info[0]);
                                                t2 = t2.substring(0, t2.length() - 3) + "000";
                                                info[0] = Integer.parseInt(t2);
                                            }
                                            else {
                                                info[0] = (int)((double)iter.get_price() * 0.7);
                                            }
                                            info[1] = iter.get_enchantlvl();
                                            info[2] = iter.get_buycount();
                                            buyList.put(itemid, info);
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                    else {
                                        continue;
                                    }
                                }
                                else {
                                    continue;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
                //收購物品   if (DeShopItemTable._random.nextInt(100) < 30 && DeShopItemTable._buyCount != 0) {
					if (false) {
                    int buyListcount = DeShopItemTable._random.nextInt(3) + 1;
                    int i = 50;
                    while (buyList.size() < buyListcount) {
                        if (!this._deInstance.isShop()) {
                            break;
                        }
                        else {
                            int index2 = DeShopItemTable._random.nextInt(DeShopItemTable._itemList.size());
                            DeShopItem iter2 = DeShopItemTable._itemList.get(index2);
                            if (iter2.get_buycount() != 0) {
                                boolean isError2 = false;
                                for (L1ItemInstance srcItem2 : sellList.keySet()) {
                                    if (iter2.get_item_id() == srcItem2.getItemId()) {
                                        isError2 = true;
                                    }
                                }
                                if (!isError2) {
                                    int itemid2 = iter2.get_item_id();
                                    L1Item src = ItemTable.get().getTemplate(itemid2);
                                    if (src != null) {
                                        int[] info2 = new int[3];
                                        if (iter2.get_price() > 10000) {
                                            switch (DeShopItemTable._random.nextInt(11)) {
                                                case 0: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.6);
                                                    break;
                                                }
                                                case 1: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.62);
                                                    break;
                                                }
                                                case 2: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.64);
                                                    break;
                                                }
                                                case 3: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.66);
                                                    break;
                                                }
                                                case 4: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.68);
                                                    break;
                                                }
                                                case 5: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.7);
                                                    break;
                                                }
                                                case 6: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.72);
                                                    break;
                                                }
                                                case 7: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.74);
                                                    break;
                                                }
                                                case 8: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.76);
                                                    break;
                                                }
                                                case 9: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.78);
                                                    break;
                                                }
                                                default: {
                                                    info2[0] = (int)((double)iter2.get_price() * 0.79);
                                                    break;
                                                }
                                            }
                                            String t3 = String.valueOf(info2[0]);
                                            t3 = t3.substring(0, t3.length() - 3) + "000";
                                            info2[0] = Integer.parseInt(t3);
                                        }
                                        else {
                                            info2[0] = (int)((double)iter2.get_price() * 0.7);
                                        }
                                        info2[1] = iter2.get_enchantlvl();
                                        info2[2] = iter2.get_buycount();
                                        buyList.put(itemid2, info2);
                                    }
                                }
                            }
                            else {
                                --i;
                            }
                            if (i <= 0) {
                                DeShopItemTable._log.warn("找不到可以回收的物品，中斷物品搜尋！");
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                if (sellList.size() > 0) {
                    this._deInstance.sellList(sellList);
                }
                if (buyList.size() > 0) {
                    this._deInstance.buyList(buyList);
                }
            }
            catch (Exception e) {
                DeShopItemTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                sellList.clear();
                buyList.clear();
            }
        }
    }
}
