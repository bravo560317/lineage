package com.lineage.server.datatables.sql;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
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
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharItemsStorage;

public class CharItemsTable implements CharItemsStorage
{
    private static final Log _log;
    private static CharItemsTable _instance;
    private static final Map<Integer, CopyOnWriteArrayList<L1ItemInstance>> _itemList;
    
    static {
        _log = LogFactory.getLog(BuddyTable.class);
        _itemList = new ConcurrentHashMap();
    }
    
    public static CharItemsTable get() {
        if (CharItemsTable._instance == null) {
            CharItemsTable._instance = new CharItemsTable();
        }
        return CharItemsTable._instance;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        int i = 0;
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_items`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int objid = rs.getInt("id");
                int item_id = rs.getInt("item_id");
                int char_id = rs.getInt("char_id");
                if (CharObjidTable.get().isChar(char_id) != null) {
                    L1Item itemTemplate = ItemTable.get().getTemplate(item_id);
                    if (itemTemplate == null) {
                        errorItem(objid);
                    }
                    else {
                        long count = rs.getLong("count");
                        int is_equipped = rs.getInt("is_equipped");
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
                        item.setEquipped(is_equipped != 0);
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
                        item.set_char_objid(char_id);
                        item.getLastStatus().updateAll();
                        if (item.getItem().getItemId() == 40312) {
                            InnKeyTable.checkey(item);
                        }
                        if (item.getItem().getItemId() == 82503) {
                            InnKeyTable.checkey(item);
                        }
                        if (item.getItem().getItemId() == 82504) {
                            InnKeyTable.checkey(item);
                        }
                        addItem(char_id, item);
                        ++i;
                    }
                }
                else {
                    deleteItem(char_id);
                }
            }
        }
        catch (SQLException e2) {
            CharItemsTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharItemsTable._log.info("載入人物背包物件清單資料數量: " + CharItemsTable._itemList.size() + "/" + i + "(" + timer.get() + "ms)");
    }
    
    private static void errorItem(int objid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `character_items` WHERE `id`=?");
            pstm.setInt(1, objid);
            pstm.execute();
        }
        catch (SQLException e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private static void errorItempower(int item_obj_id) {
        System.out.println("Welco555555555555555555555555555555555555555555555");
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `character_item_power` WHERE `item_obj_id`=?");
            pstm.setInt(1, item_obj_id);
            pstm.execute();
        }
        catch (SQLException e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private static void addItem(Integer objid, L1ItemInstance item) {
        CopyOnWriteArrayList<L1ItemInstance> list = CharItemsTable._itemList.get(objid);
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
        CharItemsTable._itemList.put(objid, list);
    }
    
    private static void deleteItem(Integer objid) {
        CopyOnWriteArrayList<L1ItemInstance> list = CharItemsTable._itemList.remove(objid);
        if (list != null) {
            for (L1ItemInstance item : list) {
                World.get().removeObject(item);
            }
        }
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_items` WHERE `char_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(Integer objid) {
        CopyOnWriteArrayList<L1ItemInstance> list = CharItemsTable._itemList.get(objid);
        if (list != null) {
            return list;
        }
        else {
            return null;
        }
    }
    
    @Override
    public void delUserItems(Integer objid) {
        deleteItem(objid);
    }
    
    @Override
    public boolean getUserItems(Integer pcObjid, int objid, long count) {
        CopyOnWriteArrayList<L1ItemInstance> list = CharItemsTable._itemList.get(pcObjid);
        if (list != null) {
            for (L1ItemInstance item : list) {
                if (item.getId() == objid && item.getCount() >= count) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean getUserItem(int objid) {
        for (CopyOnWriteArrayList<L1ItemInstance> list : CharItemsTable._itemList.values()) {
            for (L1ItemInstance item : list) {
                if (item.getId() == objid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Map<Integer, L1ItemInstance> getUserItems(int itemid) {
        Map<Integer, L1ItemInstance> outList = new ConcurrentHashMap();
        try {
            for (Integer key : CharItemsTable._itemList.keySet()) {
                CopyOnWriteArrayList<L1ItemInstance> value = CharItemsTable._itemList.get(key);
                for (L1ItemInstance item : value) {
                    if (item.getItemId() == itemid) {
                        outList.put(key, item);
                    }
                }
            }
        }
        catch (Exception e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
        return outList;
    }
    
    @Override
    public void del_item(int itemid) {
        try {
            for (Integer key : CharItemsTable._itemList.keySet()) {
                CopyOnWriteArrayList<L1ItemInstance> value = CharItemsTable._itemList.get(key);
                for (L1ItemInstance item : value) {
                    if (item.getItemId() == itemid) {
                        this.deleteItem(key, item);
                    }
                }
            }
        }
        catch (Exception e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void storeItem(int objId, L1ItemInstance item) throws Exception {
        addItem(objId, item);
        item.getLastStatus().updateAll();
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `character_items` SET `id`=?,`item_id`=?,`char_id`=?,`item_name`=?,`count`=?,`is_equipped`=?,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
            int i = 0;
            pstm.setInt(++i, item.getId());
            pstm.setInt(++i, item.getItem().getItemId());
            pstm.setInt(++i, objId);
            pstm.setString(++i, item.getItem().getName());
            pstm.setLong(++i, item.getCount());
            pstm.setInt(++i, item.isEquipped() ? 1 : 0);
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
            pstm.setInt(++i, item.getskilltypelv());
            pstm.setInt(++i, item.getItemType());
            pstm.setInt(++i, item.getItemHpr());
            pstm.setInt(++i, item.getItemMpr());
            pstm.setInt(++i, item.getItemhppotion());
            pstm.setInt(++i, item.getGamNo());
            pstm.setInt(++i, item.getGamNpcId());
            pstm.setString(++i, item.getStarNpcId());
            pstm.setString(++i, item.getraceGamNo());
            pstm.execute();
        }
        catch (SQLException e) {
            CharItemsTable._log.error("背包物品增加時發生異常 人物OBJID:" + objId, e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void deleteItem(int objid, L1ItemInstance item) throws Exception {
        CopyOnWriteArrayList<L1ItemInstance> list = CharItemsTable._itemList.get((Integer)objid);
        if (list != null) {
            list.remove(item);
            World.get().removeObject(item);
            Connection cn = null;
            PreparedStatement ps = null;
            try {
                cn = DatabaseFactory.get().getConnection();
                ps = cn.prepareStatement("DELETE FROM `character_items` WHERE `id`=?");
                ps.setInt(1, item.getId());
                ps.execute();
            }
            catch (SQLException e) {
                CharItemsTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(cn);
            }
        }
    }
    
    public void updateName(L1ItemInstance item) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        L1Object object = World.get().findObject(item.get_char_objid());
        L1PcInstance tgpc = (L1PcInstance)object;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `character_items` SET `item_id`=?,`item_name`=?,`bless`=? WHERE `id`=?");
            pstm.setInt(1, item.getItemId());
            pstm.setString(2, tgpc.getName() + "=>" + item.getItem().getName());
            pstm.setInt(3, item.getItem().getBless());
            pstm.setInt(4, item.getId());
            pstm.execute();
        }
        catch (SQLException e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateItemId_Name(L1ItemInstance item) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `character_items` SET `item_id`=?,`item_name`=?,`bless`=? WHERE `id`=?");
            pstm.setInt(1, item.getItemId());
            pstm.setString(2, item.getItem().getName());
            pstm.setInt(3, item.getItem().getBless());
            pstm.setInt(4, item.getId());
            pstm.execute();
        }
        catch (SQLException e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateItemId(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `item_id`=? WHERE `id`=?", (long)item.getItemId());
        item.getLastStatus().updateItemId();
    }
    
    @Override
    public void updateItemCount(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `count`=? WHERE `id`=?", item.getCount());
        item.getLastStatus().updateCount();
    }
    
    @Override
    public void updateGamNo(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE character_items SET gamNo = ? WHERE id = ?", (long)item.getGamNo());
        item.getLastStatus().updateGamNo();
    }
    
    @Override
    public void updateGamNpcId(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE character_items SET gamNpcId = ? WHERE id = ?", (long)item.getGamNpcId());
        item.getLastStatus().updateGamNpcId();
    }
    
    @Override
    public void updateStarNpcId(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE character_items SET StarNpcId = ? WHERE id = ?", item.getStarNpcId());
        item.getLastStatus().updateStarNpcId();
    }
    
    private void executeUpdate(int objId, String sql, String updateNum) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, updateNum);
            pstm.setInt(2, objId);
            pstm.execute();
        }
        catch (SQLException e) {
            throw e;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateItemDurability(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `durability`=? WHERE `id`=?", (long)item.get_durability());
        item.getLastStatus().updateDuraility();
    }
    
    @Override
    public void updateItemChargeCount(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `charge_count`=? WHERE `id`=?", (long)item.getChargeCount());
        item.getLastStatus().updateChargeCount();
    }
    
    @Override
    public void updateItemRemainingTime(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `remaining_time`=? WHERE `id`=?", (long)item.getRemainingTime());
        item.getLastStatus().updateRemainingTime();
    }
    
    @Override
    public void updateItemEnchantLevel(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `enchantlvl`=? WHERE `id`=?", (long)item.getEnchantLevel());
        item.getLastStatus().updateEnchantLevel();
    }
    
    @Override
    public void updateItemEquipped(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `is_equipped`=? WHERE `id`=?", (long)(item.isEquipped() ? 1 : 0));
        item.getLastStatus().updateEquipped();
    }
    
    @Override
    public void updateItemIdentified(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `is_id`=? WHERE `id`=?", (long)(item.isIdentified() ? 1 : 0));
        item.getLastStatus().updateIdentified();
    }
    
    @Override
    public void updateItemBless(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `bless`=? WHERE `id`=?", (long)item.getBless());
        item.getLastStatus().updateBless();
    }
    
    @Override
    public void updateItemAttrEnchantKind(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `attr_enchant_kind`=? WHERE `id`=?", (long)item.getAttrEnchantKind());
        item.getLastStatus().updateAttrEnchantKind();
    }
    
    @Override
    public void updateItemAttrEnchantLevel(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `attr_enchant_level`=? WHERE `id`=?", (long)item.getAttrEnchantLevel());
        item.getLastStatus().updateAttrEnchantLevel();
    }
    
    @Override
    public void updateItemDelayEffect(L1ItemInstance item) throws Exception {
        executeUpdate(item.getId(), "UPDATE `character_items` SET `last_used`=? WHERE `id`=?", item.getLastUsed());
        item.getLastStatus().updateLastUsed();
    }
    
    @Override
    public int getItemCount(int objId) throws Exception {
        int count = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `character_items` WHERE `char_id`=?");
            pstm.setInt(1, objId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ++count;
            }
        }
        catch (SQLException e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return count;
    }
    
    @Override
    public void getAdenaCount(int objid, long count) throws Exception {
        CopyOnWriteArrayList<L1ItemInstance> list = CharItemsTable._itemList.get((Integer)objid);
        if (list != null) {
            boolean isAdena = false;
            for (L1ItemInstance item : list) {
                if (item.getItemId() == 40308) {
                    item.setCount(item.getCount() + count);
                    this.updateItemCount(item);
                    isAdena = true;
                }
            }
            if (!isAdena) {
                L1ItemInstance item = ItemTable.get().createItem(40308);
                item.setCount(count);
                this.storeItem(objid, item);
            }
        }
    }
    
    @Override
    public void updateItemAttack(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemAttack`=? WHERE `id`=?", (long)item.getItemAttack());
        item.getLastStatus().updateItemAttack();
    }
    
