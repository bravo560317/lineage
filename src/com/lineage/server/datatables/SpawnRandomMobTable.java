package com.lineage.server.datatables;

import java.util.Iterator;
import com.lineage.server.utils.L1SpawnUtil;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;
import java.util.logging.Logger;

public final class SpawnRandomMobTable
{
    private static Logger _log;
    private final Map<Integer, SpawnRandomMobTable.Data> _mobs;
    private static final Random _random;
    private static SpawnRandomMobTable _instance;
    
    static {
        SpawnRandomMobTable._log = Logger.getLogger(SpawnRandomMobTable.class.getName());
        _random = new Random();
    }
    
    public static SpawnRandomMobTable get() {
        if (SpawnRandomMobTable._instance == null) {
            SpawnRandomMobTable._instance = new SpawnRandomMobTable();
        }
        return SpawnRandomMobTable._instance;
    }
    
    private SpawnRandomMobTable() {
        this._mobs = new HashMap();
        this.load();
    }
    
    private void load() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM spawnlist_random_mob");
            rs = pstm.executeQuery();
            while (rs.next()) {
                SpawnRandomMobTable.Data data = new SpawnRandomMobTable.Data();
                int id = rs.getInt("id");
                data.id = id;
                data.note = rs.getString("note");
                String[] temp = rs.getString("mapId").split(",");
                short[] i = new short[temp.length];
                int loop = 0;
                String[] array;
                for (int length = (array = temp).length, j = 0; j < length; ++j) {
                    String s = array[j];
                    i[loop] = (short)Integer.parseInt(s);
                    ++loop;
                }
                data.mapId = i;
                data.mobId = rs.getInt("mobId");
                data.cont = rs.getInt("cont");
                data.timeSecondToDelete = rs.getInt("timeSecondToKill");
                data.isActive = rs.getBoolean("isActive");
                data.isbroad = rs.getBoolean("broad");
                this._mobs.put(id, data);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            SpawnRandomMobTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SpawnRandomMobTable._log.info("載入隨機出怪設置數量:" + this._mobs.size());
    }
    
    public void startRandomMob() {
        for (SpawnRandomMobTable.Data data : this._mobs.values()) {
            if (data.isActive) {
                L1SpawnUtil.spawnRandomMob(data.id);
            }
        }
    }
    
    public short getRandomMapId(int RandomMobId) {
        SpawnRandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return 0;
        }
        else {
            int length = (this._mobs.get((Integer)RandomMobId)).mapId.length;
            int rand = SpawnRandomMobTable._random.nextInt(length);
            return (this._mobs.get((Integer)RandomMobId)).mapId[rand];
        }
    }
    
    public int getRandomMapX(int mapId) {
        int startX = MapsTable.get().getStartX(mapId);
        int endX = MapsTable.get().getEndX(mapId);
        int rand = SpawnRandomMobTable._random.nextInt(endX - startX);
        return startX + rand;
    }
    
    public int getRandomMapY(int mapId) {
        int startY = MapsTable.get().getStartY(mapId);
        int endY = MapsTable.get().getEndY(mapId);
        int rand = SpawnRandomMobTable._random.nextInt(endY - startY);
        return startY + rand;
    }
    
    public String getName(int RandomMobId) {
        SpawnRandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return "";
        }
        else {
            return (this._mobs.get((Integer)RandomMobId)).note;
        }
    }
    
    public int getMobId(int RandomMobId) {
        SpawnRandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return 0;
        }
        else {
            return (this._mobs.get((Integer)RandomMobId)).mobId;
        }
    }
    
    public int getCont(int RandomMobId) {
        SpawnRandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return 0;
        }
        else {
            return (this._mobs.get((Integer)RandomMobId)).cont;
        }
    }
    
    public int getTimeSecondToDelete(int RandomMobId) {
        SpawnRandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return 0;
        }
        else {
            return (this._mobs.get((Integer)RandomMobId)).timeSecondToDelete;
        }
    }
    
    public boolean isBroad(int RandomMobId) {
        SpawnRandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        return data.isbroad;
    }
    
    private class Data
    {
        public int id;
        public String note;
        public int mobId;
        public int cont;
        public short[] mapId;
        public int timeSecondToDelete;
        public boolean isActive;
        public boolean isbroad;
        
        private Data() {
            this.id = 0;
            this.note = "";
            this.mobId = 0;
            this.cont = 0;
            this.mapId = new short[0];
            this.timeSecondToDelete = -1;
            this.isActive = false;
            this.isbroad = false;
        }
    }
}
