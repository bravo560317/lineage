package com.lineage.server.datatables;

import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import com.lineage.server.templates.L1Weapon;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class WeaponPowerTable
{
    private static final Log _log;
    private static WeaponPowerTable _instance;
    private static final Map<Integer, int[]> _weaponPower;
    
    static {
        _log = LogFactory.getLog(WeaponPowerTable.class);
        _weaponPower = new HashMap();
    }
    
    public static WeaponPowerTable get() {
        if (WeaponPowerTable._instance == null) {
            WeaponPowerTable._instance = new WeaponPowerTable();
        }
        return WeaponPowerTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `weapon_power`");
            rs = pstm.executeQuery();
            this.fillWeaponSkillTable(rs);
        }
        catch (SQLException e) {
            WeaponPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        this.set_weapon_power();
        WeaponPowerTable._log.info("載入武器額外傷害資料數量: " + WeaponPowerTable._weaponPower.size() + "(" + timer.get() + "ms)");
    }
    
    private void set_weapon_power() {
        try {
            for (Integer key : WeaponPowerTable._weaponPower.keySet()) {
                L1Item item = ItemTable.get().getTemplate(key);
                if (item instanceof L1Weapon) {
                    int[] power = WeaponPowerTable._weaponPower.get(key);
                    L1Weapon weapon = (L1Weapon)item;
                    weapon.set_add_dmg(power[0], power[1]);
                }
                else {
                    WeaponPowerTable._log.error("武器額外傷害資料錯誤: 這個編號不是武器:" + key);
                    delete(key);
                }
            }
        }
        catch (Exception e) {
            WeaponPowerTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void fillWeaponSkillTable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int weapon_id = rs.getInt("weapon_id");
            if (ItemTable.get().getTemplate(weapon_id) == null) {
                WeaponPowerTable._log.error("武器額外傷害資料錯誤: 沒有這個編號的道具:" + weapon_id);
                delete(weapon_id);
            }
            else {
                int add_dmg_min = rs.getInt("add_dmg_min");
                int add_dmg_max = rs.getInt("add_dmg_max");
                if (add_dmg_min >= add_dmg_max) {
                    WeaponPowerTable._log.error("武器額外傷害資料錯誤: 傷害質設置異常:" + weapon_id);
                    delete(weapon_id);
                }
                else {
                    WeaponPowerTable._weaponPower.put(weapon_id, new int[] { add_dmg_min, add_dmg_max });
                }
            }
        }
    }
    
    private static void delete(int weapon_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `weapon_power` WHERE `weapon_id`=?");
            ps.setInt(1, weapon_id);
            ps.execute();
        }
        catch (SQLException e) {
            WeaponPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public int[] getTemplate(int weaponId) {
        return WeaponPowerTable._weaponPower.get((Integer)weaponId);
    }
}
