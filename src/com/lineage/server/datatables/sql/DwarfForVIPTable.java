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
import com.lineage.server.datatables.ItemTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.List;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.DwarfForVIPStorage;

public class DwarfForVIPTable implements DwarfForVIPStorage
{
    private static final Log _log;
    public static final List<Integer> savepcid;
    public static final List<L1ItemInstance> saveweapon;
    public static final List<String> savename;
    private static final Map<String, CopyOnWriteArrayList<L1ItemInstance>> _itemList;
    
    static {
        _log = LogFactory.getLog(DwarfForVIPTable.class);
        savepcid = new ArrayList();
        saveweapon = new ArrayList();
        savename = new ArrayList();
        _itemList = new ConcurrentHashMap();
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
            ps = co.prepareStatement("SELECT * FROM `系統_裝備贖回`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int objid = rs.getInt("id");
                String char_name = rs.getString("char_name");
                int item_id = rs.getInt("item_id");
                long count = rs.getLong("count");
                int enchantlvl = rs.getInt("enchantlvl");
                int is_id = rs.getInt("is_id");
                int durability = rs.getInt("durability");
                int charge_count = rs.getInt("charge_count");
                int remaining_time = rs.getInt("remaining_time");
                Timestamp last_used = rs.getTimestamp("last_used");
                int bless = rs.getInt("bless");
                int attr_enchant_kind = rs.getInt("attr_enchant_kind");
                int attr_enchant_level = rs.getInt("attr_enchant_level");
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
                    addItem(char_name, item);
                    ++i;
                }
            }
        }
        catch (SQLException e) {
            DwarfForVIPTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        DwarfForVIPTable._log.info("載入角色物品贖回物件清單資料數量: " + DwarfForVIPTable._itemList.size() + "/" + i + "(" + timer.get() + "ms)");
    }
    
    private static void errorItem(int objid) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("DELETE FROM `系統_裝備贖回` WHERE `id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            DwarfForVIPTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    private static void addItem(String char_name, L1ItemInstance item) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfForVIPTable._itemList.get(char_name);
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
        DwarfForVIPTable._itemList.put(char_name, list);
    }
    
    private static void deleteItem(String char_name) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfForVIPTable._itemList.remove(char_name);
        if (list != null) {
            for (L1ItemInstance item : list) {
                World.get().removeObject(item);
            }
        }
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `系統_裝備贖回` WHERE `char_name`=?");
            ps.setString(1, char_name);
            ps.execute();
        }
        catch (SQLException e) {
            DwarfForVIPTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public Map<String, CopyOnWriteArrayList<L1ItemInstance>> allItems() {
        return DwarfForVIPTable._itemList;
    }
    
    @Override
    public CopyOnWriteArrayList<L1ItemInstance> loadItems(String char_name) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfForVIPTable._itemList.get(char_name);
        if (list != null) {
            return list;
        }
        else {
            return null;
        }
    }
    
    @Override
    public void delUserItems(String char_name) {
        deleteItem(char_name);
    }
    
    @Override
    public boolean getUserItems(String char_name, int objid, int count) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfForVIPTable._itemList.get(char_name);
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
    public void insertItem(String char_name, L1ItemInstance item) {
        DwarfForVIPTable._log.warn("玩家:" + char_name + " 加入角色物品贖回數據:" + item.getItem().getName() + " OBJID:" + item.getId());
        addItem(char_name, item);
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `系統_裝備贖回` SET `id`=?,`char_name`=?,`item_id`= ?,`item_name`=?,`count`=?,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?");
            int i = 0;
            ps.setInt(++i, item.getId());
            ps.setString(++i, char_name);
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
            ps.execute();
        }
        catch (SQLException e) {
            DwarfForVIPTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void updateItem(L1ItemInstance item) {
        DwarfForVIPTable._log.warn("更新角色物品贖回數據:" + item.getItem().getName() + " OBJID:" + item.getId() + " Count:" + item.getCount());
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseFactory.get().getConnection();
            ps = con.prepareStatement("UPDATE `系統_裝備贖回` SET `count`=? WHERE `id`=?");
            ps.setLong(1, item.getCount());
            ps.setInt(2, item.getId());
            ps.execute();
        }
        catch (SQLException e) {
            DwarfForVIPTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void deleteItem(String char_name, L1ItemInstance item) {
        CopyOnWriteArrayList<L1ItemInstance> list = DwarfForVIPTable._itemList.get(char_name);
        if (list != null) {
            DwarfForVIPTable._log.warn("玩家:" + char_name + " 角色物品贖回 :" + item.getItem().getName() + " OBJID:" + item.getId());
            list.remove(item);
            Connection co = null;
            PreparedStatement pstm = null;
            try {
                co = DatabaseFactory.get().getConnection();
                pstm = co.prepareStatement("DELETE FROM `系統_裝備贖回` WHERE `id`=?");
                pstm.setInt(1, item.getId());
                pstm.execute();
            }
            catch (SQLException e) {
                DwarfForVIPTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(co);
            }
        }
    }
}
