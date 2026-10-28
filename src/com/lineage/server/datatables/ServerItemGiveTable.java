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
import com.lineage.server.templates.L1ServerItemGive;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ServerItemGiveTable
{
    private static final Log _log;
    private static ServerItemGiveTable _instance;
    private static final Map<Integer, L1ServerItemGive> _giveList;
    
    static {
        _log = LogFactory.getLog(ServerItemGiveTable.class);
        _giveList = new HashMap();
    }
    
    public static ServerItemGiveTable get() {
        if (ServerItemGiveTable._instance == null) {
            ServerItemGiveTable._instance = new ServerItemGiveTable();
        }
        return ServerItemGiveTable._instance;
    }
    
    private ServerItemGiveTable() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_限制物品丟怪寵`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                if (ItemTable.get().getTemplate(itemid) == null) {
                    ServerItemGiveTable._log.error("限制丟NPC品資料錯誤: 沒有這個編號的道具:" + itemid);
                }
                else {
                    boolean mob = rs.getBoolean("mob");
                    boolean pet = rs.getBoolean("pet");
                    int max = rs.getInt("max");
                    L1ServerItemGive give = new L1ServerItemGive();
                    give.set_mob(mob);
                    give.set_pet(pet);
                    give.set_max(max);
                    ServerItemGiveTable._giveList.put(new Integer(itemid), give);
                }
            }
            ServerItemGiveTable._log.info("載入限制丟NPC物品物品資料數量: " + ServerItemGiveTable._giveList.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            ServerItemGiveTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public L1ServerItemGive getTemplate(int itemid) {
        L1ServerItemGive item = null;
        if (ServerItemGiveTable._giveList.containsKey((Integer)itemid)) {
            item = ServerItemGiveTable._giveList.get((Integer)itemid);
        }
        return item;
    }
    
    public Map<Integer, L1ServerItemGive> getList() {
        return ServerItemGiveTable._giveList;
    }
    
    public int size() {
        return ServerItemGiveTable._giveList.size();
    }
}
