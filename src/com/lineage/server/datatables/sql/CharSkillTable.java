package com.lineage.server.datatables.sql;

import java.util.Iterator;
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
import com.lineage.server.templates.L1UserSkillTmp;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharSkillStorage;

public class CharSkillTable implements CharSkillStorage
{
    private static final Log _log;
    private static final Map<Integer, ArrayList<L1UserSkillTmp>> _skillMap;
    
    static {
        _log = LogFactory.getLog(CharSkillTable.class);
        _skillMap = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `character_skills`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_obj_id = rs.getInt("char_obj_id");
                if (CharObjidTable.get().isChar(char_obj_id) != null) {
                    int skill_id = rs.getInt("skill_id");
                    String skill_name = rs.getString("skill_name");
                    int is_active = rs.getInt("is_active");
                    int activetimeleft = rs.getInt("activetimeleft");
                    L1UserSkillTmp userSkill = new L1UserSkillTmp();
                    userSkill.set_char_obj_id(char_obj_id);
                    userSkill.set_skill_id(skill_id);
                    userSkill.set_skill_name(skill_name);
                    userSkill.set_is_active(is_active);
                    userSkill.set_activetimeleft(activetimeleft);
                    addMap(char_obj_id, userSkill);
                }
                else {
                    deleteBuff(char_obj_id);
                }
            }
        }
        catch (SQLException e) {
            CharSkillTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        CharSkillTable._log.info("載入人物技能紀錄資料數量: " + CharSkillTable._skillMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void deleteBuff(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_skills` WHERE `char_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            CharSkillTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addMap(int objId, L1UserSkillTmp skillTmp) {
        ArrayList<L1UserSkillTmp> list = CharSkillTable._skillMap.get((Integer)objId);
        if (list == null) {
            list = new ArrayList();
            list.add(skillTmp);
        }
        else {
            list.add(skillTmp);
        }
        CharSkillTable._skillMap.put(objId, list);
    }
    
    @Override
    public ArrayList<L1UserSkillTmp> skills(int playerobjid) {
        return CharSkillTable._skillMap.get((Integer)playerobjid);
    }
    
    @Override
    public void spellMastery(int playerobjid, int skillid, String skillname, int active, int time) {
        if (this.spellCheck(playerobjid, skillid)) {
            return;
        }
        else {
            L1UserSkillTmp userSkill = new L1UserSkillTmp();
            userSkill.set_char_obj_id(playerobjid);
            userSkill.set_skill_id(skillid);
            userSkill.set_skill_name(skillname);
            userSkill.set_is_active(active);
            userSkill.set_activetimeleft(time);
            addMap(playerobjid, userSkill);
            Connection co = null;
            PreparedStatement ps = null;
            try {
                co = DatabaseFactory.get().getConnection();
                ps = co.prepareStatement("INSERT INTO `character_skills` SET `char_obj_id`=?,`skill_id`=?,`skill_name`=?,`is_active`=?,`activetimeleft`=?");
                ps.setInt(1, userSkill.get_char_obj_id());
                ps.setInt(2, userSkill.get_skill_id());
                ps.setString(3, userSkill.get_skill_name());
                ps.setInt(4, userSkill.get_is_active());
                ps.setInt(5, userSkill.get_activetimeleft());
                ps.execute();
            }
            catch (Exception e) {
                CharSkillTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(co);
            }
            return;
        }
    }
    
    @Override
    public void spellLost(int playerobjid, int skillid) {
        ArrayList<L1UserSkillTmp> list = CharSkillTable._skillMap.get((Integer)playerobjid);
        L1UserSkillTmp del = null;
        if (list != null) {
            for (L1UserSkillTmp userSkillTmp : list) {
                if (userSkillTmp.get_skill_id() == skillid) {
                    del = userSkillTmp;
                }
            }
        }
        if (del == null) {
            return;
        }
        else {
            list.remove(del);
            Connection co = null;
            PreparedStatement ps = null;
            try {
                co = DatabaseFactory.get().getConnection();
                ps = co.prepareStatement("DELETE FROM `character_skills` WHERE `char_obj_id`=? AND `skill_id`=?");
                ps.setInt(1, playerobjid);
                ps.setInt(2, skillid);
                ps.execute();
            }
            catch (Exception e) {
                CharSkillTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(co);
            }
            return;
        }
    }
    
    @Override
    public boolean spellCheck(int playerobjid, int skillid) {
        ArrayList<L1UserSkillTmp> list = CharSkillTable._skillMap.get((Integer)playerobjid);
        if (list != null) {
            for (L1UserSkillTmp userSkillTmp : list) {
                if (userSkillTmp.get_skill_id() == skillid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void setAuto(int mode, int objid, int skillid) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("UPDATE `character_skills` SET `is_active`=? WHERE `char_obj_id`=? AND `skill_id`=?");
            ps.setInt(1, mode);
            ps.setInt(2, objid);
            ps.setInt(3, skillid);
            ps.execute();
        }
        catch (Exception e) {
            CharSkillTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
}
