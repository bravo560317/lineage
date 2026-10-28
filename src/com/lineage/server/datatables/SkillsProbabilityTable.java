package com.lineage.server.datatables;

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

public class SkillsProbabilityTable
{
    private static final Log _log;
    private static SkillsProbabilityTable _instance;
    private final Map<Integer, L1Skills> _skills;
    
    static {
        _log = LogFactory.getLog(SkillsProbabilityTable.class);
    }
    
    public static SkillsProbabilityTable get() {
        if (SkillsProbabilityTable._instance == null) {
            SkillsProbabilityTable._instance = new SkillsProbabilityTable();
        }
        return SkillsProbabilityTable._instance;
    }
    
    private SkillsProbabilityTable() {
        this._skills = new HashMap();
        this.load();
    }
    
    public static void reload() {
        SkillsProbabilityTable oldInstance = SkillsProbabilityTable._instance;
        SkillsProbabilityTable._instance = new SkillsProbabilityTable();
        oldInstance._skills.clear();
        oldInstance = null;
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_技能機率調整`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int skill_id = rs.getInt("skill_id");
                L1Skills l1skills = SkillsTable.get().getTemplate(skill_id);
                if (l1skills == null) {
                    SkillsProbabilityTable._log.error("技能準確率設置資料 不存在技能編號: " + skill_id);
                }
                l1skills.setSkillId(skill_id);
                l1skills.setName(rs.getString("技能名稱"));
                l1skills.settype1(rs.getInt("type1"));
                l1skills.settype2(rs.getInt("type2"));
                l1skills.settype3(rs.getInt("type3"));
                l1skills.settype4(rs.getInt("type4"));
                l1skills.settype5(rs.getInt("type5"));
                l1skills.settype6(rs.getInt("type6"));
                l1skills.settype7(rs.getInt("type7"));
                l1skills.settype8(rs.getInt("type8"));
                l1skills.settype9(rs.getInt("type9"));
                l1skills.settype10(rs.getInt("type10"));
                l1skills.settype11(rs.getInt("type11"));
                l1skills.settype12(rs.getInt("type12"));
                l1skills.settype13(rs.getInt("type13"));
                l1skills.settype14(rs.getInt("type14"));
                l1skills.settype15(rs.getInt("type15"));
                l1skills.settype16(rs.getInt("type16"));
                l1skills.settype17(rs.getInt("type17"));
                l1skills.settype18(rs.getInt("type18"));
                l1skills.settype19(rs.getInt("type19"));
                l1skills.settype20(rs.getInt("type20"));
                l1skills.settype21(rs.getInt("type21"));
                l1skills.settype22(rs.getInt("type22"));
                l1skills.settype23(rs.getInt("type23"));
                l1skills.settype24(rs.getInt("type24"));
                l1skills.settype25(rs.getInt("type25"));
                l1skills.settype26(rs.getInt("type26"));
                l1skills.settype27(rs.getInt("type27"));
                l1skills.settype28(rs.getInt("type28"));
                this._skills.put(new Integer(skill_id), l1skills);
            }
        }
        catch (SQLException e) {
            SkillsProbabilityTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SQLUtil.close(rs);
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }
    
    public L1Skills getTemplate(int skill_id) {
        if (this._skills.containsKey((Integer)skill_id)) {
            return this._skills.get((Integer)skill_id);
        }
        else {
            return null;
        }
    }
}
