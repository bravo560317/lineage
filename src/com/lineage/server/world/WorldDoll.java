package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1DollInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldDoll
{
    private static final Log _log;
    private static WorldDoll _instance;
    private final ConcurrentHashMap<Integer, L1DollInstance> _isDoll;
    private Collection<L1DollInstance> _allDollValues;
    
    static {
        _log = LogFactory.getLog(WorldDoll.class);
    }
    
    public static WorldDoll get() {
        if (WorldDoll._instance == null) {
            WorldDoll._instance = new WorldDoll();
        }
        return WorldDoll._instance;
    }
    
    private WorldDoll() {
        this._isDoll = new ConcurrentHashMap();
    }
    
    public Collection<L1DollInstance> all() {
        try {
            Collection<L1DollInstance> vs = this._allDollValues;
            return (vs != null) ? vs : (this._allDollValues = (Collection)Collections.unmodifiableCollection(this._isDoll.values()));
        }
        catch (Exception e) {
            WorldDoll._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1DollInstance> map() {
        return this._isDoll;
    }
    
    public L1DollInstance get(Integer key) {
        try {
            return this._isDoll.get(key);
        }
        catch (Exception e) {
            WorldDoll._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public void put(Integer key, L1DollInstance value) {
        try {
            this._isDoll.put(key, value);
        }
        catch (Exception e) {
            WorldDoll._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isDoll.remove(key);
        }
        catch (Exception e) {
            WorldDoll._log.error(e.getLocalizedMessage(), e);
        }
    }
}
