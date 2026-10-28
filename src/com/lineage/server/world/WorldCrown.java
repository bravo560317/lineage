package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldCrown
{
    private static final Log _log;
    private static WorldCrown _instance;
    private final ConcurrentHashMap<Integer, L1PcInstance> _isCrown;
    private Collection<L1PcInstance> _allPlayer;
    
    static {
        _log = LogFactory.getLog(WorldCrown.class);
    }
    
    public static WorldCrown get() {
        if (WorldCrown._instance == null) {
            WorldCrown._instance = new WorldCrown();
        }
        return WorldCrown._instance;
    }
    
    private WorldCrown() {
        this._isCrown = new ConcurrentHashMap();
    }
    
    public Collection<L1PcInstance> all() {
        try {
            Collection<L1PcInstance> vs = this._allPlayer;
            return (vs != null) ? vs : (this._allPlayer = (Collection)Collections.unmodifiableCollection(this._isCrown.values()));
        }
        catch (Exception e) {
            WorldCrown._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1PcInstance> map() {
        return this._isCrown;
    }
    
    public void put(Integer key, L1PcInstance value) {
        try {
            this._isCrown.put(key, value);
        }
        catch (Exception e) {
            WorldCrown._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isCrown.remove(key);
        }
        catch (Exception e) {
            WorldCrown._log.error(e.getLocalizedMessage(), e);
        }
    }
}
