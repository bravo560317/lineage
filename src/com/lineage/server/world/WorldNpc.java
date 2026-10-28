package com.lineage.server.world;

import java.util.Iterator;
import com.lineage.server.types.Point;
import com.lineage.server.model.map.L1Map;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldNpc
{
    private static final Log _log;
    private static WorldNpc _instance;
    private final ConcurrentHashMap<Integer, L1NpcInstance> _isNpc;
    private Collection<L1NpcInstance> _allNpcValues;
    
    static {
        _log = LogFactory.getLog(WorldNpc.class);
    }
    
    public static WorldNpc get() {
        if (WorldNpc._instance == null) {
            WorldNpc._instance = new WorldNpc();
        }
        return WorldNpc._instance;
    }
    
    private WorldNpc() {
        this._isNpc = new ConcurrentHashMap();
    }
    
    public Collection<L1NpcInstance> all() {
        try {
            Collection<L1NpcInstance> vs = this._allNpcValues;
            return (vs != null) ? vs : (this._allNpcValues = (Collection)Collections.unmodifiableCollection(this._isNpc.values()));
        }
        catch (Exception e) {
            WorldNpc._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1NpcInstance> map() {
        return this._isNpc;
    }
    
    public void put(Integer key, L1NpcInstance value) {
        try {
            this._isNpc.put(key, value);
        }
        catch (Exception e) {
            WorldNpc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isNpc.remove(key);
        }
        catch (Exception e) {
            WorldNpc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public ArrayList<L1NpcInstance> getVisibleMob(L1NpcInstance src) {
        L1Map map = src.getMap();
        Point pt = src.getLocation();
        ArrayList<L1NpcInstance> result = new ArrayList();
        for (L1NpcInstance element : this.all()) {
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
    
    public int getVisibleCount(L1NpcInstance src) {
        L1Map map = src.getMap();
        Point pt = src.getLocation();
        int count = 0;
        for (L1NpcInstance element : this.all()) {
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
}
