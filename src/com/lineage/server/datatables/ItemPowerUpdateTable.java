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
import java.util.ArrayList;
import com.lineage.server.templates.L1ItemPowerUpdate;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ItemPowerUpdateTable
{
    private static final Log _log;
    private static Map<Integer, L1ItemPowerUpdate> _updateMap;
    private static final ArrayList<Integer> _updateitemidList;
    private static ItemPowerUpdateTable _instance;
    
    static {
        _log = LogFactory.getLog(ItemPowerUpdateTable.class);
        ItemPowerUpdateTable._updateMap = new HashMap();
        _updateitemidList = new ArrayList();
    }
    
    public static ItemPowerUpdateTable get() {
        if (ItemPowerUpdateTable._instance == null) {
            ItemPowerUpdateTable._instance = new ItemPowerUpdateTable();
        }
        return ItemPowerUpdateTable._instance;
    }
    
    public static void reload() {
        ItemPowerUpdateTable._instance = new ItemPowerUpdateTable();
        ItemPowerUpdateTable._updateMap.clear();
        ItemPowerUpdateTable._instance.load();
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_item_power_update`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                if (ItemTable.get().getTemplate(itemid) == null) {
                    ItemPowerUpdateTable._log.error("特殊物品升級資料錯誤: 沒有這個編號的道具:" + itemid);
                    delete(itemid);
                }
                else {
                    int nedid = rs.getInt("nedid");
                    int type_id = rs.getInt("type_id");
                    int order_id = rs.getInt("order_id");
                    int mode = rs.getInt("mode");
                    int random = rs.getInt("random");
                    String allmsg = rs.getString("世界廣播");
                    String note = rs.getString("note");
                    if (!note.contains("=>")) {
                        updata_name(itemid);
                    }
                    L1ItemPowerUpdate value = ItemPowerUpdateTable._updateMap.get((Integer)itemid);
                    value = new L1ItemPowerUpdate();
                    value.set_itemid(itemid);
                    value.set_nedid(nedid);
                    value.set_type_id(type_id);
                    value.set_order_id(order_id);
                    value.set_mode(mode);
                    value.set_random(random);
                    value.setallmsg(allmsg);
                    ItemPowerUpdateTable._updateMap.put(itemid, value);
                    ItemPowerUpdateTable._updateitemidList.add(itemid);
                }
            }
        }
        catch (SQLException e) {
            ItemPowerUpdateTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemPowerUpdateTable._log.info("載入物品升級資料數量: " + ItemPowerUpdateTable._updateMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void updata_name(int itemId) {
        Connection cn = null;
        PreparedStatement ps = null;
        String itemname = ItemTable.get().getTemplate(itemId).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `server_item_power_update` SET `note`=? WHERE `itemid`=?");
            int i = 0;
            ++i;
            ps.setString(i, "被升級的名稱->" + itemname);
            ++i;
            ps.setInt(i, itemId);
            ps.execute();
        }
        catch (SQLException e) {
            ItemPowerUpdateTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public int get_original_type(int itemid) {
        int type_id = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_item_power_update` WHERE `itemid` =?");
            pstm.setInt(1, itemid);
            rs = pstm.executeQuery();
            while (rs.next()) {
                type_id = rs.getInt("type_id");
            }
        }
        catch (SQLException e) {
            ItemPowerUpdateTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return type_id;
    }
    
    public int get_original_itemid(int typeid) {
        int itemid = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_item_power_update` WHERE `type_id` =? AND `order_id` = '0'");
            pstm.setInt(1, typeid);
            rs = pstm.executeQuery();
            while (rs.next()) {
                itemid = rs.getInt("itemid");
            }
        }
        catch (SQLException e) {
            ItemPowerUpdateTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return itemid;
    }
    
    public ArrayList<Integer> get_updeatitemidlist() {
        return ItemPowerUpdateTable._updateitemidList;
    }
    
    public static void delete(int itemid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `server_item_power_update` WHERE `itemid`=?");
            ps.setInt(1, itemid);
            ps.execute();
        }
        catch (SQLException e) {
            ItemPowerUpdateTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public Map<Integer, L1ItemPowerUpdate> get_type_updatemap(String key) {
        Map<Integer, L1ItemPowerUpdate> updateMap = new HashMap();
        L1ItemPowerUpdate tmp = ItemPowerUpdateTable._updateMap.get(key);
        if (tmp != null) {
            int type_id = tmp.get_type_id();
            for (L1ItemPowerUpdate value : ItemPowerUpdateTable._updateMap.values()) {
                if (value.get_type_id() == type_id) {
                    updateMap.put(value.get_order_id(), value);
                }
            }
        }
        return updateMap;
    }
    
    public L1ItemPowerUpdate get(int key) {
        return ItemPowerUpdateTable._updateMap.get((Integer)key);
    }
    
    public L1ItemPowerUpdate gettemplate(String key) {
        return ItemPowerUpdateTable._updateMap.get(key);
    }
    
    public Map<Integer, L1ItemPowerUpdate> map() {
        return ItemPowerUpdateTable._updateMap;
    }
    
    public Map<Integer, L1ItemPowerUpdate> get_type_id(int itemid) {
        Map<Integer, L1ItemPowerUpdate> updateMap = new HashMap();
        L1ItemPowerUpdate tmp = ItemPowerUpdateTable._updateMap.get((Integer)itemid);
        if (tmp != null) {
            int type_id = tmp.get_type_id();
            for (L1ItemPowerUpdate value : ItemPowerUpdateTable._updateMap.values()) {
                if (value.get_type_id() == type_id) {
                    updateMap.put(value.get_order_id(), value);
                }
            }
        }
        return updateMap;
    }
}
