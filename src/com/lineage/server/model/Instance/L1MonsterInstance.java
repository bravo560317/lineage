package com.lineage.server.model.Instance;

import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.config.ConfigAlt;
import com.lineage.server.datatables.ServerCrockTable;
import com.lineage.server.world.WorldMob;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import java.util.TimerTask;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.L1UltimateBattle;
import com.lineage.server.datatables.UBTable;
import com.lineage.config.ConfigRate;
import com.lineage.server.model.drop.DropShareExecutor;
import com.lineage.server.model.drop.DropShare;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.model.L1Clan;
import java.util.ArrayList;
import com.lineage.server.datatables.MonsterEnhanceTable;
import com.lineage.data.event.Npc_Dead;
import com.lineage.server.datatables.ServerQuestMaPTable;
import java.sql.Timestamp;
import com.lineage.server.datatables.sql.L1MonTable;
import com.lineage.server.templates.L1Mon;
import com.lineage.server.datatables.ServerQuestMobTable;
import com.lineage.data.event.QuestMobSet;
import com.lineage.config.ConfigClan;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.sql.ClanTable;
import com.lineage.server.world.WorldClan;
import com.lineage.server.datatables.NpcClanContribution;
import com.lineage.data.event.ClanContribution;
import com.lineage.server.model.pr_type_name;
import com.lineage.server.datatables.NpcPrestigeTable;
import com.lineage.data.event.prestigtable;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.datatables.C1_Name_Type_Table;
import com.lineage.data.event.CampSet;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.NpcScoreTable;
import com.lineage.server.utils.CalcExp;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.data.event.ProtectorSet;
import com.lineage.william.Npc_Dead_span;
import com.lineage.data.event.Npe_DeadSpan;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.model.drop.SetDropExecutor;
import com.lineage.server.model.drop.SetDrop;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.L1Character;
import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.types.Point;
import com.lineage.config.ConfigGuardTower;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_NPCPack;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class L1MonsterInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private static final Random _random;
    private boolean _storeDroped;
    private int _ubSealCount;
    private int _ubId;
    private long _lasthprtime;
    private long _lastmprtime;
    
    static {
        _log = LogFactory.getLog(L1MonsterInstance.class);
        _random = new Random();
    }
    
    @Override
    public void onItemUse() {
        if (!this.isActived() && this._target != null && this.getNpcTemplate().is_doppel() && this._target instanceof L1PcInstance) {
            L1PcInstance targetPc = (L1PcInstance)this._target;
            this.setName(this._target.getName());
            this.setNameId(this._target.getName());
            this.setTitle(this._target.getTitle());
            this.setTempLawful(this._target.getLawful());
            this.setTempCharGfx(targetPc.getClassId());
            this.setGfxId(targetPc.getClassId());
            this.setPassispeed(640);
            this.setAtkspeed(900);
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                pc.sendPackets(new S_RemoveObject(this));
                pc.removeKnownObject(this);
                pc.updateObject();
            }
        }
        if (this.getCurrentHp() * 100 / this.getMaxHp() < 40) {
            this.useItem(1, 50);
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
                if (this.getCurrentHp() > 0) {
                    perceivedFrom.sendPackets(new S_NPCPack(this, perceivedFrom));
                    this.onNpcAI();
                    if (this.getBraveSpeed() == 1) {
                        perceivedFrom.sendPackets(new S_SkillBrave(this.getId(), 1, 600000));
                    }
                }
                else {
                    perceivedFrom.sendPackets(new S_NPCPack(this));
                }
            }
        }
        catch (Exception e) {
            L1MonsterInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void searchTarget() {
        L1NpcInstance targetNpc = this.searchTargetnpc(this);
        L1PcInstance targetPlayer = this.searchTarget(this);
        if (targetNpc != null) {
            this._hateList.add(targetNpc, 0);
            this._target = targetNpc;
        }
        else if (targetPlayer != null) {
            this._hateList.add(targetPlayer, 0);
            this._target = targetPlayer;
        }
        else {
            this.ISASCAPE = false;
        }
    }
    
    private L1MonsterInstance searchTargetnpc(L1MonsterInstance npc) {
        L1MonsterInstance targetNpc = null;
        if (this.getMapId() == 93) {
            Point npcpt = this.getLocation();
            for (L1Object object : World.get().getVisibleObjects(this, -1)) {
                if (object instanceof L1MonsterInstance) {
                    L1MonsterInstance tgnpc = (L1MonsterInstance)object;
                    if (this.getMapId() == tgnpc.getMapId()) {
                        if (!npcpt.isInScreen(tgnpc.getLocation())) {
                            continue;
                        }
                        else if (tgnpc != null && tgnpc.getCurrentHp() > 0 && !tgnpc.isDead() && this.getId() != tgnpc.getId() && this.get_showId() == tgnpc.get_showId()) {
                            targetNpc = tgnpc;
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
        if (this.getMapId() == ConfigGuardTower.MAPID) {
            for (L1Object object2 : World.get().getVisibleObjects(npc, 50)) {
                if (object2 instanceof L1MonsterInstance) {
                    L1MonsterInstance tgnpc2 = (L1MonsterInstance)object2;
                    if (tgnpc2.getNpcId() == ConfigGuardTower.TOWERID) {
                        targetNpc = tgnpc2;
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        return targetNpc;
    }
    
    private L1PcInstance searchTarget(L1MonsterInstance npc) {
        L1PcInstance targetPlayer = null;
        for (L1PcInstance pc : World.get().getVisiblePlayer(npc)) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e) {
                L1MonsterInstance._log.error(e.getLocalizedMessage(), e);
            }
            if (pc.getCurrentHp() <= 0) {
                continue;
            }
            else if (pc.isDead()) {
                continue;
            }
            else if (pc.isGhost()) {
                continue;
            }
            else if (pc.isGm()) {
                continue;
            }
            else if (npc.getNpcTemplate().is_Atkpc()) {
                continue;
            }
            else if (npc.get_showId() != pc.get_showId()) {
                continue;
            }
            else if (npc.getMapId() == 410 && pc.getTempCharGfx() == 4261) {
                continue;
            }
            else if (npc.getNpcTemplate().get_family() == NpcTable.ORC && pc.getClan() != null && pc.getClan().getCastleId() == 2) {
                continue;
            }
            else {
                L1PcInstance tgpc1 = npc.attackPc1(pc);
                if (tgpc1 != null) {
                    targetPlayer = tgpc1;
                    return targetPlayer;
                }
                else {
                    L1PcInstance tgpc2 = npc.attackPc2(pc);
                    if (tgpc2 != null) {
                        targetPlayer = tgpc2;
                        return targetPlayer;
                    }
                    else if (npc.getNpcTemplate().getKarma() < 0 && pc.getKarmaLevel() >= 1) {
                        continue;
                    }
                    else if (npc.getNpcTemplate().getKarma() > 0 && pc.getKarmaLevel() <= -1) {
                        continue;
                    }
                    else if (pc.getTempCharGfx() == 6034 && npc.getNpcTemplate().getKarma() < 0) {
                        continue;
                    }
                    else {
                        if (pc.getTempCharGfx() == 6035) {
                            if (npc.getNpcTemplate().getKarma() > 0) {
                                continue;
                            }
                            else if (npc.getNpcTemplate().get_npcId() == 46070) {
                                continue;
                            }
                            else if (npc.getNpcTemplate().get_npcId() == 46072) {
                                continue;
                            }
                        }
                        if (npc.getNpcTemplate().is_Atkpc_red() && npc.targetPlayer1(pc) != null) {
                            targetPlayer = npc.targetPlayer1(pc);
                            return targetPlayer;
                        }
                        else {
                            L1PcInstance tgpc3 = npc.targetPlayer1000(pc);
                            if (tgpc3 != null) {
                                targetPlayer = tgpc3;
                                return targetPlayer;
                            }
                            else if (!pc.isInvisble() || this.getNpcTemplate().is_agrocoi()) {
                                if (pc.hasSkillEffect(67)) {
                                    if (this.getNpcTemplate().is_agrososc()) {
                                        targetPlayer = pc;
                                        break;
                                    }
                                }
                                else if (this.getNpcTemplate().is_agro()) {
                                    targetPlayer = pc;
                                    break;
                                }
                                if (npc.getNpcTemplate().is_agrogfxid1() >= 0 && pc.getGfxId() == npc.getNpcTemplate().is_agrogfxid1()) {
                                    targetPlayer = pc;
                                    return targetPlayer;
                                }
                                else if (npc.getNpcTemplate().is_agrogfxid2() >= 0 && pc.getGfxId() == npc.getNpcTemplate().is_agrogfxid2()) {
                                    targetPlayer = pc;
                                    return targetPlayer;
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
            }
        }
        return targetPlayer;
    }
    
    private L1PcInstance attackPc2(L1PcInstance pc) {
        if (this.getNpcId() == 45600) {
            if (pc.isCrown() && pc.getTempCharGfx() == pc.getClassId()) {
                return pc;
            }
            else if (pc.isDarkelf()) {
                return pc;
            }
        }
        return null;
    }
    
    private L1PcInstance attackPc1(L1PcInstance pc) {
        int mapId = this.getMapId();
        boolean isCheck = false;
        if (mapId == 88) {
            isCheck = true;
        }
        if (mapId == 98) {
            isCheck = true;
        }
        if (mapId == 92) {
            isCheck = true;
        }
        if (mapId == 91) {
            isCheck = true;
        }
        if (mapId == 95) {
            isCheck = true;
        }
        if (isCheck && (!pc.isInvisble() || this.getNpcTemplate().is_agrocoi())) {
            return pc;
        }
        else {
            return null;
        }
    }
    
    private L1PcInstance targetPlayer1000(L1PcInstance pc) {
        if (ConfigOther.KILLRED && !this.getNpcTemplate().is_agro() && !this.getNpcTemplate().is_agrososc() && this.getNpcTemplate().is_agrogfxid1() < 0 && this.getNpcTemplate().is_agrogfxid2() < 0 && pc.getLawful() < -1000) {
            return pc;
        }
        else {
            return null;
        }
    }
    
    private L1PcInstance targetPlayer1(L1PcInstance pc) {
        if (pc.getLawful() < -1) {
            return pc;
        }
        else {
            return null;
        }
    }
    
    @Override
    public void setLink(L1Character cha) {
        if (this.get_showId() != cha.get_showId()) {
            return;
        }
        else {
            if (cha != null && this._hateList.isEmpty()) {
                this._hateList.add(cha, 0);
                this.checkTarget();
            }
            return;
        }
    }
    
    public L1MonsterInstance(L1Npc template) {
        super(template);
        this._ubSealCount = 0;
        this._ubId = 0;
        this._lasthprtime = 0L;
        this._lastmprtime = 0L;
        this._storeDroped = false; // 修正：將 applyEnhance() 移至建構子最後，確保物件完全初始化
        applyEnhance();
    }
    
    @Override
    public void onNpcAI() {
        if (this.isAiRunning()) {
            return;
        }
        else {
            if (!this._storeDroped) {
                SetDropExecutor setdrop = new SetDrop();
                setdrop.setDrop(this, this.getInventory());
                this.getInventory().shuffle();
                this._storeDroped = true;
            }
            this.setActived(false);
            this.startAI();
            return;
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance pc) {
        this.setHeading(this.targetDirection(pc.getX(), pc.getY()));
        this.broadcastPacketAll(new S_ChangeHeading(this));
        this.set_stop_time(10);
        this.setRest(true);
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        if (this.ATTACK != null) {
            this.ATTACK.attack(pc, this);
        }
        if (this.getCurrentHp() > 0 && !this.isDead()) {
            L1AttackMode attack = new L1AttackPc(pc, this);
            if (attack.calcHit()) {
                attack.calcDamage();
                attack.calcStaffOfMana();
            }
            attack.action();
            attack.commit();
        }
    }
    
    @Override
    public void ReceiveManaDamage(L1Character attacker, int mpDamage) {
        if (mpDamage > 0 && !this.isDead()) {
            this.setHate(attacker, mpDamage);
            this.onNpcAI();
            if (attacker instanceof L1PcInstance) {
                this.serchLink((L1PcInstance)attacker, this.getNpcTemplate().get_family());
            }
            int newMp = this.getCurrentMp() - mpDamage;
            if (newMp < 0) {
                newMp = 0;
            }
            this.setCurrentMp(newMp);
        }
    }
    
    public void receiveDamage(L1Character attacker, double damage, int attr) {
        int mrdef = this.getMr();
        int rnd = L1MonsterInstance._random.nextInt(100) + 1;
        if (mrdef >= rnd) {
            damage /= 2.0;
        }
        int resist = 0;
        switch (attr) {
            case 1: {
                resist = this.getEarth();
                break;
            }
            case 2: {
                resist = this.getFire();
                break;
            }
            case 4: {
                resist = this.getWater();
                break;
            }
            case 8: {
                resist = this.getWind();
                break;
            }
        }
        int resistFloor = (int)(0.16 * (double)Math.abs(resist));
        if (resist >= 0) {
            resistFloor *= 1;
        }
        else {
            resistFloor *= -1;
        }
        double attrDeffence = (double)resistFloor / 32.0;
        double coefficient = 1.0 - attrDeffence;
        damage *= coefficient;
        this.receiveDamage(attacker, (int)damage);
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        this.ISASCAPE = false;
        if (this.getCurrentHp() > 0 && !this.isDead()) {
            if (this.getHiddenStatus() == 1 || this.getHiddenStatus() == 2) {
                return;
            }
            else {
                if (damage >= 0) {
                    if (attacker instanceof L1EffectInstance) {
                        L1EffectInstance effect = (L1EffectInstance)attacker;
                        attacker = effect.getMaster();
                        if (attacker != null) {
                            this.setHate(attacker, damage);
                        }
                        // 🟩 開始套用怪物減傷
if (this._damageReduction > 0) {
    damage -= this._damageReduction;
    if (damage < 0) {
        damage = 0;
    }
}
                    }
                    else if (attacker instanceof L1IllusoryInstance) {
                        L1IllusoryInstance ill = (L1IllusoryInstance)attacker;
                        attacker = ill.getMaster();
                        if (attacker != null) {
                            this.setHate(attacker, damage);
                        }
                    }
                    else if (attacker instanceof L1MonsterInstance) {
                        switch (this.getNpcTemplate().get_npcId()) {
                            case 91290:
                            case 91294:
                            case 91295:
                            case 91296: {
                                this.setHate(attacker, damage);
                                damage = 0;
                                break;
                            }
                        }
                    }
                    else {
                        this.setHate(attacker, damage);
                    }
// 🟩 加在這裡！開始套用怪物減傷
if (this._damageReduction > 0) {
    damage -= this._damageReduction;
    if (damage < 0) {
        damage = 0;
    }
}
                }
                if (damage > 0) {
                    this.removeSkillEffect(66);
                    this.removeSkillEffect(212);
                }
                this.onNpcAI();
                L1PcInstance atkpc = null;
                if (attacker instanceof L1PcInstance) {
                    atkpc = (L1PcInstance)attacker;
                    if (damage > 0) {
                        atkpc.setPetTarget(this);
                        switch (this.getNpcTemplate().get_npcId()) {
                            case 45681:
                            case 45682:
                            case 45683:
                            case 45684: {
                                this.recall(atkpc);
                                break;
                            }
                        }
                    }
                    this.serchLink(atkpc, this.getNpcTemplate().get_family());
                }
                if (this.hasSkillEffect(219)) {
                    damage = (int)((double)damage * 1.05);
                }
                int newHp = this.getCurrentHp() - damage;
                if (newHp <= 0 && !this.isDead()) {
                    int transformId = this.getNpcTemplate().getTransformId();
                    if (transformId == -1) {
                        this.setCurrentHpDirect(0);
                        this.setDead(true);
                        this.setStatus(8);
                        openDoorWhenNpcDied(this);
                        L1MonsterInstance.Death death = new L1MonsterInstance.Death(attacker);
                        GeneralThreadPool.get().execute(death);
                        if (Npe_DeadSpan.START) {
                            Npc_Dead_span.forresolvent(atkpc, this);
                        }
                        if (atkpc != null && !this.isResurrect() && ProtectorSet.CHANCE > 0 && L1MonsterInstance._random.nextInt(1000) < ProtectorSet.CHANCE && CharItemsReading.get().checkItemId(ProtectorSet.ITEM_ID) < ProtectorSet.DROP_LIMIT) {
                            L1ItemInstance item = ItemTable.get().createItem(ProtectorSet.ITEM_ID);
                            atkpc.getInventory().storeItem(item);
                            World.get().broadcastPacketToAll(new S_BlueMessage(166, "\\f=有人獲得了守護者的靈魂"));
                        }
                        if (this.getNpcId() >= 71014 && this.getNpcId() <= 71016) {
                            GeneralThreadPool.get().execute(new L1MonsterInstance.deathDragonTimer1(this, this.getMapId()));
                        }
                        else if (this.getNpcId() >= 71026 && this.getNpcId() <= 71028) {
                            GeneralThreadPool.get().execute(new L1MonsterInstance.deathDragonTimer2(this, this.getMapId()));
                        }
                        else if (this.getNpcId() >= 97204 && this.getNpcId() <= 97206) {
                            GeneralThreadPool.get().execute(new L1MonsterInstance.deathDragonTimer3(this));
                        }
                    }
                    else {
                        this.transform(transformId);
                    }
                }
                if (newHp > 0) {
                    this.setCurrentHp(newHp);
                    this.hide();
                }
                if (ConfigOther.HPBAR) {
                    if (atkpc == null) {
                        if (attacker instanceof L1PetInstance) {
                            atkpc = (L1PcInstance)((L1PetInstance)attacker).getMaster();
                        }
                        else if (attacker instanceof L1SummonInstance) {
                            atkpc = (L1PcInstance)((L1SummonInstance)attacker).getMaster();
                        }
                        if (atkpc != null) {
                            this.broadcastPacketHP(atkpc);
                        }
                    }
                    else {
                        this.broadcastPacketHP(atkpc);
                    }
                }
            }
        }
        else if (!this.isDead()) {
            this.setDead(true);
            this.setStatus(8);
            L1MonsterInstance.Death death2 = new L1MonsterInstance.Death(attacker);
            GeneralThreadPool.get().execute(death2);
        }
    }
    
    private void recall(L1PcInstance pc) {
        if (this.getMapId() != pc.getMapId()) {
            return;
        }
        else {
            if (this.getLocation().getTileLineDistance(pc.getLocation()) > 4) {
                int count = 0;
                while (count < 10) {
                    L1Location newLoc = this.getLocation().randomLocation(3, 4, false);
                    if (this.glanceCheck(newLoc.getX(), newLoc.getY())) {
                        L1Teleport.teleport(pc, newLoc.getX(), newLoc.getY(), this.getMapId(), 5, true);
                        break;
                    }
                    else {
                        ++count;
                    }
                }
            }
            return;
        }
    }
    
    private static void openDoorWhenNpcDied(L1NpcInstance npc) {
        int[] npcId = { 46143, 46144, 46145, 46146, 46147, 46148, 46149, 46150, 46151, 46152 };
        int[] doorId = { 5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010 };
        for (int i = 0; i < npcId.length; ++i) {
            if (npc.getNpcTemplate().get_npcId() == npcId[i]) {
                openDoorInCrystalCave(doorId[i]);
            }
        }
    }
    
    private static void openDoorInCrystalCave(int doorId) {
        for (L1Object object : World.get().getObject()) {
            if (object instanceof L1DoorInstance) {
                L1DoorInstance door = (L1DoorInstance)object;
                if (door.getDoorId() == doorId) {
                    door.open();
                }
                else {
                    continue;
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
    
    private void distributeExpDropKarma(L1Character lastAttacker) {
        if (lastAttacker == null) {
            return;
        }
        else {
            L1PcInstance pc = null;
            if (this.DEATH != null) {
                pc = this.DEATH.death(lastAttacker, this);
            }
            else {
                pc = CheckUtil.checkAtkPc(lastAttacker);
            }
            if (pc != null) {
                ArrayList<L1Character> targetList = this._hateList.toTargetArrayList();
                ArrayList<Integer> hateList = this._hateList.toHateArrayList();
                long exp = this.getExp();
                CalcExp.calcExp(pc, this.getId(), targetList, hateList, exp);
                if (pc.get_c_power() != null && pc.get_c_power().get_c1_type() != 0) {
                    int score = NpcScoreTable.get().get_score(this.getNpcId());
                    if (score > 0 && !this.isResurrect()) {
                        if (pc.getdouble_score() != 0) {
                            score *= pc.getdouble_score();
                        }
                        pc.get_other().add_score(score);
                        pc.sendPackets(new S_ServerMessage("\\fR獲得陣營積分:" + score));
                        if (CampSet.CAMPSTART && pc.get_c_power() != null && pc.get_c_power().get_c1_type() != 0) {
                            int lv = C1_Name_Type_Table.get().getLv(pc.get_c_power().get_c1_type(), pc.get_other().get_score());
                            if (lv != pc.get_c_power().get_power().get_c1_id()) {
                                pc.get_c_power().set_power(pc, false);
                                pc.sendPackets(new S_ServerMessage("\\fR階級變更:" + pc.get_c_power().get_power().get_c1_name_type()));
                                pc.sendPacketsAll(new S_ChangeName(pc, true));
                            }
                        }
                    }
                }
                if (prestigtable.START) {
                    int prestige = NpcPrestigeTable.get().get_score(this.getNpcId());
                    if (prestige != 0) {
                        pc.addPrestige(prestige);
                        pc.sendPackets(new S_ServerMessage("狩獵獲得" + pr_type_name._1 + "增加:" + prestige, 11));
                    }
                }
                if (ClanContribution.START && pc.getClanid() != 0) {
                    int ClanContribution = NpcClanContribution.get().get_score(this.getNpcId());
                    L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                    if (ClanContribution != 0 && pc.getQuest().get_step(8544) != 1) {
                        clan.setClanContribution(clan.getClanContribution() + ClanContribution);
                        ClanTable.getInstance().updateClan(clan);
                        pc.setPcContribution(pc.getPcContribution() + ClanContribution);
                        pc.setClanContribution(pc.getClanContribution() + ClanContribution);
                        pc.getInventory().storeItem(92164, (long)ClanContribution);
                        pc.sendPackets(new S_ServerMessage("狩獵獲得血盟能量+:" + ClanContribution, 11));
                        pc.sendPackets(new S_SystemMessage("你給予目前的血盟貢獻度有【" + pc.getClanContribution() + "】點。"));
                        pc.sendPackets(new S_SystemMessage("你的血盟目前所獲得的貢獻度總合有【" + clan.getClanContribution() + "】點。"));
                        pc.sendPackets(new S_SystemMessage("獲得血盟貢獻幣:【" + ClanContribution + "】個。"));
                        if (pc.getPcContribution() >= ConfigClan.PcContribution) {
                            pc.sendPackets(new S_ServerMessage("\\fY每日血盟能量上限:" + ConfigClan.PcContribution));
                            pc.setPcContribution(ConfigClan.PcContribution);
                            pc.getQuest().set_step(8544, 1);
                            pc.setPcContribution(0);
                        }
                    }
                }
                if (this.getNpcTemplate().get_npcbroad() == 1) {
                    World.get().broadcastPacketToAll(new S_SystemMessage("\\fY玩家[:" + pc.getName() + "] 使用:[" + pc.getWeapon().getLogName() + "]\n\\fT☆擊殺:( " + this.getName() + " )☆"));
                }
                if (pc.getATK_ai()) {
                    pc.setATK_ai(false);
                    pc.setSkillEffect(7902, 2000);
                }
                if (QuestMobSet.START && !this.isResurrect()) {
                    ServerQuestMobTable.get().checkQuestMob(pc, this.getNpcId());
                }
                int Npc_Mid = this.getNpcTemplate().get_npcId();
                if (Npc_Mid == L1Mon.CheckNpcMid(Npc_Mid)) {
                    L1Mon mon = L1MonTable.get().getMon(Npc_Mid);
                    if (mon.getX1() > 0) {
                        L1Teleport.teleport(pc, mon.getX1(), mon.getY1(), (short)mon.getMap1(), 5, true);
                    }
                    if (mon.getDorplostid() > 0) {
                        if (pc.getnpcdmg() > (double)mon.getpcdmg()) {
                            int itemid = mon.getDorplostid();
                            L1ItemInstance item = ItemTable.get().createItem(itemid);
                            pc.getInventory().storeItem(item);
                            String a = this.getNpcTemplate().get_name();
                            String b = item.getName();
                            String c = pc.getName();
                            World.get().broadcastPacketToAll(new S_SystemMessage("\\fU恭喜：[" + c + "],神尾刀【" + a + "】"));
                            World.get().broadcastPacketToAll(new S_SystemMessage("\\fU獲得獎勵【" + b + "】"));
                            bossbox("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "(尾刀)擊敗" + "【 + " + this.getName() + "】時間:" + new Timestamp(System.currentTimeMillis()) + ")。");
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage("\\fU由於您打擊傷害,未達標準。"));
                            pc.sendPackets(new S_ServerMessage("\\fU將不發放擊殺[尾刀]寶相"));
                        }
                    }
                }
                if (Npc_Mid == L1Mon.CheckNpcMid(Npc_Mid)) {
                    L1Mon mon = L1MonTable.get().getMon(Npc_Mid);
                    if (mon.getDorpid() > 0 || mon.getX() > 0) {
                        for (L1Object tgobj : World.get().getVisibleObjects(this, 15)) {
                            if (tgobj instanceof L1PcInstance) {
                                L1PcInstance tgpc = (L1PcInstance)tgobj;
                                if (mon.getX() > 0) {
                                    L1Teleport.teleport(tgpc, mon.getX(), mon.getY(), (short)mon.getMap(), 5, true);
                                }
                                if (mon.getDorpid() > 0) {
                                    if (tgpc.getnpcdmg() > (double)mon.getpcdmg()) {
                                        int itemid2 = mon.getDorpid();
                                        L1ItemInstance item2 = ItemTable.get().createItem(itemid2);
                                        tgpc.getInventory().storeItem(item2);
                                        String b2 = item2.getName();
                                        tgpc.sendPackets(new S_ServerMessage("\\fT本次傷害達標準,獲得獎勵【" + b2 + "】"));
                                        if (tgpc.getnpcdmg() > 0.0) {
                                            tgpc.setnpcdmg(0.0);
                                            tgpc.setnpciddmg(0);
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                    else {
                                        tgpc.sendPackets(new S_ServerMessage("\\fU本次打擊傷害,未達標準。"));
                                        tgpc.sendPackets(new S_ServerMessage("\\fU將不發放擊殺寶相"));
                                        if (tgpc.getnpcdmg() > 0.0) {
                                            tgpc.setnpcdmg(0.0);
                                            tgpc.setnpciddmg(0);
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                               else {
                                    continue;
                                }
                            }
                        }
                    }
                }
                if (pc.getnpcdmg() > 0.0) {
                    pc.setnpcdmg(0.0);
                    pc.setnpciddmg(0);
                }
                if (pc.get_other3().get_type1() > 0 && !this.isResurrect()) {
                    ServerQuestMaPTable.check(pc);
                }
                if (this.isDead()) {
                    // 修正：移除此處的舊強化邏輯，統一由 Death Runnable 中的 enhanceOnDeath() 處理
                    this.distributeDrop();
                    this.giveKarma(pc);
                    if (pc.getAu_AutoSet(0) == 0 && pc.getAutoX() == 0 && pc.getAutoY() == 0 && pc.isActived() && pc.getMap().isTeleportable()) {
                        pc.setSkillEffect(8853, 60000);
                    }
                }
            }
            return;
        }
    }


    
    public static void bossbox(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("玩家紀錄/尾刀王紀錄.txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void distributeDrop() {
        ArrayList<L1Character> dropTargetList = this._dropHateList.toTargetArrayList();
        ArrayList<Integer> dropHateList = this._dropHateList.toHateArrayList();
        try {
            DropShareExecutor dropShareExecutor = new DropShare();
            dropShareExecutor.dropShare(this, dropTargetList, dropHateList);
        }
        catch (Exception e) {
            L1MonsterInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void giveKarma(L1PcInstance pc) {
        int karma = this.getKarma();
        if (karma != 0) {
            int karmaSign = Integer.signum(karma);
            int pcKarmaLevel = pc.getKarmaLevel();
            int pcKarmaLevelSign = Integer.signum(pcKarmaLevel);
            if (pcKarmaLevelSign != 0 && karmaSign != pcKarmaLevelSign) {
                karma *= 5;
            }
            pc.addKarma((int)((double)karma * ConfigRate.RATE_KARMA));
        }
    }
    
    private void giveUbSeal() {
        if (this.getUbSealCount() != 0) {
            L1UltimateBattle ub = UBTable.getInstance().getUb(this.getUbId());
            if (ub != null) {
                L1PcInstance[] membersArray;
                for (int length = (membersArray = ub.getMembersArray()).length, i = 0; i < length; ++i) {
                    L1PcInstance pc = membersArray[i];
                    if (pc != null && !pc.isDead() && !pc.isGhost()) {
                        L1ItemInstance item = pc.getInventory().storeItem(41402, (long)this.getUbSealCount());
                        pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                    }
                }
            }
        }
    }
    
    public boolean is_storeDroped() {
        return this._storeDroped;
    }
    
    public void set_storeDroped(boolean flag) {
        this._storeDroped = flag;
    }
    
    public int getUbSealCount() {
        return this._ubSealCount;
    }
    
    public void setUbSealCount(int i) {
        this._ubSealCount = i;
    }
    
    public int getUbId() {
        return this._ubId;
    }
    
    public void setUbId(int i) {
        this._ubId = i;
    }
    
    private void hide() {
        int npcid = this.getNpcTemplate().get_npcId();
        int rnd;
        switch (npcid) {
            case 45061:
            case 45161:
            case 45181:
            case 45455: {
                if (this.getMaxHp() / 3 > this.getCurrentHp()) {
                    rnd = L1MonsterInstance._random.nextInt(10);
                    if (rnd < 1) {
                        this.allTargetClear();
                        this.setHiddenStatus(1);
                        this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 11));
                        this.setStatus(13);
                        this.broadcastPacketAll(new S_NPCPack(this));
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
            case 45067:
            case 45090:
            case 45264:
            case 45321:
            case 45445:
            case 45452: {
                if (this.getMaxHp() / 3 > this.getCurrentHp()) {
                    rnd = L1MonsterInstance._random.nextInt(10);
                    if (rnd < 1) {
                        this.allTargetClear();
                        this.setHiddenStatus(2);
                        this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 44));
                        this.setStatus(4);
                        this.broadcastPacketAll(new S_NPCPack(this));
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
            case 45682: {
                if (this.getMaxHp() / 3 > this.getCurrentHp()) {
                    rnd = L1MonsterInstance._random.nextInt(100);
                    if (rnd < 1) {
                        this.allTargetClear();
                        this.setHiddenStatus(1);
                        this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 20));
                        this.setStatus(20);
                        this.broadcastPacketAll(new S_NPCPack(this));
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
            case 97259: {
                if (this.getMaxHp() / 3 > this.getCurrentHp()) {
                    rnd = L1MonsterInstance._random.nextInt(100);
                    if (rnd < 1) {
                        this.allTargetClear();
                        this.setHiddenStatus(1);
                        this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 11));
                        this.setStatus(11);
                        this.broadcastPacketAll(new S_NPCPack(this));
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
            case 46107:
            case 46108:
                Label_0506: {
                    if (this.getMaxHp() / 4 > this.getCurrentHp()) {
                        rnd = L1MonsterInstance._random.nextInt(10);
                        if (rnd < 1) {
                            this.allTargetClear();
                            this.setHiddenStatus(1);
                            this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 11));
                            this.setStatus(13);
                            this.broadcastPacketAll(new S_NPCPack(this));
                            break Label_0506;
                        }
                        else {
                            break Label_0506;
                        }
                    }
                    else {
                        break Label_0506;
                    }
                }
            case 105078: {
                if (this.getMaxHp() / 3 > this.getCurrentHp()) {
                    rnd = L1MonsterInstance._random.nextInt(100);
                    if (rnd < 2) {
                        this.allTargetClear();
                        this.setHiddenStatus(1);
                        this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 4));
                        this.setStatus(4);
                        this.broadcastPacketAll(new S_NPCPack(this));
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
    }

     // 處理怪物隱藏狀態，暫時註解以避免內掛避開
    public void initHide() {
        int npcid = this.getNpcTemplate().get_npcId();
        int rnd = L1MonsterInstance._random.nextInt(3);
        switch (npcid) {
            case 45045:
            case 45126:
            case 45134:
            case 45281: {
                if (1 > rnd) {
                    this.setHiddenStatus(1);
                    this.setStatus(4);
                    break;
                }
                else {
                    break;
                }
            }
            case 45061:
            case 45161:
            case 45181:
            case 45455: {
                if (1 > rnd) {
                    this.setHiddenStatus(1);
                    this.setStatus(13);
                    break;
                }
                else {
                    break;
                }
            }
            case 45067:
            case 45090:
            case 45264:
            case 45321:
            case 45445:
            case 45452: {
                this.setHiddenStatus(2);
                this.setStatus(4);
                break;
            }
            case 45681: {
                this.setHiddenStatus(2);
                this.setStatus(11);
                break;
            }
            case 46107:
            case 46108: {
                if (1 > rnd) {
                    this.setHiddenStatus(1);
                    this.setStatus(13);
                    break;
                }
                else {
                    break;
                }
            }
            case 46126:
            case 46127: {
                this.setHiddenStatus(3);
                this.setStatus(4);
                break;
            }
            case 97259: {
                this.setHiddenStatus(1);
                this.setStatus(11);
                break;
            }
        }
    }
    
    public void initHideForMinion(L1NpcInstance leader) {
        int npcid = this.getNpcTemplate().get_npcId();
        if (leader.getHiddenStatus() == 1) {
            switch (npcid) {
                case 45045:
                case 45126:
                case 45134:
                case 45281: {
                    this.setHiddenStatus(1);
                    this.setStatus(4);
                    break;
                }
                case 45061:
                case 45161:
                case 45181:
                case 45455: {
                    this.setHiddenStatus(1);
                    this.setStatus(13);
                    break;
                }
                case 46107:
                case 46108: {
                    this.setHiddenStatus(1);
                    this.setStatus(13);
                    break;
                }
            }
        }
        else if (leader.getHiddenStatus() == 2) {
            switch (npcid) {
                case 45067:
                case 45090:
                case 45264:
                case 45321:
                case 45445:
                case 45452: {
                    this.setHiddenStatus(2);
                    this.setStatus(4);
                    break;
                }
                case 45681: {
                    this.setHiddenStatus(2);
                    this.setStatus(11);
                    break;
                }
                case 46125:
                case 46126:
                case 46127:
                case 46128: {
                    this.setHiddenStatus(3);
                    this.setStatus(4);
                    break;
                }
            }
        }
    }
    
    @Override
    public void transform(int transformId) {
        super.transform(transformId);
        this.getInventory().clearItems();
        SetDropExecutor setDropExecutor = new SetDrop();
        setDropExecutor.setDrop(this, this.getInventory());
        this.getInventory().shuffle();
    }
    
    private final void sendServerMessage(int msgid) {
        L1QuestUser quest = WorldQuest.get().get(this.get_showId());
        if (quest != null && !quest.pcList().isEmpty()) {
            for (L1PcInstance pc : quest.pcList()) {
                pc.sendPackets(new S_ServerMessage(msgid));
            }
        }
    }
    
    public long getLastHprTime() {
        if (this._lasthprtime == 0L) {
            return this._lasthprtime = System.currentTimeMillis() / 1000L - 5L;
        }
        else {
            return this._lasthprtime;
        }
    }
    
    public void setLastHprTime(long time) {
        this._lasthprtime = time;
    }
    
    public long getLastMprTime() {
        if (this._lastmprtime == 0L) {
            return this._lastmprtime = System.currentTimeMillis() / 1000L - 5L;
        }
        else {
            return this._lastmprtime;
        }
    }
    
    public void setLastMprTime(long time) {
        this._lastmprtime = time;
    }
    
    private final class CountDownTimer extends TimerTask
    {
        private final int _loc_x;
        private final int _loc_y;
        private final short _loc_mapId;
        private final L1QuestUser _quest;
        private final int _firstMsgId;
        
        public CountDownTimer(int loc_x, int loc_y, short loc_mapId, L1QuestUser quest, int firstMsgId) {
            this._loc_x = loc_x;
            this._loc_y = loc_y;
            this._loc_mapId = loc_mapId;
            this._quest = quest;
            this._firstMsgId = firstMsgId;
        }
        
        @Override
        public void run() {
            try {
                if (this._firstMsgId != 0) {
                    L1MonsterInstance.this.sendServerMessage(this._firstMsgId);
                }
                Thread.sleep(10000L);
                L1MonsterInstance.this.sendServerMessage(1476);
                Thread.sleep(10000L);
                L1MonsterInstance.this.sendServerMessage(1477);
                Thread.sleep(10000L);
                L1MonsterInstance.this.sendServerMessage(1478);
                Thread.sleep(5000L);
                L1MonsterInstance.this.sendServerMessage(1480);
                Thread.sleep(1000L);
                L1MonsterInstance.this.sendServerMessage(1481);
                Thread.sleep(1000L);
                L1MonsterInstance.this.sendServerMessage(1482);
                Thread.sleep(1000L);
                L1MonsterInstance.this.sendServerMessage(1483);
                Thread.sleep(1000L);
                L1MonsterInstance.this.sendServerMessage(1484);
                Thread.sleep(1000L);
                for (int i = 10; i > 0; --i) {
                    if (this._quest != null && !this._quest.pcList().isEmpty()) {
                        for (L1PcInstance pc : this._quest.pcList()) {
                            L1Teleport.teleport(pc, this._loc_x, this._loc_y, this._loc_mapId, pc.getHeading(), true);
                        }
                    }
                    Thread.sleep(500L);
                }
            }
            catch (Exception ex) {}
        }
    }
    
    class Death implements Runnable
    {
        L1Character _lastAttacker;
        
        public Death(L1Character lastAttacker) {
            this._lastAttacker = lastAttacker;
        }
        
        @Override
        public void run() {
            L1MonsterInstance mob = L1MonsterInstance.this;
            if (this._lastAttacker instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)this._lastAttacker;
                pc.setKillCount(pc.getKillCount() + 1);
                pc.sendPackets(new S_OwnCharStatus(pc));
                if (mob.getUbId() != 0) {
                    int ubexp = (int)(L1MonsterInstance.this.getExp() / 10L);
                    pc.setUbScore(pc.getUbScore() + ubexp);
                }
            }
            mob.setDeathProcessing(true);
            mob.setCurrentHpDirect(0);
            mob.setDead(true);
            mob.setStatus(8);
            mob.broadcastPacketAll(new S_DoActionGFX(mob.getId(), 8));
            mob.getMap().setPassable(mob.getLocation(), true);
            mob.startChat(1);
            mob.distributeExpDropKarma(this._lastAttacker);
            mob.enhanceOnDeath();     // 修正：在死亡時呼叫強化邏輯
            mob.giveUbSeal();
            mob.setDeathProcessing(false);
            mob.setExp(0L);
            mob.setKarma(0);
            mob.allTargetClear();
            switch (mob.getNpcId()) {
                case 46123:
                case 46124: {
                    if (L1MonsterInstance.this.getMapId() == 782 && WorldMob.get().getCount((int)L1MonsterInstance.this.getMapId(), 46124) == 0 && WorldMob.get().getCount((int)L1MonsterInstance.this.getMapId(), 46123) == 0) {
                        World.get().broadcastPacketToAll(new S_ServerMessage(1470));
                        ServerCrockTable.get().updateKillTime();
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 92000:
                case 92001: {
                    if (L1MonsterInstance.this.getMapId() == 784 && WorldMob.get().getCount((int)L1MonsterInstance.this.getMapId(), 92000) == 0 && WorldMob.get().getCount((int)L1MonsterInstance.this.getMapId(), 92001) == 0) {
                        World.get().broadcastPacketToAll(new S_ServerMessage(1470));
                        ServerCrockTable.get().updateKillTime();
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            int deltime = 0;
            switch (mob.getNpcId()) {
                case 71016:
                case 71028:
                case 97206: {
                    deltime = 60;
                    break;
                }
                default: {
                    deltime = ConfigAlt.NPC_DELETION_TIME;
                    break;
                }
            }
            mob.startDeleteTimer(deltime);
        }
    }
    
    private class deathDragonTimer1 extends TimerTask
    {
        private final L1MonsterInstance npc;
        private final short mapId;
        
        public deathDragonTimer1(L1MonsterInstance paramShort, short arg3) {
            this.npc = paramShort;
            this.mapId = arg3;
        }
        
        @Override
        public void run() {
            try {
                if (this.npc.getNpcId() == 71014) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1573);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1574);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1575);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1576);
                    Thread.sleep(10000L);
                    int i = 32776 + L1MonsterInstance._random.nextInt(20);
                    int k = 32679 + L1MonsterInstance._random.nextInt(20);
                    Thread.sleep(5000L);
                    L1Location loc = new L1Location(i, k, (int)this.mapId);
                    L1SpawnUtil.spawn(71015, loc, new Random().nextInt(8), L1MonsterInstance.this.get_showId());
                }
                else if (this.npc.getNpcId() == 71015) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1577);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1578);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1579);
                    Thread.sleep(10000L);
                    int j = 32776 + L1MonsterInstance._random.nextInt(20);
                    int m = 32679 + L1MonsterInstance._random.nextInt(20);
                    Thread.sleep(5000L);
                    L1Location loc = new L1Location(j, m, (int)this.mapId);
                    L1SpawnUtil.spawn(71016, loc, new Random().nextInt(8), L1MonsterInstance.this.get_showId());
                }
                else if (this.npc.getNpcId() == 71016) {
                    for (L1PcInstance pc : World.get().getVisiblePlayer(this.npc)) {
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7854));
                        SkillMode mode = L1SkillMode.get().getSkill(6797);
                        if (mode != null) {
                            mode.start(pc, null, null, 86400);
                        }
                    }
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1580);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1581);
                    Thread.sleep(10000L);
                    GeneralThreadPool.get().execute(new L1MonsterInstance.CountDownTimer(33718, 32506, (short)4, WorldQuest.get().get(L1MonsterInstance.this.get_showId()), 0));
                }
            }
            catch (Exception ex) {}
        }
    }
    
    private class deathDragonTimer2 extends TimerTask
    {
        private final L1MonsterInstance npc;
        private final short mapId;
        
        public deathDragonTimer2(L1MonsterInstance paramShort, short arg3) {
            this.npc = paramShort;
            this.mapId = arg3;
        }
        
        @Override
        public void run() {
            try {
                if (this.npc.getNpcId() == 71026) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1661);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1662);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1663);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1664);
                    Thread.sleep(10000L);
                    int j = 32948 + L1MonsterInstance._random.nextInt(20);
                    int m = 32825 + L1MonsterInstance._random.nextInt(20);
                    L1Location loc = new L1Location(j, m, (int)this.mapId);
                    L1SpawnUtil.spawn(71027, loc, new Random().nextInt(8), L1MonsterInstance.this.get_showId());
                }
                else if (this.npc.getNpcId() == 71027) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1665);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1666);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1667);
                    Thread.sleep(10000L);
                    int k = 32948 + L1MonsterInstance._random.nextInt(20);
                    int n = 32825 + L1MonsterInstance._random.nextInt(20);
                    L1Location loc = new L1Location(k, n, (int)this.mapId);
                    L1SpawnUtil.spawn(71028, loc, new Random().nextInt(8), L1MonsterInstance.this.get_showId());
                }
                else if (this.npc.getNpcId() == 71028) {
                    ArrayList<L1Character> targetList = this.npc.getHateList().toTargetArrayList();
                    if (!targetList.isEmpty()) {
                        for (L1Character cha : targetList) {
                            if (cha instanceof L1PcInstance) {
                                L1PcInstance pc = (L1PcInstance)cha;
                                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7854));
                                SkillMode mode = L1SkillMode.get().getSkill(6798);
                                if (mode != null) {
                                    mode.start(pc, null, null, 86400);
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                    }
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1668);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1669);
                    Thread.sleep(10000L);
                    GeneralThreadPool.get().execute(new L1MonsterInstance.CountDownTimer(33718, 32506, (short)4, WorldQuest.get().get(L1MonsterInstance.this.get_showId()), 0));
                }
            }
            catch (Exception ex) {}
        }
    }

    private class deathDragonTimer3 extends TimerTask
    {
        private final L1MonsterInstance npc;
        
        public deathDragonTimer3(L1MonsterInstance npc) {
            this.npc = npc;
        }
        
        @Override
        public void run() {
            try {
                if (this.npc.getNpcId() == 97204) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1759);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1760);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1761);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1762);
                    Thread.sleep(10000L);
                    L1Location loc = new L1Location(32846, 32878, (int)this.npc.getMapId()).randomLocation(10, true);
                    L1SpawnUtil.spawn(97205, loc, 0, this.npc.get_showId());
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1763);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1764);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1765);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1766);
                    Thread.sleep(10000L);
                    Thread.sleep(5000L);
                    L1Location loc2 = new L1Location(32846, 32877, (int)this.npc.getMapId()).randomLocation(10, true);
                    L1SpawnUtil.spawn(97206, loc2, 0, this.npc.get_showId());
                }
                else if (this.npc.getNpcId() == 97206) {
                    for (L1PcInstance pc : World.get().getVisiblePlayer(this.npc)) {
                        pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 7854));
                        SkillMode mode = L1SkillMode.get().getSkill(6799);
                        if (mode != null) {
                            mode.start(pc, null, null, 86400);
                        }
                    }
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1772);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1773);
                    Thread.sleep(10000L);
                    GeneralThreadPool.get().execute(new L1MonsterInstance.CountDownTimer(33718, 32506, (short)4, WorldQuest.get().get(L1MonsterInstance.this.get_showId()), 0));
                }
            }
            catch (Exception ex) {}
        }
    }
