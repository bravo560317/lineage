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
import java.util.Map;
import org.apache.commons.logging.Log;

public final class DropItemTable
{
    private static final Log _log;
    private static DropItemTable _instance;
    private static final Map<Integer, DropItemTable.DropItemData> _dropItem;
    
    static {
        _log = LogFactory.getLog(DropItemTable.class);
        _dropItem = new HashMap();
    }
    
    public static DropItemTable get() {
        if (DropItemTable._instance == null) {
            DropItemTable._instance = new DropItemTable();
        }
        return DropItemTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `drop_item`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("item_id");
                if (ItemTable.get().getTemplate(itemid) == null) {
                    DropItemTable._log.error("掉落物品機率資料錯誤: 沒有這個編號的道具:" + itemid);
                    errorItem(itemid);
                }
                else {
                    DropItemTable.DropItemData data = new DropItemTable.DropItemData();
                    data.dropRate = rs.getDouble("drop_rate");
                    data.dropAmount = rs.getDouble("drop_amount");
                    DropItemTable._dropItem.put(new Integer(itemid), data);
                }
            }
        }
        catch (SQLException e) {
            DropItemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        DropItemTable._log.info("載入掉落物品機率資料數量: " + DropItemTable._dropItem.size() + "(" + timer.get() + "ms)");
    }
    
    private static void errorItem(int itemid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `drop_item` WHERE `item_id`=?");
            pstm.setInt(1, itemid);
            pstm.execute();
        }
        catch (SQLException e) {
            DropItemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public double getDropRate(int itemId) {
        DropItemTable.DropItemData data = DropItemTable._dropItem.get((Integer)itemId);
        if (data == null) {
            return 1.0;
        }
        else {
            return data.dropRate;
        }
    }
    
    public double getDropAmount(int itemId) {
        DropItemTable.DropItemData data = DropItemTable._dropItem.get((Integer)itemId);
        if (data == null) {
            return 1.0;
        }
        else {
            return data.dropAmount;
        }
    }
    
    private class DropItemData
    {
        public double dropRate;
        public double dropAmount;
        
        private DropItemData() {
            this.dropRate = 1.0;
            this.dropAmount = 1.0;
        }
    }
}
