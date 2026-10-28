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

public class ItemdropdeadTable
{
    private static final Log _log;
    private static ItemdropdeadTable _instance;
    public static final ArrayList<Integer> RESTRICTIONS;
    
    static {
        _log = LogFactory.getLog(ItemdropdeadTable.class);
        RESTRICTIONS = new ArrayList();
    }
    
    public static ItemdropdeadTable get() {
        if (ItemdropdeadTable._instance == null) {
            ItemdropdeadTable._instance = new ItemdropdeadTable();
        }
        return ItemdropdeadTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_死亡噴出消失物品`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                ItemdropdeadTable.RESTRICTIONS.add(itemid);
            }
        }
        catch (SQLException e) {
            ItemdropdeadTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemdropdeadTable._log.info("載入掛機限制掉落道具: " + ItemdropdeadTable.RESTRICTIONS.size() + "(" + timer.get() + "ms)");
    }
}
