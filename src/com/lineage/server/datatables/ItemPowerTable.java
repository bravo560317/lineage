package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.templates.L1ItemPower_text;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class ItemPowerTable
{
    private static final Log _log;
    private static ItemPowerTable _instance;
    public static final HashMap<Integer, L1ItemPower_text> POWER_TEXT;
    public static final HashMap<Integer, L1ItemPower_name> POWER_NAME;
    
    static {
        _log = LogFactory.getLog(ItemPowerTable.class);
        POWER_TEXT = new HashMap();
        POWER_NAME = new HashMap();
    }
    
    public static ItemPowerTable get() {
        if (ItemPowerTable._instance == null) {
            ItemPowerTable._instance = new ItemPowerTable();
        }
        return ItemPowerTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `server_item_power`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int ac = rs.getInt("ac");
                int hp = rs.getInt("hp");
                int mp = rs.getInt("mp");
                int hpr = rs.getInt("hpr");
                int mpr = rs.getInt("mpr");
                int mr = rs.getInt("mr");
                int sp = rs.getInt("sp");
                int hit = rs.getInt("hit");
                int dmgup = rs.getInt("dmgup");
                int bowhit = rs.getInt("bowhit");
                int bowdmgup = rs.getInt("bowdmgup");
                int dice_dmg = rs.getInt("dice_dmg");
                int dmg = rs.getInt("dmg");
                int er = rs.getInt("er");
                int dodge = rs.getInt("dodge");
                int dice_hp = rs.getInt("dice_hp");
                int sucking_hp = rs.getInt("sucking_hp");
                int dice_mp = rs.getInt("dice_mp");
                int sucking_mp = rs.getInt("sucking_mp");
                int double_dmg = rs.getInt("double_dmg");
                int lift = rs.getInt("lift");
                int defense_water = rs.getInt("defense_water");
                int defense_wind = rs.getInt("defense_wind");
                int defense_fire = rs.getInt("defense_fire");
                int defense_earth = rs.getInt("defense_earth");
                int regist_stun = rs.getInt("regist_stun");
                int regist_stone = rs.getInt("regist_stone");
                int regist_sleep = rs.getInt("regist_sleep");
                int regist_freeze = rs.getInt("regist_freeze");
                int regist_sustain = rs.getInt("regist_sustain");
                int regist_blind = rs.getInt("regist_blind");
                String gfx = rs.getString("gfx");
                String msg = rs.getString("msg");
                L1ItemPower_text text = new L1ItemPower_text();
                text.set_id(id);
                text.setAc(ac);
                text.setHp(hp);
                text.setMp(mp);
                text.setHpr(hpr);
                text.setMpr(mpr);
                text.setMr(mr);
                text.setSp(sp);
                text.setHit(hit);
                text.setDmgup(dmgup);
                text.setBowhit(bowhit);
                text.setBowdmgup(bowdmgup);
                text.setDice_dmg(dice_dmg);
                text.setDmg(dmg);
                text.setEr(er);
                text.setDodge(dodge);
                text.setDice_hp(dice_hp);
                text.setSucking_hp(sucking_hp);
                text.setDice_mp(dice_mp);
                text.setSucking_mp(sucking_mp);
                text.setDouble_dmg(double_dmg);
                text.setLift(lift);
                text.setDefense_water(defense_water);
                text.setDefense_wind(defense_wind);
                text.setDefense_fire(defense_fire);
                text.setDefense_earth(defense_earth);
                text.setRegist_stun(regist_stun);
                text.setRegist_stone(regist_stone);
                text.setRegist_sleep(regist_sleep);
                text.setRegist_freeze(regist_freeze);
                text.setRegist_sustain(regist_sustain);
                text.setRegist_blind(regist_blind);
                if (gfx != null && !gfx.equals("")) {
                    String[] gfxs = gfx.replaceAll(" ", "").split(",");
                    int[] out = new int[gfxs.length];
                    for (int i = 0; i < gfxs.length; ++i) {
                        out[i] = Integer.parseInt(gfxs[i]);
                    }
                    text.setGfx(out);
                }
                text.setMsg(msg);
                ItemPowerTable.POWER_TEXT.put(id, text);
            }
        }
        catch (SQLException e) {
            ItemPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        catch (Exception e2) {
            ItemPowerTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        ItemPowerTable._log.info("載入古文字能力資料數量: " + ItemPowerTable.POWER_TEXT.size() + "(" + timer.get() + "ms)");
    }
}
