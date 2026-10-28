package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class Skill_type
{
    private static final Log _log;
    private static Skill_type _instance;
    public static final ArrayList<Integer> RESTRICTIONS;
    public static final ArrayList<String> RESTRICTIONS1;
    
    static {
        _log = LogFactory.getLog(Skill_type.class);
        RESTRICTIONS = new ArrayList();
        RESTRICTIONS1 = new ArrayList();
    }
    
    public static Skill_type get() {
        if (Skill_type._instance == null) {
            Skill_type._instance = new Skill_type();
        }
        return Skill_type._instance;
    }
    
    public static void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `skills_無法被相消`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("技能編號");
                Skill_type.RESTRICTIONS.add(itemid);
            }
        }
        catch (SQLException e) {
            Skill_type._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        Skill_type._log.info("載入skills_無法被相消: " + Skill_type.RESTRICTIONS.size() + "(" + timer.get() + "ms)");
    }
}
