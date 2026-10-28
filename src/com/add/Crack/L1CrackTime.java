package com.add.Crack;

import com.lineage.server.serverpackets.S_Weather;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_MapID;
import com.lineage.server.serverpackets.S_RemoveObject;
import java.lang.reflect.Constructor;
import com.lineage.server.templates.L1Npc;
import java.util.logging.Level;
import com.lineage.server.IdFactory;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.L1Location;
import com.lineage.server.utils.Random;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.add.L1Config_other;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.Timer;
import java.util.logging.Logger;
import java.util.TimerTask;

public class L1CrackTime extends TimerTask
{
    private static Logger _log;
    private Timer _timeHandler;
    private boolean _isOver;
    private int _Crack;
    private int _startTime;
    private static final int[][] _crack;
    private static final int[][] _crackLoc;
    private static L1CrackTime _instance;
    private boolean _GateOpen;
    public static boolean _BossKill;
    
    static {
        L1CrackTime._log = Logger.getLogger(L1CrackTime.class.getName());
        _crack = new int[][] { { 32639, 32876, 780 }, { 32794, 32751, 783 } };
        _crackLoc = new int[][] { { 32728, 32709, 4 }, { 32848, 32639, 4 }, { 32852, 32705, 4 }, { 32913, 33168, 4 }, { 32957, 33247, 4 }, { 32913, 33425, 4 }, { 34255, 33203, 4 }, { 34232, 33312, 4 }, { 34276, 33359, 4 } };
        L1CrackTime._BossKill = false;
    }
    
    public static L1CrackTime getStart() {
        if (L1CrackTime._instance == null) {
            L1CrackTime._instance = new L1CrackTime();
        }
        return L1CrackTime._instance;
    }
    
    private L1CrackTime() {
        this._timeHandler = new Timer(true);
        this._isOver = false;
        this._Crack = 0;
        this._startTime = 0;
        this._GateOpen = false;
        this._timeHandler.schedule(this, 1000L, 1000L);
        GeneralThreadPool.get().execute(this);
    }
    
