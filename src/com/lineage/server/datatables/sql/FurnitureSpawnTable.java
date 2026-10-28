package com.lineage.server.datatables.sql;

import com.lineage.server.model.Instance.L1FurnitureInstance;
import java.util.Iterator;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.templates.L1Npc;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.world.World;
import com.lineage.server.datatables.NpcTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Furniture;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.FurnitureSpawnStorage;

public class FurnitureSpawnTable implements FurnitureSpawnStorage
{
    private static final Log _log;
    private static final Map<Integer, L1Furniture> _furnitureList;
    
    static {
        _log = LogFactory.getLog(FurnitureSpawnTable.class);
        _furnitureList = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `spawnlist_furniture`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int npcid = rs.getInt("npcid");
                L1Npc npc = NpcTable.get().getTemplate(npcid);
                if (npc != null) {
                    int item_obj_id = rs.getInt("item_obj_id");
                    int locx = rs.getInt("locx");
                    int locy = rs.getInt("locy");
                    short mapid = rs.getShort("mapid");
                    if (World.get().findObject(item_obj_id) != null) {
                        L1Furniture value = new L1Furniture();
                        value.set_npcid(npcid);
                        value.set_item_obj_id(item_obj_id);
                        value.set_locx(locx);
                        value.set_locy(locy);
                        value.set_mapid(mapid);
                        FurnitureSpawnTable._furnitureList.put(item_obj_id, value);
                    }
                    else {
                        delFurniture(item_obj_id);
                    }
                }
            }
        }
        catch (SQLException e) {
            FurnitureSpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
            spawnFurniture();
        }
        FurnitureSpawnTable._log.info("載入家具位置資料數量: " + FurnitureSpawnTable._furnitureList.size() + "(" + timer.get() + "ms)");
    }
    
    private static void spawnFurniture() {
        for (Integer key : FurnitureSpawnTable._furnitureList.keySet()) {
            L1Furniture value = FurnitureSpawnTable._furnitureList.get(key);
            L1SpawnUtil.spawn(value);
        }
    }
    
    private static void delFurniture(int objid) {
        Connection cn = null;
        PreparedStatement pm = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            pm = cn.prepareStatement("DELETE FROM `spawnlist_furniture` WHERE `item_obj_id`=?");
            pm.setInt(1, objid);
            pm.execute();
        }
        catch (SQLException e) {
            FurnitureSpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public void deleteFurniture(L1FurnitureInstance furniture) {
        int key = furniture.getItemObjId();
        if (FurnitureSpawnTable._furnitureList.remove((Integer)key) != null) {
            delFurniture(key);
        }
    }
    
    @Override
    public void insertFurniture(L1FurnitureInstance furniture) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            int item_obj_id = furniture.getItemObjId();
            int npcid = furniture.getNpcTemplate().get_npcId();
            int locx = furniture.getX();
            int locy = furniture.getY();
            short mapid = furniture.getMapId();
            L1Furniture value = new L1Furniture();
            value.set_npcid(npcid);
            value.set_item_obj_id(item_obj_id);
            value.set_locx(locx);
            value.set_locy(locy);
            value.set_mapid(mapid);
            FurnitureSpawnTable._furnitureList.put(item_obj_id, value);
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `spawnlist_furniture` SET `item_obj_id`=?,`npcid`=?,`locx`=?,`locy`=?,`mapid`=?");
            ps.setInt(1, item_obj_id);
            ps.setInt(2, npcid);
            ps.setInt(3, locx);
            ps.setInt(4, locy);
            ps.setInt(5, (int)mapid);
            ps.execute();
        }
        catch (SQLException e) {
            FurnitureSpawnTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
