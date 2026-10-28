package com.lineage.server.datatables;

import com.lineage.server.templates.L1Trap;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.world.WorldTrap;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1TrapInstance;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1Location;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TrapsSpawn
{
    private static final Log _log;
    private static TrapsSpawn _instance;
    
    static {
        _log = LogFactory.getLog(TrapsSpawn.class);
    }
    
    public static TrapsSpawn get() {
        if (TrapsSpawn._instance == null) {
            TrapsSpawn._instance = new TrapsSpawn();
        }
        return TrapsSpawn._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `spawnlist_trap`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int trapId = rs.getInt("trapId");
                L1Trap trapTemp = TrapTable.get().getTemplate(trapId);
                L1Location loc = new L1Location();
                loc.setMap(rs.getInt("mapId"));
                loc.setX(rs.getInt("locX"));
                loc.setY(rs.getInt("locY"));
                Point rndPt = new Point();
                rndPt.setX(rs.getInt("locRndX"));
                rndPt.setY(rs.getInt("locRndY"));
                int count = rs.getInt("count");
                int span = rs.getInt("span");
                for (int i = 0; i < count; ++i) {
                    L1TrapInstance trap = new L1TrapInstance(IdFactoryNpc.get().nextId(), trapTemp, loc, rndPt, span);
                    World.get().storeObject(trap);
                    World.get().addVisibleObject(trap);
                }
            }
        }
        catch (SQLException e) {
            TrapsSpawn._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        TrapsSpawn._log.info("載入陷阱召喚資料數量: " + WorldTrap.get().map().size() + "(" + timer.get() + "ms)");
    }
    
    public void reloadTraps() {
        Object[] array;
        for (int length = (array = WorldTrap.get().map().values().toArray()).length, i = 0; i < length; ++i) {
            Object iter = array[i];
            L1TrapInstance trap = (L1TrapInstance)iter;
            World.get().removeObject(trap);
            World.get().removeVisibleObject(trap);
        }
        this.load();
    }
}
