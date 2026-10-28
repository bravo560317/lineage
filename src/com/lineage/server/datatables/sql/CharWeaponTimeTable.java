package com.lineage.server.datatables.sql;

import com.lineage.server.templates.L1MagicWeapon;
import com.lineage.server.model.L1Object;
import com.lineage.server.datatables.ExtraMagicWeaponTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.World;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharWeaponStorage;

public class CharWeaponTimeTable implements CharWeaponStorage
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
            ps = cn.prepareStatement("SELECT * FROM `character_weapon_time`");
            rs = ps.executeQuery();
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            while (rs.next()) {
                int itemr_obj_id = rs.getInt("itemr_obj_id");
                Timestamp usertime = rs.getTimestamp("usertime");
                int magic_weapon = rs.getInt("magic_weapon");
                int steps = rs.getInt("steps");
                if (usertime != null && usertime.before(ts)) {
                    delete(itemr_obj_id);
                }
                else {
                    addValue(itemr_obj_id, usertime, magic_weapon, steps);
                    ++size;
                }
            }
        }
        catch (SQLException e) {
            CharWeaponTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharWeaponTimeTable._log.info("讀取->武器魔法使用期限資料數量: " + size + "(" + timer.get() + "ms)");
    }
    
public static int get_steps(int itemobjid) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int thesteps = -1;
        try {
            cn = DatabaseFactory.get().getConnection();
            
            // [關鍵優化] 直接在 SQL 語法中指定尋找這把武器，不要把整個資料庫讀出來！
            ps = cn.prepareStatement("SELECT `steps` FROM `character_weapon_time` WHERE `itemr_obj_id`=?");
            ps.setInt(1, itemobjid);
            rs = ps.executeQuery();
            
            // 只要找到這把武器，直接把階段讀出來
            if (rs.next()) {
                thesteps = rs.getInt("steps");
            }
        }
        catch (SQLException e) {
            CharWeaponTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        return thesteps;
    }
    
    private static void addValue(int itemr_obj_id, Timestamp usertime, int magic_weapon, int steps) {
        L1Object obj = World.get().findObject(itemr_obj_id);
        boolean isError = true;
        if (obj != null && obj instanceof L1ItemInstance) {
            L1ItemInstance item = (L1ItemInstance)obj;
            item.set_time(usertime);
            if (magic_weapon > 0) {
                L1MagicWeapon magicWeapon = ExtraMagicWeaponTable.getInstance().get(magic_weapon, steps);
                item.set_magic_weapon(magicWeapon);
            }
            isError = false;
        }
        if (isError) {
            delete(itemr_obj_id);
        }
    }
    
    public static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_weapon_time` WHERE `itemr_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            CharWeaponTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public void addTime(int itemr_obj_id, Timestamp usertime, int magic_weapon) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `character_weapon_time` SET `itemr_obj_id`=?,`usertime`=?,`magic_weapon`=?,`steps`=?");
            int i = 0;
            ps.setInt(++i, itemr_obj_id);
            ps.setTimestamp(++i, usertime);
            ps.setInt(++i, magic_weapon);
            ps.setInt(++i, 0);
            ps.execute();
        }
        catch (SQLException e) {
            CharWeaponTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    public static void addTimeUP(int itemr_obj_id, Timestamp usertime, int magic_weapon, int steps) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `character_weapon_time` SET `itemr_obj_id`=?,`usertime`=?,`magic_weapon`=?,`steps`=?");
            int i = 0;
            ps.setInt(++i, itemr_obj_id);
            ps.setTimestamp(++i, usertime);
            ps.setInt(++i, magic_weapon);
            ps.setInt(++i, steps);
            ps.execute();
        }
        catch (SQLException e) {
            CharWeaponTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
@Override
    public void updateTime(int itemr_obj_id, Timestamp usertime, int magic_weapon, int steps, int same) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            
            // 判斷是否需要更新時間 (如果傳入 null 則不更新 usertime)
            boolean isUpdateDate = (usertime != null); 

            StringBuilder sql = new StringBuilder("UPDATE `character_weapon_time` SET ");
            if (isUpdateDate) {
                sql.append("`usertime`=?, ");
            }
            sql.append("`magic_weapon`=?, `steps`=? WHERE `itemr_obj_id`=?");

            ps = co.prepareStatement(sql.toString());

            int i = 0;
            if (isUpdateDate) {
                ps.setTimestamp(++i, usertime);
            }
            
            ps.setInt(++i, magic_weapon);
            
            if (same == 0) {
                ps.setInt(++i, 0);
            } else {
                ps.setInt(++i, steps);
            }
            
            ps.setInt(++i, itemr_obj_id);
            ps.execute();
        }
        catch (SQLException e) {
            CharWeaponTimeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
	
}

