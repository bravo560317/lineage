package com.lineage.server.timecontroller.server;

import com.lineage.server.serverpackets.S_PacketBox;
import java.util.List;
import com.lineage.server.model.L1Clan;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.datatables.CastleWarGiftTable;
import com.lineage.server.model.Instance.L1CatapultInstance;
import com.lineage.server.model.Instance.L1TowerInstance;
import com.lineage.server.model.Instance.L1CrownInstance;
import com.lineage.server.model.Instance.L1FieldObjectInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.WorldClan;
import com.lineage.server.world.World;
import java.text.SimpleDateFormat;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.datatables.DoorSpawnTable;
import com.lineage.server.model.L1WarSpawn;
import com.lineage.server.datatables.lock.CharOtherReading;
import com.lineage.server.serverpackets.S_PacketBoxWar;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.TimeZone;
import com.lineage.config.Config;
import com.eric.StartCheckWarTime;
import com.lineage.config.ConfigAlt;
import com.lineage.server.datatables.lock.CastleReading;
import org.apache.commons.logging.LogFactory;
import java.util.Calendar;
import com.lineage.server.templates.L1Castle;
import org.apache.commons.logging.Log;

public class ServerWarExecutor
{
    private static final Log _log;
    private static ServerWarExecutor _instance;
    private L1Castle[] _l1castle;
    private Calendar[] _war_start_time;
    private Calendar[] _war_end_time;
    private boolean[] _is_now_war;
    private String[] _castleName;
    
    static {
        _log = LogFactory.getLog(ServerWarExecutor.class);
    }
    
    private ServerWarExecutor() {
        this._l1castle = new L1Castle[8];
        this._war_start_time = new Calendar[8];
        this._war_end_time = new Calendar[8];
        this._is_now_war = new boolean[8];
        this._castleName = new String[] { "肯特", "妖魔", "風木", "奇岩", "海音", "侏儒", "亞丁", "狄亞得要塞" };
        for (int i = 0; i < this._l1castle.length; ++i) {
            this._l1castle[i] = CastleReading.get().getCastleTable(i + 1);
            Calendar nowTime = this.getRealTime();
            Calendar oldTime = this.getRealTime();
            oldTime.setTime(this._l1castle[i].getWarTime().getTime());
            this._war_start_time[i] = this._l1castle[i].getWarTime();
            oldTime.add(ConfigAlt.ALT_WAR_TIME_UNIT, ConfigAlt.ALT_WAR_TIME);
            if (StartCheckWarTime.getInstance().isActive(this._l1castle[i].getId()) && nowTime.after(oldTime)) {
                if (i == 1) {
                    nowTime.add(ConfigAlt.ALT_WAR_INTERVAL_UNIT, ConfigAlt.ALT_WAR_INTERVAL - 3);
                }
                else {
                    nowTime.add(ConfigAlt.ALT_WAR_INTERVAL_UNIT, ConfigAlt.ALT_WAR_INTERVAL);
                }
                this._l1castle[i].setWarTime(nowTime);
                CastleReading.get().updateCastle(this._l1castle[i]);
            }
            (this._war_end_time[i] = (Calendar)this._l1castle[i].getWarTime().clone()).add(ConfigAlt.ALT_WAR_TIME_UNIT, ConfigAlt.ALT_WAR_TIME);
        }
    }
    
    public static ServerWarExecutor get() {
        if (ServerWarExecutor._instance == null) {
            ServerWarExecutor._instance = new ServerWarExecutor();
        }
        return ServerWarExecutor._instance;
    }
    
    public Calendar getRealTime() {
        TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(_tz);
        return cal;
    }
    
    public boolean isNowWar(int castle_id) {
        return this._is_now_war[castle_id - 1];
    }
    
    public void setWarTime(int castle_id, Calendar calendar) {
        this._war_start_time[castle_id - 1] = (Calendar)calendar.clone();
    }
    
    public void setEndWarTime(int castle_id, Calendar calendar) {
        (this._war_end_time[castle_id - 1] = (Calendar)calendar.clone()).add(ConfigAlt.ALT_WAR_TIME_UNIT, ConfigAlt.ALT_WAR_TIME);
    }
    
    public void checkCastleWar(L1PcInstance player) {
        for (int i = 0; i < 8; ++i) {
            if (this._is_now_war[i]) {
                player.sendPackets(new S_PacketBoxWar(2, i + 1));
            }
        }
    }
    
    public int checkCastleWar() {
        int x = 0;
        for (int i = 0; i < 8; ++i) {
            if (this._is_now_war[i]) {
                ++x;
            }
        }
        return x;
    }
    
    public boolean isNowWar() {
        return this._is_now_war[0] || this._is_now_war[1] || this._is_now_war[2] || this._is_now_war[3] || this._is_now_war[4] || this._is_now_war[5] || this._is_now_war[6] || this._is_now_war[7];
    }
    
