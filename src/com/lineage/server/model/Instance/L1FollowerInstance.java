package com.lineage.server.model.Instance;

import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_FollowerPack;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.model.L1Character;
import com.lineage.server.templates.L1Npc;
import java.util.Iterator;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1FollowerInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1FollowerInstance.class);
    }
    
    @Override
    public boolean noTarget() {
        if (this.ATTACK != null) {
            L1PcInstance pc = null;
            if (this._master instanceof L1PcInstance) {
                pc = (L1PcInstance)this._master;
            }
            this.ATTACK.attack(pc, this);
        }
        else {
            for (L1Object object : World.get().getVisibleObjects(this)) {
                if (object instanceof L1NpcInstance) {
                    L1NpcInstance tgnpc = (L1NpcInstance)object;
                    if (tgnpc.getNpcTemplate().get_npcId() == 71061 && this.getNpcTemplate().get_npcId() == 71062) {
                        if (this.getLocation().getTileLineDistance(this._master.getLocation()) >= 3) {
                            continue;
                        }
                        else {
                            L1PcInstance pc2 = (L1PcInstance)this._master;
                            if (pc2.getX() >= 32448 && pc2.getX() <= 32452 && pc2.getY() >= 33048 && pc2.getY() <= 33052) {
                                if (pc2.getMapId() != 440) {
                                    continue;
                                }
                                else {
                                    this.setParalyzed(true);
                                    if (!pc2.getInventory().checkItem(40711)) {
                                        this.createNewItem(pc2, 40711, 1L);
                                        pc2.getQuest().set_step(31, 3);
                                    }
                                    this.deleteMe();
                                    return true;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    else if (tgnpc.getNpcTemplate().get_npcId() == 71074 && this.getNpcTemplate().get_npcId() == 71075 && this.getLocation().getTileLineDistance(this._master.getLocation()) < 3) {
                        L1PcInstance pc2 = (L1PcInstance)this._master;
                        if (pc2.getX() >= 32731 && pc2.getX() <= 32735 && pc2.getY() >= 32854 && pc2.getY() <= 32858 && pc2.getMapId() == 480) {
                            this.setParalyzed(true);
                            if (!pc2.getInventory().checkItem(40633)) {
                                this.createNewItem(pc2, 40633, 1L);
                                pc2.getQuest().set_step(34, 2);
                            }
                            this.deleteMe();
                            return true;
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
        if (this.destroyed()) {
            return true;
        }
        else if (this._master == null) {
            return true;
        }
        else if (this._master.isDead() || this.getLocation().getTileLineDistance(this._master.getLocation()) > 13) {
            this.setParalyzed(true);
            this.spawn(this.getNpcTemplate().get_npcId(), this.getX(), this.getY(), this.getHeading(), this.getMapId());
            this.deleteMe();
            return true;
        }
        else {
            if (this._master != null && this._master.getMapId() == this.getMapId() && this.getLocation().getTileLineDistance(this._master.getLocation()) > 2 && this._npcMove != null) {
                this._npcMove.setDirectionMove(this._npcMove.moveDirection(this._master.getX(), this._master.getY()));
                this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
            }
            return false;
        }
    }
    
    public L1FollowerInstance(L1Npc template, L1NpcInstance target, L1Character master) {
        super(template);
        if (this.is(master)) {
            return;
        }
        else {
            this._master = master;
            this.setId(IdFactoryNpc.get().nextId());
            this.set_showId(master.get_showId());
            this.setMaster(master);
            this.setX(target.getX());
            this.setY(target.getY());
            this.setMap(target.getMapId());
            this.setHeading(target.getHeading());
            this.setLightSize(target.getLightSize());
            target.setParalyzed(true);
            target.deleteMe();
            World.get().storeObject(this);
            World.get().addVisibleObject(this);
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                this.onPerceive(pc);
            }
            this.onNpcAI();
            master.addFollower(this);
            return;
        }
    }
    
    private boolean is(L1Character master) {
        return master.getFollowerList().size() > 0;
    }
    
    @Override
    public void onNpcAI() {
        if (this.isAiRunning()) {
            return;
        }
        else {
            this.startAI();
            return;
        }
    }
    
    @Override
    public synchronized void deleteMe() {
        this._master.removeFollower(this);
        this.setMaster(null);
        this.getMap().setPassable(this.getLocation(), true);
        super.deleteMe();
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            L1AttackMode attack = new L1AttackPc(pc, this);
            if (attack.calcHit()) {
                attack.calcDamage();
                attack.calcStaffOfMana();
            }
            attack.action();
            attack.commit();
        }
        catch (Exception e) {
            L1FollowerInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance player) {
        if (this.isDead()) {
            return;
        }
        else {
            switch (this.getNpcTemplate().get_npcId()) {
                case 71062: {
                    if (this._master.equals(player)) {
                        player.sendPackets(new S_NPCTalkReturn(this.getId(), "kamit2"));
                        break;
                    }
                    else {
                        player.sendPackets(new S_NPCTalkReturn(this.getId(), "kamit1"));
                        break;
                    }
                }
                case 71075: {
                    if (this._master.equals(player)) {
                        player.sendPackets(new S_NPCTalkReturn(this.getId(), "llizard2"));
                        break;
                    }
                    else {
                        player.sendPackets(new S_NPCTalkReturn(this.getId(), "llizard1a"));
                        break;
                    }
                }
            }
            return;
        }
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            else {
                perceivedFrom.addKnownObject(this);
                perceivedFrom.sendPackets(new S_FollowerPack(this, perceivedFrom));
            }
        }
        catch (Exception e) {
            L1FollowerInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void createNewItem(L1PcInstance pc, int item_id, long count) {
        L1ItemInstance item = ItemTable.get().createItem(item_id);
        item.setCount(count);
        if (item != null) {
            if (pc.getInventory().checkAddItem(item, count) == 0) {
                pc.getInventory().storeItem(item);
            }
            else {
                item.set_showId(pc.get_showId());
                World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
            }
            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
        }
    }
    
    public void spawn(int npcId, int x, int y, int h, short m) {
        try {
            L1NpcInstance mob = L1SpawnUtil.spawn(npcId, x, y, m, h);
            L1QuestInstance newnpc = (L1QuestInstance)mob;
            newnpc.onNpcAI();
            newnpc.startChat(0);
        }
        catch (Exception e) {
            L1FollowerInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        if (this.getMaxHp() > 100) {
            if (this.getCurrentHp() > 0 && !this.isDead()) {
                int newHp = this.getCurrentHp() - damage;
                if (newHp <= 0 && !this.isDead()) {
                    this.setCurrentHpDirect(0);
                    this.setDead(true);
                    this.setStatus(8);
                    L1FollowerInstance.Death death = new L1FollowerInstance.Death();
                    GeneralThreadPool.get().execute(death);
                }
                if (newHp > 0) {
                    this.setCurrentHp(newHp);
                }
            }
            else if (!this.isDead()) {
                this.setDead(true);
                this.setStatus(8);
                L1FollowerInstance.Death death2 = new L1FollowerInstance.Death();
                GeneralThreadPool.get().execute(death2);
            }
        }
    }
    
    private class Death implements Runnable
    {
        @Override
        public void run() {
            try {
                L1FollowerInstance.this.getMap().setPassable(L1FollowerInstance.this.getLocation(), true);
                L1FollowerInstance.this.setDeathProcessing(true);
                L1FollowerInstance.this.broadcastPacketAll(new S_DoActionGFX(L1FollowerInstance.this.getId(), 8));
                L1FollowerInstance.this.setDeathProcessing(false);
                L1FollowerInstance.this.startDeleteTimer(10);
            }
            catch (Exception e) {
                L1FollowerInstance._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
