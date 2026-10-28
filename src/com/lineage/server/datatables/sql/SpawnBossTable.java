package com.lineage.server.datatables.sql;

import java.text.SimpleDateFormat;
import com.lineage.server.templates.L1Npc;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.NpcTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import com.lineage.server.model.L1Spawn;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.SpawnBossStorage;

public class SpawnBossTable implements SpawnBossStorage
{
    private static final Log _log;
    private static final Map<Integer, L1Spawn> _bossSpawnTable;
    private List<Integer> _bossId;
    private List<Integer> _bossreid;
    
    static {
        _log = LogFactory.getLog(SpawnBossTable.class);
        _bossSpawnTable = new HashMap();
    }
    
    public SpawnBossTable() {
        this._bossId = new ArrayList();
        this._bossreid = new ArrayList();
    }
    
    private Calendar timestampToCalendar(Timestamp ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        return cal;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `spawnlist_boss`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String loc = rs.getString("location");
                int npcTemplateId = rs.getInt("npc_templateid");
                L1Npc temp1 = NpcTable.get().getTemplate(npcTemplateId);
                if (temp1 == null) {
                    SpawnBossTable._log.error("BOSS召喚MOB編號: " + npcTemplateId + " 不存在資料庫中!");
                }
                else {
                    this._bossId.add(new Integer(npcTemplateId));
                    temp1.set_boss(true);
                    L1Npc temp2;
                    for (int tmp_id = temp1.getTransformId(); tmp_id > 0; tmp_id = temp2.getTransformId()) {
                        this._bossId.add(new Integer(tmp_id));
                        temp2 = NpcTable.get().getTemplate(tmp_id);
                        temp2.set_boss(true);
                    }
                    int count = rs.getInt("count");
                    if (count <= 0) {
                        continue;
                    }
                    else {
                        int group_id = rs.getInt("group_id");
                        int locx1 = rs.getInt("locx1");
                        int locy1 = rs.getInt("locy1");
                        int locx2 = rs.getInt("locx2");
                        int locy2 = rs.getInt("locy2");
                        int heading = rs.getInt("死亡廣播");
                        int mapid = rs.getShort("mapid");
                        Timestamp time = rs.getTimestamp("next_spawn_time");
                        Calendar next_spawn_time = null;
                        if (time != null) {
                            next_spawn_time = this.timestampToCalendar(rs.getTimestamp("next_spawn_time"));
                        }
                        int spawn_interval = rs.getInt("spawn_interval");
                        int exist_time = rs.getInt("存在時間");
                        String msg1 = rs.getString("死亡廣播時間");
                        String msg2 = rs.getString("死亡不廣播時間");
                        L1Spawn spawnDat = new L1Spawn(temp1);
                        spawnDat.setId(id);
                        this._bossreid.add(new Integer(id));
                        spawnDat.setLocation(loc);
                        spawnDat.setAmount(count);
                        spawnDat.setGroupId(group_id);
                        spawnDat.setNpcid(npcTemplateId);
                        if (locx2 == 0 && locy2 == 0) {
                            spawnDat.setLocX(locx1);
                            spawnDat.setLocY(locy1);
                            spawnDat.setLocX1(0);
                            spawnDat.setLocY1(0);
                            spawnDat.setLocX2(0);
                            spawnDat.setLocY2(0);
                        }
                        else {
                            spawnDat.setLocX(locx1);
                            spawnDat.setLocY(locy1);
                            spawnDat.setLocX1(locx1);
                            spawnDat.setLocY1(locy1);
                            spawnDat.setLocX2(locx2);
                            spawnDat.setLocY2(locy2);
                        }
                        if (locx2 < locx1 && locx2 != 0) {
                            SpawnBossTable._log.error("spawnlist_boss : locx2 < locx1:" + id);
                        }
                        else if (locy2 < locy1 && locy2 != 0) {
                            SpawnBossTable._log.error("spawnlist_boss : locy2 < locy1:" + id);
                        }
                        else {
                            spawnDat.setHeading(heading);
                            spawnDat.setMapId((short)mapid);
                            spawnDat.setMinRespawnDelay(10);
                            spawnDat.setMovementDistance(50);
                            spawnDat.setName(temp1.get_name());
                            spawnDat.set_nextSpawnTime(next_spawn_time);
                            spawnDat.set_spawnInterval((long)spawn_interval);
                            spawnDat.set_existTime(exist_time);
                            spawnDat.setSpawnType(0);
                            spawnDat.setBroadcast(rs.getBoolean("重生是否廣播"));
                            spawnDat.setBroadcastInfo(rs.getString("重生廣播文字"));
                            spawnDat.setmsg1(msg1);
                            spawnDat.setmsg2(msg2);
                            if (count > 1 && spawnDat.getLocX1() == 0) {
                                int range = Math.min(count * 6, 30);
                                spawnDat.setLocX1(spawnDat.getLocX() - range);
                                spawnDat.setLocY1(spawnDat.getLocY() - range);
                                spawnDat.setLocX2(spawnDat.getLocX() + range);
                                spawnDat.setLocY2(spawnDat.getLocY() + range);
                            }
                            spawnDat.init();
                            SpawnBossTable._bossSpawnTable.put(new Integer(spawnDat.getId()), spawnDat);
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            SpawnBossTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SpawnBossTable._log.info("載入BOSS召喚資料數量: " + SpawnBossTable._bossSpawnTable.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public void upDateNextSpawnTime(int id, Calendar spawnTime) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `spawnlist_boss` SET `next_spawn_time`=? WHERE `id`=?");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fm = sdf.format(spawnTime.getTime());
            int i = 0;
            pstm.setString(++i, fm);
            pstm.setInt(++i, id);
            pstm.execute();
        }
        catch (Exception e) {
            SpawnBossTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public Map<Integer, L1Spawn> get_bossSpawnTable() {
        return SpawnBossTable._bossSpawnTable;
    }
    
    @Override
    public L1Spawn getTemplate(int key) {
        return SpawnBossTable._bossSpawnTable.get((Integer)key);
    }
    
    @Override
    public List<Integer> bossIds() {
        return this._bossId;
    }
    
    @Override
    public List<Integer> bossreid() {
        return this._bossreid;
    }
}