private int _hpr = 0;
private int _damageReduction = 0;

// --- HPR控制 ---
public int getHpr() {
    return _hpr;
}

public void setHpr(int val) {
    _hpr = val;
}

// --- 傷害減免控制 ---
public int getDamageReduction() {
    return _damageReduction;
}

public void setDamageReduction(int val) {
    _damageReduction = val;
}

/**
 * 怪物死亡時的強化處理
 */
private void enhanceOnDeath() {
    L1MonsterEnhanceInstance mei = MonsterEnhanceTable.getInstance().getTemplate(getNpcId());
    if (mei == null) {
        return; // 沒有此怪物的強化設定
    }

    // 死亡次數 +1
    int currentDc = mei.getCurrentDc() + 1;
    mei.setCurrentDc(currentDc);

    // 檢查是否達到重置門檻 (death_reset)
    int resetCount = mei.getDcEnhance1(); // 在 MonsterEnhanceTable 中，death_reset 被對應到 DcEnhance1
    if (resetCount > 0 && currentDc >= resetCount) {
        // 重置所有強化數值與死亡次數
        mei.setCurrentDc(0);
        // 這邊可以選擇是否要將 add_hp 等百分比也歸零，目前設計為不歸零，僅重置計數
        World.get().broadcastPacketToAll(new S_SystemMessage(this.getName() + " 的力量似乎被削弱，回到了最初的狀態。"));
    }
    // 檢查是否達到強化門檻 (death_enhance)
    else if (mei.getDcEnhance() > 0 && currentDc % mei.getDcEnhance() == 0) {
        // 顯示強化訊息
        World.get().broadcastPacketToAll(new S_SystemMessage(this.getName() + "下次重生各項能力提升(重啟重置)"));
    }

    // 將更新後的死亡次數存回資料庫
    MonsterEnhanceTable.getInstance().save(mei);
}

