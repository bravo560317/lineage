package com.lineage.server.model.map;

import com.lineage.MapReader;
import com.lineage.config.Config;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.TextMapReader;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1WorldMap
{
    private static final Log _log;
    private static L1WorldMap _instance;
    private Map<Integer, L1Map> _maps;
    
    static {
        _log = LogFactory.getLog(TextMapReader.class);
    }
    
    public static L1WorldMap get() {
        if (L1WorldMap._instance == null) {
            L1WorldMap._instance = new L1WorldMap();
        }
        return L1WorldMap._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        L1WorldMap._log.info("MAP進行數據加載...");
        MapReader in = null;
        try {
            if (Config.CACHE_MAP_FILES) {
                in = new CachedMapReader();
            }
            else {
                in = new TextMapReader();
            }
            this._maps = in.read();
            if (this._maps == null) {
                L1WorldMap._log.error("MAP數據載入異常 maps未建立初始化");
                System.exit(0);
                return;
            }
        }
        catch (Exception e) {
            L1WorldMap._log.error(e.getLocalizedMessage(), e);
            L1WorldMap._log.info("MAP數據加載完成(" + timer.get() + " ms)");
        }
    }
    
    public L1Map getMap(short mapId) {
        L1Map map = this._maps.get((Integer)(int)mapId);
        if (map == null) {
            map = L1Map.newNull();
        }
        return map;
    }
    
    public void cloneMap(int targetId, int newId) {
        L1Map copymap = null;
        copymap = (this._maps.get((Integer)targetId)).copyMap(newId);
        this._maps.put(newId, copymap);
    }
}
