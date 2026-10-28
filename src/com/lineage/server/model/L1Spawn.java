package com.lineage.server.model;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1MonsterEnhanceInstance;
import java.util.Iterator;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.Instance.L1BowInstance;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.model.Instance.L1FurnitureInstance;
import com.lineage.server.model.Instance.L1FieldObjectInstance;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1HierarchInstance;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.MonsterEnhanceTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.datatables.NpcTable;
import java.util.HashMap;

import com.eric.gui.J_Main;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.gametime.L1GameTime;
import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.timecontroller.npc.NpcSpawnBossTimer;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.List;
import com.lineage.server.types.Point;
import java.util.Map;
import java.util.Calendar;
import com.lineage.server.templates.L1SpawnTime;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.Log;
import com.lineage.server.model.gametime.L1GameTimeAdapter;

public class L1Spawn extends L1GameTimeAdapter
{
    private static final Log _log;
    private final L1Npc _template;
    private int _id;
    private String _location;
    private int _maximumCount;
    private int _npcid;
    private int _groupId;
    private int _locx;
    private int _locy;
    private int _tmplocx;
    private int _tmplocy;
    private short _tmpmapid;
    private int _randomx;
    private int _randomy;
    private int _locx1;
    private int _locy1;
    private int _locx2;
    private int _locy2;
    private int _heading;
    private int _minRespawnDelay;
    private int _maxRespawnDelay;
    private short _mapid;
    private boolean _respaenScreen;
    private int _movementDistance;
    private boolean _rest;
    private int _spawnType;
    private int _delayInterval;
    private L1SpawnTime _time;
    private Calendar _nextSpawnTime;
    private long _spawnInterval;
    private int _existTime;
    private Map<Integer, Point> _homePoint;
    private List<L1NpcInstance> _mobs;
    private Random _random;
    private String _name;
    private boolean _initSpawn;
    private boolean _spawnHomePoint;
    private static final int SPAWN_TYPE_PC_AROUND = 1;
    private L1NpcInstance npcTemp;
    private long deleteTime;
    private boolean _isBroadcast;
    private String _broadcastInfo;
    private String _msg1;
    private String _msg2;
    
    static {
        _log = LogFactory.getLog(L1Spawn.class);
    }
    
    public L1Spawn(L1Npc mobTemplate) {
        this._nextSpawnTime = null;
        this._spawnInterval = 0L;
        this._existTime = 0;
        this._homePoint = null;
        this._mobs = new ArrayList();
        this._random = new Random();
        this._initSpawn = false;
        this._template = mobTemplate;
    }
    
    public L1Npc getNpcTemplate() {
        return this._template;
    }
    
    public String getName() {
        return this._name;
    }
    
    public void setName(String name) {
        this._name = name;
    }
    
    public short getMapId() {
        return this._mapid;
    }
    
    public void setMapId(short _mapid) {
        this._mapid = _mapid;
    }
    
    public boolean isRespawnScreen() {
        return this._respaenScreen;
    }
    
    public void setRespawnScreen(boolean flag) {
        this._respaenScreen = flag;
    }
    
    public int getMovementDistance() {
        return this._movementDistance;
    }
    
    public void setMovementDistance(int i) {
        this._movementDistance = i;
    }
    
    public int getAmount() {
        return this._maximumCount;
    }
    
    public int getGroupId() {
        return this._groupId;
    }
    
    public int getId() {
        return this._id;
    }
    
    public String getLocation() {
        return this._location;
    }
    
    public int getLocX() {
        return this._locx;
    }
    
    public int getLocY() {
        return this._locy;
    }
    
    public int getNpcId() {
        return this._npcid;
    }
    
    public int getHeading() {
        return this._heading;
    }
    
    public int getRandomx() {
        return this._randomx;
    }
    
    public int getRandomy() {
        return this._randomy;
    }
    
    public int getLocX1() {
        return this._locx1;
    }
    
    public int getLocY1() {
        return this._locy1;
    }
    
    public int getLocX2() {
        return this._locx2;
    }
    
    public int getLocY2() {
        return this._locy2;
    }
    
    public int getMinRespawnDelay() {
        return this._minRespawnDelay;
    }
    
    public int getMaxRespawnDelay() {
        return this._maxRespawnDelay;
    }
    