/**
 * 怪物生成時套用強化屬性 (百分比計算)
 */
private void applyEnhance() {
    L1MonsterEnhanceInstance mei = MonsterEnhanceTable.getInstance().getTemplate(getNpcId());
    if (mei == null) {
        return; // 沒有強化設定
    }

    // 計算需要強化的次數
    int enhanceCount = 0;
    if (mei.getDcEnhance() > 0) {
        enhanceCount = mei.getCurrentDc() / mei.getDcEnhance();
    }

    if (enhanceCount <= 0) {
        return; // 尚未達到強化次數
    }

    // 設定強化後的名稱
    String originalName = getNpcTemplate().get_name();
    String enhanceSuffix = "\\fU[強化" + enhanceCount + "次]"; // \fU 是紫色的顏色代碼
    setName(originalName + enhanceSuffix);
    setNameId(originalName + enhanceSuffix);

    // 套用各項屬性強化 (百分比)
    // HP
    double hpBonus = enhanceCount * mei.getHp() / 100.0;
    int newHp = (int) (mei.getOriginalHp() * (1 + hpBonus));
    setMaxHp(newHp);
    setCurrentHp(newHp);

    // MP
    double mpBonus = enhanceCount * mei.getMp() / 100.0;
    int newMp = (int) (mei.getOriginalMp() * (1 + mpBonus));
    setMaxMp(newMp);
    setCurrentMp(newMp);

    // AC (AC是負值，所以用減法)
    double acBonus = enhanceCount * mei.getAc();
    int newAc = (int) (mei.getOriginalAc() + acBonus); // 例如原始-80，設定-1，強化10次後變為 -80 + (10 * -1) = -90
    setAc(newAc);

    // 傷害減免
    double dmgReducBonus = enhanceCount * mei.getDamageReduction() / 100.0;
    int newDmgReduc = (int) (mei.getOriginalDamageReduction() * (1 + dmgReducBonus));
    setDamageReduction(newDmgReduc);

    // 其他屬性 (以等級為例)
    double levelBonus = enhanceCount * mei.getLevel() / 100.0;
    int newLevel = (int) (mei.getOriginalLevel() * (1 + levelBonus));
    setLevel(newLevel);
    
    // 力量 (STR)
    double strBonus = enhanceCount * mei.getStr() / 100.0;
    setStr((int) (mei.getOriginalStr() * (1 + strBonus)));

    // 敏捷 (DEX)
    double dexBonus = enhanceCount * mei.getDex() / 100.0;
    setDex((int) (mei.getOriginalDex() * (1 + dexBonus)));

    // 體質 (CON)
    double conBonus = enhanceCount * mei.getCon() / 100.0;
    setCon((int) (mei.getOriginalCon() * (1 + conBonus)));

    // 精神 (WIS)
    double wisBonus = enhanceCount * mei.getWis() / 100.0;
    setWis((int) (mei.getOriginalWis() * (1 + wisBonus)));

    // 智力 (INT)
    double intBonus = enhanceCount * mei.getInt() / 100.0;
    setInt((int) (mei.getOriginalInt() * (1 + intBonus)));

    // 魔防 (MR)
    double mrBonus = enhanceCount * mei.getMr() / 100.0;
    setMr((int) (mei.getOriginalMr() * (1 + mrBonus)));

    // 回血 (HPR)
    double hprBonus = enhanceCount * mei.getHpr() / 100.0;
    setHpr((int) (mei.getOriginalHpr() * (1 + hprBonus)));
}

}