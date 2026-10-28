package com.lineage.server.world;

import java.util.Iterator;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import com.lineage.server.model.L1War;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.logging.Log;

public class WorldWar
{
    private static final Log _log;
    private static WorldWar _instance;
    private final CopyOnWriteArrayList<L1War> _allWars;
    private List<L1War> _allWarList;
    
    static {
        _log = LogFactory.getLog(WorldWar.class);
    }
    
    public static WorldWar get() {
        if (WorldWar._instance == null) {
            WorldWar._instance = new WorldWar();
        }
        return WorldWar._instance;
    }
    
    private WorldWar() {
        this._allWars = new CopyOnWriteArrayList();
    }
    
    public void addWar(L1War war) {
        try {
            if (!this._allWars.contains(war)) {
                this._allWars.add(war);
            }
        }
        catch (Exception e) {
            WorldWar._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void removeWar(L1War war) {
        try {
            if (this._allWars.contains(war)) {
                this._allWars.remove(war);
            }
        }
        catch (Exception e) {
            WorldWar._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public List<L1War> getWarList() {
        try {
            List<L1War> vs = this._allWarList;
            return (vs != null) ? vs : (this._allWarList = (List)Collections.unmodifiableList(this._allWars));
        }
        catch (Exception e) {
            WorldWar._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public boolean isWar(String clanname, String tgclanname) {
        try {
            for (L1War war : this._allWars) {
                boolean isInWar = war.checkClanInWar(clanname);
                if (isInWar) {
                    boolean isInWarTg = war.checkClanInWar(tgclanname);
                    if (isInWarTg) {
                        return true;
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (Exception e) {
            WorldWar._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}
