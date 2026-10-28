package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldIllusionist
{
    private static final Log _log;
    private static WorldIllusionist _instance;
    private final ConcurrentHashMap<Integer, L1PcInstance> _isIllusionist;
    private Collection<L1PcInstance> _allPlayer;
    
    static {
        _log = LogFactory.getLog(WorldIllusionist.class);
    }
    
    public static WorldIllusionist get() {
        if (WorldIllusionist._instance == null) {
            WorldIllusionist._instance = new WorldIllusionist();
        }
        return WorldIllusionist._instance;
    }
    
    private WorldIllusionist() {
        this._isIllusionist = new ConcurrentHashMap();
    }
    
    public Collection<L1PcInstance> all() {
        try {
            Collection<L1PcInstance> vs = this._allPlayer;
            return (vs != null) ? vs : (this._allPlayer = (Collection)Collections.unmodifiableCollection(this._isIllusionist.values()));
        }
        catch (Exception e) {
            WorldIllusionist._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1PcInstance> map() {
        return this._isIllusionist;
    }
    
    public void put(Integer key, L1PcInstance value) {
        try {
            this._isIllusionist.put(key, value);
        }
        catch (Exception e) {
            WorldIllusionist._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isIllusionist.remove(key);
        }
        catch (Exception e) {
            WorldIllusionist._log.error(e.getLocalizedMessage(), e);
        }
    }
}
