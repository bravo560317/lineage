package com.lineage.server.datatables;

import com.lineage.server.utils.NumberUtil;
import com.lineage.server.model.Instance.L1PcInstance;
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
import com.lineage.server.model.L1Spawn;
import java.util.Map;
import org.apache.commons.logging.Log;

public class SpawnTable
{
    private static final Log _log;
    private static SpawnTable _instance;
    private static final Map<Integer, L1Spawn> _spawntable;
    
    static {
        _log = LogFactory.getLog(SpawnTable.class);
        _spawntable = new HashMap();
    }
    
    public static SpawnTable get() {
        if (SpawnTable._instance == null) {
            SpawnTable._instance = new SpawnTable();
        }
        return SpawnTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        int spawnCount = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `spawnlist`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int npcTemplateId = rs.getInt("npc_templateid");
                L1Npc template1 = NpcTable.get().getTemplate(npcTemplateId);
                if (template1 == null) {
                    SpawnTable._log.error("召喚MOB編號: " + npcTemplateId + " 不存在資料庫中!");
                }
                else {
                    int count = rs.getInt("count");
                    if (count != 0) {
                        double amount_rate = MapsTable.get().getMonsterAmount((int)rs.getShort("mapid"));
                        count = calcCount(template1, count, amount_rate);
                        if (count != 0) {
                            L1Spawn spawnDat = new L1Spawn(template1);
                            spawnDat.setId(rs.getInt("id"));
                            spawnDat.setAmount(count);
                            spawnDat.setGroupId(rs.getInt("group_id"));
                            spawnDat.setLocX(rs.getInt("locx"));
                            spawnDat.setLocY(rs.getInt("locy"));
                            spawnDat.setRandomx(rs.getInt("randomx"));
                            spawnDat.setRandomy(rs.getInt("randomy"));
                            spawnDat.setLocX1(rs.getInt("locx1"));
                            spawnDat.setLocY1(rs.getInt("locy1"));
                            spawnDat.setLocX2(rs.getInt("locx2"));
                            spawnDat.setLocY2(rs.getInt("locy2"));
                            spawnDat.setHeading(rs.getInt("heading"));
                            spawnDat.setMinRespawnDelay(rs.getInt("min_respawn_delay"));
                            spawnDat.setMaxRespawnDelay(rs.getInt("max_respawn_delay"));
                            spawnDat.setMapId(rs.getShort("mapid"));
                            spawnDat.setRespawnScreen(rs.getBoolean("respawn_screen"));
                            spawnDat.setMovementDistance(rs.getInt("movement_distance"));
                            spawnDat.setRest(rs.getBoolean("rest"));
                            spawnDat.setSpawnType(rs.getInt("avoid_pc"));
                            spawnDat.setTime(SpawnTimeTable.getInstance().get(npcTemplateId));
                            spawnDat.setName(template1.get_name());
                            if (count > 1 && spawnDat.getLocX1() == 0) {
                                int range = Math.min(count * 6, 30);
                                spawnDat.setLocX1(spawnDat.getLocX() - range);
                                spawnDat.setLocY1(spawnDat.getLocY() - range);
                                spawnDat.setLocX2(spawnDat.getLocX() + range);
                                spawnDat.setLocY2(spawnDat.getLocY() + range);
                            }
                            spawnDat.init();
                            spawnCount += spawnDat.getAmount();
                            SpawnTable._spawntable.put(new Integer(spawnDat.getId()), spawnDat);
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (SQLException e) {
            SpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SpawnTable._log.info("載入召喚MOB資料數量: " + spawnCount + "(" + timer.get() + "ms)");
    }
    
    public L1Spawn getTemplate(int Id) {
        return SpawnTable._spawntable.get(new Integer(Id));
    }
    
    public static void delete(int npc_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `spawnlist` WHERE `npc_templateid`=?");
            ps.setInt(1, npc_id);
            ps.execute();
        }
        catch (SQLException e) {
            SpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public static void storeSpawn(L1PcInstance pc, L1Npc npc) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            int count = 1;
            int randomXY = 12;
            int minRespawnDelay = 60;
            int maxRespawnDelay = 120;
            String note = npc.get_name();
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `spawnlist` SET `location`=?,`count`=?,`npc_templateid`=?,`group_id`=?,`locx`=?,`locy`=?,`randomx`=?,`randomy`=?,`heading`=?,`min_respawn_delay`=?,`max_respawn_delay`=?,`mapid`=?");
            pstm.setString(1, note);
            pstm.setInt(2, count);
            pstm.setInt(3, npc.get_npcId());
            pstm.setInt(4, 0);
            pstm.setInt(5, pc.getX());
            pstm.setInt(6, pc.getY());
            pstm.setInt(7, randomXY);
            pstm.setInt(8, randomXY);
            pstm.setInt(9, pc.getHeading());
            pstm.setInt(10, minRespawnDelay);
            pstm.setInt(11, maxRespawnDelay);
            pstm.setInt(12, (int)pc.getMapId());
            pstm.execute();
        }
        catch (Exception e) {
            SpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private static int calcCount(L1Npc npc, int count, double rate) {
        if (rate == 0.0) {
            return 0;
        }
        else if (rate == 1.0 || npc.isAmountFixed()) {
            return count;
        }
        else {
            return NumberUtil.randomRound((double)count * rate);
        }
    }
}
