package com.lineage.server.world;

import com.lineage.server.model.L1Object;
import java.util.Iterator;
import com.lineage.server.types.Point;
import com.lineage.server.model.map.L1Map;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1MonsterInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldMob
{
    private static final Log _log;
    private static WorldMob _instance;
    private final ConcurrentHashMap<Integer, L1MonsterInstance> _isMob;
    private Collection<L1MonsterInstance> _allMobValues;
    
    static {
        _log = LogFactory.getLog(WorldMob.class);
    }
    
    public static WorldMob get() {
        if (WorldMob._instance == null) {
            WorldMob._instance = new WorldMob();
        }
        return WorldMob._instance;
    }
    
    private WorldMob() {
        this._isMob = new ConcurrentHashMap();
    }
    
    public Collection<L1MonsterInstance> all() {
        try {
            Collection<L1MonsterInstance> vs = this._allMobValues;
            return (vs != null) ? vs : (this._allMobValues = (Collection)Collections.unmodifiableCollection(this._isMob.values()));
        }
        catch (Exception e) {
            WorldMob._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1MonsterInstance> map() {
        return this._isMob;
    }
    
    public void put(Integer key, L1MonsterInstance value) {
        try {
            this._isMob.put(key, value);
        }
        catch (Exception e) {
            WorldMob._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isMob.remove(key);
        }
        catch (Exception e) {
            WorldMob._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public ArrayList<L1MonsterInstance> getVisibleMob(L1MonsterInstance src) {
        L1Map map = src.getMap();
        Point pt = src.getLocation();
        ArrayList<L1MonsterInstance> result = new ArrayList();
        for (L1MonsterInstance element : this.all()) {
            if (element.equals(src)) {
                continue;
            }
            else if (map != element.getMap()) {
                continue;
            }
            else if (pt.isInScreen(element.getLocation())) {
                result.add(element);
            }
            else {
                continue;
            }
        }
        return result;
    }
    
    public ArrayList<L1MonsterInstance> getVisibleMob(L1Object src) {
        L1Map map = src.getMap();
        Point pt = src.getLocation();
        ArrayList<L1MonsterInstance> result = new ArrayList();
        for (L1MonsterInstance element : this.all()) {
            if (!element.equals(src) && map == element.getMap() && src.get_showId() == element.get_showId() && pt.isInScreen(element.getLocation())) {
                result.add(element);
            }
        }
        return result;
    }
    
    public int getVisibleCount(L1MonsterInstance src) {
        L1Map map = src.getMap();
        Point pt = src.getLocation();
        int count = 0;
        for (L1MonsterInstance element : this.all()) {
            if (element.equals(src)) {
                continue;
            }
            else if (map != element.getMap()) {
                continue;
            }
            else if (pt.isInScreen(element.getLocation()) && src.getNpcId() == element.getNpcId()) {
                ++count;
            }
            else {
                continue;
            }
        }
        return count;
    }
    
    public int getCount(int map_id, int npc_id) {
        int count = 0;
        for (L1MonsterInstance element : this.all()) {
            if (map_id == element.getMap().getId() && npc_id == element.getNpcId() && !element.isDead()) {
                ++count;
            }
        }
        return count;
    }
    
    public Collection<L1MonsterInstance> getAllMonster() {
        Collection<L1MonsterInstance> vs = this._allMobValues;
        return this._allMobValues = (Collection)Collections.unmodifiableCollection(this._isMob.values());
    }
}
