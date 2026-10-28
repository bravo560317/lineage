package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.data.item_armor.set.ArmorSet;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ArmorSets;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class ArmorSetTable
{
    private static final Log _log;
    private static ArmorSetTable _instance;
    private static final ArrayList<L1ArmorSets> _armorSetList;
    
    static {
        _log = LogFactory.getLog(ArmorSetTable.class);
        _armorSetList = new ArrayList();
    }
    
    public static ArmorSetTable get() {
        if (ArmorSetTable._instance == null) {
            ArmorSetTable._instance = new ArmorSetTable();
        }
        return ArmorSetTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `armor_set`");
            rs = pstm.executeQuery();
            this.fillTable(rs);
        }
        catch (SQLException e) {
            ArmorSetTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ArmorSetTable._log.info("載入套裝設置數量: " + ArmorSetTable._armorSetList.size() + "(" + timer.get() + "ms)");
        ArmorSet.load();
    }
    
    private void fillTable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            L1ArmorSets as = new L1ArmorSets();
            as.setId(rs.getInt("id"));
            as.setSets(rs.getString("sets"));
            as.setPolyId(rs.getInt("polyid"));
            as.setAc(rs.getInt("ac"));
            as.setHp(rs.getInt("hp"));
            as.setMp(rs.getInt("mp"));
            as.setHpr(rs.getInt("hpr"));
            as.setMpr(rs.getInt("mpr"));
            as.setMr(rs.getInt("mr"));
            as.setStr(rs.getInt("str"));
            as.setDex(rs.getInt("dex"));
            as.setCon(rs.getInt("con"));
            as.setWis(rs.getInt("wis"));
            as.setCha(rs.getInt("cha"));
            as.setIntl(rs.getInt("intl"));
            as.setDefenseWater(rs.getInt("defense_water"));
            as.setDefenseWind(rs.getInt("defense_wind"));
            as.setDefenseFire(rs.getInt("defense_fire"));
            as.setDefenseEarth(rs.getInt("defense_earth"));
            as.set_regist_stun(rs.getInt("regist_stun"));
            as.set_regist_stone(rs.getInt("regist_stone"));
            as.set_regist_sleep(rs.getInt("regist_sleep"));
            as.set_regist_freeze(rs.getInt("regist_freeze"));
            as.set_regist_sustain(rs.getInt("regist_sustain"));
            as.set_regist_blind(rs.getInt("regist_blind"));
            as.set_modifier_dmg(rs.getInt("modifier_dmg"));
            as.set_reduction_dmg(rs.getInt("reduction_dmg"));
            as.set_magic_modifier_dmg(rs.getInt("magic_modifier_dmg"));
            as.set_magic_reduction_dmg(rs.getInt("magic_reduction_dmg"));
            as.set_bow_modifier_dmg(rs.getInt("bow_modifier_dmg"));
            as.set_haste(rs.getInt("haste"));
            as.set_sp(rs.getInt("sp"));
            as.set_hit_modifier(rs.getInt("hit_modifier"));
            as.set_bow_hit_modifier(rs.getInt("bow_hit_modifier"));
            as.set_magiccritical_chance(rs.getInt("magiccritical_chance"));
            as.setEffectId(rs.getInt("時裝特效"));
            as.setEffectId1(rs.getInt("特效編號"));
            as.setInterval1(rs.getInt("特效時間"));
            as.setweaponskillpro(rs.getInt("魔武發動率"));
            as.setweaponskilldmg(rs.getInt("魔武增傷害"));
            ArmorSetTable._armorSetList.add(as);
        }
    }
    
    public L1ArmorSets[] getAllList() {
        return (L1ArmorSets[])ArmorSetTable._armorSetList.toArray(new L1ArmorSets[ArmorSetTable._armorSetList.size()]);
    }
}
