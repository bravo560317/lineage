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
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.DwarfForClanStorage;

public class DwarfForClanTable implements DwarfForClanStorage
{
    private static final Log _log;
    private static DwarfForClanTable _instance;
    private static final Map<String, CopyOnWriteArrayList<L1ItemInstance>> _itemList;
    
    static {
        _log = LogFactory.getLog(DwarfForClanTable.class);
        _itemList = new ConcurrentHashMap();
    }
    
    public static DwarfForClanTable get() {
        if (DwarfForClanTable._instance == null) {
            DwarfForClanTable._instance = new DwarfForClanTable();
        }
        return DwarfForClanTable._instance;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        int i = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `clan_warehouse` order by item_id");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int objid = rs.getInt("id");
                String clan_name = rs.getString("clan_name");
                int clan_id = CharObjidTable.get().clanObjid(clan_name);
                if (clan_id != 0) {
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
                        addItem(clan_name, item);
                        ++i;
                    }
                }
                else {
                    deleteItem(clan_name);
                }
            }
        }
        catch (SQLException e2) {
            DwarfForClanTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        DwarfForClanTable._log.info("載入血盟倉庫物件清單資料數量: " + DwarfForClanTable._itemList.size() + "/" + i + "(" + timer.get() + "ms)");
    }
    
    private static void errorItem(int objid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `clan_warehouse` WHERE `id`=?");
            pstm.setInt(1, objid);
            pstm.execute();
        }
        catch (SQLException e) {
            DwarfForClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private static void addItem(String clan_name, L1ItemInstance item) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfForClanTable._itemList.get(clan_name);
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
        DwarfForClanTable._itemList.put(clan_name, list);
    }
    
    private static void deleteItem(String clan_name) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfForClanTable._itemList.remove(clan_name);
        if (list != null) {
            for (L1ItemInstance item : list) {
                World.get().removeObject(item);
            }
        }
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `clan_warehouse` WHERE `clan_name`=?");
            ps.setString(1, clan_name);
            ps.execute();
        }
        catch (SQLException e) {
            DwarfForClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(String clan_name) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfForClanTable._itemList.get(clan_name);
        if (list != null) {
            return list;
        }
        else {
            return null;
        }
    }
    
    @Override
    public void delUserItems(String clan_name) {
        deleteItem(clan_name);
    }
    
    @Override
    public boolean getUserItems(String clan_name, int objid, int count) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfForClanTable._itemList.get(clan_name);
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
    public void insertItem(String clan_name, L1ItemInstance item) {
        DwarfForClanTable._log.warn("血盟:" + clan_name + " 加入血盟倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
        addItem(clan_name, item);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `clan_warehouse` SET `id`=?,`clan_name`=?,`item_id`= ?,`item_name`=?,`count`=?,`is_equipped`=0,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
            int i = 0;
            pstm.setInt(++i, item.getId());
            pstm.setString(++i, clan_name);
            pstm.setInt(++i, item.getItemId());
            pstm.setString(++i, item.getItem().getName());
            pstm.setLong(++i, item.getCount());
            pstm.setInt(++i, item.getEnchantLevel());
            pstm.setInt(++i, item.isIdentified() ? 1 : 0);
            pstm.setInt(++i, item.get_durability());
            pstm.setInt(++i, item.getChargeCount());
            pstm.setInt(++i, item.getRemainingTime());
            pstm.setTimestamp(++i, item.getLastUsed());
            pstm.setInt(++i, item.getBless());
            pstm.setInt(++i, item.getAttrEnchantKind());
            pstm.setInt(++i, item.getAttrEnchantLevel());
            pstm.setInt(++i, item.getItemAttack());
            pstm.setInt(++i, item.getItemBowAttack());
            pstm.setInt(++i, item.getItemHit());
            pstm.setInt(++i, item.getItemBowHit());
            pstm.setInt(++i, item.getItemReductionDmg());
            pstm.setInt(++i, item.getItemSp());
            pstm.setInt(++i, item.getItemprobability());
            pstm.setInt(++i, item.getItemStr());
            pstm.setInt(++i, item.getItemDex());
            pstm.setInt(++i, item.getItemInt());
            pstm.setInt(++i, item.getItemCon());
            pstm.setInt(++i, item.getItemCha());
            pstm.setInt(++i, item.getItemWis());
            pstm.setInt(++i, item.getItemHp());
            pstm.setInt(++i, item.getItemMp());
            pstm.setInt(++i, item.getItemMr());
            pstm.setInt(++i, item.getItemAc());
            pstm.setInt(++i, item.getItemMag_Red());
            pstm.setInt(++i, item.getItemMag_Hit());
            pstm.setInt(++i, item.getItemDg());
            pstm.setInt(++i, item.getItemistSustain());
            pstm.setInt(++i, item.getItemistStun());
            pstm.setInt(++i, item.getItemistStone());
            pstm.setInt(++i, item.getItemistSleep());
            pstm.setInt(++i, item.getItemistFreeze());
            pstm.setInt(++i, item.getItemistBlind());
            pstm.setInt(++i, item.getItemArmorType());
            pstm.setInt(++i, item.getItemArmorLv());
            pstm.setInt(++i, item.getskilltype());
            pstm.setInt(++i, item.getItemType());
            pstm.setInt(++i, item.getItemHpr());
            pstm.setInt(++i, item.getItemMpr());
            pstm.setInt(++i, item.getItemhppotion());
            pstm.setInt(++i, item.getskilltypelv());
            pstm.setInt(++i, item.getGamNo());
            pstm.setInt(++i, item.getGamNpcId());
            pstm.setString(++i, item.getStarNpcId());
            pstm.setString(++i, item.getraceGamNo());
            pstm.execute();
        }
        catch (SQLException e) {
            DwarfForClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateItem(L1ItemInstance item) {
        DwarfForClanTable._log.warn(" 更新血盟倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `clan_warehouse` SET `count`=? WHERE `id`=?");
            pstm.setLong(1, item.getCount());
            pstm.setInt(2, item.getId());
            pstm.execute();
        }
        catch (SQLException e) {
            DwarfForClanTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void deleteItem(String clan_name, L1ItemInstance item) {
        CopyOnWriteArrayList<?> list = (CopyOnWriteArrayList)DwarfForClanTable._itemList.get(clan_name);
        if (list != null) {
            DwarfForClanTable._log.warn("血盟:" + clan_name + " 血盟倉庫物品移出:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
            list.remove(item);
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM `clan_warehouse` WHERE `id`=?");
                pstm.setInt(1, item.getId());
                pstm.execute();
            }
            catch (SQLException e) {
                DwarfForClanTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
        }
    }
    
    @Override
    public boolean getUserItem(int objid) {
        for (CopyOnWriteArrayList<L1ItemInstance> list : DwarfForClanTable._itemList.values()) {
            for (L1ItemInstance item : list) {
                if (item.getId() == objid) {
                    return true;
                }
            }
        }
        return false;
    }
}