    @Override
    public void updateItemBowAttack(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemBowAttack`=? WHERE `id`=?", (long)item.getItemBowAttack());
        item.getLastStatus().updateItemBowAttack();
    }
    
    @Override
    public void updateItemHit(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemHit`=? WHERE `id`=?", (long)item.getItemHit());
        item.getLastStatus().updateItemHit();
    }
    
    @Override
    public void updateItemBowHit(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemBowHit`=? WHERE `id`=?", (long)item.getItemBowHit());
        item.getLastStatus().updateItemBowHit();
    }
    
    @Override
    public void updateItemReductionDmg(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemReductionDmg`=? WHERE `id`=?", (long)item.getItemReductionDmg());
        item.getLastStatus().updateItemReductionDmg();
    }
    
    @Override
    public void updateItemSp(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemSp`=? WHERE `id`=?", (long)item.getItemSp());
        item.getLastStatus().updateItemSp();
    }
    
    @Override
    public void updateItemprobability(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `Itemprobability`=? WHERE `id`=?", (long)item.getItemprobability());
        item.getLastStatus().updateItemprobability();
    }
    
    @Override
    public void updateItemStr(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemStr`=? WHERE `id`=?", (long)item.getItemStr());
        item.getLastStatus().updateItemStr();
    }
    
