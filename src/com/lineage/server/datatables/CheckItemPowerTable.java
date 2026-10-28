package com.lineage.server.datatables;

import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1CheckItemPower;
import java.util.Map;
import org.apache.commons.logging.Log;

public class CheckItemPowerTable
{
    private static final Log _log;
    private static final Map<Integer, L1CheckItemPower> _list;
    private static CheckItemPowerTable _instance;
    
    static {
        _log = LogFactory.getLog(CheckItemPowerTable.class);
        _list = new HashMap();
    }
    
    public static CheckItemPowerTable get() {
        if (CheckItemPowerTable._instance == null) {
            CheckItemPowerTable._instance = new CheckItemPowerTable();
        }
        return CheckItemPowerTable._instance;
    }
    
    private CheckItemPowerTable() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pstm = co.prepareStatement("SELECT * FROM `w_道具身上賦予能力`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                int str = rs.getInt("add_str");
                int dex = rs.getInt("add_dex");
                int con = rs.getInt("add_con");
                int intel = rs.getInt("add_int");
                int wis = rs.getInt("add_wis");
                int cha = rs.getInt("add_cha");
                int ac = rs.getInt("add_ac");
                int hp = rs.getInt("add_hp");
                int mp = rs.getInt("add_mp");
                int hpr = rs.getInt("add_hpr");
                int mpr = rs.getInt("add_mpr");
                int mr = rs.getInt("add_mr");
                int sp = rs.getInt("add_sp");
                int dmg = rs.getInt("add_dmg");
                int bow_dmg = rs.getInt("add_bow_dmg");
                int hit = rs.getInt("add_hit");
                int bow_hit = rs.getInt("add_bow_hit");
                int dmg_r = rs.getInt("add_dmg_r");
                int magic_r = rs.getInt("add_magic_r");
                int fire = rs.getInt("add_fire");
                int wind = rs.getInt("add_wind");
                int earth = rs.getInt("add_earth");
                int water = rs.getInt("add_water");
                int stun = rs.getInt("add_stun");
                int stone = rs.getInt("add_stone");
                int sleep = rs.getInt("add_sleep");
                int freeze = rs.getInt("add_freeze");
                int sustain = rs.getInt("add_sustain");
                int blind = rs.getInt("add_blind");
                L1CheckItemPower power = new L1CheckItemPower();
                power.set_str(str);
                power.set_dex(dex);
                power.set_con(con);
                power.set_intel(intel);
                power.set_wis(wis);
                power.set_cha(cha);
                power.set_ac(ac);
                power.set_hp(hp);
                power.set_mp(mp);
                power.set_hpr(hpr);
                power.set_mpr(mpr);
                power.set_mr(mr);
                power.set_sp(sp);
                power.set_dmg(dmg);
                power.set_bow_dmg(bow_dmg);
                power.set_hit(hit);
                power.set_bow_hit(bow_hit);
                power.set_dmg_r(dmg_r);
                power.set_magic_r(magic_r);
                power.set_fire(fire);
                power.set_wind(wind);
                power.set_earth(earth);
                power.set_water(water);
                power.set_stun(stun);
                power.set_stone(stone);
                power.set_sleep(sleep);
                power.set_freeze(freeze);
                power.set_sustain(sustain);
                power.set_blind(blind);
                CheckItemPowerTable._list.put(item_id, power);
            }
        }
        catch (SQLException e) {
            CheckItemPowerTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(co);
        }
    }
    
    public L1CheckItemPower getItem(int item_id) {
        if (CheckItemPowerTable._list.isEmpty()) {
            return null;
        }
        else if (CheckItemPowerTable._list.containsKey((Integer)item_id)) {
            return CheckItemPowerTable._list.get((Integer)item_id);
        }
        else {
            return null;
        }
    }
    
    public boolean checkItem(int item_id) {
        return CheckItemPowerTable._list.containsKey((Integer)item_id);
    }
    
    public void givepower(L1PcInstance pc, int item_id) {
        if (CheckItemPowerTable._list.isEmpty()) {
            return;
        }
        else if (!CheckItemPowerTable._list.containsKey((Integer)item_id)) {
            return;
        }
        else {
            L1CheckItemPower power = CheckItemPowerTable._list.get((Integer)item_id);
            boolean status = false;
            boolean status2 = false;
            boolean spmr = false;
            boolean attr = false;
            StringBuilder name = new StringBuilder();
            if (power.get_hp() != 0) {
                pc.addMaxHp(power.get_hp());
                name.append(" 體力上限+");
                name.append(power.get_hp());
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                if (pc.isInParty()) {
                    pc.getParty().updateMiniHP(pc);
                }
            }
            if (power.get_mp() != 0) {
                pc.addMaxMp(power.get_mp());
                name.append(" 魔力上限+");
                name.append(power.get_mp());
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
            if (power.get_hpr() != 0) {
                pc.addHpr(power.get_hpr());
                name.append(" 體力回復量+");
                name.append(power.get_hpr());
            }
            if (power.get_mpr() != 0) {
                pc.addMpr(power.get_mpr());
                name.append(" 魔力回復量+");
                name.append(power.get_mpr());
            }
            if (power.get_ac() != 0) {
                pc.addAc(-power.get_ac());
                name.append(" 防禦+");
                name.append(power.get_ac());
                attr = true;
            }
            if (power.get_sp() != 0) {
                pc.addSp(power.get_sp());
                name.append(" 魔攻+");
                name.append(power.get_sp());
                spmr = true;
            }
            if (power.get_mr() != 0) {
                pc.addMr(power.get_mr());
                name.append(" 額外魔法防禦+");
                name.append(power.get_mr());
                spmr = true;
            }
            if (power.get_str() != 0) {
                pc.addStr(power.get_str());
                name.append(" 力量+");
                name.append(power.get_str());
                status2 = true;
            }
            if (power.get_dex() != 0) {
                pc.addDex(power.get_dex());
                name.append(" 敏捷+");
                name.append(power.get_dex());
                status2 = true;
            }
            if (power.get_con() != 0) {
                pc.addCon(power.get_con());
                name.append(" 體質+");
                name.append(power.get_con());
                status2 = true;
            }
            if (power.get_wis() != 0) {
                pc.addWis(power.get_wis());
                name.append(" 精神+");
                name.append(power.get_wis());
                status2 = true;
            }
            if (power.get_intel() != 0) {
                pc.addInt(power.get_intel());
                name.append(" 智力+");
                name.append(power.get_intel());
                status2 = true;
            }
            if (power.get_cha() != 0) {
                pc.addCha(power.get_cha());
                name.append(" 魅力+");
                name.append(power.get_cha());
                status2 = true;
            }
            if (power.get_dmg() != 0) {
                pc.addDmgup(power.get_dmg());
                name.append(" 近遠距離攻擊+");
                name.append(power.get_dmg());
            }
            if (power.get_bow_dmg() != 0) {
                pc.addBowDmgup(power.get_bow_dmg());
                name.append(" 遠距離攻擊力+");
                name.append(power.get_bow_dmg());
            }
            if (power.get_hit() != 0) {
                pc.addHitup(power.get_hit());
                name.append(" 近距離命中+");
                name.append(power.get_hit());
            }
            if (power.get_bow_hit() != 0) {
                pc.addBowHitup(power.get_bow_hit());
                name.append(" 遠距離命中+");
                name.append(power.get_bow_hit());
            }
            if (power.get_dmg_r() != 0) {
                pc.add_reduction_dmg(power.get_dmg_r());
                name.append(" 物理傷害減免+");
                name.append(power.get_dmg_r());
            }
            if (power.get_magic_r() != 0) {
                pc.add_magic_reduction_dmg(power.get_magic_r());
                name.append(" 魔法傷害減免+");
                name.append(power.get_magic_r());
            }
            if (power.get_water() != 0) {
                pc.addWater(power.get_water());
                name.append(" 水屬性抗性+");
                name.append(power.get_water());
                attr = true;
            }
            if (power.get_fire() != 0) {
                pc.addFire(power.get_fire());
                name.append(" 火屬性抗性+");
                name.append(power.get_fire());
                attr = true;
            }
            if (power.get_wind() != 0) {
                pc.addWind(power.get_wind());
                name.append(" 風屬性抗性+");
                name.append(power.get_wind());
                attr = true;
            }
            if (power.get_earth() != 0) {
                pc.addEarth(power.get_earth());
                name.append(" 地屬性抗性+");
                name.append(power.get_earth());
                attr = true;
            }
            if (power.get_stun() != 0) {
                pc.addRegistStun(power.get_stun());
                name.append(" 昏迷耐性+");
                name.append(power.get_stun());
            }
            if (power.get_stone() != 0) {
                pc.addRegistStone(power.get_stone());
                name.append(" 石化耐性+");
                name.append(power.get_stone());
            }
            if (power.get_sleep() != 0) {
                pc.addRegistSleep(power.get_sleep());
                name.append(" 睡眠耐性+");
                name.append(power.get_sleep());
            }
            if (power.get_freeze() != 0) {
                pc.add_regist_freeze(power.get_freeze());
                name.append(" 寒冰耐性+");
                name.append(power.get_freeze());
            }
            if (power.get_sustain() != 0) {
                pc.addRegistSustain(power.get_sustain());
                name.append(" 支撐耐性+");
                name.append(power.get_sustain());
            }
            if (power.get_blind() != 0) {
                pc.addRegistBlind(power.get_blind());
                name.append(" 暗黑耐性+");
                name.append(power.get_blind());
            }
            if (status2) {
                pc.sendPackets(new S_OwnCharStatus2(pc));
            }
            if (attr) {
                pc.sendPackets(new S_OwnCharAttrDef(pc));
            }
            if (spmr) {
                pc.sendPackets(new S_SPMR(pc));
            }
            return;
        }
    }
    
    public void delepower(L1PcInstance pc, int item_id) {
        if (!CheckItemPowerTable._list.containsKey((Integer)item_id)) {
            return;
        }
        else {
            L1CheckItemPower power = CheckItemPowerTable._list.get((Integer)item_id);
            boolean status = false;
            boolean status2 = false;
            boolean spmr = false;
            boolean attr = false;
            StringBuilder name = new StringBuilder();
            if (power.get_hp() != 0) {
                pc.addMaxHp(-power.get_hp());
                name.append(" 體力上限-");
                name.append(power.get_hp());
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                if (pc.isInParty()) {
                    pc.getParty().updateMiniHP(pc);
                }
            }
            if (power.get_mp() != 0) {
                pc.addMaxMp(-power.get_mp());
                name.append(" 魔力上限-");
                name.append(power.get_mp());
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
            if (power.get_hpr() != 0) {
                pc.addHpr(-power.get_hpr());
                name.append(" 體力回復量-");
                name.append(power.get_hpr());
            }
            if (power.get_mpr() != 0) {
                pc.addMpr(-power.get_mpr());
                name.append(" 魔力回復量-");
                name.append(power.get_mpr());
            }
            if (power.get_ac() != 0) {
                pc.addAc(power.get_ac());
                name.append(" 防禦-");
                name.append(power.get_ac());
                attr = true;
            }
            if (power.get_sp() != 0) {
                pc.addSp(-power.get_sp());
                name.append(" 魔攻-");
                name.append(power.get_sp());
                spmr = true;
            }
            if (power.get_mr() != 0) {
                pc.addMr(-power.get_mr());
                name.append(" 魔防-");
                name.append(power.get_mr());
                spmr = true;
            }
            if (power.get_str() != 0) {
                pc.addStr(-power.get_str());
                name.append(" 力量-");
                name.append(power.get_str());
                status2 = true;
            }
            if (power.get_dex() != 0) {
                pc.addDex(-power.get_dex());
                name.append(" 敏捷-");
                name.append(power.get_dex());
                status2 = true;
            }
            if (power.get_con() != 0) {
                pc.addCon(-power.get_con());
                name.append(" 體質-");
                name.append(power.get_con());
                status2 = true;
            }
            if (power.get_wis() != 0) {
                pc.addWis(-power.get_wis());
                name.append(" 精神-");
                name.append(power.get_wis());
                status2 = true;
            }
            if (power.get_intel() != 0) {
                pc.addInt(-power.get_intel());
                name.append(" 智力-");
                name.append(power.get_intel());
                status2 = true;
            }
            if (power.get_cha() != 0) {
                pc.addCha(-power.get_cha());
                name.append(" 魅力-");
                name.append(power.get_cha());
                status2 = true;
            }
            if (power.get_dmg() != 0) {
                pc.addDmgup(-power.get_dmg());
                name.append(" 近遠距離攻擊-");
                name.append(power.get_dmg());
            }
            if (power.get_bow_dmg() != 0) {
                pc.addBowDmgup(-power.get_bow_dmg());
                name.append(" 遠距離攻擊力-");
                name.append(power.get_bow_dmg());
            }
            if (power.get_hit() != 0) {
                pc.addHitup(-power.get_hit());
                name.append(" 近距離命中-");
                name.append(power.get_hit());
            }
            if (power.get_bow_hit() != 0) {
                pc.addBowHitup(-power.get_bow_hit());
                name.append(" 遠距離命中-");
                name.append(power.get_bow_hit());
            }
            if (power.get_dmg_r() != 0) {
                pc.add_reduction_dmg(-power.get_dmg_r());
                name.append(" 物理傷害減免-");
                name.append(power.get_dmg_r());
            }
            if (power.get_magic_r() != 0) {
                pc.add_magic_reduction_dmg(-power.get_magic_r());
                name.append(" 魔法傷害減免-");
                name.append(power.get_magic_r());
            }
            if (power.get_water() != 0) {
                pc.addWater(-power.get_water());
                name.append(" 水屬性抗性-");
                name.append(power.get_water());
                attr = true;
            }
            if (power.get_fire() != 0) {
                pc.addFire(-power.get_fire());
                name.append(" 火屬性抗性-");
                name.append(power.get_fire());
                attr = true;
            }
            if (power.get_wind() != 0) {
                pc.addWind(-power.get_wind());
                name.append(" 風屬性抗性-");
                name.append(power.get_wind());
                attr = true;
            }
            if (power.get_earth() != 0) {
                pc.addEarth(-power.get_earth());
                name.append(" 地屬性抗性-");
                name.append(power.get_earth());
                attr = true;
            }
            if (power.get_stun() != 0) {
                pc.addRegistStun(-power.get_stun());
                name.append(" 昏迷耐性-");
                name.append(power.get_stun());
            }
            if (power.get_stone() != 0) {
                pc.addRegistStone(-power.get_stone());
                name.append(" 石化耐性-");
                name.append(power.get_stone());
            }
            if (power.get_sleep() != 0) {
                pc.addRegistSleep(-power.get_sleep());
                name.append(" 睡眠耐性-");
                name.append(power.get_sleep());
            }
            if (power.get_freeze() != 0) {
                pc.add_regist_freeze(-power.get_freeze());
                name.append(" 寒冰耐性-");
                name.append(power.get_freeze());
            }
            if (power.get_sustain() != 0) {
                pc.addRegistSustain(-power.get_sustain());
                name.append(" 支撐耐性-");
                name.append(power.get_sustain());
            }
            if (power.get_blind() != 0) {
                pc.addRegistBlind(-power.get_blind());
                name.append(" 暗黑耐性-");
                name.append(power.get_blind());
            }
            if (status2) {
                pc.sendPackets(new S_OwnCharStatus2(pc));
            }
            if (attr) {
                pc.sendPackets(new S_OwnCharAttrDef(pc));
            }
            if (spmr) {
                pc.sendPackets(new S_SPMR(pc));
            }
            return;
        }
    }
}
