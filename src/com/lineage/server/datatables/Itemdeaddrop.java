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

public class Itemdeaddrop
{
    private static final Log _log;
    private static Itemdeaddrop _instance;
    public static final ArrayList<Integer> RESTRICTIONS;
    
    static {
        _log = LogFactory.getLog(Itemdeaddrop.class);
        RESTRICTIONS = new ArrayList();
    }
    
    public static Itemdeaddrop get() {
        if (Itemdeaddrop._instance == null) {
            Itemdeaddrop._instance = new Itemdeaddrop();
        }
        return Itemdeaddrop._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_死亡強制不噴編號`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                Itemdeaddrop.RESTRICTIONS.add(itemid);
            }
        }
        catch (SQLException e) {
            Itemdeaddrop._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
