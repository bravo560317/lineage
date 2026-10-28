package com.lineage.server.datatables;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.WorldItem;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.sql.DwarfForClanTable;
import com.lineage.server.datatables.sql.DwarfTable;
import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class ItemSpecialAttributeCharTable
{
    private static final Log _log;
    private static ItemSpecialAttributeCharTable _instance;
    private static final HashMap<Integer, L1ItemSpecialAttributeChar> _AtrrCharList;
    
    static {
        _log = LogFactory.getLog(ItemSpecialAttributeCharTable.class);
        _AtrrCharList = new HashMap();
    }
    
    public static ItemSpecialAttributeCharTable get() {
        if (ItemSpecialAttributeCharTable._instance == null) {
            ItemSpecialAttributeCharTable._instance = new ItemSpecialAttributeCharTable();
        }
        return ItemSpecialAttributeCharTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i = 0;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `炫色二_記錄資料`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int itemobjid = rs.getInt("玩家流水號");
                if (!CharItemsTable.get().getUserItem(itemobjid) && !DwarfTable.get().getUserItem(itemobjid) && !DwarfForClanTable.get().getUserItem(itemobjid)) {
                    errorItem1(itemobjid);
                }
                String item_name = rs.getString("炫色武防名稱");
                int attr_id = rs.getInt("炫色代碼");
                String add_pc_name = rs.getString("角色名稱");
                Timestamp add_time = rs.getTimestamp("時間");
                String add_mon_name = rs.getString("使用方式");
                String mapname = rs.getString("地圖方式");
                String Acquisition_mode = rs.getString("洗白方式");
                L1ItemSpecialAttributeChar attr_char = new L1ItemSpecialAttributeChar();
                attr_char.set_itemobjid(itemobjid);
                attr_char.set_item_name(item_name);
                attr_char.set_attr_id(attr_id);
                attr_char.set_add_pc_name(add_pc_name);
                attr_char.set_add_time(add_time);
                attr_char.set_add_mon_name(add_mon_name);
                attr_char.set_mapname(mapname);
                attr_char.set_Acquisition_mode(Acquisition_mode);
                addValue(itemobjid, attr_char);
                ++i;
                ItemSpecialAttributeCharTable._AtrrCharList.put(itemobjid, attr_char);
            }
        }
        catch (SQLException e) {
            ItemSpecialAttributeCharTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        ItemSpecialAttributeCharTable._log.info("讀取->人物物品特殊屬性數量: " + i + "(" + timer.get() + "ms)");
    }
    
    private static void errorItem1(int objid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `炫色二_記錄資料` WHERE `玩家流水號`=?");
            pstm.setInt(1, objid);
            pstm.execute();
        }
        catch (SQLException e) {
            ItemSpecialAttributeCharTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public L1ItemSpecialAttributeChar getAttrId(int itemobjid) {
        return ItemSpecialAttributeCharTable._AtrrCharList.get((Integer)itemobjid);
    }
    
    private static void addValue(int item_obj_id, L1ItemSpecialAttributeChar ItemAttr) {
        L1ItemInstance item = WorldItem.get().getItem(item_obj_id);
        if (item != null && item.get_ItemAttrName() == null) {
            item.set_ItemAttrName(ItemAttr);
        }
    }
    
    public void storeItem(int itemobjid, L1ItemSpecialAttributeChar ItemAttr) throws Exception {
        if (ItemSpecialAttributeCharTable._AtrrCharList.get((Integer)itemobjid) != null) {
            return;
        }
        else {
            ItemSpecialAttributeCharTable._AtrrCharList.put(itemobjid, ItemAttr);
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("INSERT INTO `炫色二_記錄資料` SET `玩家流水號`=?,`炫色武防名稱`=?,`炫色代碼`=?,`角色名稱`=?,`時間`=?,`使用方式`=?,`地圖方式`=?,`洗白方式`=?");
                int i = 0;
                pstm.setInt(++i, itemobjid);
                pstm.setString(++i, ItemAttr.get_item_name());
                pstm.setInt(++i, ItemAttr.get_attr_id());
                pstm.setString(++i, ItemAttr.get_add_pc_name());
                pstm.setTimestamp(++i, ItemAttr.get_add_time());
                pstm.setString(++i, ItemAttr.get_add_mon_name());
                pstm.setString(++i, ItemAttr.get_mapname());
                pstm.setString(++i, ItemAttr.get_Acquisition_mode());
                pstm.execute();
            }
            catch (SQLException e) {
                ItemSpecialAttributeCharTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
            return;
        }
    }
    
    public void updateItem(int itemobjid, L1ItemSpecialAttributeChar ItemAttr) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("UPDATE `炫色二_記錄資料` SET `炫色武防名稱`=?,`炫色代碼`=?,`角色名稱`=?,`時間`=?,`使用方式`=?,`地圖方式`=?,`洗白方式`=? WHERE `玩家流水號`=?");
            int i = 0;
            pm.setString(++i, ItemAttr.get_item_name());
            pm.setInt(++i, ItemAttr.get_attr_id());
            pm.setString(++i, ItemAttr.get_add_pc_name());
            pm.setTimestamp(++i, ItemAttr.get_add_time());
            pm.setString(++i, ItemAttr.get_add_mon_name());
            pm.setString(++i, ItemAttr.get_mapname());
            pm.setString(++i, ItemAttr.get_Acquisition_mode());
            pm.setInt(++i, itemobjid);
            pm.execute();
        }
        catch (SQLException e) {
            ItemSpecialAttributeCharTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
}
