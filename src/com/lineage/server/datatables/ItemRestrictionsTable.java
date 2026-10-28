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

public class ItemRestrictionsTable
{
    private static final Log _log;
    private static ItemRestrictionsTable _instance;
    public static final ArrayList<Integer> RESTRICTIONS;
    
    static {
        _log = LogFactory.getLog(ItemRestrictionsTable.class);
        RESTRICTIONS = new ArrayList();
    }
    
    public static ItemRestrictionsTable get() {
        if (ItemRestrictionsTable._instance == null) {
            ItemRestrictionsTable._instance = new ItemRestrictionsTable();
        }
        return ItemRestrictionsTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_item_restrictions`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                String note = rs.getString("名稱");
                if (!note.contains("=>")) {
                    updata_name(itemid);
                }
                ItemRestrictionsTable.RESTRICTIONS.add(itemid);
            }
        }
        catch (SQLException e) {
            ItemRestrictionsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemRestrictionsTable._log.info("載入交易限制道具: " + ItemRestrictionsTable.RESTRICTIONS.size() + "(" + timer.get() + "ms)");
    }
    
    private static void updata_name(int itemid) {
        Connection cn = null;
        PreparedStatement ps = null;
        String itemname = ItemTable.get().getTemplate(itemid).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `server_item_restrictions` SET `名稱`=? WHERE `itemid`=?");
            int i = 0;
            ps.setString(++i, "禁止交易->" + itemname);
            ps.setInt(++i, itemid);
            ps.execute();
        }
        catch (SQLException e) {
            ItemRestrictionsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
