package com.lineage.server.templates;

import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.utils.ListMapUtil;
import com.lineage.server.serverpackets.S_HelpMessage;
import com.lineage.server.model.L1MobGroupSpawn;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.datatables.NpcTable;
import java.util.Collections;
import com.lineage.server.datatables.QuesttSpawnTable;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.model.Instance.L1MonsterInstance;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.LogFactory;
import com.lineage.data.quest.Chapter02R;
import com.lineage.data.quest.Chapter01R;
import java.util.Random;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;
import com.lineage.data.executor.QuestMobExecutor;
import org.apache.commons.logging.Log;

public class L1QuestUser
{
    private static final Log _log;
    private final int _id;
    private final int _questid;
    private final short _mapid;
    private QuestMobExecutor _mobNull;
    private boolean _info;
    private boolean _outStop;
    private int _time;
    private final ArrayList<L1PcInstance> _userList;
    private final ArrayList<L1NpcInstance> _npcList;
    private Random _random;
    private Chapter01R _hardin;
    private Chapter02R _orim;
    private int _score;
    private boolean _SpawnedDragon;
    
    static {
        _log = LogFactory.getLog(L1QuestUser.class);
    }
    
    public L1QuestUser(int id, int mapid, int questid) {
        this._mobNull = null;
        this._info = true;
        this._outStop = false;
        this._time = -1;
        this._random = new Random();
        this._SpawnedDragon = false;
        this._id = id;
        this._mapid = (short)mapid;
        this._questid = questid;
        this._userList = new ArrayList();
        this._npcList = new ArrayList();
    }
    
    public int get_id() {
        return this._id;
    }
    
    public int get_questid() {
        return this._questid;
    }
    
    public int get_mapid() {
        return this._mapid;
    }
    
    public void add(L1PcInstance pc) {
        try {
            if (!this._userList.contains(pc)) {
                this._userList.add(pc);
                pc.set_showId(this._id);
            }
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            L1QuestUser._log.info("加入副本執行成員(" + this._questid + "-" + this._id + "):" + pc.getName());
        }
    }
    
    public void remove(L1PcInstance pc) {
        try {
            if (this._userList.remove(pc)) {
                pc.set_showId(-1);
            }
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            L1QuestUser._log.info("移出副本執行成員(" + this._questid + "-" + this._id + "):" + pc.getName());
        }
    }
    
    public void set_time(int time) {
        this._time = time;
    }
    
    public int get_time() {
        return this._time;
    }
    
    public boolean is_time() {
        return this._time != -1;
    }
    
    public ArrayList<L1PcInstance> pcList() {
        return this._userList;
    }
    
    public int size() {
        return this._userList.size();
    }
    
    public List<L1NpcInstance> npcList() {
        return (List)this._npcList;
    }
    
    public void addNpc(L1NpcInstance npc) {
        this._npcList.add(npc);
    }
    
    public ArrayList<L1NpcInstance> npcList(int npcid) {
        ArrayList<L1NpcInstance> npcList = new ArrayList();
        for (L1NpcInstance npc : this._npcList) {
            if (npc.getNpcId() == npcid && !npc.isDead()) {
                npcList.add(npc);
            }
        }
        if (npcList.size() <= 0) {
            return null;
        }
        else {
            return npcList;
        }
    }
    
    public int npcSize() {
        return this._npcList.size();
    }
    
    public int mobSize() {
        int i = 0;
        for (L1NpcInstance npc : this._npcList) {
            if (npc instanceof L1MonsterInstance) {
                ++i;
            }
        }
        return i;
    }
    
