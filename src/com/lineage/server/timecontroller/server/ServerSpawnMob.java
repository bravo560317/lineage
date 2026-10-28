package com.lineage.server.timecontroller.server;

import com.lineage.server.IdFactoryNpc;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.IdFactory;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.ArrayList;
import com.lineage.server.model.L1Location;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import java.util.Calendar;
import com.lineage.william.NowTimeSpawn;
import com.lineage.server.world.TimeInfo;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerSpawnMob extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private int _omm;
    
    static {
        _log = LogFactory.getLog(ServerSpawnMob.class);
    }
    
    public void start() {
        int timeMillis = 10000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 10000L, 10000L);
    }
    
    @Override
    public void run() {
        String mTime = TimeInfo.time().getNow_YMDHMS(3);
        String hTime = TimeInfo.time().getNow_YMDHMS(4);
        int mm = Integer.parseInt(mTime);
        int hh = Integer.parseInt(hTime);
        NowTimeSpawn[] list = NowTimeSpawn.get().getList();
        if (this._omm != mm) {
            try {
                Calendar nowTime = Calendar.getInstance();
                Calendar date = Calendar.getInstance();
                int nowWeek = date.get(7) - 1;
                if (nowWeek == 0) {
                    nowWeek += 7;
                }
                nowTime.setTimeInMillis(System.currentTimeMillis());
                NowTimeSpawn[] array;
                for (int length = (array = list).length, i = 0; i < length; ++i) {
                    NowTimeSpawn aa = array[i];
                    int week = aa.getweek();
                    if (aa.getweek() > 0 && week == nowWeek && aa.getHH() == hh && aa.getMM() == mm) {
                        if (aa.getNote() != null) {
                            World.get().broadcastPacketToAll(new S_SystemMessage(aa.getNote()));
                        }
                        if (aa.getNote1() != null) {
                            World.get().broadcastPacketToAll(new S_BlueMessage(166, aa.getNote1()));
                        }
                        if (aa.getSpecial() != null) {
                            int[] special;
                            for (int length2 = (special = aa.getSpecial()).length, j = 0; j < length2; ++j) {
                                int sp = special[j];
                                World.get().broadcastPacketToAll(new S_PacketBox(83, sp));
                            }
                        }
                        if (aa.getistp() == 1) {
                            spawnDoor(aa.getNpcId(), aa.getX(), aa.getY(), 5, (short)aa.getMapId(), aa.gettptime());
                        }
                        else if (aa.getNpcId() != 0 && aa.getX() != 0 && aa.getY() != 0 && aa.getMapId() != 0) {
                            int npcid = aa.getNpcId();
                            if (aa.gettptime() > 0) {
                                this.spawn_time(new L1Location(aa.getX(), aa.getY(), (int)(short)aa.getMapId()), npcid, aa.getIsCount(), (int)(short)aa.getMapId(), aa.gettptime(), aa.getxyran());
                            }
                            else {
                                this.spawn(new L1Location(aa.getX(), aa.getY(), (int)(short)aa.getMapId()), aa.getNpcId(), aa.getIsCount(), (int)(short)aa.getMapId(), aa.getxyran());
                            }
                        }
                    }
                    if (aa.getweek() == -1 && aa.getHH() == hh && aa.getMM() == mm) {
                        if (aa.getNote() != null) {
                            World.get().broadcastPacketToAll(new S_SystemMessage(aa.getNote()));
                        }
                        if (aa.getNote1() != null) {
                            World.get().broadcastPacketToAll(new S_BlueMessage(166, aa.getNote1()));
                        }
                        if (aa.getSpecial() != null) {
                            int[] special2;
                            for (int length3 = (special2 = aa.getSpecial()).length, k = 0; k < length3; ++k) {
                                int sp = special2[k];
                                World.get().broadcastPacketToAll(new S_PacketBox(83, sp));
                            }
                        }
                        if (aa.getistp() == 1) {
                            spawnDoor(aa.getNpcId(), aa.getX(), aa.getY(), 5, (short)aa.getMapId(), aa.gettptime());
                        }
                        else if (aa.getNpcId() != 0 && aa.getX() != 0 && aa.getY() != 0 && aa.getMapId() != 0) {
                            if (aa.gettptime() > 0) {
                                this.spawn_time(new L1Location(aa.getX(), aa.getY(), (int)(short)aa.getMapId()), aa.getNpcId(), aa.getIsCount(), (int)(short)aa.getMapId(), aa.gettptime(), aa.getxyran());
                            }
                            else {
                                this.spawn(new L1Location(aa.getX(), aa.getY(), (int)(short)aa.getMapId()), aa.getNpcId(), aa.getIsCount(), (int)(short)aa.getMapId(), aa.getxyran());
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                ServerSpawnMob._log.error("固定時間放怪時間軸異常重啟", e);
                GeneralThreadPool.get().cancel(this._timer, false);
                ServerSpawnMob warTimer = new ServerSpawnMob();
                warTimer.start();
            }
            this._omm = mm;
        }
    }
    
    private ArrayList<L1NpcInstance> spawn_time(L1Location loc, int npcid, int count, int mapid, int time, int xyran) {
        ArrayList<L1NpcInstance> list = new ArrayList();
        if (count > 1) {
            for (int i = 0; i < count; ++i) {
                list.add(this.spawnNpc_time(loc, npcid, xyran, mapid, time));
            }
        }
        else {
            list.add(this.spawnNpc_time(loc, npcid, 1, mapid, time));
        }
        return list;
    }
    
    private L1NpcInstance spawnNpc_time(L1Location loc, int npcid, int randomRange, int mapid, int time) {
        L1Npc l1npc = NpcTable.get().getTemplate(npcid);
        L1NpcInstance field = null;
        if (l1npc == null) {
            return null;
        }
        else {
            try {
                field = NpcTable.get().newNpcInstance(npcid);
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            field.setId(IdFactory.get().nextId());
            field.setMap((short)loc.getMapId());
            int tryCount = 0;
            do {
                ++tryCount;
                field.setX(loc.getX() + (int)(Math.random() * (double)randomRange) - (int)(Math.random() * (double)randomRange));
                field.setY(loc.getY() + (int)(Math.random() * (double)randomRange) - (int)(Math.random() * (double)randomRange));
                if (field.getMap().isInMap(field.getLocation()) && field.getMap().isPassable(field.getX(), field.getY())) {
                    break;
                }
                else {
                    try {
                        Thread.sleep(2L);
                    }
                    catch (InterruptedException ex) {}
                }
            } while (tryCount < 50);
            if (tryCount >= 50) {
                field.getLocation().set(loc);
            }
            field.setHomeX(field.getX());
            field.setHomeY(field.getY());
            field.setHeading(5);
            field.setLightSize(l1npc.getLightSize());
            field.set_spawnTime(time);
            L1WorldMap.get().getMap((short)mapid).setPassable(field.getLocation(), false);
            World.get().storeObject(field);
            World.get().addVisibleObject(field);
            return field;
        }
    }
    
    private ArrayList<L1NpcInstance> spawn(L1Location loc, int npcid, int count, int mapid, int xyran) {
        ArrayList<L1NpcInstance> list = new ArrayList();
        if (count > 1) {
            for (int i = 0; i < count; ++i) {
                list.add(this.spawnNpc(loc, npcid, xyran, mapid));
            }
        }
        else {
            list.add(this.spawnNpc(loc, npcid, 1, mapid));
        }
        return list;
    }
    
    private L1NpcInstance spawnNpc(L1Location loc, int npcid, int randomRange, int mapid) {
        L1Npc l1npc = NpcTable.get().getTemplate(npcid);
        L1NpcInstance field = null;
        if (l1npc == null) {
            return null;
        }
        else {
            try {
                field = NpcTable.get().newNpcInstance(npcid);
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            field.setId(IdFactory.get().nextId());
            field.setMap((short)loc.getMapId());
            int tryCount = 0;
            do {
                ++tryCount;
                field.setX(loc.getX() + (int)(Math.random() * (double)randomRange) - (int)(Math.random() * (double)randomRange));
                field.setY(loc.getY() + (int)(Math.random() * (double)randomRange) - (int)(Math.random() * (double)randomRange));
                if (field.getMap().isInMap(field.getLocation()) && field.getMap().isPassable(field.getX(), field.getY())) {
                    break;
                }
                else {
                    try {
                        Thread.sleep(2L);
                    }
                    catch (InterruptedException ex) {}
                }
            } while (tryCount < 50);
            if (tryCount >= 50) {
                field.getLocation().set(loc);
            }
            field.setHomeX(field.getX());
            field.setHomeY(field.getY());
            field.setHeading(5);
            field.setLightSize(l1npc.getLightSize());
            L1WorldMap.get().getMap((short)mapid).setPassable(field.getLocation(), false);
            World.get().storeObject(field);
            World.get().addVisibleObject(field);
            return field;
        }
    }
    
    public static void spawnDoor(int npcId, int x, int y, int heading, short mapid, int time) {
        try {
            L1NpcInstance npc = NpcTable.get().newNpcInstance(npcId);
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(mapid);
            npc.setX(x);
            npc.setY(y);
            npc.setHeading(heading);
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.set_spawnTime(time * 60);
            npc.onNpcAI();
            npc.startChat(0);
        }
        catch (Exception e) {
            ServerSpawnMob._log.error(e.getLocalizedMessage(), e);
        }
    }
}
