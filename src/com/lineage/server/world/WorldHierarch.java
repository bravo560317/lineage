package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1HierarchInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldHierarch
{
    private static final Log _log;
    private static WorldHierarch _instance;
    private final ConcurrentHashMap<Integer, L1HierarchInstance> _isHierarchs;
    private Collection<L1HierarchInstance> _allHierarchValues;
    
    static {
        _log = LogFactory.getLog(WorldHierarch.class);
    }
    
    public static WorldHierarch get() {
        if (WorldHierarch._instance == null) {
            WorldHierarch._instance = new WorldHierarch();
        }
        return WorldHierarch._instance;
    }
    
    private WorldHierarch() {
        this._isHierarchs = new ConcurrentHashMap();
    }
    
    public Collection<L1HierarchInstance> all() {
        try {
            Collection<L1HierarchInstance> vs = this._allHierarchValues;
            return (vs != null) ? vs : (this._allHierarchValues = (Collection)Collections.unmodifiableCollection(this._isHierarchs.values()));
        }
        catch (Exception e) {
            WorldHierarch._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1HierarchInstance> map() {
        return this._isHierarchs;
    }
    
    public void put(Integer key, L1HierarchInstance value) {
        try {
            this._isHierarchs.put(key, value);
        }
        catch (Exception e) {
            WorldHierarch._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isHierarchs.remove(key);
        }
        catch (Exception e) {
            WorldHierarch._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int getWorldHierarchAmount() {
        return this._isHierarchs.size();
    }
}
