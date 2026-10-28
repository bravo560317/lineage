package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldKnight
{
    private static final Log _log;
    private static WorldKnight _instance;
    private final ConcurrentHashMap<Integer, L1PcInstance> _isKnight;
    private Collection<L1PcInstance> _allPlayer;
    
    static {
        _log = LogFactory.getLog(WorldKnight.class);
    }
    
    public static WorldKnight get() {
        if (WorldKnight._instance == null) {
            WorldKnight._instance = new WorldKnight();
        }
        return WorldKnight._instance;
    }
    
    private WorldKnight() {
        this._isKnight = new ConcurrentHashMap();
    }
    
    public Collection<L1PcInstance> all() {
        try {
            Collection<L1PcInstance> vs = this._allPlayer;
            return (vs != null) ? vs : (this._allPlayer = (Collection)Collections.unmodifiableCollection(this._isKnight.values()));
        }
        catch (Exception e) {
            WorldKnight._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1PcInstance> map() {
        return this._isKnight;
    }
    
    public void put(Integer key, L1PcInstance value) {
        try {
            this._isKnight.put(key, value);
        }
        catch (Exception e) {
            WorldKnight._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isKnight.remove(key);
        }
        catch (Exception e) {
            WorldKnight._log.error(e.getLocalizedMessage(), e);
        }
    }
}
