package com.lineage.server.datatables;

import com.lineage.server.world.WorldCrock;
import com.lineage.server.model.Instance.L1CrockInstance;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.S_BlueMessage;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1PcInstance;
import com.add.L1Config_other;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.model.L1Location;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;

public class ServerCrockTable
{
    private static final Log _log;
    private static boolean STATUS;
    private static boolean STATUS_KILLBOSS;
    private static Timestamp _start_time;
    private static Timestamp _end_time;
    private static Timestamp _kill_time;
    private static final int _teleport_time = 150;
    private static int _MAP_TYPE;//活動地圖輪替（0=THEBES，1=TIKAL）。
    private static int[] _THEBES_MAPID;
    private static int[] _TIKAL_MAPID;
    private static int[][] _CROCK_MAPIDS;
    private boolean _isTeleport;//是否允許進入 BOSS 房（到時間才 true）。
    private static final int[][] CROCK_LOC;//世界地圖上入口 NPC 可能出現的座標清單（隨機抽一個）。
    private static L1Location CROCK_LOC_OPEN;
    private static final Random _random;
    private static ServerCrockTable _instance;
    
    static {
        _log = LogFactory.getLog(ServerCrockTable.class);
        ServerCrockTable.STATUS = false;//活動是否已開啟（入口可見、可傳送）。
        ServerCrockTable.STATUS_KILLBOSS = false;//是否已擊殺 BOSS。
        ServerCrockTable._start_time = null;
        ServerCrockTable._end_time = null;
        ServerCrockTable._kill_time = null;
        ServerCrockTable._MAP_TYPE = 0;
        ServerCrockTable._THEBES_MAPID = new int[] { 780, 781, 782 };
        ServerCrockTable._TIKAL_MAPID = new int[] { 783, 784 };
        ServerCrockTable._CROCK_MAPIDS = new int[][] { ServerCrockTable._THEBES_MAPID, ServerCrockTable._TIKAL_MAPID };
        CROCK_LOC = new int[][] { { 33647, 32294, 4}, { 33650, 32286, 4}, { 32618, 32796, 4}, { 33427, 32813, 4}, { 32743, 32445, 4}, { 33077, 32336, 4}, { 33605, 33233, 4} };
        ServerCrockTable.CROCK_LOC_OPEN = null;
        _random = new Random();
    }
    
    public static ServerCrockTable get() {
        if (ServerCrockTable._instance == null) {
            ServerCrockTable._instance = new ServerCrockTable();
        }
        return ServerCrockTable._instance;
    }
    
    private ServerCrockTable() {
        this._isTeleport = false;
        this.load();
    }
    
