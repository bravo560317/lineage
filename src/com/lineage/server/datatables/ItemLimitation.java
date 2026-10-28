package com.lineage.server.datatables;

import com.lineage.DatabaseFactory;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactoryLogin;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ItemLimitation
{
    private static final Log _log;
    public static final Map<Integer, Integer> _limitation;
    private static ItemLimitation _instance;
    
    static {
        _log = LogFactory.getLog(ItemLimitation.class);
        _limitation = new HashMap();
    }
    
    public static ItemLimitation get() {
        if (ItemLimitation._instance == null) {
            ItemLimitation._instance = new ItemLimitation();
        }
        return ItemLimitation._instance;
    }
    
    private ItemLimitation() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int size = 0;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_掉落限制_1`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemId = rs.getInt("道具編號");
                int limit_count = rs.getInt("數量");
                String note = rs.getString("道具名稱");
                if (!note.contains("=>")) {
                    updata_name(itemId);
                }
                ItemLimitation._limitation.put(itemId, limit_count);
                ++size;
            }
        }
        catch (SQLException e) {
            ItemLimitation._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemLimitation._log.info("讀取->道具掉落限制數量: " + size + "(" + timer.get() + "ms)");
    }
    
    private static void updata_name(int itemId) {
        Connection cn = null;
        PreparedStatement ps = null;
        String itemname = ItemTable.get().getTemplate(itemId).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `系統_掉落限制_1` SET `道具名稱`=? WHERE `道具編號`=?");
            int i = 0;
            ps.setString(++i, "限制物品->" + itemname);
            ps.setInt(++i, itemId);
            ps.execute();
        }
        catch (SQLException e) {
            ItemLimitation._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public boolean checkLimitation(int itemId) {
        if (ItemLimitation._limitation.get((Integer)itemId) != null) {
            if (ItemLimitation._limitation.get((Integer)itemId) == 0) {
                return false;
            }
            else {
                int now_count = ItemLimitation._limitation.get((Integer)itemId) - 1;
                Math.max(now_count, 0);
                ItemLimitation._limitation.put(itemId, now_count);
            }
        }
        return true;
    }
}
