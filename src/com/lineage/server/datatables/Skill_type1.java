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

public class Skill_type1
{
    private static final Log _log;
    private static Skill_type1 _instance;
    public static final ArrayList<Integer> RESTRICTIONS;
    
    static {
        _log = LogFactory.getLog(Skill_type1.class);
        RESTRICTIONS = new ArrayList();
    }
    
    public static Skill_type1 get() {
        if (Skill_type1._instance == null) {
            Skill_type1._instance = new Skill_type1();
        }
        return Skill_type1._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `skills_無法檔魔屏`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("技能編號");
                Skill_type1.RESTRICTIONS.add(itemid);
            }
        }
        catch (SQLException e) {
            Skill_type1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        Skill_type1._log.info("載入skills_無法檔魔屏: " + Skill_type1.RESTRICTIONS.size() + "(" + timer.get() + "ms)");
    }
}
