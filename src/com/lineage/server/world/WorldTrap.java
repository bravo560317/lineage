package com.lineage.server.world;

import com.lineage.server.model.L1Location;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1TrapInstance;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class WorldTrap
{
    private static final Log _log;
    private static WorldTrap _instance;
    private final HashMap<Integer, L1TrapInstance> _isTrap;
    
    static {
        _log = LogFactory.getLog(WorldTrap.class);
    }
    
    public static WorldTrap get() {
        if (WorldTrap._instance == null) {
            WorldTrap._instance = new WorldTrap();
        }
        return WorldTrap._instance;
    }
    
    private WorldTrap() {
        this._isTrap = new HashMap();
    }
    
    public HashMap<Integer, L1TrapInstance> map() {
        return this._isTrap;
    }
    
    public void put(Integer key, L1TrapInstance value) {
        try {
            this._isTrap.put(key, value);
        }
        catch (Exception e) {
            WorldTrap._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(Integer key) {
        try {
            this._isTrap.remove(key);
        }
        catch (Exception e) {
            WorldTrap._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void resetAllTraps() {
        Object[] array;
        for (int length = (array = this._isTrap.values().toArray()).length, i = 0; i < length; ++i) {
            Object iter = array[i];
            L1TrapInstance trap = (L1TrapInstance)iter;
            trap.resetLocation();
            trap.enableTrap();
        }
    }
    
    public void onPlayerMoved(L1PcInstance pc) {
        L1Location loc = pc.getLocation();
        Object[] array;
        for (int length = (array = this._isTrap.values().toArray()).length, i = 0; i < length; ++i) {
            Object iter = array[i];
            L1TrapInstance trap = (L1TrapInstance)iter;
            if (trap.isEnable() && loc.equals(trap.getLocation())) {
                trap.onTrod(pc);
                trap.disableTrap();
            }
        }
    }
    
    public void onDetection(L1PcInstance pc) {
        L1Location loc = pc.getLocation();
        Object[] array;
        for (int length = (array = this._isTrap.values().toArray()).length, i = 0; i < length; ++i) {
            Object iter = array[i];
            L1TrapInstance trap = (L1TrapInstance)iter;
            if (trap.isEnable() && loc.isInScreen(trap.getLocation())) {
                trap.onDetection(pc);
                trap.disableTrap();
            }
        }
    }
}
