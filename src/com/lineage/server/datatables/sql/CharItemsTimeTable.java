package com.lineage.server.datatables.sql;

import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.WorldItem;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharItemsTimeStorage;

public class CharItemsTimeTable implements CharItemsTimeStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(CharBookTable.class);
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int size = 0;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_items_time`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int itemr_obj_id = rs.getInt("itemr_obj_id");
                Timestamp usertime = rs.getTimestamp("usertime");
                String note = rs.getString("note");
                addValue(itemr_obj_id, usertime);
                if (!note.contains("=>")) {
                    this.updata_name(itemr_obj_id);
                }
                ++size;
            }
        }
        catch (SQLException e) {
            CharItemsTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharItemsTimeTable._log.info("載入物品使用期限資料數量: " + size + "(" + timer.get() + "ms)");
    }
    
    private void updata_name(int itemr_obj_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        String Itemname = null;
        String Pcname = null;
        L1ItemInstance item = WorldItem.get().getItem(itemr_obj_id);
        if (item != null) {
            Itemname = item.getName();
            Pcname = CharacterTable.get().getCharName(item.get_char_objid());
        }
        else {
            delete(itemr_obj_id);
        }
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_items_time` SET `note`=? WHERE `itemr_obj_id`=?");
            int i = 0;
            ps.setString(++i, Pcname + "=>" + Itemname);
            ps.setInt(++i, itemr_obj_id);
            ps.execute();
        }
        catch (SQLException e) {
            CharItemsTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addValue(int item_obj_id, Timestamp usertime) {
        L1Object obj = World.get().findObject(item_obj_id);
        boolean isError = true;
        if (obj != null && obj instanceof L1ItemInstance) {
            L1ItemInstance item = (L1ItemInstance)obj;
            item.set_time(usertime);
            isError = false;
        }
        if (isError) {
            delete(item_obj_id);
        }
    }
    
    public static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_items_time` WHERE `itemr_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            CharItemsTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public void addTime(int item_obj_id, Timestamp usertime) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `character_items_time` SET `itemr_obj_id`=?,`usertime`=?");
            int i = 0;
            ++i;
            ps.setInt(i, item_obj_id);
            ++i;
            ps.setTimestamp(i, usertime);
            ps.execute();
        }
        catch (SQLException e) {
            CharItemsTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void updateTime(int item_obj_id, Timestamp usertime) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("UPDATE `character_items_time` SET `usertime`=? WHERE `itemr_obj_id`=?");
            int i = 0;
            ++i;
            ps.setTimestamp(i, usertime);
            ++i;
            ps.setInt(i, item_obj_id);
            ps.execute();
        }
        catch (SQLException e) {
            CharItemsTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public boolean isExistTimeData(int itemr_obj_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_items_time` WHERE `itemr_obj_id`=?");
            ps.setInt(1, itemr_obj_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int objid = rs.getInt("itemr_obj_id");
                if (objid == itemr_obj_id) {
                    return true;
                }
            }
        }
        catch (SQLException e) {
            CharItemsTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        return false;
    }
}