    public void setAmount(int amount) {
        this._maximumCount = amount;
    }
    
    public void setId(int id) {
        this._id = id;
    }
    
    public void setGroupId(int i) {
        this._groupId = i;
    }
    
    public void setLocation(String location) {
        this._location = location;
    }
    
    public void setLocX(int locx) {
        this._locx = locx;
    }
    
    public void setLocY(int locy) {
        this._locy = locy;
    }
    
    public void setNpcid(int npcid) {
        this._npcid = npcid;
    }
    
    public void setHeading(int heading) {
        this._heading = heading;
    }
    
    public void setRandomx(int randomx) {
        this._randomx = randomx;
    }
    
    public void setRandomy(int randomy) {
        this._randomy = randomy;
    }
    
    public void setLocX1(int locx1) {
        this._locx1 = locx1;
    }
    
    public void setLocY1(int locy1) {
        this._locy1 = locy1;
    }
    
    public void setLocX2(int locx2) {
        this._locx2 = locx2;
    }
    
    public void setLocY2(int locy2) {
        this._locy2 = locy2;
    }
    
    public void setMinRespawnDelay(int i) {
        this._minRespawnDelay = i;
    }
    
    public void setMaxRespawnDelay(int i) {
        this._maxRespawnDelay = i;
    }
    
    public int getTmpLocX() {
        return this._tmplocx;
    }
    
    public int getTmpLocY() {
        return this._tmplocy;
    }
    
    public short getTmpMapid() {
        return this._tmpmapid;
    }
    
    private boolean isSpawnTime(L1NpcInstance npcTemp) {
        if (this._nextSpawnTime != null) {
            Calendar cals = Calendar.getInstance();
            long nowTime = System.currentTimeMillis();
            cals.setTimeInMillis(nowTime);
            if (cals.after(this._nextSpawnTime)) {
                return true;
            }
            else {
                if (NpcSpawnBossTimer.MAP.get(npcTemp) == null) {
                    long spawnTime = this._nextSpawnTime.getTimeInMillis();
                    long spa = (spawnTime - nowTime) / 1000L + 5L;
                    NpcSpawnBossTimer.MAP.put(npcTemp, spa);
                }
                return false;
            }
        }
        else {
            return true;
        }
    }
    
    public Calendar get_nextSpawnTime() {
        return this._nextSpawnTime;
    }
    
    public void set_nextSpawnTime(Calendar next_spawn_time) {
        this._nextSpawnTime = next_spawn_time;
    }
    
    public void set_spawnInterval(long spawn_interval) {
        this._spawnInterval = spawn_interval;
    }
    
    public long get_spawnInterval() {
        return this._spawnInterval;
    }
    
    public void set_existTime(int exist_time) {
        this._existTime = exist_time;
    }
    
    public int get_existTime() {
        return this._existTime;
    }
    
    private int calcRespawnDelay() {
        int respawnDelay = this._minRespawnDelay * 1000;
        if (this._delayInterval > 0) {
            respawnDelay += this._random.nextInt(this._delayInterval) * 1000;
        }
        L1GameTime currentTime = L1GameTimeClock.getInstance().currentTime();
        if (this._time != null && !this._time.getTimePeriod().includes(currentTime)) {
            long diff = this._time.getTimeStart().getTime() - currentTime.toTime().getTime();
            if (diff < 0L) {
                diff += 86400000L;
            }
            diff /= 6L;
            respawnDelay = (int)diff;
        }
        return respawnDelay;
    }
    
    public void executeSpawnTask(int spawnNumber, int objectId) {
        if (this._nextSpawnTime != null) {
            this.doSpawn(spawnNumber, objectId);
        }
        else {
            L1Spawn.SpawnTask task = new L1Spawn.SpawnTask(spawnNumber, objectId, (long)this.calcRespawnDelay());
            task.getStart();
        }
    }
    
