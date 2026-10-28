package com.lineage.server.datatables.sql;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.WorldItem;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ItemPower_name;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharItemsPowerStorage;

public class CharItemsPowerTable implements CharItemsPowerStorage
{
    private static final Log _log;
    private static final Map<Integer, L1ItemPower_name> _powerMap;
    
    static {
        _log = LogFactory.getLog(BuddyTable.class);
        _powerMap = new HashMap();
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
            ps = cn.prepareStatement("SELECT * FROM `character_item_power`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int item_obj_id = rs.getInt("item_obj_id");
                int hole_count = rs.getInt("hole_count");
                int hole_1 = rs.getInt("hole_1");
                int hole_2 = rs.getInt("hole_2");
                int hole_3 = rs.getInt("hole_3");
                int hole_4 = rs.getInt("hole_4");
                int hole_5 = rs.getInt("hole_5");
                L1ItemPower_name power = new L1ItemPower_name();
                power.set_item_obj_id(item_obj_id);
                power.set_hole_count(hole_count);
                power.set_hole_1(hole_1);
                power.set_hole_2(hole_2);
                power.set_hole_3(hole_3);
                power.set_hole_4(hole_4);
                power.set_hole_5(hole_5);
                addValue(item_obj_id, power);
                ++i;
            }
        }
        catch (SQLException e) {
            CharItemsPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharItemsPowerTable._log.info("載入物件凹槽清單資料數量: " + i + "(" + timer.get() + "ms)");
    }
    
    private static void addValue(int item_obj_id, L1ItemPower_name power) {
        L1ItemInstance item = WorldItem.get().getItem(item_obj_id);
        boolean isError = true;
        if (item != null) {
            if (item.get_power_name() == null) {
                item.set_power_name(power);
            }
            CharItemsPowerTable._powerMap.put(item_obj_id, power);
            isError = false;
        }
        if (isError) {
            delItem(item_obj_id);
        }
    }
    
    private static void errorItem(int item_obj_id) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `character_item_power` WHERE `item_obj_id`=?");
            pstm.setInt(1, item_obj_id);
            pstm.execute();
        }
        catch (SQLException e) {
            CharItemsPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public static void delItem(int item_obj_id) {
        if (CharItemsPowerTable._powerMap.get((Integer)item_obj_id) != null) {
            CharItemsPowerTable._powerMap.remove(new Integer(item_obj_id));
        }
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `character_item_power` WHERE `item_obj_id`=?");
            pstm.setInt(1, item_obj_id);
            pstm.execute();
        }
        catch (ArrayIndexOutOfBoundsException ex) {}
        catch (SQLException e) {
            CharItemsPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void storeItem(int item_obj_id, L1ItemPower_name power) throws Exception {
        if (CharItemsPowerTable._powerMap.get((Integer)item_obj_id) != null) {
            return;
        }
        else {
            CharItemsPowerTable._powerMap.put(item_obj_id, power);
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("INSERT INTO `character_item_power` SET `item_obj_id`=?,`hole_count`=?,`hole_1`=?,`hole_2`=?,`hole_3`=?,`hole_4`=?,`hole_5`=?");
                int i = 0;
                pstm.setInt(++i, item_obj_id);
                pstm.setInt(++i, power.get_hole_count());
                pstm.setInt(++i, power.get_hole_1());
                pstm.setInt(++i, power.get_hole_2());
                pstm.setInt(++i, power.get_hole_3());
                pstm.setInt(++i, power.get_hole_4());
                pstm.setInt(++i, power.get_hole_5());
                pstm.execute();
            }
            catch (SQLException e) {
                CharItemsPowerTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
            return;
        }
    }
    
    @Override
    public void updateItem(int item_obj_id, L1ItemPower_name power) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("UPDATE `character_item_power` SET `hole_count`=?,`hole_1`=?,`hole_2`=?,`hole_3`=?,`hole_4`=?,`hole_5`=? WHERE `item_obj_id`=?");
            int i = 0;
            pm.setInt(++i, power.get_hole_count());
            pm.setInt(++i, power.get_hole_1());
            pm.setInt(++i, power.get_hole_2());
            pm.setInt(++i, power.get_hole_3());
            pm.setInt(++i, power.get_hole_4());
            pm.setInt(++i, power.get_hole_5());
            pm.setInt(++i, item_obj_id);
            pm.execute();
        }
        catch (SQLException e) {
            CharItemsPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    public Map<Integer, L1ItemPower_name> getPowerMap() {
        return CharItemsPowerTable._powerMap;
    }
}
