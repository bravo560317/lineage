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

public class ServerItemDropTable
{
    private static final Log _log;
    private static ServerItemDropTable _instance;
    private static final Map<Integer, Integer> _dropList;
    
    static {
        _log = LogFactory.getLog(ServerItemDropTable.class);
        _dropList = new HashMap();
    }
    
    public static ServerItemDropTable get() {
        if (ServerItemDropTable._instance == null) {
            ServerItemDropTable._instance = new ServerItemDropTable();
        }
        return ServerItemDropTable._instance;
    }
    
    private ServerItemDropTable() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_限制物品丟地上`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                if (ItemTable.get().getTemplate(itemid) == null) {
                    ServerItemDropTable._log.error("限制丟棄物品資料錯誤: 沒有這個編號的道具:" + itemid);
                }
                else {
                    int max = rs.getInt("max");
                    ServerItemDropTable._dropList.put(new Integer(itemid), max);
                }
            }
            ServerItemDropTable._log.info("載入限制丟棄物品物品資料數量: " + ServerItemDropTable._dropList.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            ServerItemDropTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public int getMaxCount(int itemid) {
        if (ServerItemDropTable._dropList.containsKey((Integer)itemid)) {
            return ServerItemDropTable._dropList.get(new Integer(itemid));
        }
        else {
            return -1;
        }
    }
    
    public Integer getTemplate(int itemid) {
        return ServerItemDropTable._dropList.get(new Integer(itemid));
    }
    
    public Map<Integer, Integer> getList() {
        return ServerItemDropTable._dropList;
    }
    
    public int size() {
        return ServerItemDropTable._dropList.size();
    }
}
