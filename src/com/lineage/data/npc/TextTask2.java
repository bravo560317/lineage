package com.lineage.data.npc;

import com.lineage.server.serverpackets.S_BlueMessage;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.IdFactory;
import com.lineage.server.datatables.NpcTable;
import java.util.Iterator;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_Weather;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_MapID;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class TextTask2
{
    private final ArrayList<L1PcInstance> _members;
    private static TextTask2 _instance;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_READY = 1;
    public static final int STATUS_PLAYING = 2;
    public static final int STATUS_CLEANING = 4;
    private static final int _minPlayer;
    private static final int _maxPlayer;
    private static final int _level;
    private static final int _bossId1;
    private static final int _bossId2;
    private static final int _bossId3;
    private static final int _bossId4;
    private static final int _bossId5;
    private static final int _bossId6;
    private static final int _bossId7;
    private static final int _bossId8;
    private static final int _bossId9;
    private static final int _bossId10;
    private static final int _adena;
    protected L1NpcInstance _targetNpc;
    private int _TextTaskStatus;
    
    static {
        _minPlayer = ConfigOther.TextMinPlayer;
        _maxPlayer = ConfigOther.TextMaxPlayer;
        _level = ConfigOther.TextLevel;
        _bossId1 = ConfigOther.Textnpa1;
        _bossId2 = ConfigOther.Textnpa2;
        _bossId3 = ConfigOther.Textnpa3;
        _bossId4 = ConfigOther.Textnpa4;
        _bossId5 = ConfigOther.Textnpa5;
        _bossId6 = ConfigOther.Textnpa6;
        _bossId7 = ConfigOther.Textnpa7;
        _bossId8 = ConfigOther.Textnpa8;
        _bossId9 = ConfigOther.Textnpa9;
        _bossId10 = ConfigOther.Textnpa10;
        _adena = ConfigOther.TextMoney;
    }
    
    public TextTask2() {
        this._members = new ArrayList();
        this._targetNpc = null;
        this._TextTaskStatus = 0;
    }
    
    public static TextTask2 getInstance() {
        if (TextTask2._instance == null) {
            TextTask2._instance = new TextTask2();
        }
        return TextTask2._instance;
    }
    
    public String enterTextTask(L1PcInstance pc) {
        if (pc.getLevel() < TextTask2._level && !this.isMember(pc)) {
            pc.sendPackets(new S_SystemMessage("你的等級不足，無法挑戰Boss武道館。"));
            return "";
        }
        else if (pc.getQuest().get_step(90558) == 1 && pc.getQuest().get_step(90559) == 0) {
            pc.sendPackets(new S_SystemMessage("您已經執行過上限1次。"));
            return "";
        }
        else if (pc.getQuest().get_step(90558) == 1 && pc.getQuest().get_step(90559) == 2) {
            pc.sendPackets(new S_SystemMessage("您今日無法在進入武道館了。"));
            return "";
        }
        else if (getInstance().getTextTaskStatus() == 4) {
            pc.sendPackets(new S_SystemMessage("目前Boss武道館的場地正在清潔中。"));
            return "";
        }
        else if (getInstance().getTextTaskStatus() == 2) {
            pc.sendPackets(new S_ServerMessage(1182));
            return "";
        }
        else if (this.getMembersCount() >= TextTask2._maxPlayer && !this.isMember(pc)) {
            pc.sendPackets(new S_SystemMessage("Boss武道館人數已滿，下次請早。"));
            return "";
        }
        else if (!pc.getInventory().checkItem(ConfigOther.tcheckitem, (long)TextTask2._adena)) {
            pc.sendPackets(new S_ServerMessage(189));
            return "";
        }
        else if ((pc.getInventory().checkItem(40089, 1L) || pc.getInventory().checkItem(140089, 1L)) && !this.isMember(pc)) {
            pc.sendPackets(new S_SystemMessage("請把復活卷軸給刪除，不然不給進入場地。"));
            return "";
        }
        else {
            L1Teleport.teleport(pc, 32701, 32894, (short)97, pc.getHeading(), true);
            this.addMember(pc);
            return "";
        }
    }
    
    private void addMember(L1PcInstance pc) {
        if (!this._members.contains(pc)) {
            this._members.add(pc);
            pc.getInventory().consumeItem(ConfigOther.tcheckitem, (long)TextTask2._adena);
        }
        if (this.getMembersCount() == ConfigOther.TextMinPlayer && this.getTextTaskStatus() == 0) {
            GeneralThreadPool.get().execute(new TextTask2.runTextTask());
            L1PcInstance[] membersArray;
            for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
                L1PcInstance pc2 = membersArray[i];
                if (pc2.getMapId() == 97) {
                    pc2.sendPackets(new S_SystemMessage("怪物強度-[簡單強度]。"));
                }
            }
        }
    }
    
    private boolean checkPlayerCount() {
        if (this.getMembersCount() < TextTask2._minPlayer) {
            this.setTextTaskStatus(4);
            this.sendMessage("人數不足 " + TextTask2._minPlayer + " 人，所以強制關閉遊戲");
            L1PcInstance[] membersArray;
            for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
                L1PcInstance pc = membersArray[i];
                pc.getInventory().storeItem(ConfigOther.tcheckitem, (long)TextTask2._adena);
                pc.sendPackets(new S_SystemMessage("退還金幣(" + TextTask2._adena + ")。"));
                L1Teleport.teleport(pc, 33442, 32797, (short)4, pc.getHeading(), true);
            }
            this.clearMembers();
            return false;
        }
        else {
            return true;
        }
    }
    
    private void endTextTask1() {
        this.setTextTaskStatus(4);
        this.sendMessage("Boss武道館挑戰失敗.請下次再來!!");
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            if (pc.getMapId() == 97) {
                L1Teleport.teleport(pc, 33442, 32797, (short)4, pc.getHeading(), true);
            }
        }
        this.clearMembers();
        this.clearColosseum();
    }
    
    private void endTextTask() {
        this.setTextTaskStatus(4);
        this.sendMessage("Boss武道館時間到了.請下次再來挑戰!!");
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            if (pc.getMapId() == 97) {
                L1Teleport.teleport(pc, 33442, 32797, (short)4, pc.getHeading(), true);
            }
            if (pc.isDead()) {
                this.restartPlayer(pc, 32616, 32782, (short)4);
            }
        }
        this.clearMembers();
        this.clearColosseum();
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
        pc.sendPackets(new S_SystemMessage("強制退出地圖,請自行登出重新進入遊戲即可!!"));
    }
    
    private void clearColosseum() {
        for (com.lineage.server.model.L1Object object39 : World.get().getVisibleObjects(97).values()) {
            Object obj = object39;
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
    
    private void spawnBoss(int npcid, String msg1, String msg2) {
        if (msg1.equalsIgnoreCase("9")) {
            this.sendMessage("第 " + msg1 + " 關 [" + this.getBossName(npcid) + "] 120秒後開始第 " + msg2 + " 關");
        }
        else if (msg1.equalsIgnoreCase("10")) {
            this.sendMessage("最後一關 [" + this.getBossName(npcid) + "] 請努力撐下去");
        }
        else {
            this.sendMessage("第 " + msg1 + " 關 [" + this.getBossName(npcid) + "] 60秒後開始第 " + msg2 + " 關");
        }
        this.spawn(npcid);
    }
    
    private void spawn(int npcid) {
        try {
            L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            npc.setId(IdFactory.get().nextId());
            npc.setMap((short)97);
            npc.setX(32701 + RandomArrayList.getInt(5));
            npc.setY(32894 + RandomArrayList.getInt(5));
            Thread.sleep(1L);
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(4);
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            npc.startChat(0);
        }
        catch (Exception ex) {}
    }
    
    private String getBossName(int npcId) {
        String BossName = null;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select name from npc where npcid = ?");
            pstm.setInt(1, npcId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                BossName = rs.getString("name");
            }
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(rs, pstm, con);
        }
        return BossName;
    }
    
    private void sendMessage(String msg) {
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            if (pc.getMapId() == 97) {
                pc.sendPackets(new S_BlueMessage(166, "\\f3" + msg));
            }
        }
    }
    
    private void setTextTaskStatus(int i) {
        this._TextTaskStatus = i;
    }
    
    private int getTextTaskStatus() {
        return this._TextTaskStatus;
    }
    
    private void clearMembers() {
        this._members.clear();
    }
    
    private boolean isMember(L1PcInstance pc) {
        return this._members.contains(pc);
    }
    
    private L1PcInstance[] getMembersArray() {
        return (L1PcInstance[])this._members.toArray(new L1PcInstance[this._members.size()]);
    }
    
    private int getMembersCount() {
        return this._members.size();
    }
    
    private class runTextTask implements Runnable
    {
        @Override
        public void run() {
            try {
                TextTask2.this.sendMessage("Boss武道館即將開始，請勿離開場地或登出。");
                Thread.sleep(10000L);
                TextTask2.this.setTextTaskStatus(1);
                TextTask2.this.sendMessage("60秒後開始遊戲");
                Thread.sleep(55000L);
                TextTask2.this.sendMessage("倒數5秒");
                Thread.sleep(1000L);
                TextTask2.this.sendMessage("4秒");
                Thread.sleep(1000L);
                TextTask2.this.sendMessage("3秒");
                Thread.sleep(1000L);
                TextTask2.this.sendMessage("2秒");
                Thread.sleep(1000L);
                TextTask2.this.sendMessage("1秒");
                Thread.sleep(1000L);
                if (TextTask2.this.checkPlayerCount()) {
                    TextTask2.this.sendMessage("Boss武道館怪物即將登場。願上帝保佑你！");
                    TextTask2.this.setTextTaskStatus(2);
                    L1PcInstance[] access$3;
                    for (int length = (access$3 = TextTask2.this.getMembersArray()).length, i = 0; i < length; ++i) {
                        L1PcInstance pc1 = access$3[i];
                        if (pc1.getMapId() == 97) {
                            pc1.getQuest().set_step(90558, 1);
                            if (pc1.getQuest().get_step(90559) == 1) {
                                pc1.getQuest().set_step(90559, 2);
                            }
                        }
                    }
                    TextTask2.this.spawnBoss(TextTask2._bossId1, "1", "2");
                    Thread.sleep(60000L);
                    TextTask2.this.spawnBoss(TextTask2._bossId2, "2", "3");
                    Thread.sleep(60000L);
                    TextTask2.this.spawnBoss(TextTask2._bossId3, "3", "4");
                    Thread.sleep(60000L);
                    TextTask2.this.spawnBoss(TextTask2._bossId4, "4", "5");
                    Thread.sleep(60000L);
                    TextTask2.this.spawnBoss(TextTask2._bossId5, "5", "6");
                    Thread.sleep(60000L);
                    TextTask2.this.spawnBoss(TextTask2._bossId6, "6", "7");
                    Thread.sleep(60000L);
                    TextTask2.this.spawnBoss(TextTask2._bossId7, "7", "8");
                    Thread.sleep(60000L);
                    TextTask2.this.spawnBoss(TextTask2._bossId8, "8", "9");
                    Thread.sleep(60000L);
                    TextTask2.this.spawnBoss(TextTask2._bossId9, "9", "10");
                    Thread.sleep(120000L);
                    TextTask2.this.spawnBoss(TextTask2._bossId10, "10", null);
                    Thread.sleep(180000L);
                    TextTask2.this.endTextTask();
                }
                Thread.sleep(5000L);
                TextTask2.this.setTextTaskStatus(0);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
