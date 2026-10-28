package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;

public class GfxIdOrginalpoly
{
    private static GfxIdOrginalpoly _instance;
    private final HashMap<Integer, L1WilliamGfxIdOrginalpoly> _gfxIdIndex;
    
    public static GfxIdOrginalpoly getInstance() {
        if (GfxIdOrginalpoly._instance == null) {
            GfxIdOrginalpoly._instance = new GfxIdOrginalpoly();
        }
        return GfxIdOrginalpoly._instance;
    }
    
    private GfxIdOrginalpoly() {
        this._gfxIdIndex = new HashMap();
        this.loadGfxIdOrginalpoly();
    }
    
    private void loadGfxIdOrginalpoly() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_變身能力_2");
            rs = pstm.executeQuery();
            this.fillWeaponSkill(rs);
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillWeaponSkill(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int gfxId = rs.getInt("gfxId");
            boolean deadExp = rs.getInt("deadExp") != 0;
            boolean cancellation = rs.getInt("cancellation") != 0;
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
            int reduction_dmg = rs.getInt("reduction_dmg");
            int reduction_magic_dmg = rs.getInt("reduction_magic_dmg");
            int addMr = rs.getInt("addMr");
            int addSp = rs.getInt("addSp");
            int addFire = rs.getInt("addFire");
            int addWind = rs.getInt("addWind");
            int addEarth = rs.getInt("addEarth");
            int addWater = rs.getInt("addWater");
            double addExp = rs.getDouble("addExp");
            int Potion_Heal = rs.getInt("Potion_Heal");
            int PVPdmg = rs.getInt("PVPdmg");
            int PVPdmgReduction = rs.getInt("PVPdmgReduction");
            int magic_hit = rs.getInt("add_magic_hit");
            int regist_stun = rs.getInt("regist_stun");
            int regist_stone = rs.getInt("regist_stone");
            int regist_sleep = rs.getInt("regist_sleep");
            int regist_freeze = rs.getInt("regist_freeze");
            int regist_sustain = rs.getInt("regist_sustain");
            int regist_blind = rs.getInt("regist_blind");
            L1WilliamGfxIdOrginalpoly gfxIdOrginalpoly = new L1WilliamGfxIdOrginalpoly(gfxId, deadExp, cancellation, addStr, addDex, addCon, addInt, addWis, addCha, addAc, addMaxHp, addMaxMp, addHpr, addMpr, addDmg, addBowDmg, addHit, addBowHit, reduction_dmg, reduction_magic_dmg, addMr, addSp, addFire, addWind, addEarth, addWater, addExp, Potion_Heal, PVPdmg, PVPdmgReduction, magic_hit, regist_stun, regist_stone, regist_sleep, regist_freeze, regist_sustain, regist_blind);
            this._gfxIdIndex.put(gfxId, gfxIdOrginalpoly);
        }
    }
    
    public L1WilliamGfxIdOrginalpoly getTemplate(int gfxId) {
        return this._gfxIdIndex.get((Integer)gfxId);
    }
    
    public L1WilliamGfxIdOrginalpoly[] getGfxIdList() {
        return (L1WilliamGfxIdOrginalpoly[])this._gfxIdIndex.values().toArray(new L1WilliamGfxIdOrginalpoly[this._gfxIdIndex.size()]);
    }
}
