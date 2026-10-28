package com.lineage.server.datatables;

import com.lineage.server.world.World;
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
import com.lineage.server.templates.L1Skills;
import java.util.Map;
import org.apache.commons.logging.Log;

public class SkillsTable
{
    private static final Log _log;
    private static SkillsTable _instance;
    private static final Map<Integer, L1Skills> _skills;
    
    static {
        _log = LogFactory.getLog(SkillsTable.class);
        _skills = new HashMap();
    }
    
    public static SkillsTable get() {
        if (SkillsTable._instance == null) {
            SkillsTable._instance = new SkillsTable();
        }
        return SkillsTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `skills`");
            rs = pstm.executeQuery();
            this.fillSkillsTable(rs);
        }
        catch (SQLException e) {
            SkillsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SkillsTable._log.info("載入技能設置資料數量: " + SkillsTable._skills.size() + "(" + timer.get() + "ms)");
    }
    
    private void fillSkillsTable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            L1Skills l1skills = new L1Skills();
            int skill_id = rs.getInt("skill_id");
            l1skills.setSkillId(skill_id);
            l1skills.setName(rs.getString("name"));
            l1skills.setSkillLevel(rs.getInt("skill_level"));
            l1skills.setSkillNumber(rs.getInt("skill_number"));
            l1skills.setMpConsume(rs.getInt("mpConsume"));
            l1skills.setHpConsume(rs.getInt("hpConsume"));
            l1skills.setItemConsumeId(rs.getInt("itemConsumeId"));
            l1skills.setItemConsumeCount(rs.getInt("itemConsumeCount"));
            l1skills.setReuseDelay(rs.getInt("reuseDelay"));
            l1skills.setBuffDuration(rs.getInt("buffDuration"));
            l1skills.setTarget(rs.getString("target"));
            l1skills.setTargetTo(rs.getInt("target_to"));
            l1skills.setDamageValue(rs.getInt("damage_value"));
            l1skills.setDamageDice(rs.getInt("damage_dice"));
            l1skills.setDamageDiceCount(rs.getInt("damage_dice_count"));
            l1skills.setProbabilityValue(rs.getInt("probability_value"));
            l1skills.setProbabilityDice(rs.getInt("probability_dice"));
            l1skills.setAttr(rs.getInt("attr"));
            l1skills.setType(rs.getInt("type"));
            l1skills.setLawful(rs.getInt("lawful"));
            l1skills.setRanged(rs.getInt("ranged"));
            l1skills.setArea(rs.getInt("area"));
            l1skills.setThrough(rs.getBoolean("through"));
            l1skills.setId(rs.getInt("id"));
            l1skills.setNameId(rs.getString("nameid"));
            l1skills.setActionId(rs.getInt("action_id"));
            l1skills.setCastGfx(rs.getInt("castgfx"));
            l1skills.setCastGfx2(rs.getInt("castgfx2"));
            l1skills.setSysmsgIdHappen(rs.getInt("sysmsgID_happen"));
            l1skills.setSysmsgIdStop(rs.getInt("sysmsgID_stop"));
            l1skills.setSysmsgIdFail(rs.getInt("sysmsgID_fail"));
            l1skills.setdetect_no_delay(rs.getInt("是否偵測無延遲"));
            SkillsTable._skills.put(new Integer(skill_id), l1skills);
        }
    }
    
    public L1Skills getTemplate(int i) {
        return SkillsTable._skills.get(new Integer(i));
    }
    
    public boolean spellCheck(int playerobjid, int skillid) {
        boolean ret = false;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM character_skills WHERE char_obj_id=? AND skill_id=?");
            pstm.setInt(1, playerobjid);
            pstm.setInt(2, skillid);
            rs = pstm.executeQuery();
            ret = rs.next();
        }
        catch (Exception ex) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return ret;
    }
    
    public void spellLost(int playerobjid, int skillid) {
        L1PcInstance pc = (L1PcInstance)World.get().findObject(playerobjid);
        if (pc != null) {
            pc.removeSkillMastery(skillid);
        }
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_skills WHERE char_obj_id=? AND skill_id=?");
            pstm.setInt(1, playerobjid);
            pstm.setInt(2, skillid);
            pstm.execute();
        }
        catch (Exception ex) {}
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
