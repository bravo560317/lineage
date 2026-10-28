package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.MapData;
import java.util.Map;
import org.apache.commons.logging.Log;

public final class MapsTable
{
    private static final Log _log;
    private static MapsTable _instance;
    private static final Map<Integer, MapData> _maps;
    private static final Map<Object, Object> _mapName;
    
    static {
        _log = LogFactory.getLog(MapsTable.class);
        _maps = new HashMap();
        _mapName = new HashMap();
    }
    
    public static MapsTable get() {
        if (MapsTable._instance == null) {
            MapsTable._instance = new MapsTable();
        }
        return MapsTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `mapids`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                MapData data = new MapData();
                int mapId = rs.getInt("mapid");
                String mapName = rs.getString("locationname");
                MapsTable._mapName.put(mapId, mapName);
                data.mapId = mapId;
                data.startX = rs.getInt("startX");
                data.endX = rs.getInt("endX");
                data.startY = rs.getInt("startY");
                data.endY = rs.getInt("endY");
                data.monster_amount = rs.getDouble("monster_amount");
                data.dropRate = rs.getDouble("drop_rate");
                data.isUnderwater = rs.getBoolean("underwater");
                data.markable = rs.getBoolean("markable");
                data.teleportable = rs.getBoolean("teleportable");
                data.escapable = rs.getBoolean("escapable");
                data.isUseResurrection = rs.getBoolean("resurrection");
                data.isUsePainwand = rs.getBoolean("painwand");
                data.isEnabledDeathPenalty = rs.getBoolean("penalty");
                data.isTakePets = rs.getBoolean("take_pets");
                data.isRecallPets = rs.getBoolean("recall_pets");
                data.isUsableItem = rs.getBoolean("usable_item");
                data.isUsableSkill = rs.getBoolean("usable_skill");
                data.isGuaji = rs.getBoolean("isguaji");
                data.isClanPc = rs.getBoolean("isClanPc");
                data.isPartyPc = rs.getBoolean("isPartyPc");
                data.isdropitem = rs.getBoolean("isdropitem");
                data.isUsableShop = rs.getInt("usable_shop");
                data.isStart_time = rs.getInt("Start_time");
                data.isEnd_time = rs.getInt("End_time");
                data.isStart_time2 = rs.getInt("Start_time2");
                data.isEnd_time2 = rs.getInt("End_time2");
                data.isweek = rs.getInt("week");
                data.CopyMapId = rs.getInt("CopyMapId");
                data.EXP = rs.getInt("EXP");
                MapsTable._maps.put(new Integer(mapId), data);
            }
        }
        catch (SQLException e) {
            MapsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        MapsTable._log.info("載入地圖設置資料數量: " + MapsTable._maps.size() + "(" + timer.get() + "ms)");
    }
    
    public int isUsableShop(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).isUsableShop;
        }
    }
    
    public int isStart_time(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).isStart_time;
        }
    }
    
    public int isEnd_time(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).isEnd_time;
        }
    }
    
    public int isStart_time2(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).isStart_time2;
        }
    }
    
    public int isEnd_time2(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).isEnd_time2;
        }
    }
    
    public int isweek(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).isweek;
        }
    }
    
    public int getMapTime(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).maptime;
        }
    }
    
    public boolean isTimingMap(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && map.maptime > 0;
    }
    
    public Map<Integer, MapData> getMaps() {
        return MapsTable._maps;
    }
    
    public void setMaps(int mapId, MapData mapdata) {
        MapsTable._maps.put(mapId, mapdata);
    }
    
    public MapData getMapData(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map;
    }
    
    public int getStartX(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).startX;
        }
    }
    
    public int getEndX(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).endX;
        }
    }
    
    public int getStartY(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).startY;
        }
    }
    
    public int getEndY(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).endY;
        }
    }
    
    public double getMonsterAmount(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0.0;
        }
        else {
            return map.monster_amount;
        }
    }
    
    public double getDropRate(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0.0;
        }
        else {
            return map.dropRate;
        }
    }
    
    public boolean isUnderwater(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isUnderwater;
    }
    
    public boolean isMarkable(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).markable;
    }
    
    public boolean isTeleportable(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).teleportable;
    }
    
    public boolean isEscapable(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).escapable;
    }
    
    public boolean isUseResurrection(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isUseResurrection;
    }
    
    public boolean isUsePainwand(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isUsePainwand;
    }
    
    public boolean isEnabledDeathPenalty(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isEnabledDeathPenalty;
    }
    
    public boolean isTakePets(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isTakePets;
    }
    
    public boolean isRecallPets(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isRecallPets;
    }
    
    public boolean isUsableItem(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isUsableItem;
    }
    
    public boolean isUsableSkill(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isUsableSkill;
    }
    
    public boolean isGuaji(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isGuaji;
    }
    
    public boolean isClanPc(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isClanPc;
    }
    
    public boolean isPartyPc(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isPartyPc;
    }
    
    public boolean isdropitem(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        return map != null && (MapsTable._maps.get((Integer)mapId)).isdropitem;
    }
    
    public String getLoaction(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return "";
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).location;
        }
    }
    
    public int getCopyMapId(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return -1;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).CopyMapId;
        }
    }
    
    public int getEXP(int mapId) {
        MapData map = MapsTable._maps.get((Integer)mapId);
        if (map == null) {
            return 0;
        }
        else {
            return (MapsTable._maps.get((Integer)mapId)).EXP;
        }
    }
    
    public String getMapName(int mapId) {
        if (MapsTable._mapName.containsKey((Integer)mapId)) {
            return (String)MapsTable._mapName.get((Integer)mapId);
        }
        else {
            return null;
        }
    }
}
