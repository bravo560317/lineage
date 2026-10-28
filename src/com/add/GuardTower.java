package com.add;

import com.lineage.server.timecontroller.server.ServerRestartTimer;
import com.lineage.server.model.TimeInform;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.TimerTask;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.L1Trade;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_GreenMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.utils.Random;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.serverpackets.S_HPMeter;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.config.ConfigGuardTower;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1MonsterInstance;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.Timer;
import org.apache.commons.logging.Log;

public class GuardTower
{
    private static final Log _log;
    private Timer _timeHandler;
    private ScheduledFuture<?> _timer;
    private static GuardTower instance;
    private static final Map<Integer, GuardTower> _guardtowerlist;
    private ArrayList<Integer> _guardtime;
    private int _id;
    private int _npcid;
    private int _count;
    private int _spawn_delay;
    private String _mags;
    static L1MonsterInstance Tower;
    private static final int STATUS_NONE = 0;
    private static final int STATUS_READY = 1;
    private static final int STATUS_PLAYING = 2;
    private static boolean SpawnStart;
    private static boolean MobAllDeath;
    private static final int TOWERID;
    private static final int TOWERX;
    private static final int TOWERY;
    private static final int MAXPC;
    private static final int MAPX;
    private static final int MAPY;
    private static final int MAPID;
    private static final int NEEDITEM;
    private static final int NEEDITEMCOUNT;
    private static final int GIFT;
    private static final int GIFT_Count;
    private static final int COMFORTGIFT;
    private static final int COMFORTGIFT_Count;
    private int _RoomStatus;
    
    static {
        _log = LogFactory.getLog(GuardTower.class);
        _guardtowerlist = new HashMap();
        GuardTower.Tower = null;
        GuardTower.SpawnStart = false;
        GuardTower.MobAllDeath = false;
        TOWERID = ConfigGuardTower.TOWERID;
        TOWERX = ConfigGuardTower.TOWERX;
        TOWERY = ConfigGuardTower.TOWERY;
        MAXPC = ConfigGuardTower.MAXPC;
        MAPX = ConfigGuardTower.MAPX;
        MAPY = ConfigGuardTower.MAPY;
        MAPID = ConfigGuardTower.MAPID;
        NEEDITEM = ConfigGuardTower.NEEDITEM;
        NEEDITEMCOUNT = ConfigGuardTower.NEEDITEMCOUNT;
        GIFT = ConfigGuardTower.GIFT;
        GIFT_Count = ConfigGuardTower.GIFT_Count;
        COMFORTGIFT = ConfigGuardTower.COMFORTGIFT;
        COMFORTGIFT_Count = ConfigGuardTower.ComfortGift_COUNT;
    }
    
    public GuardTower() {
        this._timeHandler = new Timer(true);
        this._guardtime = new ArrayList();
        this._RoomStatus = 0;
    }
    
    public static GuardTower get() {
        if (GuardTower.instance == null) {
            GuardTower.instance = new GuardTower();
        }
        return GuardTower.instance;
    }
    
    private int getSpawnDeldy() {
        return this._spawn_delay;
    }
    
    private void setSpawnDeldy(int i) {
        this._spawn_delay = i;
    }
    
    private int getCount() {
        return this._count;
    }
    
    private void setCount(int i) {
        this._count = i;
    }
    
    private int getId() {
        return this._id;
    }
    
    private void setId(int i) {
        this._id = i;
    }
    
    private int getNpcId() {
        return this._npcid;
    }
    
    private void setNpcId(int i) {
        this._npcid = i;
    }
    
    private String getMsg() {
        return this._mags;
    }
    
