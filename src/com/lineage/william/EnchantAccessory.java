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

public class EnchantAccessory
{
    private static Logger _log;
    private static final Log _logx;
    private static EnchantAccessory _instance;
    private final HashMap<Integer, L1WilliamEnchantAccessory> _ArmorIndex;
    
    static {
        EnchantAccessory._log = Logger.getLogger(EnchantAccessory.class.getName());
        _logx = LogFactory.getLog(EnchantAccessory.class.getName());
    }
    
    public static EnchantAccessory getInstance() {
        if (EnchantAccessory._instance == null) {
            EnchantAccessory._instance = new EnchantAccessory();
        }
        return EnchantAccessory._instance;
    }
    
    private EnchantAccessory() {
        this._ArmorIndex = new HashMap();
        this.loadArmorOrginal();
    }
    
    private void loadArmorOrginal() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_飾品等級");
            rs = pstm.executeQuery();
            this.fillWeaponSkill(rs);
        }
        catch (SQLException e) {
            EnchantAccessory._log.log(Level.SEVERE, "error while creating w_飾品等級 table", e);
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
            int strength = rs.getInt("strength");
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
            L1WilliamEnchantAccessory ArmorOrginal = new L1WilliamEnchantAccessory(id, type, strength, level, addStr, addDex, addCon, addInt, addWis, addCha, addAc, addMaxHp, addMaxMp, addHpr, addMpr, addDmg, addBowDmg, addHit, addBowHit, addDmgReduction, addMr, addSp, PVPdmg, PVPdmgReduction, Potion_Heal, Potion_Healling, magic_hit);
            this._ArmorIndex.put(id, ArmorOrginal);
            ++id;
        }
        EnchantAccessory._logx.info("載入w_飾品等級數據資料數量: " + this._ArmorIndex.size() + "(" + timer.get() + "ms)");
    }
    
    public L1WilliamEnchantAccessory getTemplate(int Armor) {
        return this._ArmorIndex.get((Integer)Armor);
    }
    
    public L1WilliamEnchantAccessory[] getArmorList() {
        return (L1WilliamEnchantAccessory[])this._ArmorIndex.values().toArray(new L1WilliamEnchantAccessory[this._ArmorIndex.size()]);
    }
}
