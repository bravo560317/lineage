package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Taketreasure1;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public final class Taketreasure1
{
    private static final Log _log;
    private static Taketreasure1 _instance;
    private static final ArrayList<L1Taketreasure1> _stealList;
    
    static {
        _log = LogFactory.getLog(Taketreasure1.class);
        _stealList = new ArrayList();
    }
    
    public static Taketreasure1 getInstance() {
        if (Taketreasure1._instance == null) {
            Taketreasure1._instance = new Taketreasure1();
        }
        return Taketreasure1._instance;
    }
    
    public final void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_死亡必噴消失物品");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                L1Taketreasure1 itemSteal = new L1Taketreasure1(item_id);
                Taketreasure1._stealList.add(itemSteal);
            }
        }
        catch (SQLException e) {
            Taketreasure1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public final ArrayList<L1Taketreasure1> getList() {
        return Taketreasure1._stealList;
    }
}
