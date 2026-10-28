package com.lineage;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Iterator;
import com.lineage.server.model.map.L1V1Map;
import com.lineage.server.templates.MapData;
import com.lineage.server.datatables.MapsTable;
import java.util.HashMap;
import com.lineage.server.model.map.L1Map;
import java.util.Map;
import java.util.StringTokenizer;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TextMapReader extends MapReader
{
    private static final Log _log;
    private static final String MAP_DIR = "./maps/";
    
    static {
        _log = LogFactory.getLog(TextMapReader.class);
    }
    
    private byte[][] read(int mapId, int xSize, int ySize) {
        byte[][] map = new byte[xSize][ySize];
        try {
            LineNumberReader in = new LineNumberReader(new BufferedReader(new FileReader("./maps/" + mapId + ".txt")));
            int y = 0;
            String line;
            while ((line = in.readLine()) != null) {
                if (line.trim().length() != 0) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    else {
                        int x = 0;
                        StringTokenizer tok = new StringTokenizer(line, ",");
                        while (tok.hasMoreTokens()) {
                            byte tile = Byte.parseByte(tok.nextToken());
                            try {
                                map[x][y] = tile;
                            }
                            catch (ArrayIndexOutOfBoundsException e) {
                                TextMapReader._log.error("指定地圖加載障礙數據異常: " + mapId + " X:" + x + " Y:" + y, e);
                            }
                            ++x;
                        }
                        ++y;
                    }
                }
            }
            in.close();
        }
        catch (Exception e2) {
            TextMapReader._log.error("指定地圖加載障礙數據異常: " + mapId);
        }
        return map;
    }
    
    @Override
    public Map<Integer, L1Map> read() {
        Map<Integer, L1Map> maps = new HashMap();
        Map<Integer, MapData> mapDatas = MapsTable.get().getMaps();
        for (Integer key : mapDatas.keySet()) {
            MapData mapData = mapDatas.get(key);
            int mapId = mapData.mapId;
            int xSize = mapData.endX - mapData.startX + 1;
            int ySize = mapData.endY - mapData.startY + 1;
            L1V1Map map = null;
            try {
                // 這裡已經補上 mapData.isStart_time2, mapData.isEnd_time2
                map = new L1V1Map((int)(short)mapId, this.read(mapId, xSize, ySize), mapData.startX, mapData.startY, mapData.isUnderwater, mapData.markable, mapData.teleportable, mapData.escapable, mapData.isUseResurrection, mapData.isUsePainwand, mapData.isEnabledDeathPenalty, mapData.isTakePets, mapData.isRecallPets, mapData.isUsableItem, mapData.isUsableSkill, mapData.isGuaji, mapData.isClanPc, mapData.isPartyPc, mapData.isdropitem, mapData.isUsableShop, mapData.isStart_time, mapData.isEnd_time, mapData.isStart_time2, mapData.isEnd_time2, mapData.isweek);
            }
            catch (Exception e) {
                TextMapReader._log.error("地圖資料生成數據載入異常: " + mapId, e);
            }
            if (map != null) {
                maps.put(mapId, map);
            }
        }
        return maps;
    }
    
    public L1Map read(int id) throws IOException {
        Map<Integer, MapData> mapDatas = MapsTable.get().getMaps();
        for (Integer key : mapDatas.keySet()) {
            MapData mapData = mapDatas.get(key);
            int mapId = mapData.mapId;
            int xSize = mapData.endX - mapData.startX + 1;
            int ySize = mapData.endY - mapData.startY + 1;
            L1V1Map map = null;
            if (mapId == id) {
                // 這裡也已經補上 mapData.isStart_time2, mapData.isEnd_time2
                map = new L1V1Map((int)(short)mapId, this.read(mapId, xSize, ySize), mapData.startX, mapData.startY, mapData.isUnderwater, mapData.markable, mapData.teleportable, mapData.escapable, mapData.isUseResurrection, mapData.isUsePainwand, mapData.isEnabledDeathPenalty, mapData.isTakePets, mapData.isRecallPets, mapData.isUsableItem, mapData.isUsableSkill, mapData.isGuaji, mapData.isClanPc, mapData.isPartyPc, mapData.isdropitem, mapData.isUsableShop, mapData.isStart_time, mapData.isEnd_time, mapData.isStart_time2, mapData.isEnd_time2, mapData.isweek);
                return map;
            }
        }
        throw new FileNotFoundException("MapId: " + id);
    }
}