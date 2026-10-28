package com.lineage.server.world;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Character;
import com.lineage.server.types.Point;
import com.lineage.server.model.map.L1Map;
import java.util.ArrayList;
import java.util.Iterator;
import com.lineage.server.model.L1Location;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.Instance.L1EffectInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldEffect
{
    private static final Log _log;
    private static WorldEffect _instance;
    private final ConcurrentHashMap<Integer, L1EffectInstance> _isEff;
    private Collection<L1EffectInstance> _allEffValues;
    
    static {
        _log = LogFactory.getLog(WorldEffect.class);
    }
    
    public static WorldEffect get() {
        if (WorldEffect._instance == null) {
            WorldEffect._instance = new WorldEffect();
        }
        return WorldEffect._instance;
    }
    
    private WorldEffect() {
        this._isEff = new ConcurrentHashMap();
    }
    
    public Collection<L1EffectInstance> all() {
        try {
            Collection<L1EffectInstance> vs = this._allEffValues;
            return (vs != null) ? vs : (this._allEffValues = (Collection)Collections.unmodifiableCollection(this._isEff.values()));
        }
        catch (Exception e) {
            WorldEffect._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1EffectInstance> map() {
        return this._isEff;
    }
    
    public void put(Integer key, L1EffectInstance value) {
        try {
            this._isEff.put(key, value);
        }
        catch (Exception e) {
            WorldEffect._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isEff.remove(key);
        }
        catch (Exception e) {
            WorldEffect._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean isEffect(L1Location loc, int npcid) {
        for (L1EffectInstance element : this.all()) {
            if (loc.getMapId() != element.getMap().getId()) {
                continue;
            }
            else if (npcid != element.getNpcId()) {
                continue;
            }
            else if (loc.isSamePoint(element.getLocation())) {
                return true;
            }
            else {
                continue;
            }
        }
        return false;
    }
    
    public ArrayList<L1EffectInstance> getVisibleEffect(L1Location loc) {
        ArrayList<L1EffectInstance> result = new ArrayList();
        for (L1EffectInstance element : this.all()) {
            if (loc.getMapId() != element.getMap().getId()) {
                continue;
            }
            else if (loc.isSamePoint(element.getLocation())) {
                result.add(element);
            }
            else {
                continue;
            }
        }
        return result;
    }
    
    public ArrayList<L1EffectInstance> getVisibleEffect(L1EffectInstance src) {
        L1Map map = src.getMap();
        Point pt = src.getLocation();
        ArrayList<L1EffectInstance> result = new ArrayList();
        for (L1EffectInstance element : this.all()) {
            if (element.equals(src)) {
                continue;
            }
            else if (map.getId() != element.getMap().getId()) {
                continue;
            }
            else if (src.getNpcId() != element.getNpcId()) {
                continue;
            }
            else if (pt.isSamePoint(element.getLocation())) {
                result.add(element);
            }
            else {
                continue;
            }
        }
        return result;
    }
    
    public int getVisibleCount(L1EffectInstance src) {
        L1Map map = src.getMap();
        Point pt = src.getLocation();
        int count = 0;
        for (L1EffectInstance element : this.all()) {
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
    
    public ArrayList<L1Character> getFirewall(L1EffectInstance firewall) {
        L1Map map = firewall.getMap();
        Point pt = firewall.getLocation();
        ArrayList<L1Character> result = new ArrayList();
        ArrayList<L1Object> mapSrc = World.get().getVisibleObjects(firewall, 2);
        if (mapSrc == null) {
            WorldEffect._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + map.getId());
            return result;
        }
        else if (mapSrc.isEmpty()) {
            return result;
        }
        else if (World.get().findObject(firewall.getId()) == null) {
            return result;
        }
        else if (World.get().findObject(firewall.getMaster().getId()) == null) {
            return result;
        }
        else if (firewall.destroyed()) {
            return result;
        }
        else if (firewall.getMaster() == null) {
            return result;
        }
        else {
            for (L1Object element : mapSrc) {
                if (!(element instanceof L1Character)) {
                    continue;
                }
                else if (firewall.getMaster().equals(element)) {
                    continue;
                }
                else {
                    L1Character cha = (L1Character)element;
                    if (cha.isDead()) {
                        continue;
                    }
                    else {
                        int r = pt.getTileLineDistance(element.getLocation());
                        if (r <= 1) {
                            result.add((L1Character)element);
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            return result;
        }
    }
}