    @Override
    public void run() {
        if (this._isOver) {
            try {
                this.clear();
                Thread.sleep((long)(L1Config_other._2216 * 3600 * 1000));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        switch (++this._startTime) {
            case 60: {
                this.spawnCrack();
                break;
            }
        }
        if (this._startTime >= 120 && !getBossKill()) {
            this.setGateOpen(true);
        }
        if (this._startTime == (L1Config_other._2217 - 1) * 60 && !getBossKill()) {
            World.get().broadcastServerMessage("時空裂痕即將關閉...");
        }
        if (this._startTime >= L1Config_other._2217 * 60 && !getBossKill()) {
            if ((L1Tikal.getGameEnd() == 0 && this._Crack == 2) || (L1Thebes.getGameEnd() == 0 && this._Crack == 1)) {
                this._isOver = true;
            }
            else if ((L1Tikal.getGameEnd() == 1 && this._Crack == 2) || (L1Thebes.getGameEnd() == 1 && this._Crack == 1)) {
                for (L1PcInstance pc : World.get().getAllPlayers()) {
                    if (pc.getMapId() >= 780 && pc.getMapId() <= 784 && pc.isDead()) {
                        this.restartPlayer(pc, 32616, 32782, (short)4);
                    }
                }
                setBossKill(true);
            }
            else if ((L1Tikal.getGameEnd() == 2 && this._Crack == 2) || (L1Thebes.getGameEnd() == 2 && this._Crack == 1)) {
                this._isOver = true;
            }
        }
        if (this._startTime >= (L1Config_other._2217 + L1Config_other._2218) * 60) {
            this._isOver = true;
        }
    }
    
    private void clear() {
        this._startTime = 0;
        this._isOver = false;
        this._Crack = 0;
        this.setGateOpen(false);
        setBossKill(false);
        L1Tikal.setGameStatus(0);
        L1Thebes.setGameStatus(0);
        World.get().broadcastServerMessage("時空裂痕關閉了。");
        for (L1NpcInstance object1 : WorldNpc.get().all()) {
            L1Object obj = object1;
            if (obj instanceof L1NpcInstance) {
                L1NpcInstance Cracknpc = (L1NpcInstance)obj;
                if (Cracknpc.getNpcId() == 71254) {
                    Cracknpc.deleteMe();
                }
                else {
                    continue;
                }
            }
        }
        for (L1Object obj : WorldNpc.get().all()) {
            if (obj instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)obj;
                if ((mob.getNpcId() >= 46107 && mob.getNpcId() <= 46122) || (mob.getNpcId() >= 46123 && mob.getNpcId() <= 46124) || (mob.getNpcId() >= 92002 && mob.getNpcId() <= 92017) || (mob.getNpcId() >= 92000 && mob.getNpcId() <= 92001)) {
                    mob.deleteMe();
                }
                else {
                    continue;
                }
            }
        }
        for (L1PcInstance pc : World.get().getAllPlayers()) {
            if (pc.getMapId() >= 780 && pc.getMapId() <= 784) {
                if (pc.isDead()) {
                    this.restartPlayer(pc, 32616, 32782, (short)4);
                }
                else {
                    L1Teleport.teleport(pc, 33442, 32797, (short)4, 5, true);
                }
            }
        }
    }
    
    private void spawnCrack() {
        L1Location crack = null;
        L1Location crack_loc = null;
        int rnd1 = Random.nextInt(2);
        int rnd2 = Random.nextInt(9);
        crack = new L1Location(L1CrackTime._crack[rnd1][0], L1CrackTime._crack[rnd1][1], L1CrackTime._crack[rnd1][2]);
        crack_loc = new L1Location(L1CrackTime._crackLoc[rnd2][0], L1CrackTime._crackLoc[rnd2][1], L1CrackTime._crackLoc[rnd2][2]);
        if (rnd1 == 1) {
            this._Crack = 1;
        }
        else {
            this._Crack = 2;
        }
        if (getBossKill()) {
            World.get().broadcastServerMessage("時空裂痕開啟了，裂痕的力量減弱，因此關閉時間呈現被延後的狀態...");
        }
        else {
            World.get().broadcastServerMessage("時空裂痕開啟了！！異界侵略即將開始...");
        }
        this.createCrack(crack.getX(), crack.getY(), (short)crack.getMapId(), crack_loc.getX(), crack_loc.getY(), (short)crack_loc.getMapId());
        this.createCrack(crack_loc.getX(), crack_loc.getY(), (short)crack_loc.getMapId(), crack.getX(), crack.getY(), (short)crack.getMapId());
    }
    
    private void createCrack(int x, int y, short mapId, int to_x, int to_y, short to_mapId) {
        try {
            L1Npc l1npc = NpcTable.get().getTemplate(71254);
            if (l1npc == null) {
                return;
            }
            else {
                String s = l1npc.getImpl();
                Constructor<?> constructor = Class.forName("com.lineage.server.model.Instance." + s + "Instance").getConstructors()[0];
                Object[] aobj = { l1npc };
                L1NpcInstance npc = (L1NpcInstance)constructor.newInstance(aobj);
                npc.setId(IdFactory.get().nextId());
                npc.setX(x);
                npc.setY(y);
                npc.setMap(mapId);
                npc.setHomeX(npc.getX());
                npc.setHomeY(npc.getY());
                npc.setHeading(0);
                World.get().storeObject(npc);
                World.get().addVisibleObject(npc);
                L1CrackTime.Teleport teleport = new L1CrackTime.Teleport(npc, to_x, to_y, to_mapId);
                GeneralThreadPool.get().execute(teleport);
            }
        }
        catch (Exception e) {
            L1CrackTime._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }
    
    public void restartPlayer(L1PcInstance pc, int locx, int locy, short mapid) {
        pc.removeAllKnownObjects();
        pc.broadcastPacketAll(new S_RemoveObject(pc));
        pc.setCurrentHp(pc.getLevel());
        pc.set_food(40);
        pc.setDead(false);
        pc.setStatus(0);
        World.get().moveVisibleObject(pc, (int)mapid);
        pc.setX(locx);
        pc.setY(locy);
        pc.setMap(mapid);
        pc.sendPackets(new S_MapID(pc, (int)pc.getMapId(), pc.getMap().isUnderwater()));
        pc.broadcastPacketAll(new S_OtherCharPacks(pc));
        pc.sendPackets(new S_OwnCharPack(pc));
        pc.sendPackets(new S_CharVisualUpdate(pc));
        pc.startHpRegeneration();
        pc.startMpRegeneration();
        pc.sendPackets(new S_Weather(World.get().getWeather()));
        pc.stopPcDeleteTimer();
        if (pc.getHellTime() > 0) {
            pc.beginHell(false);
        }
    }
    
    public void setGateOpen(boolean GateOpen) {
        this._GateOpen = GateOpen;
    }
    
    public boolean getGateOpen() {
        return this._GateOpen;
    }
    
    public static void setBossKill(boolean BossKill) {
        L1CrackTime._BossKill = BossKill;
    }
    
    private static boolean getBossKill() {
        return L1CrackTime._BossKill;
    }
    
    class Teleport implements Runnable
    {
        private L1NpcInstance _npc;
        private int _to_x;
        private int _to_y;
        private short _to_mapId;
        
        public Teleport(L1NpcInstance npc, int to_x, int to_y, short to_mapId) {
            this._npc = null;
            this._to_x = 0;
            this._to_y = 0;
            this._to_mapId = 0;
            this._npc = npc;
            this._to_x = to_x;
            this._to_y = to_y;
            this._to_mapId = to_mapId;
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep(1000L);
                while (!this._npc._destroyed) {
                    for (L1Object obj : World.get().getVisiblePoint(this._npc.getLocation(), 1, this._npc.get_showId())) {
                        if (obj instanceof L1PcInstance) {
                            L1PcInstance target = (L1PcInstance)obj;
                            L1Location tmp_loc = new L1Location(this._to_x, this._to_y, (int)this._to_mapId);
                            L1Location rnd_loc = tmp_loc.randomLocation(1, 5, false);
                            L1Teleport.teleport(target, rnd_loc.getX(), rnd_loc.getY(), (short)rnd_loc.getMapId(), target.getHeading(), true);
                        }
                    }
                    Thread.sleep(1000L);
                }
                return;
            }
            catch (Exception e) {
                L1CrackTime._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
                return;
            }
        }
    }
}
