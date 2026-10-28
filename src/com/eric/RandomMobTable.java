package com.eric;

import com.lineage.server.datatables.MapsTable;
import java.util.Random;
import java.util.Iterator;
import com.lineage.server.utils.L1SpawnUtil;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.collections.Maps;
import java.util.Map;
import java.util.logging.Logger;

public final class RandomMobTable
{
    private static Logger _log;
    private static RandomMobTable _instance;
    private final Map<Integer, RandomMobTable.Data> _mobs;
    
    static {
        RandomMobTable._log = Logger.getLogger(RandomMobTable.class.getName());
    }
    
    private RandomMobTable() {
        this._mobs = (Map)Maps.newHashMap();
        this.loadRandomMobFromDatabase();
    }
    
    private void loadRandomMobFromDatabase() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM eric_random_mob");
            rs = pstm.executeQuery();
            while (rs.next()) {
                RandomMobTable.Data data = new RandomMobTable.Data();
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
                this._mobs.put(new Integer(id), data);
            }
            RandomMobTable._log.config("RandomMob " + this._mobs.size());
        }
        catch (SQLException e) {
            e.printStackTrace();
            RandomMobTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void startRandomMob() {
        for (RandomMobTable.Data data : this._mobs.values()) {
            if (data.isActive) {
                L1SpawnUtil.spawn(data.id);
            }
        }
    }
    
    public static RandomMobTable getInstance() {
        if (RandomMobTable._instance == null) {
            RandomMobTable._instance = new RandomMobTable();
        }
        return RandomMobTable._instance;
    }
    
    public short getRandomMapId(int RandomMobId) {
        RandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return 0;
        }
        else {
            int length = (this._mobs.get((Integer)RandomMobId)).mapId.length;
            int rand = new Random().nextInt(length);
            return (this._mobs.get((Integer)RandomMobId)).mapId[rand];
        }
    }
    
    public int getRandomMapX(int mapId) {
        int startX = MapsTable.get().getStartX(mapId);
        int endX = MapsTable.get().getEndX(mapId);
        int rand = new Random().nextInt(endX - startX);
        return startX + rand;
    }
    
    public int getRandomMapY(int mapId) {
        int startY = MapsTable.get().getStartY(mapId);
        int endY = MapsTable.get().getEndY(mapId);
        int rand = new Random().nextInt(endY - startY);
        return startY + rand;
    }
    
    public String getName(int RandomMobId) {
        RandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return "";
        }
        else {
            return (this._mobs.get((Integer)RandomMobId)).note;
        }
    }
    
    public int getMobId(int RandomMobId) {
        RandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return 0;
        }
        else {
            return (this._mobs.get((Integer)RandomMobId)).mobId;
        }
    }
    
    public int getCont(int RandomMobId) {
        RandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return 0;
        }
        else {
            return (this._mobs.get((Integer)RandomMobId)).cont;
        }
    }
    
    public int getTimeSecondToDelete(int RandomMobId) {
        RandomMobTable.Data data = this._mobs.get((Integer)RandomMobId);
        if (data == null) {
            return 0;
        }
        else {
            return (this._mobs.get((Integer)RandomMobId)).timeSecondToDelete;
        }
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
        
        private Data() {
            this.id = 0;
            this.note = "";
            this.mobId = 0;
            this.cont = 0;
            this.mapId = new short[0];
            this.timeSecondToDelete = -1;
            this.isActive = false;
        }
    }
}
