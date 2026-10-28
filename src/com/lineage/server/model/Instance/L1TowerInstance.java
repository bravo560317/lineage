package com.lineage.server.model.Instance;

import com.lineage.server.model.L1WarSpawn;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import java.util.Collection;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1TowerInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private int _castle_id;
    private int _crackStatus;
    
    static {
        _log = LogFactory.getLog(L1TowerInstance.class);
    }
    
    public L1TowerInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            perceivedFrom.addKnownObject(this);
            perceivedFrom.sendPackets(new S_NPCPack(this));
        }
        catch (Exception e) {
            L1TowerInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onAction(L1PcInstance player) {
        try {
            if (this.getCurrentHp() > 0 && !this.isDead()) {
                L1AttackMode attack = new L1AttackPc(player, this);
                if (attack.calcHit()) {
                    attack.calcDamage();
                }
                attack.action();
                attack.commit();
            }
        }
        catch (Exception e) {
            L1TowerInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        boolean isDamage = false;
        if (this._castle_id == 0) {
            if (this.isSubTower()) {
                this._castle_id = 7;
            }
            else {
                this._castle_id = L1CastleLocation.getCastleId(this.getX(), this.getY(), this.getMapId());
            }
        }
        if (this._castle_id > 0 && ServerWarExecutor.get().isNowWar(this._castle_id)) {
            if (this._castle_id == 7 && !this.isSubTower()) {
                int subTowerDeadCount = 0;
                for (L1NpcInstance npc : WorldNpc.get().all()) {
                    if (npc instanceof L1TowerInstance) {
                        L1TowerInstance tower = (L1TowerInstance)npc;
                        if (tower.isSubTower() && tower.isDead()) {
                            ++subTowerDeadCount;
                        }
                        else {
                            continue;
                        }
                    }
                }
                if (subTowerDeadCount >= 4) {
                    isDamage = true;
                }
                else {
                    return;
                }
            }
            isDamage = true;
        }
        if (isDamage) {
            L1PcInstance pc = null;
            if (attacker instanceof L1PcInstance) {
                pc = (L1PcInstance)attacker;
            }
            else if (attacker instanceof L1PetInstance) {
                pc = (L1PcInstance)((L1PetInstance)attacker).getMaster();
            }
            else if (attacker instanceof L1SummonInstance) {
                pc = (L1PcInstance)((L1SummonInstance)attacker).getMaster();
            }
            else if (attacker instanceof L1IllusoryInstance) {
                pc = (L1PcInstance)((L1IllusoryInstance)attacker).getMaster();
            }
            else {
                boolean b = attacker instanceof L1EffectInstance;
            }
            if (pc == null) {
                return;
            }
            else {
                boolean existDefenseClan = false;
                Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                for (L1Clan clan : allClans) {
                    int clanCastleId = clan.getCastleId();
                    if (clanCastleId == this._castle_id) {
                        existDefenseClan = true;
                        break;
                    }
                }
                boolean isProclamation = false;
                for (L1War war : WorldWar.get().getWarList()) {
                    if (this._castle_id == war.getCastleId()) {
                        isProclamation = war.checkClanInWar(pc.getClanname());
                        break;
                    }
                }
                if (existDefenseClan && !isProclamation) {
                    return;
                }
                else if (this.getCurrentHp() > 0 && !this.isDead()) {
                    int newHp = this.getCurrentHp() - damage;
                    if (newHp <= 0 && !this.isDead()) {
                        this.setCurrentHpDirect(0);
                        this.setDead(true);
                        this.setStatus(36);
                        this._crackStatus = 0;
                        L1TowerInstance.Death death = new L1TowerInstance.Death(this, attacker);
                        GeneralThreadPool.get().execute(death);
                    }
                    if (newHp > 0) {
                        this.setCurrentHp(newHp);
                        if (this.getMaxHp() * 1 / 4 > this.getCurrentHp()) {
                            if (this._crackStatus != 4) {
                                this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 35));
                                this.setStatus(35);
                                this._crackStatus = 4;
                            }
                        }
                        else if (this.getMaxHp() * 2 / 4 > this.getCurrentHp()) {
                            if (this._crackStatus != 3) {
                                this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 34));
                                this.setStatus(34);
                                this._crackStatus = 3;
                            }
                        }
                        else if (this.getMaxHp() * 3 / 4 > this.getCurrentHp()) {
                            if (this._crackStatus != 2) {
                                this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 33));
                                this.setStatus(33);
                                this._crackStatus = 2;
                            }
                        }
                        else if (this.getMaxHp() >= this.getCurrentHp() && this._crackStatus != 1) {
                            this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 32));
                            this.setStatus(32);
                            this._crackStatus = 1;
                        }
                    }
                }
                else if (!this.isDead()) {
                    this.setDead(true);
                    this.setStatus(36);
                    L1TowerInstance.Death death2 = new L1TowerInstance.Death(this, attacker);
                    GeneralThreadPool.get().execute(death2);
                }
            }
        }
    }
    
    @Override
    public void setCurrentHp(int i) {
        int currentHp = Math.min(i, this.getMaxHp());
        if (this.getCurrentHp() == currentHp) {
            return;
        }
        else {
            this.setCurrentHpDirect(currentHp);
            return;
        }
    }
    
    @Override
    public void deleteMe() {
        this._destroyed = true;
        if (this.getInventory() != null) {
            this.getInventory().clearItems();
        }
        this.allTargetClear();
        this._master = null;
        World.get().removeVisibleObject(this);
        World.get().removeObject(this);
        for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
            pc.removeKnownObject(this);
            pc.sendPackets(new S_RemoveObject(this));
        }
        this.removeAllKnownObjects();
    }
    
    public boolean isSubTower() {
        return this.getNpcTemplate().get_npcId() == 81190 || this.getNpcTemplate().get_npcId() == 81191 || this.getNpcTemplate().get_npcId() == 81192 || this.getNpcTemplate().get_npcId() == 81193;
    }
    
    private class Death implements Runnable
    {
        private L1Character _lastattacker;
        private L1TowerInstance _tower;
        
        public Death(L1TowerInstance tower, L1Character attacker) {
            this._tower = null;
            this._lastattacker = attacker;
            this._tower = tower;
        }
        
        @Override
        public void run() {
            this._tower.setCurrentHpDirect(0);
            this._tower.setDead(true);
            this._tower.setStatus(36);
            this._tower.getMap().setPassable(this._tower.getLocation(), true);
            this._tower.broadcastPacketAll(new S_DoActionGFX(this._tower.getId(), 36));
            if (!this._tower.isSubTower()) {
                L1WarSpawn warspawn = new L1WarSpawn();
                warspawn.SpawnCrown(this._tower._castle_id);
            }
        }
    }
}
