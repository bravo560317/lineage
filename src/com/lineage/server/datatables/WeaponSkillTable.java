package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1WeaponSkill;
import java.util.Map;
import org.apache.commons.logging.Log;

public class WeaponSkillTable
{
    private static final Log _log;
    private static WeaponSkillTable _instance;
    private static final Map<Integer, L1WeaponSkill> _weaponIdIndex;
    
    static {
        _log = LogFactory.getLog(WeaponSkillTable.class);
        _weaponIdIndex = new HashMap();
    }
    
    public static WeaponSkillTable get() {
        if (WeaponSkillTable._instance == null) {
            WeaponSkillTable._instance = new WeaponSkillTable();
        }
        return WeaponSkillTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `weapon_skill`");
            rs = pstm.executeQuery();
            this.fillWeaponSkillTable(rs);
        }
        catch (SQLException e) {
            WeaponSkillTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        WeaponSkillTable._log.info("載入技能武器資料數量: " + WeaponSkillTable._weaponIdIndex.size() + "(" + timer.get() + "ms)");
    }
    
    private void fillWeaponSkillTable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int weaponId = rs.getInt("weapon_id");
            int probability = rs.getInt("probability");
            int fixDamage = rs.getInt("fix_damage");
            int randomDamage = rs.getInt("random_damage");
            int area = rs.getInt("area");
            int skillId = rs.getInt("skill_id");
            int skillTime = rs.getInt("skill_time");
            int effectId = rs.getInt("顯示特效");
            int effectTarget = rs.getInt("effect_target");
            boolean isArrowType = rs.getBoolean("arrow_type");
            int attr = rs.getInt("attr");
            int gfxId = rs.getInt("顯示特效_1");
            int gfxId2 = rs.getInt("顯示特效_2");
            int gfxId3 = rs.getInt("顯示特效_3");
            int gfxId4 = rs.getInt("顯示特效_4");
            int gfxId5 = rs.getInt("顯示特效_5");
            double x_str = rs.getDouble("x_str");
            double x_dex = rs.getDouble("x_dex");
            double x_int = rs.getDouble("x_int");
            double x_sp = rs.getDouble("x_sp");
            int c_hp = rs.getInt("c_hp");
            int c_mp = rs.getInt("c_mp");
            int weapon_lv = rs.getInt("weapon_lv");
            int weapon_addpro = rs.getInt("weapon_addpro");
            int COUNTER_MAGIC = rs.getInt("是否會被魔屏影響");
            L1WeaponSkill weaponSkill = new L1WeaponSkill(weaponId, probability, fixDamage, randomDamage, area, skillId, skillTime, effectId, effectTarget, isArrowType, attr, gfxId, gfxId2, gfxId3, gfxId4, gfxId5, c_hp, c_mp, x_str, x_dex, x_int, x_sp, weapon_lv, weapon_addpro, COUNTER_MAGIC);
            WeaponSkillTable._weaponIdIndex.put(weaponId, weaponSkill);
        }
    }
    
    public L1WeaponSkill getTemplate(int weaponId) {
        return WeaponSkillTable._weaponIdIndex.get((Integer)weaponId);
    }
}
