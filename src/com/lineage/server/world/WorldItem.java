package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldItem
{
    private static final Log _log;
    private static WorldItem _instance;
    private final ConcurrentHashMap<Integer, L1ItemInstance> _isItem;
    private Collection<L1ItemInstance> _allItemValues;
    
    static {
        _log = LogFactory.getLog(WorldItem.class);
    }
    
    public static WorldItem get() {
        if (WorldItem._instance == null) {
            WorldItem._instance = new WorldItem();
        }
        return WorldItem._instance;
    }
    
    private WorldItem() {
        this._isItem = new ConcurrentHashMap();
    }
    
    public Collection<L1ItemInstance> all() {
        try {
            Collection<L1ItemInstance> vs = this._allItemValues;
            return (vs != null) ? vs : (this._allItemValues = (Collection)Collections.unmodifiableCollection(this._isItem.values()));
        }
        catch (Exception e) {
            WorldItem._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1ItemInstance> map() {
        return this._isItem;
    }
    
    public void put(Integer key, L1ItemInstance value) {
        try {
            this._isItem.put(key, value);
        }
        catch (Exception e) {
            WorldItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isItem.remove(key);
        }
        catch (Exception e) {
            WorldItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public L1ItemInstance getItem(Integer key) {
        return this._isItem.get(key);
    }
}
