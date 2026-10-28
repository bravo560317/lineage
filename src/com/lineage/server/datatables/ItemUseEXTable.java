package com.lineage.server.datatables;

import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_SkillSound;
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
import org.apache.commons.logging.LogFactory;
import com.lineage.server.utils.BinaryOutputStream;
import java.util.HashMap;
import java.util.ArrayList;
import com.lineage.server.templates.L1ItemUseEX;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ItemUseEXTable
{
    private static final Log _log;
    public static final Map<Integer, L1ItemUseEX> _list;
    public static final Map<Integer, ArrayList<Integer>> _type;
    private static final HashMap<Integer, BinaryOutputStream> _os;
    private static ItemUseEXTable _instance;
    
    static {
        _log = LogFactory.getLog(ItemUseEXTable.class);
        _list = new HashMap();
        _type = new HashMap();
        _os = new HashMap();
    }
    
    public static ItemUseEXTable get() {
        if (ItemUseEXTable._instance == null) {
            ItemUseEXTable._instance = new ItemUseEXTable();
        }
        return ItemUseEXTable._instance;
    }
    
    private ItemUseEXTable() {
        this.load();
    }
    
    private void load() {
        Connection co = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Label_0941: {
            try {
                co = DatabaseFactory.get().getConnection();
                pstm = co.prepareStatement("SELECT * FROM `w_道具使用類別_1`");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    int item_id = rs.getInt("item_id");
                    String itemName = rs.getString("item_name");
                    String Message = rs.getString("Message");
                    int type = rs.getInt("type");
                    int type_mod = rs.getInt("type_mod");
                    int buff_time = rs.getInt("has_time");
                    int buff_gfx = rs.getInt("gfix");
                    boolean buff_save = rs.getBoolean("buff_save");
                    int poly = rs.getInt("poly");
                    int cancellation = rs.getInt("cancellation");
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
                    int double_score = rs.getInt("陣營積分加倍");
                    int removeitem = rs.getInt("removeitem");
                    String type_msg = rs.getString("type_msg");
                    L1ItemUseEX vip = new L1ItemUseEX();
                    vip.set_type(type);
                    vip.setItem_name(itemName);
                    vip.setMessage(Message);
                    vip.set_type_mod(type_mod);
                    vip.set_buff_time(buff_time);
                    vip.set_buff_gfx(buff_gfx);
                    vip.set_buff_save(buff_save);
                    vip.set_type(type);
                    vip.set_cancellation(cancellation);
                    vip.set_poly(poly);
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
                    vip.settype_msg(type_msg);
                    vip.set_double_score(double_score);
                    vip.set_removeitem(removeitem);
                    ItemUseEXTable._list.put(item_id, vip);
                    ArrayList<Integer> map = ItemUseEXTable._type.get((Integer)type);
                    if (map == null) {
                        map = new ArrayList();
                        map.add(item_id);
                        ItemUseEXTable._type.put(type, map);
                    }
                    else {
                        map.add(item_id);
                    }
                }
            }
            catch (SQLException e) {
                ItemUseEXTable._log.error(e.getLocalizedMessage(), e);
                break Label_0941;
            }
            finally {
                SQLUtil.close(rs);
                SQLUtil.close(pstm);
                SQLUtil.close(co);
            }
        }
        ItemUseEXTable._log.info("載入進階版效果道具數量: " + ItemUseEXTable._list.size());
    }
    
    public boolean checkItem(int item_id) {
        return ItemUseEXTable._list.containsKey((Integer)item_id);
    }
    
    public BinaryOutputStream getOS(int item_id) {
        return ItemUseEXTable._os.get((Integer)item_id);
    }
    
    public static L1ItemUseEX getItemPro(int itemId) {
        if (ItemUseEXTable._list == null) {
            return null;
        }
        else if (!ItemUseEXTable._list.containsKey((Integer)itemId)) {
            return null;
        }
        else {
            return ItemUseEXTable._list.get((Integer)itemId);
        }
    }
    
    public static void getSaveSkillid(L1PcInstance pc) {
        for (Integer skillId : ItemUseEXTable._list.keySet()) {
            if ((ItemUseEXTable._list.get(skillId)).is_buff_save()) {
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
        if (!ItemUseEXTable._list.containsKey((Integer)item_id)) {
            return false;
        }
        else {
            L1ItemUseEX value = ItemUseEXTable._list.get((Integer)item_id);
            String itemName = value.getItem_name();
            if (pc.hasSkillEffect(item_id) && value.get_type_mod() == 0) {
                pc.sendPackets(new S_ServerMessage(166, itemName + "\\aD道具賦予時效剩餘\\aE" + pc.getSkillEffectTimeSec(item_id) + "秒"));
                return false;
            }
            else {
                if (value.get_type() != 0) {
                    for (Integer buff_id : ItemUseEXTable._type.get((Integer)value.get_type())) {
                        if (pc.hasSkillEffect(buff_id)) {
                            if (value.get_type_mod() == 0) {
                                pc.sendPackets(new S_ServerMessage(value.gettype_msg()));
                                return false;
                            }
                            else {
                                pc.removeSkillEffect(buff_id);
                            }
                        }
                    }
                }
                if (buff_time != 0) {
                    pc.setSkillEffect(item_id, value.get_buff_time() * 1000);
                    pc.sendPackets(new S_ServerMessage(166, "\\aD道具賦予時效剩餘:\\aE" + pc.getSkillEffectTimeSec(item_id) + "秒"));
                    pc.sendPackets(new S_ServerMessage(value.getMessage()));
                }
                else {
                    pc.setSkillEffect(item_id, value.get_buff_time() * 1000);
                    pc.sendPackets(new S_ServerMessage(166, "\\aD道具賦予時效剩餘:\\aE" + pc.getSkillEffectTimeSec(item_id) + "秒"));
                }
                if (value.get_buff_gfx() != 0) {
                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), value.get_buff_gfx()));
                }
                StringBuilder name = new StringBuilder();
                boolean status = false;
                boolean status2 = false;
                boolean spmr = false;
                boolean attr = false;
                if (value.getMessage() != null) {
                    pc.sendPackets(new S_ServerMessage("\\aD" + value.getMessage()));
                }
                if (value.get_poly() != -1) {
                    if (pc.hasSkillEffect(67)) {
                        pc.removeSkillEffect(67);
                    }
                    L1PolyMorph.doPoly(pc, value.get_poly(), value.get_buff_time(), 1);
                }
                if (value.get_double_score() != 0) {
                    pc.setdouble_score(value.get_double_score());
                }
                int add_str = value.get_add_str();
                if (add_str != 0) {
                    pc.addStr(add_str);
                    status2 = true;
                }
                int add_dex = value.get_add_dex();
                if (add_dex != 0) {
                    pc.addDex(add_dex);
                    status2 = true;
                }
                int add_con = value.get_add_con();
                if (add_con != 0) {
                    pc.addCon(add_con);
                    status2 = true;
                }
                int add_int = value.get_add_int();
                if (add_int != 0) {
                    pc.addInt(add_int);
                    status2 = true;
                }
                int add_wis = value.get_add_wis();
                if (add_wis != 0) {
                    pc.addWis(add_wis);
                    status2 = true;
                }
                int add_cha = value.get_add_cha();
                if (add_cha != 0) {
                    pc.addCha(add_cha);
                    status2 = true;
                }
                int add_ac = value.get_add_ac();
                if (add_ac != 0) {
                    pc.addAc(-add_ac);
                    attr = true;
                }
                int add_hp = value.get_add_hp();
                if (add_hp != 0) {
                    pc.addMaxHp(add_hp);
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    if (pc.isInParty()) {
                        pc.getParty().updateMiniHP(pc);
                    }
                }
                int add_mp = value.get_add_mp();
                if (add_mp != 0) {
                    pc.addMaxMp(add_mp);
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                }
                int add_hpr = value.get_add_hpr();
                if (add_hpr != 0) {
                    pc.addHpr(add_hpr);
                }
                int add_mpr = value.get_add_mpr();
                if (add_mpr != 0) {
                    pc.addMpr(add_mpr);
                }
                int add_dmg = value.get_add_dmg();
                if (add_dmg != 0) {
                    pc.addDmgup(add_dmg);
                }
                int add_hit = value.get_add_hit();
                if (add_hit != 0) {
                    pc.addHitup(add_hit);
                }
                int add_bow_dmg = value.get_add_bow_dmg();
                if (add_bow_dmg != 0) {
                    pc.addBowDmgup(add_bow_dmg);
                }
                int add_bow_hit = value.get_add_bow_hit();
                if (add_bow_hit != 0) {
                    pc.addBowHitup(add_bow_hit);
                }
                int add_dmg_r = value.get_add_dmg_r();
                if (add_dmg_r != 0) {
                    pc.addDamageReductionByArmor(add_dmg_r);
                }
                int add_magic_r = value.get_add_magic_r();
                if (add_magic_r != 0) {
                    pc.add_magic_reduction_dmg(add_magic_r);
                }
                int add_mr = value.get_add_mr();
                if (add_mr != 0) {
                    pc.addMr(add_mr);
                    spmr = true;
                }
                int add_sp = value.get_add_sp();
                if (add_sp != 0) {
                    pc.addSp(add_sp);
                    spmr = true;
                }
                int add_fire = value.get_add_fire();
                if (add_fire != 0) {
                    pc.addFire(add_fire);
                    attr = true;
                }
                int add_wind = value.get_add_wind();
                if (add_wind != 0) {
                    pc.addWind(add_wind);
                    attr = true;
                }
                int add_earth = value.get_add_earth();
                if (add_earth != 0) {
                    pc.addEarth(add_earth);
                    attr = true;
                }
                int add_water = value.get_add_water();
                if (add_water != 0) {
                    pc.addWater(add_water);
                    attr = true;
                }
                int add_stun = value.get_add_stun();
                if (add_stun != 0) {
                    pc.addRegistStun(add_stun);
                }
                int add_stone = value.get_add_stone();
                if (add_stone != 0) {
                    pc.addRegistStone(add_stone);
                }
                int add_sleep = value.get_add_sleep();
                if (add_sleep != 0) {
                    pc.addRegistSleep(add_sleep);
                }
                int add_freeze = value.get_add_freeze();
                if (add_freeze != 0) {
                    pc.add_regist_freeze(add_freeze);
                }
                int add_sustain = value.get_add_sustain();
                if (add_sustain != 0) {
                    pc.addRegistSustain(add_sustain);
                }
                int add_blind = value.get_add_blind();
                if (add_blind != 0) {
                    pc.addRegistBlind(add_blind);
                }
                pc.sendPackets(new S_OwnCharStatus(pc));
                pc.sendPackets(new S_OwnCharStatus2(pc));
                pc.sendPackets(new S_OwnCharAttrDef(pc));
                pc.sendPackets(new S_SPMR(pc));
                pc.sendPackets(new S_ServerMessage(name.toString()));
                return true;
            }
        }
    }
    
    public void remove(L1PcInstance pc, int item_id) {
        L1ItemUseEX value = ItemUseEXTable._list.get((Integer)item_id);
        boolean status = false;
        boolean status2 = false;
        boolean spmr = false;
        boolean attr = false;
        String itemName = value.getItem_name();
        pc.sendPackets(new S_ServerMessage(166, "\\aE[" + itemName + "]道具賦予時間結束"));
        StringBuilder name = new StringBuilder();
        if (value.getMessage() != null) {
            pc.sendPackets(new S_ServerMessage("\\aE" + value.getMessage() + " 效果結束"));
        }
        if (value.get_double_score() != 0) {
            pc.setdouble_score(-value.get_double_score());
        }
        int add_str = value.get_add_str();
        if (add_str != 0) {
            pc.addStr(-add_str);
            status2 = true;
        }
        int add_dex = value.get_add_dex();
        if (add_dex != 0) {
            pc.addDex(-add_dex);
            status2 = true;
        }
        int add_con = value.get_add_con();
        if (add_con != 0) {
            pc.addCon(-add_con);
            status2 = true;
        }
        int add_int = value.get_add_int();
        if (add_int != 0) {
            pc.addInt(-add_int);
            status2 = true;
        }
        int add_wis = value.get_add_wis();
        if (add_wis != 0) {
            pc.addWis(-add_wis);
            status2 = true;
        }
        int add_cha = value.get_add_cha();
        if (add_cha != 0) {
            pc.addCha(-add_cha);
            status2 = true;
        }
        int add_ac = value.get_add_ac();
        if (add_ac != 0) {
            pc.addAc(add_ac);
            attr = true;
        }
        int add_hp = value.get_add_hp();
        if (add_hp != 0) {
            pc.addMaxHp(-add_hp);
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            if (pc.isInParty()) {
                pc.getParty().updateMiniHP(pc);
            }
        }
        int add_mp = value.get_add_mp();
        if (add_mp != 0) {
            pc.addMaxMp(-add_mp);
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
        }
        int add_hpr = value.get_add_hpr();
        if (add_hpr != 0) {
            pc.addHpr(-add_hpr);
        }
        int add_mpr = value.get_add_mpr();
        if (add_mpr != 0) {
            pc.addMpr(-add_mpr);
        }
        int add_dmg = value.get_add_dmg();
        if (add_dmg != 0) {
            pc.addDmgup(-add_dmg);
        }
        int add_hit = value.get_add_hit();
        if (add_hit != 0) {
            pc.addHitup(-add_hit);
        }
        int add_bow_dmg = value.get_add_bow_dmg();
        if (add_bow_dmg != 0) {
            pc.addBowDmgup(-add_bow_dmg);
        }
        int add_bow_hit = value.get_add_bow_hit();
        if (add_bow_hit != 0) {
            pc.addBowHitup(-add_bow_hit);
        }
        int add_dmg_r = value.get_add_dmg_r();
        if (add_dmg_r != 0) {
            pc.addDamageReductionByArmor(-add_dmg_r);
        }
        int add_magic_r = value.get_add_magic_r();
        if (add_magic_r != 0) {
            pc.add_magic_reduction_dmg(-add_magic_r);
        }
        int add_mr = value.get_add_mr();
        if (add_mr != 0) {
            pc.addMr(-add_mr);
            spmr = true;
        }
        int add_sp = value.get_add_sp();
        if (add_sp != 0) {
            pc.addSp(-add_sp);
            spmr = true;
        }
        int add_fire = value.get_add_fire();
        if (add_fire != 0) {
            pc.addFire(-add_fire);
            attr = true;
        }
        int add_wind = value.get_add_wind();
        if (add_wind != 0) {
            pc.addWind(-add_wind);
            attr = true;
        }
        int add_earth = value.get_add_earth();
        if (add_earth != 0) {
            pc.addEarth(-add_earth);
            attr = true;
        }
        int add_water = value.get_add_water();
        if (add_water != 0) {
            pc.addWater(-add_water);
            attr = true;
        }
        int add_stun = value.get_add_stun();
        if (add_stun != 0) {
            pc.addRegistStun(-add_stun);
        }
        int add_stone = value.get_add_stone();
        if (add_stone != 0) {
            pc.addRegistStone(-add_stone);
        }
        int add_sleep = value.get_add_sleep();
        if (add_sleep != 0) {
            pc.addRegistSleep(-add_sleep);
        }
        int add_freeze = value.get_add_freeze();
        if (add_freeze != 0) {
            pc.add_regist_freeze(-add_freeze);
        }
        int add_sustain = value.get_add_sustain();
        if (add_sustain != 0) {
            pc.addRegistSustain(-add_sustain);
        }
        int add_blind = value.get_add_blind();
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
    }
}
