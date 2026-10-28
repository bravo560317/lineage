package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public final class Explogpcpower
{
    private static final Log _log;
    private static Explogpcpower _instance;
    private static final Map<Integer, Double> _expmeteupList;
    
    static {
        _log = LogFactory.getLog(Explogpcpower.class);
        _expmeteupList = new HashMap();
    }
    
    public static Explogpcpower get() {
        if (Explogpcpower._instance == null) {
            Explogpcpower._instance = new Explogpcpower();
        }
        return Explogpcpower._instance;
    }
    
    public void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_轉生經驗`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int level = rs.getInt("logpcpower");
                double expPenalty = rs.getDouble("expPenalty");
                Explogpcpower._expmeteupList.put(new Integer(level), new Double(expPenalty));
            }
            Explogpcpower._log.info("載入轉生經驗:" + Explogpcpower._expmeteupList.size());
        }
        catch (SQLException e) {
            Explogpcpower._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public double getRate(int meteup) {
        double expPenalty = 1.0;
        if (Explogpcpower._expmeteupList.isEmpty()) {
            return expPenalty;
        }
        else if (Explogpcpower._expmeteupList.containsKey((Integer)meteup)) {
            return Explogpcpower._expmeteupList.get((Integer)meteup);
        }
        else {
            return expPenalty;
        }
    }
}
