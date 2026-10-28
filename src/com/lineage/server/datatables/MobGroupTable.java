package com.lineage.server.datatables;

import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.templates.L1NpcCount;
import com.lineage.server.utils.collections.Lists;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1MobGroup;
import java.util.Map;
import org.apache.commons.logging.Log;

public class MobGroupTable
{
    private static final Log _log;
    private static MobGroupTable _instance;
    private static final Map<Integer, L1MobGroup> _mobGroupIndex;
    
    static {
        _log = LogFactory.getLog(MobGroupTable.class);
        _mobGroupIndex = new HashMap();
    }
    
    public static MobGroupTable get() {
        if (MobGroupTable._instance == null) {
            MobGroupTable._instance = new MobGroupTable();
        }
        return MobGroupTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `mobgroup`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int mobGroupId = rs.getInt("id");
                boolean isRemoveGroup = rs.getBoolean("remove_group_if_leader_die");
                int leaderId = rs.getInt("leader_id");
                List minions = Lists.newArrayList();
                for (int i = 1; i <= 7; ++i) {
                    int id = rs.getInt("minion" + i + "_id");
                    int count = rs.getInt("minion" + i + "_count");
                    minions.add(new L1NpcCount(id, count));
                }
                L1MobGroup mobGroup = new L1MobGroup(mobGroupId, leaderId, minions, isRemoveGroup);
                MobGroupTable._mobGroupIndex.put(mobGroupId, mobGroup);
            }
            MobGroupTable._log.info("載入MOB隊伍資料數量: " + MobGroupTable._mobGroupIndex.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            MobGroupTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public L1MobGroup getTemplate(int mobGroupId) {
        return MobGroupTable._mobGroupIndex.get((Integer)mobGroupId);
    }
}
