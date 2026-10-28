package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1CrockInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldCrock
{
    private static final Log _log;
    private static WorldCrock _instance;
    private final ConcurrentHashMap<Integer, L1CrockInstance> _crockList;
    private Collection<L1CrockInstance> _allCrock;
    
    static {
        _log = LogFactory.getLog(WorldCrock.class);
    }
    
    public static WorldCrock get() {
        if (WorldCrock._instance == null) {
            WorldCrock._instance = new WorldCrock();
        }
        return WorldCrock._instance;
    }
    
    private WorldCrock() {
        this._crockList = new ConcurrentHashMap();
    }
    
    public Collection<L1CrockInstance> all() {
        try {
            Collection<L1CrockInstance> vs = this._allCrock;
            return (vs != null) ? vs : (this._allCrock = (Collection)Collections.unmodifiableCollection(this._crockList.values()));
        }
        catch (Exception e) {
            WorldCrock._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1CrockInstance> map() {
        return this._crockList;
    }
    
    public void put(int key, L1CrockInstance value) {
        try {
            this._crockList.put(key, value);
        }
        catch (Exception e) {
            WorldCrock._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(int key) {
        try {
            this._crockList.remove((Integer)key);
        }
        catch (Exception e) {
            WorldCrock._log.error(e.getLocalizedMessage(), e);
        }
    }
}
