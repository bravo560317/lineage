package com.lineage.server.datatables;

import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.LinkedHashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1MeteAbility;
import java.util.Map;
import org.apache.commons.logging.Log;

public final class ExtraMeteAbilityTable
{
    private static final Log _log;
    private static ExtraMeteAbilityTable _instance;
    private static final Map<Integer, L1MeteAbility> _meteList;
    
    static {
        _log = LogFactory.getLog(ExtraMeteAbilityTable.class);
        _meteList = new LinkedHashMap();
    }
    
    public static ExtraMeteAbilityTable getInstance() {
        if (ExtraMeteAbilityTable._instance == null) {
            ExtraMeteAbilityTable._instance = new ExtraMeteAbilityTable();
        }
        return ExtraMeteAbilityTable._instance;
    }
    
    public final void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("SELECT * FROM 系統_轉生 ORDER BY 轉生次數");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int mete_level = rs.getInt("轉生次數");
                String title = rs.getString("轉生頭銜");
                int type = rs.getInt("職業判定");
                int ac = rs.getInt("防禦");
                int hp = rs.getInt("血量");
                int mp = rs.getInt("魔量");
                int hpr = rs.getInt("回血量");
                int mpr = rs.getInt("回魔量");
                int str = rs.getInt("力量");
                int con = rs.getInt("體質");
                int dex = rs.getInt("敏捷");
                int wis = rs.getInt("精神");
                int cha = rs.getInt("魅力");
                int intel = rs.getInt("智力");
                int sp = rs.getInt("魔攻");
                int mr = rs.getInt("魔防");
                int hit_modifier = rs.getInt("近距離命中");
                int dmg_modifier = rs.getInt("近距離傷害");
                int bow_hit_modifier = rs.getInt("遠距離命中");
                int bow_dmg_modifier = rs.getInt("遠距離傷害");
                int magic_hit_modifier = rs.getInt("魔法命中");
                int magic_dmg_modifier = rs.getInt("魔法傷害");
                int magic_dmg_reduction = rs.getInt("魔法傷害減免");
                int reduction_dmg = rs.getInt("全傷害減免");
                int defense_water = rs.getInt("水屬性防禦");
                int defense_wind = rs.getInt("風屬性防禦");
                int defense_fire = rs.getInt("火屬性防禦");
                int defense_earth = rs.getInt("地屬性防禦");
                int regist_stun = rs.getInt("昏迷耐性");
                int regist_stone = rs.getInt("石化耐性");
                int regist_sleep = rs.getInt("睡眠耐性");
                int regist_freeze = rs.getInt("寒冰耐性");
                int regist_sustain = rs.getInt("支撐耐性");
                int regist_blind = rs.getInt("暗黑耐性");
                L1MeteAbility meteAbility = new L1MeteAbility(title, ac, hp, mp, hpr, mpr, str, con, dex, wis, cha, intel, sp, mr, hit_modifier, dmg_modifier, bow_hit_modifier, bow_dmg_modifier,magic_hit_modifier ,magic_dmg_modifier, magic_dmg_reduction, reduction_dmg, defense_water, defense_wind, defense_fire, defense_earth, regist_stun, regist_stone, regist_sleep, regist_freeze, regist_sustain, regist_blind);
                int index = mete_level;
                if (type != -1) {
                    index = index * 10000 + type;
                }
                ExtraMeteAbilityTable._meteList.put(index, meteAbility);
            }
        }
        catch (SQLException e) {
            ExtraMeteAbilityTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
        ExtraMeteAbilityTable._log.info("載入轉生附加能力資料數量: " + ExtraMeteAbilityTable._meteList.size() + "(" + timer.get() + "ms)");
    }
    
    public final L1MeteAbility get(int meteLevel, int type) {
        int index = meteLevel * 10000 + type;
        L1MeteAbility temp = ExtraMeteAbilityTable._meteList.get((Integer)index);
        if (temp != null) {
            return temp;
        }
        else {
            return ExtraMeteAbilityTable._meteList.get((Integer)meteLevel);
        }
    }
    
    public static final void effectBuff(L1PcInstance pc, L1MeteAbility value, int negative) {
        pc.addAc(value.getAc() * negative);
        pc.addMaxHp(value.getHp() * negative);
        pc.addMaxMp(value.getMp() * negative);
        pc.addHpr(value.getHpr() * negative);
        pc.addMpr(value.getMpr() * negative);
        pc.addStr(value.getStr() * negative);
        pc.addCon(value.getCon() * negative);
        pc.addDex(value.getDex() * negative);
        pc.addWis(value.getWis() * negative);
        pc.addCha(value.getCha() * negative);
        pc.addInt(value.getInt() * negative);
        pc.addSp(value.getSp() * negative);
        pc.addMr(value.getMr() * negative);
        pc.addHitup(value.getHitModifier() * negative);
        pc.addDmgup(value.getDmgModifier() * negative);
        pc.addBowHitup(value.getBowHitModifier() * negative);
        pc.addBowDmgup(value.getBowDmgModifier() * negative);
        pc.addOriginalMagicHit(value.getMagicHitModifier() * negative);
        pc.addMagicDmgModifier(value.getMagicDmgModifier() * negative);
        pc.addMagicDmgReduction(value.getMagicDmgReduction() * negative);
        pc.addDamageReductionByArmor(value.getReductionDmg() * negative);
        pc.addWater(value.getDefenseWater() * negative);
        pc.addWind(value.getDefenseWind() * negative);
        pc.addFire(value.getDefenseFire() * negative);
        pc.addEarth(value.getDefenseEarth() * negative);
        pc.addRegistStun(value.getRegistStun() * negative);
        pc.addRegistStone(value.getRegistStone() * negative);
        pc.addRegistSleep(value.getRegistSleep() * negative);
        pc.add_regist_freeze(value.getRegistFreeze() * negative);
        pc.addRegistSustain(value.getRegistSustain() * negative);
        pc.addRegistBlind(value.getRegistBlind() * negative);
    }
}
