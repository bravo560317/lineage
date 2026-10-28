package com.lineage.server.model.Instance;

import java.util.Arrays;
import com.lineage.server.serverpackets.S_HPMeter;
import com.lineage.server.serverpackets.S_NPCPack_Summon;
import com.lineage.server.serverpackets.S_PetMenuPacket;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.serverpackets.S_SkillSound;
import java.util.List;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.drop.SetDropExecutor;
import com.lineage.server.model.drop.SetDrop;
import com.lineage.server.datatables.NpcTable;
import java.util.Iterator;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.model.L1Character;
import com.lineage.server.templates.L1Npc;
import com.lineage.config.ConfigOther;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class L1SummonInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private static final int _summonTime;
    private int _currentPetStatus;
    private int _checkMove;
    private boolean _tamed;
    private boolean _isReturnToNature;
    private static Random _random;
    private int _time;
    private int _tempModel;
    
    static {
        _log = LogFactory.getLog(L1SummonInstance.class);
        _summonTime = ConfigOther.summtime;
        L1SummonInstance._random = new Random();
    }
    
    public boolean tamed() {
        return this._tamed;
    }
    
    @Override
    public boolean noTarget() {
        if (!this.isAiRunning()) {
            this.startAI();
        }
        switch (this._currentPetStatus) {
            case 3: {
                return true;
            }
            case 4: {
                if (this._master != null && this._master.getMapId() == this.getMapId() && this.getLocation().getTileLineDistance(this._master.getLocation()) < 5) {
                    if (this._npcMove != null) {
                        int dir = this._npcMove.targetReverseDirection(this._master.getX(), this._master.getY());
                        dir = this._npcMove.checkObject(dir);
                        this._npcMove.setDirectionMove(dir);
                        this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    this._currentPetStatus = 3;
                    return true;
                }
            }
            case 5: {
                if ((Math.abs(this.getHomeX() - this.getX()) > 1 || Math.abs(this.getHomeY() - this.getY()) > 1) && this._npcMove != null) {
                    int dir = this._npcMove.moveDirection(this.getHomeX(), this.getHomeY());
                    if (dir == -1) {
                        this.setHomeX(this.getX());
                        this.setHomeY(this.getY());
                        break;
                    }
                    else {
                        this._npcMove.setDirectionMove(dir);
                        this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                        break;
                    }
                }
                else {
                    break;
                }
            }
            default: {
                if (this._master != null && this._master.getMapId() == this.getMapId()) {
                    int location = this.getLocation().getTileLineDistance(this._master.getLocation());
                    if (location > 2 && this._npcMove != null) {
                        int dir2 = this._npcMove.moveDirection(this._master.getX(), this._master.getY());
                        if (dir2 == -1) {
                            ++this._checkMove;
                            if (this._checkMove >= 10) {
                                this._checkMove = 0;
                                this._currentPetStatus = 3;
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            this._checkMove = 0;
                            this._npcMove.setDirectionMove(dir2);
                            this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                else {
                    this._currentPetStatus = 3;
                    return true;
                }
            }
        }
        return false;
    }
    
    public L1SummonInstance(L1Npc template, L1Character master) {
        super(template);
        this._checkMove = 0;
        this._isReturnToNature = false;
        this._time = 0;
        this._tempModel = 3;
        this.setId(IdFactoryNpc.get().nextId());
        this.set_showId(master.get_showId());
        this.set_time(L1SummonInstance._summonTime);
        this.setMaster(master);
        this.setX(master.getX() + L1SummonInstance._random.nextInt(5) - 2);
        this.setY(master.getY() + L1SummonInstance._random.nextInt(5) - 2);
        this.setMap(master.getMapId());
        this.setHeading(5);
        this.setLightSize(template.getLightSize());
        this._currentPetStatus = 1;
        this._tamed = false;
        World.get().storeObject(this);
        World.get().addVisibleObject(this);
        for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
            this.onPerceive(pc);
        }
        master.addPet(this);
        if (master instanceof L1PcInstance) {
            this.addMaster((L1PcInstance)master);
        }
        else if (master instanceof L1NpcInstance) {
            this._currentPetStatus = 1;
        }
    }
    
    public L1SummonInstance(L1NpcInstance target, L1Character master, boolean isCreateZombie) {
        super(null);
        this._checkMove = 0;
        this._isReturnToNature = false;
        this._time = 0;
        this._tempModel = 3;
        this.setId(IdFactoryNpc.get().nextId());
        this.set_showId(master.get_showId());
        if (isCreateZombie) {
            int npcId = 45065;
            L1PcInstance pc = (L1PcInstance)master;
            int level = pc.getLevel();
            if (pc.isWizard()) {
                if (level >= 24 && level <= 31) {
                    npcId = 81183;
                }
                else if (level >= 32 && level <= 39) {
                    npcId = 81184;
                }
                else if (level >= 40 && level <= 43) {
                    npcId = 81185;
                }
                else if (level >= 44 && level <= 47) {
                    npcId = 81186;
                }
                else if (level >= 48 && level <= 51) {
                    npcId = 81187;
                }
                else if (level >= 52) {
                    npcId = 81188;
                }
            }
            else if (pc.isElf() && level >= 48) {
                npcId = 81183;
            }
            L1Npc template = NpcTable.get().getTemplate(npcId).clone();
            this.setting_template(template);
        }
        else {
            this.setting_template(target.getNpcTemplate());
            this.setCurrentHpDirect(target.getCurrentHp());
            this.setCurrentMpDirect(target.getCurrentMp());
        }
        this.set_time(L1SummonInstance._summonTime);
        this.setMaster(master);
        this.setX(target.getX());
        this.setY(target.getY());
        this.setMap(target.getMapId());
        this.setHeading(target.getHeading());
        this.setLightSize(target.getLightSize());
        this.setPetcost(6);
        if (target instanceof L1MonsterInstance && !((L1MonsterInstance)target).is_storeDroped()) {
            SetDropExecutor setDropExecutor = new SetDrop();
            setDropExecutor.setDrop(target, target.getInventory());
        }
        this.setInventory(target.getInventory());
        target.setInventory(null);
        this._currentPetStatus = 1;
        this._tamed = true;
        for (L1NpcInstance each : master.getPetList().values()) {
            each.targetRemove(target);
        }
        target.deleteMe();
        World.get().storeObject(this);
        World.get().addVisibleObject(this);
        for (L1PcInstance pc2 : World.get().getRecognizePlayer(this)) {
            this.onPerceive(pc2);
        }
        master.addPet(this);
        if (master instanceof L1PcInstance) {
            this.addMaster((L1PcInstance)master);
        }
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        this.ISASCAPE = false;
        if (this.getCurrentHp() > 0) {
            if (damage > 0) {
                this.setHate(attacker, 0);
                this.removeSkillEffect(66);
                this.removeSkillEffect(212);
                if (!this.isExsistMaster()) {
                    this._currentPetStatus = 1;
                    this.setTarget(attacker);
                }
            }
            if (attacker instanceof L1PcInstance && damage > 0) {
                L1PcInstance player = (L1PcInstance)attacker;
                player.setPetTarget(this);
            }
            int newHp = this.getCurrentHp() - damage;
            if (newHp <= 0) {
                this.Death(attacker);
            }
            else {
                this.setCurrentHp(newHp);
            }
        }
        else if (!this.isDead()) {
            L1SummonInstance._log.error("NPC hp減少處理失敗 可能原因: 初始hp為0(" + this.getNpcId() + ")");
            this.Death(attacker);
        }
    }
    
    public synchronized void Death(L1Character lastAttacker) {
        if (!this.isDead()) {
            this.setDead(true);
            this.setCurrentHp(0);
            this.setStatus(8);
            this.getMap().setPassable(this.getLocation(), true);
            this.deleteMe();
        }
    }
    
    public synchronized void returnToNature() {
        this._isReturnToNature = true;
        if (!this._tamed) {
            this.getMap().setPassable(this.getLocation(), true);
            L1Inventory targetInventory = null;
            if (this._master != null && this._master.getInventory() != null) {
                targetInventory = this._master.getInventory();
            }
            List<L1ItemInstance> items = this._inventory.getItems();
            for (L1ItemInstance item : items) {
                if (targetInventory != null) {
                    if (this._master.getInventory().checkAddItem(item, item.getCount()) == 0) {
                        this._inventory.tradeItem(item, item.getCount(), targetInventory);
                        ((L1PcInstance)this._master).sendPackets(new S_ServerMessage(143, this.getName(), item.getLogName()));
                    }
                    else {
                        item.set_showId(this.get_showId());
                        targetInventory = World.get().getInventory(this.getX(), this.getY(), this.getMapId());
                        this._inventory.tradeItem(item, item.getCount(), targetInventory);
                    }
                }
                else {
                    item.set_showId(this.get_showId());
                    targetInventory = World.get().getInventory(this.getX(), this.getY(), this.getMapId());
                    this._inventory.tradeItem(item, item.getCount(), targetInventory);
                }
            }
            this.deleteMe();
        }
        else {
            this.liberate();
        }
    }
    
    @Override
    public synchronized void deleteMe() {
        if (this._master != null) {
            this._master.removePet(this);
        }
        if (this._destroyed) {
            return;
        }
        else {
            if (!this._tamed && !this._isReturnToNature) {
                this.broadcastPacketX8(new S_SkillSound(this.getId(), 169));
            }
            super.deleteMe();
            return;
        }
    }
    
    public void liberate() {
        L1MonsterInstance monster = new L1MonsterInstance(this.getNpcTemplate());
        monster.setId(IdFactoryNpc.get().nextId());
        monster.setX(this.getX());
        monster.setY(this.getY());
        monster.setMap(this.getMapId());
        monster.setHeading(this.getHeading());
        monster.set_storeDroped(true);
        monster.setInventory(this.getInventory());
        this.setInventory(null);
        monster.setCurrentHpDirect(this.getCurrentHp());
        monster.setCurrentMpDirect(this.getCurrentMp());
        monster.setExp(0L);
        this.deleteMe();
        World.get().storeObject(monster);
        World.get().addVisibleObject(monster);
    }
    
    public void setTarget(L1Character target) {
        if (target != null && (this._currentPetStatus == 1 || this._currentPetStatus == 2 || this._currentPetStatus == 5)) {
            this.setHate(target, 0);
        }
        if (!this.isAiRunning()) {
            this.startAI();
        }
    }
    
    public void setMasterTarget(L1Character target) {
        if (target != null && (this._currentPetStatus == 1 || this._currentPetStatus == 5)) {
            this.setHate(target, 10);
        }
        if (!this.isAiRunning()) {
            this.startAI();
        }
    }
    
    @Override
    public void onAction(L1PcInstance player) {
        if (!this.isAiRunning()) {
            this.startAI();
        }
        if (player == null) {
            return;
        }
        else {
            L1Character cha = this.getMaster();
            if (cha == null) {
                return;
            }
            else {
                L1PcInstance master = (L1PcInstance)cha;
                if (master.isTeleport()) {
                    return;
                }
                else if (master.equals(player)) {
                    L1AttackMode attack_mortion = new L1AttackPc(player, this);
                    attack_mortion.action();
                    return;
                }
                else if ((this.isSafetyZone() || player.isSafetyZone()) && this.isExsistMaster()) {
                    L1AttackMode attack_mortion = new L1AttackPc(player, this);
                    attack_mortion.action();
                    return;
                }
                else if (player.checkNonPvP(player, this)) {
                    return;
                }
                else {
                    L1AttackMode attack = new L1AttackPc(player, this);
                    if (attack.calcHit()) {
                        attack.calcDamage();
                    }
                    attack.action();
                    attack.commit();
                    return;
                }
            }
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance player) {
        if (this.isDead()) {
            return;
        }
        else {
            if (this._master.equals(player)) {
                player.sendPackets(new S_PetMenuPacket(this, 0));
            }
            return;
        }
    }
    
    @Override
    public void onFinalAction(L1PcInstance player, String action) {
        if (!this.isAiRunning()) {
            this.startAI();
        }
        int status = Integer.parseInt(action);
        switch (status) {
            case 0:
            case 6: {
                this.Death(null);
                break;
            }
            default: {
                Object[] petList = this._master.getPetList().values().toArray();
                Object[] array;
                for (int length = (array = petList).length, i = 0; i < length; ++i) {
                    Object petObject = array[i];
                    if (petObject instanceof L1SummonInstance) {
                        L1SummonInstance summon = (L1SummonInstance)petObject;
                        summon.set_currentPetStatus(status);
                    }
                }
                break;
            }
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
                perceivedFrom.sendPackets(new S_NPCPack_Summon(this, perceivedFrom));
                if (this.getMaster() != null && perceivedFrom.getId() == this.getMaster().getId()) {
                    perceivedFrom.sendPackets(new S_HPMeter(this.getId(), 100 * this.getCurrentHp() / this.getMaxHp()));
                }
                if (!this.isAiRunning()) {
                    this.startAI();
                }
            }
        }
        catch (Exception e) {
            L1SummonInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onItemUse() {
        // 1. 加速藥水：無論是否在戰鬥中，只要沒有加速狀態就喝
        this.useItem(1, 100);
        
        // 2. 治癒藥水：血量低於 80% 就喝補血藥水 (原為 40%)
        if (this.getCurrentHp() * 100 / this.getMaxHp() < 80) {
            this.useItem(0, 100);
        }
    }
    
    @Override
    public void onGetItem(L1ItemInstance item) {
        if (this.getNpcTemplate().get_digestitem() > 0) {
            this.setDigestItem(item);
        }
        Arrays.sort(L1SummonInstance.healPotions);
        Arrays.sort(L1SummonInstance.haestPotions);
        if (Arrays.binarySearch(L1SummonInstance.healPotions, item.getItem().getItemId()) >= 0) {
            if (this.getCurrentHp() != this.getMaxHp()) {
                this.useItem(0, 100);
            }
        }
        else if (Arrays.binarySearch(L1SummonInstance.haestPotions, item.getItem().getItemId()) >= 0) {
            this.useItem(1, 100);
        }
    }
    
    @Override
    public void setCurrentHp(int i) {
        if (!this.isAiRunning()) {
            this.startAI();
        }
        int currentHp = Math.min(i, this.getMaxHp());
        if (this.getCurrentHp() == currentHp) {
            return;
        }
        else {
            this.setCurrentHpDirect(currentHp);
            if (this._master instanceof L1PcInstance) {
                int hpRatio = 100 * currentHp / this.getMaxHp();
                L1PcInstance master = (L1PcInstance)this._master;
                master.sendPackets(new S_HPMeter(this.getId(), hpRatio));
            }
            return;
        }
    }
    
    @Override
    public void setCurrentMp(int i) {
        int currentMp = Math.min(i, this.getMaxMp());
        if (this.getCurrentMp() == currentMp) {
            return;
        }
        else {
            this.setCurrentMpDirect(currentMp);
            return;
        }
    }
    
    public void set_currentPetStatus(int i) {
        this._currentPetStatus = i;
        this.set_tempModel();
        switch (this._currentPetStatus) {
            case 3: {
                this.allTargetClear();
                break;
            }
            case 5: {
                this.setHomeX(this.getX());
                this.setHomeY(this.getY());
                break;
            }
        }
        if (!this.isAiRunning()) {
            this.startAI();
        }
    }
    
    public int get_currentPetStatus() {
        return this._currentPetStatus;
    }
    
    public boolean isExsistMaster() {
        boolean isExsistMaster = true;
        if (this.getMaster() != null) {
            String masterName = this.getMaster().getName();
            if (World.get().getPlayer(masterName) == null) {
                isExsistMaster = false;
            }
        }
        return isExsistMaster;
    }
    
    public int get_time() {
        return this._time;
    }
    
    public void set_time(int time) {
        this._time = time;
    }
    
    public void set_tempModel() {
        this._tempModel = this._currentPetStatus;
    }
    
    public void get_tempModel() {
        this._currentPetStatus = this._tempModel;
    }
    
    public void checkAI() {
        if (!this.isAiRunning()) {
            String masterName = null;
            if (this._master != null) {
                masterName = this._master.getName();
            }
            L1SummonInstance._log.error("召喚物AI沒有運行，自動重新運行。召喚獸：" + this.getNpcTemplate().get_name() + " 主人：" + masterName);
            this.startAI();
        }
    }
}
