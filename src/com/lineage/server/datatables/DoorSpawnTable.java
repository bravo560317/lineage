package com.lineage.server.datatables;

import java.util.Iterator;
import com.lineage.server.templates.L1Npc;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1DoorInstance;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class DoorSpawnTable
{
    private static final Log _log;
    private static DoorSpawnTable _instance;
    private static final ArrayList<L1DoorInstance> _doorList;
    
    static {
        _log = LogFactory.getLog(DoorSpawnTable.class);
        _doorList = new ArrayList();
    }
    
    public static DoorSpawnTable get() {
        if (DoorSpawnTable._instance == null) {
            DoorSpawnTable._instance = new DoorSpawnTable();
        }
        return DoorSpawnTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        int i = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `spawnlist_door`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ++i;
                L1Npc l1npc = NpcTable.get().getTemplate(81158);
                if (l1npc != null) {
                    int id = rs.getInt("id");
                    if (id < 808 || id > 812) {
                        L1DoorInstance door = (L1DoorInstance)NpcTable.get().newNpcInstance(l1npc);
                        door.setId(IdFactoryNpc.get().nextId());
                        door.setDoorId(id);
                        door.setGfxId(rs.getInt("gfxid"));
                        int x = rs.getInt("locx");
                        int y = rs.getInt("locy");
                        door.setX(x);
                        door.setY(y);
                        door.setMap(rs.getShort("mapid"));
                        door.setHomeX(x);
                        door.setHomeY(y);
                        door.setDirection(rs.getInt("direction"));
                        door.setLeftEdgeLocation(rs.getInt("left_edge_location"));
                        door.setRightEdgeLocation(rs.getInt("right_edge_location"));
                        int hp = rs.getInt("hp");
                        door.setMaxHp(hp);
                        door.setCurrentHp(hp);
                        door.setKeeperId(rs.getInt("keeper"));
                        World.get().storeObject(door);
                        World.get().addVisibleObject(door);
                        DoorSpawnTable._doorList.add(door);
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (SQLException e) {
            DoorSpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        catch (SecurityException e2) {
            DoorSpawnTable._log.error(e2.getLocalizedMessage(), e2);
        }
        catch (IllegalArgumentException e3) {
            DoorSpawnTable._log.error(e3.getLocalizedMessage(), e3);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        DoorSpawnTable._log.info("載入門資料數量: " + i + "(" + timer.get() + "ms)");
    }
    
    public void removeDoor(L1DoorInstance door) {
        DoorSpawnTable._doorList.remove(door);
    }
    
    public L1DoorInstance[] getDoorList() {
        return (L1DoorInstance[])DoorSpawnTable._doorList.toArray(new L1DoorInstance[DoorSpawnTable._doorList.size()]);
    }
    
    public L1DoorInstance spawnDoor(int doorId, int gfxId, int locx, int locy, short mapid, int hp, int keeper, boolean isopening, int left_edge_location, int right_edge_location) {
        return this.spawnDoor(doorId, gfxId, locx, locy, mapid, hp, keeper, isopening, left_edge_location, right_edge_location, 0);
    }
    
    public L1DoorInstance spawnDoor(int doorId, int gfxId, int locx, int locy, short mapid, int hp, int keeper, boolean isopening, int left_edge_location, int right_edge_location, int direction) {
        for (L1DoorInstance temp : DoorSpawnTable._doorList) {
            if (temp.getMapId() == mapid && temp.getHomeX() == locx && temp.getHomeY() == locy) {
                return temp;
            }
        }
        L1Npc l1npc = NpcTable.get().getTemplate(81158);
        L1DoorInstance door = (L1DoorInstance)NpcTable.get().newNpcInstance(l1npc);
        door.setId(IdFactoryNpc.get().nextId());
        door.setDoorId(doorId);
        door.setGfxId(gfxId);
        door.setX(locx);
        door.setY(locy);
        door.setMap(mapid);
        door.setHomeX(locx);
        door.setHomeY(locy);
        door.setDirection(direction);
        door.setLeftEdgeLocation(left_edge_location);
        door.setRightEdgeLocation(right_edge_location);
        door.setMaxHp(hp);
        door.setCurrentHp(hp);
        door.setKeeperId(keeper);
        World.get().storeObject(door);
        World.get().addVisibleObject(door);
        DoorSpawnTable._doorList.add(door);
        return door;
    }
    
    public void addDoor(L1DoorInstance door) {
        DoorSpawnTable._doorList.add(door);
    }
}
