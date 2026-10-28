package com.lineage.server.datatables;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ItemLimitTable
{
    private static final Log _log;
    private static ItemLimitTable _instance;
    public static final Map<Integer, Integer> TIME;
    
    static {
        _log = LogFactory.getLog(ItemLimitTable.class);
        TIME = new HashMap();
    }
    
    public static ItemLimitTable get() {
        if (ItemLimitTable._instance == null) {
            ItemLimitTable._instance = new ItemLimitTable();
        }
        return ItemLimitTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_npc火神製作上限`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("火神成品編號");
                int value = rs.getInt("製作上限數量");
                ItemLimitTable.TIME.put(key, value);
            }
        }
        catch (SQLException e) {
            ItemLimitTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void update(L1ItemInstance item) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `w_npc火神製作上限` SET `製作上限數量`=`製作上限數量` - 1 WHERE `火神成品編號`= ?");
            pstm.setInt(1, item.getItemId());
            pstm.execute();
        }
        catch (SQLException e) {
            ItemLimitTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void update2(int item, int i) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `w_npc火神製作上限` SET `製作上限數量`= ? WHERE `火神成品編號`= ?");
            pstm.setInt(1, i);
            pstm.setInt(2, item);
            pstm.execute();
        }
        catch (SQLException e) {
            ItemLimitTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void update3(int item) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `w_npc火神製作上限` SET `製作上限數量`=`製作上限數量` - 1 WHERE `火神成品編號`= ?");
            pstm.setInt(1, item);
            pstm.execute();
        }
        catch (SQLException e) {
            ItemLimitTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
