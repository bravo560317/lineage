package com.lineage.server.model.SoulTower;

import com.lineage.server.IdFactoryNpc;
import com.lineage.server.datatables.DoorSpawnTable;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.IdFactory;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.WorldItem;
import java.util.Iterator;
import com.lineage.server.model.L1Location;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

public class SoulTowerThread extends Thread
{
    private static Logger _log;
    short mapId;
    L1PcInstance pc;
    L1DoorInstance door0;
    ArrayList<L1NpcInstance> list;
    
    static {
        SoulTowerThread._log = Logger.getLogger(SoulTowerThread.class.getName());
    }
    
    public SoulTowerThread(int mapId, L1PcInstance pc) {
        this.list = new ArrayList();
        this.mapId = (short)mapId;
        this.pc = pc;
    }
    
    @Override
    public void run() {
        System.out.println("屍魂塔副本線程開始 地圖：" + (int)this.mapId + " 玩家：" + this.pc.getName());
        try {
            long begin = System.currentTimeMillis();
            L1Teleport.teleport(this.pc, 32869, 32923, this.mapId, 2, true);
            Thread.sleep(100L);
            this.pc.sendPackets(new S_PacketBox(195, 1800));
            this.pc.sendPackets(new S_PacketBox(83, 2));
            this.pc.sendPackets(new S_PacketBox(83, 7));
            this.spawn(new L1Location(32846, 32849, (int)this.mapId), 93214, 1);
            this.spawn(new L1Location(32801, 32812, (int)this.mapId), 93214, 1);
            this.spawn(new L1Location(32756, 32872, (int)this.mapId), 93214, 1);
            L1DoorInstance door1 = this.spawnDoor(0, 12632, 32843, 32878, this.mapId, 0, 1, false, 32843, 32846);
            door1.close();
            this.spawn(new L1Location(32849, 32923, (int)this.mapId), 93200, 10);
            this.spawn(new L1Location(32844, 32905, (int)this.mapId), 93198, 6);
            this.spawn(new L1Location(32844, 32905, (int)this.mapId), 93199, 6);
            Thread.sleep(10000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18344"));
            Thread.sleep(3000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18327"));
            Thread.sleep(3000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18328"));
            this.isKillNpc(this.list = this.spawn(new L1Location(32843, 32894, (int)this.mapId), 93203, 2));
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18329"));
            this.spawn(new L1Location(32843, 32886, (int)this.mapId), 93203, 2);
            this.spawn(new L1Location(32843, 32886, (int)this.mapId), 93198, 6);
            this.isKillNpc(this.list = this.spawn(new L1Location(32843, 32885, (int)this.mapId), 93201, 1));
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            door1.open();
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18338"));
            L1DoorInstance door2 = this.spawnDoor(0, 6336, 32842, 32848, this.mapId, 0, 1, false, 32841, 32844);
            door2.close();
            Thread.sleep(3000L);
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18347"));
            (this.list = this.spawn(new L1Location(32844, 32862, (int)this.mapId), 93203, 4)).addAll(this.spawn(new L1Location(32844, 32862, (int)this.mapId), 93204, 4));
            this.list.addAll(this.spawn(new L1Location(32844, 32862, (int)this.mapId), 93205, 4));
            this.spawn(new L1Location(32844, 32862, (int)this.mapId), 93198, 6);
            this.isKillNpc(this.list);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18330"));
            this.spawn(new L1Location(32859, 32858, (int)this.mapId), 93203, 2);
            this.isKillNpc(this.list = this.spawn(new L1Location(32859, 32858, (int)this.mapId), 93206, 1));
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18333"));
            door2.open();
            L1DoorInstance door3 = this.spawnDoor(0, 12754, 32820, 32812, this.mapId, 0, 1, false, 32811, 32812, 1);
            L1DoorInstance door4 = this.spawnDoor(0, 12754, 32820, 32813, this.mapId, 0, 1, false, 32812, 32813, 1);
            L1DoorInstance door5 = this.spawnDoor(0, 12754, 32820, 32814, this.mapId, 0, 1, false, 32813, 32814, 1);
            door3.close();
            door4.close();
            door5.close();
            Thread.sleep(5000L);
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18331"));
            this.spawn(new L1Location(32846, 32814, (int)this.mapId), 93204, 2);
            this.spawn(new L1Location(32846, 32814, (int)this.mapId), 93198, 3);
            this.spawn(new L1Location(32846, 32814, (int)this.mapId), 93203, 2);
            this.spawn(new L1Location(32864, 32804, (int)this.mapId), 93204, 2);
            this.spawn(new L1Location(32864, 32804, (int)this.mapId), 93199, 3);
            this.spawn(new L1Location(32864, 32804, (int)this.mapId), 93203, 2);
            this.spawn(new L1Location(32850, 32801, (int)this.mapId), 93204, 2);
            this.spawn(new L1Location(32850, 32801, (int)this.mapId), 93198, 3);
            this.spawn(new L1Location(32850, 32801, (int)this.mapId), 93203, 2);
            this.spawn(new L1Location(32831, 32799, (int)this.mapId), 93204, 2);
            this.spawn(new L1Location(32831, 32799, (int)this.mapId), 93199, 3);
            this.spawn(new L1Location(32831, 32799, (int)this.mapId), 93203, 2);
            this.isKillNpc(this.list = this.spawn(new L1Location(32833, 32809, (int)this.mapId), 93203, 1));
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            door3.open();
            door4.open();
            door5.open();
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18348"));
            L1DoorInstance door6 = this.spawnDoor(0, 12711, 32790, 32815, this.mapId, 0, 1, false, 32814, 32818, 1);
            door6.close();
            this.spawn(new L1Location(32800, 32816, (int)this.mapId), 93203, 2);
            this.spawn(new L1Location(32800, 32816, (int)this.mapId), 93205, 3);
            this.isKillNpc(this.list = this.spawn(new L1Location(32800, 32816, (int)this.mapId), 93207, 1));
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            door6.open();
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18340"));
            ArrayList<L1DoorInstance> doorlist = new ArrayList();
            for (int i = 32769; i <= 32777; ++i) {
                L1DoorInstance door7 = this.spawnDoor(0, 12754, i, 32829, this.mapId, 0, 1, false, i, i, 0);
                door7.close();
                doorlist.add(door7);
            }
            (this.door0 = this.spawnDoor(0, 12711, 32760, 32819, this.mapId, 0, 1, false, 32818, 32822, 1)).close();
            Thread.sleep(3000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18349"));
            this.spawn(new L1Location(32776, 32818, (int)this.mapId), 93205, 4);
            this.spawn(new L1Location(32776, 32818, (int)this.mapId), 93199, 4);
            this.spawn(new L1Location(32776, 32818, (int)this.mapId), 93203, 4);
            this.spawn(new L1Location(32776, 32818, (int)this.mapId), 93204, 4);
            this.isKillNpc(this.list = this.spawn(new L1Location(32776, 32818, (int)this.mapId), 93198, 4));
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            for (L1DoorInstance door8 : doorlist) {
                door8.open();
            }
            doorlist = new ArrayList();
            for (int i = 32763; i <= 32776; ++i) {
                L1DoorInstance door7 = this.spawnDoor(0, 12754, i, 32843, this.mapId, 0, 1, false, i, i, 0);
                door7.close();
                doorlist.add(door7);
            }
            Thread.sleep(5000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18332"));
            this.spawn(new L1Location(32772, 32835, (int)this.mapId), 93204, 4);
            this.spawn(new L1Location(32772, 32835, (int)this.mapId), 93205, 4);
            this.spawn(new L1Location(32772, 32835, (int)this.mapId), 93199, 4);
            this.spawn(new L1Location(32772, 32835, (int)this.mapId), 93203, 4);
            this.spawn(new L1Location(32772, 32835, (int)this.mapId), 93198, 4);
            Thread.sleep(15000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            for (L1DoorInstance door8 : doorlist) {
                door8.open();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18333"));
            doorlist = new ArrayList();
            for (int i = 32749; i < 32751; ++i) {
                L1DoorInstance door7 = this.spawnDoor(0, 12754, i, 32881, this.mapId, 0, 1, false, i, i, 0);
                door7.close();
                doorlist.add(door7);
            }
            this.spawn(new L1Location(32769, 32854, (int)this.mapId), 93205, 2);
            this.spawn(new L1Location(32769, 32854, (int)this.mapId), 93199, 2);
            this.spawn(new L1Location(32769, 32854, (int)this.mapId), 93203, 2);
            this.spawn(new L1Location(32769, 32854, (int)this.mapId), 93204, 2);
            this.spawn(new L1Location(32769, 32854, (int)this.mapId), 93198, 2);
            this.isKillNpc(this.list = this.spawn(new L1Location(32769, 32854, (int)this.mapId), 93208, 1));
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18341"));
            for (L1DoorInstance door8 : doorlist) {
                door8.open();
            }
            L1DoorInstance door9 = this.spawnDoor(0, 12711, 32769, 32905, this.mapId, 0, 1, false, 32904, 32908, 1);
            door9.close();
            Thread.sleep(10000L);
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18334"));
            this.spawn(new L1Location(32753, 32898, (int)this.mapId), 93205, 6);
            this.spawn(new L1Location(32753, 32898, (int)this.mapId), 93199, 6);
            this.spawn(new L1Location(32753, 32898, (int)this.mapId), 93203, 6);
            this.spawn(new L1Location(32753, 32898, (int)this.mapId), 93204, 6);
            this.spawn(new L1Location(32753, 32898, (int)this.mapId), 93198, 10);
            Thread.sleep(15000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18335"));
            this.isKillNpc(this.list = this.spawn(new L1Location(32765, 32906, (int)this.mapId), 93202, 1));
            door9.open();
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18342"));
            Thread.sleep(3000L);
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18336"));
            this.spawn(new L1Location(32786, 32906, (int)this.mapId), 93205, 4);
            this.spawn(new L1Location(32786, 32906, (int)this.mapId), 93199, 4);
            this.spawn(new L1Location(32786, 32906, (int)this.mapId), 93203, 4);
            this.spawn(new L1Location(32786, 32906, (int)this.mapId), 93204, 4);
            this.spawn(new L1Location(32786, 32906, (int)this.mapId), 93198, 4);
            Thread.sleep(10000L);
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18337"));
            this.isKillNpc(this.list = this.spawn(new L1Location(32786, 32906, (int)this.mapId), 93210, 1));
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(83, 2));
            this.pc.sendPackets(new S_PacketBox(83, 7));
            int usertime = (int)((System.currentTimeMillis() - begin) / 1000L);
            this.pc.sendPackets(new S_PacketBox(196, usertime));
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18343"));
            Thread.sleep(2000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18574"));
            Thread.sleep(2000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            this.pc.sendPackets(new S_PacketBox(84, 2, "\\f=$18575"));
            this.pc.sendPackets(new S_PacketBox(83, 3));
            Thread.sleep(2000L);
            if (!this.isSoulTower()) {
                this.quitSoulTower();
            }
            for (int j = 0; j < 10; ++j) {
                this.pc.sendPackets(new S_PacketBox(84, 2, "$" + (18576 + j)));
                Thread.sleep(1000L);
            }
            this.quitSoulTower();
        }
        catch (InterruptedException ex) {}
    }
    
    private ArrayList<L1NpcInstance> spawn(L1Location loc, int npcid, int count) {
        ArrayList<L1NpcInstance> list = new ArrayList();
        if (count > 1) {
            for (int i = 0; i < count; ++i) {
                list.add(this.spawnNpc(loc, npcid, 10));
            }
        }
        else {
            list.add(this.spawnNpc(loc, npcid, 1));
        }
        return list;
    }
    
    private int isKillNpc(ArrayList<L1NpcInstance> list) {
        boolean isAllKill = true;
        while (isAllKill) {
            if (!this.isSoulTower()) {
                this.quitSoulTower();
                return -1;
            }
            else {
                boolean isAllDeath = false;
                for (L1NpcInstance npc : list) {
                    if (!npc.isDead()) {
                        break;
                    }
                    else {
                        isAllDeath = npc.isDead();
                    }
                }
                if (isAllDeath) {
                    isAllKill = false;
                    return 0;
                }
                else {
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException ex) {}
                }
            }
        }
        this.quitSoulTower();
        return -1;
    }
    
    private boolean isSoulTower() {
        return this.pc != null && this.pc.getOnlineStatus() != 0 && this.pc.getMapId() == this.mapId;
    }
    
    private void quitSoulTower() {
        for (int i = 640319; i <= 640325; ++i) {
            L1ItemInstance[] itemlist = this.pc.getInventory().findItemsId(i);
            if (itemlist != null && itemlist.length > 0) {
                L1ItemInstance[] array;
                for (int length = (array = itemlist).length, j = 0; j < length; ++j) {
                    L1ItemInstance item = array[j];
                    this.pc.getInventory().removeItem(item);
                }
            }
        }
        L1ItemInstance item2 = this.pc.getInventory().findItemId(95294);
        if (item2 != null) {
            this.pc.getInventory().removeItem(item2);
        }
        if (this.pc.hasSkillEffect(8906)) {
            this.pc.removeSkillEffect(8906);
        }
        if (this.pc.hasSkillEffect(8907)) {
            this.pc.removeSkillEffect(8907);
        }
        this.pc.setTempThread(null);
        if (this.pc != null && this.pc.getMapId() == this.mapId) {
            this.pc.setSoulTower(0);
            L1Teleport.teleport(this.pc, 33703, 32502, (short)4, 5, true);
        }
        for (L1ItemInstance obj : WorldItem.get().all()) {
            if (obj.getMapId() == this.mapId) {
                L1Inventory groundInventory = World.get().getInventory(obj.getX(), obj.getY(), obj.getMapId());
                groundInventory.removeItem(obj);
            }
        }
        World.get().closeMap((int)this.mapId);
        L1SoulTower.get().mapStat[this.mapId - 4001] = false;
        System.out.println("屍魂塔副本線程結束 地圖：" + (int)this.mapId);
        this.interrupt();
    }
    
    private L1NpcInstance spawnNpc(L1Location loc, int npcid, int randomRange) {
        L1Npc l1npc = NpcTable.get().getTemplate(npcid);
        L1NpcInstance field = null;
        if (l1npc == null) {
            SoulTowerThread._log.warning("召喚的NPCID:" + npcid + "不存在");
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
            L1WorldMap.get().getMap(this.mapId).setPassable(field.getLocation(), false);
            World.get().storeObject(field);
            World.get().addVisibleObject(field);
            return field;
        }
    }
    
    public L1DoorInstance spawnDoor(int doorId, int gfxId, int locx, int locy, short mapid, int hp, int keeper, boolean isopening, int left_edge_location, int right_edge_location) {
        return this.spawnDoor(doorId, gfxId, locx, locy, mapid, hp, keeper, isopening, left_edge_location, right_edge_location, 0);
    }
    
    public L1DoorInstance spawnDoor(int doorId, int gfxId, int locx, int locy, short mapid, int hp, int keeper, boolean isopening, int left_edge_location, int right_edge_location, int direction) {
        L1DoorInstance[] doorList;
        int length = (doorList = DoorSpawnTable.get().getDoorList()).length;
        int i = 0;
        while (i < length) {
            L1DoorInstance temp = doorList[i];
            if (temp.getMapId() == mapid && temp.getHomeX() == locx && temp.getHomeY() == locy) {
                return temp;
            }
            else {
                ++i;
            }
        }
        L1DoorInstance door = null;
        try {
            door = (L1DoorInstance)NpcTable.get().newNpcInstance(81158);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        door.setId(IdFactoryNpc.get().nextId());
        door.setDoorId(doorId);
        door.setGfxId(gfxId);
        door.setX(locx);
        door.setY(locy);
        door.setMap(mapid);
        door.setHomeX(locx);
        door.setHomeY(locy);
        door.setDirection(direction);
        door.setLeftEdgeLocation(left_edge_location);
        door.setRightEdgeLocation(right_edge_location);
        door.setMaxHp(hp);
        door.setCurrentHp(hp);
        door.setKeeperId(keeper);
        door.setOpenStatus(28);
        World.get().storeObject(door);
        World.get().addVisibleObject(door);
        DoorSpawnTable.get().addDoor(door);
        return door;
    }
}
