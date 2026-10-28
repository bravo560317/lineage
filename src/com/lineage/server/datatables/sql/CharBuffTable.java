package com.lineage.server.datatables.sql;

import com.lineage.server.templates.L1ItemUseEX;
import com.lineage.server.model.skill.skillmode.SkillMode;
import java.util.Iterator;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_PacketBoxCooking;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Cooking;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_PacketBoxThirdSpeed;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.datatables.ItemUseEXTable;
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
import com.lineage.server.templates.L1BuffTmp;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharBuffStorage;

public class CharBuffTable implements CharBuffStorage
{
    private static final Log _log;
    private static final Map<Integer, ArrayList<L1BuffTmp>> _buffMap;
    private static final int[] _buffSkill;
    
    static {
        _log = LogFactory.getLog(CharBuffTable.class);
        _buffMap = new HashMap();
        _buffSkill = new int[] { 2, 67, 43, 54, 3, 99, 151, 159, 168, 52, 101, 150, 26, 42, 109, 110, 114, 115, 117, 148, 155, 163, 149, 156, 166, 186, 188, 1002, 4002, 1000, 1001, 1016, 998, 1017, 20000, 3000, 3008, 3001, 3009, 3002, 3010, 3003, 3011, 3004, 3012, 3005, 3013, 3006, 3014, 3016, 3024, 3017, 3025, 3018, 3026, 3019, 3027, 3020, 3028, 3021, 3029, 3022, 3030, 3032, 3040, 3033, 3041, 3034, 3042, 3035, 3043, 3036, 3044, 3037, 3045, 3038, 3046, 3007, 3015, 3023, 3031, 3039, 3047, 3048, 3049, 3050, 3051, 6666, 6667, 6668, 6669, 6670, 6671, 6672, 6673, 6674, 6675, 6676, 6677, 6678, 6679, 6680, 6681, 5000, 5001, 5002, 5003, 5004, 6683, 6684, 6685, 6686, 6687, 6688, 6689, 6797, 6798, 6799, 6800, 9000, 9001, 9002, 9003, 9004, 8000, 8591, 7002, 7003, 9990, 8060 };
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_buff`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_obj_id = rs.getInt("char_obj_id");
                if (CharObjidTable.get().isChar(char_obj_id) != null) {
                    int skill_id = rs.getInt("skill_id");
                    int remaining_time = rs.getInt("remaining_time");
                    int poly_id = rs.getInt("poly_id");
                    L1BuffTmp buffTmp = new L1BuffTmp();
                    buffTmp.set_char_obj_id(char_obj_id);
                    buffTmp.set_skill_id(skill_id);
                    buffTmp.set_remaining_time(remaining_time);
                    buffTmp.set_poly_id(poly_id);
                    addMap(char_obj_id, buffTmp);
                }
                else {
                    delete(char_obj_id);
                }
            }
        }
        catch (SQLException e) {
            CharBuffTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharBuffTable._log.info("載入保留技能紀錄資料數量: " + CharBuffTable._buffMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        ArrayList<?> list = (ArrayList)CharBuffTable._buffMap.get((Integer)objid);
        if (list != null) {
            list.clear();
        }
        CharBuffTable._buffMap.remove((Integer)objid);
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_buff` WHERE `char_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            CharBuffTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addMap(int objId, L1BuffTmp buffTmp) {
        ArrayList<L1BuffTmp> list = CharBuffTable._buffMap.get((Integer)objId);
        if (list == null) {
            ArrayList<L1BuffTmp> newlist = new ArrayList();
            newlist.add(buffTmp);
            CharBuffTable._buffMap.put(objId, newlist);
        }
        else {
            list.add(buffTmp);
        }
    }
    
    @Override
    public void saveBuff(L1PcInstance pc) {
        int[] buffSkill;
        for (int length = (buffSkill = CharBuffTable._buffSkill).length, i = 0; i < length; ++i) {
            int skillId = buffSkill[i];
            int timeSec = pc.getSkillEffectTimeSec(skillId);
            if (timeSec > 0) {
                int polyId = -1;
                if (skillId == 67) {
                    polyId = pc.getTempCharGfx();
                }
                storeBuff(pc.getId(), skillId, timeSec, polyId);
            }
        }
        ItemUseEXTable.getSaveSkillid(pc);
        pc.clearSkillEffectTimer();
    }
    
    public static void storeBuff(int objId, int skillId, int time) {
        storeBuff(objId, skillId, time, -1);
    }
    
    public static void storeBuff(int objId, int skillId, int time, int polyId) {
        L1BuffTmp buffTmp = new L1BuffTmp();
        buffTmp.set_char_obj_id(objId);
        buffTmp.set_skill_id(skillId);
        buffTmp.set_remaining_time(time);
        buffTmp.set_poly_id(polyId);
        addMap(objId, buffTmp);
        storeBuffR(buffTmp);
    }
    
    @Override
    public void buff(L1PcInstance pc) {
        int objid = pc.getId();
        ArrayList<L1BuffTmp> list = CharBuffTable._buffMap.get((Integer)objid);
        if (list != null) {
            for (L1BuffTmp buffTmp : list) {
                int skill_id = buffTmp.get_skill_id();
                int remaining_time = buffTmp.get_remaining_time();
                int poly_id = buffTmp.get_poly_id();
                if (remaining_time > 0) {
                    if (skill_id == 67) {
                        L1PolyMorph.doPoly(pc, poly_id, remaining_time, 0);
                    }
                    else {
                        switch (skill_id) {
                            case 998: {
                                pc.sendPackets(new S_PacketBoxThirdSpeed(remaining_time));
                                pc.sendPacketsAll(new S_Liquor(pc.getId(), 8));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 1000: {
                                pc.sendPackets(new S_SkillBrave(pc.getId(), 1, remaining_time));
                                pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 1, 0));
                                pc.setBraveSpeed(1);
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 1001: {
                                pc.sendPackets(new S_SkillHaste(pc.getId(), 1, remaining_time));
                                pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 1, 0));
                                pc.setMoveSpeed(1);
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 1002: {
                                pc.sendPackets(new S_PacketBox(34, remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 1016: {
                                pc.sendPackets(new S_SkillBrave(pc.getId(), 3, remaining_time));
                                pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 3, 0));
                                pc.setBraveSpeed(3);
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 3000:
                            case 3001:
                            case 3002:
                            case 3003:
                            case 3004:
                            case 3005:
                            case 3006:
                            case 3007:
                            case 3008:
                            case 3009:
                            case 3010:
                            case 3011:
                            case 3012:
                            case 3013:
                            case 3014:
                            case 3015:
                            case 3016:
                            case 3017:
                            case 3018:
                            case 3019:
                            case 3020:
                            case 3021:
                            case 3022:
                            case 3023:
                            case 3024:
                            case 3025:
                            case 3026:
                            case 3027:
                            case 3028:
                            case 3029:
                            case 3030:
                            case 3031:
                            case 3032:
                            case 3033:
                            case 3034:
                            case 3035:
                            case 3036:
                            case 3037:
                            case 3038:
                            case 3039:
                            case 3040:
                            case 3041:
                            case 3042:
                            case 3043:
                            case 3044:
                            case 3045:
                            case 3046:
                            case 3047:
                            case 3048:
                            case 3049:
                            case 3050:
                            case 3051: {
                                L1Cooking.eatCooking(pc, skill_id, remaining_time);
                                continue;
                            }
                            case 4002:
                            case 7004: {
                                pc.sendPackets(new S_PacketBox(36, remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5000: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水150% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5001: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水175% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5002: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水200% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5003: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水225% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5004: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水250% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5005: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水275% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5006: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水300% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5007: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水325% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5008: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水350% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5009: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水375% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5010: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水400% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5011: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水450% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5012: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水500% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5013: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水550% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 5014: {
                                pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水600% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 6666: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水130% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6667: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水150% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6668: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水170% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6669: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水200% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6670: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水250% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6671: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水300% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6672: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水350% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6673: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水400% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6674: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水450% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6675: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水500% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6676: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水550% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6677: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水600% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6678: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水650% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6679: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水700% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6680: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水750% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 6681: {
                                pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水800% 剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                continue;
                            }
                            case 7002: {
                                pc.sendPackets(new S_ServerMessage("\\fY積分加倍(2倍)作用中剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 7003: {
                                pc.sendPackets(new S_ServerMessage("\\fY積分加倍(3倍)作用中剩餘時間(秒):" + remaining_time));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 8000: {
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 8060: {
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                pc.addHitup(3);
                                pc.addDmgup(3);
                                pc.addBowHitup(3);
                                pc.addBowDmgup(3);
                                pc.addSp(3);
                                pc.sendPackets(new S_SPMR(pc));
                                continue;
                            }
                            case 8591: {
                                pc.set_mazu(true);
                                pc.setSkillEffect(8591, remaining_time * 1000);
                                pc.set_mazu_time((long)remaining_time);
                                pc.sendPackets(new S_SystemMessage("媽祖狀態還有" + remaining_time + "秒。"));
                                continue;
                            }
                            case 9000:
                            case 9001:
                            case 9002:
                            case 9003:
                            case 9004: {
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 9990: {
                                pc.sendPackets(new S_ServerMessage("\\fX您目前監禁時間剩餘時間(秒):" + remaining_time));
                                pc.sendPackets(new S_ServerMessage("\\fX請保持角色在線以扣除監禁時間。"));
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            case 52788: {
                                pc.setSkillEffect(skill_id, remaining_time * 1000);
                                continue;
                            }
                            default: {
                                SkillMode mode = L1SkillMode.get().getSkill(skill_id);
                                if (mode != null) {
                                    try {
                                        mode.start(pc, pc, null, remaining_time);
                                    }
                                    catch (Exception e) {
                                        CharBuffTable._log.error(e.getLocalizedMessage(), e);
                                    }
                                    continue;
                                }
                                else if (ItemUseEXTable.get().checkItem(skill_id)) {
                                    L1ItemUseEX vip = ItemUseEXTable.getItemPro(skill_id);
                                    if (vip != null) {
                                        int add_str = vip.get_add_str();
                                        if (add_str != 0) {
                                            pc.addStr(add_str);
                                        }
                                        int add_dex = vip.get_add_dex();
                                        if (add_dex != 0) {
                                            pc.addDex(add_dex);
                                        }
                                        int add_con = vip.get_add_con();
                                        if (add_con != 0) {
                                            pc.addCon(add_con);
                                        }
                                        int add_int = vip.get_add_int();
                                        if (add_int != 0) {
                                            pc.addInt(add_int);
                                        }
                                        int add_wis = vip.get_add_wis();
                                        if (add_wis != 0) {
                                            pc.addWis(add_wis);
                                        }
                                        int add_cha = vip.get_add_cha();
                                        if (add_cha != 0) {
                                            pc.addCha(add_cha);
                                        }
                                        int add_ac = vip.get_add_ac();
                                        if (add_ac != 0) {
                                            pc.addAc(-add_ac);
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
                                        }
                                        int add_sp = vip.get_add_sp();
                                        if (add_sp != 0) {
                                            pc.addSp(add_sp);
                                        }
                                        int add_fire = vip.get_add_fire();
                                        if (add_fire != 0) {
                                            pc.addFire(add_fire);
                                        }
                                        int add_wind = vip.get_add_wind();
                                        if (add_wind != 0) {
                                            pc.addWind(add_wind);
                                        }
                                        int add_earth = vip.get_add_earth();
                                        if (add_earth != 0) {
                                            pc.addEarth(add_earth);
                                        }
                                        int add_water = vip.get_add_water();
                                        if (add_water != 0) {
                                            pc.addWater(add_water);
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
                                        pc.sendPackets(new S_OwnCharStatus(pc));
                                        pc.sendPackets(new S_OwnCharStatus2(pc));
                                        pc.sendPackets(new S_OwnCharAttrDef(pc));
                                        pc.sendPackets(new S_SPMR(pc));
                                        pc.setSkillEffect(skill_id, remaining_time * 1000);
                                        String itemName = vip.getItem_name();
                                        if (itemName != null) {
                                            pc.sendPackets(new S_ServerMessage(166, itemName + "\\aD賦予時效剩餘:\\aE" + remaining_time + "秒"));
                                        }
                                        if (vip.getMessage() != null) {
                                            pc.sendPackets(new S_ServerMessage("\\aD" + vip.getMessage()));
                                        }
                                        if (vip.get_add_exp() > 0) {
                                            pc.sendPackets(new S_PacketBoxCooking(pc, 32, remaining_time));
                                            continue;
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                    else {
                                        continue;
                                    }
                                }
                                else {
                                    L1SkillUse l1skilluse = new L1SkillUse();
                                    l1skilluse.handleCommands(pc, skill_id, pc.getId(), pc.getX(), pc.getY(), remaining_time, 1);
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void deleteBuff(L1PcInstance pc) {
        delete(pc.getId());
    }
    
    @Override
    public void deleteBuff(int objid) {
        delete(objid);
    }
    
    private static void storeBuffR(L1BuffTmp buffTmp) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `character_buff` SET `char_obj_id`=?,`skill_id`=?,`remaining_time`=?,`poly_id`=?");
            ps.setInt(1, buffTmp.get_char_obj_id());
            ps.setInt(2, buffTmp.get_skill_id());
            ps.setInt(3, buffTmp.get_remaining_time());
            ps.setInt(4, buffTmp.get_poly_id());
            ps.execute();
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
