package com.lineage.server.datatables.sql;

import java.util.Iterator;
import java.util.HashMap;
import com.lineage.server.world.World;
import java.sql.Timestamp;
import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.InnKeyTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ShopS;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.DwarfShopStorage;

public class DwarfShopTable implements DwarfShopStorage
{
    private static final Log _log;
    private static final Map<Integer, L1ItemInstance> _itemList;
    private static final Map<Integer, L1ShopS> _shopSList;
    private static int _id;
    
    static {
        _log = LogFactory.getLog(DwarfShopTable.class);
        _itemList = new ConcurrentHashMap();
        _shopSList = new ConcurrentHashMap();
        DwarfShopTable._id = 0;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `character_shopinfo`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int objid = rs.getInt("id");
                int item_id = rs.getInt("item_id");
                L1Item itemTemplate = ItemTable.get().getTemplate(item_id);
                if (itemTemplate == null) {
                    errorItem(objid);
                }
                else {
                    long count = rs.getLong("count");
                    int enchantlvl = rs.getInt("enchantlvl");
                    int is_id = rs.getInt("is_id");
                    int durability = rs.getInt("durability");
                    int charge_count = rs.getInt("charge_count");
                    int remaining_time = rs.getInt("remaining_time");
                    Timestamp last_used = null;
                    try {
                        last_used = rs.getTimestamp("last_used");
                    }
                    catch (Exception e) {
                        last_used = null;
                    }
                    int bless = rs.getInt("bless");
                    int attr_enchant_kind = rs.getInt("attr_enchant_kind");
                    int attr_enchant_level = rs.getInt("attr_enchant_level");
                    int itemAttack = rs.getInt("ItemAttack");
                    int itemBowAttack = rs.getInt("ItemBowAttack");
                    int itemHit = rs.getInt("ItemHit");
                    int itemBowHit = rs.getInt("ItemBowHit");
                    int itemReductionDmg = rs.getInt("ItemReductionDmg");
                    int itemSp = rs.getInt("ItemSp");
                    int itemprobability = rs.getInt("Itemprobability");
                    int itemStr = rs.getInt("ItemStr");
                    int itemDex = rs.getInt("ItemDex");
                    int itemInt = rs.getInt("ItemInt");
                    int itemCon = rs.getInt("ItemCon");
                    int itemCha = rs.getInt("ItemCha");
                    int itemWis = rs.getInt("ItemWis");
                    int itemHp = rs.getInt("ItemHp");
                    int itemMp = rs.getInt("ItemMp");
                    int itemMr = rs.getInt("ItemMr");
                    int itemAc = rs.getInt("ItemAc");
                    int itemMag_Red = rs.getInt("ItemMag_Red");
                    int itemMag_Hit = rs.getInt("ItemMag_Hit");
                    int itemDg = rs.getInt("ItemDg");
                    int itemistSustain = rs.getInt("ItemistSustain");
                    int itemistStun = rs.getInt("ItemistStun");
                    int itemistStone = rs.getInt("ItemistStone");
                    int itemistSleep = rs.getInt("ItemistSleep");
                    int itemistFreeze = rs.getInt("ItemistFreeze");
                    int itemistBlind = rs.getInt("ItemistBlind");
                    int itemArmorType = rs.getInt("ItemArmorType");
                    int itemArmorLv = rs.getInt("ItemArmorLv");
                    int skilltype = rs.getInt("skilltype");
                    int skilltypelv = rs.getInt("skilltypelv");
                    int itemType = rs.getInt("ItemType");
                    int itemHpr = rs.getInt("ItemHpr");
                    int itemMpr = rs.getInt("ItemMpr");
                    int itemhppotion = rs.getInt("Itemhppotion");
                    String racegamno = rs.getString("racegamno");
                    L1ItemInstance item = new L1ItemInstance();
                    item.setId(objid);
                    item.setItem(itemTemplate);
                    item.setCount(count);
                    item.setEquipped(false);
                    item.setEnchantLevel(enchantlvl);
                    item.setIdentified(is_id != 0);
                    item.set_durability(durability);
                    item.setChargeCount(charge_count);
                    item.setRemainingTime(remaining_time);
                    item.setLastUsed(last_used);
                    item.setBless(bless);
                    item.setAttrEnchantKind(attr_enchant_kind);
                    item.setAttrEnchantLevel(attr_enchant_level);
                    item.setItemAttack(itemAttack);
                    item.setItemBowAttack(itemBowAttack);
                    item.setItemHit(itemHit);
                    item.setItemBowHit(itemBowHit);
                    item.setItemReductionDmg(itemReductionDmg);
                    item.setItemSp(itemSp);
                    item.setItemprobability(itemprobability);
                    item.setItemStr(itemStr);
                    item.setItemDex(itemDex);
                    item.setItemInt(itemInt);
                    item.setItemCon(itemCon);
                    item.setItemCha(itemCha);
                    item.setItemWis(itemWis);
                    item.setItemHp(itemHp);
                    item.setItemMp(itemMp);
                    item.setItemMr(itemMr);
                    item.setItemAc(itemAc);
                    item.setItemMag_Red(itemMag_Red);
                    item.setItemMag_Hit(itemMag_Hit);
                    item.setItemDg(itemDg);
                    item.setItemistSustain(itemistSustain);
                    item.setItemistStun(itemistStun);
                    item.setItemistStone(itemistStone);
                    item.setItemistSleep(itemistSleep);
                    item.setItemistFreeze(itemistFreeze);
                    item.setItemistBlind(itemistBlind);
                    item.setItemArmorType(itemArmorType);
                    item.setItemArmorLv(itemArmorLv);
                    item.setskilltype(skilltype);
                    item.setskilltypelv(skilltypelv);
                    item.setItemType(itemType);
                    item.setItemHpr(itemHpr);
                    item.setItemMpr(itemMpr);
                    item.setItemhppotion(itemhppotion);
                    item.setraceGamNo(racegamno);
                    item.setGamNo(rs.getInt("gamNo"));
                    item.setGamNpcId(rs.getInt("gamNpcId"));
                    item.setStarNpcId(rs.getString("starNpcId"));
                    if (item.getItem().getItemId() == 40312) {
                        InnKeyTable.checkey(item);
                    }
                    if (item.getItem().getItemId() == 82503) {
                        InnKeyTable.checkey(item);
                    }
                    if (item.getItem().getItemId() == 82504) {
                        InnKeyTable.checkey(item);
                    }
                    addItem(objid, item);
                }
            }
        }
        catch (SQLException e2) {
            DwarfShopTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
            loadShopS();
        }
        DwarfShopTable._log.info("載入託售道具資料數量: " + DwarfShopTable._itemList.size() + "/" + DwarfShopTable._shopSList.size() + "(" + timer.get() + "ms)");
    }
    
    private static void addItem(int key, L1ItemInstance value) {
        if (DwarfShopTable._itemList.get((Integer)key) == null) {
            DwarfShopTable._itemList.put(key, value);
        }
        if (World.get().findObject(key) == null) {
            World.get().storeObject(value);
        }
    }
    
    @Override
    public int get_id() {
        return DwarfShopTable._id;
    }
    
    @Override
    public void set_id(int id) {
        DwarfShopTable._id = id;
    }
    
    private static void loadShopS() {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `character_shop`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int item_obj_id = rs.getInt("item_obj_id");
                int user_obj_id = rs.getInt("user_obj_id");
                int adena = rs.getInt("adena");
                Timestamp overtime = rs.getTimestamp("overtime");
                int end = rs.getInt("end");
                String none = rs.getString("none");
                if (DwarfShopTable._id < id) {
                    DwarfShopTable._id = id;
                }
                L1ShopS shopS = new L1ShopS();
                shopS.set_id(id);
                shopS.set_item_obj_id(item_obj_id);
                shopS.set_user_obj_id(user_obj_id);
                shopS.set_adena(adena);
                shopS.set_overtime(overtime);
                shopS.set_end(end);
                shopS.set_none(none);
                switch (end) {
                    case 0:
                    case 1:
                    case 3: {
                        L1ItemInstance item = DwarfShopTable._itemList.get((Integer)item_obj_id);
                        shopS.set_item(item);
                        break;
                    }
                    case 2:
                    case 4: {
                        shopS.set_item(null);
                        break;
                    }
                }
                userMap(id, shopS);
            }
        }
        catch (SQLException e) {
            DwarfShopTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    private static void userMap(int key, L1ShopS value) {
        if (DwarfShopTable._shopSList.get((Integer)key) == null) {
            DwarfShopTable._shopSList.put(key, value);
        }
    }
    
    private static void errorItem(int objid) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("DELETE FROM `character_shopinfo` WHERE `id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            DwarfShopTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public HashMap<Integer, L1ShopS> allShopS() {
        HashMap<Integer, L1ShopS> shopSList = new HashMap();
        for (L1ShopS value : DwarfShopTable._shopSList.values()) {
            if (value.get_end() == 0) {
                shopSList.put(value.get_id(), value);
            }
        }
        return shopSList;
    }
    
    @Override
    public Map<Integer, L1ItemInstance> allItems() {
        return DwarfShopTable._itemList;
    }
    
    @Override
    public L1ShopS getShopS(int objid) {
        L1ShopS out = null;
        int i = 0;
        for (L1ShopS value : DwarfShopTable._shopSList.values()) {
            if (value.get_end() == 0 && value.get_item_obj_id() == objid) {
                out = value;
                ++i;
            }
        }
        if (i > 1) {
            DwarfShopTable._log.error("取回託售物品資料異常-未售出物品OBJID重複:" + objid);
        }
        return out;
    }
    
    @Override
    public HashMap<Integer, L1ShopS> getShopSMap(int pcobjid) {
        HashMap<Integer, L1ShopS> shopSMap = new HashMap();
        int index = 0;
        for (int i = DwarfShopTable._shopSList.size() + 1; i > 0; --i) {
            L1ShopS value = DwarfShopTable._shopSList.get((Integer)i);
            if (value != null && value.get_user_obj_id() == pcobjid) {
                shopSMap.put(index, value);
                ++index;
            }
        }
        if (shopSMap.size() > 0) {
            return shopSMap;
        }
        else {
            return null;
        }
    }
    
    @Override
    public void insertItem(int key, L1ItemInstance item, L1ShopS shopS) {
        addItem(key, item);
        this.set_userMap(shopS.get_id(), shopS);
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `character_shopinfo` SET `id`=?,`item_id`= ?,`item_name`=?,`count`=?,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
            int i = 0;
            ps.setInt(++i, item.getId());
            ps.setInt(++i, item.getItemId());
            ps.setString(++i, item.getItem().getName());
            ps.setLong(++i, item.getCount());
            ps.setInt(++i, item.getEnchantLevel());
            ps.setInt(++i, item.isIdentified() ? 1 : 0);
            ps.setInt(++i, item.get_durability());
            ps.setInt(++i, item.getChargeCount());
            ps.setInt(++i, item.getRemainingTime());
            ps.setTimestamp(++i, item.getLastUsed());
            ps.setInt(++i, item.getBless());
            ps.setInt(++i, item.getAttrEnchantKind());
            ps.setInt(++i, item.getAttrEnchantLevel());
            ps.setInt(++i, item.getItemAttack());
            ps.setInt(++i, item.getItemBowAttack());
            ps.setInt(++i, item.getItemHit());
            ps.setInt(++i, item.getItemBowHit());
            ps.setInt(++i, item.getItemReductionDmg());
            ps.setInt(++i, item.getItemSp());
            ps.setInt(++i, item.getItemprobability());
            ps.setInt(++i, item.getItemStr());
            ps.setInt(++i, item.getItemDex());
            ps.setInt(++i, item.getItemInt());
            ps.setInt(++i, item.getItemCon());
            ps.setInt(++i, item.getItemCha());
            ps.setInt(++i, item.getItemWis());
            ps.setInt(++i, item.getItemHp());
            ps.setInt(++i, item.getItemMp());
            ps.setInt(++i, item.getItemMr());
            ps.setInt(++i, item.getItemAc());
            ps.setInt(++i, item.getItemMag_Red());
            ps.setInt(++i, item.getItemMag_Hit());
            ps.setInt(++i, item.getItemDg());
            ps.setInt(++i, item.getItemistSustain());
            ps.setInt(++i, item.getItemistStun());
            ps.setInt(++i, item.getItemistStone());
            ps.setInt(++i, item.getItemistSleep());
            ps.setInt(++i, item.getItemistFreeze());
            ps.setInt(++i, item.getItemistBlind());
            ps.setInt(++i, item.getItemArmorType());
            ps.setInt(++i, item.getItemArmorLv());
            ps.setInt(++i, item.getskilltype());
            ps.setInt(++i, item.getskilltypelv());
            ps.setInt(++i, item.getItemType());
            ps.setInt(++i, item.getItemHpr());
            ps.setInt(++i, item.getItemMpr());
            ps.setInt(++i, item.getItemhppotion());
            ps.setInt(++i, item.getGamNo());
            ps.setInt(++i, item.getGamNpcId());
            ps.setString(++i, item.getStarNpcId());
            ps.setString(++i, item.getraceGamNo());
            ps.execute();
        }
        catch (SQLException e) {
            DwarfShopTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    private void set_userMap(int getId, L1ShopS shopS) {
        userMap(shopS.get_id(), shopS);
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `character_shop` SET `id`=?,`item_obj_id`= ?,`user_obj_id`=?,`adena`=?,`overtime`=?,`end`=?,`none`=?");
            int i = 0;
            ps.setInt(++i, shopS.get_id());
            ps.setInt(++i, shopS.get_item_obj_id());
            ps.setInt(++i, shopS.get_user_obj_id());
            ps.setInt(++i, shopS.get_adena());
            ps.setTimestamp(++i, shopS.get_overtime());
            ps.setInt(++i, shopS.get_end());
            ps.setString(++i, shopS.get_none());
            ps.execute();
        }
        catch (SQLException e) {
            DwarfShopTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void updateShopS(L1ShopS shopS) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `character_shop` SET `end`=? WHERE `id`=?");
            pstm.setLong(1, (long)shopS.get_end());
            pstm.setInt(2, shopS.get_id());
            pstm.execute();
        }
        catch (SQLException e) {
            DwarfShopTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void deleteItem(int key) {
        L1ItemInstance item = DwarfShopTable._itemList.get((Integer)key);
        if (item != null) {
            DwarfShopTable._itemList.remove((Integer)key);
            errorItem(key);
        }
    }
}