    @Override
    public void updateItemDex(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemDex`=? WHERE `id`=?", (long)item.getItemDex());
        item.getLastStatus().updateItemDex();
    }
    
    @Override
    public void updateItemInt(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemInt`=? WHERE `id`=?", (long)item.getItemInt());
        item.getLastStatus().updateItemInt();
    }
    
    @Override
    public void updateItemHp(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemHp`=? WHERE `id`=?", (long)item.getItemHp());
        item.getLastStatus().updateItemHp();
    }
    
    @Override
    public void updateItemMp(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemMp`=? WHERE `id`=?", (long)item.getItemMp());
        item.getLastStatus().updateItemMp();
    }
    
    @Override
    public void updateskilltype(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `skilltype`=? WHERE `id`=?", (long)item.getskilltype());
        item.getLastStatus().updateskilltype();
    }
    
    @Override
    public void updateskilltypelv(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `skilltypelv`=? WHERE `id`=?", (long)item.getskilltypelv());
        item.getLastStatus().updateskilltypelv();
    }
    
    @Override
    public void updateItemCon(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemCon`=? WHERE `id`=?", (long)item.getItemCon());
        item.getLastStatus().updateItemCon();
    }
    
    @Override
    public void updateItemWis(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemWis`=? WHERE `id`=?", (long)item.getItemWis());
        item.getLastStatus().updateItemWis();
    }
    
