package com.lineage.server.model;

import com.lineage.server.datatables.UBTable;
import com.lineage.server.utils.IntRange;
import java.util.logging.Level;
import java.util.TimeZone;
import com.lineage.config.Config;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.utils.Random;
import com.lineage.server.datatables.UBSpawnTable;
import com.lineage.server.utils.collections.Lists;
import java.util.TreeSet;
import java.util.HashSet;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.world.World;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Iterator;
import com.lineage.server.datatables.UbSupplies;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import java.util.logging.Logger;
import java.util.SortedSet;
import java.util.Set;

public class L1UltimateBattle
{
    private int _locX;
    private int _locY;
    private L1Location _location;
    private short _mapId;
    private int _locX1;
    private int _locY1;
    private int _locX2;
    private int _locY2;
    private int _ubId;
    private int _pattern;
    private boolean _isNowUb;
    private boolean _active;
    private int _minLevel;
    private int _maxLevel;
    private int _maxPlayer;
    private boolean _enterRoyal;
    private boolean _enterKnight;
    private boolean _enterMage;
    private boolean _enterElf;
    private boolean _enterDarkelf;
    private boolean _enterDragonKnight;
    private boolean _enterIllusionist;
    private boolean _enterMale;
    private boolean _enterFemale;
    private boolean _usePot;
    private int _hpr;
    private int _mpr;
    private String _ubName;
    private static int BEFORE_MINUTE;
    private Set<Integer> _managers;
    private SortedSet<Integer> _ubTimes;
    private static final Logger _log;
    private final List<L1PcInstance> _members;
    private String[] _ubInfo;
    
    static {
        L1UltimateBattle.BEFORE_MINUTE = 5;
        _log = Logger.getLogger(L1UltimateBattle.class.getName());
    }
    
    private void sendRoundMessage(int curRound) {
        int[] MSGID_ROUND_TABLE = { 893, 894, 895, 896 };
        this.sendMessage(MSGID_ROUND_TABLE[curRound - 1], "");
    }
    
    private void spawnSupplies(int curRound) {
        List<L1UbSupplie> sup = UbSupplies.getInstance().getUbSupplies(curRound);
        if (sup.size() != 0) {
            for (L1UbSupplie t : sup) {
                this.spawnGroundItem(t.getUbItemId(), t.getUbItemStackCont(), t.getUbItemCont());
            }
        }
    }
    
    private void removeRetiredMembers() {
        L1PcInstance[] temp = this.getMembersArray();
        L1PcInstance[] array;
        for (int length = (array = temp).length, i = 0; i < length; ++i) {
            L1PcInstance element = array[i];
            if (element.getMapId() != this._mapId) {
                this.removeMember(element);
            }
        }
    }
    
