package com.lineage.server.utils.collections;

import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.locks.Lock;
import java.util.Map;
import org.apache.commons.logging.Log;

public class CTRAssignLock
{
    private static final Log LOG;
    private static final Map<Integer, Lock> GRDINVLOCKMAP;
    
    static {
        LOG = LogFactory.getLog(CTRAssignLock.class);
        GRDINVLOCKMAP = UMapFactory.newHashMap();
    }
    
    public static void registerGrdIvnLock(Integer mapId) {
        CTRAssignLock.GRDINVLOCKMAP.put(mapId, new ReentrantLock(true));
    }
    
    public static Lock getGrdIvnLock(int mapId) {
        return CTRAssignLock.GRDINVLOCKMAP.get(new Integer(mapId));
    }
    
    public static void removeGrdIvnLock(Integer mapId) {
        CTRAssignLock.GRDINVLOCKMAP.remove(mapId);
    }
}
