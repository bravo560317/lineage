package com.lineage.server.world;

import com.lineage.server.templates.L1Npc;
import java.util.Random;
import java.util.List;
import com.lineage.server.datatables.DoorSpawnTable;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import java.util.Map;
import com.lineage.config.Config;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.Instance.L1DwarfInstance;
import com.lineage.server.model.Instance.L1MerchantInstance;
import java.util.ArrayList;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1GroundInventory;
import java.util.Collections;
import com.lineage.server.model.Instance.L1CrockInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1BowInstance;
import com.lineage.server.model.Instance.L1HierarchInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1TrapInstance;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Iterator;
import com.lineage.server.utils.collections.CTRAssignLock;
import com.lineage.server.datatables.MapsTable;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import java.util.HashMap;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class World
{
    private static final Log _log;
    private final ConcurrentHashMap<String, L1PcInstance> _allPlayers;
    private final ConcurrentHashMap<Integer, L1Object> _allObjects;
    private final HashMap<Integer, ConcurrentHashMap<Integer, L1Object>> _visibleObjects;
    private static World _instance;
    private Collection<L1Object> _allValues;
    private Collection<L1PcInstance> _allPlayerValues;
    private int _weather;
    private boolean _worldChatEnabled;
    private boolean _processingContributionTotal;
    private static final int MAX_MAP_ID = 12000;
    
    static {
        _log = LogFactory.getLog(World.class);
    }
    
    public World() {
        this._weather = 4;
        this._worldChatEnabled = true;
        this._processingContributionTotal = false;
        this._allPlayers = new ConcurrentHashMap();
        this._allObjects = new ConcurrentHashMap();
        this._visibleObjects = new HashMap();
        for (Integer mapid : MapsTable.get().getMaps().keySet()) {
            ConcurrentHashMap<Integer, L1Object> map = new ConcurrentHashMap();
            this._visibleObjects.put(mapid, map);
            CTRAssignLock.registerGrdIvnLock(mapid);
        }
        World._log.info("遊戲世界儲存中心建立完成!!!");
    }
    
    public static World get() {
        if (World._instance == null) {
            World._instance = new World();
        }
        return World._instance;
    }
    
    public Object getRegion(Object object) {
        return null;
    }
    
    public void clear() {
        World._instance = new World();
    }
    
    public void storeObject(L1Object object) {
        try {
            if (object == null) {
                throw new NullPointerException();
            }
            else {
                this._allObjects.put(object.getId(), object);
                if (object instanceof L1ItemInstance) {
                    WorldItem.get().put(new Integer(object.getId()), (L1ItemInstance)object);
                }
                if (object instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)object;
                    if (pc.isCrown()) {
                        WorldCrown.get().put(new Integer(pc.getId()), pc);
                    }
                    else if (pc.isKnight()) {
                        WorldKnight.get().put(new Integer(pc.getId()), pc);
                    }
                    else if (pc.isElf()) {
                        WorldElf.get().put(new Integer(pc.getId()), pc);
                    }
                    else if (pc.isWizard()) {
                        WorldWizard.get().put(new Integer(pc.getId()), pc);
                    }
                    else if (pc.isDarkelf()) {
                        WorldDarkelf.get().put(new Integer(pc.getId()), pc);
                    }
                    else if (pc.isDragonKnight()) {
                        WorldDragonKnight.get().put(new Integer(pc.getId()), pc);
                    }
                    else if (pc.isIllusionist()) {
                        WorldIllusionist.get().put(new Integer(pc.getId()), pc);
                    }
                    this._allPlayers.put(pc.getName(), pc);
                }
                if (object instanceof L1DeInstance) {
                    L1DeInstance de = (L1DeInstance)object;
                    WorldDe.get().put(de.getNameId(), de);
                }
                if (object instanceof L1TrapInstance) {
                    WorldTrap.get().put(new Integer(object.getId()), (L1TrapInstance)object);
                }
                if (object instanceof L1PetInstance) {
                    WorldPet.get().put(new Integer(object.getId()), (L1PetInstance)object);
                }
                if (object instanceof L1SummonInstance) {
                    WorldSummons.get().put(new Integer(object.getId()), (L1SummonInstance)object);
                }
                if (object instanceof L1DollInstance) {
                    WorldDoll.get().put(new Integer(object.getId()), (L1DollInstance)object);
                }
                if (object instanceof L1EffectInstance) {
                    WorldEffect.get().put(new Integer(object.getId()), (L1EffectInstance)object);
                }
                if (object instanceof L1MonsterInstance) {
                    WorldMob.get().put(new Integer(object.getId()), (L1MonsterInstance)object);
                }
                if (object instanceof L1HierarchInstance) {
                    WorldHierarch.get().put(new Integer(object.getId()), (L1HierarchInstance)object);
                }
                if (object instanceof L1BowInstance) {
                    WorldBow.get().put(new Integer(object.getId()), (L1BowInstance)object);
                }
                if (object instanceof L1NpcInstance) {
                    WorldNpc.get().put(new Integer(object.getId()), (L1NpcInstance)object);
                }
                if (object instanceof L1CrockInstance) {
                    WorldCrock.get().put(new Integer(object.getId()), (L1CrockInstance)object);
                }
            }
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void removeObject(L1Object object) {
        try {
            if (object == null) {
                throw new NullPointerException();
            }
            else {
                this._allObjects.remove((Integer)object.getId());
                if (object instanceof L1ItemInstance) {
                    WorldItem.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)object;
                    if (pc.isCrown()) {
                        WorldCrown.get().remove(new Integer(pc.getId()));
                    }
                    else if (pc.isKnight()) {
                        WorldKnight.get().remove(new Integer(pc.getId()));
                    }
                    else if (pc.isElf()) {
                        WorldElf.get().remove(new Integer(pc.getId()));
                    }
                    else if (pc.isWizard()) {
                        WorldWizard.get().remove(new Integer(pc.getId()));
                    }
                    else if (pc.isDarkelf()) {
                        WorldDarkelf.get().remove(new Integer(pc.getId()));
                    }
                    else if (pc.isDragonKnight()) {
                        WorldDragonKnight.get().remove(new Integer(pc.getId()));
                    }
                    else if (pc.isIllusionist()) {
                        WorldIllusionist.get().remove(new Integer(pc.getId()));
                    }
                    this._allPlayers.remove(pc.getName());
                }
                if (object instanceof L1DeInstance) {
                    L1DeInstance de = (L1DeInstance)object;
                    WorldDe.get().remove(de.getNameId());
                }
                if (object instanceof L1TrapInstance) {
                    WorldTrap.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1PetInstance) {
                    WorldPet.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1SummonInstance) {
                    WorldSummons.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1DollInstance) {
                    WorldDoll.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1EffectInstance) {
                    WorldEffect.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1MonsterInstance) {
                    WorldMob.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1BowInstance) {
                    WorldBow.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1NpcInstance) {
                    WorldNpc.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1HierarchInstance) {
                    WorldHierarch.get().remove(new Integer(object.getId()));
                }
                if (object instanceof L1CrockInstance) {
                    WorldCrock.get().remove(new Integer(object.getId()));
                }
            }
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public L1Object findObject(int oID) {
        if (oID == 0) {
            return null;
        }
        else {
            return this._allObjects.get((Integer)oID);
        }
    }
    
    public Collection<L1Object> getObject() {
        try {
            Collection<L1Object> vs = this._allValues;
            return (vs != null) ? vs : (this._allValues = (Collection)Collections.unmodifiableCollection(this._allObjects.values()));
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public L1GroundInventory getInventory(int x, int y, short map) {
        try {
            int inventoryKey = ((x - 30000) * 10000 + (y - 30000)) * -1;
            ConcurrentHashMap<Integer, L1Object> idmap = this._visibleObjects.get(new Integer((int)map));
            if (idmap != null) {
                Object object = idmap.get((Integer)inventoryKey);
                if (object == null) {
                    return new L1GroundInventory(inventoryKey, x, y, map);
                }
                else {
                    return (L1GroundInventory)object;
                }
            }
            else {
                World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + (int)map);
            }
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    public L1GroundInventory getInventory(L1Location loc) {
        return this.getInventory(loc.getX(), loc.getY(), (short)loc.getMap().getId());
    }
    
    public void addVisibleObject(L1Object object) {
        try {
            ConcurrentHashMap<Integer, L1Object> map = this._visibleObjects.get(new Integer((int)object.getMapId()));
            if (map != null) {
                map.put(object.getId(), object);
            }
            else {
                World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + (int)object.getMapId());
            }
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void removeVisibleObject(L1Object object) {
        try {
            ConcurrentHashMap<Integer, L1Object> map = this._visibleObjects.get(new Integer((int)object.getMapId()));
            if (map != null) {
                map.remove((Integer)object.getId());
            }
            else {
                World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + (int)object.getMapId());
            }
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void moveVisibleObject(L1Object object, int newMapId) {
        try {
            int srcMapId = object.getMapId();
            if (srcMapId != newMapId) {
                ConcurrentHashMap<Integer, L1Object> mapSrc = this._visibleObjects.get(new Integer(srcMapId));
                if (mapSrc != null) {
                    mapSrc.remove((Integer)object.getId());
                }
                else {
                    World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + srcMapId);
                }
                ConcurrentHashMap<Integer, L1Object> map = this._visibleObjects.get(new Integer(newMapId));
                if (map != null) {
                    map.put(object.getId(), object);
                }
                else {
                    World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + newMapId);
                }
            }
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private HashMap<Integer, Integer> createLineMap(Point src, Point target) {
        HashMap<Integer, Integer> lineMap = new HashMap();
        int x0 = src.getX();
        int y0 = src.getY();
        int x2 = target.getX();
        int y2 = target.getY();
        int sx = (x2 > x0) ? 1 : -1;
        int dx = (x2 > x0) ? (x2 - x0) : (x0 - x2);
        int sy = (y2 > y0) ? 1 : -1;
        int dy = (y2 > y0) ? (y2 - y0) : (y0 - y2);
        int x3 = x0;
        int y3 = y0;
        if (dx >= dy) {
            int E = -dx;
            for (int i = 0; i <= dx; ++i) {
                int key = (x3 << 16) + y3;
                lineMap.put(key, key);
                x3 += sx;
                E += dy << 1;
                if (E >= 0) {
                    y3 += sy;
                    E -= dx << 1;
                }
            }
        }
        else {
            int E = -dy;
            for (int i = 0; i <= dy; ++i) {
                int key = (x3 << 16) + y3;
                lineMap.put(key, key);
                y3 += sy;
                E += dx << 1;
                if (E >= 0) {
                    x3 += sx;
                    E -= dy << 1;
                }
            }
        }
        return lineMap;
    }
    
    public ArrayList<L1Object> getVisibleLineObjects(L1Object src, L1Object target) {
        try {
            HashMap<Integer, Integer> lineMap = this.createLineMap(src.getLocation(), target.getLocation());
            int map = target.getMapId();
            ArrayList<L1Object> result = new ArrayList();
            ConcurrentHashMap<Integer, L1Object> mapSrc = this._visibleObjects.get(new Integer(map));
            if (mapSrc == null) {
                World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + map);
                return result;
            }
            else {
                for (L1Object element : mapSrc.values()) {
                    if (element.equals(src)) {
                        continue;
                    }
                    else if (src.get_showId() != element.get_showId()) {
                        continue;
                    }
                    else {
                        int key = (element.getX() << 16) + element.getY();
                        if (lineMap.containsKey((Integer)key)) {
                            result.add(element);
                        }
                        else {
                            continue;
                        }
                    }
                }
                lineMap.clear();
                return result;
            }
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ArrayList<L1Object> getVisibleBoxObjects(L1Object object, int heading, int width, int height) {
        int x = object.getX();
        int y = object.getY();
        int map = object.getMapId();
        L1Location location = object.getLocation();
        ArrayList<L1Object> result = new ArrayList();
        int[] headingRotate = { 6, 7, 0, 1, 2, 3, 4, 5 };
        double cosSita = Math.cos((double)headingRotate[heading] * 3.141592653589793 / 4.0);
        double sinSita = Math.sin((double)headingRotate[heading] * 3.141592653589793 / 4.0);
        ConcurrentHashMap<Integer, L1Object> mapSrc = this._visibleObjects.get(new Integer(map));
        if (mapSrc == null) {
            World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + map);
            return result;
        }
        else {
            for (L1Object element : mapSrc.values()) {
                if (element.equals(object)) {
                    continue;
                }
                else if (object.get_showId() != element.get_showId()) {
                    continue;
                }
                else if (map != element.getMapId()) {
                    continue;
                }
                else if (location.isSamePoint(element.getLocation())) {
                    result.add(element);
                }
                else {
                    int distance = location.getTileLineDistance(element.getLocation());
                    if (distance > height && distance > width) {
                        continue;
                    }
                    else {
                        int x2 = element.getX() - x;
                        int y2 = element.getY() - y;
                        int rotX = (int)Math.round((double)x2 * cosSita + (double)y2 * sinSita);
                        int rotY = (int)Math.round((double)(-x2) * sinSita + (double)y2 * cosSita);
                        int xmin = 0;
                        int xmax = height;
                        int ymin = -width;
                        int ymax = width;
                        if (rotX > 0 && distance <= xmax && rotY >= ymin && rotY <= ymax) {
                            result.add(element);
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
    
    public ArrayList<L1Object> getVisibleObjects(L1Object object) {
        return this.getVisibleObjects(object, -1);
    }
    
    public boolean getVisibleObjects(L1Object src, L1Object tg) {
        return src.get_showId() == tg.get_showId() && src.getMapId() == tg.getMapId() && src.getLocation().isInScreen(tg.getLocation());
    }
    
    public ArrayList<L1Object> getVisibleObjects(L1Object object, int radius) {
        L1Map map = object.getMap();
        Point pt = object.getLocation();
        ArrayList<L1Object> result = new ArrayList();
        ConcurrentHashMap<Integer, L1Object> mapSrc = this._visibleObjects.get(new Integer(map.getId()));
        if (mapSrc == null) {
            World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + map.getId());
            return result;
        }
        else {
            for (L1Object element : mapSrc.values()) {
                if (element.equals(object)) {
                    continue;
                }
                else {
                    if (object.get_showId() != element.get_showId()) {
                        boolean is = false;
                        if (element instanceof L1MerchantInstance) {
                            is = true;
                        }
                        if (element instanceof L1DwarfInstance) {
                            is = true;
                        }
                        if (!is) {
                            continue;
                        }
                    }
                    if (map != element.getMap()) {
                        continue;
                    }
                    else {
                        switch (radius) {
                            case -1: {
                                if (pt.isInScreen(element.getLocation())) {
                                    result.add(element);
                                    continue;
                                }
                                else {
                                    continue;
                                }
                            }
                            case 0: {
                                if (pt.isSamePoint(element.getLocation())) {
                                    result.add(element);
                                    continue;
                                }
                                else {
                                    continue;
                                }
                            }
                            default: {
                                int r = pt.getTileLineDistance(element.getLocation());
                                if (r <= radius) {
                                    result.add(element);
                                    continue;
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }
    }
    
    public ArrayList<L1Object> getVisiblePoint(L1Location loc, int radius, int showid) {
        ArrayList<L1Object> result = new ArrayList();
        int mapId = loc.getMapId();
        ConcurrentHashMap<Integer, L1Object> mapSrc = this._visibleObjects.get(new Integer(mapId));
        if (mapSrc == null) {
            World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + mapId);
            return result;
        }
        else {
            for (L1Object element : mapSrc.values()) {
                if (mapId != element.getMapId()) {
                    continue;
                }
                else if (showid != element.get_showId()) {
                    continue;
                }
                else if (loc.getTileLineDistance(element.getLocation()) <= radius) {
                    result.add(element);
                }
                else {
                    continue;
                }
            }
            return result;
        }
    }
    
    public ArrayList<L1PcInstance> getVisiblePc(L1Location loc) {
        ArrayList<L1PcInstance> result = new ArrayList();
        int mapId = loc.getMapId();
        ConcurrentHashMap<Integer, L1Object> mapSrc = this._visibleObjects.get(new Integer(mapId));
        if (mapSrc == null) {
            World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + mapId);
            return result;
        }
        else {
            for (L1PcInstance element : this._allPlayers.values()) {
                if (mapId != element.getMapId()) {
                    continue;
                }
                else if (loc.isInScreen(element.getLocation())) {
                    result.add(element);
                }
                else {
                    continue;
                }
            }
            return result;
        }
    }
    
    public ArrayList<L1PcInstance> getVisiblePlayer(L1Object object) {
        return this.getVisiblePlayer(object, -1);
    }
    
    public ArrayList<L1PcInstance> getVisiblePlayer(L1Object object, int radius) {
        int map = object.getMapId();
        Point pt = object.getLocation();
        ArrayList<L1PcInstance> result = new ArrayList();
        for (L1PcInstance element : this._allPlayers.values()) {
            if (element.equals(object)) {
                continue;
            }
            else if (map != element.getMapId()) {
                continue;
            }
            else if (object.get_showId() != element.get_showId()) {
                continue;
            }
            else {
                switch (radius) {
                    case -1: {
                        if (pt.isInScreen(element.getLocation())) {
                            result.add(element);
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    case 0: {
                        if (pt.isSamePoint(element.getLocation())) {
                            result.add(element);
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    default: {
                        if (pt.getTileLineDistance(element.getLocation()) <= radius) {
                            result.add(element);
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        return result;
    }
    
    public ArrayList<L1PcInstance> getVisiblePlayerExceptTargetSight(L1Object object, L1Object target) {
        int map = object.getMapId();
        Point objectPt = object.getLocation();
        Point targetPt = target.getLocation();
        ArrayList<L1PcInstance> result = new ArrayList();
        for (L1PcInstance element : this._allPlayers.values()) {
            if (element.equals(object)) {
                continue;
            }
            else if (map != element.getMapId()) {
                continue;
            }
            else if (target.get_showId() != element.get_showId()) {
                continue;
            }
            else if (Config.PC_RECOGNIZE_RANGE == -1) {
                if (objectPt.isInScreen(element.getLocation()) && !targetPt.isInScreen(element.getLocation())) {
                    result.add(element);
                }
                else {
                    continue;
                }
            }
            else if (objectPt.getTileLineDistance(element.getLocation()) <= Config.PC_RECOGNIZE_RANGE && targetPt.getTileLineDistance(element.getLocation()) > Config.PC_RECOGNIZE_RANGE) {
                result.add(element);
            }
            else {
                continue;
            }
        }
        return result;
    }
    
    public ArrayList<L1PcInstance> getVisiblePlayerExceptTargetSight(L1Object object, L1Object target, int radius) {
        int map = object.getMapId();
        Point objectPt = object.getLocation();
        Point targetPt = target.getLocation();
        ArrayList<L1PcInstance> result = new ArrayList();
        for (L1PcInstance element : this._allPlayers.values()) {
            if (element.equals(object)) {
                continue;
            }
            else if (map != element.getMapId()) {
                continue;
            }
            else if (target.get_showId() != element.get_showId()) {
                continue;
            }
            else if (objectPt.getTileLineDistance(element.getLocation()) <= radius && targetPt.getTileLineDistance(element.getLocation()) <= radius) {
                result.add(element);
            }
            else {
                continue;
            }
        }
        return result;
    }
    
    public ArrayList<L1PcInstance> getRecognizePlayer(L1Object object) {
        return this.getVisiblePlayer(object, Config.PC_RECOGNIZE_RANGE);
    }
    
    public Collection<L1PcInstance> getAllPlayers() {
        try {
            Collection<L1PcInstance> vs = this._allPlayerValues;
            return (vs != null) ? vs : (this._allPlayerValues = (Collection)Collections.unmodifiableCollection(this._allPlayers.values()));
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public L1PcInstance getPlayer(String name) {
        if (this._allPlayers.contains(name)) {
            return this._allPlayers.get(name);
        }
        else {
            for (L1PcInstance each : this.getAllPlayers()) {
                if (each.getName().equalsIgnoreCase(name)) {
                    return each;
                }
            }
            return null;
        }
    }
    
    public boolean get_pc(L1PcInstance pc, String name) {
        L1PcInstance tg = this._allPlayers.get(name);
        return tg != null && pc.getLocation().isInScreen(tg.getLocation());
    }
    
    public final Map<Integer, L1Object> getAllVisibleObjects() {
        return (Map)this._allObjects;
    }
    
    public final HashMap<Integer, ConcurrentHashMap<Integer, L1Object>> getVisibleObjects() {
        return this._visibleObjects;
    }
    
    public final ConcurrentHashMap<Integer, L1Object> getVisibleObjects(int mapId) {
        return this._visibleObjects.get(new Integer(mapId));
    }
    
    public void setWeather(int weather) {
        this._weather = weather;
    }
    
    public int getWeather() {
        return this._weather;
    }
    
    public void set_worldChatElabled(boolean flag) {
        this._worldChatEnabled = flag;
    }
    
    public boolean isWorldChatElabled() {
        return this._worldChatEnabled;
    }
    
    public void setProcessingContributionTotal(boolean flag) {
        this._processingContributionTotal = flag;
    }
    
    public boolean isProcessingContributionTotal() {
        return this._processingContributionTotal;
    }
    
    public void broadcastPacketToAll(ServerBasePacket packet) {
        for (L1PcInstance tgpc : this.getAllPlayers()) {
            if (check(tgpc)) {
                tgpc.sendPackets(packet);
            }
        }
    }
    
    private static boolean check(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public void broadcastServerMessage(String message) {
        this.broadcastPacketToAll(new S_SystemMessage(message));
    }
    
    public ArrayList<L1Object> getVisiblePoint(L1Location loc, int radius) {
        ArrayList<L1Object> result = new ArrayList();
        int mapId = loc.getMapId();
        ConcurrentHashMap<Integer, L1Object> mapSrc = this._visibleObjects.get(new Integer(mapId));
        if (mapSrc == null) {
            World._log.error("遊戲世界儲存中心並未建立該地圖編號資料檔案: " + mapId);
            return result;
        }
        else {
            if (mapId <= 12000) {
                for (L1Object element : mapSrc.values()) {
                    if (mapId != element.getMapId()) {
                        continue;
                    }
                    else if (loc.getTileLineDistance(element.getLocation()) <= radius) {
                        result.add(element);
                    }
                    else {
                        continue;
                    }
                }
            }
            return result;
        }
    }
    
    public void closeMap(int mapid) {
        for (Map.Entry<Integer, L1Object> entry : this._allObjects.entrySet()) {
            L1Object object = entry.getValue();
            if (object.getMapId() == mapid) {
                if (object instanceof L1DoorInstance) {
                    DoorSpawnTable.get().removeDoor((L1DoorInstance)object);
                }
                this.removeObject(object);
            }
        }
        ConcurrentHashMap<Integer, L1Object> map = new ConcurrentHashMap();
        this._visibleObjects.put(mapid, map);
    }
    
    public void broadcastPacketToAlldroplist(ServerBasePacket packet) {
        for (L1PcInstance pc : this.getAllPlayers()) {
            pc.sendPackets(packet);
        }
    }
    
    public List<L1PcInstance> getRandomPlayers(int count) {
        try {
            Collection<L1PcInstance> tempList = this.getAllPlayers();
            if (!tempList.isEmpty()) {
                L1PcInstance[] userList = (L1PcInstance[])tempList.toArray(new L1PcInstance[tempList.size()]);
                List<L1PcInstance> newList = new ArrayList();
                Random _random = new Random();
                while (newList.size() < count) {
                    L1PcInstance each_pc = userList[_random.nextInt(userList.length)];
                    if (each_pc != null && !newList.contains(each_pc)) {
                        newList.add(each_pc);
                    }
                }
                return newList;
            }
        }
        catch (Exception e) {
            World._log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    public void addMap(int mapid) {
        ConcurrentHashMap<Integer, L1Object> map = new ConcurrentHashMap();
        this._visibleObjects.put(mapid, map);
    }
    
    public L1PcInstance[] getAllPlayersToArray() {
        return (L1PcInstance[])this._allPlayers.values().toArray(new L1PcInstance[this._allPlayers.size()]);
    }
    
    public L1PcInstance getPlayer(int objid) {
        for (L1PcInstance each : this.getAllPlayers()) {
            if (each.getId() == objid) {
                return each;
            }
        }
        return null;
    }
    
    public int getObjId(L1Npc npc) {
        for (L1Object obj : this._allObjects.values()) {
            if (obj instanceof L1NpcInstance && ((L1NpcInstance)obj).getNpcTemplate().get_npcId() == npc.get_npcId()) {
                return obj.getId();
            }
        }
        return 0;
    }
}
