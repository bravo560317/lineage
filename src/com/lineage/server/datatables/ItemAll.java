package com.lineage.server.datatables;

import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Iterator;
import com.lineage.server.datatables.sql.CharBuffTable;
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
import com.lineage.server.templates.L1ItemAll;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ItemAll
{
    private static final Log _log;
    private static final Map<Integer, L1ItemAll> _VIPList;
    private static ItemAll _instance;
    
    static {
        _log = LogFactory.getLog(ItemAll.class);
        _VIPList = new HashMap();
    }
    
    public static ItemAll get() {
        if (ItemAll._instance == null) {
            ItemAll._instance = new ItemAll();
        }
        return ItemAll._instance;
    }
    
    private ItemAll() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `etcitem_buff_time`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                int has_time = rs.getInt("has_time");
                String itemName = rs.getString("item_name");
                String Message = rs.getString("Message");
                int type = rs.getInt("type");
                boolean buff_save = rs.getBoolean("buff_save");
                int materials = rs.getInt("materials");
                int count = rs.getInt("count");
                String not_materials_msg = rs.getString("not_materials_msg");
                int add_str = rs.getInt("add_str");
                int add_dex = rs.getInt("add_dex");
                int add_con = rs.getInt("add_con");
                int add_int = rs.getInt("add_int");
                int add_wis = rs.getInt("add_wis");
                int add_cha = rs.getInt("add_cha");
                int add_ac = rs.getInt("add_ac");
                int add_hp = rs.getInt("add_hp");
                int add_mp = rs.getInt("add_mp");
                int add_hpr = rs.getInt("add_hpr");
                int add_mpr = rs.getInt("add_mpr");
                int add_dmg = rs.getInt("add_dmg");
                int add_hit = rs.getInt("add_hit");
                int add_bow_dmg = rs.getInt("add_bow_dmg");
                int add_bow_hit = rs.getInt("add_bow_hit");
                int add_dmg_r = rs.getInt("add_dmg_r");
                int add_magic_r = rs.getInt("add_magic_r");
                int add_mr = rs.getInt("add_mr");
                int add_sp = rs.getInt("add_sp");
                int add_fire = rs.getInt("add_fire");
                int add_wind = rs.getInt("add_wind");
                int add_earth = rs.getInt("add_earth");
                int add_water = rs.getInt("add_water");
                int add_stun = rs.getInt("add_stun");
                int add_stone = rs.getInt("add_stone");
                int add_sleep = rs.getInt("add_sleep");
                int add_freeze = rs.getInt("add_freeze");
                int add_sustain = rs.getInt("add_sustain");
                int add_blind = rs.getInt("add_blind");
                int add_exp = rs.getInt("add_exp");
                boolean death_exp = rs.getBoolean("death_exp");
                boolean death_item = rs.getBoolean("death_item");
                String type_msg = rs.getString("type_msg");
                int effect = rs.getInt("effect");
                L1ItemAll vip = new L1ItemAll();
                vip.setItem_name(itemName);
                vip.setMessage(Message);
                vip.set_type(type);
                vip.setmaterials(materials);
                vip.setcount(count);
                vip.set_has_time(has_time);
                vip.set_buff_save(buff_save);
                vip.setnot_materials_msg(not_materials_msg);
                vip.set_add_str(add_str);
                vip.set_add_dex(add_dex);
                vip.set_add_con(add_con);
                vip.set_add_int(add_int);
                vip.set_add_wis(add_wis);
                vip.set_add_cha(add_cha);
                vip.set_add_ac(add_ac);
                vip.set_add_hp(add_hp);
                vip.set_add_mp(add_mp);
                vip.set_add_hpr(add_hpr);
                vip.set_add_mpr(add_mpr);
                vip.set_add_dmg(add_dmg);
                vip.set_add_hit(add_hit);
                vip.set_add_bow_dmg(add_bow_dmg);
                vip.set_add_bow_hit(add_bow_hit);
                vip.set_add_dmg_r(add_dmg_r);
                vip.set_add_magic_r(add_magic_r);
                vip.set_add_mr(add_mr);
                vip.set_add_sp(add_sp);
                vip.set_add_fire(add_fire);
                vip.set_add_wind(add_wind);
                vip.set_add_earth(add_earth);
                vip.set_add_water(add_water);
                vip.set_add_stun(add_stun);
                vip.set_add_stone(add_stone);
                vip.set_add_sleep(add_sleep);
                vip.set_add_freeze(add_freeze);
                vip.set_add_sustain(add_sustain);
                vip.set_add_blind(add_blind);
                vip.set_add_exp(add_exp);
                vip.settype_msg(type_msg);
                vip.set_death_exp(death_exp);
                vip.set_death_item(death_item);
                vip.seteffect(effect);
                ItemAll._VIPList.put(item_id, vip);
            }
        }
        catch (SQLException e) {
            ItemAll._log.error(e.getLocalizedMessage(), e);
        }
        SQLUtil.close(rs);
        SQLUtil.close(pstm);
        SQLUtil.close(con);
        SQLUtil.close(rs);
        SQLUtil.close(pstm);
        SQLUtil.close(con);
        SQLUtil.close(rs);
        SQLUtil.close(pstm);
        SQLUtil.close(con);
        ItemAll._log.info("載入道具能力數量: " + ItemAll._VIPList.size() + "(" + timer.get() + "ms)");
    }
    
    public L1ItemAll getVIP(int item_id) {
        if (ItemAll._VIPList.isEmpty()) {
            return null;
        }
        else if (ItemAll._VIPList.containsKey((Integer)item_id)) {
            return ItemAll._VIPList.get((Integer)item_id);
        }
        else {
            return null;
        }
    }
    
    public boolean checkVIP(int item_id) {
        return ItemAll._VIPList.containsKey((Integer)item_id);
    }
    
    public static L1ItemAll getItemPro(int itemId) {
        if (ItemAll._VIPList == null) {
            return null;
        }
        else if (!ItemAll._VIPList.containsKey((Integer)itemId)) {
            return null;
        }
        else {
            return ItemAll._VIPList.get((Integer)itemId);
        }
    }
    
    public static void getSaveSkillid(L1PcInstance pc) {
        for (Integer skillId : ItemAll._VIPList.keySet()) {
            if ((ItemAll._VIPList.get(skillId)).is_buff_save()) {
                int timeSec = pc.getSkillEffectTimeSec(skillId);
                if (timeSec > 0) {
                    CharBuffTable.storeBuff(pc.getId(), skillId, timeSec);
                }
                else {
                    continue;
                }
            }
        }
    }
    
    public boolean add(L1PcInstance pc, int item_id, int buff_time) {
        if (!ItemAll._VIPList.containsKey((Integer)item_id)) {
            return false;
        }
        else {
            L1ItemAll vip = ItemAll._VIPList.get((Integer)item_id);
            String itemName = vip.getItem_name();
            if (pc.hasSkillEffect(item_id)) {
                pc.sendPackets(new S_ServerMessage(166, itemName + "\\aD 時效還有1\\aE" + pc.getSkillEffectTimeSec(item_id) + "秒"));
                return false;
            }
            else {
                if (buff_time != 0) {
                    pc.setSkillEffect(item_id, vip.get_has_time() * 1000);
                    pc.sendPackets(new S_ServerMessage(166, itemName + "\\aD 時效還有2\\aE" + pc.getSkillEffectTimeSec(item_id) + "秒"));
                }
                else {
                    pc.setSkillEffect(item_id, vip.get_has_time() * 1000);
                }
                StringBuilder name = new StringBuilder();
                boolean status = false;
                boolean status2 = false;
                boolean spmr = false;
                boolean attr = false;
                int add_str = vip.get_add_str();
                if (add_str != 0) {
                    pc.addStr(add_str);
                    status2 = true;
                }
                int add_dex = vip.get_add_dex();
                if (add_dex != 0) {
                    pc.addDex(add_dex);
                    status2 = true;
                }
                int add_con = vip.get_add_con();
                if (add_con != 0) {
                    pc.addCon(add_con);
                    status2 = true;
                }
                int add_int = vip.get_add_int();
                if (add_int != 0) {
                    pc.addInt(add_int);
                    status2 = true;
                }
                int add_wis = vip.get_add_wis();
                if (add_wis != 0) {
                    pc.addWis(add_wis);
                    status2 = true;
                }
                int add_cha = vip.get_add_cha();
                if (add_cha != 0) {
                    pc.addCha(add_cha);
                    status2 = true;
                }
                int add_ac = vip.get_add_ac();
                if (add_ac != 0) {
                    pc.addAc(-add_ac);
                    attr = true;
                }
                int add_hp = vip.get_add_hp();
                if (add_hp != 0) {
                    pc.addMaxHp(add_hp);
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    if (pc.isInParty()) {
                        pc.getParty().updateMiniHP(pc);
                    }
                }
                int add_mp = vip.get_add_mp();
                if (add_mp != 0) {
                    pc.addMaxMp(add_mp);
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                }
                int add_hpr = vip.get_add_hpr();
                if (add_hpr != 0) {
                    pc.addHpr(add_hpr);
                }
                int add_mpr = vip.get_add_mpr();
                if (add_mpr != 0) {
                    pc.addMpr(add_mpr);
                }
                int add_dmg = vip.get_add_dmg();
                if (add_dmg != 0) {
                    pc.addDmgup(add_dmg);
                }
                int add_hit = vip.get_add_hit();
                if (add_hit != 0) {
                    pc.addHitup(add_hit);
                }
                int add_bow_dmg = vip.get_add_bow_dmg();
                if (add_bow_dmg != 0) {
                    pc.addBowDmgup(add_bow_dmg);
                }
                int add_bow_hit = vip.get_add_bow_hit();
                if (add_bow_hit != 0) {
                    pc.addBowHitup(add_bow_hit);
                }
                int add_dmg_r = vip.get_add_dmg_r();
                if (add_dmg_r != 0) {
                    pc.addDamageReductionByArmor(add_dmg_r);
                }
                int add_magic_r = vip.get_add_magic_r();
                if (add_magic_r != 0) {
                    pc.add_magic_modifier_dmg(add_magic_r);
                }
                int add_mr = vip.get_add_mr();
                if (add_mr != 0) {
                    pc.addMr(add_mr);
                    spmr = true;
                }
                int add_sp = vip.get_add_sp();
                if (add_sp != 0) {
                    pc.addSp(add_sp);
                    spmr = true;
                }
                int add_fire = vip.get_add_fire();
                if (add_fire != 0) {
                    pc.addFire(add_fire);
                    attr = true;
                }
                int add_wind = vip.get_add_wind();
                if (add_wind != 0) {
                    pc.addWind(add_wind);
                    attr = true;
                }
                int add_earth = vip.get_add_earth();
                if (add_earth != 0) {
                    pc.addEarth(add_earth);
                    attr = true;
                }
                int add_water = vip.get_add_water();
                if (add_water != 0) {
                    pc.addWater(add_water);
                    attr = true;
                }
                int add_stun = vip.get_add_stun();
                if (add_stun != 0) {
                    pc.addRegistStun(add_stun);
                }
                int add_stone = vip.get_add_stone();
                if (add_stone != 0) {
                    pc.addRegistStone(add_stone);
                }
                int add_sleep = vip.get_add_sleep();
                if (add_sleep != 0) {
                    pc.addRegistSleep(add_sleep);
                }
                int add_freeze = vip.get_add_freeze();
                if (add_freeze != 0) {
                    pc.add_regist_freeze(add_freeze);
                }
                int add_sustain = vip.get_add_sustain();
                if (add_sustain != 0) {
                    pc.addRegistSustain(add_sustain);
                }
                int add_blind = vip.get_add_blind();
                if (add_blind != 0) {
                    pc.addRegistBlind(add_blind);
                }
                if (status) {
                    pc.sendPackets(new S_OwnCharStatus(pc));
                }
                else {
                    if (status2) {
                        pc.sendPackets(new S_OwnCharStatus2(pc));
                    }
                    if (attr) {
                        pc.sendPackets(new S_OwnCharAttrDef(pc));
                    }
                    if (spmr) {
                        pc.sendPackets(new S_SPMR(pc));
                    }
                }
                pc.sendPackets(new S_ServerMessage(name.toString()));
                return true;
            }
        }
    }
    
    public void addItemVIP(L1PcInstance pc, int item_id) {
        if (ItemAll._VIPList.isEmpty()) {
            return;
        }
        else if (!ItemAll._VIPList.containsKey((Integer)item_id)) {
            return;
        }
        else {
            L1ItemAll vip = ItemAll._VIPList.get((Integer)item_id);
            StringBuilder name = new StringBuilder();
            boolean status = false;
            boolean status2 = false;
            boolean spmr = false;
            boolean attr = false;
            int add_str = vip.get_add_str();
            if (add_str != 0) {
                pc.addStr(add_str);
                status2 = true;
            }
            int add_dex = vip.get_add_dex();
            if (add_dex != 0) {
                pc.addDex(add_dex);
                status2 = true;
            }
            int add_con = vip.get_add_con();
            if (add_con != 0) {
                pc.addCon(add_con);
                status2 = true;
            }
            int add_int = vip.get_add_int();
            if (add_int != 0) {
                pc.addInt(add_int);
                status2 = true;
            }
            int add_wis = vip.get_add_wis();
            if (add_wis != 0) {
                pc.addWis(add_wis);
                status2 = true;
            }
            int add_cha = vip.get_add_cha();
            if (add_cha != 0) {
                pc.addCha(add_cha);
                status2 = true;
            }
            int add_ac = vip.get_add_ac();
            if (add_ac != 0) {
                pc.addAc(-add_ac);
                attr = true;
            }
            int add_hp = vip.get_add_hp();
            if (add_hp != 0) {
                pc.addMaxHp(add_hp);
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                if (pc.isInParty()) {
                    pc.getParty().updateMiniHP(pc);
                }
            }
            int add_mp = vip.get_add_mp();
            if (add_mp != 0) {
                pc.addMaxMp(add_mp);
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
            int add_hpr = vip.get_add_hpr();
            if (add_hpr != 0) {
                pc.addHpr(add_hpr);
            }
            int add_mpr = vip.get_add_mpr();
            if (add_mpr != 0) {
                pc.addMpr(add_mpr);
            }
            int add_dmg = vip.get_add_dmg();
            if (add_dmg != 0) {
                pc.addDmgup(add_dmg);
            }
            int add_hit = vip.get_add_hit();
            if (add_hit != 0) {
                pc.addHitup(add_hit);
            }
            int add_bow_dmg = vip.get_add_bow_dmg();
            if (add_bow_dmg != 0) {
                pc.addBowDmgup(add_bow_dmg);
            }
            int add_bow_hit = vip.get_add_bow_hit();
            if (add_bow_hit != 0) {
                pc.addBowHitup(add_bow_hit);
            }
            int add_dmg_r = vip.get_add_dmg_r();
            if (add_dmg_r != 0) {
                pc.addDamageReductionByArmor(add_dmg_r);
            }
            int add_magic_r = vip.get_add_magic_r();
            if (add_magic_r != 0) {
                pc.add_magic_modifier_dmg(add_magic_r);
            }
            int add_mr = vip.get_add_mr();
            if (add_mr != 0) {
                pc.addMr(add_mr);
                spmr = true;
            }
            int add_sp = vip.get_add_sp();
            if (add_sp != 0) {
                pc.addSp(add_sp);
                spmr = true;
            }
            int add_fire = vip.get_add_fire();
            if (add_fire != 0) {
                pc.addFire(add_fire);
                attr = true;
            }
            int add_wind = vip.get_add_wind();
            if (add_wind != 0) {
                pc.addWind(add_wind);
                attr = true;
            }
            int add_earth = vip.get_add_earth();
            if (add_earth != 0) {
                pc.addEarth(add_earth);
                attr = true;
            }
            int add_water = vip.get_add_water();
            if (add_water != 0) {
                pc.addWater(add_water);
                attr = true;
            }
            int add_stun = vip.get_add_stun();
            if (add_stun != 0) {
                pc.addRegistStun(add_stun);
            }
            int add_stone = vip.get_add_stone();
            if (add_stone != 0) {
                pc.addRegistStone(add_stone);
            }
            int add_sleep = vip.get_add_sleep();
            if (add_sleep != 0) {
                pc.addRegistSleep(add_sleep);
            }
            int add_freeze = vip.get_add_freeze();
            if (add_freeze != 0) {
                pc.add_regist_freeze(add_freeze);
            }
            int add_sustain = vip.get_add_sustain();
            if (add_sustain != 0) {
                pc.addRegistSustain(add_sustain);
            }
            int add_blind = vip.get_add_blind();
            if (add_blind != 0) {
                pc.addRegistBlind(add_blind);
            }
            pc.sendPackets(new S_ServerMessage(name.toString()));
            if (status) {
                pc.sendPackets(new S_OwnCharStatus(pc));
            }
            else {
                if (status2) {
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
                if (attr) {
                    pc.sendPackets(new S_OwnCharAttrDef(pc));
                }
            }
            if (spmr) {
                pc.sendPackets(new S_SPMR(pc));
            }
            return;
        }
    }
    
    public void deleItemVIP(L1PcInstance pc, int item_id) {
        if (!ItemAll._VIPList.containsKey((Integer)item_id)) {
            return;
        }
        else {
            L1ItemAll vip = ItemAll._VIPList.get((Integer)item_id);
            boolean status = false;
            boolean status2 = false;
            boolean spmr = false;
            boolean attr = false;
            String itemName = vip.getItem_name();
            pc.sendPackets(new S_ServerMessage(166, "\\aE" + itemName + " 時間效果結束"));
            StringBuilder name = new StringBuilder();
            int add_str = vip.get_add_str();
            if (add_str != 0) {
                pc.addStr(-add_str);
                status2 = true;
            }
            int add_dex = vip.get_add_dex();
            if (add_dex != 0) {
                pc.addDex(-add_dex);
                status2 = true;
            }
            int add_con = vip.get_add_con();
            if (add_con != 0) {
                pc.addCon(-add_con);
                status2 = true;
            }
            int add_int = vip.get_add_int();
            if (add_int != 0) {
                pc.addInt(-add_int);
                status2 = true;
            }
            int add_wis = vip.get_add_wis();
            if (add_wis != 0) {
                pc.addWis(-add_wis);
                status2 = true;
            }
            int add_cha = vip.get_add_cha();
            if (add_cha != 0) {
                pc.addCha(-add_cha);
                status2 = true;
            }
            int add_ac = vip.get_add_ac();
            if (add_ac != 0) {
                pc.addAc(add_ac);
                attr = true;
            }
            int add_hp = vip.get_add_hp();
            if (add_hp != 0) {
                pc.addMaxHp(-add_hp);
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                if (pc.isInParty()) {
                    pc.getParty().updateMiniHP(pc);
                }
            }
            int add_mp = vip.get_add_mp();
            if (add_mp != 0) {
                pc.addMaxMp(-add_mp);
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
            int add_hpr = vip.get_add_hpr();
            if (add_hpr != 0) {
                pc.addHpr(-add_hpr);
            }
            int add_mpr = vip.get_add_mpr();
            if (add_mpr != 0) {
                pc.addMpr(-add_mpr);
            }
            int add_dmg = vip.get_add_dmg();
            if (add_dmg != 0) {
                pc.addDmgup(-add_dmg);
            }
            int add_hit = vip.get_add_hit();
            if (add_hit != 0) {
                pc.addHitup(-add_hit);
            }
            int add_bow_dmg = vip.get_add_bow_dmg();
            if (add_bow_dmg != 0) {
                pc.addBowDmgup(-add_bow_dmg);
            }
            int add_bow_hit = vip.get_add_bow_hit();
            if (add_bow_hit != 0) {
                pc.addBowHitup(-add_bow_hit);
            }
            int add_dmg_r = vip.get_add_dmg_r();
            if (add_dmg_r != 0) {
                pc.addDamageReductionByArmor(-add_dmg_r);
            }
            int add_magic_r = vip.get_add_magic_r();
            if (add_magic_r != 0) {
                pc.add_magic_modifier_dmg(-add_magic_r);
            }
            int add_mr = vip.get_add_mr();
            if (add_mr != 0) {
                pc.addMr(-add_mr);
                spmr = true;
            }
            int add_sp = vip.get_add_sp();
            if (add_sp != 0) {
                pc.addSp(-add_sp);
                spmr = true;
            }
            int add_fire = vip.get_add_fire();
            if (add_fire != 0) {
                pc.addFire(-add_fire);
                attr = true;
            }
            int add_wind = vip.get_add_wind();
            if (add_wind != 0) {
                pc.addWind(-add_wind);
                attr = true;
            }
            int add_earth = vip.get_add_earth();
            if (add_earth != 0) {
                pc.addEarth(-add_earth);
                attr = true;
            }
            int add_water = vip.get_add_water();
            if (add_water != 0) {
                pc.addWater(-add_water);
                attr = true;
            }
            int add_stun = vip.get_add_stun();
            if (add_stun != 0) {
                pc.addRegistStun(-add_stun);
            }
            int add_stone = vip.get_add_stone();
            if (add_stone != 0) {
                pc.addRegistStone(-add_stone);
            }
            int add_sleep = vip.get_add_sleep();
            if (add_sleep != 0) {
                pc.addRegistSleep(-add_sleep);
            }
            int add_freeze = vip.get_add_freeze();
            if (add_freeze != 0) {
                pc.add_regist_freeze(-add_freeze);
            }
            int add_sustain = vip.get_add_sustain();
            if (add_sustain != 0) {
                pc.addRegistSustain(-add_sustain);
            }
            int add_blind = vip.get_add_blind();
            if (add_blind != 0) {
                pc.addRegistBlind(-add_blind);
            }
            pc.sendPackets(new S_ServerMessage(name.toString()));
            if (status) {
                pc.sendPackets(new S_OwnCharStatus(pc));
            }
            else {
                if (status2) {
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                }
                if (attr) {
                    pc.sendPackets(new S_OwnCharAttrDef(pc));
                }
            }
            if (spmr) {
                pc.sendPackets(new S_SPMR(pc));
            }
            return;
        }
    }
}
