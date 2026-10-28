package com.lineage.server.datatables;

import com.lineage.server.serverpackets.S_Teleport2;
import com.lineage.server.model.gametime.L1GameTimeClock;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import org.apache.commons.logging.Log;

public class DungeonTable
{
    private static final Log _log;
    private static DungeonTable _instance;
    private static Map<String, DungeonTable.NewDungeon> _dungeonMap;
    private static AtomicInteger _nextId;
    
    static {
        _log = LogFactory.getLog(DungeonTable.class);
        DungeonTable._instance = null;
        DungeonTable._dungeonMap = new HashMap();
        DungeonTable._nextId = new AtomicInteger(50000);
    }
    
    public static DungeonTable get() {
        if (DungeonTable._instance == null) {
            DungeonTable._instance = new DungeonTable();
        }
        return DungeonTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `dungeon`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int srcMapId = rs.getInt("src_mapid");
                int srcX = rs.getInt("src_x");
                int srcY = rs.getInt("src_y");
                String key = new StringBuilder().append(srcMapId).append(srcX).append(srcY).toString();
                int newX = rs.getInt("new_x");
                int newY = rs.getInt("new_y");
                int newMapId = rs.getInt("new_mapid");
                int heading = rs.getInt("new_heading");
                DungeonTable.DungeonType dungeonType = DungeonTable.DungeonType.NONE;
                if (((srcX == 33423 || srcX == 33424 || srcX == 33425 || srcX == 33426) && srcY == 33502 && srcMapId == 4) || ((srcX == 32733 || srcX == 32734 || srcX == 32735 || srcX == 32736) && srcY == 32794 && srcMapId == 83)) {
                    dungeonType = DungeonTable.DungeonType.SHIP_FOR_FI;
                }
                else if (((srcX == 32935 || srcX == 32936 || srcX == 32937) && srcY == 33058 && srcMapId == 70) || ((srcX == 32732 || srcX == 32733 || srcX == 32734 || srcX == 32735) && srcY == 32796 && srcMapId == 84)) {
                    dungeonType = DungeonTable.DungeonType.SHIP_FOR_HEINE;
                }
                else if (((srcX == 32750 || srcX == 32751 || srcX == 32752) && srcY == 32874 && srcMapId == 445) || ((srcX == 32731 || srcX == 32732 || srcX == 32733) && srcY == 32796 && srcMapId == 447)) {
                    dungeonType = DungeonTable.DungeonType.SHIP_FOR_PI;
                }
                else if (((srcX == 32296 || srcX == 32297 || srcX == 32298) && srcY == 33087 && srcMapId == 440) || ((srcX == 32735 || srcX == 32736 || srcX == 32737) && srcY == 32794 && srcMapId == 446)) {
                    dungeonType = DungeonTable.DungeonType.SHIP_FOR_HIDDENDOCK;
                }
                else if (((srcX == 32630 || srcX == 32631 || srcX == 32632) && srcY == 32983 && srcMapId == 0) || ((srcX == 32733 || srcX == 32734 || srcX == 32735) && srcY == 32796 && srcMapId == 5)) {
                    dungeonType = DungeonTable.DungeonType.SHIP_FOR_GLUDIN;
                }
                else if (((srcX == 32540 || srcX == 32542 || srcX == 32543 || srcX == 32544 || srcX == 32545) && srcY == 32728 && srcMapId == 4) || ((srcX == 32734 || srcX == 32735 || srcX == 32736 || srcX == 32737) && srcY == 32794 && srcMapId == 6)) {
                    dungeonType = DungeonTable.DungeonType.SHIP_FOR_TI;
                }
                DungeonTable.NewDungeon newDungeon = new DungeonTable.NewDungeon(newX, newY, (short)newMapId, heading, dungeonType);
                if (DungeonTable._dungeonMap.containsKey(key)) {
                    DungeonTable._log.error("相同SRC傳送座標(" + key + ")");
                }
                DungeonTable._dungeonMap.put(key, newDungeon);
            }
        }
        catch (SQLException e) {
            DungeonTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        DungeonTable._log.info("載入地圖切換點設置數量: " + DungeonTable._dungeonMap.size() + "(" + timer.get() + "ms)");
    }
    
    public boolean dg(int locX, int locY, int mapId, L1PcInstance pc) {
        int servertime = L1GameTimeClock.getInstance().currentTime().getSeconds();
        int nowtime = servertime % 86400;
        String key = new StringBuilder().append(mapId).append(locX).append(locY).toString();
        if (DungeonTable._dungeonMap.containsKey(key)) {
            DungeonTable.NewDungeon newDungeon = DungeonTable._dungeonMap.get(key);
            DungeonTable.DungeonType dungeonType = newDungeon._dungeonType;
            boolean teleportable = false;
            if (dungeonType == DungeonTable.DungeonType.NONE) {
                teleportable = true;
            }
            else if ((nowtime >= 5400 && nowtime < 9000) || (nowtime >= 16200 && nowtime < 19800) || (nowtime >= 27000 && nowtime < 30600) || (nowtime >= 37800 && nowtime < 41400) || (nowtime >= 48600 && nowtime < 52200) || (nowtime >= 59400 && nowtime < 63000) || (nowtime >= 70200 && nowtime < 73800) || (nowtime >= 81000 && nowtime < 84600)) {
                if ((pc.getInventory().checkItem(40299, 1L) && dungeonType == DungeonTable.DungeonType.SHIP_FOR_GLUDIN) || (pc.getInventory().checkItem(40301, 1L) && dungeonType == DungeonTable.DungeonType.SHIP_FOR_HEINE) || (pc.getInventory().checkItem(40302, 1L) && dungeonType == DungeonTable.DungeonType.SHIP_FOR_PI)) {
                    teleportable = true;
                }
            }
            else if (((nowtime >= 0 && nowtime < 360) || (nowtime >= 10800 && nowtime < 14400) || (nowtime >= 21600 && nowtime < 25200) || (nowtime >= 32400 && nowtime < 36000) || (nowtime >= 43200 && nowtime < 46800) || (nowtime >= 54000 && nowtime < 57600) || (nowtime >= 64800 && nowtime < 68400) || (nowtime >= 75600 && nowtime < 79200)) && ((pc.getInventory().checkItem(40298, 1L) && dungeonType == DungeonTable.DungeonType.SHIP_FOR_TI) || (pc.getInventory().checkItem(40300, 1L) && dungeonType == DungeonTable.DungeonType.SHIP_FOR_FI) || (pc.getInventory().checkItem(40303, 1L) && dungeonType == DungeonTable.DungeonType.SHIP_FOR_HIDDENDOCK))) {
                teleportable = true;
            }
            if (teleportable) {
                int id = newDungeon._id;
                short newMap = newDungeon._newMapId;
                int newX = newDungeon._newX;
                int newY = newDungeon._newY;
                int heading = newDungeon._heading;
                pc.setSkillEffect(78, 2000);
                pc.stopHpRegeneration();
                pc.stopMpRegeneration();
                this.teleport(pc, id, newX, newY, newMap, heading, false);
                return true;
            }
        }
        return false;
    }
    
    private void teleport(L1PcInstance pc, int id, int newX, int newY, short newMap, int heading, boolean b) {
        pc.setTeleportX(newX);
        pc.setTeleportY(newY);
        pc.setTeleportMapId(newMap);
        pc.setTeleportHeading(heading);
        pc.sendPackets(new S_Teleport2((int)newMap, id));
    }
    
    private enum DungeonType
    {
        NONE("NONE", 0), 
        SHIP_FOR_FI("SHIP_FOR_FI", 1), 
        SHIP_FOR_HEINE("SHIP_FOR_HEINE", 2), 
        SHIP_FOR_PI("SHIP_FOR_PI", 3), 
        SHIP_FOR_HIDDENDOCK("SHIP_FOR_HIDDENDOCK", 4), 
        SHIP_FOR_GLUDIN("SHIP_FOR_GLUDIN", 5), 
        SHIP_FOR_TI("SHIP_FOR_TI", 6);
        
        private DungeonType(String s, int n) {
        }
    }
    
    private static class NewDungeon
    {
        int _id;
        int _newX;
        int _newY;
        short _newMapId;
        int _heading;
        DungeonTable.DungeonType _dungeonType;
        
        private NewDungeon(int newX, int newY, short newMapId, int heading, DungeonTable.DungeonType dungeonType) {
            this._id = DungeonTable._nextId.incrementAndGet();
            this._newX = newX;
            this._newY = newY;
            this._newMapId = newMapId;
            this._heading = heading;
            this._dungeonType = dungeonType;
        }
    }
}
