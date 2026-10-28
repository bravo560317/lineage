package com.lineage.server.model;

import com.lineage.server.model.map.L1WorldMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.map.L1Map;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.server.types.Point;

public class L1Location extends Point
{
    private static final Log _log;
    private static Random _random;
    protected L1Map _map;
    
    static {
        _log = LogFactory.getLog(L1Location.class);
        L1Location._random = new Random();
    }
    
    public L1Location() {
        this._map = L1Map.newNull();
    }
    
    public L1Location(L1Location loc) {
        this(loc._x, loc._y, loc._map);
    }
    
    public L1Location(int x, int y, int mapId) {
        super(x, y);
        this._map = L1Map.newNull();
        this.setMap(mapId);
    }
    
    public L1Location(int x, int y, L1Map map) {
        super(x, y);
        this._map = L1Map.newNull();
        this._map = map;
    }
    
    public L1Location(Point pt, int mapId) {
        super(pt);
        this._map = L1Map.newNull();
        this.setMap(mapId);
    }
    
    public L1Location(Point pt, L1Map map) {
        super(pt);
        this._map = L1Map.newNull();
        this._map = map;
    }
    
    public void set(L1Location loc) {
        this._map = loc._map;
        this._x = loc._x;
        this._y = loc._y;
    }
    
    public void set(int x, int y, int mapId) {
        this.set(x, y);
        this.setMap(mapId);
    }
    
    public void set(int x, int y, L1Map map) {
        this.set(x, y);
        this._map = map;
    }
    
    public void set(Point pt, int mapId) {
        this.set(pt);
        this.setMap(mapId);
    }
    
    public void set(Point pt, L1Map map) {
        this.set(pt);
        this._map = map;
    }
    
    public L1Map getMap() {
        return this._map;
    }
    
    public int getMapId() {
        return this._map.getId();
    }
    
    public void setMap(L1Map map) {
        this._map = map;
    }
    
    public void setMap(int mapId) {
        this._map = L1WorldMap.get().getMap((short)mapId);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof L1Location)) {
            return false;
        }
        else {
            L1Location loc = (L1Location)obj;
            return this.getMap() == loc.getMap() && this.getX() == loc.getX() && this.getY() == loc.getY();
        }
    }
    
    @Override
    public int hashCode() {
        return 7 * this._map.getId() + this.hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d) on %d", (Integer)this._x, (Integer)this._y, (Integer)this._map.getId());
    }
    
    public L1Location randomLocation(int max, boolean isRandomTeleport) {
        return this.randomLocation(0, max, isRandomTeleport);
    }
    
    public L1Location randomLocation(int min, int max, boolean isRandomTeleport) {
        return randomLocation(this, min, max, isRandomTeleport);
    }
    
    public static L1Location randomLocation(L1Location baseLocation, int min, int max, boolean isRandomTeleport) {
        L1Location newLocation = null;
        try {
            if (min > max) {
                throw new IllegalArgumentException("min > max 設定異常");
            }
            else if (max <= 0) {
                return new L1Location(baseLocation);
            }
            else {
                if (min < 0) {
                    min = 0;
                }
                newLocation = new L1Location();
                int newX = 0;
                int newY = 0;
                int locX = baseLocation.getX();
                int locY = baseLocation.getY();
                short mapId = (short)baseLocation.getMapId();
                L1Map map = baseLocation.getMap();
                newLocation.setMap(map);
                int locX2 = locX - max;
                int locX3 = locX + max;
                int locY2 = locY - max;
                int locY3 = locY + max;
                int mapX1 = map.getX();
                int mapX2 = mapX1 + map.getWidth();
                int mapY1 = map.getY();
                int mapY2 = mapY1 + map.getHeight();
                if (locX2 < mapX1) {
                    locX2 = mapX1;
                }
                if (locX3 > mapX2) {
                    locX3 = mapX2;
                }
                if (locY2 < mapY1) {
                    locY2 = mapY1;
                }
                if (locY3 > mapY2) {
                    locY3 = mapY2;
                }
                int diffX = locX3 - locX2;
                int diffY = locY3 - locY2;
                int trial = 0;
                int amax = (int)Math.pow((double)(1 + max * 2), 2.0);
                int amin = (min == 0) ? 0 : ((int)Math.pow((double)(1 + (min - 1) * 2), 2.0));
                int trialLimit = 40 * amax / (amax - amin);
                boolean run = true;
                while (run) {
                    if (trial >= trialLimit) {
                        newLocation.set(locX, locY);
                        run = false;
                        break;
                    }
                    else {
                        ++trial;
                        try {
                            newX = locX2 + L1Location._random.nextInt(diffX + 1);
                            newY = locY2 + L1Location._random.nextInt(diffY + 1);
                        }
                        catch (Exception e) {
                            newLocation.set(locX, locY);
                            run = false;
                            break;
                        }
                        newLocation.set(newX, newY);
                        if (baseLocation.getTileLineDistance(newLocation) < min) {
                            continue;
                        }
                        else {
                        if (isRandomTeleport) {
                            if (L1CastleLocation.checkInAllWarArea(newX, newY, mapId)) {
                                continue;
                            }
                            // 移除對安全區(盟屋)的判斷
                            /*
                            else if (L1HouseLocation.isInHouse(newX, newY, mapId)) {
                                continue;
                            }
                            */
                        }
                            if (map.isInMap(newX, newY) && map.isPassable(newX, newY, null)) {
                                run = false;
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            L1Location._log.error(e2.getLocalizedMessage(), e2);
        }
        return newLocation;
    }
}
