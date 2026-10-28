package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1DeInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldDe
{
    private static final Log _log;
    private static WorldDe _instance;
    private final ConcurrentHashMap<String, L1DeInstance> _isDe;
    private Collection<L1DeInstance> _allDe;
    
    static {
        _log = LogFactory.getLog(WorldDe.class);
    }
    
    public static WorldDe get() {
        if (WorldDe._instance == null) {
            WorldDe._instance = new WorldDe();
        }
        return WorldDe._instance;
    }
    
    private WorldDe() {
        this._isDe = new ConcurrentHashMap();
    }
    
    public Collection<L1DeInstance> all() {
        try {
            Collection<L1DeInstance> vs = this._allDe;
            return (vs != null) ? vs : (this._allDe = (Collection)Collections.unmodifiableCollection(this._isDe.values()));
        }
        catch (Exception e) {
            WorldDe._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<String, L1DeInstance> map() {
        return this._isDe;
    }
    
    public L1DeInstance getDe(String key) {
        try {
            return this._isDe.get(key);
        }
        catch (Exception e) {
            WorldDe._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public void put(String key, L1DeInstance value) {
        try {
            this._isDe.put(key, value);
        }
        catch (Exception e) {
            WorldDe._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(String key) {
        try {
            this._isDe.remove(key);
        }
        catch (Exception e) {
            WorldDe._log.error(e.getLocalizedMessage(), e);
        }
    }
}
