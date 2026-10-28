package com.lineage.server.world;

import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1BowInstance;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class WorldBow
{
    private static final Log _log;
    private static WorldBow _instance;
    private final HashMap<Integer, L1BowInstance> _isBow;
    
    static {
        _log = LogFactory.getLog(WorldBow.class);
    }
    
    public static WorldBow get() {
        if (WorldBow._instance == null) {
            WorldBow._instance = new WorldBow();
        }
        return WorldBow._instance;
    }
    
    private WorldBow() {
        this._isBow = new HashMap();
    }
    
    public HashMap<Integer, L1BowInstance> map() {
        return this._isBow;
    }
    
    public void put(Integer key, L1BowInstance value) {
        try {
            this._isBow.put(key, value);
        }
        catch (Exception e) {
            WorldBow._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isBow.remove(key);
        }
        catch (Exception e) {
            WorldBow._log.error(e.getLocalizedMessage(), e);
        }
    }
}
