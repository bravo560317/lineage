package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.collections.Maps;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import com.lineage.william.L1PowerItem;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class PowerItemTable
{
    private static final Log _log;
    private static final ArrayList<L1PowerItem> etcitem;
    private static final Map<Integer, L1PowerItem> mapetcitem;
    private static PowerItemTable _instance;
    
    static {
        _log = LogFactory.getLog(PowerItemTable.class);
        etcitem = new ArrayList();
        mapetcitem = Maps.newHashMap();
    }
    
    public static PowerItemTable get() {
        if (PowerItemTable._instance == null) {
            PowerItemTable._instance = new PowerItemTable();
        }
        return PowerItemTable._instance;
    }
    
    private PowerItemTable() {
        this.getList();
    }
    
    public L1PowerItem getItem(int itemid) {
        return PowerItemTable.mapetcitem.get((Integer)itemid);
    }
    
    public ArrayList<L1PowerItem> getAllList() {
        return PowerItemTable.etcitem;
    }
    
    private void getList() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_道具打洞能力水晶");
            rs = pstm.executeQuery();
            PowerItemTable.mapetcitem.put(0, new L1PowerItem());
            while (rs.next()) {
                L1PowerItem value = new L1PowerItem();
                value.itemid = rs.getInt("itemid");
                value.powercount = rs.getInt("powercount");
                value.probability_unequi = rs.getInt("probability_unequi");
                value.unequipment = rs.getBoolean("unequipment");
                value.probability_polyid = rs.getInt("probability_polyid");
                value.polyid = rs.getInt("polyid");
                value.polyid_time = rs.getInt("polyid_time");
                value.type = rs.getString("type");
                value.powername = rs.getString("powername");
                value.probability = rs.getInt("probability");
                value.skill_id = rs.getInt("skill_id");
                value.target_to = rs.getInt("target_to");
                value.addMaxHP = rs.getInt("addMaxHP");
                value.addMaxMP = rs.getInt("addMaxMP");
                value.add_str = rs.getInt("add_str");
                value.add_con = rs.getInt("add_con");
                value.add_dex = rs.getInt("add_dex");
                value.add_int = rs.getInt("add_int");
                value.add_wis = rs.getInt("add_wis");
                value.add_cha = rs.getInt("add_cha");
                value.add_hpr = rs.getInt("add_hpr");
                value.add_mpr = rs.getInt("add_mpr");
                value.add_sp = rs.getInt("add_sp");
                value.hit_modifier = rs.getInt("hit_modifier");
                value.dmg_modifier = rs.getInt("dmg_modifier");
                value.bow_hit_modifier = rs.getInt("bow_hit_modifier");
                value.bow_dmg_modifier = rs.getInt("bow_dmg_modifier");
                value.add_ac = rs.getInt("add_ac");
                value.m_def = rs.getInt("m_def");
                value.double_dmg_chance = rs.getInt("double_dmg_chance");
                value.addDamageReductionByArmor = rs.getInt("addDamageReductionByArmor");
                value.gif = rs.getInt("gif");
                PowerItemTable.etcitem.add(value);
                PowerItemTable.mapetcitem.put(value.itemid, value);
            }
        }
        catch (SQLException e) {
            PowerItemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
