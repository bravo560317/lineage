package com.lineage.server.model.map;

import java.util.Iterator;
import com.lineage.server.datatables.MapsTable;
import java.util.HashMap;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import com.lineage.server.templates.MapData;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import com.lineage.TextMapReader;
import java.io.File;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.MapReader;

public class CachedMapReader extends MapReader
{
    private static final Log _log;
    private static final String CACHE_DIR = "./config/mapcache/";
    
    static {
        _log = LogFactory.getLog(CachedMapReader.class);
    }
    
    private L1V1Map cacheMap(int mapId) throws IOException {
        File file = new File("./config/mapcache/");
        if (!file.exists()) {
            file.mkdir();
        }
        L1V1Map map = (L1V1Map)new TextMapReader().read(mapId);
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("./config/mapcache/" + mapId + ".map")));
        out.writeInt(map.getId());
        out.writeInt(map.getX());
        out.writeInt(map.getY());
        out.writeInt(map.getWidth());
        out.writeInt(map.getHeight());
        byte[][] rawTiles;
        for (int length = (rawTiles = map.getRawTiles()).length, i = 0; i < length; ++i) {
            byte[] line = rawTiles[i];
            byte[] array;
            for (int length2 = (array = line).length, j = 0; j < length2; ++j) {
                byte tile = array[j];
                out.writeByte((int)tile);
            }
        }
        out.flush();
        out.close();
        return map;
    }
    
    public L1Map read(int mapId, MapData mapData) throws IOException {
        File file = new File("./config/mapcache/" + mapId + ".map");
        if (!file.exists()) {
            return this.cacheMap(mapId);
        }
        else {
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("./config/mapcache/" + mapId + ".map")));
            int id = in.readInt();
            if (mapId != id) {
                throw new FileNotFoundException();
            }
            else {
                int xLoc = in.readInt();
                int yLoc = in.readInt();
                int width = in.readInt();
                int height = in.readInt();
                byte[][] tiles = new byte[width][height];
                byte[][] array;
                for (int length = (array = tiles).length, i = 0; i < length; ++i) {
                    byte[] line = array[i];
                    in.read(line);
                }
                in.close();
                L1V1Map map = null;
                try {
                    map = new L1V1Map(id, tiles, xLoc, yLoc, mapData.isUnderwater, mapData.markable, mapData.teleportable, mapData.escapable, mapData.isUseResurrection, mapData.isUsePainwand, mapData.isEnabledDeathPenalty, mapData.isTakePets, mapData.isRecallPets, mapData.isUsableItem, mapData.isUsableSkill, mapData.isGuaji, mapData.isClanPc, mapData.isPartyPc, mapData.isdropitem, mapData.isUsableShop, mapData.isStart_time, mapData.isEnd_time, mapData.isStart_time2, mapData.isEnd_time2, mapData.isweek);
                }
                catch (Exception e) {
                    CachedMapReader._log.error("地圖資料生成數據載入異常: " + mapId, e);
                }
                return map;
            }
        }
    }
    
    @Override
    public Map<Integer, L1Map> read() {
        Map maps = new HashMap();
        Map<Integer, MapData> mapDatas = MapsTable.get().getMaps();
        for (Integer key : mapDatas.keySet()) {
            MapData mapData = mapDatas.get(key);
            try {
                maps.put(key, this.read(key, mapData));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return maps;
    }
}