    @Override
    public void updateItemCha(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemCha`=? WHERE `id`=?", (long)item.getItemCha());
        item.getLastStatus().updateItemCha();
    }
    
    @Override
    public void updateItemMr(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemMr`=? WHERE `id`=?", (long)item.getItemMr());
        item.getLastStatus().updateItemMr();
    }
    
    @Override
    public void updateItemAc(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemAc`=? WHERE `id`=?", (long)item.getItemAc());
        item.getLastStatus().updateItemAc();
    }
    
    @Override
    public void updateItemMag_Red(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemMag_Red`=? WHERE `id`=?", (long)item.getItemMag_Red());
        item.getLastStatus().updateItemMag_Red();
    }
    
    @Override
    public void updateItemMag_Hit(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemMag_Hit`=? WHERE `id`=?", (long)item.getItemMag_Hit());
        item.getLastStatus().updateItemMag_Hit();
    }
    
    @Override
    public void updateItemDg(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemDg`=? WHERE `id`=?", (long)item.getItemDg());
        item.getLastStatus().updateItemDg();
    }
    
    @Override
    public void updateItemistSustain(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistSustain`=? WHERE `id`=?", (long)item.getItemistSustain());
        item.getLastStatus().updateItemistSustain();
    }
    
    @Override
    public void updateItemistStun(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistStun`=? WHERE `id`=?", (long)item.getItemistStun());
        item.getLastStatus().updateItemistStun();
    }
    
    @Override
    public void updateItemistStone(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistStone`=? WHERE `id`=?", (long)item.getItemistStone());
        item.getLastStatus().updateItemistStone();
    }
    
    @Override
    public void updateItemistSleep(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistSleep`=? WHERE `id`=?", (long)item.getItemistSleep());
        item.getLastStatus().updateItemistSleep();
    }
    
    @Override
    public void updateItemistFreeze(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistFreeze`=? WHERE `id`=?", (long)item.getItemistFreeze());
        item.getLastStatus().updateItemistFreeze();
    }
    
    @Override
    public void updateItemistBlind(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistBlind`=? WHERE `id`=?", (long)item.getItemistBlind());
        item.getLastStatus().updateItemistBlind();
    }
    
    @Override
    public void updateItemArmorLv(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemArmorLv`=? WHERE `id`=?", (long)item.getItemArmorLv());
        item.getLastStatus().updateItemArmorLv();
    }
    
    @Override
    public void updateItemArmorType(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemArmorType`=? WHERE `id`=?", (long)item.getItemArmorType());
        item.getLastStatus().updateItemArmorType();
    }
    
    @Override
    public void updateItemType(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemType`=? WHERE `id`=?", (long)item.getItemArmorType());
        item.getLastStatus().updateItemArmorType();
    }
    
    @Override
    public void updateItemHpr(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemHpr`=? WHERE `id`=?", (long)item.getItemHpr());
        item.getLastStatus().updateItemHpr();
    }
    
    @Override
    public void updateItemMpr(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemMpr`=? WHERE `id`=?", (long)item.getItemMpr());
        item.getLastStatus().updateItemMpr();
    }
    
    @Override
    public void updateBless(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `Bless`=? WHERE `id`=?", (long)item.getBless());
        item.getLastStatus().updateBless();
    }
    
    @Override
    public void updateItemhppotion(L1ItemInstance item) throws Exception {
        this.executeUpdate(item.getId(), "UPDATE `character_items` SET `Itemhppotion`=? WHERE `id`=?", (long)item.getItemhppotion());
        item.getLastStatus().updateItemhppotion();
    }
    
    private void executeUpdate(int objId, String sql, long updateNum) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(sql.toString());
            pstm.setLong(1, updateNum);
            pstm.setInt(2, objId);
            pstm.execute();
        }
        catch (SQLException e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private static void executeUpdate(int objId, String sql, Timestamp ts) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(sql.toString());
            pstm.setTimestamp(1, ts);
            pstm.setInt(2, objId);
            pstm.execute();
        }
        catch (SQLException e) {
            CharItemsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public int checkItemId(int itemId) {
        int counter = 0;
        for (CopyOnWriteArrayList<L1ItemInstance> object44 : CharItemsTable._itemList.values()) {
            CopyOnWriteArrayList<L1ItemInstance> list = object44;
            for (L1ItemInstance item : list) {
                if (item.getItemId() == itemId) {
                    ++counter;
                }
            }
        }
        return counter;
    }
}
