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
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;

public final class DropItemTable1
{
    private static final Log _log;
    private static DropItemTable1 _instance;
    private static final Map<Integer, DropItemTable1.DropItemData> _dropItem;
    public static final ArrayList<Integer> RESTRICTIONS;
    
    static {
        _log = LogFactory.getLog(DropItemTable1.class);
        _dropItem = new HashMap();
        RESTRICTIONS = new ArrayList();
    }
    
    public static DropItemTable1 get() {
        if (DropItemTable1._instance == null) {
            DropItemTable1._instance = new DropItemTable1();
        }
        return DropItemTable1._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `內掛機率掉落`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                String itemid = rs.getString("item_id");
                DropItemTable1.DropItemData data = new DropItemTable1.DropItemData();
                data.dropRate = rs.getInt("機率");
                DropItemTable1._dropItem.put(new Integer(itemid), data);
                DropItemTable1.RESTRICTIONS.add(Integer.valueOf(itemid));
            }
        }
        catch (SQLException e) {
            DropItemTable1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        DropItemTable1._log.info("載入掉落物品機率資料數量: " + DropItemTable1._dropItem.size() + "(" + timer.get() + "ms)");
    }
    
    public int getDropRate(int itemId) {
        DropItemTable1.DropItemData data = DropItemTable1._dropItem.get((Integer)itemId);
        if (data == null) {
            return 1;
        }
        else {
            return data.dropRate;
        }
    }
    
    private class DropItemData
    {
        public int dropRate;
        public double dropAmount;
        
        private DropItemData() {
            this.dropRate = 1;
            this.dropAmount = 1.0;
        }
    }
}