    public void spawnQuestMob() {
        PerformanceTimer timer = new PerformanceTimer();
        try {
            ArrayList<L1QuestMobSpawn> spawnList = QuesttSpawnTable.get().getMobSpawn(this._questid);
            if (spawnList.size() > 0) {
                for (L1QuestMobSpawn mobSpawn : spawnList) {
                    if (mobSpawn.get_mapid() == this._mapid) {
                        int count = mobSpawn.get_count();
                        if (count > 0) {
                            for (int i = 0; i < count; ++i) {
                                this.spawn(mobSpawn);
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            spawnList.clear();
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            L1QuestUser._log.info("副本任務啟動(" + this._questid + "-" + this._id + ") NPC完成召喚 數量:" + this._npcList.size() + "(" + timer.get() + "ms)");
        }
    }
    
    public void spawnQuestMob(int roundId) {
        try {
            ArrayList<L1QuestMobSpawn> spawnList = QuesttSpawnTable.get().getMobSpawn(this._questid);
            if (!spawnList.isEmpty()) {
                for (L1QuestMobSpawn mobSpawn : spawnList) {
                    if (mobSpawn.get_mapid() == this._mapid) {
                        int count = (mobSpawn.get_count() <= 1) ? mobSpawn.get_count() : this._random.nextInt(mobSpawn.get_count());
                        if (count > 0 && mobSpawn.get_round() == roundId) {
                            for (int i = 0; i < count; ++i) {
                                this.spawn(mobSpawn);
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            spawnList.clear();
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void spawnQuestMob(int roundId, int leastCount) {
        try {
            ArrayList<L1QuestMobSpawn> spawnList = QuesttSpawnTable.get().getMobSpawn(this._questid);
            if (!spawnList.isEmpty()) {
                for (int checkCount = 0; checkCount < leastCount; ++checkCount) {
                    Collections.shuffle(spawnList);
                    for (L1QuestMobSpawn mobSpawn : spawnList) {
                        if (mobSpawn.get_mapid() == this._mapid) {
                            int count = (mobSpawn.get_count() <= 1) ? mobSpawn.get_count() : this._random.nextInt(mobSpawn.get_count());
                            if (count > 0 && mobSpawn.get_round() == roundId) {
                                checkCount += count;
                                for (int i = 0; i < count; ++i) {
                                    this.spawn(mobSpawn);
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            spawnList.clear();
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int spawnQuestMob(int roundId, int mobId1, int mobId2) {
        try {
            ArrayList<L1QuestMobSpawn> spawnList = QuesttSpawnTable.get().getMobSpawn(this._questid);
            if (!spawnList.isEmpty()) {
                Collections.shuffle(spawnList);
                for (L1QuestMobSpawn mobSpawn : spawnList) {
                    if (mobSpawn.get_mapid() == this._mapid && mobSpawn.get_npc_templateid() != mobId1 && mobSpawn.get_npc_templateid() != mobId2 && mobSpawn.get_count() > 0 && mobSpawn.get_round() == roundId) {
                        return mobSpawn.get_npc_templateid();
                    }
                }
            }
            spawnList.clear();
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }
    
    private void spawn(L1QuestMobSpawn mobSpawn) {
        try {
            int npcid = mobSpawn.get_npc_templateid();
            int group_id = mobSpawn.get_group_id();
            int locx1 = mobSpawn.get_locx1();
            int locy1 = mobSpawn.get_locy1();
            int locx2 = mobSpawn.get_locx2();
            int locy2 = mobSpawn.get_locy2();
            int heading = mobSpawn.get_heading();
            int mapid = mobSpawn.get_mapid();
            L1Npc template = NpcTable.get().getTemplate(npcid);
            if (template == null) {
                L1QuestUser._log.error("召喚NPC編號: " + npcid + " 不存在資料庫中!");
            }
            else if (locx1 != 0 && locy1 != 0 && locx2 != 0 && locy2 != 0) {
                int x = 0;
                int y = 0;
                L1Map map = L1WorldMap.get().getMap((short)mapid);
                int tryCount = 0;
                while (tryCount <= 50) {
                    x = this._random.nextInt(locx2 - locx1) + locx1;
                    y = this._random.nextInt(locy2 - locy1) + locy1;
                    if (map.isInMap(x, y) && map.isPassable(x, y, null)) {
                        L1Location loc = new L1Location(x, y, mapid);
                        L1NpcInstance mob = L1SpawnUtil.spawn(npcid, loc, heading, this._id);
                        this._npcList.add(mob);
                        this.groupSpawn(group_id, mob);
                        break;
                    }
                    else {
                        ++tryCount;
                    }
                }
            }
            else {
                L1Location loc2 = new L1Location(locx1, locy1, mapid);
                L1NpcInstance mob2 = L1SpawnUtil.spawn(npcid, loc2, heading, this._id);
                this._npcList.add(mob2);
                this.groupSpawn(group_id, mob2);
            }
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void groupSpawn(int group_id, L1NpcInstance mob) {
        if (group_id != 0) {
            L1MobGroupSpawn.getInstance().doSpawn(mob, group_id, true, true);
        }
        if (mob.getMobGroupInfo() != null) {
            for (L1NpcInstance mobx : mob.getMobGroupInfo().getList()) {
                if (!mobx.equals(mob)) {
                    this._npcList.add(mobx);
                }
            }
        }
    }
    
    public void removeMob(L1NpcInstance mob) {
        try {
            if (this._npcList.remove(mob) && this.is_info()) {
                this.sendPackets(new S_HelpMessage("\\fY剩餘怪物：" + this.mobSize()));
            }
            if (this.mobSize() <= 0 && this._mobNull != null) {
                this._mobNull.stopQuest(this);
            }
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void removeMob() {
        try {
            ArrayList<L1NpcInstance> allList = new ArrayList();
            allList.addAll(this._npcList);
            for (L1NpcInstance npc : allList) {
                npc.deleteMe();
            }
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            ListMapUtil.clear(this._npcList);
            L1QuestUser._log.info("副本任務結束(" + this._questid + "-" + this._id + ")");
        }
    }
    
    public void endQuest() {
        try {
            for (L1PcInstance pc : this._userList) {
                if (pc.getMapId() == this._mapid) {
                    L1Teleport.teleport(pc, 33430, 32814, (short)4, 4, true);
                }
            }
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            ListMapUtil.clear(this._userList);
        }
    }
    
    public void sendPackets(ServerBasePacket basePacket) {
        try {
            for (L1PcInstance pc : this._userList) {
                pc.sendPackets(basePacket);
            }
        }
        catch (Exception e) {
            L1QuestUser._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void set_info(boolean _info) {
        this._info = _info;
    }
    
    public boolean is_info() {
        return this._info;
    }
    
    public void set_outStop(boolean _outStop) {
        this._outStop = _outStop;
    }
    
    public boolean is_outStop() {
        return this._outStop;
    }
    
    public void set_object(QuestMobExecutor mobNull) {
        this._mobNull = mobNull;
    }
    
    public QuestMobExecutor get_object() {
        return this._mobNull;
    }
    
    public void set_hardinR(Chapter01R hardin) {
        this._hardin = hardin;
    }
    
    public Chapter01R get_hardinR() {
        return this._hardin;
    }
    
    public void set_orimR(Chapter02R orim) {
        this._orim = orim;
    }
    
    public Chapter02R get_orimR() {
        return this._orim;
    }
    
    public void add_score(int i) {
        this._score += i;
    }
    
    public int get_score() {
        return this._score;
    }
    
    public boolean is_SpawnedDragon() {
        return this._SpawnedDragon;
    }
    
    public void set_SpawnedDragon(boolean _SpawnedDragon) {
        this._SpawnedDragon = _SpawnedDragon;
    }
}