    private void sendMessage(int type, String msg) {
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            pc.sendPackets(new S_ServerMessage(type, msg));
        }
    }
    
    private void spawnGroundItem(int itemId, int stackCount, int count) {
        L1Item temp = ItemTable.get().getTemplate(itemId);
        if (temp == null) {
            return;
        }
        else {
            for (int i = 0; i < count; ++i) {
                L1Location loc = this._location.randomLocation((this.getLocX2() - this.getLocX1()) / 2, false);
                if (temp.isStackable()) {
                    L1ItemInstance item = ItemTable.get().createItem(itemId);
                    item.setEnchantLevel(0);
                    item.setCount((long)stackCount);
                    L1GroundInventory ground = World.get().getInventory(loc.getX(), loc.getY(), this._mapId);
                    if (ground.checkAddItem(item, (long)stackCount) == 0) {
                        ground.storeItem(item);
                    }
                }
                else {
                    L1ItemInstance item = null;
                    for (int createCount = 0; createCount < stackCount; ++createCount) {
                        item = ItemTable.get().createItem(itemId);
                        item.setEnchantLevel(0);
                        L1GroundInventory ground2 = World.get().getInventory(loc.getX(), loc.getY(), this._mapId);
                        if (ground2.checkAddItem(item, (long)stackCount) == 0) {
                            ground2.storeItem(item);
                        }
                    }
                }
            }
            return;
        }
    }
    
    private void clearColosseum() {
        for (L1Object object46 : World.get().getVisibleObjects((int)this._mapId).values()) {
            Object obj = object46;
            if (obj instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)obj;
                if (!mob.isDead()) {
                    mob.setDead(true);
                    mob.setStatus(8);
                    mob.setCurrentHpDirect(0);
                    mob.deleteMe();
                }
                else {
                    continue;
                }
            }
            else if (obj instanceof L1Inventory) {
                L1Inventory inventory = (L1Inventory)obj;
                inventory.clearItems();
            }
            else {
                continue;
            }
        }
    }
    
    public L1UltimateBattle() {
        this._managers = new HashSet();
        this._ubTimes = new TreeSet();
        this._members = (List)Lists.newArrayList();
    }
    
    public void start() {
        int patternsMax = UBSpawnTable.getInstance().getMaxPattern(this._ubId);
        this._pattern = Random.nextInt(patternsMax) + 1;
        L1UltimateBattle.UbThread ub = new L1UltimateBattle.UbThread();
        GeneralThreadPool.get().execute(ub);
    }
    
    public void addMember(L1PcInstance pc) {
        if (!this._members.contains(pc)) {
            this._members.add(pc);
        }
    }
    
    public void removeMember(L1PcInstance pc) {
        this._members.remove(pc);
    }
    
    public void clearMembers() {
        this._members.clear();
    }
    
    public boolean isMember(L1PcInstance pc) {
        return this._members.contains(pc);
    }
    
    public L1PcInstance[] getMembersArray() {
        return (L1PcInstance[])this._members.toArray(new L1PcInstance[this._members.size()]);
    }
    
    public int getMembersCount() {
        return this._members.size();
    }
    
    private void setNowUb(boolean i) {
        this._isNowUb = i;
    }
    
    public boolean isNowUb() {
        return this._isNowUb;
    }
    
    public int getUbId() {
        return this._ubId;
    }
    
    public void setUbId(int id) {
        this._ubId = id;
    }
    
    public short getMapId() {
        return this._mapId;
    }
    
    public void setMapId(short mapId) {
        this._mapId = mapId;
    }
    
    public int getMinLevel() {
        return this._minLevel;
    }
    
    public void setMinLevel(int level) {
        this._minLevel = level;
    }
    
    public int getMaxLevel() {
        return this._maxLevel;
    }
    
    public void setMaxLevel(int level) {
        this._maxLevel = level;
    }
    
    public int getMaxPlayer() {
        return this._maxPlayer;
    }
    
    public void setMaxPlayer(int count) {
        this._maxPlayer = count;
    }
    
    public void setEnterRoyal(boolean enterRoyal) {
        this._enterRoyal = enterRoyal;
    }
    
    public void setEnterKnight(boolean enterKnight) {
        this._enterKnight = enterKnight;
    }
    
    public void setEnterMage(boolean enterMage) {
        this._enterMage = enterMage;
    }
    
    public void setEnterElf(boolean enterElf) {
        this._enterElf = enterElf;
    }
    
    public void setEnterDarkelf(boolean enterDarkelf) {
        this._enterDarkelf = enterDarkelf;
    }
    
    public void setEnterDragonKnight(boolean enterDragonKnight) {
        this._enterDragonKnight = enterDragonKnight;
    }
    
    public void setEnterIllusionist(boolean enterIllusionist) {
        this._enterIllusionist = enterIllusionist;
    }
    
    public void setEnterMale(boolean enterMale) {
        this._enterMale = enterMale;
    }
    
    public void setEnterFemale(boolean enterFemale) {
        this._enterFemale = enterFemale;
    }
    
    public boolean canUsePot() {
        return this._usePot;
    }
    
    public void setUsePot(boolean usePot) {
        this._usePot = usePot;
    }
    
    public int getHpr() {
        return this._hpr;
    }
    
    public void setHpr(int hpr) {
        this._hpr = hpr;
    }
    
    public int getMpr() {
        return this._mpr;
    }
    
    public void setMpr(int mpr) {
        this._mpr = mpr;
    }
    
    public int getLocX1() {
        return this._locX1;
    }
    
    public void setLocX1(int locX1) {
        this._locX1 = locX1;
    }
    
    public int getLocY1() {
        return this._locY1;
    }
    
    public void setLocY1(int locY1) {
        this._locY1 = locY1;
    }
    
    public int getLocX2() {
        return this._locX2;
    }
    
    public void setLocX2(int locX2) {
        this._locX2 = locX2;
    }
    
    public int getLocY2() {
        return this._locY2;
    }
    
    public void setLocY2(int locY2) {
        this._locY2 = locY2;
    }
    
    public void setName(String name) {
        this._ubName = name;
    }
    
    public String getName() {
        return this._ubName;
    }
    
    public void resetLoc() {
        this._locX = (this._locX2 + this._locX1) / 2;
        this._locY = (this._locY2 + this._locY1) / 2;
        this._location = new L1Location(this._locX, this._locY, (int)this._mapId);
    }
    
    public L1Location getLocation() {
        return this._location;
    }
    
    public void addManager(int npcId) {
        this._managers.add(npcId);
    }
    
    public boolean containsManager(int npcId) {
        return this._managers.contains((Integer)npcId);
    }
    
    public void addUbTime(int time) {
        this._ubTimes.add(time);
    }
    
    public String getNextUbTime() {
        return intToTimeFormat(this.nextUbTime());
    }
    
    private int nextUbTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        int nowTime = Integer.valueOf(sdf.format(getRealTime().getTime()));
        SortedSet<Integer> tailSet = (SortedSet)this._ubTimes.tailSet(nowTime);
        if (tailSet.isEmpty()) {
            tailSet = this._ubTimes;
        }
        return tailSet.first();
    }
    
    private static String intToTimeFormat(int n) {
        return String.valueOf(n / 100) + ":" + n % 100 / 10 + n % 10;
    }
    
    private static Calendar getRealTime() {
        TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(_tz);
        return cal;
    }
    
    public boolean checkUbTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Calendar realTime = getRealTime();
        realTime.add(12, L1UltimateBattle.BEFORE_MINUTE);
        int nowTime = Integer.valueOf(sdf.format(realTime.getTime()));
        return this._ubTimes.contains((Integer)nowTime);
    }
    
    private void setActive(boolean f) {
        this._active = f;
    }
    
    public boolean isActive() {
        return this._active;
    }
    
    public boolean canPcEnter(L1PcInstance pc) {
        L1UltimateBattle._log.log(Level.FINE, "pcname=" + pc.getName() + " ubid=" + this._ubId + " minlvl=" + this._minLevel + " maxlvl=" + this._maxLevel);
        return IntRange.includes(pc.getLevel(), this._minLevel, this._maxLevel) && ((pc.isCrown() && this._enterRoyal) || (pc.isKnight() && this._enterKnight) || (pc.isWizard() && this._enterMage) || (pc.isElf() && this._enterElf) || (pc.isDarkelf() && this._enterDarkelf) || (pc.isDragonKnight() && this._enterDragonKnight) || (pc.isIllusionist() && this._enterIllusionist));
    }
    
    public String[] makeUbInfoStrings() {
        if (this._ubInfo != null) {
            return this._ubInfo;
        }
        else {
            String nextUbTime = this.getNextUbTime() + " ";
            StringBuilder classesBuff = new StringBuilder();
            if (this._enterDarkelf) {
                classesBuff.append("黑暗妖精 ");
            }
            if (this._enterMage) {
                classesBuff.append("法師 ");
            }
            if (this._enterElf) {
                classesBuff.append("妖精 ");
            }
            if (this._enterKnight) {
                classesBuff.append("騎士 ");
            }
            if (this._enterRoyal) {
                classesBuff.append("王族 ");
            }
            if (this._enterDragonKnight) {
                classesBuff.append("龍騎士 ");
            }
            if (this._enterIllusionist) {
                classesBuff.append("幻術師 ");
            }
            String classes = classesBuff.toString().trim();
            StringBuilder sexBuff = new StringBuilder();
            if (this._enterMale) {
                sexBuff.append("男 ");
            }
            if (this._enterFemale) {
                sexBuff.append("女 ");
            }
            String sex = sexBuff.toString().trim();
            String loLevel = String.valueOf(String.valueOf(this._minLevel) + " ");
            String hiLevel = String.valueOf(String.valueOf(this._maxLevel) + " ");
            String teleport = this._location.getMap().isEscapable() ? "可以" : "不可以";
            String res = this._location.getMap().isUseResurrection() ? "可以" : "不可以";
            String pot = "可以";
            String hpr = String.valueOf(this._hpr);
            String mpr = String.valueOf(this._mpr);
            String summon = this._location.getMap().isTakePets() ? "可以" : "不可以";
            String summon2 = this._location.getMap().isRecallPets() ? "可以" : "不可以";
            return this._ubInfo = new String[] { nextUbTime, classes, sex, loLevel, hiLevel, teleport, res, pot, hpr, mpr, summon, summon2 };
        }
    }
    
    class UbThread implements Runnable
    {
        private void countDown() throws InterruptedException {
            int MSGID_COUNT = 4702;
            int MSGID_START = 4701;
            for (int loop = 0; loop < L1UltimateBattle.BEFORE_MINUTE * 60 - 10; ++loop) {
                Thread.sleep(1000L);
            }
            L1UltimateBattle.this.removeRetiredMembers();
            L1UltimateBattle.this.sendMessage(4702, "10");
            Thread.sleep(5000L);
            L1UltimateBattle.this.sendMessage(4702, "5");
            Thread.sleep(1000L);
            L1UltimateBattle.this.sendMessage(4702, "4");
            Thread.sleep(1000L);
            L1UltimateBattle.this.sendMessage(4702, "3");
            Thread.sleep(1000L);
            L1UltimateBattle.this.sendMessage(4702, "2");
            Thread.sleep(1000L);
            L1UltimateBattle.this.sendMessage(4702, "1");
            Thread.sleep(1000L);
            L1UltimateBattle.this.sendMessage(4701, "アルティメット バトル");
            L1UltimateBattle.this.removeRetiredMembers();
        }
        
        private void waitForNextRound(int curRound) throws InterruptedException {
            int[] WAIT_TIME_TABLE = { 6, 6, 2, 18 };
            for (int wait = WAIT_TIME_TABLE[curRound - 1], i = 0; i < wait; ++i) {
                Thread.sleep(10000L);
            }
            L1UltimateBattle.this.removeRetiredMembers();
        }
        
        @Override
        public void run() {
            try {
                L1UltimateBattle.this.setActive(true);
                this.countDown();
                L1UltimateBattle.this.setNowUb(true);
                for (int round = 1; round <= 4; ++round) {
                    L1UltimateBattle.this.sendRoundMessage(round);
                    L1UbPattern pattern = UBSpawnTable.getInstance().getPattern(L1UltimateBattle.this._ubId, L1UltimateBattle.this._pattern);
                    List<L1UbSpawn> spawnList = (List)pattern.getSpawnList(round);
                    for (L1UbSpawn spawn : spawnList) {
                        if (L1UltimateBattle.this.getMembersCount() > 0) {
                            spawn.spawnAll();
                        }
                        Thread.sleep((long)(spawn.getSpawnDelay() * 1000));
                    }
                    if (L1UltimateBattle.this.getMembersCount() > 0) {
                        L1UltimateBattle.this.spawnSupplies(round);
                    }
                    L1PcInstance[] membersArray;
                    for (int length = (membersArray = L1UltimateBattle.this.getMembersArray()).length, i = 0; i < length; ++i) {
                        L1PcInstance pc = membersArray[i];
                        UBTable.getInstance().writeUbScore(L1UltimateBattle.this.getUbId(), pc);
                    }
                    this.waitForNextRound(round);
                }
                L1PcInstance[] membersArray2;
                for (int length2 = (membersArray2 = L1UltimateBattle.this.getMembersArray()).length, j = 0; j < length2; ++j) {
                    L1PcInstance pc2 = membersArray2[j];
                    int rndx = Random.nextInt(4);
                    int rndy = Random.nextInt(4);
                    int locx = 33503 + rndx;
                    int locy = 32764 + rndy;
                    short mapid = 4;
                    L1Teleport.teleport(pc2, locx, locy, mapid, 5, true);
                    L1UltimateBattle.this.removeMember(pc2);
                }
                L1UltimateBattle.this.clearColosseum();
                L1UltimateBattle.this.setActive(false);
                L1UltimateBattle.this.setNowUb(false);
            }
            catch (Exception e) {
                L1UltimateBattle._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }
}
