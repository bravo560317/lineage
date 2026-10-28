package com.lineage.server.datatables;

import com.lineage.server.serverpackets.S_Teleport2;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;

public class DungeonRTable
{
    private static final Log _log;
    private static DungeonRTable _instance;
    private static Map<String, ArrayList<int[]>> _dungeonMap;
    private static Map<String, Integer> _dungeonMapID;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(DungeonRTable.class);
        DungeonRTable._instance = null;
        DungeonRTable._dungeonMap = new HashMap();
        DungeonRTable._dungeonMapID = new HashMap();
        DungeonRTable._random = new Random();
    }
    
    public static DungeonRTable get() {
        if (DungeonRTable._instance == null) {
            DungeonRTable._instance = new DungeonRTable();
        }
        return DungeonRTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int teltportId = 1000;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `dungeon_random`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int srcMapId = rs.getInt("src_mapid");
                int srcX = rs.getInt("src_x");
                int srcY = rs.getInt("src_y");
                String key = new StringBuilder().append(srcMapId).append(srcX).append(srcY).toString();
                if (DungeonRTable._dungeonMap.containsKey(key)) {
                    DungeonRTable._log.error("相同SRC(多點)傳送座標(" + key + ")");
                }
                else {
                    int heading = rs.getInt("new_heading");
                    ArrayList<int[]> value = new ArrayList();
                    if (rs.getInt("new_x1") != 0) {
                        int[] newLoc = { rs.getInt("new_x1"), rs.getInt("new_y1"), rs.getShort("new_mapid1"), heading };
                        value.add(newLoc);
                    }
                    if (rs.getInt("new_x2") != 0) {
                        int[] newLoc = { rs.getInt("new_x2"), rs.getInt("new_y2"), rs.getShort("new_mapid2"), heading };
                        value.add(newLoc);
                    }
                    if (rs.getInt("new_x3") != 0) {
                        int[] newLoc = { rs.getInt("new_x3"), rs.getInt("new_y3"), rs.getShort("new_mapid3"), heading };
                        value.add(newLoc);
                    }
                    if (rs.getInt("new_x4") != 0) {
                        int[] newLoc = { rs.getInt("new_x4"), rs.getInt("new_y4"), rs.getShort("new_mapid4"), heading };
                        value.add(newLoc);
                    }
                    if (rs.getInt("new_x5") != 0) {
                        int[] newLoc = { rs.getInt("new_x5"), rs.getInt("new_y5"), rs.getShort("new_mapid5"), heading };
                        value.add(newLoc);
                    }
                    DungeonRTable._dungeonMap.put(key, value);
                    DungeonRTable._dungeonMapID.put(key, (Integer)(teltportId++));
                }
            }
        }
        catch (SQLException e) {
            DungeonRTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        DungeonRTable._log.info("載入地圖切換點設置(多點)數量: " + DungeonRTable._dungeonMapID.size() + "(" + timer.get() + "ms)");
    }
    
    public boolean dg(int locX, int locY, int mapId, L1PcInstance pc) {
        String key = new StringBuilder().append(mapId).append(locX).append(locY).toString();
        if (DungeonRTable._dungeonMap.containsKey(key)) {
            ArrayList<int[]> newLocs = DungeonRTable._dungeonMap.get(key);
            int rnd = DungeonRTable._random.nextInt(newLocs.size());
            int[] loc = newLocs.get(rnd);
            int newX = loc[0];
            int newY = loc[1];
            short newMap = (short)loc[2];
            int heading = loc[3];
            pc.setSkillEffect(78, 2000);
            pc.stopHpRegeneration();
            pc.stopMpRegeneration();
            int teleportId = DungeonRTable._dungeonMapID.get(key);
            this.teleport(pc, teleportId, newX, newY, newMap, heading, false);
            return true;
        }
        else {
            return false;
        }
    }
    
    private void teleport(L1PcInstance pc, int id, int newX, int newY, short newMap, int heading, boolean b) {
        pc.setTeleportX(newX);
        pc.setTeleportY(newY);
        pc.setTeleportMapId(newMap);
        pc.setTeleportHeading(heading);
        pc.sendPackets(new S_Teleport2((int)newMap, id));
    }
}
