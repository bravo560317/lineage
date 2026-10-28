package com.lineage.server.model.Instance;

import com.lineage.server.datatables.DeShopChatTable;
import com.lineage.server.templates.L1NpcChat;
import com.lineage.server.model.L1NpcChatTimer;
import com.lineage.server.datatables.NpcChatTable;
import com.lineage.server.timecontroller.npc.NpcDeadTimer;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_Resurrection;
import com.lineage.server.serverpackets.S_ChangeShape;
import com.lineage.server.datatables.NpcTable;
import com.eric.gui.J_Main;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_SkillHaste;
import java.util.Timer;
import java.util.Collection;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.world.WorldNpc;
import java.util.Date;
import com.lineage.server.serverpackets.S_SystemMessage;
import java.text.SimpleDateFormat;
import com.lineage.server.datatables.lock.SpawnBossReading;
import java.util.Calendar;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.timecontroller.npc.NpcWorkTimer;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.model.L1GroundInventory;
import java.util.ArrayList;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1AttackNpc;
import com.lineage.server.model.L1Location;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.World;
import java.util.Iterator;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_NewMaster;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.timecontroller.quest.KIRTAS_Timer;
import com.lineage.server.model.L1MobGroupInfo;
import java.util.HashMap;
import java.util.List;
import com.lineage.server.model.L1HateList;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.model.L1MobSkillUse;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.L1Spawn;
import com.lineage.server.templates.L1Npc;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.server.model.L1Character;

