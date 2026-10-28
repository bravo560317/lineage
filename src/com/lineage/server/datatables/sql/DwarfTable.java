package com.lineage.server.datatables.sql;

import java.util.Iterator;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Item;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.InnKeyTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.DwarfStorage;

public class DwarfTable implements DwarfStorage
{
    private static final Log _log;
    private static DwarfTable _instance;
    private static final Map<String, CopyOnWriteArrayList<L1ItemInstance>> _itemList;
    
    static {
        _log = LogFactory.getLog(DwarfTable.class);
        _itemList = new ConcurrentHashMap();
    }
    
    public static DwarfTable get() {
        if (DwarfTable._instance == null) {
            DwarfTable._instance = new DwarfTable();
        }
        return DwarfTable._instance;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        int i = 0;
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM character_warehouse order by item_id");
            rs = ps.executeQuery();
            while (rs.next()) {
                int objid = rs.getInt("id");
                String account_name = rs.getString("account_name").toLowerCase();
                boolean account = AccountReading.get().isAccountUT(account_name);
                if (account) {
                    int item_id = rs.getInt("item_id");
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
                    L1Item itemTemplate = ItemTable.get().getTemplate(item_id);
                    if (itemTemplate == null) {
                        errorItem(objid);
                    }
                    else {
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
                        addItem(account_name, item);
                        ++i;
                    }
                }
                else {
                    deleteItem(account_name);
                }
            }
        }
        catch (SQLException e2) {
            DwarfTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        DwarfTable._log.info("載入帳號倉庫物件清單資料數量: " + DwarfTable._itemList.size() + "/" + i + "(" + timer.get() + "ms)");
    }
    
    private static void errorItem(int objid) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("DELETE FROM `character_warehouse` WHERE `id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            DwarfTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    public static void addItem(String account_name, L1ItemInstance item) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfTable._itemList.get(account_name);
        if (list == null) {
            list = new CopyOnWriteArrayList();
            if (!list.contains(item)) {
                list.add(item);
            }
        }
        else if (!list.contains(item)) {
            list.add(item);
        }
        if (World.get().findObject(item.getId()) == null) {
            World.get().storeObject(item);
        }
        DwarfTable._itemList.put(account_name, list);
    }
    
    private static void deleteItem(String account_name) {
        System.out.println("刪除遺失資料-帳號不存在");
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfTable._itemList.remove(account_name);
        if (list != null) {
            for (L1ItemInstance item : list) {
                World.get().removeObject(item);
            }
        }
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_warehouse` WHERE `account_name`=?");
            ps.setString(1, account_name);
            ps.execute();
        }
        catch (SQLException e) {
            DwarfTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public Map<String, CopyOnWriteArrayList<L1ItemInstance>> allItems() {
        return DwarfTable._itemList;
    }
    
    @Override
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(String account_name) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfTable._itemList.get(account_name);
        if (list != null) {
            return list;
        }
        else {
            return null;
        }
    }
    
    @Override
    public void delUserItems(String account_name) {
        deleteItem(account_name);
    }
    
    @Override
    public boolean getUserItems(String account_name, int objid, int count) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfTable._itemList.get(account_name);
        if (list != null) {
            if (list.size() <= 0) {
                return false;
            }
            else {
                for (L1ItemInstance item : list) {
                    if (item.getId() == objid && item.getCount() >= (long)count) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public void insertItem(String account_name, L1ItemInstance item) {
        DwarfTable._log.warn("帳號:" + account_name + " 加入帳號倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
        addItem(account_name, item);
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `character_warehouse` SET `id`=?,`account_name`=?,`item_id`= ?,`item_name`=?,`count`=?,`is_equipped`=0,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
            int i = 0;
            ps.setInt(++i, item.getId());
            ps.setString(++i, account_name);
            ps.setInt(++i, item.getItemId());
            ps.setString(++i, item.getItem().getName());
            ps.setLong(++i, item.getCount());
            ps.setInt(++i, item.getEnchantLevel());
            ps.setInt(++i, item.isIdentified() ? 1 : 0);
            ps.setInt(++i, item.get_durability());
            ps.setInt(++i, item.getChargeCount());
            ps.setInt(++i, item.getRemainingTime());
            if (item.getLastUsed() != null) {
                System.out.println(item.getLastUsed().getTime());
            }
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
            DwarfTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void updateItem(L1ItemInstance item) {
        DwarfTable._log.warn("更新帳號倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseFactory.get().getConnection();
            ps = con.prepareStatement("UPDATE `character_warehouse` SET `count`=? WHERE `id`=?");
            ps.setLong(1, item.getCount());
            ps.setInt(2, item.getId());
            ps.execute();
        }
        catch (SQLException e) {
            DwarfTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void deleteItem(String account_name, L1ItemInstance item) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfTable._itemList.get(account_name);
        if (list != null) {
            DwarfTable._log.warn("帳號:" + account_name + " 帳號倉庫物品移出 :" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
            list.remove(item);
            Connection co = null;
            PreparedStatement pstm = null;
            try {
                co = DatabaseFactory.get().getConnection();
                pstm = co.prepareStatement("DELETE FROM `character_warehouse` WHERE `id`=?");
                pstm.setInt(1, item.getId());
                pstm.execute();
            }
            catch (SQLException e) {
                DwarfTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(co);
            }
        }
    }
    
    @Override
    public boolean getUserItem(int objid) {
        for (CopyOnWriteArrayList<L1ItemInstance> list : DwarfTable._itemList.values()) {
            for (L1ItemInstance item : list) {
                if (item.getId() == objid) {
                    return true;
                }
            }
        }
        return false;
    }
}
