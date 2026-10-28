package com.lineage.server.model;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1MonsterInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class L1MobSkillUseSpawn
{
    private static final Log _log;
    private Random _rnd;
    
    static {
        _log = LogFactory.getLog(L1MobSkillUseSpawn.class);
    }
    
    public L1MobSkillUseSpawn() {
        this._rnd = new Random();
    }
    
    public void mobspawn(L1Character attacker, L1Character target, int summonId, int count) {
        for (int i = 0; i < count; ++i) {
            this.mobspawn(attacker, target, summonId);
        }
    }
    
    public L1MonsterInstance mobspawnX(L1Character attacker, L1Character target, int summonId) {
        return this.mobspawn(attacker, target, summonId);
    }
    
    private L1MonsterInstance mobspawn(L1Character attacker, L1Character target, int summonId) {
        try {
            L1NpcInstance mob = NpcTable.get().newNpcInstance(summonId);
            if (mob == null) {
                L1MobSkillUseSpawn._log.error("NPC召喚技能 目標NPCID設置異常 異常編號: " + summonId);
                return null;
            }
            else {
                mob.setId(IdFactoryNpc.get().nextId());
                L1Location loc = null;
                switch (mob.getNpcId()) {
                    case 86121:
                    case 86122:
                    case 97221: {
                        loc = L1Location.randomLocation(attacker.getLocation(), 5, 15, false);
                        break;
                    }
                    default: {
                        loc = attacker.getLocation().randomLocation(6, false);
                        break;
                    }
                }
                int heading = this._rnd.nextInt(8);
                mob.setX(loc.getX());
                mob.setY(loc.getY());
                mob.setHomeX(loc.getX());
                mob.setHomeY(loc.getY());
                short mapid = attacker.getMapId();
                mob.setMap(mapid);
                mob.setHeading(heading);
                mob.set_showId(attacker.get_showId());
                World.get().storeObject(mob);
                World.get().addVisibleObject(mob);
                L1Object object = World.get().findObject(mob.getId());
                L1MonsterInstance newnpc = (L1MonsterInstance)object;
                newnpc.set_storeDroped(true);
                int gfx = newnpc.getTempCharGfx();
                switch (gfx) {
                    case 30: {
                        newnpc.broadcastPacketAll(new S_NPCPack(newnpc));
                        newnpc.broadcastPacketAll(new S_DoActionGFX(newnpc.getId(), 30));
                        break;
                    }
                    case 145:
                    case 2158:
                    case 3547:
                    case 3566:
                    case 3957:
                    case 3969:
                    case 3984:
                    case 3989:
                    case 7719:
                    case 10071:
                    case 10947:
                    case 11465:
                    case 11467:
                    case 12467: {
                        newnpc.broadcastPacketAll(new S_NPCPack(newnpc));
                        newnpc.broadcastPacketAll(new S_DoActionGFX(newnpc.getId(), 4));
                        break;
                    }
                    case 7539:
                    case 7557:
                    case 7558:
                    case 7864:
                    case 7869:
                    case 7870:
                    case 8036:
                    case 8054:
                    case 8055: {
                        for (L1PcInstance _pc : World.get().getVisiblePlayer(newnpc, 50)) {
                            if (newnpc.getTempCharGfx() == 7539) {
                                _pc.sendPackets(new S_ServerMessage(1570));
                            }
                            else if (newnpc.getTempCharGfx() == 7864) {
                                _pc.sendPackets(new S_ServerMessage(1657));
                            }
                            else if (newnpc.getTempCharGfx() == 8036) {
                                _pc.sendPackets(new S_ServerMessage(1755));
                            }
                            else {
                                continue;
                            }
                        }
                        newnpc.broadcastPacketAll(new S_NPCPack(newnpc));
                        newnpc.broadcastPacketAll(new S_DoActionGFX(newnpc.getId(), 11));
                        break;
                    }
                    case 7548:
                    case 7550:
                    case 7552:
                    case 7554:
                    case 7585:
                    case 7591:
                    case 7840:
                    case 8096: {
                        newnpc.broadcastPacketAll(new S_NPCPack(newnpc));
                        newnpc.broadcastPacketAll(new S_DoActionGFX(newnpc.getId(), 11));
                        break;
                    }
                }
                newnpc.onNpcAI();
                newnpc.startChat(0);
                if (gfx == 10947) {
                    newnpc.set_spawnTime(15);
                }
                else {
                    newnpc.set_spawnTime(60);
                }
                if (target != null) {
                    switch (newnpc.getNpcId()) {
                        case 86121:
                        case 86122:
                        case 97221:
                        case 99030:
                        case 99083:
                        case 99084:
                        case 107037: {
                            break;
                        }
                        default: {
                            newnpc.setLink(target);
                            break;
                        }
                    }
                }
                if (newnpc != null) {
                    return newnpc;
                }
            }
        }
        catch (Exception e) {
            L1MobSkillUseSpawn._log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    public void mobspawnSrc(L1Character attacker, L1Character target, int summonId) {
        try {
            L1NpcInstance npc = NpcTable.get().newNpcInstance(summonId);
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(attacker.getMapId());
            npc.setX(attacker.getX());
            npc.setY(attacker.getY());
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(attacker.getHeading());
            npc.set_showId(attacker.get_showId());
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            npc.startChat(0);
            npc.setLink(target);
        }
        catch (Exception e) {
            L1MobSkillUseSpawn._log.error(e.getLocalizedMessage(), e);
        }
    }
}
