package com.lineage.server.datatables;

import java.util.Iterator;
import com.lineage.server.model.L1UbPattern;
import com.lineage.server.templates.L1Npc;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1UbSpawn;
import java.util.Map;
import org.apache.commons.logging.Log;

public class UBSpawnTable
{
    private static final Log _log;
    private static UBSpawnTable _instance;
    private static final Map<Integer, L1UbSpawn> _spawnTable;
    
    static {
        _log = LogFactory.getLog(UBSpawnTable.class);
        _spawnTable = new HashMap();
    }
    
    public static UBSpawnTable getInstance() {
        if (UBSpawnTable._instance == null) {
            UBSpawnTable._instance = new UBSpawnTable();
        }
        return UBSpawnTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `spawnlist_ub`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int npcid = rs.getInt(6);
                L1Npc npcTemp = NpcTable.get().getTemplate(npcid);
                if (npcTemp == null) {
                    UBSpawnTable._log.error("召喚NPC編號: " + npcid + " 不存在資料庫中!(spawnlist_ub)");
                    delete(npcid);
                }
                else {
                    L1UbSpawn spawnDat = new L1UbSpawn();
                    spawnDat.setId(rs.getInt(1));
                    spawnDat.setUbId(rs.getInt(2));
                    spawnDat.setPattern(rs.getInt(3));
                    spawnDat.setGroup(rs.getInt(4));
                    spawnDat.setName(npcTemp.get_name());
                    spawnDat.setNpcTemplateId(npcid);
                    spawnDat.setAmount(rs.getInt(7));
                    spawnDat.setSpawnDelay(rs.getInt(8));
                    spawnDat.setSealCount(rs.getInt(9));
                    UBSpawnTable._spawnTable.put(spawnDat.getId(), spawnDat);
                }
            }
        }
        catch (SQLException e) {
            UBSpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        UBSpawnTable._log.info("載入無線大賽召喚資料數量: " + UBSpawnTable._spawnTable.size() + "(" + timer.get() + "ms)");
    }
    
    public static void delete(int npc_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `spawnlist_ub` WHERE `npc_templateid`=?");
            ps.setInt(1, npc_id);
            ps.execute();
        }
        catch (SQLException e) {
            UBSpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public L1UbSpawn getSpawn(int spawnId) {
        return UBSpawnTable._spawnTable.get((Integer)spawnId);
    }
    
    public int getMaxPattern(int ubId) {
        int n = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT MAX(pattern) FROM `spawnlist_ub` WHERE `ub_id`=?");
            pstm.setInt(1, ubId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            UBSpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return n;
    }
    
    public L1UbPattern getPattern(int ubId, int patternNumer) {
        L1UbPattern pattern = new L1UbPattern();
        for (L1UbSpawn spawn : UBSpawnTable._spawnTable.values()) {
            if (spawn.getUbId() == ubId && spawn.getPattern() == patternNumer) {
                pattern.addSpawn(spawn.getGroup(), spawn);
            }
        }
        pattern.freeze();
        return pattern;
    }
}
