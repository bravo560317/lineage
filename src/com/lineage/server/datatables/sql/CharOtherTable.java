package com.lineage.server.datatables.sql;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1PcOther;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharOtherStorage;

public class CharOtherTable implements CharOtherStorage
{
    private static final Log _log;
    private static final Map<Integer, L1PcOther> _otherMap;
    
    static {
        _log = LogFactory.getLog(CharOtherTable.class);
        _otherMap = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_other`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_obj_id = rs.getInt("char_obj_id");
                if (CharObjidTable.get().isChar(char_obj_id) != null) {
                    int hpup = rs.getInt("hpup");
                    int mpup = rs.getInt("mpup");
                    int score = rs.getInt("score");
                    int color = rs.getInt("color");
                    int usemap = rs.getInt("usemap");
                    int usemaptime = rs.getInt("usemaptime");
                    int clanskill = rs.getInt("clanskill");
                    int killCount = rs.getInt("killCount");
                    int deathCount = rs.getInt("deathCount");
                    int Attack = rs.getInt("Attack");
                    int BowAttack = rs.getInt("BowAttack");
                    int Hit = rs.getInt("Hit");
                    int BowHit = rs.getInt("BowHit");
                    int Sp = rs.getInt("Sp");
                    int Str = rs.getInt("Str");
                    int Dex = rs.getInt("Dex");
                    int Int = rs.getInt("Int");
                    int Con = rs.getInt("Con");
                    int Cha = rs.getInt("Cha");
                    int Wis = rs.getInt("Wis");
                    int Hp = rs.getInt("Hp");
                    int Mp = rs.getInt("Mp");
                    int Mr = rs.getInt("Mr");
                    int ReductionDmg = rs.getInt("ReductionDmg");
                    int Hpr = rs.getInt("Hpr");
                    int Mpr = rs.getInt("Mpr");
                    int hppotion = rs.getInt("hppotion");
                    int exp = rs.getInt("exp");
                    int ac = rs.getInt("ac");
                    int weight = rs.getInt("weight");
                    int regist_stun = rs.getInt("regist_stun");
                    int regist_stone = rs.getInt("regist_stone");
                    int regist_sleep = rs.getInt("regist_sleep");
                    int regist_freeze = rs.getInt("regist_freeze");
                    int regist_sustain = rs.getInt("regist_sustain");
                    int regist_blind = rs.getInt("regist_blind");
                    int PvP_dmg = rs.getInt("PvP_dmg");
                    int PvP_hit = rs.getInt("PvP_hit");
                    int PvP_bow_dmg = rs.getInt("PvP_bow_dmg");
                    int PvP_bow_hit = rs.getInt("PvP_bow_hit");
                    int PvP_dmg_r = rs.getInt("PvP_dmg_r");
                    int PvP_magic_r = rs.getInt("PvP_magic_r");
                    int Ran_PvP_dmg_b = rs.getInt("Ran_PvP_dmg_b");
                    Double PvP_dmg_b = rs.getDouble("PvP_dmg_b");
                    int Ran_PvP_magic_b = rs.getInt("Ran_PvP_magic_b");
                    Double PvP_magic_b = rs.getDouble("PvP_magic_b");
                    L1PcOther other = new L1PcOther();
                    other.set_objid(char_obj_id);
                    other.set_addhp(hpup);
                    other.set_addmp(mpup);
                    other.set_score(score);
                    other.set_color(color);
                    if (usemaptime <= 0) {
                        other.set_usemap(-1);
                        other.set_usemapTime(0);
                    }
                    else {
                        other.set_usemap(usemap);
                        other.set_usemapTime(usemaptime);
                    }
                    other.set_clanskill(clanskill);
                    other.set_killCount(killCount);
                    other.set_deathCount(deathCount);
                    other.set_Attack(Attack);
                    other.set_BowAttack(BowAttack);
                    other.set_Hit(Hit);
                    other.set_BowHit(BowHit);
                    other.set_Sp(Sp);
                    other.set_Str(Str);
                    other.set_Dex(Dex);
                    other.set_Int(Int);
                    other.set_Con(Con);
                    other.set_Cha(Cha);
                    other.set_Wis(Wis);
                    other.set_Hp(Hp);
                    other.set_Mp(Mp);
                    other.set_Mr(Mr);
                    other.set_ReductionDmg(ReductionDmg);
                    other.set_Hpr(Hpr);
                    other.set_Mpr(Mpr);
                    other.set_hppotion(hppotion);
                    other.set_exp(exp);
                    other.set_ac(ac);
                    other.set_weight(weight);
                    other.set_regist_stun(regist_stun);
                    other.set_regist_stone(regist_stone);
                    other.set_regist_sleep(regist_sleep);
                    other.set_regist_freeze(regist_freeze);
                    other.set_regist_sustain(regist_sustain);
                    other.set_regist_blind(regist_blind);
                    other.set_PvP_dmg(PvP_dmg);
                    other.set_PvP_hit(PvP_hit);
                    other.set_PvP_bow_dmg(PvP_bow_dmg);
                    other.set_PvP_bow_hit(PvP_bow_hit);
                    other.set_PvP_dmg_r(PvP_dmg_r);
                    other.set_PvP_magic_r(PvP_magic_r);
                    other.set_Ran_PvP_dmg_b(Ran_PvP_dmg_b);
                    other.setPvP_dmg_b(PvP_dmg_b);
                    other.set_Ran_PvP_magic_b(Ran_PvP_magic_b);
                    other.setPvP_magic_b(PvP_magic_b);
                    addMap(char_obj_id, other);
                }
                else {
                    delete(char_obj_id);
                }
            }
        }
        catch (SQLException e) {
            CharOtherTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharOtherTable._log.info("載入額外紀錄資料數量: " + CharOtherTable._otherMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_other` WHERE `char_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
            CharOtherTable._otherMap.remove((Integer)objid);
        }
        catch (SQLException e) {
            CharOtherTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addMap(int objId, L1PcOther other) {
        L1PcOther otherTmp = CharOtherTable._otherMap.get((Integer)objId);
        if (otherTmp == null) {
            CharOtherTable._otherMap.put(objId, other);
        }
    }
    
    @Override
    public L1PcOther getOther(L1PcInstance pc) {
        L1PcOther otherTmp = CharOtherTable._otherMap.get((Integer)pc.getId());
        return otherTmp;
    }
    
    @Override
    public void storeOther(int objId, L1PcOther other) {
        L1PcOther otherTmp = CharOtherTable._otherMap.get((Integer)objId);
        if (otherTmp == null) {
            addMap(objId, other);
            this.addNewOther(other);
        }
        else {
            this.updateOther(other);
        }
    }
    
    private void updateOther(L1PcOther other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            int hpup = other.get_addhp();
            int mpup = other.get_addmp();
            int score = other.get_score();
            int color = other.get_color();
            int usemap = other.get_usemap();
            int usemapTime = other.get_usemapTime();
            int clanskill = other.get_clanskill();
            int killCount = other.get_killCount();
            int deathCount = other.get_deathCount();
            int Attack = other.get_Attack();
            int BowAttack = other.get_BowAttack();
            int Hit = other.get_Hit();
            int BowHit = other.get_BowHit();
            int Sp = other.get_Sp();
            int Str = other.get_Str();
            int Dex = other.get_Dex();
            int Int = other.get_Int();
            int Con = other.get_Con();
            int Cha = other.get_Cha();
            int Wis = other.get_Wis();
            int Hp = other.get_Hp();
            int Mp = other.get_Mp();
            int Mr = other.get_Mr();
            int ReductionDmg = other.get_ReductionDmg();
            int Hpr = other.get_Hpr();
            int Mpr = other.get_Mpr();
            int hppotion = other.get_hppotion();
            int exp = other.get_exp();
            int ac = other.get_ac();
            int weight = other.get_weight();
            int regist_stun = other.get_regist_stun();
            int regist_stone = other.get_regist_stone();
            int regist_sleep = other.get_regist_sleep();
            int regist_freeze = other.get_regist_freeze();
            int regist_sustain = other.get_regist_sustain();
            int regist_blind = other.get_regist_blind();
            int PvP_dmg = other.get_PvP_dmg();
            int PvP_hit = other.get_PvP_hit();
            int PvP_bow_dmg = other.get_PvP_bow_dmg();
            int PvP_bow_hit = other.get_PvP_bow_hit();
            int PvP_dmg_r = other.get_PvP_dmg_r();
            int PvP_magic_r = other.get_PvP_magic_r();
            int Ran_PvP_dmg_b = other.get_Ran_PvP_dmg_b();
            Double PvP_dmg_b = other.getPvP_dmg_b();
            int Ran_PvP_magic_b = other.get_Ran_PvP_magic_b();
            Double PvP_magic_b = other.getPvP_magic_b();
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_other` SET `hpup`=?,`mpup`=?,`score`=?,`color`=?,`usemap`=?,`usemaptime`=?,`clanskill`=?,`killCount`=?,`deathCount`=?,`Attack`=?,`BowAttack`=?,`Hit`=?,`BowHit`=?,`Sp`=?,`Str`=?,`Dex`=?,`Int`=?,`Con`=?,`Cha`=?,`Wis`=?,`Hp`=?,`Mp`=?,`Mr`=?,`ReductionDmg`=?,`Hpr`=?,`Mpr`=?,`hppotion`=?,`exp`=?,`ac`=?,`weight`=?,`regist_stun`=?,`regist_stone`=?,`regist_sleep`=?,`regist_freeze`=?,`regist_sustain`=?,`regist_blind`=?,`PvP_dmg`=?,`PvP_hit`=?,`PvP_bow_dmg`=?,`PvP_bow_hit`=?,`PvP_dmg_r`=?,`PvP_magic_r`=?,`Ran_PvP_dmg_b`=?,`PvP_dmg_b`=?,`Ran_PvP_magic_b`=?,`PvP_magic_b`=? WHERE `char_obj_id`=?");
            int i = 0;
            ps.setInt(++i, hpup);
            ps.setInt(++i, mpup);
            ps.setInt(++i, score);
            ps.setInt(++i, color);
            ps.setInt(++i, usemap);
            ps.setInt(++i, usemapTime);
            ps.setInt(++i, clanskill);
            ps.setInt(++i, killCount);
            ps.setInt(++i, deathCount);
            ps.setInt(++i, Attack);
            ps.setInt(++i, BowAttack);
            ps.setInt(++i, Hit);
            ps.setInt(++i, BowHit);
            ps.setInt(++i, Sp);
            ps.setInt(++i, Str);
            ps.setInt(++i, Dex);
            ps.setInt(++i, Int);
            ps.setInt(++i, Con);
            ps.setInt(++i, Cha);
            ps.setInt(++i, Wis);
            ps.setInt(++i, Hp);
            ps.setInt(++i, Mp);
            ps.setInt(++i, Mr);
            ps.setInt(++i, ReductionDmg);
            ps.setInt(++i, Hpr);
            ps.setInt(++i, Mpr);
            ps.setInt(++i, hppotion);
            ps.setInt(++i, exp);
            ps.setInt(++i, ac);
            ps.setInt(++i, weight);
            ps.setInt(++i, regist_stun);
            ps.setInt(++i, regist_stone);
            ps.setInt(++i, regist_sleep);
            ps.setInt(++i, regist_freeze);
            ps.setInt(++i, regist_sustain);
            ps.setInt(++i, regist_blind);
            ps.setInt(++i, PvP_dmg);
            ps.setInt(++i, PvP_hit);
            ps.setInt(++i, PvP_bow_dmg);
            ps.setInt(++i, PvP_bow_hit);
            ps.setInt(++i, PvP_dmg_r);
            ps.setInt(++i, PvP_magic_r);
            ps.setInt(++i, Ran_PvP_dmg_b);
            ps.setDouble(++i, PvP_dmg_b);
            ps.setInt(++i, Ran_PvP_magic_b);
            ps.setDouble(++i, PvP_magic_b);
            ps.setInt(++i, other.get_objid());
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void addNewOther(L1PcOther other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            int oid = other.get_objid();
            int hpup = other.get_addhp();
            int mpup = other.get_addmp();
            int score = other.get_score();
            int color = other.get_color();
            int usemap = other.get_usemap();
            int usemapTime = other.get_usemapTime();
            int clanskill = other.get_clanskill();
            int killCount = other.get_killCount();
            int deathCount = other.get_deathCount();
            int Attack = other.get_Attack();
            int BowAttack = other.get_BowAttack();
            int Hit = other.get_Hit();
            int BowHit = other.get_BowHit();
            int Sp = other.get_Sp();
            int Str = other.get_Str();
            int Dex = other.get_Dex();
            int Int = other.get_Int();
            int Con = other.get_Con();
            int Cha = other.get_Cha();
            int Wis = other.get_Wis();
            int Hp = other.get_Hp();
            int Mp = other.get_Mp();
            int Mr = other.get_Mr();
            int ReductionDmg = other.get_ReductionDmg();
            int Hpr = other.get_Hpr();
            int Mpr = other.get_Mpr();
            int hppotion = other.get_hppotion();
            int exp = other.get_exp();
            int ac = other.get_ac();
            int weight = other.get_weight();
            int regist_stun = other.get_regist_stun();
            int regist_stone = other.get_regist_stone();
            int regist_sleep = other.get_regist_sleep();
            int regist_freeze = other.get_regist_freeze();
            int regist_sustain = other.get_regist_sustain();
            int regist_blind = other.get_regist_blind();
            int PvP_dmg = other.get_PvP_dmg();
            int PvP_hit = other.get_PvP_hit();
            int PvP_bow_dmg = other.get_PvP_bow_dmg();
            int PvP_bow_hit = other.get_PvP_bow_hit();
            int PvP_dmg_r = other.get_PvP_dmg_r();
            int PvP_magic_r = other.get_PvP_magic_r();
            int Ran_PvP_dmg_b = other.get_Ran_PvP_dmg_b();
            Double PvP_dmg_b = other.getPvP_dmg_b();
            int Ran_PvP_magic_b = other.get_Ran_PvP_magic_b();
            Double PvP_magic_b = other.getPvP_magic_b();
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `character_other` SET `char_obj_id`=?,`hpup`=?,`mpup`=?,`score`=?,`color`=?,`usemap`=?,`usemaptime`=?,`clanskill`=?,`killCount`=?,`deathCount`=?,`Attack`=?,`BowAttack`=?,`Hit`=?,`BowHit`=?,`Sp`=?,`Str`=?,`Dex`=?,`Int`=?,`Con`=?,`Cha`=?,`Wis`=?,`Hp`=?,`Mp`=?,`Mr`=?,`ReductionDmg`=?,`Hpr`=?,`Mpr`=?,`hppotion`=?,`exp`=?,`ac`=?,`weight`=?,`regist_stun`=?,`regist_stone`=?,`regist_sleep`=?,`regist_freeze`=?,`regist_sustain`=?,`regist_blind`=?,`PvP_dmg`=?,`PvP_hit`=?,`PvP_bow_dmg`=?,`PvP_bow_hit`=?,`PvP_dmg_r`=?,`PvP_magic_r`=?,`Ran_PvP_dmg_b`=?,`PvP_dmg_b`=?,`Ran_PvP_magic_b`=?,`PvP_magic_b`=?");
            int i = 0;
            ps.setInt(++i, oid);
            ps.setInt(++i, hpup);
            ps.setInt(++i, mpup);
            ps.setInt(++i, score);
            ps.setInt(++i, color);
            ps.setInt(++i, usemap);
            ps.setInt(++i, usemapTime);
            ps.setInt(++i, clanskill);
            ps.setInt(++i, killCount);
            ps.setInt(++i, deathCount);
            ps.setInt(++i, Attack);
            ps.setInt(++i, BowAttack);
            ps.setInt(++i, Hit);
            ps.setInt(++i, BowHit);
            ps.setInt(++i, Sp);
            ps.setInt(++i, Str);
            ps.setInt(++i, Dex);
            ps.setInt(++i, Int);
            ps.setInt(++i, Con);
            ps.setInt(++i, Cha);
            ps.setInt(++i, Wis);
            ps.setInt(++i, Hp);
            ps.setInt(++i, Mp);
            ps.setInt(++i, Mr);
            ps.setInt(++i, ReductionDmg);
            ps.setInt(++i, Hpr);
            ps.setInt(++i, Mpr);
            ps.setInt(++i, hppotion);
            ps.setInt(++i, exp);
            ps.setInt(++i, ac);
            ps.setInt(++i, weight);
            ps.setInt(++i, regist_stun);
            ps.setInt(++i, regist_stone);
            ps.setInt(++i, regist_sleep);
            ps.setInt(++i, regist_freeze);
            ps.setInt(++i, regist_sustain);
            ps.setInt(++i, regist_blind);
            ps.setInt(++i, PvP_dmg);
            ps.setInt(++i, PvP_hit);
            ps.setInt(++i, PvP_bow_dmg);
            ps.setInt(++i, PvP_bow_hit);
            ps.setInt(++i, PvP_dmg_r);
            ps.setInt(++i, PvP_magic_r);
            ps.setInt(++i, Ran_PvP_dmg_b);
            ps.setDouble(++i, PvP_dmg_b);
            ps.setInt(++i, Ran_PvP_magic_b);
            ps.setDouble(++i, PvP_magic_b);
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public void tam() {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            for (L1PcOther other : CharOtherTable._otherMap.values()) {
                other.set_killCount(0);
                other.set_deathCount(0);
            }
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_other` SET `killCount`='0' AND `deathCount`='0'");
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