    public void init() {
        if (this._time != null && this._time.isDeleteAtEndTime()) {
            L1GameTimeClock.getInstance().addListener(this);
        }
        this._delayInterval = this._maxRespawnDelay - this._minRespawnDelay;
        this._initSpawn = true;
        if (ConfigAlt.SPAWN_HOME_POINT && ConfigAlt.SPAWN_HOME_POINT_COUNT <= this.getAmount() && ConfigAlt.SPAWN_HOME_POINT_DELAY >= this.getMinRespawnDelay() && this.isAreaSpawn()) {
            this._spawnHomePoint = true;
            this._homePoint = new HashMap();
        }
        int spawnNum = 0;
        while (spawnNum < this._maximumCount) {
            this.doSpawn(++spawnNum);
        }
        this._initSpawn = false;
    }
    
    protected void doSpawn(int spawnNumber) {
        if (this._time != null && !this._time.getTimePeriod().includes(L1GameTimeClock.getInstance().currentTime())) {
            this.executeSpawnTask(spawnNumber, 0);
            return;
        }
        else {
            this.doSpawn(spawnNumber, 0);
            return;
        }
    }
    
    protected void doSpawn(int spawnNumber, int objectId) {
        this._tmplocx = 0;
        this._tmplocy = 0;
        this._tmpmapid = 0;
        L1NpcInstance npcTemp = null;
        try {
            int newlocx = this.getLocX();
            int newlocy = this.getLocY();
            int tryCount = 0;
            npcTemp = NpcTable.get().newNpcInstance(this._template);
            synchronized (this._mobs) {
                this._mobs.add(npcTemp);
            }
            if (objectId == 0) {
                npcTemp.setId(IdFactoryNpc.get().nextId());
            }
            else {
                npcTemp.setId(objectId);
            }
            if (this.getHeading() >= 0 && this.getHeading() <= 7) {
                npcTemp.setHeading(this.getHeading());
            }
            else {
                npcTemp.setHeading(5);
            }
            int npcId = npcTemp.getNpcTemplate().get_npcId();
            if (npcId == 45488 && this.getMapId() == 9) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(2)));
            }
            else if (npcId == 45601 && this.getMapId() == 11) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(3)));
            }
            else if (npcId == 45649 && this.getMapId() == 80) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(3)));
            }
            else if (npcId == 105040 && this.getMapId() == 78) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(5)));
            }
            else if (npcId == 105070 && this.getMapId() == 80) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(3)));
            }
            else {
                npcTemp.setMap(this.getMapId());
            }
            npcTemp.setMovementDistance(this.getMovementDistance());
            npcTemp.setRest(this.isRest());
            while (tryCount <= 50) {
                if (this.isAreaSpawn()) {
                    Point pt = null;
                    if (this._spawnHomePoint && (pt = this._homePoint.get((Integer)spawnNumber)) != null) {
                        L1Location loc = new L1Location(pt, (int)this.getMapId()).randomLocation(ConfigAlt.SPAWN_HOME_POINT_RANGE, false);
                        newlocx = loc.getX();
                        newlocy = loc.getY();
                    }
                    else {
                        int rangeX = this.getLocX2() - this.getLocX1();
                        int rangeY = this.getLocY2() - this.getLocY1();
                        newlocx = this._random.nextInt(rangeX) + this.getLocX1();
                        newlocy = this._random.nextInt(rangeY) + this.getLocY1();
                    }
                    if (tryCount > 49) {
                        if (this._nextSpawnTime == null) {
                            newlocx = this.getLocX();
                            newlocy = this.getLocY();
                        }
                        else {
                            L1Spawn.SpawnTask task = new L1Spawn.SpawnTask(spawnNumber, npcTemp.getId(), 5000L);
                            task.getStart();
                            return;
                        }
                    }
                }
                else if (this.isRandomSpawn()) {
                    newlocx = this.getLocX() + ((int)(Math.random() * (double)this.getRandomx()) - (int)(Math.random() * (double)this.getRandomx()));
                    newlocy = this.getLocY() + ((int)(Math.random() * (double)this.getRandomy()) - (int)(Math.random() * (double)this.getRandomy()));
                }
                else {
                    newlocx = this.getLocX();
                    newlocy = this.getLocY();
                }
                if (this.getSpawnType() == 1) {
                    L1Location loc2 = new L1Location(newlocx, newlocy, (int)this.getMapId());
                    ArrayList<L1PcInstance> pcs = World.get().getVisiblePc(loc2);
                    if (pcs.size() > 0) {
                        L1Location newloc = loc2.randomLocation(20, false);
                        newlocx = newloc.getX();
                        newlocy = newloc.getY();
                    }
                }
                npcTemp.setX(newlocx);
                npcTemp.setHomeX(newlocx);
                npcTemp.setY(newlocy);
                npcTemp.setHomeY(newlocy);
                if (this._nextSpawnTime == null) {
                    if (npcTemp.getMap().isInMap(npcTemp.getLocation()) && npcTemp.getMap().isPassable(npcTemp.getLocation(), npcTemp) && npcTemp instanceof L1MonsterInstance) {
                        if (this.isRespawnScreen()) {
                            break;
                        }
                        else {
                            ArrayList<L1PcInstance> pcs2 = World.get().getVisiblePc(npcTemp.getLocation());
                            if (pcs2.size() == 0) {
                                break;
                            }
                            else {
                                L1Spawn.SpawnTask task = new L1Spawn.SpawnTask(spawnNumber, npcTemp.getId(), 5000L);
                                task.getStart();
                                return;
                            }
                        }
                    }
                }
                else if (npcTemp.getMap().isPassable(npcTemp.getLocation(), npcTemp)) {
                    break;
                }
                ++tryCount;
            }
            if (npcTemp instanceof L1MonsterInstance) {
                ((L1MonsterInstance)npcTemp).initHide();
            }
            npcTemp.setSpawn(this);
            npcTemp.setreSpawn(true);
            npcTemp.setSpawnNumber(spawnNumber);
            if (this._initSpawn && this._spawnHomePoint) {
                Point pt = new Point(npcTemp.getX(), npcTemp.getY());
                this._homePoint.put(spawnNumber, pt);
            }
            if (this._nextSpawnTime != null && !this.isSpawnTime(npcTemp)) {
                return;
            }
            else {
                if (npcTemp instanceof L1MonsterInstance) {
                    L1MonsterInstance mob = (L1MonsterInstance)npcTemp;
                    if (mob.getMapId() == 666) {
                        mob.set_storeDroped(true);
                    }
                }
                if (npcId == 45573 && npcTemp.getMapId() == 2) {
                    for (L1PcInstance pc : World.get().getAllPlayers()) {
                        if (pc.getMapId() == 2) {
                            L1Teleport.teleport(pc, 32664, 32797, (short)2, 0, true);
                        }
                    }
                }
                if ((npcId == 46142 && npcTemp.getMapId() == 73) || (npcId == 46141 && npcTemp.getMapId() == 74)) {
                    for (L1PcInstance pc : World.get().getAllPlayers()) {
                        if (pc.getMapId() >= 72 && pc.getMapId() <= 74) {
                            L1Teleport.teleport(pc, 32733, 32858, (short)74, pc.getHeading(), true);
                        }
                    }
                }

                doCrystalCave(npcId);
                World.get().storeObject(npcTemp);
                World.get().addVisibleObject(npcTemp);
                if (npcTemp instanceof L1MonsterInstance) {
                    L1MonsterInstance mobtemp = (L1MonsterInstance) npcTemp;
                    if (!this._initSpawn && mobtemp.getHiddenStatus() == 0) {
                        mobtemp.onNpcAI();
                    }
                }
                if (this.getGroupId() != 0) {
                    L1MobGroupSpawn.getInstance().doSpawn(npcTemp, this.getGroupId(), this.isRespawnScreen(), this._initSpawn);
                }
                npcTemp.turnOnOffLight();
                npcTemp.startChat(0);
                if (this.isBroadcast() && this.getBroadcastInfo() != null && !this.getBroadcastInfo().isEmpty()) {
                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format(this.getBroadcastInfo(), npcTemp.getName())));
                    J_Main.getInstance().addBossChatmsg((String.format(this.getBroadcastInfo(), npcTemp.getName())));
                }
                this._tmplocx = newlocx;
                this._tmplocy = newlocy;
                this._tmpmapid = npcTemp.getMapId();
                boolean setPassable = true;
                if (npcTemp instanceof L1DollInstance) {
                    setPassable = false;
                }
                if (npcTemp instanceof L1HierarchInstance) {
                    setPassable = false;
                }
                if (npcTemp instanceof L1EffectInstance) {
                    setPassable = false;
                }
                if (npcTemp instanceof L1FieldObjectInstance) {
                    setPassable = false;
                }
                if (npcTemp instanceof L1FurnitureInstance) {
                    setPassable = false;
                }
                if (npcTemp instanceof L1DoorInstance) {
                    setPassable = false;
                }
                if (npcTemp instanceof L1BowInstance) {
                    setPassable = false;
                }
                if (setPassable) {
                    L1WorldMap.get().getMap(npcTemp.getMapId()).setPassable(npcTemp.getX(), npcTemp.getY(), false, 2);
                }
            } // ← 這個是 try 的結束
        }
        catch (Exception e) {
            L1Spawn._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void setRest(boolean flag) {
        this._rest = flag;
    }
    
    public boolean isRest() {
        return this._rest;
    }
    
    private int getSpawnType() {
        return this._spawnType;
    }
    
    public void setSpawnType(int type) {
        this._spawnType = type;
    }
    
    private boolean isAreaSpawn() {
        return this.getLocX1() != 0 && this.getLocY1() != 0 && this.getLocX2() != 0 && this.getLocY2() != 0;
    }
    
    private boolean isRandomSpawn() {
        return this.getRandomx() != 0 || this.getRandomy() != 0;
    }
    
    public L1SpawnTime getTime() {
        return this._time;
    }
    
    public void setTime(L1SpawnTime time) {
        this._time = time;
    }
    
    @Override
    public void onMinuteChanged(L1GameTime time) {
        if (this._time.getTimePeriod().includes(time)) {
            return;
        }
        else {
            synchronized (this._mobs) {
                if (this._mobs.isEmpty()) {
                    return;
                }
                else {
                    for (L1NpcInstance mob : this._mobs) {
                        mob.setCurrentHpDirect(0);
                        mob.setDead(true);
                        mob.setStatus(8);
                        mob.deleteMe();
                    }
                    this._mobs.clear();
                }
            }
            return;
        }
    }
    
    public static void doCrystalCave(int npcId) {
        int[] npcId2 = { 46143, 46144, 46145, 46146, 46147, 46148, 46149, 46150, 46151, 46152 };
        int[] doorId = { 5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010 };
        for (int i = 0; i < npcId2.length; ++i) {
            if (npcId == npcId2[i]) {
                closeDoorInCrystalCave(doorId[i]);
            }
        }
    }
    
    public final L1NpcInstance getNpcTemp() {
        return this.npcTemp;
    }
    
    public final long getDeleteTime() {
        return this.deleteTime;
    }
    
    public final void setDeleteTime(long deleteTime) {
        this.deleteTime = deleteTime;
    }
    
    private static void closeDoorInCrystalCave(int doorId) {
        for (L1Object object : World.get().getObject()) {
            if (object instanceof L1DoorInstance) {
                L1DoorInstance door = (L1DoorInstance)object;
                if (door.getDoorId() == doorId) {
                    door.close();
                }
                else {
                    continue;
                }
            }
        }
    }
    
    public final boolean isBroadcast() {
        return this._isBroadcast;
    }
    
    public final void setBroadcast(boolean isBroadcast) {
        this._isBroadcast = isBroadcast;
    }
    
    public final String getBroadcastInfo() {
        return this._broadcastInfo;
    }
    
    public final void setBroadcastInfo(String broadcastInfo) {
        this._broadcastInfo = broadcastInfo;
    }
    
    public final String getmsg1() {
        return this._msg1;
    }
    
    public final void setmsg1(String msg1) {
        this._msg1 = msg1;
    }
    
    public final String getmsg2() {
        return this._msg2;
    }
    
    public final void setmsg2(String msg2) {
        this._msg2 = msg2;
    }
    
    private class SpawnTask implements Runnable
    {
        private int _spawnNumber;
        private int _objectId;
        private long _delay;
        
        private SpawnTask(int spawnNumber, int objectId, long delay) {
            this._spawnNumber = spawnNumber;
            this._objectId = objectId;
            this._delay = delay;
        }
        
        public void getStart() {
            GeneralThreadPool.get().schedule(this, this._delay);
        }
        
        @Override
        public void run() {
            L1Spawn.this.doSpawn(this._spawnNumber, this._objectId);
        }
    }
}
