package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.LinkedHashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1AttrWeapon;
import java.util.Map;
import org.apache.commons.logging.Log;

public final class ExtraAttrWeaponTable {
	  private static final Log _log = LogFactory.getLog(ExtraAttrWeaponTable.class);
	  
	  private static final Map<Integer, L1AttrWeapon> _attrList = new LinkedHashMap<>();
	  
	  private static ExtraAttrWeaponTable _instance;
	  
	  public static ExtraAttrWeaponTable getInstance() {
	    if (_instance == null)
	      _instance = new ExtraAttrWeaponTable(); 
	    return _instance;
	  }
    
    public final void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("SELECT * FROM w_屬性卷軸設置 ORDER BY id");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int stage = rs.getInt("stage");
                int chance = rs.getInt("chance");
                int probability = rs.getInt("probability");
                int type = rs.getInt("type");
                int fix_damage = rs.getInt("fix_damage");
                int random_damage = rs.getInt("random_damage");
                int c_hp = rs.getInt("固定吸血");
                int c_mp = rs.getInt("固定吸魔");
                double type_bind = rs.getDouble("type_bind");
                double type_drain_hp = rs.getDouble("type_drain_hp");
                int type_drain_mp = rs.getInt("type_drain_mp");
                double type_dmgup = rs.getDouble("type_dmgup");
                int type_range = rs.getInt("type_range");
                int type_range_dmg = rs.getInt("type_range_dmg");
                int type_light_dmg = rs.getInt("type_light_dmg");
                boolean type_skill_1 = rs.getBoolean("type_skill_1");
                boolean type_skill_2 = rs.getBoolean("type_skill_2");
                boolean type_skill_3 = rs.getBoolean("type_skill_3");
                boolean type_skill_4 = rs.getBoolean("衝擊之暈");
                double type_skill_time = rs.getDouble("type_skill_time");
                String temp_poly_list = rs.getString("type_poly_list");
                String[] type_poly_list = (String[])null;
                if (temp_poly_list != null && !temp_poly_list.isEmpty()) 
                    type_poly_list = temp_poly_list.replace(" ", "").split(",");              
                boolean type_remove_weapon = rs.getBoolean("type_remove_weapon");
                boolean type_remove_doll = rs.getBoolean("type_remove_doll");
                int type_remove_armor = rs.getInt("type_remove_armor");
                int gfixd = rs.getInt("gfixd");
                String Message = rs.getString("世界廣播");
                L1AttrWeapon attrWeapon = new L1AttrWeapon(name, stage, chance, probability, type, fix_damage, random_damage, c_hp, c_mp, type_bind, type_drain_hp, type_drain_mp, type_dmgup, type_range, type_range_dmg, type_light_dmg, type_skill_1, type_skill_2, type_skill_3, type_skill_4, type_skill_time, type_poly_list, type_remove_weapon, type_remove_doll, type_remove_armor, gfixd, Message);
                int index = id * 100 + stage;
                ExtraAttrWeaponTable._attrList.put(index, attrWeapon);
            }
        }
        catch (SQLException e) {
            ExtraAttrWeaponTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
        ExtraAttrWeaponTable._log.info("載入屬性武器資料數量: " + ExtraAttrWeaponTable._attrList.size() + "(" + timer.get() + "ms)");
    }
    
    public final L1AttrWeapon get(int id, int stage) {
        int index = id * 100 + stage;
        return ExtraAttrWeaponTable._attrList.get((Integer)index);
    }
}
