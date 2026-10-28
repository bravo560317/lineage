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
import com.lineage.server.templates.L1PcOther2;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharOtherStorage2;

public class CharOtherTable2 implements CharOtherStorage2
{
    private static final Log _log;
    private static final Map<Integer, L1PcOther2> _otherMap;
    
    static {
        _log = LogFactory.getLog(CharOtherTable2.class);
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
            ps = cn.prepareStatement("SELECT * FROM `character_天賦紀錄`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_obj_id = rs.getInt("char_obj_id");
                if (CharObjidTable.get().isChar(char_obj_id) != null) {
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
                    int pvp = rs.getInt("PVP");
                    int bowpvp = rs.getInt("BowPVP");
                    int tfcount = rs.getInt("tfcount");
                    int tfcount2 = rs.getInt("tfcount1");
                    L1PcOther2 other = new L1PcOther2();
                    other.set_objid(char_obj_id);
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
                    other.set_pvp(pvp);
                    other.set_bowpvp(bowpvp);
                    other.set_TfCount(tfcount);
                    other.set_TfCount1(tfcount2);
                    addMap(char_obj_id, other);
                }
                else {
                    delete(char_obj_id);
                }
            }
        }
        catch (SQLException e) {
            CharOtherTable2._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharOtherTable2._log.info("載入額外紀錄資料數量: " + CharOtherTable2._otherMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_天賦紀錄` WHERE `char_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
            CharOtherTable2._otherMap.remove((Integer)objid);
        }
        catch (SQLException e) {
            CharOtherTable2._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addMap(int objId, L1PcOther2 other) {
        L1PcOther2 otherTmp = CharOtherTable2._otherMap.get((Integer)objId);
        if (otherTmp == null) {
            CharOtherTable2._otherMap.put(objId, other);
        }
    }
    
    @Override
    public L1PcOther2 getOther(L1PcInstance pc) {
        L1PcOther2 otherTmp = CharOtherTable2._otherMap.get((Integer)pc.getId());
        return otherTmp;
    }
    
    @Override
    public void storeOther(int objId, L1PcOther2 other) {
        L1PcOther2 otherTmp = CharOtherTable2._otherMap.get((Integer)objId);
        if (otherTmp == null) {
            addMap(objId, other);
            this.addNewOther(other);
        }
        else {
            this.updateOther(other);
        }
    }
    
    private void updateOther(L1PcOther2 other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
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
            int pvp = other.get_pvp();
            int bowpvp = other.get_bowpvp();
            int tfcount = other.get_TfCount();
            int tfcount2 = other.get_TfCount1();
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_天賦紀錄` SET `Attack`=?,`BowAttack`=?,`Hit`=?,`BowHit`=?,`Sp`=?,`Str`=?,`Dex`=?,`Int`=?,`Con`=?,`Cha`=?,`Wis`=?,`Hp`=?,`Mp`=?,`Mr`=?,`ReductionDmg`=?,`Hpr`=?,`Mpr`=?,`hppotion`=?,`exp`=?,`ac`=?,`weight`=?,`regist_stun`=?,`regist_stone`=?,`regist_sleep`=?,`regist_freeze`=?,`regist_sustain`=?,`regist_blind`=?,`pvp`=?,`bowpvp`=?,`tfcount`=?,`tfcount1`=? WHERE `char_obj_id`=?");
            int i = 0;
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
            ps.setInt(++i, pvp);
            ps.setInt(++i, bowpvp);
            ps.setInt(++i, tfcount);
            ps.setInt(++i, tfcount2);
            ps.setInt(++i, other.get_objid());
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable2._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void addNewOther(L1PcOther2 other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            int oid = other.get_objid();
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
            int pvp = other.get_pvp();
            int bowpvp = other.get_bowpvp();
            int tfcount = other.get_TfCount();
            int tfcount2 = other.get_TfCount1();
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `character_天賦紀錄` SET `char_obj_id`=?,`Attack`=?,`BowAttack`=?,`Hit`=?,`BowHit`=?,`Sp`=?,`Str`=?,`Dex`=?,`Int`=?,`Con`=?,`Cha`=?,`Wis`=?,`Hp`=?,`Mp`=?,`Mr`=?,`ReductionDmg`=?,`Hpr`=?,`Mpr`=?,`hppotion`=?,`exp`=?,`ac`=?,`weight`=?,`regist_stun`=?,`regist_stone`=?,`regist_sleep`=?,`regist_freeze`=?,`regist_sustain`=?,`regist_blind`=?,`pvp`=?,`bowpvp`=?,`tfcount`=?,`tfcount1`=?");
            int i = 0;
            ps.setInt(++i, oid);
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
            ps.setInt(++i, pvp);
            ps.setInt(++i, bowpvp);
            ps.setInt(++i, tfcount);
            ps.setInt(++i, tfcount2);
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable2._log.error(e.getLocalizedMessage(), e);
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
            for (L1PcOther2 l1PcOther2 : CharOtherTable2._otherMap.values()) {}
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_天賦紀錄` SET `killCount`='0' AND `deathCount`='0'");
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable2._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        
    }
}
