package com.lineage.william;

import com.lineage.server.utils.PerformanceTimer;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import java.util.logging.Logger;

public class EnchantOrginal
{
    private static Logger _log;
    private static final Log _logx;
    private static EnchantOrginal _instance;
    private final HashMap<Integer, L1WilliamEnchantOrginal> _ArmorIndex;
    
    static {
        EnchantOrginal._log = Logger.getLogger(EnchantOrginal.class.getName());
        _logx = LogFactory.getLog(EnchantOrginal.class.getName());
    }
    
    public static EnchantOrginal getInstance() {
        if (EnchantOrginal._instance == null) {
            EnchantOrginal._instance = new EnchantOrginal();
        }
        return EnchantOrginal._instance;
    }
    
    private EnchantOrginal() {
        this._ArmorIndex = new HashMap();
        this.loadArmorOrginal();
    }
    
    private void loadArmorOrginal() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_裝武強化Lv");
            rs = pstm.executeQuery();
            this.fillWeaponSkill(rs);
        }
        catch (SQLException e) {
            EnchantOrginal._log.log(Level.SEVERE, "error while creating w_裝武強化Lv", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillWeaponSkill(ResultSet rs) throws SQLException {
        PerformanceTimer timer = new PerformanceTimer();
        int id = 0;
        while (rs.next()) {
            int type = rs.getInt("type");
            int itemid = rs.getInt("itemid");
            int level = rs.getInt("level");
            byte addStr = rs.getByte("addStr");
            byte addDex = rs.getByte("addDex");
            byte addCon = rs.getByte("addCon");
            byte addInt = rs.getByte("addInt");
            byte addWis = rs.getByte("addWis");
            byte addCha = rs.getByte("addCha");
            int addAc = rs.getInt("addAc");
            int addMaxHp = rs.getInt("addMaxHp");
            int addMaxMp = rs.getInt("addMaxMp");
            int addHpr = rs.getInt("addHpr");
            int addMpr = rs.getInt("addMpr");
            int addDmg = rs.getInt("addDmg");
            int addBowDmg = rs.getInt("addBowDmg");
            int addHit = rs.getInt("addHit");
            int addBowHit = rs.getInt("addBowHit");
            int addDmgReduction = rs.getInt("addDmgReduction");
            int addMr = rs.getInt("addMr");
            int addSp = rs.getInt("addSp");
            int PVPdmg = rs.getInt("PVPdmg");
            int PVPdmgReduction = rs.getInt("PVPdmgReduction");
            int Potion_Heal = rs.getInt("Potion_Heal");
            int Potion_Healling = rs.getInt("Potion_Healling");
            int magic_hit = rs.getInt("add_magic_hit");
            double weaponSkillDmg = rs.getDouble("weaponSkillDmg");
            int weaponSkillChance = rs.getInt("weaponSkillChance");
            L1WilliamEnchantOrginal ArmorOrginal = new L1WilliamEnchantOrginal(id, type, itemid, level, addStr, addDex, addCon, addInt, addWis, addCha, addAc, addMaxHp, addMaxMp, addHpr, addMpr, addDmg, addBowDmg, addHit, addBowHit, addDmgReduction, addMr, addSp, PVPdmg, PVPdmgReduction, Potion_Heal, Potion_Healling, magic_hit, weaponSkillDmg, weaponSkillChance);
            this._ArmorIndex.put(id, ArmorOrginal);
            ++id;
        }
        EnchantOrginal._logx.info("載入w_武防等級數據資料數量: " + this._ArmorIndex.size() + "(" + timer.get() + "ms)");
    }
    
    public L1WilliamEnchantOrginal getTemplate(int Armor) {
        return this._ArmorIndex.get((Integer)Armor);
    }
    
    public L1WilliamEnchantOrginal[] getArmorList() {
        return (L1WilliamEnchantOrginal[])this._ArmorIndex.values().toArray(new L1WilliamEnchantOrginal[this._ArmorIndex.size()]);
    }
}
