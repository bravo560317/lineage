package com.lineage.server.world;

import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PetInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldPet
{
    private static final Log _log;
    private static WorldPet _instance;
    private final ConcurrentHashMap<Integer, L1PetInstance> _isPet;
    private Collection<L1PetInstance> _allPetValues;
    
    static {
        _log = LogFactory.getLog(WorldPet.class);
    }
    
    public static WorldPet get() {
        if (WorldPet._instance == null) {
            WorldPet._instance = new WorldPet();
        }
        return WorldPet._instance;
    }
    
    private WorldPet() {
        this._isPet = new ConcurrentHashMap();
    }
    
    public Collection<L1PetInstance> all() {
        try {
            Collection<L1PetInstance> vs = this._allPetValues;
            return (vs != null) ? vs : (this._allPetValues = (Collection)Collections.unmodifiableCollection(this._isPet.values()));
        }
        catch (Exception e) {
            WorldPet._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1PetInstance> map() {
        return this._isPet;
    }
    
    public L1PetInstance get(Integer key) {
        try {
            return this._isPet.get(key);
        }
        catch (Exception e) {
            WorldPet._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public void put(Integer key, L1PetInstance value) {
        try {
            this._isPet.put(key, value);
        }
        catch (Exception e) {
            WorldPet._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isPet.remove(key);
        }
        catch (Exception e) {
            WorldPet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
