package com.lineage.server.model;

import java.util.Iterator;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1HouseLocTmp;
import java.util.Map;
import com.lineage.server.types.Point;
import org.apache.commons.logging.Log;

public class L1HouseLocation
{
    private static final Log _log;
    private static final int[] TELEPORT_LOC_MAPID;
    private static final Point[] TELEPORT_LOC_GIRAN;
    private static final Point[] TELEPORT_LOC_HEINE;
    private static final Point[] TELEPORT_LOC_ADEN;
    private static final Point[] TELEPORT_LOC_GLUDIN;
    private static final Map<Integer, L1HouseLocTmp> _houseLoc;
    
    static {
        _log = LogFactory.getLog(L1HouseLocation.class);
        TELEPORT_LOC_MAPID = new int[] { 4, 4, 4, 350 };
        TELEPORT_LOC_GIRAN = new Point[] { new Point(33419, 32810), new Point(33343, 32723), new Point(33553, 32712), new Point(32702, 32842) };
        TELEPORT_LOC_HEINE = new Point[] { new Point(33604, 33236), new Point(33649, 33413), new Point(33553, 32712), new Point(32702, 32842) };
        TELEPORT_LOC_ADEN = new Point[] { new Point(33966, 33253), new Point(33921, 33177), new Point(33553, 32712), new Point(32702, 32842) };
        TELEPORT_LOC_GLUDIN = new Point[] { new Point(32628, 32807), new Point(32623, 32729), new Point(33553, 32712), new Point(32702, 32842) };
        _houseLoc = new HashMap();
    }
    
    public static void put(Integer e, L1HouseLocTmp loc) {
        L1HouseLocation._houseLoc.put(e, loc);
    }
    
    public static boolean isInHouse(int locx, int locy, short mapid) {
        for (Integer houseId : L1HouseLocation._houseLoc.keySet()) {
            if (isInHouseLoc(houseId, locx, locy, mapid)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isInHouseLoc(int houseId, int locx, int locy, short mapid) {
        try {
            L1HouseLocTmp loc = L1HouseLocation._houseLoc.get(new Integer(houseId));
            if (loc != null) {
                int locx2 = loc.get_locx1();
                int locx3 = loc.get_locx2();
                int locy2 = loc.get_locy1();
                int locy3 = loc.get_locy2();
                int locx4 = loc.get_locx3();
                int locx5 = loc.get_locx4();
                int locy4 = loc.get_locy3();
                int locy5 = loc.get_locy4();
                int locmapid = loc.get_mapid();
                int basement = loc.get_basement();
                if (locx >= locx2 && locx <= locx3 && locy >= locy2 && locy <= locy3 && mapid == locmapid) {
                    return true;
                }
                else if (locx4 != 0 && locx >= locx4 && locx <= locx5 && locy >= locy4 && locy <= locy5 && mapid == locmapid) {
                    return true;
                }
                else if (basement != 0 && mapid == basement) {
                    return true;
                }
            }
        }
        catch (Exception e) {
            L1HouseLocation._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    public static int[] getHouseLoc(int houseId) {
        int[] loc = new int[3];
        try {
            L1HouseLocTmp locTmp = L1HouseLocation._houseLoc.get(new Integer(houseId));
            if (loc != null) {
                loc[0] = locTmp.get_homelocx();
                loc[1] = locTmp.get_homelocy();
                loc[2] = locTmp.get_mapid();
            }
        }
        catch (Exception e) {
            L1HouseLocation._log.error(e.getLocalizedMessage(), e);
        }
        return loc;
    }
    
    public static int[] getBasementLoc(int houseId) {
        int[] loc = new int[3];
        if (houseId >= 262145 && houseId <= 262189) {
            loc[0] = 32765;
            loc[1] = 32832;
            loc[2] = houseId - 257077;
        }
        else if (houseId >= 327681 && houseId <= 327691) {
            loc[0] = 32766;
            loc[1] = 32829;
            loc[2] = houseId - 322568;
        }
        else if (houseId >= 524289 && houseId <= 524294) {
            loc = getHouseLoc(houseId);
        }
        return loc;
    }
    
    public static int[] getHouseTeleportLoc(int houseId, int number) {
        int[] loc = new int[3];
        if (houseId >= 262145 && houseId <= 262189) {
            loc[0] = L1HouseLocation.TELEPORT_LOC_GIRAN[number].getX();
            loc[1] = L1HouseLocation.TELEPORT_LOC_GIRAN[number].getY();
            loc[2] = L1HouseLocation.TELEPORT_LOC_MAPID[number];
        }
        else if (houseId >= 327681 && houseId <= 327691) {
            loc[0] = L1HouseLocation.TELEPORT_LOC_HEINE[number].getX();
            loc[1] = L1HouseLocation.TELEPORT_LOC_HEINE[number].getY();
            loc[2] = L1HouseLocation.TELEPORT_LOC_MAPID[number];
        }
        else if (houseId >= 458753 && houseId <= 458819) {
            loc[0] = L1HouseLocation.TELEPORT_LOC_ADEN[number].getX();
            loc[1] = L1HouseLocation.TELEPORT_LOC_ADEN[number].getY();
            loc[2] = L1HouseLocation.TELEPORT_LOC_MAPID[number];
        }
        else if (houseId >= 524289 && houseId <= 524294) {
            loc[0] = L1HouseLocation.TELEPORT_LOC_GLUDIN[number].getX();
            loc[1] = L1HouseLocation.TELEPORT_LOC_GLUDIN[number].getY();
            loc[2] = L1HouseLocation.TELEPORT_LOC_MAPID[number];
        }
        return loc;
    }
}
