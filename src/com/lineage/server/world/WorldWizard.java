package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldWizard
{
    private static final Log _log;
    private static WorldWizard _instance;
    private final ConcurrentHashMap<Integer, L1PcInstance> _isWizard;
    private Collection<L1PcInstance> _allPlayer;
    
    static {
        _log = LogFactory.getLog(WorldWizard.class);
    }
    
    public static WorldWizard get() {
        if (WorldWizard._instance == null) {
            WorldWizard._instance = new WorldWizard();
        }
        return WorldWizard._instance;
    }
    
    private WorldWizard() {
        this._isWizard = new ConcurrentHashMap();
    }
    
    public Collection<L1PcInstance> all() {
        try {
            Collection<L1PcInstance> vs = this._allPlayer;
            return (vs != null) ? vs : (this._allPlayer = (Collection)Collections.unmodifiableCollection(this._isWizard.values()));
        }
        catch (Exception e) {
            WorldWizard._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1PcInstance> map() {
        return this._isWizard;
    }
    
    public void put(Integer key, L1PcInstance value) {
        try {
            this._isWizard.put(key, value);
        }
        catch (Exception e) {
            WorldWizard._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isWizard.remove(key);
        }
        catch (Exception e) {
            WorldWizard._log.error(e.getLocalizedMessage(), e);
        }
    }
}
