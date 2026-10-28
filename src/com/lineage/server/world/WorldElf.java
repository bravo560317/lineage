package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldElf
{
    private static final Log _log;
    private static WorldElf _instance;
    private final ConcurrentHashMap<Integer, L1PcInstance> _isElf;
    private Collection<L1PcInstance> _allPlayer;
    
    static {
        _log = LogFactory.getLog(WorldElf.class);
    }
    
    public static WorldElf get() {
        if (WorldElf._instance == null) {
            WorldElf._instance = new WorldElf();
        }
        return WorldElf._instance;
    }
    
    private WorldElf() {
        this._isElf = new ConcurrentHashMap();
    }
    
    public Collection<L1PcInstance> all() {
        try {
            Collection<L1PcInstance> vs = this._allPlayer;
            return (vs != null) ? vs : (this._allPlayer = (Collection)Collections.unmodifiableCollection(this._isElf.values()));
        }
        catch (Exception e) {
            WorldElf._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1PcInstance> map() {
        return this._isElf;
    }
    
    public void put(Integer key, L1PcInstance value) {
        try {
            this._isElf.put(key, value);
        }
        catch (Exception e) {
            WorldElf._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isElf.remove(key);
        }
        catch (Exception e) {
            WorldElf._log.error(e.getLocalizedMessage(), e);
        }
    }
}
