package com.lineage.server.world;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.datatables.QuestTable;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.server.templates.L1QuestUser;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.Log;

public class WorldQuest
{
    private static final Log _log;
    private final Lock _lock;
    private static WorldQuest _instance;
    private AtomicInteger _nextId;
    private final ConcurrentHashMap<Integer, L1QuestUser> _isQuest;
    private Collection<L1QuestUser> _allQuestValues;
    
    static {
        _log = LogFactory.getLog(WorldQuest.class);
    }
    
    public static WorldQuest get() {
        if (WorldQuest._instance == null) {
            WorldQuest._instance = new WorldQuest();
        }
        return WorldQuest._instance;
    }
    
    private WorldQuest() {
        this._lock = new ReentrantLock(true);
        this._isQuest = new ConcurrentHashMap();
        this._nextId = new AtomicInteger(100);
    }
    
    public int nextId() {
        this._lock.lock();
        try {
            int objid = this._nextId.getAndIncrement();
            return objid;
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public int maxId() {
        this._lock.lock();
        try {
            return this._nextId.get();
        }
        finally {
            this._lock.unlock();
        }
    }
    
    public Collection<L1QuestUser> all() {
        try {
            Collection<L1QuestUser> vs = this._allQuestValues;
            return (vs != null) ? vs : (this._allQuestValues = (Collection)Collections.unmodifiableCollection(this._isQuest.values()));
        }
        catch (Exception e) {
            WorldQuest._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public ConcurrentHashMap<Integer, L1QuestUser> map() {
        return this._isQuest;
    }
    
    public ArrayList<L1QuestUser> getQuests(int questId) {
        try {
            ArrayList<L1QuestUser> questList = new ArrayList();
            if (this._isQuest.size() > 0) {
                for (L1QuestUser quest : this.all()) {
                    if (quest.get_questid() == questId) {
                        questList.add(quest);
                    }
                }
            }
            return questList;
        }
        catch (Exception e) {
            WorldQuest._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public L1QuestUser get(int key) {
        try {
            return this._isQuest.get(new Integer(key));
        }
        catch (Exception e) {
            WorldQuest._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public L1QuestUser put(int key, int mapid, int questid, L1PcInstance pc) {
        try {
            if (!QuestMapTable.get().isQuestMap(mapid)) {
                WorldQuest._log.error("副本地圖編號錯誤: 原因-找不到這個副本地圖的設置 MapId:" + mapid);
                return null;
            }
            else if (QuestTable.get().getTemplate(questid) == null) {
                WorldQuest._log.error("副本地圖編號錯誤: 原因-找不到這個副本任務的設置 questid:" + questid);
                return null;
            }
            else {
                L1QuestUser value = this._isQuest.get(new Integer(key));
                if (value != null) {
                    pc.set_showId(key);
                    value.add(pc);
                }
                else {
                    value = new L1QuestUser(key, mapid, questid);
                    pc.set_showId(key);
                    value.add(pc);
                    value.spawnQuestMob();
                }
                this._isQuest.put(new Integer(key), value);
                return value;
            }
        }
        catch (Exception e) {
            WorldQuest._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public void remove(int key, L1NpcInstance npc) {
        try {
            L1QuestUser value = this._isQuest.get(new Integer(key));
            if (value != null) {
                value.removeMob(npc);
            }
        }
        catch (Exception e) {
            WorldQuest._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove(int key, L1PcInstance pc) {
        try {
            L1QuestUser value = this._isQuest.get(new Integer(key));
            if (value != null) {
                pc.set_showId(-1);
                value.remove(pc);
                boolean isRemove = false;
                if (value.is_outStop()) {
                    isRemove = true;
                }
                if (value.size() <= 0) {
                    isRemove = true;
                }
                if (isRemove) {
                    this._isQuest.remove(new Integer(key));
                    value.endQuest();
                    value.removeMob();
                }
            }
        }
        catch (Exception e) {
            WorldQuest._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean isQuest(int key) {
        try {
            L1QuestUser value = this._isQuest.get(new Integer(key));
            if (value != null) {
                return true;
            }
        }
        catch (Exception e) {
            WorldQuest._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}
