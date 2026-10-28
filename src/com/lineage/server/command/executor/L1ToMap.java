package com.lineage.server.command.executor;

import com.lineage.server.model.map.L1Map;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ToMap implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1ToMap.class);
    }
    
    private L1ToMap() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ToMap();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            Integer mapId = Integer.parseInt(arg);
            L1Map mapData = L1WorldMap.get().getMap(mapId.shortValue());
            if (mapData == null) {
                L1ToMap._log.error("指定地圖不存在" + mapId);
                return;
            }
            else {
                int x = mapData.getX();
                int y = mapData.getY();
                int height = mapData.getHeight();
                int width = mapData.getWidth();
                int newx = x + height / 2;
                int newy = y + width / 2;
                L1Location loc = new L1Location(newx, newy, mapId);
                L1Location newLocation = loc.randomLocation(200, true);
                int newX = newLocation.getX();
                int newY = newLocation.getY();
                L1Teleport.teleport(pc, newX, newY, mapId.shortValue(), 5, true);
            }
        }
        catch (Exception e) {
            L1ToMap._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