    private void load() {
        long time = System.currentTimeMillis();
        Timestamp nowtime = new Timestamp(time);
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_crock`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                ServerCrockTable._start_time = rs.getTimestamp("start_time");
                ServerCrockTable._end_time = rs.getTimestamp("end_time");
                int open_type = rs.getInt("open_type");
                if (open_type >= 0) {
                    ServerCrockTable._MAP_TYPE = open_type;
                }
                ServerCrockTable._kill_time = rs.getTimestamp("kill_time");
                int kill_type = rs.getInt("kill_type");
                if (ServerCrockTable._start_time != null && ServerCrockTable._start_time.before(nowtime)) {
                    this.addStartTime(true);
                    break;
                }
                else if (ServerCrockTable._kill_time != null) {
                    if (ServerCrockTable._kill_time.before(nowtime)) {
                        this.updateKillTime(null);
                        ServerCrockTable._kill_time = null;
                        long starttime = ServerCrockTable._start_time.getTime() - time;
                        ServerCrockTable.open_crock open = new ServerCrockTable.open_crock(starttime);
                        open.start();
                    }
                    else {
                        continue;
                    }
                }
                else {
                    long starttime = ServerCrockTable._start_time.getTime() - time;
                    ServerCrockTable.open_crock open = new ServerCrockTable.open_crock(starttime);
                    open.start();
                }
            }
        }
        catch (SQLException e) {
            ServerCrockTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        if (ServerCrockTable._start_time == null) {
            this.addStartTime(false);
            ServerCrockTable._log.info("載入時空裂痕開啟/結束:" + ServerCrockTable._start_time + "/" + ServerCrockTable._end_time);
        }
    }
    
    private void addStartTime(boolean update) {
        Calendar start = Calendar.getInstance();
        if (start.get(11) >= 19) {
            start.add(5, 1);
        }
        start.set(11, 19);
        start.set(12, 0);
        start.set(13, 0);
        Timestamp start_time = new Timestamp(start.getTimeInMillis());
        Calendar end = Calendar.getInstance();
        if (start.get(11) >= 19) {
            end.add(5, 1);
        }
        end.set(11, 22);
        end.set(12, 0);
        end.set(13, 0);
        Timestamp end_time = new Timestamp(end.getTimeInMillis());
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            if (update) {
                ps = co.prepareStatement("UPDATE `server_crock` SET `start_time`=?,`end_time`=?, `open_type`=?, `kill_time`=?");
            }
            else {
                ps = co.prepareStatement("INSERT INTO `server_crock` SET `start_time`=?,`end_time`=?, `open_type`=?, `kill_time`=?");
            }
            ps.setTimestamp(1, start_time);
            ps.setTimestamp(2, end_time);
            ps.setInt(3, 0);
            ps.setTimestamp(4, null);
            ps.execute();
        }
        catch (SQLException e) {
            ServerCrockTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        this.load();
        ServerCrockTable._log.info("加入時空裂痕時間");
    }
    
    private void updateStartTime() {
        Calendar start = Calendar.getInstance();
        start.add(5, L1Config_other._4046);
        start.set(11, 19);
        start.set(12, 0);
        start.set(13, 0);
        Timestamp start_time = new Timestamp(start.getTimeInMillis());
        Calendar end = Calendar.getInstance();
        end.add(5, L1Config_other._4046);
        end.set(11, 22);
        end.set(12, 0);
        end.set(13, 0);
        Timestamp end_time = new Timestamp(end.getTimeInMillis());
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("UPDATE `server_crock` SET `start_time`=?, `end_time`=?, `open_type`=?, `kill_time`=?");
            ps.setTimestamp(1, start_time);
            ps.setTimestamp(2, end_time);
            if (ServerCrockTable._MAP_TYPE == 0) {
                ps.setInt(3, 1);
            }
            else {
                ps.setInt(3, 0);
            }
            ps.setTimestamp(4, null);
            ps.execute();
        }
        catch (SQLException e) {
            ServerCrockTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        ServerCrockTable._log.info("更新時空裂痕時間");
    }
    
    private void updateKillTime(Timestamp kill_time) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("UPDATE `server_crock` SET `kill_time`=?, `kill_type`=?");
            ps.setTimestamp(1, kill_time);
            ps.setInt(2, ServerCrockTable._MAP_TYPE);
            ps.execute();
        }
        catch (SQLException e) {
            ServerCrockTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    public void updateKillTime() {
        if (!ServerCrockTable.STATUS) {
            return;
        }
        else {
            ServerCrockTable.STATUS_KILLBOSS = true;
            Timestamp kill_time = new Timestamp(System.currentTimeMillis() + 86400000L);
            this.updateKillTime(ServerCrockTable._kill_time = kill_time);
            if (ServerCrockTable._MAP_TYPE == 0) {
                this.Message(1474);
                this.Message(1475);
            }
            else if (ServerCrockTable._MAP_TYPE == 1) {
                this.Message(1488);
                this.Message(1489);
            }
            return;
        }
    }
    
    public boolean checkTeleport(int locX, int locY, int mapId, L1PcInstance pc) {
        if (ServerCrockTable.STATUS && Math.abs(ServerCrockTable.CROCK_LOC_OPEN.getX() - locX) <= 2 && Math.abs(ServerCrockTable.CROCK_LOC_OPEN.getY() - locY) <= 2 && mapId == ServerCrockTable.CROCK_LOC_OPEN.getMapId()) {
            int[][] locx = { { 32647, 32863, 780 }, { 32817, 32739, 783 } };
            int[] loc = locx[ServerCrockTable._MAP_TYPE];
            int newX = loc[0];
            int newY = loc[1];
            short newMap = (short)loc[2];
            pc.setSkillEffect(78, 2000);
            pc.stopHpRegeneration();
            pc.stopMpRegeneration();
            L1Teleport.teleport(pc, newX, newY, newMap, pc.getHeading(), false);
            return true;
        }
        else {
            return false;
        }
    }
    
    public void loginMessage(L1PcInstance pc) {
        if (ServerCrockTable.STATUS) {
            pc.sendPackets(new S_ServerMessage(1466));
        }
    }
    
    public boolean checkBoss() {
        return !ServerCrockTable.STATUS_KILLBOSS && this._isTeleport;
    }
    
    private void Message(int msg_id) {
        Collection<L1PcInstance> allpc = World.get().getAllPlayers();
        if (allpc.isEmpty()) {
            return;
        }
        else {
            for (L1PcInstance pc : allpc) {
                int mapId = pc.getMapId();
                int[] array;
                for (int length = (array = ServerCrockTable._CROCK_MAPIDS[ServerCrockTable._MAP_TYPE]).length, j = 0; j < length; ++j) {
                    int i = array[j];
                    if (mapId == i) {
                        pc.sendPackets(new S_ServerMessage(msg_id));
                    }
                }
            }
            return;
        }
    }
    
    private void teleport() {
        Collection<L1PcInstance> allpc = World.get().getAllPlayers();
        if (allpc.isEmpty()) {
            return;
        }
        else {
            for (L1PcInstance pc : allpc) {
                int mapId = pc.getMapId();
                if (mapId >= 780 && mapId <= 784) {
                    L1Teleport.teleport(pc, 33973, 33245, (short)4, 4, true);
                }
            }
            return;
        }
    }
    
    public void kill_boss_teleport() {
        try {
            World.get().broadcastPacketToAll(new S_BlueMessage(1470));
            Thread.sleep(5000L);
            Collection<L1PcInstance> allpc = World.get().getAllPlayers();
            if (allpc.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance pc : allpc) {
                    int mapId = pc.getMapId();
                    if (mapId >= 780 && mapId <= 784) {
                        World.get().broadcastPacketToAll(new S_BlueMessage(1478));
                        Thread.sleep(5000L);
                        World.get().broadcastPacketToAll(new S_BlueMessage(1480));
                        Thread.sleep(1000L);
                        World.get().broadcastPacketToAll(new S_BlueMessage(1481));
                        Thread.sleep(1000L);
                        World.get().broadcastPacketToAll(new S_BlueMessage(1482));
                        Thread.sleep(1000L);
                        World.get().broadcastPacketToAll(new S_BlueMessage(1483));
                        Thread.sleep(1000L);
                        World.get().broadcastPacketToAll(new S_BlueMessage(1484));
                        Thread.sleep(1000L);
                        L1Teleport.teleport(pc, 33973, 33245, (short)4, 4, true);
                    }
                }
            }
        }
        catch (Exception e) {
            ServerCrockTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /*
    static void access$1(boolean status) {
        ServerCrockTable.STATUS = status;
    }
    */
    
    /*
    static void access$4(L1Location crock_LOC_OPEN) {
        ServerCrockTable.CROCK_LOC_OPEN = crock_LOC_OPEN;
    }
    */
    
    /*
    static void access$13(ServerCrockTable serverCrockTable, boolean isTeleport) {
        serverCrockTable._isTeleport = isTeleport;
    }
    */
    
    private class open_crock implements Runnable
    {
        private long _delay;
        
        public open_crock(long delay) {
            this._delay = delay;
        }
        
        private void start() {
            GeneralThreadPool.get().schedule(this, this._delay);
        }
        
        @Override
        public void run() {
            try {
                ServerCrockTable.this.updateStartTime();
                ServerCrockTable.STATUS = true;
                int[] r = ServerCrockTable.CROCK_LOC[ServerCrockTable._random.nextInt(ServerCrockTable.CROCK_LOC.length)];
                L1Location loc = new L1Location(r[0], r[1], r[2]);
                ServerCrockTable.CROCK_LOC_OPEN = loc;
                ServerCrockTable._log.info("時空裂痕開啟座標:" + r[0] + "," + r[1]);
                L1CrockInstance crock = L1SpawnUtil.spawn(loc);
                if (crock != null) {
                    World.get().broadcastPacketToAll(new S_ServerMessage(1469));
                    if (ServerCrockTable._end_time != null) {
                        long time = System.currentTimeMillis();
                        long endtime = ServerCrockTable._end_time.getTime() - time;
                        ServerCrockTable.close_crock close = new ServerCrockTable.close_crock(endtime);
                        close.start();
                    }
                    long teleporttime = 9000000L;
                    ServerCrockTable.open_bossRoom teleport = new ServerCrockTable.open_bossRoom(teleporttime);
                    teleport.start();
                }
            }
            catch (Exception e) {
                ServerCrockTable._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
    
    private class close_crock implements Runnable
    {
        private long _delay;
        
        public close_crock(long delay) {
            this._delay = delay;
        }
        
        private void start() {
            GeneralThreadPool.get().schedule(this, this._delay);
        }
        
        @Override
        public void run() {
            try {
                if (ServerCrockTable._kill_time == null) {
                    World.get().broadcastPacketToAll(new S_ServerMessage(1467));
                    Thread.sleep(2000L);
                    ServerCrockTable.this.Message(1478);
                    Thread.sleep(4000L);
                    if (!ServerCrockTable.STATUS_KILLBOSS) {
                        if (ServerCrockTable._MAP_TYPE == 0) {
                            ServerCrockTable.this.Message(1479);
                        }
                        else if (ServerCrockTable._MAP_TYPE == 1) {
                            ServerCrockTable.this.Message(1490);
                        }
                    }
                    else {
                        ServerCrockTable.this.updateKillTime(null);
                    }
                    Thread.sleep(1000L);
                    ServerCrockTable.this.Message(1480);
                    Thread.sleep(1000L);
                    ServerCrockTable.this.Message(1481);
                    Thread.sleep(1000L);
                    ServerCrockTable.this.Message(1482);
                    Thread.sleep(1000L);
                    ServerCrockTable.this.Message(1483);
                    Thread.sleep(1000L);
                    ServerCrockTable.this.Message(1484);
                    Thread.sleep(1000L);
                    ServerCrockTable.this.teleport();
                    Thread.sleep(1500L);
                    Collection<L1CrockInstance> allCrock = WorldCrock.get().all();
                    if (allCrock.isEmpty()) {
                        return;
                    }
                    else {
                        for (L1CrockInstance crock : allCrock) {
                            crock.deleteMe();
                        }
                        World.get().broadcastPacketToAll(new S_ServerMessage(1468));
                        ServerCrockTable.STATUS = false;
                    }
                }
                ServerCrockTable.this._isTeleport = false;
            }
            catch (Exception e) {
                ServerCrockTable._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
    
    private class open_bossRoom implements Runnable
    {
        private long _delay;
        
        public open_bossRoom(long delay) {
            this._delay = delay;
        }
        
        private void start() {
            GeneralThreadPool.get().schedule(this, this._delay);
        }
        
        @Override
        public void run() {
            try {
                ServerCrockTable.this._isTeleport = true;
            }
            catch (Exception e) {
                ServerCrockTable._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
