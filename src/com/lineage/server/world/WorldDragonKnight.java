package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldDragonKnight
{
    private static final Log _log;
    private static WorldDragonKnight _instance;
    private final ConcurrentHashMap<Integer, L1PcInstance> _isDragonKnight;
    private Collection<L1PcInstance> _allPlayer;
    
    static {
        _log = LogFactory.getLog(WorldDragonKnight.class);
    }
    
    public static WorldDragonKnight get() {
        if (WorldDragonKnight._instance == null) {
            WorldDragonKnight._instance = new WorldDragonKnight();
        }
        return WorldDragonKnight._instance;
    }
    
    private WorldDragonKnight() {
        this._isDragonKnight = new ConcurrentHashMap();
    }
    
    public Collection<L1PcInstance> all() {
        try {
            Collection<L1PcInstance> vs = this._allPlayer;
            return (vs != null) ? vs : (this._allPlayer = (Collection)Collections.unmodifiableCollection(this._isDragonKnight.values()));
        }
        catch (Exception e) {
            WorldDragonKnight._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1PcInstance> map() {
        return this._isDragonKnight;
    }
    
    public void put(Integer key, L1PcInstance value) {
        try {
            this._isDragonKnight.put(key, value);
        }
        catch (Exception e) {
            WorldDragonKnight._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isDragonKnight.remove(key);
        }
        catch (Exception e) {
            WorldDragonKnight._log.error(e.getLocalizedMessage(), e);
        }
    }
}
