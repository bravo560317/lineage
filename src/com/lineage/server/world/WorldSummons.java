package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1SummonInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldSummons
{
    private static final Log _log;
    private static WorldSummons _instance;
    private final ConcurrentHashMap<Integer, L1SummonInstance> _isSummons;
    private Collection<L1SummonInstance> _allSummonValues;
    
    static {
        _log = LogFactory.getLog(WorldSummons.class);
    }
    
    public static WorldSummons get() {
        if (WorldSummons._instance == null) {
            WorldSummons._instance = new WorldSummons();
        }
        return WorldSummons._instance;
    }
    
    private WorldSummons() {
        this._isSummons = new ConcurrentHashMap();
    }
    
    public Collection<L1SummonInstance> all() {
        try {
            Collection<L1SummonInstance> vs = this._allSummonValues;
            return (vs != null) ? vs : (this._allSummonValues = (Collection)Collections.unmodifiableCollection(this._isSummons.values()));
        }
        catch (Exception e) {
            WorldSummons._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1SummonInstance> map() {
        return this._isSummons;
    }
    
    public void put(Integer key, L1SummonInstance value) {
        try {
            this._isSummons.put(key, value);
        }
        catch (Exception e) {
            WorldSummons._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isSummons.remove(key);
        }
        catch (Exception e) {
            WorldSummons._log.error(e.getLocalizedMessage(), e);
        }
    }
}
