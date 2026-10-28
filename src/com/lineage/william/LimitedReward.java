package com.lineage.william;

import com.lineage.server.utils.PerformanceTimer;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import java.util.logging.Logger;

public class LimitedReward
{
    private static Logger _log;
    private static final Log _logx;
    private static LimitedReward _instance;
    public static final HashMap<Integer, L1WilliamLimitedReward> _itemIdIndex;
    public static final HashMap<Integer, L1WilliamLimitedReward> _itemIdIndex1;
    
    static {
        LimitedReward._log = Logger.getLogger(LimitedReward.class.getName());
        _logx = LogFactory.getLog(LimitedReward.class.getName());
        _itemIdIndex = new HashMap();
        _itemIdIndex1 = new HashMap();
    }
    
    public static LimitedReward getInstance() {
        if (LimitedReward._instance == null) {
            LimitedReward._instance = new LimitedReward();
        }
        return LimitedReward._instance;
    }
    
    public static void reload() {
        LimitedReward oldInstance = LimitedReward._instance;
        LimitedReward._instance = new LimitedReward();
        LimitedReward._itemIdIndex.clear();
    }
    
    private LimitedReward() {
        loadChackSerial();
    }
    
    private static void loadChackSerial() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_npc即時獎勵");
            rs = pstm.executeQuery();
            fillChackSerial(rs);
        }
        catch (SQLException e) {
            LimitedReward._log.log(Level.SEVERE, "error while creating w_npc即時獎勵 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private static void fillChackSerial(ResultSet rs) throws SQLException {
        PerformanceTimer timer = new PerformanceTimer();
        while (rs.next()) {
            int id = rs.getInt("id");
            boolean activity = rs.getInt("activity") == 1;
            int check_classId = rs.getInt("check_classId");
            int check_level = rs.getInt("check_level");
            int check_item = rs.getInt("check_item");
            int check_itemCount = rs.getInt("check_itemCount");
            String surplus_msg = rs.getString("surplus_msg");
            int surplus_msg_color = rs.getInt("surplus_msg_color");
            String itemId = rs.getString("give_item_id");
            String count = rs.getString("give_itemCount");
            String enchantlvl = rs.getString("give_itemEnchantlvl");
            int totalCount = rs.getInt("total_People");
            int appearCount = rs.getInt("appear_People");
            int quest_id = rs.getInt("quest_id");
            int quest_step = rs.getInt("quest_step");
            String message = rs.getString("message");
            String message_end = rs.getString("message_end");
            L1WilliamLimitedReward armor_upgrade = new L1WilliamLimitedReward(id, activity, check_classId, check_level, check_item, check_itemCount, surplus_msg, surplus_msg_color, itemId, count, enchantlvl, totalCount, appearCount, quest_id, quest_step, message, message_end);
            LimitedReward._itemIdIndex.put(id, armor_upgrade);
            LimitedReward._itemIdIndex1.put(check_item, armor_upgrade);
        }
        LimitedReward._logx.info("載入即時獎勵系統數據資料數量: " + LimitedReward._itemIdIndex.size() + "(" + timer.get() + "ms)");
    }
    
    public L1WilliamLimitedReward[] getLimitedRewardList() {
        return (L1WilliamLimitedReward[])LimitedReward._itemIdIndex.values().toArray(new L1WilliamLimitedReward[LimitedReward._itemIdIndex.size()]);
    }
    
    public L1WilliamLimitedReward getTemplate(int id) {
        return LimitedReward._itemIdIndex.get((Integer)id);
    }
    
    public L1WilliamLimitedReward getTemplate1(int check_item) {
        return LimitedReward._itemIdIndex1.get((Integer)check_item);
    }
    
    public static void limitedRewardToList(int id, long appearCount) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE w_npc即時獎勵 SET appear_People=? WHERE id=?");
            ps.setLong(1, appearCount);
            ps.setInt(2, id);
            ps.execute();
        }
        catch (SQLException e) {
            e.getLocalizedMessage();
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public static void limitedRewardToList_isOver(int id, int activity) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE w_npc即時獎勵 SET activity=? WHERE id=?");
            ps.setInt(1, activity);
            ps.setInt(2, id);
            ps.execute();
        }
        catch (SQLException e) {
            e.getLocalizedMessage();
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