    protected void checkWarTime() {
        try {
            for (int i = 0; i < 8; ++i) {
                Calendar now = this.getRealTime();
                if (this._war_start_time[i].before(now) && this._war_end_time[i].after(now)) {
                    if (!this._is_now_war[i]) {
                        this._is_now_war[i] = true;
                        CharOtherReading.get().tam();
                        L1WarSpawn warspawn = new L1WarSpawn();
                        warspawn.SpawnFlag(i + 1);
                        L1DoorInstance[] doorList;
                        for (int length = (doorList = DoorSpawnTable.get().getDoorList()).length, j = 0; j < length; ++j) {
                            L1DoorInstance door = doorList[j];
                            if (L1CastleLocation.checkInWarArea(i + 1, door)) {
                                door.repairGate();
                            }
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        String time = sdf.format(this._war_start_time[i].getTime());
                        ServerWarExecutor._log.info(String.valueOf(this._castleName[i]) + " 的攻城戰開始。時間: " + time);
                        World.get().broadcastPacketToAll(new S_PacketBoxWar(0, i + 1));
                        int[] loc = new int[3];
                        for (L1PcInstance pc : World.get().getAllPlayers()) {
                            int castleId = i + 1;
                            if (L1CastleLocation.checkInWarArea(castleId, pc) && !pc.isGm()) {
                                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                if (clan != null && clan.getCastleId() == castleId) {
                                    continue;
                                }
                                else {
                                    loc = L1CastleLocation.getGetBackLoc(castleId);
                                    L1Teleport.teleport(pc, loc[0], loc[1], (short)loc[2], 5, true);
                                }
                            }
                        }
                    }
                }
                else if (this._war_end_time[i].before(now) && this._is_now_war[i]) {
                    this._is_now_war[i] = false;
                    World.get().broadcastPacketToAll(new S_PacketBoxWar(1, i + 1));
                    if (i == 1) {
                        this._war_start_time[i].add(ConfigAlt.ALT_WAR_INTERVAL_UNIT, ConfigAlt.ALT_WAR_INTERVAL - 3);
                        this._war_end_time[i].add(ConfigAlt.ALT_WAR_INTERVAL_UNIT, ConfigAlt.ALT_WAR_INTERVAL - 3);
                    }
                    else {
                        this._war_start_time[i].add(ConfigAlt.ALT_WAR_INTERVAL_UNIT, ConfigAlt.ALT_WAR_INTERVAL);
                        this._war_end_time[i].add(ConfigAlt.ALT_WAR_INTERVAL_UNIT, ConfigAlt.ALT_WAR_INTERVAL);
                    }
                    this._l1castle[i].setTaxRate(10);
                    this._l1castle[i].setPublicMoney(0L);
                    CastleReading.get().updateCastle(this._l1castle[i]);
                    int castle_id = i + 1;
                    List<L1War> list = WorldWar.get().getWarList();
                    for (L1War war : list) {
                        if (war.get_castleId() == castle_id) {
                            war.ceaseCastleWar();
                        }
                    }
                    for (L1Object l1object : World.get().getObject()) {
                        if (l1object instanceof L1FieldObjectInstance) {
                            L1FieldObjectInstance flag = (L1FieldObjectInstance)l1object;
                            if (L1CastleLocation.checkInWarArea(castle_id, flag)) {
                                flag.deleteMe();
                            }
                        }
                        if (l1object instanceof L1CrownInstance) {
                            L1CrownInstance crown = (L1CrownInstance)l1object;
                            if (L1CastleLocation.checkInWarArea(castle_id, crown)) {
                                crown.deleteMe();
                            }
                        }
                        if (l1object instanceof L1TowerInstance) {
                            L1TowerInstance tower = (L1TowerInstance)l1object;
                            if (L1CastleLocation.checkInWarArea(castle_id, tower)) {
                                tower.deleteMe();
                            }
                        }
                        if (l1object instanceof L1CatapultInstance) {
                            L1CatapultInstance catapult = (L1CatapultInstance)l1object;
                            if (L1CastleLocation.checkInWarArea(castle_id, catapult)) {
                                catapult.deleteMe();
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    L1WarSpawn warspawn2 = new L1WarSpawn();
                    warspawn2.spawnTower(castle_id);
                    warspawn2.SpawnCatapult(castle_id);
                    L1DoorInstance[] doorList2;
                    for (int length2 = (doorList2 = DoorSpawnTable.get().getDoorList()).length, k = 0; k < length2; ++k) {
                        L1DoorInstance door2 = doorList2[k];
                        if (L1CastleLocation.checkInWarArea(castle_id, door2)) {
                            door2.repairGate();
                        }
                    }
                    World.get().broadcastPacketToAll(new S_PacketBoxWar());
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String time2 = sdf2.format(now.getTime());
                    ServerWarExecutor._log.info(String.valueOf(this._castleName[i]) + " 的攻城戰結束。時間: " + time2);
                    CastleWarGiftTable.get().get_gift(castle_id);
                }
            }
        }
        catch (Exception e) {
            ServerWarExecutor._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void sendIcon(int castle_id, L1PcInstance pc) {
        pc.sendPackets(new S_PacketBox(153, (int)((this._war_end_time[castle_id - 1].getTimeInMillis() - this.getRealTime().getTimeInMillis()) / 1000L)));
    }
}
