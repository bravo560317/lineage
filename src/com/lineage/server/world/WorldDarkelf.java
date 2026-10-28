package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldDarkelf
{
    private static final Log _log;
    private static WorldDarkelf _instance;
    private final ConcurrentHashMap<Integer, L1PcInstance> _isDarkelf;
    private Collection<L1PcInstance> _allPlayer;
    
    static {
        _log = LogFactory.getLog(WorldDarkelf.class);
    }
    
    public static WorldDarkelf get() {
        if (WorldDarkelf._instance == null) {
            WorldDarkelf._instance = new WorldDarkelf();
        }
        return WorldDarkelf._instance;
    }
    
    private WorldDarkelf() {
        this._isDarkelf = new ConcurrentHashMap();
    }
    
    public Collection<L1PcInstance> all() {
        try {
            Collection<L1PcInstance> vs = this._allPlayer;
            return (vs != null) ? vs : (this._allPlayer = (Collection)Collections.unmodifiableCollection(this._isDarkelf.values()));
        }
        catch (Exception e) {
            WorldDarkelf._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1PcInstance> map() {
        return this._isDarkelf;
    }
    
    public void put(Integer key, L1PcInstance value) {
        try {
            this._isDarkelf.put(key, value);
        }
        catch (Exception e) {
            WorldDarkelf._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isDarkelf.remove(key);
        }
        catch (Exception e) {
            WorldDarkelf._log.error(e.getLocalizedMessage(), e);
        }
    }
}
