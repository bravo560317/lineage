package com.lineage.server.model;

import java.util.Collection;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.serverpackets.S_Light;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.utils.RangeInt;
import com.lineage.server.utils.IntRange;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.serverpackets.S_PetCtrlMenu;
import com.lineage.server.model.Instance.L1PetInstance;
import java.util.Set;
import com.lineage.server.model.skill.L1SkillStop;
import com.lineage.server.model.skill.L1SkillTimerCreator;
import com.lineage.server.types.Point;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_HPMeter;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1EffectInstance;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1HierarchInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1FollowerInstance;
import com.lineage.server.model.skill.L1SkillTimer;
import java.util.HashMap;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.Map;
import com.lineage.server.model.poison.L1Poison;
import org.apache.commons.logging.Log;

public class L1Character extends L1Object
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    private L1Poison _poison;
    private boolean _sleeped;
    private final Map<Integer, L1NpcInstance> _petlist;
    private final HashMap<Integer, L1SkillTimer> _skillEffect;
    private final Map<Integer, L1ItemDelay.ItemDelayTimer> _itemdelay;
    private final Map<Integer, L1FollowerInstance> _followerlist;
    private int _secHp;
    private int _currentHp;
    private int _currentMp;
    private boolean _paralyzed;
    L1Paralysis _paralysis;
    protected static final byte[] HEADING_TABLE_X;
    protected static final byte[] HEADING_TABLE_Y;
    private boolean _isSkillDelay;
    private final Map<Integer, L1DollInstance> _dolls;
    private long _exp;
    private final List<L1Object> _knownObjects;
    private final List<L1PcInstance> _knownPlayer;
    private String _name;
    private int _level;
    private int _maxHp;
    private int _trueMaxHp;
    private int _maxMp;
    private int _trueMaxMp;
    private int _ac;
    private int _trueAc;
    private short _str;
    private short _trueStr;
    private short _con;
    private short _trueCon;
    private short _dex;
    private short _trueDex;
    private short _cha;
    private short _trueCha;
    private short _int;
    private short _trueInt;
    private short _wis;
    private short _trueWis;
    private int _wind;
    private int _trueWind;
    private int _water;
    private int _trueWater;
    private int _fire;
    private int _trueFire;
    private int _earth;
    private int _trueEarth;
    private int _addAttrKind;
    private int _registStun;
    private int _trueRegistStun;
    private int _registStone;
    private int _trueRegistStone;
    private int _registSleep;
    private int _trueRegistSleep;
    private int _registFreeze;
    private int _trueRegistFreeze;
    private int _registSustain;
    private int _trueRegistSustain;
    private int _registBlind;
    private int _trueRegistBlind;
    private int _dmgup;
    private int _bowDmgup;
    private int _hitup;
    private int _trueHitup;
    private int _bowHitup;
    private int _trueBowHitup;
    private int _mr;
    private int _trueMr;
    private int _sp;
    private boolean _isDead;
    private int _status;
    private String _title;
    private int _lawful;
    private int _heading;
    private int _moveSpeed;
    private int _braveSpeed;
    private int _tempCharGfx;
    private int _gfxid;
    private int _karma;
    private int _chaLightSize;
    private int _ownLightSize;
    private int _tmp_targetid;
    private int _tmp_mr;
    private int _dodge_up;
    private int _dodge_down;
    private boolean _decay_potion;
    private int _innRoomNumber;
    private boolean _isHall;
    private L1DollInstance _usingdoll;
    private L1DollInstance _power_doll;
    private int _trueSp;
    private L1HierarchInstance _Hierarch;
    private int _hierarch;
    private ArrayList<L1EffectInstance> _TrueTargetEffectList;
    private boolean _TripleArrow;
    private int _cancellation;
    private int _double_score;
    private int _poisonStatus2;
    private int _poisonStatus7;
    private boolean _isPinkName;
    private int _useitemobjid;
    
    static {
        _log = LogFactory.getLog(L1Character.class);
        HEADING_TABLE_X = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        HEADING_TABLE_Y = new byte[] { -1, -1, 0, 1, 1, 1, 0, -1 };
    }
    
    public L1Character() {
        this._poison = null;
        this._petlist = new HashMap();
        this._skillEffect = new HashMap();
        this._itemdelay = new HashMap();
        this._followerlist = new HashMap();
        this._secHp = -1;
        this._isSkillDelay = false;
        this._dolls = new HashMap();
        this._knownObjects = new CopyOnWriteArrayList();
        this._knownPlayer = new CopyOnWriteArrayList();
        this._maxHp = 0;
        this._trueMaxHp = 0;
        this._maxMp = 0;
        this._trueMaxMp = 0;
        this._ac = 10;
        this._trueAc = 0;
        this._str = 0;
        this._trueStr = 0;
        this._con = 0;
        this._trueCon = 0;
        this._dex = 0;
        this._trueDex = 0;
        this._cha = 0;
        this._trueCha = 0;
        this._int = 0;
        this._trueInt = 0;
        this._wis = 0;
        this._trueWis = 0;
        this._wind = 0;
        this._trueWind = 0;
        this._water = 0;
        this._trueWater = 0;
        this._fire = 0;
        this._trueFire = 0;
        this._earth = 0;
        this._trueEarth = 0;
        this._registStun = 0;
        this._trueRegistStun = 0;
        this._registStone = 0;
        this._trueRegistStone = 0;
        this._registSleep = 0;
        this._trueRegistSleep = 0;
        this._registFreeze = 0;
        this._trueRegistFreeze = 0;
        this._registSustain = 0;
        this._trueRegistSustain = 0;
        this._registBlind = 0;
        this._trueRegistBlind = 0;
        this._dmgup = 0;
        this._bowDmgup = 0;
        this._hitup = 0;
        this._trueHitup = 0;
        this._bowHitup = 0;
        this._trueBowHitup = 0;
        this._mr = 0;
        this._trueMr = 0;
        this._sp = 0;
        this._dodge_up = 0;
        this._dodge_down = 0;
        this._decay_potion = false;
        this._power_doll = null;
        this._trueSp = 0;
        this._Hierarch = null;
        this._hierarch = 1;
        this._TrueTargetEffectList = new ArrayList();
        this._TripleArrow = false;
        this._cancellation = 0;
        this._double_score = 0;
        this._isPinkName = false;
        this._level = 1;
    }
    
    public int targetReverseHeading(L1Character cha) {
        int heading = cha.getHeading();
        heading += 4;
        if (heading > 7) {
            heading -= 8;
        }
        return heading;
    }
    
    public void resurrect(int hp) {
        try {
            if (!this.isDead()) {
                return;
            }
            else {
                if (hp <= 0) {
                    hp = 1;
                }
                this.setDead(false);
                this.setCurrentHp(hp);
                this.setStatus(0);
                L1PolyMorph.undoPoly(this);
                for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                    pc.sendPackets(new S_RemoveObject(this));
                    pc.removeKnownObject(this);
                    pc.updateObject();
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void broadcastPacketHP(L1PcInstance pc) {
        try {
            if (this._secHp != this.getCurrentHp()) {
                this._secHp = this.getCurrentHp();
                if (!pc.isActived()) {
                    pc.sendPackets(new S_HPMeter(this));
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int getCurrentHp() {
        return this._currentHp;
    }
    
    public void setCurrentHp(int i) {
        this._currentHp = i;
        if (this._currentHp >= this.getMaxHp()) {
            this._currentHp = this.getMaxHp();
        }
    }
    
    public void setCurrentHpDirect(int i) {
        this._currentHp = i;
    }
    
    public int getCurrentMp() {
        return this._currentMp;
    }
    
    public void setCurrentMp(int i) {
        this._currentMp = i;
        if (this._currentMp >= this.getMaxMp()) {
            this._currentMp = this.getMaxMp();
        }
    }
    
    public void setCurrentMpDirect(int i) {
        this._currentMp = i;
    }
    
    public boolean isSleeped() {
        return this._sleeped;
    }
    
    public void setSleeped(boolean sleeped) {
        this._sleeped = sleeped;
    }
    
    public boolean isParalyzedX() {
        return this.hasSkillEffect(33) || this.hasSkillEffect(1011) || this.hasSkillEffect(1009) || this.hasSkillEffect(66) || this.hasSkillEffect(87) || this.hasSkillEffect(212) || this.hasSkillEffect(50) || this.hasSkillEffect(157) || this.hasSkillEffect(103) || this.hasSkillEffect(208);
    }
    
    public boolean isParalyzed() {
        return this._paralyzed;
    }
    
    public void setParalyzed(boolean paralyzed) {
        this._paralyzed = paralyzed;
    }
    
    public L1Paralysis getParalysis() {
        return this._paralysis;
    }
    
    public void setParalaysis(L1Paralysis p) {
        this._paralysis = p;
    }
    
    public void cureParalaysis() {
        if (this._paralysis != null) {
            this._paralysis.cure();
        }
    }
    
    public void broadcastPacketAll(ServerBasePacket packet) {
        try {
            for (L1PcInstance pc : World.get().getVisiblePlayer(this)) {
                if (pc.get_showId() == this.get_showId()) {
                    pc.sendPackets(packet);
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void broadcastPacketYN(ServerBasePacket packet) {
        try {
            for (L1PcInstance pc : World.get().getVisiblePlayer(this)) {
                if (pc.get_showId() == this.get_showId() && pc.getopengfxid()) {
                    pc.sendPackets(packet);
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void broadcastPacketX10(ServerBasePacket packet) {
        try {
            for (L1PcInstance pc : World.get().getVisiblePlayer(this, 10)) {
                if (pc.get_showId() == this.get_showId()) {
                    pc.sendPackets(packet);
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void broadcastPacketX8(ServerBasePacket packet) {
        try {
            for (L1PcInstance pc : World.get().getVisiblePlayer(this, 8)) {
                if (pc.get_showId() == this.get_showId()) {
                    pc.sendPackets(packet);
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void broadcastPacketXR(ServerBasePacket packet, int r) {
        try {
            for (L1PcInstance pc : World.get().getVisiblePlayer(this, r)) {
                if (pc.get_showId() == this.get_showId()) {
                    pc.sendPackets(packet);
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void wideBroadcastPacket(ServerBasePacket packet) {
        for (L1PcInstance pc : World.get().getVisiblePlayer(this, 50)) {
            if (pc.get_showId() == this.get_showId()) {
                pc.sendPackets(packet);
            }
        }
    }
    
    public void broadcastPacketExceptTargetSight(ServerBasePacket packet, L1Character target) {
        boolean isX8 = false;
        if (ServerWarExecutor.get().checkCastleWar() > 0) {
            isX8 = true;
        }
        if (isX8) {
            for (L1PcInstance tgpc : World.get().getVisiblePlayerExceptTargetSight(this, target, 8)) {
                tgpc.sendPackets(packet);
            }
        }
        else {
            for (L1PcInstance tgpc : World.get().getVisiblePlayerExceptTargetSight(this, target)) {
                tgpc.sendPackets(packet);
            }
        }
    }
    
    public int[] getFrontLoc() {
        int[] loc = new int[2];
        int x = this.getX();
        int y = this.getY();
        int heading = this.getHeading();
        x += L1Character.HEADING_TABLE_X[heading];
        y += L1Character.HEADING_TABLE_Y[heading];
        loc[0] = x;
        loc[1] = y;
        return loc;
    }
    
    public int targetDirection(int tx, int ty) {
        float dis_x = (float)Math.abs(this.getX() - tx);
        float dis_y = (float)Math.abs(this.getY() - ty);
        float dis = Math.max(dis_x, dis_y);
        if (dis == 0.0f) {
            return this.getHeading();
        }
        else {
            int avg_x = (int)Math.floor((double)(dis_x / dis + 0.59f));
            int avg_y = (int)Math.floor((double)(dis_y / dis + 0.59f));
            int dir_x = 0;
            int dir_y = 0;
            if (this.getX() < tx) {
                dir_x = 1;
            }
            if (this.getX() > tx) {
                dir_x = -1;
            }
            if (this.getY() < ty) {
                dir_y = 1;
            }
            if (this.getY() > ty) {
                dir_y = -1;
            }
            if (avg_x == 0) {
                dir_x = 0;
            }
            if (avg_y == 0) {
                dir_y = 0;
            }
            switch (dir_x) {
                case -1: {
                    switch (dir_y) {
                        case -1: {
                            return 7;
                        }
                        case 0: {
                            return 6;
                        }
                        case 1: {
                            return 5;
                        }
                        default: {
                            break;
                        }
                    }
                }
                case 0: {
                    switch (dir_y) {
                        case -1: {
                            return 0;
                        }
                        case 1: {
                            return 4;
                        }
                        default: {
                            break;
                        }
                    }
                }
                case 1: {
                    switch (dir_y) {
                        case -1: {
                            return 1;
                        }
                        case 0: {
                            return 2;
                        }
                        case 1: {
                            return 3;
                        }
                        default: {
                            break;
                        }
                    }
                }
            }
            return this.getHeading();
        }
    }
    
    public boolean glanceCheck(int tx, int ty) {
        L1Map map = this.getMap();
        int chx = this.getX();
        int chy = this.getY();
        int i = 0;
        while (i < 15 && ((chx != tx || chy != ty) && (chx + 1 != tx || chy - 1 != ty) && (chx + 1 != tx || chy != ty) && (chx + 1 != tx || chy + 1 != ty) && (chx != tx || chy + 1 != ty) && (chx - 1 != tx || chy + 1 != ty) && (chx - 1 != tx || chy != ty) && (chx - 1 != tx || chy - 1 != ty)) && (chx != tx || chy - 1 != ty)) {
            int th = this.targetDirection(tx, ty);
            if (!map.isArrowPassable(chx, chy, th)) {
                return false;
            }
            else {
                if (chx < tx) {
                    if (chy == ty) {
                        ++chx;
                    }
                    else if (chy > ty) {
                        ++chx;
                        --chy;
                    }
                    else if (chy < ty) {
                        ++chx;
                        ++chy;
                    }
                }
                else if (chx == tx) {
                    if (chy < ty) {
                        ++chy;
                    }
                    else if (chy > ty) {
                        --chy;
                    }
                }
                else if (chx > tx) {
                    if (chy == ty) {
                        --chx;
                    }
                    else if (chy < ty) {
                        --chx;
                        ++chy;
                    }
                    else if (chy > ty) {
                        --chx;
                        --chy;
                    }
                }
                ++i;
            }
        }
        return true;
    }
    
    public boolean isAttackPosition(int x, int y, int range) {
        return this.getLocation().getTileLineDistance(new Point(x, y)) <= range && this.glanceCheck(x, y);
    }
    
    public L1Inventory getInventory() {
        return null;
    }
    
    private void addSkillEffect(int skillId, int timeMillis) {
        L1SkillTimer timer = null;
        if (timeMillis > 0) {
            timer = L1SkillTimerCreator.create(this, skillId, timeMillis);
            timer.begin();
        }
        this._skillEffect.put(skillId, timer);
    }
    
    public void setSkillEffect(int skillId, int timeMillis) {
        L1SkillTimer timer = this._skillEffect.get((Integer)skillId);
        if (timer != null) {
            int remainingTimeMills = timer.getRemainingTime();
            timeMillis /= 1000;
            if (remainingTimeMills >= 0 && (remainingTimeMills < timeMillis || timeMillis == 0)) {
                timer.setRemainingTime(timeMillis);
            }
        }
        else {
            this.addSkillEffect(skillId, timeMillis);
        }
    }
    
    public void removeSkillEffect(int skillId) {
        L1SkillTimer timer = this._skillEffect.remove((Integer)skillId);
        if (timer != null) {
            timer.end();
        }
    }
    
    public void removeNoTimerSkillEffect(int skillId) {
        this._skillEffect.remove((Integer)skillId);
        L1SkillStop.stopSkill(this, skillId);
    }
    
    public void clearAllSkill() {
        ArrayList<Integer> effectlist = new ArrayList();
        for (Integer key : this._skillEffect.keySet()) {
            effectlist.add(key);
        }
        for (int i = 0; i < effectlist.size(); ++i) {
            if (this.hasSkillEffect(effectlist.get(i))) {
                this.removeSkillEffect(effectlist.get(i));
            }
        }
        this._skillEffect.clear();
    }
    
    public void killSkillEffectTimer(int skillId) {
        L1SkillTimer timer = this._skillEffect.remove((Integer)skillId);
        if (timer != null) {
            timer.kill();
        }
    }
    
    public void clearSkillEffectTimer() {
        for (L1SkillTimer timer : this._skillEffect.values()) {
            if (timer != null) {
                timer.kill();
            }
        }
        this._skillEffect.clear();
    }
    
    public boolean hasSkillEffect(int skillId) {
        return this._skillEffect.containsKey((Integer)skillId);
    }
    
    public Set<Integer> getSkillEffect() {
        return (Set)this._skillEffect.keySet();
    }
    
    public boolean getSkillisEmpty() {
        return this._skillEffect.isEmpty();
    }
    
    public int getSkillEffectTimeSec(int skillId) {
        L1SkillTimer timer = this._skillEffect.get((Integer)skillId);
        if (timer == null) {
            return -1;
        }
        else {
            return timer.getRemainingTime();
        }
    }
    
    public void setSkillDelay(boolean flag) {
        this._isSkillDelay = flag;
    }
    
    public boolean isSkillDelay() {
        return this._isSkillDelay;
    }
    
    public void addItemDelay(int delayId, L1ItemDelay.ItemDelayTimer timer) {
        this._itemdelay.put(delayId, timer);
    }
    
    public void removeItemDelay(int delayId) {
        this._itemdelay.remove((Integer)delayId);
    }
    
    public boolean hasItemDelay(int delayId) {
        return this._itemdelay.containsKey((Integer)delayId);
    }
    
    public L1ItemDelay.ItemDelayTimer getItemDelayTimer(int delayId) {
        return this._itemdelay.get((Integer)delayId);
    }
    
    public void addPet(L1NpcInstance npc) {
        this._petlist.put(npc.getId(), npc);
        this.sendPetCtrlMenu(npc, true);
    }
    
    public void removePet(L1NpcInstance npc) {
        this._petlist.remove((Integer)npc.getId());
        this.sendPetCtrlMenu(npc, false);
    }
    
    public Map<Integer, L1NpcInstance> getPetList() {
        return this._petlist;
    }
    
    private final void sendPetCtrlMenu(L1NpcInstance npc, boolean type) {
        if (npc instanceof L1PetInstance) {
            L1PetInstance pet = (L1PetInstance)npc;
            L1Character cha = pet.getMaster();
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                pc.sendPackets(new S_PetCtrlMenu(pc, pet, type));
                if (type) {
                    pc.sendPackets(new S_HPMeter(pet));
                }
            }
        }
        else if (npc instanceof L1SummonInstance) {
            L1SummonInstance summon = (L1SummonInstance)npc;
            L1Character cha = summon.getMaster();
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                pc.sendPackets(new S_PetCtrlMenu(pc, summon, type));
                if (type) {
                    pc.sendPackets(new S_HPMeter(summon));
                }
            }
        }
    }
    
    public void setUsingDoll(L1DollInstance doll) {
        this._usingdoll = doll;
    }
    
    public L1DollInstance getUsingDoll() {
        return this._usingdoll;
    }
    
    public void addDoll(L1DollInstance doll) {
        this._dolls.put(doll.getItemObjId(), doll);
    }
    
    public void removeDoll(L1DollInstance doll) {
        this._dolls.remove((Integer)doll.getItemObjId());
    }
    
    public L1DollInstance getDoll(int ItemObjId) {
        return this._dolls.get((Integer)ItemObjId);
    }
    
    public Map<Integer, L1DollInstance> getDolls() {
        return this._dolls;
    }
    
    public void add_power_doll(L1DollInstance doll) {
        this._power_doll = doll;
    }
    
    public void remove_power_doll() {
        this._power_doll = null;
    }
    
    public L1DollInstance get_power_doll() {
        return this._power_doll;
    }
    
    public void addFollower(L1FollowerInstance follower) {
        this._followerlist.put(follower.getId(), follower);
    }
    
    public void removeFollower(L1FollowerInstance follower) {
        this._followerlist.remove((Integer)follower.getId());
    }
    
    public Map<Integer, L1FollowerInstance> getFollowerList() {
        return this._followerlist;
    }
    
    public void setPoison(L1Poison poison) {
        this._poison = poison;
    }
    
    public void curePoison() {
        if (this._poison == null) {
            return;
        }
        else {
            this._poison.cure();
            return;
        }
    }
    
    public L1Poison getPoison() {
        return this._poison;
    }
    
    public void setPoisonEffect(int effectId) {
        this.broadcastPacketX8(new S_Poison(this.getId(), effectId));
    }
    
    public int getZoneType() {
        if (this.getMap().isSafetyZone(this.getLocation())) {
            return 1;
        }
        else if (this.getMap().isCombatZone(this.getLocation())) {
            return -1;
        }
        else {
            return 0;
        }
    }
    
    public boolean isSafetyZone() {
        return this.getMap().isSafetyZone(this.getLocation());
    }
    
    public boolean isCombatZone() {
        return this.getMap().isCombatZone(this.getLocation());
    }
    
    public boolean isNoneZone() {
        return this.getZoneType() == 0;
    }
    
    public long getExp() {
        return this._exp;
    }
    
    public void setExp(long exp) {
        this._exp = exp;
    }
    
    public boolean knownsObject(L1Object obj) {
        return this._knownObjects.contains(obj);
    }
    
    public List<L1Object> getKnownObjects() {
        return this._knownObjects;
    }
    
    public List<L1PcInstance> getKnownPlayers() {
        return this._knownPlayer;
    }
    
    public void addKnownObject(L1Object obj) {
        if (!this._knownObjects.contains(obj)) {
            this._knownObjects.add(obj);
            if (obj instanceof L1PcInstance) {
                this._knownPlayer.add((L1PcInstance)obj);
            }
        }
    }
    
    public void removeKnownObject(L1Object obj) {
        this._knownObjects.remove(obj);
        if (obj instanceof L1PcInstance) {
            this._knownPlayer.remove(obj);
        }
    }
    
    public void removeAllKnownObjects() {
        this._knownObjects.clear();
        this._knownPlayer.clear();
    }
    
    public String getName() {
        return this._name;
    }
    
    public void setName(String s) {
        this._name = s;
    }
    
    public synchronized int getLevel() {
        return this._level;
    }
    
    public synchronized void setLevel(int level) {
        this._level = level;
    }
    
    public int getMaxHp() {
        return this._maxHp;
    }
    
    public void setMaxHp(int hp) {
        this._trueMaxHp = hp;
        if (this instanceof L1PcInstance) {
            this._maxHp = (short)IntRange.ensure(this._trueMaxHp, 1, 32767);
        }
        else {
            this._maxHp = IntRange.ensure(this._trueMaxHp, 1, Integer.MAX_VALUE);
        }
        this._currentHp = Math.min(this._currentHp, this._maxHp);
    }
    
    public void addMaxHp(int i) {
        this.setMaxHp(this._trueMaxHp + i);
    }
    
    public int getMaxMp() {
        return this._maxMp;
    }
    
    public void setMaxMp(int mp) {
        this._trueMaxMp = mp;
        if (this instanceof L1PcInstance) {
            this._maxMp = (short)IntRange.ensure(this._trueMaxMp, 1, 32767);
        }
        else {
            this._maxMp = IntRange.ensure(this._trueMaxMp, 1, Integer.MAX_VALUE);
        }
        this._currentMp = Math.min(this._currentMp, this._maxMp);
    }
    
    public void addMaxMp(int i) {
        this.setMaxMp(this._trueMaxMp + i);
    }
    
    public int getAc() {
        int ac = this._ac;
        if (this instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)this;
            switch (pc.guardianEncounter()) {
                case 0: {
                    ac -= 2;
                    break;
                }
                case 1: {
                    ac -= 4;
                    break;
                }
                case 2: {
                    ac -= 6;
                    break;
                }
            }
        }
        return RangeInt.ensure(ac, -211, 44);
    }
    
    public void setAc(int i) {
        this._trueAc = i;
        this._ac = IntRange.ensure(i, -219, 44);
    }
    
    public void addAc(int i) {
        this.setAc(this._trueAc + i);
    }
    
    public short getStr() {
        return this._str;
    }
    
    public void setStr(int i) {
        this._trueStr = (short)i;
        this._str = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addStr(int i) {
        this.setStr(this._trueStr + i);
    }
    
    public short getCon() {
        return this._con;
    }
    
    public void setCon(int i) {
        this._trueCon = (short)i;
        this._con = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addCon(int i) {
        this.setCon(this._trueCon + i);
    }
    
    public short getDex() {
        return this._dex;
    }
    
    public void setDex(int i) {
        this._trueDex = (short)i;
        this._dex = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addDex(int i) {
        this.setDex(this._trueDex + i);
    }
    
    public short getCha() {
        return this._cha;
    }
    
    public void setCha(int i) {
        this._trueCha = (short)i;
        this._cha = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addCha(int i) {
        this.setCha(this._trueCha + i);
    }
    
    public short getInt() {
        return this._int;
    }
    
    public void setInt(int i) {
        this._trueInt = (short)i;
        this._int = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addInt(int i) {
        this.setInt(this._trueInt + i);
    }
    
    public short getWis() {
        return this._wis;
    }
    
    public void setWis(int i) {
        this._trueWis = (short)i;
        this._wis = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addWis(int i) {
        this.setWis(this._trueWis + i);
    }
    
    public int getWind() {
        return this._wind;
    }
    
    public void addWind(int i) {
        this._trueWind += i;
        if (this._trueWind >= 150) {
            this._wind = 150;
        }
        else if (this._trueWind <= -150) {
            this._wind = -150;
        }
        else {
            this._wind = this._trueWind;
        }
    }
    
    public int getWater() {
        return this._water;
    }
    
    public void addWater(int i) {
        this._trueWater += i;
        if (this._trueWater >= 150) {
            this._water = 150;
        }
        else if (this._trueWater <= -150) {
            this._water = -150;
        }
        else {
            this._water = this._trueWater;
        }
    }
    
    public int getFire() {
        return this._fire;
    }
    
    public void addFire(int i) {
        this._trueFire += i;
        if (this._trueFire >= 150) {
            this._fire = 150;
        }
        else if (this._trueFire <= -150) {
            this._fire = -150;
        }
        else {
            this._fire = this._trueFire;
        }
    }
    
    public int getEarth() {
        return this._earth;
    }
    
    public void addEarth(int i) {
        this._trueEarth += i;
        if (this._trueEarth >= 150) {
            this._earth = 150;
        }
        else if (this._trueEarth <= -150) {
            this._earth = -150;
        }
        else {
            this._earth = this._trueEarth;
        }
    }
    
    public int getAddAttrKind() {
        return this._addAttrKind;
    }
    
    public void setAddAttrKind(int i) {
        this._addAttrKind = i;
    }
    
    public int getRegistStun() {
        return this._registStun;
    }
    
    public void addRegistStun(int i) {
        this._trueRegistStun += i;
        if (this._trueRegistStun > 127) {
            this._registStun = 127;
        }
        else if (this._trueRegistStun < -128) {
            this._registStun = -128;
        }
        else {
            this._registStun = this._trueRegistStun;
        }
    }
    
    public int getRegistStone() {
        return this._registStone;
    }
    
    public void addRegistStone(int i) {
        this._trueRegistStone += i;
        if (this._trueRegistStone > 127) {
            this._registStone = 127;
        }
        else if (this._trueRegistStone < -128) {
            this._registStone = -128;
        }
        else {
            this._registStone = this._trueRegistStone;
        }
    }
    
    public int getRegistSleep() {
        return this._registSleep;
    }
    
    public void addRegistSleep(int i) {
        this._trueRegistSleep += i;
        if (this._trueRegistSleep > 127) {
            this._registSleep = 127;
        }
        else if (this._trueRegistSleep < -128) {
            this._registSleep = -128;
        }
        else {
            this._registSleep = this._trueRegistSleep;
        }
    }
    
    public int getRegistFreeze() {
        return this._registFreeze;
    }
    
    public void add_regist_freeze(int i) {
        this._trueRegistFreeze += i;
        if (this._trueRegistFreeze > 127) {
            this._registFreeze = 127;
        }
        else if (this._trueRegistFreeze < -128) {
            this._registFreeze = -128;
        }
        else {
            this._registFreeze = this._trueRegistFreeze;
        }
    }
    
    public int getRegistSustain() {
        return this._registSustain;
    }
    
    public void addRegistSustain(int i) {
        this._trueRegistSustain += i;
        if (this._trueRegistSustain > 127) {
            this._registSustain = 127;
        }
        else if (this._trueRegistSustain < -128) {
            this._registSustain = -128;
        }
        else {
            this._registSustain = this._trueRegistSustain;
        }
    }
    
    public int getRegistBlind() {
        return this._registBlind;
    }
    
    public void addRegistBlind(int i) {
        this._trueRegistBlind += i;
        if (this._trueRegistBlind > 127) {
            this._registBlind = 127;
        }
        else if (this._trueRegistBlind < -128) {
            this._registBlind = -128;
        }
        else {
            this._registBlind = this._trueRegistBlind;
        }
    }
    
    public int getDmgup() {
        return this._dmgup;
    }
    
    public void addDmgup(int i) {
        this._dmgup += i;
    }
    
    public int getBowDmgup() {
        return this._bowDmgup;
    }
    
    public void addBowDmgup(int i) {
        this._bowDmgup += i;
    }
    
    public int getHitup() {
        return this._hitup;
    }
    
    public void addHitup(int i) {
        this._trueHitup += i;
        if (this._trueHitup >= 127) {
            this._hitup = 127;
        }
        else if (this._trueHitup <= -128) {
            this._hitup = -128;
        }
        else {
            this._hitup = this._trueHitup;
        }
    }
    
    public int getBowHitup() {
        return this._bowHitup;
    }
    
    public void addBowHitup(int i) {
        this._trueBowHitup += i;
        if (this._trueBowHitup >= 127) {
            this._bowHitup = 127;
        }
        else if (this._trueBowHitup <= -128) {
            this._bowHitup = -128;
        }
        else {
            this._bowHitup = this._trueBowHitup;
        }
    }
    
    public int getMr() {
        if (this.hasSkillEffect(153)) {
            return this._mr >> 2;
        }
        else {
            return this._mr;
        }
    }
    
    public int getTrueMr() {
        return this._trueMr;
    }
    
    public void addMr(int i) {
        this._trueMr += i;
        if (this._trueMr <= 0) {
            this._mr = 0;
        }
        else {
            this._mr = this._trueMr;
        }
    }
    
    public int getSp() {
        return this.getTrueSp() + this._sp;
    }
    
    public int getTrueSp() {
        return this.getMagicLevel() + this.getMagicBonus();
    }
    
    public void addSp(int i) {
        this._sp += i;
    }
    
    public boolean isDead() {
        return this._isDead;
    }
    
    public void setDead(boolean flag) {
        this._isDead = flag;
    }
    
    public int getStatus() {
        return this._status;
    }
    
    public void setStatus(int i) {
        this._status = i;
    }
    
    public String getTitle() {
        return this._title;
    }
    
    public void setTitle(String s) {
        this._title = s;
    }
    
    public int getLawful() {
        return this._lawful;
    }
    
    public void setLawful(int i) {
        this._lawful = i;
    }
    
    public synchronized void addLawful(int i) {
        this._lawful += i;
        if (this._lawful > 32767) {
            this._lawful = 32767;
        }
        else if (this._lawful < -32768) {
            this._lawful = -32768;
        }
    }
    
    public int getHeading() {
        return this._heading;
    }
    
    public void setHeading(int i) {
        this._heading = Math.floorMod(i, 8);
    }
    
    public int getMoveSpeed() {
        return this._moveSpeed;
    }
    
    public void setMoveSpeed(int i) {
        this._moveSpeed = i;
    }
    
    public int getBraveSpeed() {
        return this._braveSpeed;
    }
    
    public void setBraveSpeed(int i) {
        this._braveSpeed = i;
    }
    
    public int getTempCharGfx() {
        return this._tempCharGfx;
    }
    
    public void setTempCharGfx(int i) {
        this._tempCharGfx = i;
    }
    
    public int getGfxId() {
        return this._gfxid;
    }
    
    public void setGfxId(int i) {
        this._gfxid = i;
    }
    
    public int getMagicLevel() {
        return this.getLevel() / 2;
    }
    
public int getMagicBonus() {
    int i = this.getInt();

    if (i <= 5) {
        return -2;
    } else if (i <= 8) {
        return -1;
    } else if (i <= 11) {
        return 0;
    } else if (i <= 14) {
        return 1;
    } else if (i <= 17) {
        return 2;
    } else if (i <= 24) {
        return i - 15;
    } else {
        return i - 14;   // 不再封頂，INT越高越多
    }
}
    
    public boolean isInvisble() {
        return this.hasSkillEffect(60) || this.hasSkillEffect(97);
    }
    
    public void healHp(int pt) {
        this.setCurrentHp(this.getCurrentHp() + pt);
    }
    
    public int getKarma() {
        return this._karma;
    }
    
    public void setKarma(int karma) {
        this._karma = karma;
    }
    
    public void setMr(int i) {
        this._trueMr = i;
        if (this._trueMr <= 0) {
            this._mr = 0;
        }
        else {
            this._mr = this._trueMr;
        }
    }
    
    public void turnOnOffLight() {
        int lightSize = 0;
        if (this instanceof L1NpcInstance) {
            L1NpcInstance npc = (L1NpcInstance)this;
            lightSize = npc.getLightSize();
        }
        for (L1ItemInstance item : this.getInventory().getItems()) {
            if (item.getItem().getType2() == 0 && item.getItem().getType() == 2) {
                int itemlightSize = item.getItem().getLightRange();
                if (itemlightSize != 0 && item.isNowLighting() && itemlightSize > lightSize) {
                    lightSize = itemlightSize;
                }
                else {
                    continue;
                }
            }
        }
        if (this.hasSkillEffect(2)) {
            lightSize = 14;
        }
        if (this instanceof L1PcInstance) {
            if (ConfigOther.LIGHT) {
                lightSize = 20;
            }
            L1PcInstance pc = (L1PcInstance)this;
            pc.sendPackets(new S_Light(pc.getId(), lightSize));
        }
        if (!this.isInvisble()) {
            this.broadcastPacketAll(new S_Light(this.getId(), lightSize));
        }
        this.setOwnLightSize(lightSize);
        this.setChaLightSize(lightSize);
    }
    
    public int getChaLightSize() {
        if (this.isInvisble()) {
            return 0;
        }
        else if (ConfigOther.LIGHT) {
            return 14;
        }
        else {
            return this._chaLightSize;
        }
    }
    
    public void setChaLightSize(int i) {
        this._chaLightSize = i;
    }
    
    public int getOwnLightSize() {
        if (this.isInvisble()) {
            return 0;
        }
        else if (ConfigOther.LIGHT) {
            return 14;
        }
        else {
            return this._ownLightSize;
        }
    }
    
    public void setOwnLightSize(int i) {
        this._ownLightSize = i;
    }
    
    public int get_tmp_targetid() {
        return this._tmp_targetid;
    }
    
    public void set_tmp_targetid(int targetid) {
        this._tmp_targetid = targetid;
    }
    
    public int get_tmp_mr() {
        return this._tmp_mr;
    }
    
    public void set_tmp_mr(int tmp) {
        this._tmp_mr = tmp;
    }
    
    public int get_dodge() {
        return this._dodge_up;
    }
    
    public void add_dodge(int i) {
        this._dodge_up += i;
        if (this._dodge_up > 10) {
            this._dodge_up = 10;
        }
        else if (this._dodge_up < 0) {
            this._dodge_up = 0;
        }
    }
    
    public int get_dodge_down() {
        return this._dodge_down;
    }
    
    public void add_dodge_down(int i) {
        this._dodge_down += i;
        if (this._dodge_down > 10) {
            this._dodge_down = 10;
        }
        else if (this._dodge_down < 0) {
            this._dodge_down = 0;
        }
    }
    
    public void addHierarch(L1HierarchInstance hierarch) {
        this._Hierarch = hierarch;
    }
    
    public void removeHierarch() {
        this._Hierarch = null;
    }
    
    public L1HierarchInstance getHierarchs() {
        return this._Hierarch;
    }
    
    public int getHierarch() {
        return this._hierarch;
    }
    
    public void setHierarch(int i) {
        this._hierarch = i;
    }
    
    public void set_decay_potion(boolean b) {
        this._decay_potion = b;
    }
    
    public boolean is_decay_potion() {
        return this._decay_potion;
    }
    
    public String getViewName() {
        return this._name;
    }
    
    public int getInnRoomNumber() {
        return this._innRoomNumber;
    }
    
    public void setInnRoomNumber(int i) {
        this._innRoomNumber = i;
    }
    
    public boolean checkRoomOrHall() {
        return this._isHall;
    }
    
    public void setHall(boolean i) {
        this._isHall = i;
    }
    
    public void add_TrueTargetEffect(L1EffectInstance effect) {
        this._TrueTargetEffectList.add(effect);
    }
    
    public ArrayList<L1EffectInstance> get_TrueTargetEffectList() {
        return this._TrueTargetEffectList;
    }
    
    public void setTripleArrow(boolean b) {
        this._TripleArrow = b;
    }
    
    public boolean isTripleArrow() {
        return this._TripleArrow;
    }
    
    public boolean isFreezeAtion() {
        return this.hasSkillEffect(87) || this.hasSkillEffect(50) || this.hasSkillEffect(157) || this.isSleeped() || this.isParalyzed() || this.isDead();
    }
    
    public void broadcastPacketBossWeaponAll(ServerBasePacket packet) {
        try {
            for (L1PcInstance pc : World.get().getVisiblePlayer(this)) {
                if (pc.get_showId() == this.get_showId() && !L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
                    pc.sendPackets(packet);
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int getcancellation() {
        return this._cancellation;
    }
    
    public void setcancellation(int i) {
        this._cancellation = i;
    }
    
    public int getdouble_score() {
        return this._double_score;
    }
    
    public void setdouble_score(int i) {
        this._double_score = i;
    }
    
    public int get_poisonStatus2() {
        return this._poisonStatus2;
    }
    
    public void set_poisonStatus2(int i) {
        this._poisonStatus2 = i;
    }
    
    public int get_poisonStatus7() {
        return this._poisonStatus7;
    }
    
    public void set_poisonStatus7(int i) {
        this._poisonStatus7 = i;
    }
    
    public boolean isPinkName() {
        return this._isPinkName;
    }
    
    public void setPinkName(boolean flag) {
        this._isPinkName = flag;
    }
    
    public void setuseitemobjid(int objid) {
        this._useitemobjid = objid;
    }
    
    public int getuseitemobjid() {
        return this._useitemobjid;
    }
    
    public boolean checkPassable(int locx, int locy) {
        boolean isPc = false;
        L1PcInstance pc = null;
        if (this instanceof L1PcInstance) {
            pc = (L1PcInstance)this;
            isPc = true;
        }
        if (isPc && QuestMapTable.get().isQuestMap((int)this.getMapId())) {
            return false;
        }
        else {
            Collection<L1Object> allObj = (Collection)World.get().getVisibleObjects(this, 1);
            if (allObj.isEmpty()) {
                return false;
            }
            else {
                for (L1Object obj : allObj) {
                    if (obj instanceof L1ItemInstance) {
                        continue;
                    }
                    else if (!(obj instanceof L1Character)) {
                        continue;
                    }
                    else {
                        L1Character character = (L1Character)obj;
                        if (character.isInvisble()) {
                            continue;
                        }
                        else {
                            if (character instanceof L1MonsterInstance) {
                                L1MonsterInstance mon = (L1MonsterInstance)character;
                                if (mon.isDead()) {
                                    continue;
                                }
                                else if (mon.getHiddenStatus() == 2) {
                                    continue;
                                }
                                else if (mon.getHiddenStatus() == 1) {
                                    continue;
                                }
                                else if (mon.getHiddenStatus() == 3) {
                                    continue;
                                }
                                else if (mon.isInvisble()) {
                                    continue;
                                }
                                else if (isPc) {
                                    if (pc.isMoveStatus()) {
                                        pc.setMoveErrorCount(0);
                                    }
                                    else if (pc.getMoveErrorCount() >= 2) {
                                        pc.setMoveErrorCount(pc.getMoveErrorCount() - 1);
                                        return true;
                                    }
                                    if (mon.getX() == locx && mon.getY() == locy && mon.getMapId() == this.getMapId()) {
                                        pc.setMoveErrorCount(pc.getMoveErrorCount() + 1);
                                        pc.setMoveStatus(false);
                                    }
                                    else {
                                        pc.setMoveStatus(true);
                                    }
                                }
                                else if (mon.getX() == locx && mon.getY() == locy) {
                                    return true;
                                }
                            }
                            if (character instanceof L1PcInstance) {
                                L1PcInstance tgpc = (L1PcInstance)character;
                                if (tgpc.isDead()) {
                                    continue;
                                }
                                else if (tgpc.isInvisble()) {
                                    continue;
                                }
                                else if (tgpc.isGmInvis()) {
                                    continue;
                                }
                                else if (tgpc.isGhost()) {
                                    continue;
                                }
                                else if (tgpc.getX() == locx && tgpc.getY() == locy && tgpc.getMapId() == this.getMapId()) {
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
                return false;
            }
        }
    }
    
    public boolean isParalyzed_guaji() {
        return this.hasSkillEffect(50) || this.hasSkillEffect(80) || this.hasSkillEffect(157) || this.hasSkillEffect(87) || this.hasSkillEffect(66) || this.hasSkillEffect(33) || this.hasSkillEffect(4000) || this.hasSkillEffect(192) || this.hasSkillEffect(4017);
    }
}
