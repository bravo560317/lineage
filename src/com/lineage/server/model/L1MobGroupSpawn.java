package com.lineage.server.model;

import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.datatables.NpcTable;
import java.util.Iterator;
import com.lineage.server.templates.L1MobGroup;
import com.lineage.server.templates.L1NpcCount;
import com.lineage.server.datatables.MobGroupTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class L1MobGroupSpawn
{
    private static final Log _log;
    private static L1MobGroupSpawn _instance;
    private static Random _random;
    private boolean _isRespawnScreen;
    private boolean _isInitSpawn;
    
    static {
        _log = LogFactory.getLog(L1MobGroupSpawn.class);
        L1MobGroupSpawn._random = new Random();
    }
    
    public static L1MobGroupSpawn getInstance() {
        if (L1MobGroupSpawn._instance == null) {
            L1MobGroupSpawn._instance = new L1MobGroupSpawn();
        }
        return L1MobGroupSpawn._instance;
    }
    
    public void doSpawn(L1NpcInstance leader, int groupId, boolean isRespawnScreen, boolean isInitSpawn) {
        L1MobGroup mobGroup = MobGroupTable.get().getTemplate(groupId);
        if (mobGroup == null) {
            return;
        }
        else {
            this._isRespawnScreen = isRespawnScreen;
            this._isInitSpawn = isInitSpawn;
            L1MobGroupInfo mobGroupInfo = new L1MobGroupInfo();
            mobGroupInfo.setRemoveGroup(mobGroup.isRemoveGroupIfLeaderDie());
            mobGroupInfo.addMember(leader);
            for (L1NpcCount minion : mobGroup.getMinions()) {
                if (!minion.isZero()) {
                    for (int i = 0; i < minion.getCount(); ++i) {
                        L1NpcInstance mob = this.spawn(leader, minion.getId());
                        if (mob != null) {
                            mobGroupInfo.addMember(mob);
                        }
                    }
                }
            }
            return;
        }
    }
    
    private L1NpcInstance spawn(L1NpcInstance leader, int npcId) {
        L1NpcInstance mob = null;
        try {
            mob = NpcTable.get().newNpcInstance(npcId);
            mob.setId(IdFactoryNpc.get().nextId());
            mob.setHeading(leader.getHeading());
            mob.setMap(leader.getMapId());
            mob.setMovementDistance(leader.getMovementDistance());
            mob.setRest(leader.isRest());
            mob.setX(leader.getX() + L1MobGroupSpawn._random.nextInt(5) - 2);
            mob.setY(leader.getY() + L1MobGroupSpawn._random.nextInt(5) - 2);
            if (!this.canSpawn(mob)) {
                mob.setX(leader.getX());
                mob.setY(leader.getY());
            }
            mob.setHomeX(mob.getX());
            mob.setHomeY(mob.getY());
            if (mob instanceof L1MonsterInstance) {
                ((L1MonsterInstance)mob).initHideForMinion(leader);
            }
            mob.setreSpawn(false);
            if (mob instanceof L1MonsterInstance && mob.getMapId() == 666) {
                ((L1MonsterInstance)mob).set_storeDroped(true);
            }
            mob.set_showId(leader.get_showId());
            World.get().storeObject(mob);
            World.get().addVisibleObject(mob);
            if (leader.is_spawnTime()) {
                mob.set_spawnTime(leader.get_spawnTime());
            }
            if (mob instanceof L1MonsterInstance && !this._isInitSpawn && mob.getHiddenStatus() == 0) {
                mob.onNpcAI();
            }
            mob.turnOnOffLight();
            mob.startChat(0);
        }
        catch (Exception e) {
            L1MobGroupSpawn._log.error(e.getLocalizedMessage(), e);
        }
        return mob;
    }
    
    private boolean canSpawn(L1NpcInstance mob) {
        if (mob.getMap().isInMap(mob.getLocation()) && mob.getMap().isPassable(mob.getLocation(), mob)) {
            if (this._isRespawnScreen) {
                return true;
            }
            else if (World.get().getVisiblePlayer(mob).size() == 0) {
                return true;
            }
        }
        return false;
    }
}
