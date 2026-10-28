package com.lineage.data.npc.other;

import com.lineage.server.IdFactoryNpc;
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
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class exp_game9
{
    private final ArrayList<L1PcInstance> _members;
    private static exp_game9 _instance;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_READY = 1;
    public static final int STATUS_PLAYING = 2;
    public static final int STATUS_CLEANING = 4;
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
    private int _DailyGameTime9;
    
    static {
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
    
    public exp_game9() {
        this._members = new ArrayList();
        this._targetNpc = null;
        this._TextTaskStatus = 0;
        this._DailyGameTime9 = 0;
    }
    
    public static exp_game9 getInstance() {
        if (exp_game9._instance == null) {
            exp_game9._instance = new exp_game9();
        }
        return exp_game9._instance;
    }
    
    public String enterTextTask(L1PcInstance pc) {
        if (pc.getLevel() < 85 && !this.isMember(pc)) {
            pc.sendPackets(new S_SystemMessage("請依照自身等級進入經驗副本"));
            return "";
        }
        else if (pc.getLevel() > 94 && !this.isMember(pc)) {
            pc.sendPackets(new S_SystemMessage("請依照自身等級進入經驗副本"));
            return "";
        }
        else if (pc.getQuest().get_step(8592) >= 3) {
            pc.sendPackets(new S_SystemMessage("今日已達上限次數"));
            return "";
        }
        else if (getInstance().getTextTaskStatus() == 4) {
            pc.sendPackets(new S_SystemMessage("此經驗副本清潔時間1分鐘 請稍候"));
            return "";
        }
        else if (getInstance().getTextTaskStatus() == 2) {
            pc.sendPackets(new S_SystemMessage("此經驗副本已啟動.剩餘時間" + this.getDailyGameTime9() + "秒"));
            return "";
        }
        else if (!pc.getInventory().checkItem(40308, 500000L)) {
            pc.sendPackets(new S_ServerMessage(189));
            return "";
        }
        else {
            if (pc.getQuest().get_step(8592) == 0) {
                pc.getQuest().set_step(8592, 1);
            }
            else if (pc.getQuest().get_step(8592) == 1) {
                pc.getQuest().set_step(8592, 2);
            }
            else if (pc.getQuest().get_step(8592) == 2) {
                pc.getQuest().set_step(8592, 3);
            }
            L1Teleport.teleport(pc, 32768, 32773, (short)5067, pc.getHeading(), true);
            this.addMember(pc);
            return "";
        }
    }
    
    private void addMember(L1PcInstance pc) {
        if (!this._members.contains(pc)) {
            this._members.add(pc);
            pc.getInventory().consumeItem(40308, 500000L);
        }
        if (this.getMembersCount() == 1 && this.getTextTaskStatus() == 0) {
            GeneralThreadPool.get().execute(new exp_game9.runTextTask());
        }
    }
    
    private void endTextTask() {
        this.setTextTaskStatus(4);
        this.sendMessage("經驗副本時間到達.已關閉");
        L1PcInstance[] membersArray;
        for (int length = (membersArray = this.getMembersArray()).length, i = 0; i < length; ++i) {
            L1PcInstance pc = membersArray[i];
            if (pc.getMapId() == 5067) {
                L1Teleport.teleport(pc, 33442, 32797, (short)4, pc.getHeading(), true);
            }
        }
        this.clearMembers();
        this.clearColosseum();
    }
    
    private void clearColosseum() {
        for (com.lineage.server.model.L1Object object36 : World.get().getVisibleObjects(5067).values()) {
            Object obj = object36;
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
            this.sendMessage("第 " + msg1 + " 關 [" + this.getBossName(npcid) + "] 30秒後開始第 " + msg2 + " 關");
        }
        else if (msg1.equalsIgnoreCase("10")) {
            this.sendMessage("最後一關 [" + this.getBossName(npcid) + "] 請努力撐下去，1分鐘後將關閉經驗副本");
        }
        else {
            this.sendMessage("第 " + msg1 + " 關 [" + this.getBossName(npcid) + "] 15秒後開始第 " + msg2 + " 關");
        }
        this.spawn(npcid);
    }
    
    private void spawn(int npcid) {
        try {
            L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            npc.setId(IdFactory.get().nextId());
            npc.setMap((short)5067);
            npc.setX(32701 + RandomArrayList.getInt(7));
            npc.setY(32894 + RandomArrayList.getInt(7));
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
            if (pc.getMapId() == 5067) {
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
    
    private void spawn(int npcId, int x, int y, int heading, short mapid) {
        try {
            L1NpcInstance npc = NpcTable.get().newNpcInstance(npcId);
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(mapid);
            npc.setX(x);
            npc.setY(y);
            npc.setHeading(heading);
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            npc.startChat(0);
        }
        catch (Exception ex) {}
    }
    
    private void setDailyGameTime9(int i) {
        this._DailyGameTime9 = i;
    }
    
    public int getDailyGameTime9() {
        return this._DailyGameTime9;
    }
    
    private class runTextTask implements Runnable
    {
        @Override
        public void run() {
            try {
                exp_game9.this.sendMessage("經驗副本倒數5秒後開始!!");
                exp_game9.this.setTextTaskStatus(2);
                GeneralThreadPool.get().execute(new exp_game9.TimeUpdater9());
                exp_game9.this.sendMessage("經驗副本啟動-5秒");
                Thread.sleep(1000L);
                exp_game9.this.sendMessage("經驗副本啟動-4秒");
                Thread.sleep(1000L);
                exp_game9.this.sendMessage("經驗副本啟動-3秒");
                Thread.sleep(1000L);
                exp_game9.this.sendMessage("經驗副本啟動-2秒");
                Thread.sleep(1000L);
                exp_game9.this.sendMessage("經驗副本啟動-1秒");
                Thread.sleep(1000L);
                exp_game9.this.sendMessage("***經驗副本已啟動***");
                exp_game9.this.setTextTaskStatus(2);
                Thread.sleep(2000L);
                short mapId = 5067;
                int x1 = 32759;
                int y1 = 32765;
                int x2 = 32764;
                int y2 = 32765;
                int x3 = 32771;
                int y3 = 32765;
                int x4 = 32777;
                int y4 = 32765;
                int x5 = 32779;
                int y5 = 32773;
                int x6 = 32772;
                int y6 = 32773;
                int x7 = 32764;
                int y7 = 32773;
                int x8 = 32757;
                int y8 = 32773;
                int x9 = 32758;
                int y9 = 32781;
                int x10 = 32764;
                int y10 = 32781;
                int x11 = 32772;
                int y11 = 32781;
                int x12 = 32778;
                int y12 = 32781;
                exp_game9.this.spawn(81931, x1, y1, 4, mapId);
                exp_game9.this.spawn(81932, x2, y2, 4, mapId);
                exp_game9.this.spawn(81933, x3, y3, 4, mapId);
                exp_game9.this.spawn(81934, x4, y4, 4, mapId);
                exp_game9.this.spawn(81935, x5, y5, 4, mapId);
                exp_game9.this.spawn(81936, x6, y6, 4, mapId);
                exp_game9.this.spawn(81937, x7, y7, 4, mapId);
                exp_game9.this.spawn(81936, x8, y8, 4, mapId);
                exp_game9.this.spawn(81935, x9, y9, 4, mapId);
                exp_game9.this.spawn(81934, x10, y10, 4, mapId);
                exp_game9.this.spawn(81933, x11, y11, 4, mapId);
                exp_game9.this.spawn(81932, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("30秒後第二波怪物將會出現");
                Thread.sleep(30000L);
                exp_game9.this.spawn(81939, x1, y1, 4, mapId);
                exp_game9.this.spawn(81940, x2, y2, 4, mapId);
                exp_game9.this.spawn(81941, x3, y3, 4, mapId);
                exp_game9.this.spawn(81942, x4, y4, 4, mapId);
                exp_game9.this.spawn(81943, x5, y5, 4, mapId);
                exp_game9.this.spawn(81944, x6, y6, 4, mapId);
                exp_game9.this.spawn(81945, x7, y7, 4, mapId);
                exp_game9.this.spawn(81944, x8, y8, 4, mapId);
                exp_game9.this.spawn(81943, x9, y9, 4, mapId);
                exp_game9.this.spawn(81942, x10, y10, 4, mapId);
                exp_game9.this.spawn(81941, x11, y11, 4, mapId);
                exp_game9.this.spawn(81940, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("30秒後第三波怪物將會出現");
                Thread.sleep(30000L);
                exp_game9.this.spawn(81947, x1, y1, 4, mapId);
                exp_game9.this.spawn(81948, x2, y2, 4, mapId);
                exp_game9.this.spawn(81949, x3, y3, 4, mapId);
                exp_game9.this.spawn(81950, x4, y4, 4, mapId);
                exp_game9.this.spawn(81951, x5, y5, 4, mapId);
                exp_game9.this.spawn(81952, x6, y6, 4, mapId);
                exp_game9.this.spawn(81953, x7, y7, 4, mapId);
                exp_game9.this.spawn(81952, x8, y8, 4, mapId);
                exp_game9.this.spawn(81951, x9, y9, 4, mapId);
                exp_game9.this.spawn(81950, x10, y10, 4, mapId);
                exp_game9.this.spawn(81949, x11, y11, 4, mapId);
                exp_game9.this.spawn(81948, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("30秒後第四波怪物將會出現");
                Thread.sleep(30000L);
                exp_game9.this.spawn(81955, x1, y1, 4, mapId);
                exp_game9.this.spawn(81956, x2, y2, 4, mapId);
                exp_game9.this.spawn(81957, x3, y3, 4, mapId);
                exp_game9.this.spawn(81958, x4, y4, 4, mapId);
                exp_game9.this.spawn(81959, x5, y5, 4, mapId);
                exp_game9.this.spawn(81960, x6, y6, 4, mapId);
                exp_game9.this.spawn(81961, x7, y7, 4, mapId);
                exp_game9.this.spawn(81960, x8, y8, 4, mapId);
                exp_game9.this.spawn(81959, x9, y9, 4, mapId);
                exp_game9.this.spawn(81958, x10, y10, 4, mapId);
                exp_game9.this.spawn(81957, x11, y11, 4, mapId);
                exp_game9.this.spawn(81956, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("30秒後第五波怪物將會出現");
                Thread.sleep(15000L);
                exp_game9.this.spawn(81963, x1, y1, 4, mapId);
                exp_game9.this.spawn(81964, x2, y2, 4, mapId);
                exp_game9.this.spawn(81965, x3, y3, 4, mapId);
                exp_game9.this.spawn(81966, x4, y4, 4, mapId);
                exp_game9.this.spawn(81967, x5, y5, 4, mapId);
                exp_game9.this.spawn(81968, x6, y6, 4, mapId);
                exp_game9.this.spawn(81969, x7, y7, 4, mapId);
                exp_game9.this.spawn(81968, x8, y8, 4, mapId);
                exp_game9.this.spawn(81967, x9, y9, 4, mapId);
                exp_game9.this.spawn(81966, x10, y10, 4, mapId);
                exp_game9.this.spawn(81965, x11, y11, 4, mapId);
                exp_game9.this.spawn(81964, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("30秒後第六波怪物將會出現");
                Thread.sleep(30000L);
                exp_game9.this.spawn(81971, x1, y1, 4, mapId);
                exp_game9.this.spawn(81972, x2, y2, 4, mapId);
                exp_game9.this.spawn(81973, x3, y3, 4, mapId);
                exp_game9.this.spawn(81974, x4, y4, 4, mapId);
                exp_game9.this.spawn(81975, x5, y5, 4, mapId);
                exp_game9.this.spawn(81974, x6, y6, 4, mapId);
                exp_game9.this.spawn(81973, x7, y7, 4, mapId);
                exp_game9.this.spawn(81972, x8, y8, 4, mapId);
                exp_game9.this.spawn(81971, x9, y9, 4, mapId);
                exp_game9.this.spawn(81974, x10, y10, 4, mapId);
                exp_game9.this.spawn(81973, x11, y11, 4, mapId);
                exp_game9.this.spawn(81972, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("30秒後第七波怪物將會出現");
                Thread.sleep(30000L);
                exp_game9.this.spawn(81977, x1, y1, 4, mapId);
                exp_game9.this.spawn(81978, x2, y2, 4, mapId);
                exp_game9.this.spawn(81979, x3, y3, 4, mapId);
                exp_game9.this.spawn(81980, x4, y4, 4, mapId);
                exp_game9.this.spawn(81981, x5, y5, 4, mapId);
                exp_game9.this.spawn(81982, x6, y6, 4, mapId);
                exp_game9.this.spawn(81983, x7, y7, 4, mapId);
                exp_game9.this.spawn(81982, x8, y8, 4, mapId);
                exp_game9.this.spawn(81981, x9, y9, 4, mapId);
                exp_game9.this.spawn(81980, x10, y10, 4, mapId);
                exp_game9.this.spawn(81979, x11, y11, 4, mapId);
                exp_game9.this.spawn(81978, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("30秒後第八波怪物將會出現");
                Thread.sleep(30000L);
                exp_game9.this.spawn(81985, x1, y1, 4, mapId);
                exp_game9.this.spawn(81986, x2, y2, 4, mapId);
                exp_game9.this.spawn(81987, x3, y3, 4, mapId);
                exp_game9.this.spawn(81988, x4, y4, 4, mapId);
                exp_game9.this.spawn(81989, x5, y5, 4, mapId);
                exp_game9.this.spawn(81990, x6, y6, 4, mapId);
                exp_game9.this.spawn(81991, x7, y7, 4, mapId);
                exp_game9.this.spawn(81992, x8, y8, 4, mapId);
                exp_game9.this.spawn(81990, x9, y9, 4, mapId);
                exp_game9.this.spawn(81989, x10, y10, 4, mapId);
                exp_game9.this.spawn(81988, x11, y11, 4, mapId);
                exp_game9.this.spawn(81987, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("30秒後第九波怪物將會出現");
                Thread.sleep(30000L);
                exp_game9.this.spawn(81994, x1, y1, 4, mapId);
                exp_game9.this.spawn(81995, x2, y2, 4, mapId);
                exp_game9.this.spawn(81996, x3, y3, 4, mapId);
                exp_game9.this.spawn(81997, x4, y4, 4, mapId);
                exp_game9.this.spawn(81998, x5, y5, 4, mapId);
                exp_game9.this.spawn(81999, x6, y6, 4, mapId);
                exp_game9.this.spawn(82000, x7, y7, 4, mapId);
                exp_game9.this.spawn(81999, x8, y8, 4, mapId);
                exp_game9.this.spawn(81998, x9, y9, 4, mapId);
                exp_game9.this.spawn(81997, x10, y10, 4, mapId);
                exp_game9.this.spawn(81996, x11, y11, 4, mapId);
                exp_game9.this.spawn(81995, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("60秒後第後一波怪物將會出現");
                Thread.sleep(60000L);
                exp_game9.this.spawn(82010, x1, y1, 4, mapId);
                exp_game9.this.spawn(82011, x2, y2, 4, mapId);
                exp_game9.this.spawn(82012, x3, y3, 4, mapId);
                exp_game9.this.spawn(82013, x4, y4, 4, mapId);
                exp_game9.this.spawn(82014, x5, y5, 4, mapId);
                exp_game9.this.spawn(82015, x6, y6, 4, mapId);
                exp_game9.this.spawn(82016, x7, y7, 4, mapId);
                exp_game9.this.spawn(82015, x8, y9, 4, mapId);
                exp_game9.this.spawn(82014, x9, y9, 4, mapId);
                exp_game9.this.spawn(82013, x10, y10, 4, mapId);
                exp_game9.this.spawn(82012, x11, y11, 4, mapId);
                exp_game9.this.spawn(82011, x12, y12, 4, mapId);
                exp_game9.this.sendMessage("120秒後將結束經驗副本.請多留意時間");
                Thread.sleep(120000L);
                exp_game9.this.endTextTask();
                Thread.sleep(2000L);
                exp_game9.this.setTextTaskStatus(0);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private class TimeUpdater9 implements Runnable
    {
        @Override
        public void run() {
            int _remnant = 480;
            while (_remnant > 0) {
                try {
                    exp_game9.this.setDailyGameTime9(_remnant);
                    Thread.sleep(1000L);
                    --_remnant;
                }
                catch (InterruptedException ex) {}
            }
        }
    }
}