    private void setMsg(String s) {
        this._mags = s;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_守塔活動怪物設定`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                GuardTower Gt = new GuardTower();
                Gt.setId(rs.getInt("波次"));
                Gt.setNpcId(rs.getInt("Npc編號"));
                Gt.setCount(rs.getInt("數量"));
                Gt.setSpawnDeldy(rs.getInt("延遲幾秒"));
                Gt.setMsg(rs.getString("文字公告顯示"));
                GuardTower._guardtowerlist.put(Gt.getId(), Gt);
            }
        }
        catch (SQLException e) {
            GuardTower._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        GuardTower._log.info("讀取->守塔活動召喚資料數量: " + GuardTower._guardtowerlist.size() + "(" + timer.get() + "ms)");
    }
    
    public void LoadTime() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i = 0;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `系統_守塔活動時間設定`");
            rs = ps.executeQuery();
            while (rs.next()) {
                this._guardtime.add(rs.getInt("整點(24H)"));
                ++i;
            }
        }
        catch (SQLException e) {
            GuardTower._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        GuardTower._log.info("讀取->守塔活動時間軸: " + i + "(" + timer.get() + "ms)");
    }
    
    private ArrayList<Integer> GuardTimes() {
        return this._guardtime;
    }
    
    private void setRoomStatus(int i) {
        this._RoomStatus = i;
    }
    
    private int getRoomStatus() {
        return this._RoomStatus;
    }
    
    private void AllMessage(String msg) {
        for (com.lineage.server.model.L1Object object12 : World.get().getVisibleObjects(GuardTower.MAPID).values()) {
            Object obj = object12;
            if (obj instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)obj;
                if (pc != null && !pc.isGhost()) {
                    pc.sendPackets(new S_ServerMessage(new StringBuilder().append(msg).toString()));
                }
                else {
                    continue;
                }
            }
        }
    }
    
    private static int PcCount() {
        int PcCount = 0;
        for (com.lineage.server.model.L1Object object13 : World.get().getVisibleObjects(GuardTower.MAPID).values()) {
            Object obj = object13;
            if (obj instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)obj;
                if (pc != null && !pc.isDead() && !pc.isGhost()) {
                    if (GuardTower.Tower != null) {
                        pc.sendPackets(new S_HPMeter(GuardTower.Tower));
                    }
                    ++PcCount;
                }
                else {
                    continue;
                }
            }
        }
        return PcCount;
    }
    
    private static int MobCount() {
        int MobCount = 0;
        for (com.lineage.server.model.L1Object object14 : World.get().getVisibleObjects(GuardTower.MAPID).values()) {
            Object obj = object14;
            if (obj instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)obj;
                if (mob.getNpcId() == GuardTower.TOWERID) {
                    continue;
                }
                else {
                    ++MobCount;
                }
            }
        }
        return MobCount;
    }
    
    private static boolean TowerIsAlive() {
        for (com.lineage.server.model.L1Object object15 : World.get().getVisibleObjects(GuardTower.MAPID).values()) {
            Object obj = object15;
            if (obj instanceof L1MonsterInstance) {
                L1MonsterInstance npc = (L1MonsterInstance)obj;
                if (npc.getNpcId() == GuardTower.TOWERID && !npc.isDead()) {
                    return true;
                }
                else {
                    continue;
                }
            }
        }
        return false;
    }
    
    private void ClearRoom() {
        for (com.lineage.server.model.L1Object object16 : World.get().getVisibleObjects(GuardTower.MAPID).values()) {
            Object obj = object16;
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
            else if (obj instanceof L1NpcInstance) {
                L1NpcInstance mob2 = (L1NpcInstance)obj;
                if (!mob2.isDead()) {
                    mob2.setDead(true);
                    mob2.setStatus(8);
                    mob2.setCurrentHpDirect(0);
                    mob2.deleteMe();
                }
                else {
                    continue;
                }
            }
            else {
                continue;
            }
        }
    }
    
    private void StartTimer() {
        new GuardTower.BuyTicket().begin();
    }
    
    private void spawn(int npcid) {
        int X = 0;
        int Y = 0;
        L1MonsterInstance mob = new L1MonsterInstance(NpcTable.get().getTemplate(npcid));
        if (npcid == GuardTower.TOWERID) {
            X = GuardTower.TOWERX;
            Y = GuardTower.TOWERY;
            GuardTower.Tower = mob;
        }
        else {
            int index = Random.nextInt(ConfigGuardTower.X.size());
            X = ConfigGuardTower.X.get(index);
            Y = ConfigGuardTower.Y.get(index);
        }
        mob.setId(IdFactoryNpc.get().nextId());
        mob.setHeading(5);
        if (npcid == GuardTower.TOWERID) {
            mob.setTempCharGfx(9484);
            mob.setGfxId(9484);
        }
        mob.setX(X);
        mob.setY(Y);
        mob.setHomeX(mob.getX());
        mob.setHomeY(mob.getY());
        mob.setMap((short)GuardTower.MAPID);
        World.get().storeObject(mob);
        World.get().addVisibleObject(mob);
        S_NPCPack s_npcPack = new S_NPCPack(mob);
        for (L1PcInstance pc : World.get().getRecognizePlayer(mob)) {
            pc.addKnownObject(mob);
            mob.addKnownObject(pc);
            pc.sendPackets(s_npcPack);
        }
        mob.onNpcAI();
        mob.turnOnOffLight();
    }
    
    private void endRoom() {
        if (TowerIsAlive()) {
            for (com.lineage.server.model.L1Object object17 : World.get().getVisibleObjects(GuardTower.MAPID).values()) {
                Object obj = object17;
                if (obj instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)obj;
                    if (pc != null && !pc.isDead() && !pc.isGhost()) {
                        L1Teleport.teleport(pc, 33448, 32793, (short)4, pc.getHeading(), true);
                        if (GuardTower.GIFT != 0) {
                            pc.getInventory().storeItem(GuardTower.GIFT, (long)GuardTower.GIFT_Count);
                            L1Item item2 = ItemTable.get().getTemplate(GuardTower.GIFT);
                            pc.sendPackets(new S_ServerMessage("恭喜你獲得活動獎勵(" + item2.getName() + ")"));
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        else {
            for (Object obj : World.get().getVisibleObjects(GuardTower.MAPID).values()) {
                if (obj instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)obj;
                    if (pc != null && !pc.isDead() && !pc.isGhost()) {
                        L1Teleport.teleport(pc, 33448, 32793, (short)4, pc.getHeading(), true);
                        if (GuardTower.GIFT != 0) {
                            pc.getInventory().storeItem(GuardTower.COMFORTGIFT, (long)GuardTower.COMFORTGIFT_Count);
                            L1Item item2 = ItemTable.get().getTemplate(GuardTower.COMFORTGIFT);
                            pc.sendPackets(new S_ServerMessage("恭喜你獲得活動獎勵(" + item2.getName() + ")"));
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        World.get().broadcastPacketToAll(new S_SystemMessage("「守塔活動」已結束!歡迎各位下次再來挑戰..."));
        World.get().broadcastPacketToAll(new S_GreenMessage("\\f2「守塔活動」已結束!歡迎各位下次再來挑戰..."));
        this.ClearRoom();
    }
    
    public String enterRoom(L1PcInstance pc) {
        if (this.getRoomStatus() == 0) {
            pc.sendPackets(new S_SystemMessage("\\fT目前守塔活動尚未開放。"));
            return "";
        }
        else if (this.getRoomStatus() == 2) {
            pc.sendPackets(new S_SystemMessage("\\fT守塔活動已經開始。"));
            return "";
        }
        else if (PcCount() >= GuardTower.MAXPC) {
            pc.sendPackets(new S_SystemMessage("\\fT守塔活動參與人數已滿無法進入。"));
            return "";
        }
        else if (!pc.getInventory().checkItem(GuardTower.NEEDITEM, (long)GuardTower.NEEDITEMCOUNT)) {
            pc.sendPackets(new S_SystemMessage("\\fT您的參賽所需道具數量不足"));
            return "";
        }
        else {
            if (pc.isInParty()) {
                pc.getParty().leaveMember(pc);
            }
            if (pc.getTradeID() != 0) {
                L1Trade trade = new L1Trade();
                trade.tradeCancel(pc);
            }
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null && clan.getWarehouseUsingChar() == pc.getId()) {
                clan.setWarehouseUsingChar(0);
            }
            pc.getInventory().consumeItem(GuardTower.NEEDITEM, (long)GuardTower.NEEDITEMCOUNT);
            L1Teleport.teleport(pc, GuardTower.MAPX, GuardTower.MAPY, (short)GuardTower.MAPID, pc.getHeading(), true);
            return "";
        }
    }
    
    /*
    static void access$0(GuardTower guardTower, ScheduledFuture timer) {
        guardTower._timer = timer;
    }
    */
    
    /*
    static void access$15(boolean mobAllDeath) {
        GuardTower.MobAllDeath = mobAllDeath;
    }
    */
    
    /*
    static void access$22(boolean spawnStart) {
        GuardTower.SpawnStart = spawnStart;
    }
    */
    
    public class GuardTowerTime extends TimerTask
    {
        public void start() {
            int timeMillis = 60000;
            GuardTower.this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 60000L, 60000L);
        }
        
        @Override
        public void run() {
            try {
                String mTime = TimeInform.time().getNow_YMDHMS(3);
                String hTime = TimeInform.time().getNow_YMDHMS(4);
                int mm = Integer.parseInt(mTime);
                int hh = Integer.parseInt(hTime);
                Iterator iterator = GuardTower.this.GuardTimes().iterator();
                while (iterator.hasNext()) {
                    int Nowtime = (Integer)iterator.next();
                    if (Nowtime == hh && mm == 0) {
                        if (ServerRestartTimer.isRtartTime()) {
                            World.get().broadcastPacketToAll(new S_SystemMessage("\\fR由於伺服器即將維護重開，本次守塔活動暫不開放"));
                            World.get().broadcastPacketToAll(new S_GreenMessage("\\f2由於伺服器即將維護重開，本次守塔活動暫不開放"));
                            break;
                        }
                        else {
                            World.get().broadcastPacketToAll(new S_SystemMessage("\\fU「守塔活動」已開放報名參加，剩餘時間5分鐘!!!"));
                            World.get().broadcastPacketToAll(new S_GreenMessage("\\f2「守塔活動」已開放報名參加剩下時間5分鐘!!!"));
                            GuardTower.this.StartTimer();
                            break;
                        }
                    }
                }
            }
            catch (Exception e) {
                GuardTower._log.error("守塔活動時間軸異常,活動取消", e);
                GeneralThreadPool.get().cancel(GuardTower.this._timer, false);
            }
        }
    }
    
    private class BuyTicket extends TimerTask
    {
        @Override
        public void run() {
            GuardTower.this.setRoomStatus(2);
            World.get().broadcastPacketToAll(new S_SystemMessage("\\fT「守塔活動」報名已結束!活動正式開始..."));
            World.get().broadcastPacketToAll(new S_GreenMessage("\\f2「守塔活動」報名已結束!活動正式開始..."));
            new GuardTower.ReciprocalTime().L1CrackTime();
        }
        
        private void begin() {
            GuardTower.this.setRoomStatus(1);
            Timer timer = new Timer();
            timer.schedule(this, 300000L);
        }
    }
    
    private class ReciprocalTime extends TimerTask
    {
        private int _startTime;
        
        private ReciprocalTime() {
            this._startTime = 0;
        }
        
        private void L1CrackTime() {
            GuardTower.this._timeHandler.schedule(this, 1000L, 1000L);
            GeneralThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            ++this._startTime;
            String msg = null;
            switch (this._startTime) {
                case 1: {
                    msg = "\\f3距離活動開始時間剩下10秒";
                    break;
                }
                case 6: {
                    msg = "5";
                    break;
                }
                case 7: {
                    msg = "4";
                    break;
                }
                case 8: {
                    msg = "3";
                    break;
                }
                case 9: {
                    msg = "2";
                    break;
                }
                case 10: {
                    if (PcCount() > 0) {
                        GuardTower.this.spawn(GuardTower.TOWERID);
                        for (com.lineage.server.model.L1Object object18 : World.get().getVisibleObjects(GuardTower.MAPID).values()) {
                            Object obj = object18;
                            if (obj instanceof L1PcInstance) {
                                L1PcInstance pc = (L1PcInstance)obj;
                                pc.sendPackets(new S_GreenMessage("活動開始"));
                                if (pc.getTradeID() != 0) {
                                    L1Trade trade = new L1Trade();
                                    trade.tradeCancel(pc);
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                        new GuardTower.PlayGame().GameTime();
                    }
                    else {
                        World.get().broadcastPacketToAll(new S_SystemMessage("守塔活動參加人數不足，本場取消。"));
                        GuardTower.this.setRoomStatus(0);
                    }
                    this.cancel();
                    break;
                }
            }
            if (msg != null) {
                GuardTower.this.AllMessage(msg);
            }
        }
    }
    
    private class SpawnMob extends TimerTask
    {
        private int _startTime;
        private int _rand;
        
        private SpawnMob() {
            this._startTime = 0;
            this._rand = 0;
        }
        
        private void SpawnTime() {
            GuardTower.this._timeHandler.schedule(this, 1000L, 1000L);
            GeneralThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            if (GuardTower.this.getRoomStatus() == 2) {
                int r = GuardTower._guardtowerlist.size() + 1;
                if (this._rand > r) {
                    if (MobCount() == 0) {
                        GuardTower.MobAllDeath = true;
                    }
                }
                else {
                    GuardTower Gt = GuardTower._guardtowerlist.get((Integer)this._rand);
                    if (Gt != null) {
                        if (Gt.getSpawnDeldy() != 0) {
                            ++this._startTime;
                            if (this._startTime == Gt.getSpawnDeldy()) {
                                if (Gt.getMsg() != null) {
                                    GuardTower.this.AllMessage(new StringBuilder().append(Gt.getMsg()).toString());
                                }
                                for (int i = 0; i < Gt.getCount(); ++i) {
                                    GuardTower.this.spawn(Gt.getNpcId());
                                }
                                this._startTime = 0;
                                ++this._rand;
                            }
                        }
                        else {
                            if (Gt.getMsg() != null) {
                                GuardTower.this.AllMessage(new StringBuilder().append(Gt.getMsg()).toString());
                            }
                            for (int i = 0; i < Gt.getCount(); ++i) {
                                GuardTower.this.spawn(Gt.getNpcId());
                            }
                            ++this._rand;
                        }
                    }
                    else {
                        ++this._rand;
                    }
                }
            }
            else {
                this.cancel();
            }
        }
    }
    
    private class PlayGame extends TimerTask
    {
        private int _startTime;
        
        private PlayGame() {
            this._startTime = 0;
        }
        
        private void GameTime() {
            GuardTower.this._timeHandler.schedule(this, 1000L, 1000L);
            GeneralThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            ++this._startTime;
            String msg = null;
            if (this._startTime == 2340) {
                msg = "\\fH距離活動結束時間剩下一分鐘 ...";
            }
            if (this._startTime == 2400) {
                msg = "\\fAH時間到 ...";
                new GuardTower.EndGame().GameTime();
                this.cancel();
            }
            else {
                if (TowerIsAlive()) {
                    if (PcCount() > 0) {
                        if (!GuardTower.SpawnStart) {
                            GuardTower.SpawnStart = true;
                            new GuardTower.SpawnMob().SpawnTime();
                        }
                    }
                    else {
                        msg = "\\fU全體參加者陣亡 ...";
                        new GuardTower.EndGame().GameTime();
                        this.cancel();
                    }
                }
                else {
                    msg = "\\fU守護者之塔死亡 ...";
                    new GuardTower.EndGame().GameTime();
                    this.cancel();
                }
                if (GuardTower.MobAllDeath) {
                    msg = "\\fU所有入侵者都已經被擊殺 ...";
                    new GuardTower.EndGame().GameTime();
                    this.cancel();
                }
                if (msg != null) {
                    GuardTower.this.AllMessage(msg);
                }
            }
        }
    }
    
    private class EndGame extends TimerTask
    {
        private int _startTime;
        
        private EndGame() {
            this._startTime = 0;
        }
        
        private void GameTime() {
            GuardTower.this._timeHandler.schedule(this, 1000L, 1000L);
            GeneralThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            GuardTower.this.setRoomStatus(0);
            GuardTower.SpawnStart = false;
            ++this._startTime;
            String msg = null;
            switch (this._startTime) {
                case 2: {
                    msg = "\\fU守塔活動結束 請稍等 ...";
                    break;
                }
                case 4: {
                    msg = "\\fU系統正在分配是否有獎勵...。";
                    break;
                }
                case 6: {
                    GuardTower.MobAllDeath = false;
                    GuardTower.this.endRoom();
                    this.cancel();
                    break;
                }
            }
            if (msg != null) {
                GuardTower.this.AllMessage(msg);
            }
        }
    }
}
