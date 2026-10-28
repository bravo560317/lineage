package com.lineage.server.world;

import java.util.HashMap;
import com.lineage.server.model.L1CastleLocation;
import java.util.Iterator;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.model.L1Clan;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class WorldClan
{
    private static final Log _log;
    private static WorldClan _instance;
    private final ConcurrentHashMap<String, L1Clan> _isClan;
    private Collection<L1Clan> _allClanValues;
    
    static {
        _log = LogFactory.getLog(WorldClan.class);
    }
    
    public static WorldClan get() {
        if (WorldClan._instance == null) {
            WorldClan._instance = new WorldClan();
        }
        return WorldClan._instance;
    }
    
    private WorldClan() {
        this._isClan = new ConcurrentHashMap();
    }
    
    public Collection<L1Clan> getAllClans() {
        try {
            Collection<L1Clan> vs = this._allClanValues;
            return (vs != null) ? vs : (this._allClanValues = (Collection)Collections.unmodifiableCollection(this._isClan.values()));
        }
        catch (Exception e) {
            WorldClan._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public L1Clan getClan(int clan_id) {
        L1Clan clan = null;
        Collection<L1Clan> allClans = this.getAllClans();
        for (L1Clan c : allClans) {
            if (c.getClanId() == clan_id) {
                clan = c;
                break;
            }
        }
        return clan;
    }
    
    public void storeClan(L1Clan clan) {
        L1Clan temp = this.getClan(clan.getClanName());
        if (temp == null) {
            this._isClan.put(clan.getClanName(), clan);
            int castle_id = clan.getCastleId();
            if (castle_id != 0 && L1CastleLocation.mapCastle().get(new Integer(castle_id)) == null) {
                L1CastleLocation.putCastle(new Integer(castle_id), clan);
            }
        }
    }
    
    public void removeClan(L1Clan clan) {
        L1Clan temp = this.getClan(clan.getClanName());
        if (temp != null) {
            this._isClan.remove(clan.getClanName());
        }
    }
    
    public L1Clan getClan(String clan_name) {
        return this._isClan.get(clan_name);
    }
    
    public ConcurrentHashMap<String, L1Clan> map() {
        return this._isClan;
    }
    
    public HashMap<Integer, String> castleClanMap() {
        HashMap<Integer, String> isClan = new HashMap();
        for (L1Clan clan : this.getAllClans()) {
            if (clan.getCastleId() != 0) {
                isClan.put(clan.getCastleId(), clan.getClanName());
            }
        }
        return isClan;
    }
    
    public void put(String key, L1Clan value) {
        try {
            this._isClan.put(key, value);
        }
        catch (Exception e) {
            WorldClan._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(String key) {
        try {
            this._isClan.remove(key);
        }
        catch (Exception e) {
            WorldClan._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public String getCastleClanName(int id) {
        for (L1Clan clan : this.getAllClans()) {
            if (clan.getCastleId() == id) {
                return clan.getClanName();
            }
        }
        return " ";
    }
}