public class L1NpcInstance extends L1Character
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    public static final int MOVE_SPEED = 0;
    public static final int ATTACK_SPEED = 1;
    public static final int MAGIC_SPEED = 2;
    public static final int HIDDEN_STATUS_NONE = 0;
    public static final int HIDDEN_STATUS_SINK = 1;
    public static final int HIDDEN_STATUS_FLY = 2;
    public static final int HIDDEN_STATUS_ICE = 3;
    public static final int HIDDEN_STATUS_KIRTAS = 4;
    public static final int REST_MILLISEC = 10;
    public static final int DISTANCE = 20;
    public static final int CHAT_TIMING_APPEARANCE = 0;
    public static final int CHAT_TIMING_DEAD = 1;
    public static final int CHAT_TIMING_HIDE = 2;
    public static final int CHAT_TIMING_GAME_TIME = 3;
    private static final Random _random;
    private L1Npc _npcTemplate;
    private L1Spawn _spawn;
    private int _spawnNumber;
    private int _petcost;
    public L1Inventory _inventory;
    public L1MobSkillUse _mobSkill;
    private boolean firstFound;
    private int _randomMoveDistance;
    private int _randomMoveDirection;
    public NpcExecutor TALK;
    public NpcExecutor ACTION;
    public NpcExecutor ATTACK;
    public NpcExecutor DEATH;
    public NpcExecutor WORK;
    public NpcExecutor SPAWN;
    protected boolean ISASCAPE;
    private boolean _aiRunning;
    private boolean _actived;
    private boolean _firstAttack;
    private int _sleep_time;
    protected final L1HateList _hateList;
    protected final L1HateList _dropHateList;
    protected final List<L1ItemInstance> _targetItemList;
    private final HashMap<L1ItemInstance, L1NpcInstance.DelItemTime> _del_map;
    protected L1Character _target;
    protected L1ItemInstance _targetItem;
    protected L1Character _master;
    private boolean _deathProcessing;
    protected boolean _hprRunning;
    private boolean _mprRunning;
    protected NpcMoveExecutor _npcMove;
    private int _passispeed;
    private int _atkspeed;
    private boolean _pickupItem;
    public static final int USEITEM_HEAL = 0;
    public static final int USEITEM_HASTE = 1;
    public static int[] healPotions;
    public static int[] haestPotions;
    private String _nameId;
    private boolean _Agro;
    private boolean _Agrocoi;
    private boolean _Agrososc;
    private int _homeX;
    private int _homeY;
    private boolean _reSpawn;
    private int _lightSize;
    private boolean _weaponBreaked;
    private int _hiddenStatus;
    private int _movementDistance;
    private int _tempLawful;
    private int _drainedMana;
    public boolean _destroyed;
    private boolean _rest;
    private boolean _isResurrect;
    private L1MobGroupInfo _mobGroupInfo;
    private int _mobGroupId;
    private int _bowActId;
    private int _ranged;
    private int _skyTime;
    private int _quest_id;
    private boolean _isShop;
    private int _spawnTime;
    private boolean _isspawnTime;
    private boolean _isremovearmor;
    private int _deadTimerTemp;
    private int _stop_time;
    private int _work_time;
    private KIRTAS_Timer _kirtastimer;
    public int _barrierTime;
    private long _shells_damag_time;
    private long _shells_silence_time;
    private String _craftkey;
    private boolean _isDropitems;
    private boolean _forDropitems;
    
    static {
        _log = LogFactory.getLog(L1NpcInstance.class);
        _random = new Random();
        // 治癒藥水清單：加入寵物專用水(50214)
        L1NpcInstance.healPotions = new int[] { 50214, 40012, 40011, 40010 };
        // 加速藥水清單：40013(勇水)、40018(綠水)、140013(祝福勇水)、140018(祝福綠水)
        L1NpcInstance.haestPotions = new int[] { 140018, 40018, 140013, 40013 };
    }
    
    protected void startAI() {
        if (this.isAiRunning()) {
            return;
        }
        else if (this.isDead()) {
            return;
        }
        else if (this.destroyed()) {
            return;
        }
        else if (this.getCurrentHp() <= 0) {
            return;
        }
        else {
            NpcAI npcai = new NpcAI(this);
            this.setAiRunning(true);
            npcai.startAI();
            this.startHpRegeneration();
            this.startMpRegeneration();
            return;
        }
    }
    
    public void onItemUse() {
    }
    
    public void searchTarget() {
    }
    
    public void addMaster(L1PcInstance master) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            if (master != null) {
                if (master.get_other().get_color() != 0) {
                    stringBuilder.append(master.get_other().color());
                }
                if (master.isProtector()) {
                    stringBuilder.append("**守護者**");
                }
                else {
                    stringBuilder.append(master.getViewName());
                }
            }
            else {
                stringBuilder.append("");
            }
            master.sendPackets(new S_NewMaster(stringBuilder.toString(), this));
        }
        catch (Exception e) {
            L1NpcInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void checkTarget() {
        if (!this.isAiRunning()) {
            this.startAI();
        }
        try {
            if (this._target == null) {
                this.clearTagert();
                return;
            }
            else if (this._target.getMapId() != this.getMapId()) {
                this.clearTagert();
                return;
            }
            else if (this._target.getCurrentHp() <= 0) {
                this.clearTagert();
                return;
            }
            else if (this._target.isDead()) {
                this.clearTagert();
                return;
            }
            else if (this.get_showId() != this._target.get_showId()) {
                this.clearTagert();
                return;
            }
            else if (this._target.isInvisble() && !this.getNpcTemplate().is_agrocoi() && !this._hateList.containsKey(this._target)) {
                this.clearTagert();
                return;
            }
            else {
                int distance = this.getLocation().getTileDistance(this._target.getLocation());
                if (distance > 30) {
                    this.clearTagert();
                    return;
                }
            }
        }
        catch (Exception e) {
            return;
        }
    }
    
    private void clearTagert() {
        if (this._target != null) {
            this.tagertClear();
        }
        if (!this._hateList.isEmpty()) {
            this._target = this._hateList.getMaxHateCharacter();
            this.checkTarget();
        }
    }
    
    public boolean isHate(L1Character cha) {
        return this._hateList.isHate(cha);
    }
    
    public void setHate(L1Character cha, int hate) {
        try {
            if (cha != null && cha.getId() != this.getId()) {
                if (!this.isFirstAttack() && hate != 0) {
                    hate += this.getMaxHp() / 10;
                    this.setFirstAttack(true);
                }
                if (this._npcMove != null) {
                    this._npcMove.clear();
                }
                this._hateList.add(cha, hate);
                this._dropHateList.add(cha, hate);
                this._target = this._hateList.getMaxHateCharacter();
                this.checkTarget();
            }
            if (!this.isAiRunning()) {
                this.startAI();
            }
        }
        catch (Exception e) {
            return;
        }
    }
    
    public void setLink(L1Character cha) {
    }
    
    public void serchLink(L1PcInstance targetPlayer, int family) {
        List<L1Object> targetKnownObjects = targetPlayer.getKnownObjects();
        for (Object knownObject : targetKnownObjects) {
            if (knownObject instanceof L1NpcInstance) {
                L1NpcInstance npc = (L1NpcInstance)knownObject;
                if (this.get_showId() != npc.get_showId()) {
                    continue;
                }
                else {
                    if (npc.getNpcTemplate().get_agrofamily() > 0) {
                        if (npc.getNpcTemplate().get_agrofamily() == 1) {
                            if (npc.getNpcTemplate().get_family() == family) {
                                npc.setLink(targetPlayer);
                            }
                        }
                        else {
                            npc.setLink(targetPlayer);
                        }
                    }
                    L1MobGroupInfo mobGroupInfo = this.getMobGroupInfo();
                    if (mobGroupInfo != null && this.getMobGroupId() != 0 && this.getMobGroupId() == npc.getMobGroupId()) {
                        if (npc.getNpcId() == 99007) {
                            return;
                        }
                        else {
                            npc.setLink(targetPlayer);
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        }
    }
    
    public void onTarget() {
        try {
            switch (this.getNpcId()) {
                case 99030:
                case 99083:
                case 107037: {
                    this.Astarothfire();
                    break;
                }
            }
            this.setActived(true);
            if (this._targetItemList.size() > 0) {
                this._targetItemList.clear();
            }
            if (this._targetItem != null) {
                this._targetItem = null;
            }
            L1Character target = this._target;
            if (target == null) {
                this.tagertClear();
                return;
            }
            else {
                if (this.getAtkspeed() == 0 && this.getPassispeed() > 0 && this.getLevel() <= 90) {
                    this.ISASCAPE = true;
                }
                if (this.ISASCAPE) {
                    this.ascape(target.getLocation());
                }
                this.attack(target);
            }
        }
        catch (Exception e) {
            L1NpcInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void Astarothfire() {
        int radius = 0;
        int damage = 0;
        switch (this.getNpcId()) {
            case 99030: {
                radius = 0;
                break;
            }
            case 99083: {
                radius = 0;
                break;
            }
            case 107037: {
                radius = 3;
                damage = 200;
                break;
            }
        }
        for (L1PcInstance player : World.get().getVisiblePlayer(this, radius)) {
            if (player != null) {
                if (this.getNpcId() == 99030) {
                    if (this._target != null && this._target.getId() == player.getId()) {
                        L1Teleport.teleport(player, 32990, 32990, (short)8001, 5, true);
                        this.setSleepTime(5000);
                        return;
                    }
                }
                else if (this.getNpcId() == 99083) {
                    L1Location loc = player.getLocation();
                    L1Location newloc = loc.randomLocation(100, true);
                    L1Teleport.teleport(player, newloc, player.getHeading(), true);
                    return;
                }
                player.receiveDamage(this, (double)damage, false, true);
                player.sendPacketsAll(new S_DoActionGFX(player.getId(), 2));
                this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 11));
                this.deleteMe();
            }
        }
    }
    
    private void kirtasfire() {
        this.setSleepTime(1000);
        int radius = 0;
        int damage = 0;
        switch (this.getNpcId()) {
            case 86121: {
                radius = 3;
                damage = 200;
                break;
            }
            case 86122: {
                radius = 2;
                damage = 100;
                break;
            }
            case 97221: {
                radius = 1;
                damage = 100;
                this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 19));
                break;
            }
            case 99030:
            case 99084: {
                radius = 0;
                break;
            }
        }
        for (L1PcInstance player : World.get().getVisiblePlayer(this, radius)) {
            if (player != null) {
                if (this.getNpcId() == 99084) {
                    if (L1NpcInstance._random.nextInt(100) < 70) {
                        L1Location loc = player.getLocation();
                        L1Location newloc = loc.randomLocation(100, true);
                        L1Teleport.teleport(player, newloc, player.getHeading(), true);
                    }
                    else {
                        L1Teleport.teleport(player, 32812, 33184, (short)4, player.getHeading(), true);
                    }
                    return;
                }
                else if (this.getNpcId() == 99030) {
                    L1Teleport.teleport(player, 32990, 32990, (short)8001, 5, true);
                    return;
                }
                else {
                    player.receiveDamage(this, (double)damage, true, true);
                    player.sendPacketsAll(new S_DoActionGFX(player.getId(), 2));
                }
            }
        }
    }
    
    private void attack(L1Character target) {
        if (!this.isAiRunning()) {
            this.startAI();
        }
        if (this.isAttackPosition(target.getX(), target.getY(), this.get_ranged())) {
            if (this._mobSkill.isSkillTrigger(target)) {
                if (L1NpcInstance._random.nextInt(2) >= 1) {
                    this.setHeading(this.targetDirection(target.getX(), target.getY()));
                    this.attackTarget(target);
                }
                else if (this._mobSkill.skillUse(target, true)) {
                    this.setSleepTime(this.calcSleepTime(this._mobSkill.getSleepTime(), 2));
                }
                else {
                    this.setHeading(this.targetDirection(target.getX(), target.getY()));
                    this.attackTarget(target);
                }
            }
            else {
                this.setHeading(this.targetDirection(target.getX(), target.getY()));
                this.attackTarget(target);
            }
            if (this._npcMove != null) {
                this._npcMove.clear();
            }
        }
        else if (this._target == null) {
            return;
        }
        else if (this.glanceCheck(this._target.getX(), this._target.getY()) && this._mobSkill.skillUse(target, false)) {
            this.setSleepTime(this.calcSleepTime(this._mobSkill.getSleepTime(), 2));
            return;
        }
        else if (this.getPassispeed() > 0) {
            int distance = this.getLocation().getTileDistance(target.getLocation());
            if (this.firstFound && this.getNpcTemplate().is_teleport() && distance > 3 && distance < 20 && this.nearTeleport(target.getX(), target.getY())) {
                this.firstFound = false;
                return;
            }
            else if (this.getNpcTemplate().is_teleport() && 20 > L1NpcInstance._random.nextInt(100) && this.getCurrentMp() >= 10 && distance > 6 && distance < 20 && this.nearTeleport(target.getX(), target.getY())) {
                return;
            }
            else if (this._npcMove != null) {
                int dir = this._npcMove.moveDirection(target.getX(), target.getY());
                if (dir == -1) {
                    this.tagertClear();
                }
                else {
                    this._npcMove.setDirectionMove(dir);
                    this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                }
            }
        }
        else {
            switch (this.getGfxId()) {
                case 816: {
                    this.attackTarget(target);
                    break;
                }
                default: {
                    this.tagertClear();
                    break;
                }
            }
        }
    }

    public void arenaAttackTarget(final L1Character target) {
        if (target == null || target.isDead() || target.getCurrentHp() <= 0) {
            return;
        }
        this._target = target;
        if (this._mobSkill != null && this._mobSkill.isSkillTrigger(target)
                && this._mobSkill.skillUse(target, true)) {
            this.setSleepTime(this.calcSleepTime(this._mobSkill.getSleepTime(), 2));
            return;
        }
        this.setHeading(this.targetDirection(target.getX(), target.getY()));
        this.attackTarget(target);
        this.setSleepTime(this.calcSleepTime(this.getAtkspeed(), 1));
    }
    
    private void ascape(L1Location location) {
        int escapeDistance = 17;
        if (this.hasSkillEffect(40)) {
            escapeDistance = 1;
        }
        if (this.getLocation().getTileLineDistance(location) > escapeDistance) {
            this.tagertClear();
        }
        else if (this._npcMove != null) {
            int dir = this._npcMove.targetReverseDirection(location.getX(), location.getY());
            dir = this._npcMove.checkObject(dir);
            dir = this._npcMove.openDoor(dir);
            this._npcMove.setDirectionMove(dir);
            this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
        }
    }
    
    private boolean isTraget(L1Character cha) {
        boolean isTraget = false;
        if (cha instanceof L1PcInstance) {
            L1PcInstance tg = (L1PcInstance)cha;
            if (!tg.isSafetyZone()) {
                isTraget = true;
            }
        }
        else if (cha instanceof L1PetInstance) {
            L1PetInstance tg2 = (L1PetInstance)cha;
            if (!tg2.isSafetyZone()) {
                isTraget = true;
            }
        }
        else if (cha instanceof L1SummonInstance) {
            L1SummonInstance tg3 = (L1SummonInstance)cha;
            if (!tg3.isSafetyZone()) {
                isTraget = true;
            }
        }
        else if (cha instanceof L1MonsterInstance) {
            isTraget = true;
        }
        return isTraget;
    }
    
    public void attackTarget(L1Character target) {
        if (this instanceof L1IllusoryInstance && !this.isTraget(target)) {
            return;
        }
        else {
            if (target instanceof L1PcInstance) {
                L1PcInstance player = (L1PcInstance)target;
                player.isTeleport();
            }
            else if (target instanceof L1PetInstance) {
                L1PetInstance pet = (L1PetInstance)target;
                L1Character cha = pet.getMaster();
                if (cha instanceof L1PcInstance) {
                    L1PcInstance player2 = (L1PcInstance)cha;
                    player2.isTeleport();
                }
            }
            else if (target instanceof L1SummonInstance) {
                L1SummonInstance summon = (L1SummonInstance)target;
                L1Character cha = summon.getMaster();
                if (cha instanceof L1PcInstance) {
                    L1PcInstance player2 = (L1PcInstance)cha;
                    if (player2.isTeleport()) {
                        return;
                    }
                }
            }
            if (this instanceof L1PetInstance) {
                L1PetInstance pet = (L1PetInstance)this;
                L1Character cha = pet.getMaster();
                if (cha instanceof L1PcInstance) {
                    L1PcInstance player2 = (L1PcInstance)cha;
                    player2.isTeleport();
                }
            }
            else if (this instanceof L1SummonInstance) {
                L1SummonInstance summon = (L1SummonInstance)this;
                L1Character cha = summon.getMaster();
                if (cha instanceof L1PcInstance) {
                    L1PcInstance player2 = (L1PcInstance)cha;
                    if (player2.isTeleport()) {
                        return;
                    }
                }
            }
            if (target instanceof L1NpcInstance) {
                L1NpcInstance npc = (L1NpcInstance)target;
                if (npc.getHiddenStatus() != 0) {
                    this.allTargetClear();
                    return;
                }
            }
            boolean isCounterBarrier = false;
            L1AttackMode attack = new L1AttackNpc(this, target);
            if (attack.calcHit()) {
                if (target.hasSkillEffect(91)) {
                    L1Magic magic = new L1Magic(target, this);
                    boolean isProbability = magic.calcProbabilityMagic(91);
                    boolean isShortDistance = attack.isShortDistance();
                    if (isProbability && isShortDistance) {
                        isCounterBarrier = true;
                    }
                }
                if (!isCounterBarrier) {
                    attack.calcDamage();
                }
            }
            if (isCounterBarrier) {
                attack.commitCounterBarrier();
            }
            else {
                attack.action();
                attack.commit();
            }
            this.setSleepTime(this.calcSleepTime(this.getAtkspeed(), 1));
            return;
        }
    }
    
    public void searchTargetItem() {
        ArrayList<L1GroundInventory> gInventorys = new ArrayList();
        for (L1Object obj : World.get().getVisibleObjects(this)) {
            if (obj != null && obj instanceof L1GroundInventory) {
                gInventorys.add((L1GroundInventory)obj);
            }
        }
        if (gInventorys.size() == 0) {
            return;
        }
        else {
            int pickupIndex = (int)(Math.random() * (double)gInventorys.size());
            L1GroundInventory inventory = gInventorys.get(pickupIndex);
            for (L1ItemInstance item : inventory.getItems()) {
                if (this.getInventory().checkAddItem(item, item.getCount()) == 0) {
                    this._targetItem = item;
                    this._targetItemList.add(this._targetItem);
                }
            }
            return;
        }
    }
    
    public void searchItemFromAir() {
        ArrayList<L1GroundInventory> gInventorys = new ArrayList();
        for (L1Object obj : World.get().getVisibleObjects(this)) {
            if (obj != null && obj instanceof L1GroundInventory) {
                gInventorys.add((L1GroundInventory)obj);
            }
        }
        if (gInventorys.size() == 0) {
            return;
        }
        else {
            int pickupIndex = (int)(Math.random() * (double)gInventorys.size());
            L1GroundInventory inventory = gInventorys.get(pickupIndex);
            for (L1ItemInstance item : inventory.getItems()) {
                if ((item.getItem().getType() == 6 || item.getItem().getType() == 7) && this.getInventory().checkAddItem(item, item.getCount()) == 0 && this.getHiddenStatus() == 2) {
                    this.setHiddenStatus(0);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 45));
                    this.setStatus(0);
                    this.broadcastPacketAll(new S_NPCPack(this));
                    this.onNpcAI();
                    this.startChat(2);
                    this._targetItem = item;
                    this._targetItemList.add(this._targetItem);
                }
            }
            return;
        }
    }
    
    public static void shuffle(L1Object[] arr) {
        for (int i = arr.length - 1; i > 0; --i) {
            int t = (int)(Math.random() * (double)i);
            L1Object tmp = arr[i];
            arr[i] = arr[t];
            arr[t] = tmp;
        }
    }
    
    public void checkTargetItem() {
        if (this._targetItem == null || this._targetItem.getMapId() != this.getMapId() || this.getLocation().getTileDistance(this._targetItem.getLocation()) > 15) {
            if (!this._targetItemList.isEmpty()) {
                this._targetItem = this._targetItemList.get(0);
                this._targetItemList.remove(0);
                this.checkTargetItem();
            }
            else {
                this._targetItem = null;
            }
        }
    }
    
    public void onTargetItem() {
        if (this.getLocation().getTileLineDistance(this._targetItem.getLocation()) == 0) {
            this.pickupTargetItem(this._targetItem);
        }
        else if (this._npcMove != null) {
            int dir = this._npcMove.moveDirection(this._targetItem.getX(), this._targetItem.getY());
            if (dir == -1) {
                this._targetItemList.remove(this._targetItem);
                this._targetItem = null;
            }
            else {
                this._npcMove.setDirectionMove(dir);
                this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
            }
        }
    }
    
    public void pickupTargetItem(L1ItemInstance targetItem) {
        try {
            int x = targetItem.getX();
            int y = targetItem.getY();
            short m = targetItem.getMapId();
            L1Inventory groundInv = World.get().getInventory(x, y, m);
            L1ItemInstance item = groundInv.tradeItem(targetItem, targetItem.getCount(), this.getInventory());
            this.onGetItem(item);
            this._targetItemList.remove(this._targetItem);
            this._targetItem = null;
            this.setSleepTime(1000);
        }
        catch (Exception e) {
            L1NpcInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean noTarget() {
        switch (this.getNpcId()) {
            case 86121:
            case 86122:
            case 97221:
            case 99084: {
                this.kirtasfire();
                break;
            }
        }
        if (World.get().getVisiblePlayer(this).isEmpty()) {
            return true;
        }
        else if (this._master != null && this._master.getMapId() == this.getMapId() && this.getLocation().getTileLineDistance(this._master.getLocation()) > 2) {
            int dir = this._npcMove.moveDirection(this._master.getX(), this._master.getY());
            if (dir == -1) {
                return true;
            }
            else {
                this._npcMove.setDirectionMove(dir);
                this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                return false;
            }
        }
        else if (World.get().getVisiblePlayer(this, 20).size() <= 0) {
            this.teleport(this.getHomeX(), this.getHomeY(), this.getHeading());
            return true;
        }
        else {
            if (this._master == null && this.getPassispeed() > 0 && !this.isRest()) {
                L1MobGroupInfo mobGroupInfo = this.getMobGroupInfo();
                if (mobGroupInfo == null || (mobGroupInfo != null && mobGroupInfo.isLeader(this))) {
                    if (this._randomMoveDistance == 0) {
                        this._randomMoveDistance = L1NpcInstance._random.nextInt(5) + 1;
                        this._randomMoveDirection = L1NpcInstance._random.nextInt(20);
                        if (this.getHomeX() != 0 && this.getHomeY() != 0 && this._randomMoveDirection < 8 && L1NpcInstance._random.nextInt(3) == 0) {
                            this._randomMoveDirection = this._npcMove.moveDirection(this.getHomeX(), this.getHomeY());
                        }
                        return false;
                    }
                    else {
                        --this._randomMoveDistance;
                        int dir2 = checkObject(this.getX(), this.getY(), this.getMapId(), this._randomMoveDirection);
                        if (dir2 != -1) {
                            this._npcMove.setDirectionMove(dir2);
                            this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                        }
                        return false;
                    }
                }
                else {
                    L1NpcInstance leader = mobGroupInfo.getLeader();
                    if (this.getPassispeed() > 0 && this.getLocation().getTileLineDistance(leader.getLocation()) > 2 && this._npcMove != null) {
                        int dir3 = this._npcMove.moveDirection(leader.getX(), leader.getY());
                        if (dir3 == -1) {
                            return true;
                        }
                        else {
                            this._npcMove.setDirectionMove(dir3);
                            this.setSleepTime(this.calcSleepTime(this.getPassispeed(), 0));
                        }
                    }
                }
            }
            return false;
        }
    }
    
    public static int checkObject(int x, int y, short m, int d) {
        L1Map map = L1WorldMap.get().getMap(m);
        switch (d) {
            case 0: {
                if (map.isPassable(x, y, 0)) {
                    return 0;
                }
                else if (map.isPassable(x, y, 7)) {
                    return 7;
                }
                else if (map.isPassable(x, y, 1)) {
                    return 1;
                }
                else {
                    break;
                }
            }
            case 1: {
                if (map.isPassable(x, y, 1)) {
                    return 1;
                }
                else if (map.isPassable(x, y, 0)) {
                    return 0;
                }
                else if (map.isPassable(x, y, 2)) {
                    return 2;
                }
                else {
                    break;
                }
            }
            case 2: {
                if (map.isPassable(x, y, 2)) {
                    return 2;
                }
                else if (map.isPassable(x, y, 1)) {
                    return 1;
                }
                else if (map.isPassable(x, y, 3)) {
                    return 3;
                }
                else {
                    break;
                }
            }
            case 3: {
                if (map.isPassable(x, y, 3)) {
                    return 3;
                }
                else if (map.isPassable(x, y, 2)) {
                    return 2;
                }
                else if (map.isPassable(x, y, 4)) {
                    return 4;
                }
                else {
                    break;
                }
            }
            case 4: {
                if (map.isPassable(x, y, 4)) {
                    return 4;
                }
                else if (map.isPassable(x, y, 3)) {
                    return 3;
                }
                else if (map.isPassable(x, y, 5)) {
                    return 5;
                }
                else {
                    break;
                }
            }
            case 5: {
                if (map.isPassable(x, y, 5)) {
                    return 5;
                }
                else if (map.isPassable(x, y, 4)) {
                    return 4;
                }
                else if (map.isPassable(x, y, 6)) {
                    return 6;
                }
                else {
                    break;
                }
            }
            case 6: {
                if (map.isPassable(x, y, 6)) {
                    return 6;
                }
                else if (map.isPassable(x, y, 5)) {
                    return 5;
                }
                else if (map.isPassable(x, y, 7)) {
                    return 7;
                }
                else {
                    break;
                }
            }
            case 7: {
                if (map.isPassable(x, y, 7)) {
                    return 7;
                }
                else if (map.isPassable(x, y, 6)) {
                    return 6;
                }
                else if (map.isPassable(x, y, 0)) {
                    return 0;
                }
                else {
                    break;
                }
            }
        }
        return -1;
    }
    
    public void onFinalAction(L1PcInstance pc, String s) {
    }
    
    private void tagertClear() {
        if (this._target == null) {
            return;
        }
        else {
            this._hateList.remove(this._target);
            this._target = null;
            return;
        }
    }
    
    public void targetRemove(L1Character target) {
        this._hateList.remove(target);
        if (this._target != null && this._target.equals(target)) {
            this._target = null;
        }
    }
    
    public void allTargetClear() {
        if (this._npcMove != null) {
            this._npcMove.clear();
        }
        this._hateList.clear();
        this._dropHateList.clear();
        this._target = null;
        this._targetItemList.clear();
        this._targetItem = null;
    }
    
    public void setMaster(L1Character cha) {
        this._master = cha;
    }
    
    public L1Character getMaster() {
        return this._master;
    }
    
    public void onNpcAI() {
    }
    
    public void refineItem() {
        int[] materials = (int[])null;
        int[] counts = (int[])null;
        int[] createitem = (int[])null;
        int[] createcount = (int[])null;
        if (this._npcTemplate.get_npcId() == 105078 && this._inventory.checkItem(40054)) {
            this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 2));
            this.setStatus(0);
            this.setHiddenStatus(0);
            this.broadcastPacketAll(new S_NPCPack(this));
        }
        if (this._npcTemplate.get_npcId() == 45032) {
            if (this.getExp() != 0L && !this._inventory.checkItem(20)) {
                materials = new int[] { 40508, 40521, 40045 };
                counts = new int[] { 150, 3, 3 };
                createitem = new int[] { 20 };
                createcount = new int[] { 1 };
                if (this._inventory.checkItem(materials, counts)) {
                    for (int i = 0; i < materials.length; ++i) {
                        this._inventory.consumeItem(materials[i], (long)counts[i]);
                    }
                    for (int j = 0; j < createitem.length; ++j) {
                        this._inventory.storeItem(createitem[j], (long)createcount[j]);
                    }
                }
            }
            if (this.getExp() != 0L && !this._inventory.checkItem(19)) {
                materials = new int[] { 40494, 40521 };
                counts = new int[] { 150, 3 };
                createitem = new int[] { 19 };
                createcount = new int[] { 1 };
                if (this._inventory.checkItem(materials, counts)) {
                    for (int i = 0; i < materials.length; ++i) {
                        this._inventory.consumeItem(materials[i], (long)counts[i]);
                    }
                    for (int j = 0; j < createitem.length; ++j) {
                        this._inventory.storeItem(createitem[j], (long)createcount[j]);
                    }
                }
            }
            if (this.getExp() != 0L && !this._inventory.checkItem(3)) {
                materials = new int[] { 40494, 40521 };
                counts = new int[] { 50, 1 };
                createitem = new int[] { 3 };
                createcount = new int[] { 1 };
                if (this._inventory.checkItem(materials, counts)) {
                    for (int i = 0; i < materials.length; ++i) {
                        this._inventory.consumeItem(materials[i], (long)counts[i]);
                    }
                    for (int j = 0; j < createitem.length; ++j) {
                        this._inventory.storeItem(createitem[j], (long)createcount[j]);
                    }
                }
            }
            if (this.getExp() != 0L && !this._inventory.checkItem(100)) {
                materials = new int[] { 88, 40508, 40045 };
                counts = new int[] { 4, 80, 3 };
                createitem = new int[] { 100 };
                createcount = new int[] { 1 };
                if (this._inventory.checkItem(materials, counts)) {
                    for (int i = 0; i < materials.length; ++i) {
                        this._inventory.consumeItem(materials[i], (long)counts[i]);
                    }
                    for (int j = 0; j < createitem.length; ++j) {
                        this._inventory.storeItem(createitem[j], (long)createcount[j]);
                    }
                }
            }
            if (this.getExp() != 0L && !this._inventory.checkItem(89)) {
                materials = new int[] { 88, 40494 };
                counts = new int[] { 2, 80 };
                createitem = new int[] { 89 };
                createcount = new int[] { 1 };
                if (this._inventory.checkItem(materials, counts)) {
                    for (int i = 0; i < materials.length; ++i) {
                        this._inventory.consumeItem(materials[i], (long)counts[i]);
                    }
                    for (int j = 0; j < createitem.length; ++j) {
                        L1ItemInstance item = this._inventory.storeItem(createitem[j], (long)createcount[j]);
                        if (this.getNpcTemplate().get_digestitem() > 0) {
                            this.setDigestItem(item);
                        }
                    }
                }
            }
        }
        else if ((this._npcTemplate.get_npcId() == 45166 || this._npcTemplate.get_npcId() == 45167) && this.getExp() != 0L && !this._inventory.checkItem(40726)) {
            materials = new int[] { 40725 };
            counts = new int[] { 1 };
            createitem = new int[] { 40726 };
            createcount = new int[] { 1 };
            if (this._inventory.checkItem(materials, counts)) {
                for (int i = 0; i < materials.length; ++i) {
                    this._inventory.consumeItem(materials[i], (long)counts[i]);
                }
                for (int j = 0; j < createitem.length; ++j) {
                    this._inventory.storeItem(createitem[j], (long)createcount[j]);
                }
            }
        }
    }
    
    public L1HateList getHateList() {
        return this._hateList;
    }
    
    public final boolean isHpR() {
        return !this.destroyed() && !this.isDead() && this.getMaxHp() > 0 && this.getCurrentHp() > 0 && this.getCurrentHp() < this.getMaxHp();
    }
    
    public final void startHpRegeneration() {
        if (this.destroyed() && this.isDead()) {
            return;
        }
        else if (this.getCurrentHp() <= 0) {
            return;
        }
        else {
            if (!this._hprRunning) {
                this._hprRunning = true;
            }
            return;
        }
    }
    
    public final boolean isHpRegenerationX() {
        return this._hprRunning;
    }
    
    public final void stopHpRegeneration() {
        if (this._hprRunning) {
            this._hprRunning = false;
        }
    }
    
    public final boolean isMpR() {
        return !this.destroyed() && !this.isDead() && this.getMaxHp() > 0 && this.getMaxMp() > 0 && this.getCurrentHp() > 0 && this.getCurrentMp() < this.getMaxMp();
    }
    
    public final void startMpRegeneration() {
        if (this.destroyed() && this.isDead()) {
            return;
        }
        else if (this.getCurrentHp() <= 0) {
            return;
        }
        else {
            if (!this._mprRunning) {
                this._mprRunning = true;
            }
            return;
        }
    }
    
    public final boolean isMpRegenerationX() {
        return this._mprRunning;
    }
    
    public final void stopMpRegeneration() {
        if (this._mprRunning) {
            this._mprRunning = false;
        }
    }
    
    public L1NpcInstance(L1Npc template) {
        this._inventory = new L1Inventory();
        this.firstFound = true;
        this._randomMoveDistance = 0;
        this._randomMoveDirection = 0;
        this.TALK = null;
        this.ACTION = null;
        this.ATTACK = null;
        this.DEATH = null;
        this.WORK = null;
        this.SPAWN = null;
        this.ISASCAPE = false;
        this._aiRunning = false;
        this._actived = false;
        this._firstAttack = false;
        this._hateList = new L1HateList();
        this._dropHateList = new L1HateList();
        this._targetItemList = new ArrayList();
        this._del_map = new HashMap();
        this._target = null;
        this._targetItem = null;
        this._master = null;
        this._deathProcessing = false;
        this._hprRunning = false;
        this._mprRunning = false;
        this._npcMove = null;
        this._movementDistance = 0;
        this._tempLawful = 0;
        this._drainedMana = 0;
        this._destroyed = false;
        this._rest = false;
        this._isResurrect = false;
        this._mobGroupInfo = null;
        this._mobGroupId = 0;
        this._bowActId = -1;
        this._ranged = -1;
        this._isShop = false;
        this._spawnTime = 0;
        this._isspawnTime = false;
        this._isremovearmor = false;
        this._deadTimerTemp = -1;
        this._stop_time = -1;
        this._work_time = -1;
        this._shells_damag_time = 0L;
        this._shells_silence_time = 0L;
        this._craftkey = null;
        this._isDropitems = false;
        this._forDropitems = false;
        this.setStatus(0);
        this.setMoveSpeed(0);
        this.setDead(false);
        this.setStatus(0);
        this.setreSpawn(false);
        this._npcMove = new NpcMove(this);
        if (template != null) {
            this.setting_template(template);
        }
    }
    
    public void setting_template(L1Npc template) {
        this._npcTemplate = template;
        int randomlevel = 0;
        double rate = 0.0;
        double diff = 0.0;
        this.setName(template.get_name());
        String name = this.newName(template.get_npcId()) + template.get_nameid();
        this.setNameId(name);
        if (template.get_randomlevel() == 0) {
            this.setLevel(template.get_level());
        }
        else {
            randomlevel = L1NpcInstance._random.nextInt(template.get_randomlevel() - template.get_level() + 1);
            diff = (double)(template.get_randomlevel() - template.get_level());
            rate = (double)randomlevel / diff;
            randomlevel += template.get_level();
            this.setLevel(randomlevel);
        }
        if (template.get_randomhp() == 0) {
            this.setMaxHp(template.get_hp());
            this.setCurrentHpDirect(template.get_hp());
        }
        else {
            double randomhp = rate * (double)(template.get_randomhp() - template.get_hp());
            int hp = (int)((double)template.get_hp() + randomhp);
            this.setMaxHp(hp);
            this.setCurrentHpDirect(hp);
        }
        if (template.get_randommp() == 0) {
            this.setMaxMp(template.get_mp());
            this.setCurrentMpDirect(template.get_mp());
        }
        else {
            double randommp = rate * (double)(template.get_randommp() - template.get_mp());
            int mp = (int)((double)template.get_mp() + randommp);
            this.setMaxMp(mp);
            this.setCurrentMpDirect(mp);
        }
        if (template.get_randomac() == 0) {
            this.setAc(template.get_ac());
        }
        else {
            double randomac = rate * (double)(template.get_randomac() - template.get_ac());
            int ac = (int)((double)template.get_ac() + randomac);
            this.setAc(ac);
        }
        if (template.get_randomlevel() == 0) {
            this.setStr(template.get_str());
            this.setCon(template.get_con());
            this.setDex(template.get_dex());
            this.setInt(template.get_int());
            this.setWis(template.get_wis());
            this.setMr(template.get_mr());
        }
        else {
            this.setStr((int)(byte)(int)Math.min((double)template.get_str() + diff, 127.0));
            this.setCon((int)(byte)(int)Math.min((double)template.get_con() + diff, 127.0));
            this.setDex((int)(byte)(int)Math.min((double)template.get_dex() + diff, 127.0));
            this.setInt((int)(byte)(int)Math.min((double)template.get_int() + diff, 127.0));
            this.setWis((int)(byte)(int)Math.min((double)template.get_wis() + diff, 127.0));
            this.setMr((int)(byte)(int)Math.min((double)template.get_mr() + diff, 127.0));
            this.addHitup((int)diff * 2);
            this.addDmgup((int)diff * 2);
        }
        this.setPassispeed(template.get_passispeed());
        this.setAtkspeed(template.get_atkspeed());
        this.setAgro(template.is_agro());
        this.setAgrocoi(template.is_agrocoi());
        this.setAgrososc(template.is_agrososc());
        if (this.getNpcTemplate().get_weakAttr() != 0) {
            switch (this.getNpcTemplate().get_weakAttr()) {
                case -8: {
                    this.addWind(50);
                    break;
                }
                case -4: {
                    this.addWater(50);
                    break;
                }
                case -2: {
                    this.addFire(50);
                    break;
                }
                case -1: {
                    this.addEarth(50);
                    break;
                }
                case 1: {
                    this.addEarth(-50);
                    break;
                }
                case 2: {
                    this.addFire(-50);
                    break;
                }
                case 4: {
                    this.addWater(-50);
                    break;
                }
                case 8: {
                    this.addWind(-50);
                    break;
                }
            }
        }
        int gfxid = this.newGfx(template.get_gfxid());
        this.setTempCharGfx(gfxid);
        this.setGfxId(gfxid);
        this.setGfxidInStatus(gfxid);
        if (template.get_randomexp() == 0) {
            this.setExp((long)template.get_exp());
        }
        else {
            int level = this.getLevel();
            int exp = level * level;
            ++exp;
            this.setExp((long)exp);
        }
        if (template.get_randomlawful() == 0) {
            this.setLawful(template.get_lawful());
            this.setTempLawful(template.get_lawful());
        }
        else {
            double randomlawful = rate * (double)(template.get_randomlawful() - template.get_lawful());
            int lawful = (int)((double)template.get_lawful() + randomlawful);
            this.setLawful(lawful);
            this.setTempLawful(lawful);
        }
        this.setPickupItem(template.is_picupitem());
        this.setBraveSpeed(template.is_bravespeed() ? 1 : 0);
        this.setKarma(template.getKarma());
        this.setLightSize(template.getLightSize());
        if (template.talk()) {
            this.TALK = template.getNpcExecutor();
        }
        if (template.action()) {
            this.ACTION = template.getNpcExecutor();
        }
        if (template.attack()) {
            this.ATTACK = template.getNpcExecutor();
        }
        if (template.death()) {
            this.DEATH = template.getNpcExecutor();
        }
        if (template.work()) {
            this.WORK = template.getNpcExecutor();
            if (this.WORK.workTime() != 0) {
                NpcWorkTimer.put(this, this.WORK.workTime());
            }
            else {
                this.WORK.work(this);
            }
        }
        if (template.spawn()) {
            (this.SPAWN = template.getNpcExecutor()).spawn(this);
        }
        this._mobSkill = new L1MobSkillUse(this);
    }
    
    public void setGfxidInStatus(int gfxid) {
        switch (gfxid) {
            case 51:
            case 110:
            case 147: {
                this.setStatus(24);
                break;
            }
            case 57:
            case 816:
            case 3137:
            case 3140:
            case 3145:
            case 3148:
            case 3151:
            case 7621:
            case 10649:
            case 11714: {
                this.setStatus(20);
                break;
            }
            case 111: {
                this.setStatus(4);
                break;
            }
        }
    }
    
    private String newName(int npcid) {
        String color = "";
        return color;
    }
    
    private int newGfx(int get_gfxid) {
        int[] r = null;
        switch (get_gfxid) {
            case 998:
            case 999:
            case 1002:
            case 1003: {
                r = new int[] { 998, 999, 1002, 1003 };
                break;
            }
            case 1318:
            case 1321: {
                r = new int[] { 1318, 1321 };
                break;
            }
            case 1597:
            case 1600: {
                r = new int[] { 1597, 1600 };
                break;
            }
            case 5942: {
                r = new int[] { 5942, 5135, 5137, 5139, 5141, 5143, 5145, 5156, 5158, 5160, 5162 };
                break;
            }
        }
        if (r != null) {
            int newgfx = L1NpcInstance._random.nextInt(r.length);
            get_gfxid = r[newgfx];
        }
        return get_gfxid;
    }
    
    public int getPassispeed() {
        return this._passispeed;
    }
    
    public void setPassispeed(int i) {
        this._passispeed = i;
    }
    
    public int getAtkspeed() {
        return this._atkspeed;
    }
    
    public void setAtkspeed(int i) {
        this._atkspeed = i;
    }
    
    public boolean isPickupItem() {
        return this._pickupItem;
    }
    
    public void setPickupItem(boolean flag) {
        this._pickupItem = flag;
    }
    
    @Override
    public L1Inventory getInventory() {
        return this._inventory;
    }
    
    public void setInventory(L1Inventory inventory) {
        this._inventory = inventory;
    }
    
    public L1Npc getNpcTemplate() {
        return this._npcTemplate;
    }
    
    public int getNpcId() {
        return this._npcTemplate.get_npcId();
    }
    
    public void setPetcost(int i) {
        this._petcost = i;
    }
    
    public int getPetcost() {
        return this._petcost;
    }
    
    public void setSpawn(L1Spawn spawn) {
        this._spawn = spawn;
    }
    
    public L1Spawn getSpawn() {
        return this._spawn;
    }
    
    public void setSpawnNumber(int number) {
        this._spawnNumber = number;
    }
    
    public int getSpawnNumber() {
        return this._spawnNumber;
    }
    
    public void onDecay(boolean isReuseId) {
        int id = 0;
        if (isReuseId) {
            id = this.getId();
        }
        else {
            id = 0;
        }
        this._spawn.executeSpawnTask(this._spawnNumber, id);
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            perceivedFrom.addKnownObject(this);
            perceivedFrom.sendPackets(new S_NPCPack(this));
            this.onNpcAI();
        }
        catch (Exception e) {
            L1NpcInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void deleteMe() {
        if (this._destroyed) {
            return;
        }
        else {
            int tgid = 0;
            if (this instanceof L1IllusoryInstance && this.getMaster() != null && this.getMaster() instanceof L1PcInstance) {
                L1PcInstance masterPc = (L1PcInstance)this.getMaster();
                if (masterPc != null) {
                    masterPc.get_otherList().removeIllusoryList(this.getId());
                }
            }
            L1NpcInstance tgMob = null;
            if (tgid != 0) {
                ArrayList<L1Object> list = World.get().getVisibleObjects(this);
                for (L1Object objects : list) {
                    if (objects instanceof L1NpcInstance) {
                        L1NpcInstance tgMobR = (L1NpcInstance)objects;
                        if (tgMobR.getNpcId() != tgid) {
                            continue;
                        }
                        else if (!tgMobR.isDead()) {
                            this.resurrect(this.getMaxHp());
                            return;
                        }
                        else if (!tgMobR._destroyed) {
                            tgMob = tgMobR;
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            this._destroyed = true;
            if (!(this instanceof L1PetInstance) && this.getInventory() != null) {
                this.getInventory().clearItems();
            }
            this.allTargetClear();
            this._master = null;
            int showid = this.get_showId();
            if (WorldQuest.get().isQuest(showid)) {
                WorldQuest.get().remove(showid, this);
            }
            if (!this.getDolls().isEmpty()) {
                Object[] array;
                for (int length = (array = this.getDolls().values().toArray()).length, i = 0; i < length; ++i) {
                    Object obj = array[i];
                    L1DollInstance doll = (L1DollInstance)obj;
                    if (doll != null) {
                        doll.deleteDoll();
                    }
                }
            }
            if (!this.getPetList().isEmpty()) {
                Object[] array2;
                for (int length2 = (array2 = this.getPetList().values().toArray()).length, j = 0; j < length2; ++j) {
                    Object obj2 = array2[j];
                    L1NpcInstance summon = (L1NpcInstance)obj2;
                    if (summon != null && summon instanceof L1SummonInstance) {
                        L1SummonInstance su = (L1SummonInstance)summon;
                        su.deleteMe();
                    }
                }
            }
            this._hateList.clear();
            this._dropHateList.clear();
            this._targetItemList.clear();
            this._del_map.clear();
            World.get().removeVisibleObject(this);
            World.get().removeObject(this);
            List<L1PcInstance> players = (List)World.get().getRecognizePlayer(this);
            if (players.size() > 0) {
                for (L1PcInstance tgpc : players) {
                    if (tgpc != null) {
                        if (this.getTempCharGfx() == 10947) {
                            tgpc.sendPackets(new S_DoActionGFX(this.getId(), 8));
                        }
                        tgpc.removeKnownObject(this);
                        tgpc.sendPackets(new S_RemoveObject(this));
                    }
                }
            }
            this.removeAllKnownObjects();
            if (tgMob != null) {
                tgMob.deleteMe();
            }
            try {
                if (this.getNpcTemplate().is_boss() && this._spawn.get_nextSpawnTime() != null) {
                    long newTime = this._spawn.get_spawnInterval() * 60L * 1000L;
                    Calendar cals = Calendar.getInstance();
                    cals.setTimeInMillis(System.currentTimeMillis() + newTime);
                    this._spawn.get_nextSpawnTime().setTimeInMillis(cals.getTimeInMillis());
                    SpawnBossReading.get().upDateNextSpawnTime(this._spawn.getId(), cals);
                    switch (this._spawn.getHeading()) {
                        case 0: {
                            Date spawnTime = this._spawn.get_nextSpawnTime().getTime();
                            String nextTime = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(spawnTime);
                            String message = String.format(this._spawn.getmsg1(), this.getNameId(), "\n " + nextTime);

                            // ✅ 廣播給玩家
                            World.get().broadcastPacketToAll(new S_SystemMessage(message));

                            // ✅ 顯示在 BOSS GUI 訊息視窗
                            J_Main.getInstance().addBossChatmsg("[BOSS死亡] " + message);
                            break;
                        }
                        case 1: {
                            World.get().broadcastPacketToAll(new S_SystemMessage(String.format(this._spawn.getmsg2(), this.getNameId())));
                            Date spawnTime = this._spawn.get_nextSpawnTime().getTime();
                            String nextTime = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(spawnTime);
                            String message = String.format(this._spawn.getmsg1(), this.getNameId(), "\n " + nextTime);
                            J_Main.getInstance().addBossChatmsg("[BOSS死亡] " + message);
                            break;
                        }
                    }
                }
            }
            catch (Exception ex) {}
            L1MobGroupInfo mobGroupInfo = this.getMobGroupInfo();
            if (mobGroupInfo != null && mobGroupInfo.removeMember(this) == 0) {
                this.setMobGroupInfo(null);
            }
            if (this.isReSpawn()) {
                this._spawn.executeSpawnTask(this._spawnNumber, 0);
            }
            return;
        }
    }
    
    public void outParty(L1NpcInstance mob) {
        Collection<L1NpcInstance> list = WorldNpc.get().all();
        for (L1NpcInstance tgNpc : list) {
            if (tgNpc.isDead()) {
                continue;
            }
            else if (tgNpc.getMaster() == null) {
                continue;
            }
            else if (tgNpc.getMaster().equals(mob)) {
                tgNpc.broadcastPacketAll(new S_SkillSound(tgNpc.getId(), 2236));
                tgNpc.deleteMe();
            }
            else {
                continue;
            }
        }
    }
    
    public void ReceiveManaDamage(L1Character attacker, int damageMp) {
    }
    
    public void receiveDamage(L1Character attacker, int damage) {
    }
    
    public void setDigestItem(L1ItemInstance item) {
        L1NpcInstance.DelItemTime delItemTime = new L1NpcInstance.DelItemTime();
        delItemTime._del_item_time = this.getNpcTemplate().get_digestitem();
        this._del_map.put(item, delItemTime);
    }
    
    public HashMap<L1ItemInstance, L1NpcInstance.DelItemTime> getDigestItem() {
        return this._del_map;
    }
    
    public void getDigestItemClear() {
        this._del_map.clear();
    }
    
    public boolean getDigestItemEmpty() {
        return this._del_map.isEmpty();
    }
    
    public void onGetItem(L1ItemInstance item) {
        this.refineItem();
        this.getInventory().shuffle();
        if (this.getNpcTemplate().get_digestitem() > 0) {
            this.setDigestItem(item);
        }
    }
    
    public void approachPlayer(L1PcInstance pc) {
        if (pc.hasSkillEffect(60) || pc.hasSkillEffect(97) || pc.isGmInvis()) {
            return;
        }
        else {
            switch (this.getHiddenStatus()) {
                case 1: {
                    if (this.getCurrentHp() == this.getMaxHp() && pc.getLocation().getTileLineDistance(this.getLocation()) <= 2) {
                        this.appearOnGround(pc);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if (this.getCurrentHp() == this.getMaxHp()) {
                        if (pc.getLocation().getTileLineDistance(this.getLocation()) <= 1) {
                            this.appearOnGround(pc);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        this.searchItemFromAir();
                        break;
                    }
                }
                case 3: {
                    if (this.getCurrentHp() < this.getMaxHp()) {
                        this.appearOnGround(pc);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 4: {
                    if (this.getCurrentHp() == this.getMaxHp() || this.getbarrierTime() > 15) {
                        this.appearOnGround(pc);
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return;
        }
    }
    
    public void startKIRTAS_Timer() {
        this._kirtastimer = new KIRTAS_Timer(this);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(this._kirtastimer, 1000L, 1000L);
    }
    
    public void stopKIRTAS_Timer() {
        this._kirtastimer.cancel();
        this._kirtastimer = null;
    }
    
    public final int getbarrierTime() {
        return this._barrierTime;
    }
    
    public final void setbarrierTime(int i) {
        this._barrierTime = i;
    }
    
    public void appearOnGround(L1PcInstance pc) {
        switch (this.getHiddenStatus()) {
            case 1:
            case 3: {
                this.setHiddenStatus(0);
                if (this.getTempCharGfx() == 1245) {
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 11));
                }
                else {
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 4));
                }
                this.setStatus(0);
                this.broadcastPacketAll(new S_NPCPack(this));
                if (!pc.hasSkillEffect(60) && !pc.hasSkillEffect(97) && !pc.isGm()) {
                    this._hateList.add(pc, 0);
                    this._target = pc;
                }
                this.onNpcAI();
                break;
            }
            case 2: {
                this.setHiddenStatus(0);
                this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 45));
                this.setStatus(0);
                this.broadcastPacketAll(new S_NPCPack(this));
                if (!pc.hasSkillEffect(60) && !pc.hasSkillEffect(97) && !pc.isGm()) {
                    this._hateList.add(pc, 0);
                    this._target = pc;
                }
                this.onNpcAI();
                this.startChat(2);
                break;
            }
            case 4: {
                if (this.hasSkillEffect(11060) && !this.hasSkillEffect(11057)) {
                    this.killSkillEffectTimer(11060);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 21));
                }
                else if (this.hasSkillEffect(11059) && !this.hasSkillEffect(11057)) {
                    this.killSkillEffectTimer(11059);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 41));
                }
                else if (this.hasSkillEffect(11058)) {
                    this.killSkillEffectTimer(11058);
                    this.killSkillEffectTimer(78);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 5));
                }
                else if (this.hasSkillEffect(11057)) {
                    this.killSkillEffectTimer(11057);
                    this.killSkillEffectTimer(11060);
                    this.killSkillEffectTimer(11059);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 25));
                }
                this.setbarrierTime(0);
                this.stopKIRTAS_Timer();
                this.setHiddenStatus(0);
                this.setStatus(0);
                this.broadcastPacketAll(new S_NPCPack(this));
                if (!pc.hasSkillEffect(60) && !pc.hasSkillEffect(97) && !pc.isGm()) {
                    this._hateList.add(pc, 0);
                    this._target = pc;
                }
                this.onNpcAI();
                break;
            }
        }
    }
    
    public L1Character is_now_target() {
        return this._target;
    }
    
    public void setNowTarget(L1Character target) {
        if (!this.isAiRunning()) {
            this.startAI();
        }
        this._target = target;
    }
    
    public L1ItemInstance is_now_targetItem() {
        return this._targetItem;
    }
    
    public void set_now_targetItem(L1ItemInstance item) {
        this._targetItem = item;
    }
    
    public NpcMoveExecutor getMove() {
        return this._npcMove;
    }
    
    private void useHealPotion(int healHp, int effectId) {
        this.broadcastPacketAll(new S_SkillSound(this.getId(), effectId));
        if (this.hasSkillEffect(173)) {
            healHp >>= 1;
        }
        if (this.hasSkillEffect(4012)) {
            healHp >>= 1;
        }
        if (this.hasSkillEffect(4011)) {
            healHp *= -1;
        }
        if (this instanceof L1PetInstance) {
            ((L1PetInstance)this).setCurrentHp(this.getCurrentHp() + healHp);
        }
        else if (this instanceof L1SummonInstance) {
            ((L1SummonInstance)this).setCurrentHp(this.getCurrentHp() + healHp);
        }
        else {
            this.setCurrentHpDirect(this.getCurrentHp() + healHp);
        }
    }
    
    private void useHastePotion(int time) {
        this.broadcastPacketAll(new S_SkillHaste(this.getId(), 1, time));
        this.broadcastPacketAll(new S_SkillSound(this.getId(), 191));
        this.setMoveSpeed(1);
        this.setSkillEffect(1001, time * 1000);
    }
    
    public void useItem(int type, int chance) {
        if (this.is_decay_potion()) {
            return;
        }
        else if (L1NpcInstance._random.nextInt(100) > chance) {
            return;
        }
        else if (this.getInventory() == null) {
            return;
        }
        else {
            switch (type) {
                case 0: {
                    // 優先消耗寵物專用水 (50214)
                    L1ItemInstance petPotion = this.getInventory().findItemId(50214);
                    if (petPotion != null) {
                        int healAmount = 15; // 預設補血量(避免資料庫填錯時的防呆)
                        int gfxId = 189;     // 預設動畫(紅水)
                        try {
                            // 動態讀取資料庫 classname 的設定 (例如: hp.UserAddHp 45 194)
                            String className = petPotion.getItem().getclassname();
                            if (className != null && !className.isEmpty() && !className.equals("0")) {
                                String[] set = className.split(" ");
                                if (set.length >= 3) {
                                    healAmount = Integer.parseInt(set[1]); // 抓取第一個參數做為補血量
                                    gfxId = Integer.parseInt(set[2]);      // 抓取第二個參數做為動畫ID
                                }
                            }
                        } catch (Exception e) {}

                        if (this.getInventory().consumeItem(50214, 1L)) {
                            this.useHealPotion(healAmount, gfxId); // 寵物自己喝，並帶入資料庫設定的值
                            break;
                        }
                    }
                    if (this.getInventory().consumeItem(40010, 1L)) {
                        this.useHealPotion(15, 189);
                        break;
                    }
                    // 消耗橘水 (40011)，恢復 45 HP，播放動畫 194
                    else if (this.getInventory().consumeItem(40011, 1L)) {
                        this.useHealPotion(45, 194);
                        break;
                    }
                    // 消耗白水 (40012)，恢復 15 HP，播放動畫 197
                    else if (this.getInventory().consumeItem(40012, 1L)) {
                        this.useHealPotion(45, 197);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1: {
                    if (this.hasSkillEffect(1001)) {
                        return;
                    }
                    else if (this.getInventory().consumeItem(140018, 1L)) {
                        this.useHastePotion(2100);
                        break;
                    }
                    else if (this.getInventory().consumeItem(40018, 1L)) {
                        this.useHastePotion(1800);
                        break;
                    }
                    else if (this.getInventory().consumeItem(140013, 1L)) {
                        this.useHastePotion(350);
                        break;
                    }
                    else if (this.getInventory().consumeItem(40013, 1L)) {
                        this.useHastePotion(300);
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return;
        }
    }
    
    public void setDirectionMoveSrc(int dir) {
        if (dir >= 0 && dir <= 7) {
            int locx = this.getX() + L1NpcInstance.HEADING_TABLE_X[dir];
            int locy = this.getY() + L1NpcInstance.HEADING_TABLE_Y[dir];
            this.setHeading(dir);
            this.setX(locx);
            this.setY(locy);
        }
    }
    
    public boolean nearTeleport(int nx, int ny) {
        int rdir = L1NpcInstance._random.nextInt(8);
        int i = 0;
        while (i < 8) {
            int dir = rdir + i;
            if (dir > 7) {
                dir -= 8;
            }
            nx += L1NpcInstance.HEADING_TABLE_X[dir];
            ny += L1NpcInstance.HEADING_TABLE_Y[dir];
            if (this.getMap().isPassable(nx, ny, this)) {
                dir += 4;
                if (dir > 7) {
                    dir -= 8;
                }
                this.teleport(nx, ny, dir);
                this.setCurrentMp(this.getCurrentMp() - 10);
                return true;
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    public void teleport(int nx, int ny, int heading) {
        try {
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                pc.sendPackets(new S_SkillSound(this.getId(), 169));
                pc.sendPackets(new S_RemoveObject(this));
                pc.removeKnownObject(this);
            }
            this.setX(nx);
            this.setY(ny);
            this.setHeading(heading);
        }
        catch (Exception e) {
            L1NpcInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public String getNameId() {
        return this._nameId;
    }
    
    public void setNameId(String s) {
        this._nameId = s;
    }
    
    public boolean isAgro() {
        return this._Agro;
    }
    
    public void setAgro(boolean flag) {
        this._Agro = flag;
    }
    
    public boolean isAgrocoi() {
        return this._Agrocoi;
    }
    
    public void setAgrocoi(boolean flag) {
        this._Agrocoi = flag;
    }
    
    public boolean isAgrososc() {
        return this._Agrososc;
    }
    
    public void setAgrososc(boolean flag) {
        this._Agrososc = flag;
    }
    
    public int getHomeX() {
        return this._homeX;
    }
    
    public void setHomeX(int i) {
        this._homeX = i;
    }
    
    public int getHomeY() {
        return this._homeY;
    }
    
    public void setHomeY(int i) {
        this._homeY = i;
    }
    
    public boolean isReSpawn() {
        return this._reSpawn;
    }
    
    public void setreSpawn(boolean flag) {
        this._reSpawn = flag;
    }
    
    public int getLightSize() {
        return this._lightSize;
    }
    
    public void setLightSize(int i) {
        this._lightSize = i;
    }
    
    public boolean isWeaponBreaked() {
        return this._weaponBreaked;
    }
    
    public void setWeaponBreaked(boolean flag) {
        this._weaponBreaked = flag;
    }
    
    public int getHiddenStatus() {
        return this._hiddenStatus;
    }
    
    public void setHiddenStatus(int i) {
        this._hiddenStatus = i;
    }
    
    public int getMovementDistance() {
        return this._movementDistance;
    }
    
    public void setMovementDistance(int i) {
        this._movementDistance = i;
    }
    
    public int getTempLawful() {
        return this._tempLawful;
    }
    
    public void setTempLawful(int i) {
        this._tempLawful = i;
    }
    
    public int calcSleepTime(int sleepTime, int type) {
        if (sleepTime <= 0) {
            sleepTime = 960;
        }
        switch (this.getMoveSpeed()) {
            case 1: {
                sleepTime = (int)((double)sleepTime - (double)sleepTime * 0.25);
                break;
            }
            case 2: {
                sleepTime *= 2;
                break;
            }
        }
        if (this.getBraveSpeed() == 1) {
            sleepTime = (int)((double)sleepTime - (double)sleepTime * 0.25);
        }
        if (this.hasSkillEffect(167) && (type == 1 || type == 2)) {
            sleepTime = (int)((double)sleepTime + (double)sleepTime * 0.25);
        }
        return sleepTime;
    }
    
    protected void setAiRunning(boolean aiRunning) {
        this._aiRunning = aiRunning;
    }
    
    protected boolean isAiRunning() {
        return this._aiRunning;
    }
    
    public boolean destroyed() {
        return this._destroyed;
    }
    
    protected L1MobSkillUse mobSkill() {
        return this._mobSkill;
    }
    
    protected void setActived(boolean actived) {
        this._actived = actived;
    }
    
    protected boolean isActived() {
        return this._actived;
    }
    
    protected void setFirstAttack(boolean firstAttack) {
        this._firstAttack = firstAttack;
    }
    
    protected boolean isFirstAttack() {
        return this._firstAttack;
    }
    
    public void setSleepTime(int sleep_time) {
        this._sleep_time = sleep_time;
    }
    
    protected int getSleepTime() {
        return this._sleep_time;
    }
    
    protected void setDeathProcessing(boolean deathProcessing) {
        this._deathProcessing = deathProcessing;
    }
    
    protected boolean isDeathProcessing() {
        return this._deathProcessing;
    }
    
    public int drainMana(int drain) {
        if (this._drainedMana >= ConfigOther.drainedMana) {
            return 0;
        }
        else {
            int result = Math.min(drain, this.getCurrentMp());
            if (this._drainedMana + result > ConfigOther.drainedMana) {
                result = ConfigOther.drainedMana - this._drainedMana;
            }
            this._drainedMana += result;
            return result;
        }
    }
    
    public void transform(int transformId) {
        this.stopHpRegeneration();
        this.stopMpRegeneration();
        int transformGfxId = this.getNpcTemplate().getTransformGfxId();
        if (transformGfxId != 0) {
            this.broadcastPacketAll(new S_SkillSound(this.getId(), transformGfxId));
        }
        L1Npc npcTemplate = NpcTable.get().getTemplate(transformId);
        this.setting_template(npcTemplate);
        this.broadcastPacketAll(new S_ChangeShape(this, this.getTempCharGfx()));
        for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
            if (pc.get_showId() != this.get_showId()) {
                continue;
            }
            else {
                this.onPerceive(pc);
            }
        }
    }
    
    public void setRest(boolean rest) {
        this._rest = rest;
    }
    
    public boolean isRest() {
        return this._rest;
    }
    
    public boolean isResurrect() {
        return this._isResurrect;
    }
    
    public void setResurrect(boolean flag) {
        this._isResurrect = flag;
    }
    
    @Override
    public synchronized void resurrect(int hp) {
        if (this._destroyed) {
            return;
        }
        else {
            super.resurrect(hp);
            this.startHpRegeneration();
            this.startMpRegeneration();
            this.broadcastPacketAll(new S_Resurrection(this, this, 0));
            L1SkillUse skill = new L1SkillUse();
            skill.handleCommands(null, 44, this.getId(), this.getX(), this.getY(), 0, 1, this);
            if (this._deadTimerTemp != -1) {
                this._deadTimerTemp = -1;
            }
            return;
        }
    }
    
    protected synchronized void startDeleteTimer(int deltime) {
        if (this._deadTimerTemp != -1) {
            return;
        }
        else {
            NpcDeadTimer.put(this, deltime);
            return;
        }
    }
    
    public boolean isInMobGroup() {
        return this.getMobGroupInfo() != null;
    }
    
    public L1MobGroupInfo getMobGroupInfo() {
        return this._mobGroupInfo;
    }
    
    public void setMobGroupInfo(L1MobGroupInfo m) {
        this._mobGroupInfo = m;
    }
    
    public int getMobGroupId() {
        return this._mobGroupId;
    }
    
    public void setMobGroupId(int i) {
        this._mobGroupId = i;
    }
    
    public void startChat(int chatTiming) {
        if (chatTiming == 0 && this.isDead()) {
            return;
        }
        else if (chatTiming == 1 && !this.isDead()) {
            return;
        }
        else if (chatTiming == 2 && this.isDead()) {
            return;
        }
        else if (chatTiming == 3 && this.isDead()) {
            return;
        }
        else {
            int npcId = this.getNpcTemplate().get_npcId();
            L1NpcChat npcChat = null;
            switch (chatTiming) {
                case 0: {
                    npcChat = NpcChatTable.get().getTemplateAppearance(npcId);
                    break;
                }
                case 1: {
                    npcChat = NpcChatTable.get().getTemplateDead(npcId);
                    break;
                }
                case 2: {
                    npcChat = NpcChatTable.get().getTemplateHide(npcId);
                    break;
                }
                case 3: {
                    npcChat = NpcChatTable.get().getTemplateGameTime(npcId);
                    break;
                }
            }
            if (npcChat == null) {
                return;
            }
            else {
                Timer timer = new Timer(true);
                L1NpcChatTimer npcChatTimer = new L1NpcChatTimer(this, npcChat);
                if (!npcChat.isRepeat()) {
                    timer.schedule(npcChatTimer, (long)npcChat.getStartDelayTime());
                }
                else {
                    timer.scheduleAtFixedRate(npcChatTimer, (long)npcChat.getStartDelayTime(), (long)npcChat.getRepeatInterval());
                }
                return;
            }
        }
    }
    
    public int getBowActId() {
        if (this._bowActId != -1) {
            return this._bowActId;
        }
        else {
            return this.getNpcTemplate().getBowActId();
        }
    }
    
    public void setBowActId(int bowActId) {
        this._bowActId = bowActId;
    }
    
    public int get_ranged() {
        if (this._ranged != -1) {
            return this._ranged;
        }
        else {
            return this.getNpcTemplate().get_ranged();
        }
    }
    
    public void set_ranged(int ranged) {
        this._ranged = ranged;
    }
    
    public final int getSkyTime() {
        return this._skyTime;
    }
    
    public final void setSkyTime(int i) {
        this._skyTime = i;
    }
    
    public int get_quest_id() {
        return this._quest_id;
    }
    
    public void set_quest_id(int quest_id) {
        this._quest_id = quest_id;
    }
    
    public boolean isShop() {
        return this._isShop;
    }
    
    public void set_isShop(boolean isShop) {
        if (this instanceof L1DeInstance) {
            L1DeInstance de = (L1DeInstance)this;
            de.setShopChat(DeShopChatTable.get().getChat(), DeShopChatTable.get().getChat());
        }
        this._isShop = isShop;
    }
    
    public void set_spawnTime(int spawnTime) {
        this._spawnTime = spawnTime;
        this._isspawnTime = true;
    }
    
    public int get_spawnTime() {
        return this._spawnTime;
    }
    
    public boolean is_spawnTime() {
        return this._isspawnTime;
    }
    
    public boolean isremovearmor() {
        return this._isremovearmor;
    }
    
    public void set_removearmor(boolean isremovearmor) {
        this._isremovearmor = isremovearmor;
    }
    
    public void set_deadTimerTemp(int time) {
        this._deadTimerTemp = time;
    }
    
    public int get_deadTimerTemp() {
        return this._deadTimerTemp;
    }
    
    public void set_stop_time(int time) {
        this._stop_time = time;
    }
    
    public int get_stop_time() {
        return this._stop_time;
    }
    
    public void set_work_time(int time) {
        this._work_time = time;
    }
    
    public int get_work_time() {
        return this._work_time;
    }
    
    public void setShellsDamageTime(long i) {
        this._shells_damag_time = i;
    }
    
    public long getShellsDamageTime() {
        return this._shells_damag_time;
    }
    
    public void setShellsSilenceTime(long i) {
        this._shells_silence_time = i;
    }
    
    public long getShellsSilenceTime() {
        return this._shells_silence_time;
    }
    
    public void set_craftkey(String craftkey) {
        this._craftkey = craftkey;
    }
    
    public String get_craftkey() {
        return this._craftkey;
    }
    
    public boolean isDropitems() {
        return this._isDropitems;
    }
    
    public void setDropItems(boolean i) {
        this._isDropitems = i;
    }
    
    public boolean forDropitems() {
        return this._forDropitems;
    }
    
    public void giveDropItems(boolean i) {
        this._forDropitems = i;
    }
    
    public class DelItemTime
    {
        public int _del_item_time;
        
        public DelItemTime() {
            this._del_item_time = 0;
        }
    }
}
